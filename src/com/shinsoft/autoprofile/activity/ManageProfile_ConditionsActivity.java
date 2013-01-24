/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.activity
 * File name: ManageProfile_ConditionsActivity.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.activity.conditions.GenericConditionActivity;
import com.shinsoft.autoprofile.adapter.ConditionAdapter;
import com.shinsoft.autoprofile.conditions.utils.ConditionsManager;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.manager.content.ContentManager;
import com.shinsoft.autoprofile.manager.exception.ProfileException;
import com.shinsoft.autoprofile.profile.ProfileInfo;
import com.shinsoft.autoprofile.view.ActionItem;
import com.shinsoft.autoprofile.view.QuickAction;

public class ManageProfile_ConditionsActivity extends ListActivity implements OnClickListener, OnItemClickListener
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Class property objects
	private static ConditionAdapter conditionAdapter;
	private static Intent intent;
	
	// Activity widgets
	private static Button addConditionBtn;
	
	/**
	 * When creating activity, configure each widgets depending of profileid
	 *  - Use a specific layout when profileInfo id = 1 (default one)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * @since 1.0
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        ProfileInfo profileInfo =((ManageProfileActivity)getParent()).getProfileInfo();
        if (profileInfo.getId() == 1)
        	setContentView(R.layout.manageprofile_defaultconditions);
        else
        {
        	setContentView(R.layout.manageprofile_conditions);
        
	        // Context menu configuration
	        registerForContextMenu(getListView());
	        
	        // ============================
	        //		INITIALIZE WIDGETS
	        // ============================
	        conditionAdapter = new ConditionAdapter(this, profileInfo.getListConditions());
	    	this.setListAdapter(conditionAdapter);
	        this.getListView().setOnItemClickListener(this);
	    
	        addConditionBtn = (Button)this.findViewById(R.id.ManageProfile_ListConditionsAdd);
	        addConditionBtn.setOnClickListener(this);
	        addConditionBtn.setOnCreateContextMenuListener(this);
        }
	}
	
	@Override
	protected void onDestroy()
	{
		conditionAdapter = null;
		intent = null;
		addConditionBtn = null;
		super.onDestroy();
	}
	
	/**
	 * Retrieve results of called Activity (Add/Edit conditions)
	 * @see com.shinsoft.autoprofile.activity.utils.ManageProfileListActivityAbstract#onActivityResult(int, int, android.content.Intent)
	 * @since 1.0
	 */
	@Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
    	// Do actions depending of activity finished
    	switch (requestCode)
    	{
    		// 'Add condition' and 'Edit condition'
    		case Constants.REQUEST_CODE_EDITCONDITION :
    		case Constants.REQUEST_CODE_ADDCONDITION :
    		{
    			if (resultCode == Constants.RESULT_CODE_OK)
    			{
    				try
    				{
	    				// Retrieve condition
	    				ProfileInfo profileInfo = ((ManageProfileActivity)getParent()).getProfileInfo();
	    				ProfileCondition profileCondition = (ProfileCondition)data.getExtras().getSerializable(Constants.EXTRA_KEY_PROFILECONDITION);
	    				// Save into database
	    				ContentManager.getProfileManager(this).saveCondition(profileCondition);
	    				// Save in profileInfo
	    				if (profileInfo.getListConditions().contains(profileCondition))
	    					profileInfo.replaceCondition(profileCondition);
	    				else
	    					profileInfo.addCondition(profileCondition);
	    				profileInfo.setbModified(true);
	    				// Updating view
	    				conditionAdapter.notifyDataSetChanged();
    				}
    				catch (ProfileException pe)
    				{
    					if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false))
    						Toast.makeText(this, "[Debug] Exception when saving condition (E:" + pe.getMessage() + ")", Toast.LENGTH_LONG).show();
    					
    					Log.e(TAG, "<!> An error append while saving condition <!>",pe);
    				}
    			}
    			break;
    		}
    	}
    	super.onActivityResult(requestCode,resultCode,data);
    }
	
	
	/**
	 * When a click is performed in available condition list, call new activity to configure the new condition
	 * When a click is performed in conditions list, open quick action
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 * @since 1.0
	 */
	public void onItemClick(AdapterView<?> parent,View v,int position,long id)
	{
		ProfileCondition profileCondition = this.getProfileInfo().getListConditions().get(position);
		
		final QuickAction qa = new QuickAction(v,getResources().getString(profileCondition.getNameId()),profileCondition.getIconResourceId());
		final ActionItem modifyItem;
		final ActionItem deleteItem;

		// Create 'modify' button
		modifyItem = new ActionItem(getResources().getDrawable(R.drawable.edit),position);
		modifyItem.setTitle(getResources().getString(R.string.ListContextMenuModify));
		modifyItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				editSelectedCondition(modifyItem.getPosition());
			}
		});
		
		// Create 'delete' button
		deleteItem = new ActionItem(getResources().getDrawable(R.drawable.delete),position);
		deleteItem.setTitle(getResources().getString(R.string.ListContextMenuDelete));
		deleteItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				removeSelectedCondition(modifyItem.getPosition());
			}
		});
		
		// Add item to QuickAction and display it
		qa.addActionItem(modifyItem);
		qa.addActionItem(deleteItem);
		qa.setAnimStyle(QuickAction.ANIM_AUTO);
		qa.animateTrack(false);
		qa.show();
	}
	
	/**
	 * When click performed on Add button, display the addCondition dialog
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * @since 1.0
	 */
	public void onClick(View v) 
	{
		// Check if the button Add button as been clicked
		switch (v.getId())
		{
			case R.id.ManageProfile_ListConditionsAdd :
			{
				if (this.getProfileInfo().getListAvailableConditions().size() > 0)
					v.showContextMenu();
				else
					Toast.makeText(this,R.string.ManageProfile_ListConditionsEmpty,Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}
	
	
    @Override
	public void onCreateContextMenu(ContextMenu menu,View v,ContextMenuInfo menuInfo)
	{
    	switch (v.getId())
    	{
    		case R.id.ManageProfile_ListConditionsAdd :
    		{
    			menu.setHeaderTitle(R.string.ManageProfile_ListConditionsAdd);
    			for (Class<?> currentClass : this.getProfileInfo().getListAvailableConditions())
    			{
    				try
    				{
    					ProfileCondition currentCondition = ((ProfileCondition)currentClass.newInstance());
    					menu.add(0/*groupId*/,currentCondition.getClassId()/*itemId*/,0/*order*/,currentCondition.getNameId());
    					super.onCreateContextMenu(menu,v,menuInfo);
    				}
    				catch (Exception e)
    				{
    					
    				}
    			}
    			break;
    		}
    	}
	}
	/**
	 * When selecting a context menu item, perform specific action
	 *  - Add selected condition
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 * @since 1.0
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		switch (item.getItemId())
    	{
	    	// AddCondition Context Menu clicked
	    	default :
	    	{
	    		try
	    		{
		    		int classId = item.getItemId();
					Intent intent = new Intent().setClass(this, GenericConditionActivity.class);
					// Create a new instance of selected condition class, and set profileId
					ProfileCondition profileCond = (ProfileCondition)ConditionsManager.getClassForClassID(classId).newInstance();
					profileCond.setProfileid(((ManageProfileActivity)getParent()).getProfileInfo().getId());
					// Call new Activity
			        intent.putExtra(Constants.EXTRA_KEY_PROFILECONDITION,profileCond);
			        startActivityForResult(intent,Constants.REQUEST_CODE_ADDCONDITION);
	    		}
	    		catch (Exception e)
	    		{
	    			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false))
	    				Toast.makeText(this, "[Debug] Exception when generating activity to add condition (E:" + e.getMessage() + ")", Toast.LENGTH_LONG).show();
	    			
	    			Log.e(TAG, "<!> An error append while generating activity to add condition <!>",e);
	    		}
		        
	    		break;
	    	}
    	}
		return super.onContextItemSelected(item);
	}
	
	/**
	 * Remove selected condition
	 * @param position
	 * @throws ProfileException
	 * @since 1.0
	 */
	private void removeSelectedCondition(int position)
	{
		try
		{
			// Remove from list and refresh display
			ProfileInfo profileInfo = ((ManageProfileActivity)getParent()).getProfileInfo();
			ProfileCondition profileCondition = profileInfo.getListConditions().get(position);
			// Update database
			ContentManager.getProfileManager(this).deleteCondition(profileCondition.getId());
			// Update object
			profileInfo.removeCondition(profileCondition);
			profileInfo.setbModified(true);
			// Update list view
			conditionAdapter.notifyDataSetChanged();
		}
		catch (Exception e)
		{
			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false))
				Toast.makeText(this, "[Debug] Exception when deleting condition (E:" + e.getMessage() + ")", Toast.LENGTH_LONG).show();
			
			Log.e(TAG, "<!> An error append while deleting condition <!>",e);
		}
	}
	
	/**
	 * Call a new activity to edit selected condition (provide position in list)
	 * @param profileCond
	 * @since 1.0
	 */
	private void editSelectedCondition(int position)
	{
		ProfileCondition profileCond = this.getProfileInfo().getListConditions().get(position);
		intent = new Intent(this,GenericConditionActivity.class);
		intent.putExtra(Constants.EXTRA_KEY_PROFILECONDITION,profileCond);
		startActivityForResult(intent,Constants.REQUEST_CODE_EDITCONDITION);
	}
	
	/**
	 * Retrieve parent profileInfo
	 * @return
	 * @since 1.0
	 */
	public ProfileInfo getProfileInfo()
	{
		return ((ManageProfileActivity)this.getParent()).getProfileInfo();
	}
}