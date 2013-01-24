/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.conditions
 * File name: ConditionInterface.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.manager.model;


import android.app.Activity;

import com.shinsoft.autoprofile.profile.ProfileActivationUpdater;

/**
 * Interface for all condition objects
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public interface ConditionInterface extends ProfileItemInterface
{
	/**
	 * Update the profileActivator in parameter
	 * @param profileActivator
	 * @param profileActivatorUpdater
	 * @since 1.0
	 */
	public boolean isMatching(ProfileActivationUpdater profileActivatorUpdater);
	
}



