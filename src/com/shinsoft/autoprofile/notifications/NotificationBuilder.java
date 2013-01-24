/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 18 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager
 * File name: NotificationBuilder.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.notifications;

import java.io.Serializable;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;

public class NotificationBuilder implements Serializable
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	private static final long serialVersionUID = -6542363401829745014L;

	private static final int NOTIFICATION_ID_DELETEPROFILE = 1;
	private static final int NOTIFICATION_ID_SAVEDPROFILE = 1;
	
	public static NotificationManager mNotificationManager;
	
	/**
	 * Display a notification message for the profile suppression
	 * @param context
	 * @param profileName
	 * @since 1.0
	 */
	public static void notifyProfileDeleted(Context context, String profileName)
	{
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		String message = context.getResources().getString(R.string.ListProfiles_ProfileDeletedToastMsg,new Object[]{ profileName });
		CharSequence tickerText 		= message;
		Notification notification 		= new Notification(R.drawable.icon_red, tickerText, System.currentTimeMillis());
		CharSequence contentTitle 		= context.getResources().getString(R.string.app_name);
		CharSequence contentText 		= message;
		Intent notificationIntent 		= new Intent(context, AutoProfile.class);
		PendingIntent contentIntent 	= PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context.getApplicationContext(), contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID_DELETEPROFILE, notification);
	}
	
	
	/**
	 * Display a notification message for the profile save
	 * @param context
	 * @param profileName
	 * @since 1.0
	 */
	public static void notifyProfileSaved(Context context, String profileName)
	{
		if (mNotificationManager == null)
			mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	
		String message = context.getResources().getString(R.string.ListProfiles_ProfileSavedToastMsg,new Object[]{ profileName });
		CharSequence tickerText 		= message;
		Notification notification 		= new Notification(R.drawable.icon_green, tickerText, System.currentTimeMillis());
		CharSequence contentTitle 		= context.getResources().getString(R.string.app_name);
		CharSequence contentText 		= message;
		Intent notificationIntent 		= new Intent(context, AutoProfile.class);
		PendingIntent contentIntent 	= PendingIntent.getActivity(context, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context.getApplicationContext(), contentTitle, contentText, contentIntent);
		mNotificationManager.notify(NOTIFICATION_ID_SAVEDPROFILE, notification);
	}
	
	
}



