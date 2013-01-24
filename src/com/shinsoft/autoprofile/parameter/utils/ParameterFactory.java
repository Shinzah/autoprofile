/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: ConditionFactory.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.parameter.utils;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.conditions.BatteryCondition;
import com.shinsoft.autoprofile.parameter.VolumeParameter;


public class ParameterFactory
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	/**
	 * Build a specific parameter object with given generic parameters
	 * @param id
	 * @param profileid
	 * @param classid
	 * @param conditions
	 * @return
	 * @since 1.0
	 */
	public static ProfileParameter buildParameterObject(int id, int profileid, int classid, String parameters)
	{
		ProfileParameter param = null;
		switch (classid)
		{
			case VolumeParameter.CLASSID :
			{
				param = new VolumeParameter(id,profileid,parameters);
			}
		}
		return param;
	}
	
}



