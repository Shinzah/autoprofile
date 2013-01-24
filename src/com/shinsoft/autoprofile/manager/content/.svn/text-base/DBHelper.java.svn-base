package com.shinsoft.autoprofile.manager.content;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.profile.ProfileInfo;


/**
 * Database access helper
 * <br>
 * Versions : <br>
 *
 *   <li>1.0 : Initialisation</li>
 * <br>
 * @version 1.0
 */
public class DBHelper extends SQLiteOpenHelper
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Database constants
	private static final String DATABASE_NAME = "autoprofiledb";
	private static final int DATABASE_VERSION = 2;

	// Class property object
	private Context context;
	
	// Table 'profile' constants
	public static final String TABLE_NAME_PROFILE = "profile";
	public static final String FIELD_PROFILE_ID = "id";
	public static final String FIELD_PROFILE_NAME = "name";
	public static final String FIELD_PROFILE_ICON = "icon";
	public static final String FIELD_PROFILE_PRIO = "prio";
	public static final String FIELD_PROFILE_ACTIVE = "active";
	public static final String[] FIELDS_PROFILE = {FIELD_PROFILE_ID,FIELD_PROFILE_NAME,FIELD_PROFILE_ICON,FIELD_PROFILE_PRIO,FIELD_PROFILE_ACTIVE};
	private static final String CREATE_TABLE_PROFILE = "CREATE TABLE "  +TABLE_NAME_PROFILE + " (" +
															FIELD_PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
															FIELD_PROFILE_NAME + " VARCHAR(50) NOT NULL, " +
															FIELD_PROFILE_ICON + " VARCHAR(50) NOT NULL, " +
															FIELD_PROFILE_PRIO + " INTEGER NOT NULL, " +
															FIELD_PROFILE_ACTIVE + " INTEGER NOT NULL)";
	
	// Table 'conditions' constants
	public static final String TABLE_NAME_CONDITIONS = "conditions";
	public static final String FIELD_CONDITIONS_ID = "id";
	public static final String FIELD_CONDITIONS_PROFILEID = "profileid";
	public static final String FIELD_CONDITIONS_CLASSID = "classid";
	public static final String FIELD_CONDITIONS_CONDITIONS = "conditions";
	public static final String[] FIELDS_CONDITIONS = {FIELD_CONDITIONS_ID, FIELD_CONDITIONS_PROFILEID, FIELD_CONDITIONS_CLASSID, FIELD_CONDITIONS_CONDITIONS};
	private static final String CREATE_TABLE_CONDITIONS = "CREATE TABLE " + TABLE_NAME_CONDITIONS +" (" +
															FIELD_CONDITIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
															FIELD_CONDITIONS_PROFILEID + " INTEGER NOT NULL, " +
															FIELD_CONDITIONS_CLASSID + " INTEGER NOT NULL," + 
															FIELD_CONDITIONS_CONDITIONS + " VARCHAR(250) NOT NULL)";

	// Table 'parameters' constants
	public static final String TABLE_NAME_PARAMETERS = "parameters";
	public static final String FIELD_PARAMETERS_ID = "id";
	public static final String FIELD_PARAMETERS_PROFILEID = "profileid";
	public static final String FIELD_PARAMETERS_CLASSID = "classid";
	public static final String FIELD_PARAMETERS_PARAMETERS = "parameters";
	public static final String[] FIELDS_PARAMETERS = {FIELD_PARAMETERS_ID, FIELD_PARAMETERS_PROFILEID, FIELD_PARAMETERS_CLASSID, FIELD_PARAMETERS_PARAMETERS};
	private static final String CREATE_TABLE_PARAMETERS = "CREATE TABLE " + TABLE_NAME_PARAMETERS +" (" +
															FIELD_PARAMETERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
															FIELD_PARAMETERS_PROFILEID + " INTEGER NOT NULL, " +
															FIELD_PARAMETERS_CLASSID + " INTEGER NOT NULL," + 
															FIELD_PARAMETERS_PARAMETERS + " VARCHAR(250) NOT NULL)";
	
	/**
	 * Default Constructor
	 * @param context
	 */
	public DBHelper(Context context) 
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	/**
	 * onCreate override (Table creation)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// Create table 'profile'
		db.execSQL(CREATE_TABLE_PROFILE);
		// Create table 'conditions'
		db.execSQL(CREATE_TABLE_CONDITIONS);
		// Create table 'parameters'
		db.execSQL(CREATE_TABLE_PARAMETERS);
		
		// Insert default values
		ContentValues initialValues = new ContentValues();
		initialValues.put(FIELD_PROFILE_NAME, context.getResources().getString(R.string.DefaultProfileName));
		initialValues.put(FIELD_PROFILE_ICON, ProfileInfo.DEFAULT_ICON);
		initialValues.put(FIELD_PROFILE_PRIO, -1);
		initialValues.put(FIELD_PROFILE_ACTIVE, ProfileInfo.DEFAULT_ACTIVATE?1:0);
		db.insert(TABLE_NAME_PROFILE, null, initialValues);
	}
	
	/**
	 * onUpdate override
	 * - Drop table if exists and call onCreate();
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CONDITIONS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_PARAMETERS);
		onCreate(db);
	}
}
