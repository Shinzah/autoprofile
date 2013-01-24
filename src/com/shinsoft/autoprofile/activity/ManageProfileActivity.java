package com.shinsoft.autoprofile.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.manager.NotificationBuilder;
import com.shinsoft.autoprofile.manager.content.ContentManager;
import com.shinsoft.autoprofile.profile.ProfileInfo;

/**
 * SubActivity for AutoProfile
 * Add a profile
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class ManageProfileActivity extends TabActivity implements OnTabChangeListener
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Class constants
	private static final String TAB_GENERAL = "general";
	private static final String TAB_CONDITIONS = "conditions";
	private static final String TAB_PARAMETERS = "parameters";
	
	// Class property
	private ProfileInfo profileInfo;
	private boolean back = false;
	
	 /**
	  * At activity creation, initialize widgets depending on mode (Edit/Add) and create tabs
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageprofile);
        
        // Retrieve profileInfo to work on
        if (this.getIntent().hasExtra(Constants.EXTRA_KEY_PROFILE_POSITION))
        {
        	int position = this.getIntent().getExtras().getInt(Constants.EXTRA_KEY_PROFILE_POSITION);
        	this.profileInfo = ContentManager.getProfileManager(this).getAllProfile().get(position);
        }
        else
        	this.profileInfo = new ProfileInfo();
         
        // Are we in edition mode ?
		if (this.profileInfo.getId() > 0)
			this.setTitle(R.string.Title_ManageProfileActivity_EditProfile);
		
        // BUILDING TAB
        Intent intent;
        TabHost tabHost = getTabHost();
        tabHost.setup();
        tabHost.setOnTabChangedListener(this);
        
        TabSpec tabSpec;
        // 'General' tab
        intent = new Intent(this, ManageProfile_GeneralActivity.class);
        tabSpec = tabHost.newTabSpec(TAB_GENERAL).setContent(intent);
        tabSpec.setIndicator(getResources().getString(R.string.ManageProfile_TabGeneral),getResources().getDrawable(R.drawable.edit));
        tabHost.addTab(tabSpec);
        
        // 'Conditions' tab
        intent = new Intent(this, ManageProfile_ConditionsActivity.class);
        tabSpec = tabHost.newTabSpec(TAB_CONDITIONS).setContent(intent);
        tabSpec.setIndicator(getResources().getString(R.string.ManageProfile_TabConditions),getResources().getDrawable(R.drawable.listconditions));
        tabHost.addTab(tabSpec);
        
        // 'Parameters' tab
        intent = new Intent(this, ManageProfile_ParametersActivity.class);
        tabSpec = tabHost.newTabSpec(TAB_PARAMETERS).setContent(intent);
        tabSpec.setIndicator(getResources().getString(R.string.ManageProfile_TabParameters),getResources().getDrawable(R.drawable.listparameters));
        tabHost.addTab(tabSpec);
    }
    
    /**
     * When leaving the activity, save the current profile is modified and inform caller of modification
     * @see android.app.Activity#finish()
     * @since 1.0
     */
    @Override
    public void finish()
    {
    	// If the profileInfo is valid, then save profileInfo
    	if (isValidProfileInfo())
    	{
    		try
    		{
    			if (this.profileInfo.isbModified())
    			{
	    			ContentManager.getProfileManager(this).saveProfile(this.profileInfo);
	    			
	    			// If a profile as been added, then update profile list
	    			if (ContentManager.getProfileManager(this).getAllProfile().contains(this.profileInfo))
	    				ContentManager.getProfileManager(this).getAllProfile().remove(this.profileInfo);
	    			ContentManager.getProfileManager(this).getAllProfile().add(this.profileInfo);
	    			
	        		// Display a toast message
	    			NotificationBuilder.notifyProfileSaved(this,this.profileInfo.getName());
	    			
	    			getIntent().putExtra(Constants.EXTRA_KEY_PROFILEINFO,this.profileInfo);
	    			setResult(Constants.RESULT_CODE_OK,getIntent());
    			}
    			else
    				setResult(Constants.RESULT_CODE_CANCEL);
    		}
    		catch (Exception e)
    		{
    			if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false))
    				Toast.makeText(this, "[Debug] Exception when saving profile (E:" + e.getMessage() + ")", Toast.LENGTH_LONG).show();
				
				Log.e(TAG, "<!> An error append while saving profile <!>",e);
    		}
    		super.finish();
    	}
    	// If in creation mode and back is selected, then do anything
    	else if (this.back && this.profileInfo.getId() == 0)
    	{
    		setResult(Constants.RESULT_CODE_CANCEL);
    		super.finish();
    	}
    }
    
	/**
	 * When changing tab, if the profile name is empty, avoid to change tab, force to stay on tab id0 (general)
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 * @since 1.0
	 */
	public void onTabChanged(String tabId)
	{
		// If the profileInfo is invalid and we are trying to go to others tab, block it and stay focuses on first tab (general)
		if (
				(
					(tabId.equals(TAB_CONDITIONS))
					|| (tabId.equals(TAB_PARAMETERS))
				)
				&& (!isValidProfileInfo())
    		)
    	{
    		getTabHost().setCurrentTab(0);
    	}
		// Else, if the profileInfo id is 0, we have to save it in database
		else if (!tabId.equals(TAB_GENERAL) && isValidProfileInfo() && this.profileInfo.getId() == 0)
		{
			try
			{
				ContentManager.getProfileManager(this).saveProfile(this.profileInfo);
			}
			catch (Exception e)
			{
				if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false))
    				Toast.makeText(this, "[Debug] Exception when saving profile (E:" + e.getMessage() + ")", Toast.LENGTH_LONG).show();
				
				Log.e(TAG, "<!> An error append while saving profile <!>",e);
			}
		}
	}
    
    /**
     * When pressing menu key, display a specific options menu
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     * @since 1.0
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	getMenuInflater().inflate(R.menu.manageprofile_menu, menu);
    	return true;
    }
    
    /**
     * When a menu item is selected, to specific actions
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * @since 1.0
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId())
    	{
	    	case R.id.MenuItem_ManageProfileBackItem :
	    	{
	    		this.back = true;
	    		this.finish();
	    		break;
	    	}
	    	case R.id.MenuItem_ManageProfileSaveItem :
	    	{
	    		this.profileInfo.setbModified(true);
				this.finish();
				break;
	    	}
    	}
    	return super.onOptionsItemSelected(item);
    }
	
	
    /**
     * Check if the profileInfo is valid, and display a toast is an error is spotted
     * 	- Check if the profileName is filled
     * @return
     * @since 1.0
     */
    private boolean isValidProfileInfo()
    {
    	if (this.profileInfo.getName() == null || this.profileInfo.getName().trim().length() == 0)
		{
    		Toast.makeText(this, R.string.ErrorEmptyProfileName, Toast.LENGTH_LONG).show();
    		return false;
		}
    	else
    		return true;
    }
    
	/**
	 * Getter for activity profileInfo
	 * @return
	 * @since 1.0
	 */
	public ProfileInfo getProfileInfo()
	{
		return this.profileInfo;
	}
}
