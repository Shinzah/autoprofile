/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 15 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: DateCondition.java
 *
 * Comments :
 **/
package com.shinsoft.autoprofile.conditions;

import java.sql.Date;
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
 * DateCondition class
 * - Field conditions composition :
 * 		fromDate|toDate
 * 		Ex: 15487814874|8781848781
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class DateCondition extends ProfileCondition
{
	private static final long serialVersionUID = -3161716819868514485L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	public static final int CLASSID = 2;
	
	// Class properties
	private Date fromDate;
	private Date toDate;
	
	/**
	 * Empty Constructor
	 * @since 1.0
	 */
	public DateCondition()
	{
		this.fromDate = new Date(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		Calendar toCal = Calendar.getInstance();
		toCal.add(Calendar.DAY_OF_MONTH,1);
		this.toDate = new Date(toCal.get(Calendar.YEAR)-1900,toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Minimal Constructor (New condition creation)
	 * @param profileid
	 * @since 1.0
	 */
	public DateCondition(int profileid)
	{
		this.profileid = profileid;
		this.fromDate = new Date(Calendar.getInstance().get(Calendar.YEAR)-1900,Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		Calendar toCal = Calendar.getInstance();
		toCal.add(Calendar.DAY_OF_MONTH,1);
		this.toDate = new Date(toCal.get(Calendar.YEAR)-1900,toCal.get(Calendar.MONTH),toCal.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Full Constructor (Edition, initialization)
	 * @param id
	 * @param profileid
	 * @param conditions
	 * @since 1.0
	 */
	public DateCondition(int id, int profileid, String conditions)
	{
		this.id = id;
		this.profileid = profileid;
		this.conditions = conditions;
		this.parseConditions();
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
		return R.string.ConditionName_DateCondition;
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getConditionsSummary(android.app.Activity)
	 * @since 1.0
	 */
	public String getConditionsSummary(Activity context)
	{
		if (this.summary == null || this.summary.trim().length() == 0)
			this.summary = String.format(context.getResources().getString(R.string.ConditionSummary_DateCondition),DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault()).format(this.getFromDate().getTime()),DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault()).format(this.getToDate().getTime()));
		
		return this.summary;
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#buildConditions()
	 * @since 1.0
	 */
	public void buildConditions()
	{
		StringBuilder conditionsSb = new StringBuilder();
		conditionsSb.append(this.fromDate.toString()).append(ProfileCondition.CONDITIONS_SEPARATOR).append(this.toDate.toString());
		this.conditions = conditionsSb.toString();
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getIconResourceId()
	 * @since 1.0
	 */
	public int getIconResourceId()
	{
		return R.drawable.conditionicon_date;
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
				this.fromDate = Date.valueOf(condTokenized.nextToken());
				// Filling toTime
				this.toDate = Date.valueOf(condTokenized.nextToken());
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
		java.sql.Date currentDate = new java.sql.Date(profileActivatorUpdater.getDateAndTime().getTime().getTime());
		
		// Case #1 : From 01/01/10 to 01/02/10 equals date (currentDate= 01/01/10)
		if (	currentDate.compareTo(this.getFromDate()) == 0
						|| currentDate.compareTo(this.getToDate()) == 0
			)
			return true;
		// Case #2 : From 01/01/10 to 01/02/10 (currentDate= 05/01/10)
		else if (	currentDate.after(this.getFromDate())
						&& currentDate.before(this.getToDate())
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
		return Constants.EVENT_DATE;
	}
	
	
	public Date getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
		this.summary = null;
	}
	public Date getToDate()
	{
		return toDate;
	}
	public void setToDate(Date toDate)
	{
		this.toDate = toDate;
		this.summary = null;
	}
}
