/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 22 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.service.receiver
 * File name: AutoProfileReceiver.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.service.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.profile.ProfileActivatorUpdater;
import com.shinsoft.autoprofile.service.AutoProfileService;


/**
 * Receiver class for profile activator
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class AutoProfileReceiver extends android.content.BroadcastReceiver
{
	private static final String TAG = AutoProfileService.class.getSimpleName();
	
	private AutoProfileService service;
	private ProfileActivatorUpdater profileUpdater;
	private boolean debugMode;
	
	/**
	 * Full Constructor
	 * @param service
	 * @param profileUpdater
	 * @since 1.0
	 */
	public AutoProfileReceiver(AutoProfileService service, ProfileActivatorUpdater profileUpdater)
	{
		this.service = service;
		this.profileUpdater = profileUpdater;
		this.debugMode = PreferenceManager.getDefaultSharedPreferences(service).getBoolean(service.getResources().getString(R.string.PreferenceItemKey_DebugMode),false);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String actionReceived = null;
		if (intent != null)
			actionReceived = intent.getAction();
		
		if (debugMode)
			Log.d(TAG,"Broadcast message received (ActionReceived="+actionReceived+")");
		
		// Switch between all available action to make the right update
		
		// Case Profile update
		if (Constants.ACTION_PROFILE_UPDATED.equals(actionReceived))
		{
			this.service.updateProfileToUse();
		}
		// Case for battery
		if (Intent.ACTION_BATTERY_CHANGED.equals(actionReceived))
		{
			int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
	        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
	        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_DISCHARGING);

	        if (status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL)
	        	profileUpdater.updateBatteryInCharge(true);
	        else
	        	profileUpdater.updateBatteryInCharge(false);
	        
		    int level = -1;
		    if (rawlevel >= 0 && scale > 0) 
		        level = (rawlevel * 100) / scale;
		    
		    if (debugMode)
				Log.d(TAG,"Battery intention received for level "+level + " ("+(profileUpdater.isBatteryInCharge()?"Charging":"Discharging")+")");
		    
		    profileUpdater.updateBatteryLevel(level);
		    this.service.updateActivators();
		}
		
	}
}



