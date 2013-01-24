/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager
 * File name: ConditionsLister.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.conditions.utils;

import java.util.ArrayList;
import java.util.List;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.conditions.BatteryCondition;
import com.shinsoft.autoprofile.conditions.DateCondition;
import com.shinsoft.autoprofile.conditions.TimeCondition;

public class ConditionsManager
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	/**
	 * Return the list of all conditions class existing
	 * @return
	 * @since 1.0
	 */
	public static List<Class<?>> listAllAvailableConditions()
	{
		List<Class<?>> listRet = new ArrayList<Class<?>>();
		listRet.add(TimeCondition.class);
		listRet.add(DateCondition.class);
		listRet.add(BatteryCondition.class);
		
		return listRet;
	}
	
	/**
	 * Return the object class for the specified classID
	 * @param classID
	 * @return
	 * @since 1.0
	 */
	public static Class<?> getClassForClassID(int classID)
	{
		switch (classID)
		{
			case TimeCondition.CLASSID :
				return TimeCondition.class;
			case DateCondition.CLASSID :
				return DateCondition.class;
			case BatteryCondition.CLASSID :
				return BatteryCondition.class;
		}
		return null;
	}
	
}