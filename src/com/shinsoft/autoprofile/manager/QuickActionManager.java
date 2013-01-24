/** 
 * Interface Technologies (c), 2010
 * Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com), 5 oct. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager
 * File name: QuickActionManager.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.manager;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.activity.ManageProfile_ConditionsActivity;
import com.shinsoft.autoprofile.activity.ManageProfile_ParametersActivity;
import com.shinsoft.autoprofile.manager.model.ProfileItemInterface;
import com.shinsoft.autoprofile.profile.ProfileInfo;
import com.shinsoft.autoprofile.view.ActionItem;
import com.shinsoft.autoprofile.view.QuickAction;

public class QuickActionManager
{
	public static QuickAction buildQuickActionForProfileItem(final Context context, View v, ProfileInfo profileInfo, int itemPosition, int itemType)
	{
		// Retrieve the ProfileItem to use
		ProfileItemInterface profileItem = null;
		switch (itemType)
		{
			case ProfileItemInterface.TYPE_CONDITION :
			{
				profileItem = profileInfo.getListConditions().get(itemPosition);
				break;
			}
			case ProfileItemInterface.TYPE_PARAMETER :
			{
				profileItem = profileInfo.getListParameters().get(itemPosition);
				break;
			}
		}

		final QuickAction qa = new QuickAction(v,context.getResources().getString(profileItem.getNameId()),profileItem.getIconResourceId());
		final ActionItem modifyItem;
		final ActionItem deleteItem;
		
		// Create 'modify' button
		modifyItem = new ActionItem(context.getResources().getDrawable(R.drawable.edit),itemPosition);
		modifyItem.setTitle(context.getResources().getString(R.string.ListContextMenuModify));
		modifyItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) { 
				if (context instanceof ManageProfile_ConditionsActivity)
					((ManageProfile_ConditionsActivity)context).editSelected(modifyItem.getPosition());
				else if (context instanceof ManageProfile_ParametersActivity)
					((ManageProfile_ParametersActivity)context).editSelected(modifyItem.getPosition());
			}
		});
		
		// Create 'delete' button
		deleteItem = new ActionItem(context.getResources().getDrawable(R.drawable.delete),itemPosition);
		deleteItem.setTitle(context.getResources().getString(R.string.ListContextMenuDelete));
		deleteItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (context instanceof ManageProfile_ConditionsActivity)
					((ManageProfile_ConditionsActivity)context).removeSelected(modifyItem.getPosition());
				else if (context instanceof ManageProfile_ParametersActivity)
					((ManageProfile_ParametersActivity)context).removeSelected(modifyItem.getPosition());
			}
		});
		
		// Add item to QuickAction and display it
		qa.addActionItem(modifyItem);
		qa.addActionItem(deleteItem);
		qa.setAnimStyle(QuickAction.ANIM_AUTO);
		qa.animateTrack(false);

		return qa;
	}
}