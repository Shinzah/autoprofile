/** 
 * Interface Technologies (c), 2010
 * Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com), 1 oct. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.parameter.utils
 * File name: ParameterProcessor.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.parameter.utils;

import java.util.List;

import com.shinsoft.autoprofile.parameter.VolumeParameter;
import com.shinsoft.autoprofile.profile.ProfileInfo;

/**
 * Utility class for parameters processing and appliance
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
 * @version 1.0
 */
public class ParameterProcessor
{
	
	/**
	 * Compute and process parameters depending of list parameters given
	 * @param listParameters
	 * @since 1.0
	 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
	 */
	public static void processParameters(List<ProfileInfo> listProfile)
	{
		// Build a new ParameterRecap object
		ParameterRecap paramRecap = new ParameterRecap();
		
		// Order profile by priority ASC (0 -> 50)
		
		// Loop on all profile to apply parameters
		for (ProfileInfo currentProfile : listProfile)
		{
			List<ProfileParameter> listParameters = currentProfile.getListParameters();
			if (listParameters != null && !listParameters.isEmpty())
			{
				// Let ParameterRecap make the work for us
				for (ProfileParameter parameter : listParameters)
				{
					paramRecap.fillParameter(parameter);
				}
			}
		}
		
		// Now we have a fullfilled ParameterRecap, apply all parameters
		applyParameters(paramRecap);
	}

	
	private static void applyParameters(ParameterRecap paramRecap)
	{
		
	}
	
	/**
	 * Static class which recapitulate all parameters to apply
	 * <br>
	 * Versions : <br>
	 *
	 *   <li>1.0 : Initialisation</li>
	 * <br>
	 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
	 * @version 1.0
	 */
	static class ParameterRecap
	{
		// Volume informations
		boolean bApplyVolumeInfo;
		
		ParameterRecap() { }
		
		
		/**
		 * Add profileParameter parameters into current object
		 * @param parameter
		 * @since 1.0
		 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
		 */
		protected void fillParameter(ProfileParameter parameter)
		{
			switch (parameter.getClassId())
			{
				case VolumeParameter.CLASSID :
				{
					
					break;
				}
				
			}
		}
	}
	
}