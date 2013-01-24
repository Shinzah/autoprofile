/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile
 * File name: ProfileParameter.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SpinnerAdapter;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.manager.Constants;
import com.shinsoft.autoprofile.parameter.VolumeParameter;
import com.shinsoft.autoprofile.parameter.utils.ProfileParameter;

public class GenericParameterActivity extends Activity implements OnClickListener, OnCheckedChangeListener, OnSeekBarChangeListener, OnItemSelectedListener
{
	private static final String TAG = AutoProfile.class.getSimpleName();

	private ProfileParameter profileParameter;
	private String parentTitle;
	
	// Volume Parameter
	private Spinner volumeParameterChangeProfile;
	private TextView volumeParameterMainVolumeLabel;
	private CheckBox volumeParameterMainVolume;
	private SeekBar volumeParameterMainVolumeLevel;
	private TextView volumeParameterMediaVolumeLabel;
	private CheckBox volumeParameterMediaVolume;
	private SeekBar volumeParameterMediaVolumeLevel;
	private TextView volumeParameterAlarmVolumeLabel;
	private CheckBox volumeParameterAlarmVolume;
	private SeekBar volumeParameterAlarmVolumeLevel;
	private TextView volumeParameterCallVolumeLabel;
	private CheckBox volumeParameterCallVolume;
	private SeekBar volumeParameterCallVolumeLevel;
	private TextView volumeParameterRingVolumeLabel;
	private CheckBox volumeParameterRingVolume;
	private SeekBar volumeParameterRingVolumeLevel;
	
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
        this.profileParameter = (ProfileParameter)this.getIntent().getExtras().getSerializable(Constants.EXTRA_KEY_PROFILEPARAMETER);
        this.parentTitle = this.getIntent().getExtras().getString(Constants.EXTRA_KEY_PARENTTITLE);
        this.buildActivityTitle();
        
        // Fill generic layout
        ImageView img = (ImageView)findViewById(R.id.LayoutIcon);
        TextView txtView = (TextView)findViewById(R.id.LayoutLabel);
        TextView summaryView = (TextView)findViewById(R.id.LayoutLabelSummary);
        
        View parameterView = null;
        // Fill generic widget and inflate body depending of profileParameter object
        switch (this.profileParameter.getClassId())
        {
	        case VolumeParameter.CLASSID :
	        {
	        	summaryView.setText(R.string.ParameterExplain_VolumeParameter);
	        	img.setImageResource(R.drawable.parameter_volume);
	        	txtView.setText(R.string.ParameterName_VolumeParameter);	
	        	parameterView = getLayoutInflater().inflate(R.layout.parameter_volume,null);
	        	break;
	        }
        }
        
        LinearLayout bodyLayout = (LinearLayout)findViewById(R.id.LayoutBody);
        bodyLayout.addView(parameterView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        
       	this.initParameter(parameterView);
	}

