package com.zhuolei.mobilesafe.activitycontrol;

import android.app.Activity;
import android.util.Log;

public class ActivityControl {
	
	private static final String TAG = "ActivityControl";
	private static ActivityControl instance;
	
	public static ActivityControl getInstance() {
		
		if(instance == null) {
			instance = new ActivityControl();
		}
		return instance;
		
	}
	
	public boolean finishActivity(Activity win) {
		
		try{
			win.onBackPressed();
		}catch(Exception e) {
			Log.v(TAG, e.getMessage());
		}
		
		return false;		
	}
	
}
