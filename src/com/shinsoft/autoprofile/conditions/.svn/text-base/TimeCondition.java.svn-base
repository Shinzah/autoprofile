/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 15 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: TimeCondition.java
 *
 * Comments :
 **/
package com.shinsoft.autoprofile.conditions;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

import android.app.Activity;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.profile.ProfileActivatorUpdater;

/**
 * TimeCondition class
 * - Field conditions composition :
 * 		fromTime|toTime
 * 		Ex: 15487814874|8781848781
 * 
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class TimeCondition extends ProfileCondition
{
	private static final long serialVersionUID = 1117129346604858841L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	public static final int CLASSID = 1;
	
	// Class properties
	private Time fromTime;
	private Time toTime;
	
	/**
	 * Empty Constructor
	 * @since 1.0
	 */
	public TimeCondition()
	{
		this.fromTime = new Time(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0);
		Calendar calToTime = Calendar.getInstance();
		calToTime.add(Calendar.HOUR_OF_DAY, 1);
		this.toTime = new Time(calToTime.get(Calendar.HOUR_OF_DAY),0,0);
	}
	
	/**
	 * Minimal Constructor (New condition creation)
	 * @param profileid
	 * @since 1.0
	 */
	public TimeCondition(int profileid) 
	{
		this.profileid = profileid;
		this.fromTime = new Time(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),0,0);
		Calendar calToTime = Calendar.getInstance();
		calToTime.add(Calendar.HOUR_OF_DAY, 1);
		this.toTime = new Time(calToTime.get(Calendar.HOUR_OF_DAY),0,0);
	}
	
	/**
	 * Full Constructor (Edition, initialization)
	 * @param id
	 * @param profileid
	 * @param conditions
	 * @since 1.0
	 */
	public TimeCondition(int id, int profileid, String conditions)
	{
		this.id = id;
		this.profileid = profileid;
		this.conditions = conditions;
		this.parseConditions();
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#buildConditions()
	 * @since 1.0
	 */
	public void buildConditions()
	{
		StringBuilder conditionsSb = new StringBuilder();
		conditionsSb.append(this.fromTime.getTime()).append(ProfileCondition.CONDITIONS_SEPARATOR).append(this.toTime.getTime());
		this.conditions = conditionsSb.toString();
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getClassId()
	 * @since 1.0
	 */
	public int getClassId()
	{
		return CLASSID;
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getNameId()
	 * @since 1.0
	 */
	public int getNameId()
	{
		return R.string.ConditionName_TimeCondition;
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getConditionsSummary(android.app.Activity)
	 * @since 1.0
	 */
	public String getConditionsSummary(Activity context)
	{
		if (this.summary == null || this.summary.trim().length() == 0)
			this.summary = String.format(context.getResources().getString(R.string.ConditionSummary_TimeCondition),DateFormat.getTimeInstance(DateFormat.SHORT,Locale.getDefault()).format(this.getFromTime().getTime()),DateFormat.getTimeInstance(DateFormat.SHORT,Locale.getDefault()).format(this.getToTime().getTime()));
		
		return this.summary;
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getIconResourceId()
	 * @since 1.0
	 */
	public int getIconResourceId()
	{
		return R.drawable.conditionicon_time;
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#parseConditions()
	 * @since 1.0
	 */
	public void parseConditions()
	{
		try
		{
			// TimeCondition conditions must be like 17845810245|187945115 (Long|Long)
			StringTokenizer condTokenized = new StringTokenizer(this.conditions,ProfileCondition.CONDITIONS_SEPARATOR);
			if (condTokenized.countTokens() == 2)
			{
				// Filling fromTime
				this.fromTime = new Time(Long.parseLong(condTokenized.nextToken()));
				// Filling toTime
				this.toTime = new Time(Long.parseLong(condTokenized.nextToken()));
			}
		}
		catch (Exception e)
		{
			Log.e(TAG,"<!> An error append while parsing conditions <!>",e);
		}
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#isMatching(com.shinsoft.autoprofile.profile.ProfileActivator, com.shinsoft.autoprofile.profile.ProfileActivatorUpdater)
	 * @since 1.0
	 */
	public boolean isMatching(ProfileActivatorUpdater profileActivatorUpdater)
	{
		Time currentTime = new Time(profileActivatorUpdater.getDateAndTime().get(Calendar.HOUR_OF_DAY),profileActivatorUpdater.getDateAndTime().get(Calendar.MINUTE),0/*seconds*/);
		// Case #1 : From 06h to 14h
		if (
						this.getFromTime().before(this.getToTime())
						&& currentTime.after(this.getFromTime()) && currentTime.before(this.getToTime())
			)
			return true;
		// Case #2 : From 22h to 07h
		else if (
						this.getToTime().before(this.getFromTime())
						&& 
						(currentTime.after(this.getFromTime()) || currentTime.before(this.getToTime()))
				)
			return true;
		else
			return false;
	}
	
	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getEventId()
	 * @since 1.0
	 */
	public int getEventId()
	{
		return Constants.EVENT_TIME;
	}
	
	public Time getFromTime()
	{
		return fromTime;
	}
	public void setFromTime(Time fromTime)
	{
		this.fromTime = fromTime;
		this.summary = null;
	}
	public Time getToTime()
	{
		return toTime;
	}
	public void setToTime(Time toTime)
	{
		this.toTime = toTime;
		this.summary = null;
	}
}