	/**
	 * Initialize parameters layout widgets
	 * @param bodyView
	 * @since 1.0
	 * @author Arnaud KEIFLIN (arnaud@keiflin.fr)
	 */
	private void initParameter(View bodyView)
	{
		switch (this.profileParameter.getClassId())
		{
			case VolumeParameter.CLASSID :
			{
				this.volumeParameterChangeProfile = (Spinner)bodyView.findViewById(R.id.VolumeParameter_ChangeProfile);
				this.volumeParameterChangeProfile.setOnItemSelectedListener(this);
				//this.volumeParameterChangeProfile.setAdapter(adapter);
				
				this.volumeParameterMainVolumeLabel = (TextView)bodyView.findViewById(R.id.VolumeParameter_MainVolume_Label);
				this.volumeParameterMainVolume = (CheckBox)bodyView.findViewById(R.id.VolumeParameter_MainVolume);
				this.volumeParameterMainVolume.setOnCheckedChangeListener(this);
				this.volumeParameterMainVolumeLevel = (SeekBar)bodyView.findViewById(R.id.VolumeParameter_MainLevel);
				this.volumeParameterMainVolumeLevel.setOnSeekBarChangeListener(this);
				
				this.volumeParameterMediaVolumeLabel = (TextView)bodyView.findViewById(R.id.VolumeParameter_MediaVolume_Label);
				this.volumeParameterMediaVolume = (CheckBox)bodyView.findViewById(R.id.VolumeParameter_MediaVolume);
				this.volumeParameterMediaVolume.setOnCheckedChangeListener(this);
				this.volumeParameterMediaVolumeLevel = (SeekBar)bodyView.findViewById(R.id.VolumeParameter_MediaLevel);
				this.volumeParameterMediaVolumeLevel.setOnSeekBarChangeListener(this);
				
				this.volumeParameterAlarmVolumeLabel = (TextView)bodyView.findViewById(R.id.VolumeParameter_AlarmVolume_Label);
				this.volumeParameterAlarmVolume = (CheckBox)bodyView.findViewById(R.id.VolumeParameter_AlarmVolume);
				this.volumeParameterAlarmVolume.setOnCheckedChangeListener(this);
				this.volumeParameterAlarmVolumeLevel = (SeekBar)bodyView.findViewById(R.id.VolumeParameter_AlarmLevel);
				this.volumeParameterAlarmVolumeLevel.setOnSeekBarChangeListener(this);
				
				this.volumeParameterCallVolumeLabel = (TextView)bodyView.findViewById(R.id.VolumeParameter_CallVolume_Label);
				this.volumeParameterCallVolume = (CheckBox)bodyView.findViewById(R.id.VolumeParameter_CallVolume);
				this.volumeParameterCallVolume.setOnCheckedChangeListener(this);
				this.volumeParameterCallVolumeLevel = (SeekBar)bodyView.findViewById(R.id.VolumeParameter_CallLevel);
				this.volumeParameterCallVolumeLevel.setOnSeekBarChangeListener(this);
				
				this.volumeParameterRingVolumeLabel = (TextView)bodyView.findViewById(R.id.VolumeParameter_RingVolume_Label);
				this.volumeParameterRingVolume = (CheckBox)bodyView.findViewById(R.id.VolumeParameter_RingVolume);
				this.volumeParameterRingVolume.setOnCheckedChangeListener(this);
				this.volumeParameterRingVolumeLevel = (SeekBar)bodyView.findViewById(R.id.VolumeParameter_RingLevel);
				this.volumeParameterRingVolumeLevel.setOnSeekBarChangeListener(this);
				
				if (this.profileParameter.getId() > 0)
				{
					VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
					this.volumeParameterMainVolume.setChecked(volumeParameter.isDefineMainVolume());
					this.volumeParameterMainVolumeLevel.setProgress(volumeParameter.getMainVolume());
					this.volumeParameterMediaVolume.setChecked(volumeParameter.isDefineMediaVolume());
					this.volumeParameterMediaVolumeLevel.setProgress(volumeParameter.getMediaVolume());
					this.volumeParameterAlarmVolume.setChecked(volumeParameter.isDefineAlarmVolume());
					this.volumeParameterAlarmVolumeLevel.setProgress(volumeParameter.getAlarmVolume());
					this.volumeParameterCallVolume.setChecked(volumeParameter.isDefineCallVolume());
					this.volumeParameterCallVolumeLevel.setProgress(volumeParameter.getCallVolume());
					this.volumeParameterRingVolume.setChecked(volumeParameter.isDefineRingVolume());
					this.volumeParameterRingVolumeLevel.setProgress(volumeParameter.getRingVolume());
				}
				break;
			}
		}
			
        
		this.updateWidgets();
	}
	
	/**
     * When pressing menu key, display a specific options menu
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     * @since 1.0
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	getMenuInflater().inflate(R.menu.manageprofile_menu, menu);
    	return true;
    }
	
    /**
     * When a menu item is selected, to specific actions
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     * @since 1.0
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId())
    	{
	    	case R.id.MenuItem_ManageProfileBackItem :
	    	{
	    		this.setResult(Constants.RESULT_CODE_CANCEL);
	    		this.finish();
	    		break;
	    	}
	    	case R.id.MenuItem_ManageProfileSaveItem :
	    	{
	    		this.returnParameter();
	    		this.finish();
				break;
	    	}
    	}
    	return super.onOptionsItemSelected(item);
    }
	
	
	
	/**
	 * @see android.app.Activity#onBackPressed()
	 * @since 1.0
	 */
	@Override
	public void onBackPressed()
	{
		this.returnParameter();
		super.onBackPressed();
	}
	
