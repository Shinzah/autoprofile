/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 17 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.profile
 * File name: ProfileComparator.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.profile;

import java.util.Comparator;

import com.shinsoft.autoprofile.AutoProfile;

public class ProfileComparator implements Comparator<ProfileInfo>
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	public static final int SORT_BY_PRIORITY_ASC = 1;
	public static final int SORT_BY_PRIORITY_DESC = 2;
	public static final int SORT_BY_NAME_ASC = 3;
	public static final int SORT_BY_NAME_DESC = 4;
	
	private int mode;
	
	public ProfileComparator(int mode)
	{
		this.mode = mode;
	}
	public ProfileComparator(String mode)
	{
		this.mode = Integer.parseInt(mode);
	}
	
	public int compare(ProfileInfo profile1, ProfileInfo profile2)
	{
		switch (mode)
		{
			case SORT_BY_PRIORITY_ASC :
				return new Integer(profile1.getPrio()).compareTo(new Integer(profile2.getPrio()));
			case SORT_BY_NAME_ASC :
				return profile1.getName().compareTo(profile2.getName());
			case SORT_BY_NAME_DESC :
				return profile2.getName().compareTo(profile1.getName());
			case SORT_BY_PRIORITY_DESC :
			default :
				return new Integer(profile2.getPrio()).compareTo(new Integer(profile1.getPrio()));
		}
	}

	
	public int getMode()
	{
		return mode;
	}

	public void setMode(int mode)
	{
		this.mode = mode;
	}
	public void setMode(String mode)
	{
		this.mode = Integer.parseInt(mode);
	}

}



