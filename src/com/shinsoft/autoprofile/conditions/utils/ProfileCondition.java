package com.shinsoft.autoprofile.conditions.utils;

import java.io.Serializable;

import com.shinsoft.autoprofile.AutoProfile;


public abstract class ProfileCondition implements ConditionInterface, Serializable
{
	private static final long serialVersionUID = 6190335221340807478L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	public static final String CONDITIONS_SEPARATOR = "|";
	
	// Class properties
	protected int id;
	protected int profileid;
	protected String conditions;
	protected String summary;
	
	public ProfileCondition() {}
	public ProfileCondition(int profileid) 
	{ 
		this.profileid = profileid; 
	}
	public ProfileCondition(int id, int profileid, String conditions) 
	{ 
		this.id = id;
		this.profileid = profileid;
		this.conditions = conditions;
		this.parseConditions();
	}
	/**
	 * Override of initial method to make a profilecondition equals to another depending of his condition id
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ProfileCondition)
		{
			ProfileCondition p0 = (ProfileCondition)o;
			if (p0.getId() == this.getId())
				return true;
			else
				return false;
		}
		else
			return super.equals(o);
	}
	
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getProfileid()
	{
		return profileid;
	}
	public void setProfileid(int profileid)
	{
		this.profileid = profileid;
	}
	public String getConditions()
	{
		if (this.conditions == null || this.conditions.trim().length() == 0)
			this.buildConditions();
			
		return this.conditions;
	}
	public void setConditions(String conditions)
	{
		this.conditions = conditions;
	}
}
