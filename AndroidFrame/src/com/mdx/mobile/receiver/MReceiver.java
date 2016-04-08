package com.mdx.mobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

public abstract class MReceiver extends BroadcastReceiver {

	public void regedit(Context context){
		context.registerReceiver(this, this.getIntentFilter());
	}
	
	
	public abstract IntentFilter getIntentFilter();
	
	public void unRegedit(Context context){
		context.unregisterReceiver(this);
	}

}
