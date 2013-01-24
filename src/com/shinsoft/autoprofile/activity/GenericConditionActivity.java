/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile
 * File name: GenericConditionActivity.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.activity;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.conditions.BatteryCondition;
import com.shinsoft.autoprofile.conditions.DateCondition;
import com.shinsoft.autoprofile.conditions.TimeCondition;
import com.shinsoft.autoprofile.conditions.utils.ProfileCondition;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.parameter.VolumeParameter;

public class GenericConditionActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener, android.widget.RadioGroup.OnCheckedChangeListener
{
	private static final String TAG = AutoProfile.class.getSimpleName();

	private ProfileCondition profileCondition;
	
	// TimeCondition
	private Button defineFromTime;
	private Button defineToTime;
	private boolean twentyfourhmode;
	
	// DateCondition
	private Button defineFromDate;
	private Button defineToDate;
	
	// BatteryCondition
	private SeekBar batteryPercent;
	private CheckBox isInCharge;
	private TextView percentLabel;
	private RadioGroup higherLower;
	
	/**
	 * On activity creation, initialize all widgets
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * @since 1.0
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generic_layout);

        // Retrieve datas from Extras
        this.profileCondition = (ProfileCondition)this.getIntent().getExtras().getSerializable(Constants.EXTRA_KEY_PROFILECONDITION);
        
        this.buildActivityTitle();
        
        // Fill generic layout
        ImageView img = (ImageView)findViewById(R.id.LayoutIcon);
        TextView txtView = (TextView)findViewById(R.id.LayoutLabel);
        TextView summaryView = (TextView)findViewById(R.id.LayoutLabelSummary);
        
        Button saveBtn = (Button)this.findViewById(R.id.LayoutSaveBtn);
        saveBtn.setOnClickListener(this);
        Button cancelBtn = (Button)this.findViewById(R.id.LayoutCancelBtn);
        cancelBtn.setOnClickListener(this);

        View conditionView = null;
        // Fill generic widget and inflate body depending of profilecondition instance
        switch (this.profileCondition.getClassId())
        {
	        case TimeCondition.CLASSID :
	        {
	        	// TODO : Ajouter le titre de la fenetre
	        	summaryView.setText(R.string.ConditionExplain_TimeCondition);
	        	img.setImageResource(R.drawable.conditionicon_time);
	        	txtView.setText(R.string.ConditionName_TimeCondition);	
	        	conditionView = getLayoutInflater().inflate(R.layout.condition_time,null);
	        	break;
	        }
	        case DateCondition.CLASSID :
	        {
	        	// TODO : Ajouter le titre de la fenetre
	        	summaryView.setText(R.string.ConditionExplain_DateCondition);
	        	img.setImageResource(R.drawable.conditionicon_date);
	        	txtView.setText(R.string.ConditionName_DateCondition);	
	        	conditionView = getLayoutInflater().inflate(R.layout.condition_date,null);
	        	break;
	        }
	        case BatteryCondition.CLASSID :
	        {
	        	// TODO : Ajouter le titre de la fenetre
	        	summaryView.setText(R.string.ConditionExplain_BatteryCondition);
	        	img.setImageResource(R.drawable.conditionicon_battery);
	        	txtView.setText(R.string.ConditionName_BatteryCondition);	
	        	conditionView = getLayoutInflater().inflate(R.layout.condition_battery,null);
	        	break;
	        }
        }
        
        LinearLayout bodyLayout = (LinearLayout)findViewById(R.id.LayoutBody);
        bodyLayout.addView(conditionView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        
       	this.initCondition(conditionView);
	}

	private void initCondition(View bodyView)
	{
		switch (this.profileCondition.getClassId())
		{
			case TimeCondition.CLASSID :
			{
		        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		        this.twentyfourhmode = prefs.getBoolean(getResources().getString(R.string.PreferenceItemKey_24hMode),false);
		        
		        this.defineFromTime = (Button)bodyView.findViewById(R.id.TimeCondition_FromBtn);
		        this.defineFromTime.setOnClickListener(this);
		        this.defineToTime = (Button)bodyView.findViewById(R.id.TimeCondition_ToBtn);
		        this.defineToTime.setOnClickListener(this);
				break;
			}
			case DateCondition.CLASSID :
			{
				this.defineFromDate = (Button)this.findViewById(R.id.DateCondition_FromBtn);
		        this.defineFromDate.setOnClickListener(this);
		        this.defineToDate = (Button)this.findViewById(R.id.DateCondition_ToBtn);
		        this.defineToDate.setOnClickListener(this);			
				break;
			}
			case BatteryCondition.CLASSID :
			{
				this.isInCharge = (CheckBox)this.findViewById(R.id.BatteryCondition_InCharge);
		        this.isInCharge.setOnCheckedChangeListener(this);
		        this.batteryPercent = (SeekBar)this.findViewById(R.id.BatteryCondition_Percent);
		        this.batteryPercent.setOnSeekBarChangeListener(this);
		        this.percentLabel = (TextView)this.findViewById(R.id.BatteryCondition_PercentLabel);
		        
		        this.higherLower = (RadioGroup)this.findViewById(R.id.BatteryCondition_HigherLower);
		        this.higherLower.setOnCheckedChangeListener(this);
				break;
			}
		}
			
        
		this.updateWidgets();
	}
	
	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * @since 1.0
	 */
	public void onClick(View v)
	{
		switch (v.getId())
		{
			// ######################
			//  TIMECONDITION BUTTONS
			// ######################
			case R.id.TimeCondition_FromBtn :
			{
				TimeCondition timeCondition = (TimeCondition)this.profileCondition;
				int hour = timeCondition.getFromTime().getHours();
				int minute = timeCondition.getFromTime().getMinutes();
				
				OnTimeSetListener fromTimeListener = new OnTimeSetListener() {
					public void onTimeSet(
									TimePicker view,
									int hourOfDay,
									int minute)
					{
						((TimeCondition)profileCondition).setFromTime(new Time(hourOfDay,minute,0));
						updateWidgets();
					} };
				
				new TimePickerDialog(this,fromTimeListener,hour,minute,twentyfourhmode).show();
				break;
			}
			case R.id.TimeCondition_ToBtn :
			{
				TimeCondition timeCondition = (TimeCondition)this.profileCondition;
				int hour = timeCondition.getToTime().getHours();
				int minute = timeCondition.getToTime().getMinutes();
				
				OnTimeSetListener toTimeListener = new OnTimeSetListener() {
					public void onTimeSet(
									TimePicker view,
									int hourOfDay,
									int minute)
					{
						((TimeCondition)profileCondition).setToTime(new Time(hourOfDay,minute,0));
						updateWidgets();
					} };
				
				new TimePickerDialog(this,toTimeListener,hour,minute,twentyfourhmode).show();
				break;
			}
			// ######################
			// DATECONDITION BUTTONS
			// ######################			
			case R.id.DateCondition_FromBtn :
			{
				DateCondition dateCondition = (DateCondition)this.profileCondition;
				int date = dateCondition.getFromDate().getDate();
				int month = dateCondition.getFromDate().getMonth();
				int year = dateCondition.getFromDate().getYear();
				
				OnDateSetListener callBack = new OnDateSetListener() {
					public void onDateSet(
									DatePicker view,
									int year,
									int monthOfYear,
									int dayOfMonth)
					{
						((DateCondition)profileCondition).setFromDate(new Date((year-1900),monthOfYear,dayOfMonth));
						updateWidgets();
					} };
				
				new DatePickerDialog(this,callBack,(year+1900),month,date).show();
				break;
			}
			case R.id.DateCondition_ToBtn :
			{
				DateCondition dateCondition = (DateCondition)this.profileCondition;
				int date = dateCondition.getToDate().getDate();
				int month = dateCondition.getToDate().getMonth();
				int year = dateCondition.getToDate().getYear();
				
				OnDateSetListener callBack = new OnDateSetListener() {
					public void onDateSet(
									DatePicker view,
									int year,
									int monthOfYear,
									int dayOfMonth)
					{
						((DateCondition)profileCondition).setToDate(new Date((year-1900),monthOfYear,dayOfMonth));
						updateWidgets();
					} };
				
				new DatePickerDialog(this,callBack,(year+1900),month,date).show();
				break;
			}
			// ######################
			// GENERIC LAYOUT BUTTONS
			// ######################
			case R.id.LayoutSaveBtn :
			{
				// Update conditions into object
				this.profileCondition.buildConditions();
				// Save timecondition in database
				Intent backIntent = new Intent();
				backIntent.putExtra(Constants.EXTRA_KEY_PROFILECONDITION,this.profileCondition);
				setResult(Constants.RESULT_CODE_OK,backIntent);
				this.finish();
				break;
			}
			case R.id.LayoutCancelBtn :
			{
				setResult(Constants.RESULT_CODE_NO);
				this.finish();
				break;
			}
		}
	}
	
