/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 19 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.profile
 * File name: ProfileLite.java
 *
 * Comments :
 **/

package com.shinsoft.autoprofile.profile;

import com.shinsoft.autoprofile.AutoProfile;


/**
 * Profile class
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class Profile
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	public static final boolean DEFAULT_ACTIVATE = true;
	public static final int DEFAULT_PRIORITY = 50;
	public static final String DEFAULT_ICON = "profileicon1";
	public static final int DEFAULT_PROFILEID = 1;
	
	// Class 'database' attributes
	protected int id;
	protected String name;
	protected String icon;
	protected int prio;
	protected boolean activate;
	
	
	/**
	 * Default Constructor
	 * @since 1.0
	 */
	public Profile()
	{
		this.icon = DEFAULT_ICON;
		this.prio = DEFAULT_PRIORITY;
		this.activate = DEFAULT_ACTIVATE;
	}
	
	/**
	 * Full Constructor
	 * @param id
	 * @param name
	 * @param icon
	 * @param prio
	 * @param activate
	 * @since 1.0
	 */
	public Profile(int id, String name, String icon, int prio, boolean activate)
	{
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.prio = prio;
		this.activate = activate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrio() {
		return prio;
	}

	public void setPrio(int prio) {
		this.prio = prio;
	}

	public boolean isActivate() {
		return activate;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}
}



