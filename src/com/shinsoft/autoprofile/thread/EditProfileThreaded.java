/** 
 * Arnaud KEIFLIN (arnaud@keiflin.fr), 18 sept. 2010
 *
 * Project : AutoProfile
 * Package location: com.shinsoft.autoprofile.thread
 * File name: EditProfileThread.java
 *
 * Comments :
 **/


package com.shinsoft.autoprofile.thread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.shinsoft.autoprofile.R;
import com.shinsoft.autoprofile.activity.ManageProfileActivity;
import com.shinsoft.autoprofile.manager.Constants;

public class EditProfileThreaded implements Runnable
{
	private ProgressDialog progressDialog;
	private int position;
	private Activity context;
	
	public static void launch(Activity context, int position)
	{
		new EditProfileThreaded(context,position);
	}
	
	public EditProfileThreaded(Activity context, int position)
	{
		this.position = position;
    	this.context = context;
		this.progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.loading), context.getResources().getString(R.string.please_wait), true,false);
		Thread thread = new Thread(this);
		thread.start();
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			progressDialog.dismiss();
		}
	};
	
	public void run() 
	{
		this.editSelectedProfile();
		handler.sendEmptyMessage(0);
	}
	
    /**
     * Call edition activity and provide the currentProfileInfo
     * @param position
     * @since 1.0
     */
    private void editSelectedProfile()
	{
    	Intent intent = new Intent(context, ManageProfileActivity.class);
    	intent.putExtra(Constants.EXTRA_KEY_PROFILE_POSITION,position);
    	context.startActivityForResult(intent,Constants.REQUEST_CODE_EDITPROFILE);
	}
}