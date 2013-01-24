package com.shinsoft.autoprofile.manager;

import com.shinsoft.autoprofile.R;

/**
 * Constants Class
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class Constants 
{
	// ACTION CONSTANTS
	public static final String ACTION_PROFILE_UPDATED				= "ProfileUpdated";
	public static final String ACTION_PROFILEACTIVATED_UPDATED		= "ProfileActivatedUpdated";
	
	// DEFAULT CONSTANTS
	public static final int DEFAULT_PROFILE_ID 						= 1;
	
	// RECEIVER EVENT CONSTANTS
	public static final int EVENT_BATTERY							= 1;
	public static final int EVENT_DATE								= 2;
	public static final int EVENT_TIME								= 3;
	
	// REQUEST CODE FOR AUTOPROFILE
	// AutoProfileActivity
	public static final int REQUEST_CODE_ADDPROFILE 				= 1;
	public static final int REQUEST_CODE_EDITPROFILE 				= 2;
	public static final int REQUEST_CODE_DISPLAYPREFS 				= 3;
	// ManageProfileActivity
	public static final int REQUEST_CODE_ADDCONDITION 				= 1;
	public static final int REQUEST_CODE_EDITCONDITION 				= 2;
	public static final int REQUEST_CODE_ADDPARAMETER 				= 3;
	public static final int REQUEST_CODE_EDITPARAMETER 				= 4;
	// SaveProfileDialogActivity
	public static final int RESULT_CODE_OK 							= 1;
	public static final int RESULT_CODE_NO 							= 2;
	public static final int RESULT_CODE_CANCEL 						= 3;
	
	// BUNDLE EXTRAS KEY
	public static final String EXTRA_KEY_PROFILEINFO 				= "ProfileInfo";
	public static final String EXTRA_KEY_PROFILE_POSITION			= "ProfilePosition";
	
	public static final String EXTRA_KEY_PROFILECONDITION 			= "ProfileCondition";
	public static final String EXTRA_KEY_PROFILEPARAMETER 			= "ProfileParameter";
	
	
	// ICON RESOURCES NAME
	public static final int[] PROFILEICON_RESOURCES_LIST = {
		R.drawable.profileicon1, R.drawable.profileicon2,
		R.drawable.profileicon3, R.drawable.profileicon4,
		R.drawable.profileicon5, R.drawable.profileicon6,
		R.drawable.profileicon7, R.drawable.profileicon8,
		R.drawable.profileicon9, R.drawable.profileicon10,
		R.drawable.profileicon11, R.drawable.profileicon12,
		R.drawable.profileicon13, R.drawable.profileicon14,
		R.drawable.profileicon15, R.drawable.profileicon16,
		R.drawable.profileicon17, R.drawable.profileicon18,
		R.drawable.profileicon19, R.drawable.profileicon20,
		R.drawable.profileicon21, R.drawable.profileicon22,
		R.drawable.profileicon23, R.drawable.profileicon24,
		R.drawable.profileicon25, R.drawable.profileicon26,
		R.drawable.profileicon27, R.drawable.profileicon28,
		R.drawable.profileicon29, R.drawable.profileicon30,
		R.drawable.profileicon31, R.drawable.profileicon32,
		R.drawable.profileicon33, R.drawable.profileicon34,
		R.drawable.profileicon35, R.drawable.profileicon36
	};
	public static final String[] PROFILEICON_RESOURCESNAME_LIST = new String[PROFILEICON_RESOURCES_LIST.length];
	public static final String[] PROFILEICON_NAME_LIST = {
		"profileicon1", "profileicon2",
		"profileicon3", "profileicon4",
		"profileicon5", "profileicon6",
		"profileicon7", "profileicon8",
		"profileicon9", "profileicon10",
		"profileicon11", "profileicon12",
		"profileicon13", "profileicon14",
		"profileicon15", "profileicon16",
		"profileicon17", "profileicon18",
		"profileicon19", "profileicon20",
		"profileicon21", "profileicon22",
		"profileicon23", "profileicon24",
		"profileicon25", "profileicon26",
		"profileicon27", "profileicon28",
		"profileicon29", "profileicon30",
		"profileicon31", "profileicon32",
		"profileicon33", "profileicon34",
		"profileicon35", "profileicon36"
	};
	
	static {
		for (int idx = 0;idx < PROFILEICON_RESOURCES_LIST.length;idx++)
		{
			PROFILEICON_RESOURCESNAME_LIST[idx] = String.valueOf(PROFILEICON_RESOURCES_LIST[idx]);
		}
	}
	
	
}
