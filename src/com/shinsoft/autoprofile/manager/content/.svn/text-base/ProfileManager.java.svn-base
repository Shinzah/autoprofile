package com.shinsoft.autoprofile.manager.content;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.conditions.utils.ConditionFactory;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.manager.exception.ProfileException;
import com.shinsoft.autoprofile.parameter.utils.ParameterFactory;
import com.shinsoft.autoprofile.parameter.utils.ProfileParameter;
import com.shinsoft.autoprofile.profile.ProfileInfo;

/**
 * Database adapter
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class ProfileManager
{
	private static final String TAG = AutoProfile.class.getSimpleName();

	// Class properties
	private DBHelper dbHelper;
	private SQLiteDatabase sqlDB;
	private boolean debugMode;
	
	// Profiles static var
	private List<ProfileInfo> allProfile;
	
	/**
	 * Default Constructor
	 * @param context
	 * @since 1.0
	 */
	public ProfileManager(Context context)
	{
		this.dbHelper = new DBHelper(context);
		this.debugMode = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.PreferenceItemKey_DebugMode),false);
	}
	
	/**
	 * Open the sqlDatabase connection is none exist
	 * @return
	 * @since 1.0
	 */
	public ProfileManager open()
	{
		this.sqlDB = dbHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * Check if the sqlDatabase connection is opened
	 * @return
	 * @since 1.0
	 */
	public boolean isOpen()
	{
		return (this.sqlDB!=null);
	}
	
	/**
	 * When finalizing the object, close the qqlDataBase connection
	 * @see java.lang.Object#finalize()
	 * @since 1.0
	 */
	@Override
	protected void finalize()
					throws Throwable
	{
		if (this.sqlDB != null)
			this.sqlDB.close();

		super.finalize();
	}
	
	
	public List<ProfileInfo> getAllProfile()
	{
		if (allProfile == null)
			allProfile = getListProfile();
		
		return allProfile;
	}
	
	/**
	 * Retrieve all profile from table 'profile'
	 * @return
	 * @throws ProfileException
	 */
	private List<ProfileInfo> getListProfile()
	{
		if (debugMode)
			Log.i(TAG, "-> getAllProfile : Starting");
		
		try
		{
			// Retrieve all profile from table 'profile'
			Cursor results = sqlDB.query(DBHelper.TABLE_NAME_PROFILE, DBHelper.FIELDS_PROFILE, null/*selection*/, null/*selectionArgs*/, null/*groupBy*/, null/*having*/, DBHelper.FIELD_PROFILE_PRIO + " DESC");
			if (results != null && results.getCount() > 0)
			{
				allProfile = new ArrayList<ProfileInfo>();
				// Loop on results to build profileInfo objects
				results.moveToFirst();
				while (!results.isAfterLast())
				{
					int id = results.getInt(0);
					String name = results.getString(1);
					String icon = results.getString(2);
					int prio = results.getInt(3);
					boolean active = (1 == results.getInt(4));
					allProfile.add(new ProfileInfo(id, name, icon, prio, active));
					
					results.moveToNext();
				}
			}
			results.close();
			
			// For each profile retrieved, retrieve conditions
			if (allProfile != null && allProfile.size() > 0)
			{
				for (ProfileInfo currentProfile : allProfile)
				{
					StringBuilder whereClause = new StringBuilder(DBHelper.FIELD_CONDITIONS_PROFILEID).append("=?");
					Cursor resultsCond = sqlDB.query(DBHelper.TABLE_NAME_CONDITIONS, DBHelper.FIELDS_CONDITIONS, whereClause.toString()/*selection*/, new String[]{String.valueOf(currentProfile.getId())}/*selectionArgs*/, null/*groupBy*/, null/*having*/, null/*groupby*/);
					if (resultsCond != null && resultsCond.getCount() > 0)
					{
						resultsCond.moveToFirst();
						while (!resultsCond.isAfterLast())
						{
							int id = resultsCond.getInt(0);
							// ignore the profileid index 1
							int classId = resultsCond.getInt(2);
							String conditions = resultsCond.getString(3);
							// build object
							ProfileCondition profileCondition = ConditionFactory.buildConditionObject(id,currentProfile.getId(),classId,conditions);
							// add to profileinfo
							currentProfile.addCondition(profileCondition);
							
							resultsCond.moveToNext();
						}
					}
					resultsCond.close();
				}
			}
			
			// For each profile retrieved, retrieve parameters
			if (allProfile != null && allProfile.size() > 0)
			{
				for (ProfileInfo currentProfile : allProfile)
				{
					StringBuilder whereClause = new StringBuilder(DBHelper.FIELD_PARAMETERS_PROFILEID).append("=?");
					Cursor resultsCond = sqlDB.query(DBHelper.TABLE_NAME_PARAMETERS, DBHelper.FIELDS_PARAMETERS, whereClause.toString()/*selection*/, new String[]{String.valueOf(currentProfile.getId())}/*selectionArgs*/, null/*groupBy*/, null/*having*/, null/*groupby*/);
					if (resultsCond != null && resultsCond.getCount() > 0)
					{
						resultsCond.moveToFirst();
						while (!resultsCond.isAfterLast())
						{
							int id = resultsCond.getInt(0);
							// ignore the profileid index 1
							int classId = resultsCond.getInt(2);
							String parameters = resultsCond.getString(3);
							// build object
							ProfileParameter profileParameter = ParameterFactory.buildParameterObject(id,currentProfile.getId(),classId,parameters);
							// add to profileinfo
							currentProfile.addParameter(profileParameter);
							
							resultsCond.moveToNext();
						}
					}
					resultsCond.close();
				}
			}
			
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while getting all profile from database <!>",e);
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- getAllProfile : Finished ");
		}
		return allProfile;
	}
	
	/**
	 * Add/Update provided ProfileInfo into table 'profile'
	 * @param profile
	 * @throws ProfileException
	 * @since 1.0
	 */
	public int saveProfile(ProfileInfo profile) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> saveProfile : Starting");
		
		int profileId = 0;
		try
		{
			// Add
			if (profile.getId() == 0)
			{
				ContentValues addValues = new ContentValues();
				addValues.put(DBHelper.FIELD_PROFILE_NAME, profile.getName());
				addValues.put(DBHelper.FIELD_PROFILE_ICON, profile.getIcon());
				addValues.put(DBHelper.FIELD_PROFILE_PRIO, profile.getPrio());
				addValues.put(DBHelper.FIELD_PROFILE_ACTIVE, (profile.isActivate()?1:0));
				profileId = new Long(sqlDB.insert(DBHelper.TABLE_NAME_PROFILE, null, addValues)).intValue();
				profile.setId(profileId);
			}
			// Update
			else
			{
				profileId = profile.getId();
				// Inserting data into 'profile' table
				ContentValues updateValues = new ContentValues();
				updateValues.put(DBHelper.FIELD_PROFILE_NAME, profile.getName());
				updateValues.put(DBHelper.FIELD_PROFILE_ICON, profile.getIcon());
				updateValues.put(DBHelper.FIELD_PROFILE_PRIO, profile.getPrio());
				updateValues.put(DBHelper.FIELD_PROFILE_ACTIVE, (profile.isActivate()?1:0));
				StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_PROFILE_ID).append("=?");
				sqlDB.update(DBHelper.TABLE_NAME_PROFILE,updateValues,whereSb.toString(),new String[]{String.valueOf(profile.getId())});
			}
			profile.setbModified(false);
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while saving profile into table profile <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- saveProfile : Finished ");
		}
		return profileId;
	}
	
	/**
	 * Add/Update provided condition into table 'conditions'
	 * @param profileCondition
	 * @throws ProfileException
	 * @since 1.0
	 */
	public int saveCondition(ProfileCondition profileCondition) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> saveCondition : Starting");
		int conditionId = 0;
		try
		{
			// Add
			if (profileCondition.getId() == 0)
			{
				ContentValues addValues = new ContentValues();
				addValues.put(DBHelper.FIELD_CONDITIONS_PROFILEID, profileCondition.getProfileid());
				addValues.put(DBHelper.FIELD_CONDITIONS_CLASSID, profileCondition.getClassId());
				addValues.put(DBHelper.FIELD_CONDITIONS_CONDITIONS, profileCondition.getConditions());
				conditionId = new Long(sqlDB.insert(DBHelper.TABLE_NAME_CONDITIONS, null, addValues)).intValue();
				profileCondition.setId(conditionId);
			}
			// Update (You can only update the condition)
			else
			{
				conditionId = profileCondition.getId();
				ContentValues updateValues = new ContentValues();
				updateValues.put(DBHelper.FIELD_CONDITIONS_CONDITIONS, profileCondition.getConditions());
				StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_CONDITIONS_ID).append("=?");
				sqlDB.update(DBHelper.TABLE_NAME_CONDITIONS,updateValues,whereSb.toString(),new String[]{String.valueOf(profileCondition.getId())});
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while saving condition into table conditions <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- saveCondition : Finished ");
		}
		return conditionId;
	}
	
	/**
	 * Add/Update provided parameter into table 'parameters'
	 * @param profileParameter
	 * @throws ProfileException
	 * @since 1.0
	 */
	public int saveParameter(ProfileParameter profileParameter) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> saveParameter : Starting");
		int conditionId = 0;
		try
		{
			// Add
			if (profileParameter.getId() == 0)
			{
				ContentValues addValues = new ContentValues();
				addValues.put(DBHelper.FIELD_PARAMETERS_PROFILEID, profileParameter.getProfileid());
				addValues.put(DBHelper.FIELD_PARAMETERS_CLASSID, profileParameter.getClassId());
				addValues.put(DBHelper.FIELD_PARAMETERS_PARAMETERS, profileParameter.getParameters());
				conditionId = new Long(sqlDB.insert(DBHelper.TABLE_NAME_CONDITIONS, null, addValues)).intValue();
				profileParameter.setId(conditionId);
			}
			// Update (You can only update the parameter)
			else
			{
				conditionId = profileParameter.getId();
				ContentValues updateValues = new ContentValues();
				updateValues.put(DBHelper.FIELD_PARAMETERS_PARAMETERS, profileParameter.getParameters());
				StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_PARAMETERS_ID).append("=?");
				sqlDB.update(DBHelper.TABLE_NAME_PARAMETERS,updateValues,whereSb.toString(),new String[]{String.valueOf(profileParameter.getId())});
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while saving parameter into table parameters <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- saveParameter : Finished ");
		}
		return conditionId;
	}
	
	
	/**
	 * Update the field activate for a specific profile
	 * @param profileid
	 * @param activate
	 * @throws ProfileException
	 * @since 1.0
	 */
	public void updateProfileActivation(int profileid, boolean activate) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> updateProfileActivation : Starting");
		
		try
		{
			// Updating data into 'profile' table
			ContentValues values = new ContentValues();
			values.put(DBHelper.FIELD_PROFILE_ACTIVE, activate?1:0);
			StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_PROFILE_ID).append("=?");
			sqlDB.update(DBHelper.TABLE_NAME_PROFILE,values,whereSb.toString(),new String[]{String.valueOf(profileid)});
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while updating activate in table profile <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- updateProfileActivation : Finished ");
		}
	}
	
	/**
	 * Delete the specified profile from table 'profile' and all his conditions from table 'conditions'
	 * @param profileid
	 * @throws ProfileException
	 * @since 1.0
	 */
	public void deleteProfile(int profileid) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> deleteProfile : Starting");
		try
		{
			// Delete profile
			StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_PROFILE_ID).append("=?");
			sqlDB.delete(DBHelper.TABLE_NAME_PROFILE,whereSb.toString(),new String[]{String.valueOf(profileid)});
			// Delete conditions associated to profile id
			whereSb = new StringBuilder(DBHelper.FIELD_CONDITIONS_PROFILEID).append("=?");
			sqlDB.delete(DBHelper.TABLE_NAME_CONDITIONS,whereSb.toString(),new String[]{String.valueOf(profileid)});
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while deleting profile in table profile <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- deleteProfile : Finished ");
		}
	}
	
	
	/**
	 * Delete the specified condition from table 'conditions'
	 * @param conditionid
	 * @throws ProfileException
	 * @since 1.0
	 */
	public void deleteCondition(int conditionid) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> deleteCondition : Starting");
		try
		{
			StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_CONDITIONS_ID).append("=?");
			sqlDB.delete(DBHelper.TABLE_NAME_CONDITIONS,whereSb.toString(),new String[]{String.valueOf(conditionid)});
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while deleting condition in table conditions <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- deleteCondition : Finished ");
		}
	}
	
	/**
	 * Delete the specified parameter from table 'parameters'
	 * @param parameterid
	 * @throws ProfileException
	 * @since 1.0
	 */
	public void deleteParameter(int parameterid) throws ProfileException
	{
		if (debugMode)
			Log.i(TAG, "-> deleteParameter : Starting");
		try
		{
			StringBuilder whereSb = new StringBuilder(DBHelper.FIELD_PARAMETERS_ID).append("=?");
			sqlDB.delete(DBHelper.TABLE_NAME_PARAMETERS,whereSb.toString(),new String[]{String.valueOf(parameterid)});
		}
		catch (Exception e)
		{
			Log.e(TAG, "<!> An error append while deleting parameter in table parameters <!>",e);
			throw new ProfileException();
		}
		finally
		{
			if (debugMode)
				Log.i(TAG, "<- deleteParameter : Finished ");
		}
	}
}
