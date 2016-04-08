package com.zhuolei.mobilesafe.thread;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

public class AutoStartScanThread1 implements Runnable{
	private Context context;
	private List<ResolveInfo> resolveInfo;
	public AutoStartScanThread1(Context context){
		this.context = context;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Intent intent = new Intent("android.intent.action.BOOT_COMPLETED"); 
		resolveInfo = context.getPackageManager().queryBroadcastReceivers(intent, 0);
	}

	
}
