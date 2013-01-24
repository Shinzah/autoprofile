/** 
 * Interface Technologies (c), 2010
 * Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com), 8 oct. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.adapter
 * File name: ExpandableProfileAdapter.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.adapter;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.manager.content.ContentManager;
import com.shinsoft.autoprofile.manager.content.ProfileManager;
import com.shinsoft.autoprofile.profile.ProfileInfo;
import com.shinsoft.autoprofile.view.PriorityBar;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class ExpandableProfileAdapter extends BaseExpandableListAdapter
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Class constants
	private static final int CHILD_POSITION_CONDITIONS = 0;
	private static final int CHILD_POSITION_PARAMETERS = 1;
	
	// Class objects
	private LayoutInflater mInflater;
	private ProfileManager profileManager;
	private Activity mActivity;
	
	public ExpandableProfileAdapter(Activity activity)
	{
		this.mInflater = LayoutInflater.from(activity);
		this.mActivity = activity;
		this.profileManager = ContentManager.getProfileManager(activity);
	}
	
	public Object getChild(int groupPosition,int childPosition)
	{
		return null;
	}

	public long getChildId(int groupPosition,int childPosition)
	{
		return childPosition;
	}

	public View getChildView(int groupPosition,int childPosition,
						boolean isLastChild,View view,
						ViewGroup viewGroup)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int getChildrenCount(int groupPosition)
	{
		// There is always 2 children (1 for conditions and 1 for parameters)
		return 2;
	}

	public Object getGroup(int groupPosition)
	{
		return this.profileManager.getAllProfile().get(groupPosition);
	}

	public int getGroupCount()
	{
		return this.profileManager.getAllProfile().size();
	}

	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	public View getGroupView(int groupPosition,
					boolean isExpanded,
					View profileRow, ViewGroup parent)
	{
		ProfileViewHolder holder;
		ProfileInfo profileInfo = this.profileManager.getAllProfile().get(groupPosition);
		
		// If convertView is null then inflate profileView layout
		if (profileRow == null)
		{
			holder = new ProfileViewHolder();
			profileRow = mInflater.inflate(R.layout.rowview_profile,null);
			
			// Retrieve all layout widgets
			holder.profileIcon = (ImageView)profileRow.findViewById(R.id.ListProfileIcon);
			holder.profileName = (TextView)profileRow.findViewById(R.id.ListProfileName);
			holder.profileConditions = (TextView)profileRow.findViewById(R.id.ListProfileConditions);
			holder.profilePriorityBar = (PriorityBar)profileRow.findViewById(R.id.ListProfilePriorityBar);
			holder.statusIcon = (ImageView)profileRow.findViewById(R.id.ListProfileIconStatus);
			profileRow.setTag(holder);
		}
		else
			holder = (ProfileViewHolder) profileRow.getTag();
		
		// Configure Name
		holder.profileName.setText(profileInfo.getName());
		if (!profileInfo.isActivate())
		{
			profileRow.setBackgroundResource(R.color.lighter_bgcolor);
			holder.profileName.setTextAppearance(mActivity,R.style.ProfileName_Desactivated);
		}
		else
		{
			profileRow.setBackgroundResource(0);
			holder.profileName.setTextAppearance(mActivity,R.style.ProfileName);
		}
		
		if (profileInfo.isInUse() && PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(mActivity.getResources().getString(R.string.PreferenceItemKey_AutoLaunchService),true))
			holder.statusIcon.setImageResource(R.drawable.activate);
		else
			holder.statusIcon.setImageDrawable(null);
		
		
		// If default profile, do not display conditions but display specific text
		if (profileInfo.getId() == Constants.DEFAULT_PROFILE_ID)
			holder.profileConditions.setText(R.string.ConditionsSummary_DefaultProfile);
		else
			holder.profileConditions.setText(profileInfo.buildConditionsSummary(mActivity));
		
		// Filling icon
		holder.profileIcon.setImageResource(profileInfo.getIconResourceId());
		// Filling priorityFiller
		if (PreferenceManager.getDefaultSharedPreferences(mActivity).getBoolean(mActivity.getResources().getString(R.string.PreferenceItemKey_ListProfiles_DisplayPriority),true))
			holder.profilePriorityBar.setPercentComplete((profileInfo.getPrio()*2));		
		else
			holder.profilePriorityBar.setVisibility(View.INVISIBLE);
		
		return (profileRow);
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public boolean isChildSelectable(int groupPosition,int childPosition)
	{
		return false;
	}

	
	
	
	/**
	 * Internal class to hold all list row widgets
	 * <br>
	 * Versions : <br>
	 *
	 *   <li>1.0 : Initialisation</li>
	 * <br>
	 * @version 1.0
	 */
	private class ProfileViewHolder
	{
		private ImageView profileIcon;
		private ImageView statusIcon;
		private TextView profileName;
		private TextView profileConditions;
		private PriorityBar profilePriorityBar;
	}
	
}



