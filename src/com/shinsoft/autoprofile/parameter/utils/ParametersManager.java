/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager
 * File name: ConditionsLister.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.parameter.utils;

import java.util.ArrayList;
import java.util.List;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.parameter.VolumeParameter;

public class ParametersManager
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	/**
	 * Return the list of all conditions class existing
	 * @return
	 * @since 1.0
	 */
	public static List<Class<?>> listAllAvailableParameters()
	{
		List<Class<?>> listRet = new ArrayList<Class<?>>();
		listRet.add(VolumeParameter.class);
		
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
			case VolumeParameter.CLASSID :
				return VolumeParameter.class;
		}
		return null;
	}
	
}