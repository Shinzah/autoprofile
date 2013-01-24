/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 17 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: BatteryCondition.java
 *
 * Comments :
 **/

package com.shinsoft.autoprofile.conditions;

import java.util.StringTokenizer;

import android.app.Activity;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.profile.ProfileActivatorUpdater;

/**
 * BatteryCondition class
 * Field conditions composition :
 * 		isInCharge|percent|higher/lower
 * 		Ex: 1|50|+
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class BatteryCondition extends ProfileCondition
{
	private static final long serialVersionUID = -6097082184802332597L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	public static final int CLASSID = 3;
	
	public static final int DEFAULT_BATTERYLEVEL = 50;
	public static final String BATTERY_HIGHER = "+";
	public static final String BATTERY_LOWER = "-";
	
	
	// Class properties
	private int percent;
	private boolean isInCharge;
	private String higherLower;
	
	/**
	 * Empty Constructor
	 * @since 1.0
	 */
	public BatteryCondition()
	{
		this.percent = DEFAULT_BATTERYLEVEL;
		this.isInCharge = false;
		this.higherLower = BATTERY_LOWER;
	}
	
	/**
	 * Minimal Constructor (New condition creation)
	 * @param profileid
	 * @since 1.0
	 */
	public BatteryCondition(int profileid)
	{
		this.profileid = profileid;
		this.percent = DEFAULT_BATTERYLEVEL;
		this.isInCharge = false;
		this.higherLower = BATTERY_LOWER;
	}
	
	/**
	 * Full Constructor (Edition, initialization)
	 * @param id
	 * @param profileid
	 * @param conditions
	 * @since 1.0
	 */
	public BatteryCondition(int id, int profileid, String conditions)
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
		return R.string.ConditionName_BatteryCondition;
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getConditionsSummary(android.app.Activity)
	 * @since 1.0
	 */
	public String getConditionsSummary(Activity context)
	{
		if (this.summary == null || this.summary.trim().length() == 0)
		{
			if (this.isInCharge)
				this.summary = context.getResources().getString(R.string.ConditionSummary_BatteryCondition_InCharge);
			else
			{
				String strToUse = null;
				if (BATTERY_HIGHER.equalsIgnoreCase(this.higherLower))
					strToUse = context.getResources().getString(R.string.ConditionSummary_BatteryCondition_PercentHigher);
				else
					strToUse = context.getResources().getString(R.string.ConditionSummary_BatteryCondition_PercentLower);
				this.summary = String.format(strToUse,String.valueOf(this.percent));
			}
		}
		
		return this.summary;
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#buildConditions()
	 * @since 1.0
	 */
	public void buildConditions()
	{
		StringBuilder conditionsSb = new StringBuilder();
		conditionsSb.append(this.isInCharge?"1":"0").append(ProfileCondition.CONDITIONS_SEPARATOR).append(this.percent).append(ProfileCondition.CONDITIONS_SEPARATOR).append(this.higherLower);
		this.conditions = conditionsSb.toString();
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#getIconResourceId()
	 * @since 1.0
	 */
	public int getIconResourceId()
	{
		return R.drawable.conditionicon_battery;
	}

	/**
	 * @see com.shinsoft.autoprofile.conditions.utils.ConditionInterface#parseConditions()
	 * @since 1.0
	 */
	public void parseConditions()
	{
		try
		{
			// DateCondition conditions must be like 1|50 (String|int)
			StringTokenizer condTokenized = new StringTokenizer(this.conditions,ProfileCondition.CONDITIONS_SEPARATOR);
			if (condTokenized.countTokens() == 3)
			{
				// Filling isInCharge
				this.isInCharge = ("1".equals(condTokenized.nextToken()));
				// Filling percent
				this.percent = Integer.parseInt(condTokenized.nextToken());
				// Filling higher/lower
				this.higherLower = condTokenized.nextToken();
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
		if (
				(
					!this.isInCharge 
					&& !profileActivatorUpdater.isBatteryInCharge()
				)
				// Case #1 : Higher than 50% (currentLevel = 60%)
				&& (
						(
							BatteryCondition.BATTERY_HIGHER.equalsIgnoreCase(this.getHigherLower())
							&& profileActivatorUpdater.getBatteryLevel() >= this.getPercent()
						)
						// Case #2 : Lower than 50% (currentLevel = 40%)
						||
						(
							BatteryCondition.BATTERY_LOWER.equalsIgnoreCase(this.getHigherLower())
							&& profileActivatorUpdater.getBatteryLevel() <= this.getPercent()		
						)
					)
			)
			return true;
		else if (this.isInCharge && profileActivatorUpdater.isBatteryInCharge())
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
		return Constants.EVENT_BATTERY;
	}
	
	public int getPercent()
	{
		return percent;
	}
	public void setPercent(int percent)
	{
		this.percent = percent;
		this.summary = null;
	}
	public boolean isInCharge()
	{
		return isInCharge;
	}
	public void setInCharge(boolean isInCharge)
	{
		this.isInCharge = isInCharge;
		this.summary = null;
	}

	public String getHigherLower() {
		return higherLower;
	}

	public void setHigherLower(String higherLower) {
		this.higherLower = higherLower;
		this.summary = null;
	}
}