	/**
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * @since 1.0
	 */
	public void onClick(View v)
	{
		switch (v.getId())
		{
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
			case R.id.VolumeParameter_MainLevel :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setMainVolume(seekBar.getProgress());
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_MediaLevel :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setMediaVolume(seekBar.getProgress());
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_AlarmLevel :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setAlarmVolume(seekBar.getProgress());
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_CallLevel :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setCallVolume(seekBar.getProgress());
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_RingLevel :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setRingVolume(seekBar.getProgress());
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
			case R.id.VolumeParameter_MainVolume :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setDefineMainVolume(isChecked);
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_MediaVolume :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setDefineMediaVolume(isChecked);
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_AlarmVolume :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setDefineAlarmVolume(isChecked);
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_RingVolume :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setDefineRingVolume(isChecked);
				updateWidgets();
				break;
			}
			case R.id.VolumeParameter_CallVolume :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				volumeParameter.setDefineCallVolume(isChecked);
				updateWidgets();
				break;
			}
		}
	}
	
	/**
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android.widget.AdapterView, android.view.View, int, long)
	 * @since 1.0
	 */
	public void onItemSelected(AdapterView<?> arg0,View v,int arg2,long arg3)
	{
		switch (v.getId())
		{
			case R.id.VolumeParameter_ChangeProfile :
			{
				VolumeParameter volumeParameter = (VolumeParameter)this.profileParameter;
				//volumeParameter.setChangeProfile(changeProfile);
				this.updateWidgets();
			}
		}
	}
	public void onNothingSelected(AdapterView<?> v) {}
	
	
	/**
	 * Update button label with the values selected
	 * @since 1.0
	 */
	private void updateWidgets()
	{
		switch (this.profileParameter.getClassId())
		{
			case VolumeParameter.CLASSID :
			{
				// TODO : First, check if a profile is selected

				// If a checkbox is unchecked, then hide seekbar
				this.volumeParameterMainVolumeLevel.setEnabled(this.volumeParameterMainVolume.isChecked());
				this.volumeParameterMediaVolumeLevel.setEnabled(this.volumeParameterMediaVolume.isChecked());
				this.volumeParameterAlarmVolumeLevel.setEnabled(this.volumeParameterAlarmVolume.isChecked());
				this.volumeParameterCallVolumeLevel.setEnabled(this.volumeParameterCallVolume.isChecked());
				this.volumeParameterRingVolumeLevel.setEnabled(this.volumeParameterRingVolume.isChecked());
				
				// Update label depending of values selected
				StringBuilder labelWValue = new StringBuilder(getString(R.string.VolumeParameterMainVolumeLabel));
				if (this.volumeParameterMainVolume.isChecked())
					labelWValue.append(" (").append(this.volumeParameterMainVolumeLevel.getProgress()).append(")");
				this.volumeParameterMainVolumeLabel.setText(labelWValue.toString());
				
				labelWValue = new StringBuilder(getString(R.string.VolumeParameterMediaVolumeLabel));
				if (this.volumeParameterMediaVolume.isChecked())
					labelWValue.append(" (").append(this.volumeParameterMediaVolumeLevel.getProgress()).append(")");
				this.volumeParameterMediaVolumeLabel.setText(labelWValue.toString());

				labelWValue = new StringBuilder(getString(R.string.VolumeParameterAlarmVolumeLabel));
				if (this.volumeParameterAlarmVolume.isChecked())
					labelWValue.append(" (").append(this.volumeParameterAlarmVolumeLevel.getProgress()).append(")");
				this.volumeParameterAlarmVolumeLabel.setText(labelWValue.toString());
				
				labelWValue = new StringBuilder(getString(R.string.VolumeParameterCallVolumeLabel));
				if (this.volumeParameterCallVolume.isChecked())
					labelWValue.append(" (").append(this.volumeParameterCallVolumeLevel.getProgress()).append(")");
				this.volumeParameterCallVolumeLabel.setText(labelWValue.toString());
				
				labelWValue = new StringBuilder(getString(R.string.VolumeParameterRingVolumeLabel));
				if (this.volumeParameterRingVolume.isChecked())
					labelWValue.append(" (").append(this.volumeParameterRingVolumeLevel.getProgress()).append(")");
				this.volumeParameterRingVolumeLabel.setText(labelWValue.toString());
				break;
			}
		}
	}
	
	private void buildActivityTitle()
	{
		final String SEPARATOR = " > "; 
		
		StringBuilder titleSb = new StringBuilder();
        titleSb.append(this.parentTitle).append(SEPARATOR).append(getResources().getString(R.string.Title_Parameter));
        
        switch (this.profileParameter.getClassId())
        {
        	case VolumeParameter.CLASSID :
        	{
        		titleSb.append(SEPARATOR).append(getResources().getString(R.string.Title_VolumeParameter));
        		break;
        	}
        }
        
        setTitle(titleSb.toString());
	}
	
	/**
	 * Return parameter to caller
	 * @since 1.0
	 * @author Arnaud KEIFLIN (arnaud.keiflin@interface-tech.com)
	 */
	private void returnParameter()
	{
		this.profileParameter.build();
		Intent backIntent = new Intent();
		backIntent.putExtra(Constants.EXTRA_KEY_PROFILEPARAMETER,this.profileParameter);
		setResult(Constants.RESULT_CODE_OK,backIntent);
	}
}