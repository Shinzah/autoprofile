/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: ConditionFactory.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.conditions.utils;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.conditions.BatteryCondition;
import com.shinsoft.autoprofile.conditions.DateCondition;
import com.shinsoft.autoprofile.conditions.TimeCondition;


public class ConditionFactory
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	/**
	 * Build a specific condition object with given generic parameters
	 * @param id
	 * @param profileid
	 * @param classid
	 * @param conditions
	 * @return
	 * @since 1.0
	 */
	public static ProfileCondition buildConditionObject(int id, int profileid, int classid, String conditions)
	{
		ProfileCondition cond = null;
		switch (classid)
		{
			case TimeCondition.CLASSID :
			{
				cond = new TimeCondition(id,profileid,conditions);
				break;
			}
			case DateCondition.CLASSID :
			{
				cond = new DateCondition(id,profileid,conditions);
				break;
			}
			case BatteryCondition.CLASSID :
			{
				cond = new BatteryCondition(id,profileid,conditions);
			}
		}
		return cond;
	}
	
}



