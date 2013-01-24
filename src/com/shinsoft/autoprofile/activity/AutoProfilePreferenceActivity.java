/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 9 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile
 * File name: AutoProfilePreferenceActivity.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;

public class AutoProfilePreferenceActivity extends PreferenceActivity
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.pref);
	}
}



