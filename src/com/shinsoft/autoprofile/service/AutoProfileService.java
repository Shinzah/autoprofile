/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 18 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.service
 * File name: AutoProfileService.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.manager.content.ContentManager;
import com.shinsoft.autoprofile.parameter.utils.ParameterProcessor;
import com.shinsoft.autoprofile.profile.ProfileActivatorUpdater;
import com.shinsoft.autoprofile.profile.ProfileInfo;
import com.shinsoft.autoprofile.service.receiver.AutoProfileReceiver;

/**
 * Main service of AutoProfile application
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class AutoProfileService extends Service
{
	private static final String TAG = AutoProfileService.class.getSimpleName();
	public static final String EVENT_ACTION 		= "AutoProfileServiceType";
	
	private static ProfileActivatorUpdater profileUpdater = new ProfileActivatorUpdater(Calendar.getInstance(), -1/*batteryLevel*/, false/*batteryInCharge*/);
	private static Notification notification;
	
	//Task
	private Timer timerDateAndTime;
	private List<Integer> listProfileIdInUsed;
	
	private BroadcastReceiver receiver;
	private boolean debugMode;
	
	/**
	 * On service creation, set it in foreground with notification, and initialize activators
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate()
	{
		debugMode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.PreferenceItemKey_DebugMode),false);
		
		// Display notification and set service to foreground
		CharSequence tickerText 		= getResources().getString(R.string.Notif_Started);
		notification			 		= new Notification(R.drawable.icon, tickerText, 0);
		CharSequence contentTitle 		= getResources().getString(R.string.app_name);
		CharSequence contentText 		= getResources().getString(R.string.Notif_IsWatchingForConditions);
		Intent notificationIntent 		= new Intent(this, AutoProfile.class);
		PendingIntent contentIntent 	= PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(getApplicationContext(), contentTitle, contentText, contentIntent);
		startForeground(R.id.ServiceNotification,notification);
		
		this.updateProfileToUse();
		super.onCreate();
	}

	/**
	 * Force the start command to be sticky
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	
	/**
	 * On destroy, stop service foreground and unregistrer receivers
	 * @see android.app.Service#onDestroy()
	 * @since 1.0
	 */
	@Override
	public void onDestroy()
	{
		if (debugMode)
			Log.d(TAG,"Service shutting down...");
		
		if (this.receiver != null)
		{
			unregisterReceiver(this.receiver);
			this.receiver = null;
		}
		
		stopForeground(true/*removeNotification*/);
		super.onDestroy();
	}	
	
	/**
	 * On Bind, return a AutoProfileBinder object
	 * @see android.app.Service#onBind(android.content.Intent)
	 * @since 1.0
	 */
	@Override
	public IBinder onBind(Intent arg0)
	{
		return new AutoProfileBinder();
	}
	
	/**
	 * Binder class for current Service
	 * <br>
	 * Versions : <br>
	 *
	 *   <li>1.0 : Initialisation</li>
	 * <br>
	 * @version 1.0
	 */
	public class AutoProfileBinder extends Binder
	{
		AutoProfileService getService()
		{
			return AutoProfileService.this;
		}
	}
	
	
	
	public void updateProfileToUse()
	{
		if (debugMode)
			Log.d(TAG,"Updating profile to use...");
		List<Integer> listEvent = new ArrayList<Integer>();
		List<String> listActionsForReceiver = new ArrayList<String>();
		List<ProfileInfo> listProfileActivable = new ArrayList<ProfileInfo>();
		listActionsForReceiver.add(Constants.ACTION_PROFILE_UPDATED);
		
		// Retrieve all profile
		List<ProfileInfo> allProfile = ContentManager.getProfileManager(getApplicationContext()).getAllProfile();
		
		// Remove previous receiver
		if (this.receiver != null)
		{
			unregisterReceiver(this.receiver);
			this.receiver = null;
			
			if (debugMode)
				Log.d(TAG,"Previous receiver unregistered");
		}
		
		// Compute events to use in receiver
		for (ProfileInfo currentProfile : allProfile)
		{
			// Only treat profile not in modification
			if (!currentProfile.isbModified())
			{
				// Check for profile activator
				if (currentProfile.checkAndUpdateActivator(profileUpdater))
				{
					listProfileActivable.add(currentProfile);
				
					// Retrieve all events for current profile
					for (Integer currentEvent : currentProfile.getEvents())
					{
						if (!listEvent.contains(currentEvent))
							listEvent.add(currentEvent);
					}
				}
			}
		}
		
		if (debugMode)
			Log.d(TAG,listEvent.size()+" actions to treat");
		
		// Prepare Actions list to use in receiver
		for (Integer currentEvent : listEvent)
		{
			// Simple time event, so create a daemon task each minute
			if (currentEvent.intValue() == Constants.EVENT_TIME)
			{
				if (debugMode)
					Log.d(TAG,"Launching time timer");
				
				this.timerDateAndTime = new Timer(true/*isDaemon*/);
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						if (debugMode)
							Log.d(TAG,"Updating time");
						profileUpdater.updateDateAndTime(Calendar.getInstance());
						updateActivators();
					}
				};
				this.timerDateAndTime.schedule(task, 60000/*delay 1minute*/, 60000/*period*/); // Launch each minute
			}
			// If the event is a date one, we have to check if there is no time event in the list, because time event is used for date event
			// else, we have to create a daemon task each hour (avoid battery useless leak)
			else if (currentEvent.intValue() == Constants.EVENT_DATE 
					&& !listEvent.contains(Constants.EVENT_TIME)
					&& timerDateAndTime == null)
			{
				if (debugMode)
					Log.d(TAG,"Launching date timer");
				
				this.timerDateAndTime = new Timer(true/*isDaemon*/);
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						if (debugMode)
							Log.d(TAG,"Updating date");
						profileUpdater.updateDateAndTime(Calendar.getInstance());
						updateActivators();
					}
				};
				this.timerDateAndTime.schedule(task, 600000/*delay*/, 600000/*period*/); // Launch each hour
			}
			// For a battery event, we have to register a new battery broadcastreceiver
			else if (currentEvent.intValue() == Constants.EVENT_BATTERY)
			{
				if (debugMode)
					Log.d(TAG,"Adding intention for battery");
				
				listActionsForReceiver.add(Intent.ACTION_BATTERY_CHANGED);
			}
		}
		
		// Create new receiver
		if (!listActionsForReceiver.isEmpty())
		{
			if (debugMode)
				Log.d(TAG,"Registering a new receiver for "+listActionsForReceiver.size()+" actions");
			
			this.receiver = new AutoProfileReceiver(this,profileUpdater);
			
			// Now register broadcastReceiver
			IntentFilter intentFilter = new IntentFilter();
			for (String currentAction : listActionsForReceiver)
			{
				intentFilter.addAction(currentAction);
			}
			
			registerReceiver(receiver, intentFilter);
			
			if (debugMode)
				Log.d(TAG,"New receiver registered");
		}
		
		// Updating activators
		this.updateActivators();
	}
	
	
	/**
	 * Update all profile activators and apply parameters if change made
	 * @param listProfileActivable
	 * @since 1.0
	 */
	public void updateActivators()
	{
		if (debugMode) 
			Log.d(TAG,"Updating profiles activators");
		
		List<Integer> listProfileIdActivable = new ArrayList<Integer>();
		boolean changeMade = false;
		
		// If listprofileActivable is null, it mean the receiver as received a new event, so retrieve all values and compute
		List<ProfileInfo> allProfile = ContentManager.getProfileManager(getApplicationContext()).getAllProfile();
		List<ProfileInfo> listProfileActivable = new ArrayList<ProfileInfo>();
		// Compute events to use in receiver
		for (ProfileInfo currentProfile : allProfile)
		{
			// Only treat profile not in modification and activated
			if (!currentProfile.isbModified())
			{
				// Retrieve profile activable
				if (currentProfile.checkAndUpdateActivator(profileUpdater))
				{
					listProfileActivable.add(currentProfile);
					listProfileIdActivable.add(currentProfile.getId());	
				}
			}
		}
		
		// Check there is any change
		if (this.listProfileIdInUsed != null && !this.listProfileIdInUsed.isEmpty() 
						&& listProfileActivable != null && !listProfileActivable.isEmpty())
		{
			if (debugMode)
				Log.d(TAG,"Found "+(listProfileIdActivable!=null?listProfileIdActivable.size():"0")+" profiles activable");
			
			// Order each list to compare them
			Collections.sort(listProfileIdActivable);
			Collections.sort(this.listProfileIdInUsed);
			
			if (!this.listProfileIdInUsed.equals(listProfileIdActivable))
			{
				changeMade = true;
				if (debugMode)
					Log.d(TAG,"Change detected ("+this.listProfileIdInUsed.toString()+" - "+listProfileIdActivable.toString()+")");	
			}
			else if (debugMode)
				Log.d(TAG,"No change detected");	
				
		}
		else
		{
			if (debugMode)
				Log.d(TAG,"No previous listProfileIdInUse");
			changeMade = true;
		}
		
		
		// If change made
		if (changeMade)
		{
			if (debugMode)
				Log.d(TAG,"Profile change detected, processing ...");
			
			// Update "in use" profile id list
			this.listProfileIdInUsed = new ArrayList<Integer>();
			this.listProfileIdInUsed.addAll(listProfileIdActivable);

			// Notify activity of change made
	    	Intent intent = new Intent(Constants.ACTION_PROFILEACTIVATED_UPDATED);
	    	sendBroadcast(intent);

	    	// Proceed parameter change
	    	ParameterProcessor.processParameters(listProfileActivable);
	    	
			// Update notification text
			if (debugMode)
				Log.d(TAG,listProfileActivable.size()+" profile activable :");
			
			String contentTitle = getResources().getString(R.string.app_name).toString();
			StringBuilder contentText = new StringBuilder();
			for (ProfileInfo currentProfile : listProfileActivable)
			{
				if (debugMode)
					Log.d(TAG,"    -> Activating "+currentProfile.getName());
				
				if (contentText.length() > 0)
					contentText.append(", ");
				contentText.append(currentProfile.getName());
			}
			Intent notificationIntent 		= new Intent(this, AutoProfile.class);
			PendingIntent contentIntent 	= PendingIntent.getActivity(this, 0, notificationIntent, 0);
			notification.setLatestEventInfo(getApplicationContext(), contentTitle, contentText.toString(), contentIntent);
			notification.when = System.currentTimeMillis();
			((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(R.id.ServiceNotification,notification);
		}
	}
}