	/**
	 * @see android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android.widget.SeekBar)
	 * @since 1.0
	 */
	public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser) 
	{
		switch (seekBar.getId())
		{
			case R.id.BatteryCondition_Percent :
			{
				BatteryCondition batteryCondition = (BatteryCondition)this.profileCondition;
				batteryCondition.setPercent(seekBar.getProgress());
				updateWidgets();
				break;
			}
		}
	}
	public void onStopTrackingTouch(SeekBar seekBar){}
	public void onStartTrackingTouch(SeekBar seekBar) { }
	
	/**
	 * @see android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged(android.widget.CompoundButton, boolean)
	 * @since 1.0
	 */
	public void onCheckedChanged(CompoundButton buttonView,boolean isChecked)
	{
		switch (buttonView.getId())
		{
			case R.id.BatteryCondition_InCharge :
			{
				BatteryCondition batteryCondition = (BatteryCondition)this.profileCondition;
				batteryCondition.setInCharge(isChecked);
				updateWidgets();
				break;
			}
		}
	}
	public void onCheckedChanged(RadioGroup widget,int checkedId)
	{
		switch (checkedId)
		{
			case R.id.BatteryCondition_Lower :
			{
				((BatteryCondition)this.profileCondition).setHigherLower(BatteryCondition.BATTERY_LOWER);
				break;
			}
			case R.id.BatteryCondition_Higher :
			{
				((BatteryCondition)this.profileCondition).setHigherLower(BatteryCondition.BATTERY_HIGHER);
				break;
			}
		}
	}
	
	/**
	 * Update button label with the values selected in timepickerdialog
	 * @since 1.0
	 */
	private void updateWidgets()
	{
		switch (this.profileCondition.getClassId())
		{
			case TimeCondition.CLASSID :
			{
				TimeCondition timeCondition = (TimeCondition)this.profileCondition;
		        this.defineFromTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT,Locale.getDefault()).format(timeCondition.getFromTime().getTime()));
		        this.defineToTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT,Locale.getDefault()).format(timeCondition.getToTime().getTime()));
		        break;
			}
			case DateCondition.CLASSID :
			{
				DateCondition dateCondition = (DateCondition)this.profileCondition;
				this.defineFromDate.setText(DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault()).format(dateCondition.getFromDate().getTime()));
		        this.defineToDate.setText(DateFormat.getDateInstance(DateFormat.SHORT,Locale.getDefault()).format(dateCondition.getToDate().getTime()));
		        break;
			}
			case BatteryCondition.CLASSID :
			{
				BatteryCondition batteryCondition = (BatteryCondition)this.profileCondition;
				if (batteryCondition.getId() > 0)
				{
					this.batteryPercent.setProgress(batteryCondition.getPercent());
					this.isInCharge.setChecked(batteryCondition.isInCharge());
					if (BatteryCondition.BATTERY_LOWER.equalsIgnoreCase(batteryCondition.getHigherLower()))
						this.higherLower.check(R.id.BatteryCondition_Lower);
					else
						this.higherLower.check(R.id.BatteryCondition_Higher);
				}
				
				this.batteryPercent.setEnabled(!batteryCondition.isInCharge());
				for (int idx=0;idx<this.higherLower.getChildCount();idx++)
					this.higherLower.getChildAt(idx).setEnabled(!batteryCondition.isInCharge());
				
				StringBuilder labelWValue = new StringBuilder(getString(R.string.BatteryConditionPercentLabel));
				labelWValue.append(" (").append(this.batteryPercent.getProgress()).append(")");
				this.percentLabel.setText(labelWValue.toString());
				
				break;
			}
		}
	}
	
	/**
	 * Build the activity title depending of profileCondition
	 * @since 1.0
	 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
	 */
	private void buildActivityTitle()
	{
		final String SEPARATOR = " > "; 
		
		StringBuilder titleSb = new StringBuilder();
        titleSb.append(getParent().getTitle().toString()).append(SEPARATOR).append(getResources().getString(R.string.Title_Condition));
        
        switch (this.profileCondition.getClassId())
        {
        	case TimeCondition.CLASSID :
        	{
        		titleSb.append(SEPARATOR).append(getResources().getString(R.string.Title_TimeCondition));
        		break;
        	}
        	case DateCondition.CLASSID :
        	{
        		titleSb.append(SEPARATOR).append(getResources().getString(R.string.Title_DateCondition));
        		break;
        	}
        	case BatteryCondition.CLASSID :
        	{
        		titleSb.append(SEPARATOR).append(getResources().getString(R.string.Title_BatteryCondition));
        		break;
        	}
        }
        
        setTitle(titleSb.toString());
	}
	
}