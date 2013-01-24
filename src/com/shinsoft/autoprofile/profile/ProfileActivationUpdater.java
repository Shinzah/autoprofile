package com.shinsoft.autoprofile.profile;

import java.util.Calendar;

public class ProfileActivationUpdater 
{
	private Calendar dateAndTime;
	private int batteryLevel;
	private boolean batteryInCharge;
	
	public ProfileActivationUpdater(Calendar dateAndTime, int batteryLevel, boolean batteryInCharge)
	{
		this.dateAndTime = dateAndTime;
		this.batteryLevel = batteryLevel;
		this.batteryInCharge = batteryInCharge;
	}

	public void updateBatteryLevel(int batteryLevel)
	{
		this.batteryLevel = batteryLevel;
	}
	public void updateDateAndTime(Calendar dateAndTime)
	{
		this.dateAndTime = dateAndTime;
	}
	public void updateBatteryInCharge(boolean batteryInCharge)
	{
		this.batteryInCharge = batteryInCharge;
	}
	
	public Calendar getDateAndTime() {
		return dateAndTime;
	}

	public int getBatteryLevel() {
		return batteryLevel;
	}

	public boolean isBatteryInCharge()
	{
		return batteryInCharge;
	}

}