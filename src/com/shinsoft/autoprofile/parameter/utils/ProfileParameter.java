package com.shinsoft.autoprofile.parameter.utils;

import java.io.Serializable;

import com.shinsoft.autoprofile.AutoProfile;


public abstract class ProfileParameter implements ParameterInterface, Serializable
{
	private static final long serialVersionUID = -8011982800464065888L;
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	public static final String PARAMETERS_SEPARATOR = "|";
	
	// Class properties
	protected int id;
	protected int profileid;
	protected String parameters;
	protected String summary;
	
	public ProfileParameter() {}
	public ProfileParameter(int profileid) 
	{ 
		this.profileid = profileid; 
	}
	public ProfileParameter(int id, int profileid, String parameters) 
	{ 
		this.id = id;
		this.profileid = profileid;
		this.parameters = parameters;
		this.parseParameters();
	}
	
	/**
	 * Override of initial method to make a profilecondition equals to another depending of his condition id
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @since 1.0
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof ProfileParameter)
		{
			ProfileParameter p0 = (ProfileParameter)o;
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
	public String getParameters()
	{
		if (this.parameters == null || this.parameters.trim().length() == 0)
			this.buildParameters();
		return parameters;
	}
	public void setParameters(String parameters)
	{
		this.parameters = parameters;
	}
}
