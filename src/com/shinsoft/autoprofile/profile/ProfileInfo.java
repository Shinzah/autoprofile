package com.shinsoft.autoprofile.profile;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.conditions.BatteryCondition;
import com.shinsoft.autoprofile.conditions.DateCondition;
import com.shinsoft.autoprofile.conditions.TimeCondition;
import com.shinsoft.autoprofile.conditions.utils.ConditionsManager;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.parameter.utils.ParametersManager;
import com.shinsoft.autoprofile.parameter.utils.ProfileParameter;


/**
 * Bean for Profile Informations
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class ProfileInfo extends Profile implements Serializable
{
	// Class constants
	private static final long serialVersionUID = 6770835409518539080L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Class others attributes
	private List<ProfileCondition> listConditions;
	private List<Class<?>> listAvailableConditions;
	private List<ProfileParameter> listParameters;
	private List<Class<?>> listAvailableParameters;
	private boolean bModified;
	private int iconResId;
	private boolean isInUse;
	
	/**
	 * Full Constructor
	 * @param id
	 * @param name
	 * @param icon
	 * @param prio
	 * @param activate
	 * @since 1.0
	 */
	public ProfileInfo(int id, String name, String icon, int prio, boolean activate)
	{
		super(id,name,icon,prio,activate);
		this.initInfo();
	}
	
	/**
	 * Empty Constructor
	 * @since 1.0
	 */
	public ProfileInfo()
	{
		super();
		this.initInfo();
	}
	
	public Profile getProfile()
	{
		return new Profile(id,name,icon,prio,activate);
	}
	
	/**
	 * Initialize default values of info
	 * @since 1.0
	 */
	private void initInfo()
	{
		this.listAvailableConditions = ConditionsManager.listAllAvailableConditions();
		this.listAvailableParameters = ParametersManager.listAllAvailableParameters();
		this.listConditions = new ArrayList<ProfileCondition>();
		this.listParameters = new ArrayList<ProfileParameter>();
	}
	
	
	public boolean checkAndUpdateActivator(ProfileActivatorUpdater profileActivatorUpdater)
	{
		if (this.isActivate())
		{
			boolean bCanBeUsed = true;
			// Update each condition in profileActivator
			for (ProfileCondition currentCond : this.listConditions)
			{
				if (!currentCond.isMatching(profileActivatorUpdater))
				{
					bCanBeUsed = false;
					break;
				}
			}
			this.isInUse = bCanBeUsed;
			return bCanBeUsed;
		}
		else
		{
			this.isInUse = false;
			return false;
		}
	}
	
	/**
	 * Return a list of all events id for profile conditions
	 * @return
	 * @since 1.0
	 */
	public List<Integer> getEvents()
	{
		List<Integer> listEvents = new ArrayList<Integer>();
		for (ProfileCondition currentCond : this.listConditions)
		{
			listEvents.add(currentCond.getEventId());
		}
		return listEvents;
	}
	
	
	
	
	/**
	 * Add a new condition in conditionsList, and remove condition class from available condition classes
	 * @param condition
	 * @since 1.0
	 */
	public void addCondition(ProfileCondition condition)
	{
		if (this.listAvailableConditions.contains(condition.getClass()));
			this.listAvailableConditions.remove(condition.getClass());
		
		this.listConditions.add(condition);
	}
	/**
	 * Add a new parameter in parametersList, and remove condition class from available parameter classes
	 * @param condition
	 * @since 1.0
	 */
	public void addParameter(ProfileParameter parameter)
	{
		if (this.listAvailableParameters.contains(parameter.getClass()));
			this.listAvailableParameters.remove(parameter.getClass());
		
		this.listParameters.add(parameter);
	}
	
	/**
	 * Replace an existing condition into conditionsList
	 * @param condition
	 * @since 1.0
	 */
	public void replaceCondition(ProfileCondition condition)
	{
		if (this.listConditions != null && this.listConditions.contains(condition))
			this.listConditions.set(this.listConditions.indexOf(condition),condition);
	}
	/**
	 * Replace an existing parameter into parametersList
	 * @param condition
	 * @since 1.0
	 */
	public void replaceParameter(ProfileParameter parameter)
	{
		if (this.listParameters != null && this.listParameters.contains(parameter))
			this.listParameters.set(this.listParameters.indexOf(parameter),parameter);
	}
	
	/**
	 * Remove specified condition from consitionsList and add condition class into available condition classes
	 * @param condition
	 * @since 1.0
	 */
	public void removeCondition(ProfileCondition condition)
	{
		if (this.listConditions != null && this.listConditions.contains(condition))
		{
			this.listAvailableConditions.add(condition.getClass());
			this.listConditions.remove(condition);
		}
	}
	/**
	 * Remove specified parameter from parameterList and add parameter class into available parameter classes
	 * @param condition
	 * @since 1.0
	 */
	public void removeParameter(ProfileParameter parameter)
	{
		if (this.listParameters != null && this.listParameters.contains(parameter))
		{
			this.listAvailableParameters.add(parameter.getClass());
			this.listParameters.remove(parameter);
		}
	}
	
	/**
	 * Build all conditions summary
	 * @param context
	 * @return
	 * @since 1.0
	 */
	public String buildConditionsSummary(Activity context)
	{
		StringBuilder ret = new StringBuilder();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		if (this.getListConditions() != null && this.getListConditions().size() > 0)
		{
			for (ProfileCondition cond : this.getListConditions())
			{
				// Display 'time' ?
				if (cond instanceof TimeCondition && prefs.getBoolean(context.getResources().getString(R.string.PreferenceItemKey_ListProfiles_DisplayTime),true))
				{
					if (ret.length() > 0) ret.append("\n");
					ret.append(cond.getConditionsSummary(context));
				}
				// Display 'date' ?
				if (cond instanceof DateCondition && prefs.getBoolean(context.getResources().getString(R.string.PreferenceItemKey_ListProfiles_DisplayDate),true))
				{
					if (ret.length() > 0) ret.append("\n");
					ret.append(cond.getConditionsSummary(context));
				}
				// Displat 'power' ?
				if (cond instanceof BatteryCondition && prefs.getBoolean(context.getResources().getString(R.string.PreferenceItemKey_ListProfiles_DisplayBattery),true))
				{
					if (ret.length() > 0) ret.append("\n");
					ret.append(cond.getConditionsSummary(context));
				}
			}
		}
		
		return ret.toString();
	}
	
	/**
	 * Return the ressource id for profileinfo icon
	 * @return
	 * @since 1.0
	 */
	public int getIconResourceId()
	{
		try
		{
			if (iconResId == 0)
			{
				Field field = R.drawable.class.getField(this.getIcon()) ;
				return field.getInt(R.drawable.class);
			}
			else
				return iconResId;
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while computing resource id for icon <!>",e);
		}
		return R.drawable.profileicon1;
	}
	
	
	/**
	 * Override super method to make a profileInfo equals to another depending of his profile id
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ProfileInfo)
		{
			ProfileInfo p0 = (ProfileInfo)o;
			if (p0.getId() == this.getId())
				return true;
			else
				return false;
		}
		else
			return super.equals(o);
	}
	

	public boolean isbModified()
	{
		return bModified;
	}

	public void setbModified(boolean bModified)
	{
		this.bModified = bModified;
	}

	public List<Class<?>> getListAvailableConditions()
	{
		return listAvailableConditions;
	}

	public List<Class<?>> getListAvailableParameters()
	{
		return listAvailableParameters;
	}
	public List<ProfileCondition> getListConditions() 
	{
		return listConditions;
	}
	public List<ProfileParameter> getListParameters() 
	{
		return listParameters;
	}

	public boolean isInUse()
	{
		return isInUse;
	}

	public void setInUse(boolean isInUse)
	{
		this.isInUse = isInUse;
	}
	
}
