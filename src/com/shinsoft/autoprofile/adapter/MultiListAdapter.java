/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 6 oct. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.adapter
 * File name: HashAdapter.java
 *
 * Comments :
 **/

package com.shinsoft.autoprofile.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.parameter.VolumeParameter;

public class MultiListAdapter extends BaseAdapter
{
	private List<String> labels = new ArrayList<String>();
	private List<Integer> values = new ArrayList<Integer>();
	private LayoutInflater mInflater;

	
	public static MultiListAdapter getInstanceForChangeProfile(Context context)
	{
		List<Integer> listRingerModeValue = new ArrayList<Integer>();
		listRingerModeValue.add(VolumeParameter.RINGER_MODE_NOCHANGE);
		listRingerModeValue.add(AudioManager.RINGER_MODE_SILENT);
		listRingerModeValue.add(AudioManager.RINGER_MODE_VIBRATE);
		listRingerModeValue.add(AudioManager.RINGER_MODE_NORMAL);
		
		List<String> listRingerModeLabel = new ArrayList<String>();
		listRingerModeLabel.add(context.getResources().getString(R.string.VolumeParameterChangeProfileNoChangeLabel));
		listRingerModeLabel.add(context.getResources().getString(R.string.VolumeParameterChangeProfileSilentLabel));
		listRingerModeLabel.add(context.getResources().getString(R.string.VolumeParameterChangeProfileVibrationLabel));
		listRingerModeLabel.add(context.getResources().getString(R.string.VolumeParameterChangeProfileNormalLabel));
		
		return new MultiListAdapter(context,listRingerModeLabel,listRingerModeValue);
	}
	
	/**
	 * Default Constructor
	 * @param context
	 * @param labels
	 * @param values
	 * @since 1.0
	 */
	public MultiListAdapter(Context context, List<String> labels, List<Integer> values)
	{
		this.labels = labels;
		this.values = values;
		this.mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * @see android.widget.Adapter#getCount()
	 * @since 1.0
	 */
	public int getCount()
	{
		return labels.size();
	}

	/**
	 * @see android.widget.Adapter#getItemId(int)
	 * @since 1.0
	 */
	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * @see android.widget.Adapter#getItem(int)
	 * @since 1.0
	 */
	public Object getItem(int position)
	{
		return labels.get(position);
	}

	/**
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 * @since 1.0
	 */
	public View getView(int position,View convertView,ViewGroup parent)
	{
		return createResource(position,convertView,parent,android.R.layout.simple_spinner_item);
	}

	/**
	 * @see android.widget.BaseAdapter#getDropDownView(int, android.view.View, android.view.ViewGroup)
	 * @since 1.0
	 */
	public View getDropDownView(int position,View convertView,ViewGroup parent)
	{
		return createResource(position,convertView,parent,android.R.layout.simple_spinner_dropdown_item);

	}

	/**
	 * Create a view
	 * @param position
	 * @param convertView
	 * @param parent
	 * @param resource
	 * @return
	 * @since 1.0
	 */
	public View createResource(int position,View convertView,ViewGroup parent,int resource)
	{
		ViewHolder holder;
		if (convertView == null)
		{
			convertView = mInflater.inflate(resource,parent,false);
			holder = new ViewHolder();
			holder.text = (TextView)convertView.findViewById(android.R.id.text1);
			holder.value = this.values.get(position);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder)convertView.getTag();

		holder.text.setText(getItem(position).toString());

		return convertView;
	}

	/**
	 * Internal holder class
	 * <br>
	 * Versions : <br>
	 *
	 *   <li>1.0 : Initialisation</li>
	 * <br>
	 * @version 1.0
	 */
	public class ViewHolder
	{
		private TextView text;
		private Integer value;
		
		public Integer getValue()
		{
			return value;
		}
	}

}
