/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 16 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager.content
 * File name: ContentManager.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.manager.content;

import android.content.Context;

import com.shinsoft.autoprofile.AutoProfile;

public final class ContentManager
{
	private static final String TAG = AutoProfile.class.getSimpleName();

	private static ProfileManager profileManager;

	/**
	 * Return the instance of profileManager
	 * @param context
	 * @return
	 * @since 1.0
	 */
	public static ProfileManager getProfileManager(Context context)
	{
		if (profileManager == null)
			profileManager = new ProfileManager(context).open();
		else if (!profileManager.isOpen())
			profileManager.open();
		
		return profileManager;
	}
	
}



