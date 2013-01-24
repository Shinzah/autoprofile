/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 25 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager.receiver
 * File name: ProfileActivatedReceiver.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;

public class ProfileActivatedReceiver extends BroadcastReceiver
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	private boolean debugMode;
	private AutoProfile autoProfile;
	
	public ProfileActivatedReceiver(AutoProfile autoProfile,boolean debugMode)
	{
		this.debugMode = debugMode;
		this.autoProfile = autoProfile;
	}
	
	@Override
	public void onReceive(Context context,Intent intent)
	{
		if (debugMode)
			Log.d(TAG,"Action received from AutoProfileService, notifying activity of change !");
		
		autoProfile.refreshListView();
	}

}



