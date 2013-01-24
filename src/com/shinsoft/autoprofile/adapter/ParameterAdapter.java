/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 11 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.adapter
 * File name: ParametersAdapter.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shinsoft.autoprofile.AutoProfile;
import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.parameter.utils.ProfileParameter;

@SuppressWarnings("rawtypes")
public class ParameterAdapter extends ArrayAdapter
{
	private static final String TAG = AutoProfile.class.getSimpleName();
	
	// Class property
	private List<ProfileParameter> listParameters;
	private LayoutInflater inflater;
	
	/**
	 * Default Constructor
	 * @param context
	 * @param listParameters
	 * @since 1.0
	 */
	public ParameterAdapter(Activity context, List<ProfileParameter> listParameters)
	{
		super(context,R.layout.rowview_condition);
		this.inflater = LayoutInflater.from(context);
		this.listParameters = listParameters;
	}

	/**
	 * Build a custom view for each parameter
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 * @since 1.0
	 */
	@Override
	public View getView(int position,View parameterRow,ViewGroup parent)
	{
		ViewHolder holder;
		ProfileParameter profileParam = this.listParameters.get(position);
		
		// If convertView is null then inflate profileView layout
		if (parameterRow == null)
		{
			holder = new ViewHolder();
			parameterRow = inflater.inflate(R.layout.rowview_parameter,null);
			
			// Retrieve all layout widgets
			holder.parameterIcon = (ImageView)parameterRow.findViewById(R.id.ListParameterIcon);
			holder.parameterName = (TextView)parameterRow.findViewById(R.id.ListParameterName);
			holder.parameterParameters = (TextView)parameterRow.findViewById(R.id.ListParameterParameters);
			
			parameterRow.setTag(holder);
		}
		else
			holder = (ViewHolder)parameterRow.getTag();
		
		// Configure widgets
		holder.parameterName.setText(profileParam.getNameId());
		holder.parameterParameters.setText(profileParam.getParametersSummary((Activity)getContext()));
		holder.parameterIcon.setImageResource(profileParam.getIconResourceId());
		
		return (parameterRow);
	}
	
	
	@Override
	public int getCount()
	{
		return this.listParameters.size();
	}
	@Override
	public Object getItem(int position)
	{
		return this.listParameters.get(position);
	}
	@Override
	public long getItemId(int position)
	{
		return position;
	}
	
	
	/**
	 * Internal class to hold all list row widgets
	 * <br>
	 * Versions : <br>
	 *
	 *   <li>1.0 : Initialisation</li>
	 * <br>
	 * @version 1.0
	 */
	public class ViewHolder
	{
		private ImageView parameterIcon;
		private TextView parameterName;
		private TextView parameterParameters;
	}
}