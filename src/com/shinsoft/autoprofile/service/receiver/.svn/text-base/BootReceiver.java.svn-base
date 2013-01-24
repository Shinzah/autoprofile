/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 18 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.service.receiver
 * File name: BootReceiver.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.service.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.service.AutoProfileService;

/**
 * BOOT_COMPLETED receiver to launch service
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class BootReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.PreferenceItem_AutoLaunchService),true))
		{
			// On receive android.intent.action.BOOT_COMPLETED start the service
			 context.startService(new Intent().setComponent(new ComponentName(context.getPackageName(), AutoProfileService.class.getName())));
		}
	}

}



