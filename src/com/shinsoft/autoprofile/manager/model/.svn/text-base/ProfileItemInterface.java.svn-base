/** 
 * Interface Technologies (c), 2010
 * Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com), 5 oct. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.manager.model
 * File name: ItemInterface.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.manager.model;

import android.app.Activity;

public interface ProfileItemInterface
{
	public static int TYPE_CONDITION = 0;
	public static int TYPE_PARAMETER = 1;
	
	/**
	 * Return the class ID associated to the current object
	 * @return
	 * @since 1.0
	 */
	public int getClassId();
	
	/**
	 * Return the string name identifiant of the current object
	 * @return
	 * @since 1.0
	 */
	public int getNameId();
	
	/**
	 * Return the icon identifiant of the current condition object
	 * @return
	 * @since 1.0
	 */
	public int getIconResourceId();
	
	/**
	 * Return a formatted string with condition's options summary
	 * @param context
	 * @return
	 * @since 1.0
	 */
	public String getSummary(Activity context);
	
	
	/**
	 * Generate the field 'conditions' depending of specific condition object
	 * @since 1.0
	 */
	public void build();
	
	/**
	 * Parse the field 'conditions' and fill specific condition object
	 * @since 1.0
	 */
	public void parse();
	
}



