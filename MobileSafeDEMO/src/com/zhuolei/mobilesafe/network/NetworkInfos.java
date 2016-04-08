package com.zhuolei.mobilesafe.network;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.zhuolei.mobilesafe.main.MainActivity;
import com.zhuolei.mobilesafe.main.TabhostActivity;

public class NetworkInfos {
	
	private static final String TAG = "NetworkInfos";
	private static NetworkInfos networkInfo;
	
	public static NetworkInfos getNetworkInfo(){
		
		if(networkInfo == null) {
			networkInfo = new NetworkInfos();
		}
		return networkInfo;
	}
	
	public boolean isConn(Context context){
		
		boolean bConnFlag = false;
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = connManager.getActiveNetworkInfo();

		if(network != null){
			Log.v(TAG, String.valueOf(network.getType()));
			bConnFlag = connManager.getActiveNetworkInfo().isAvailable();
		}
		return bConnFlag;

	}

	public void setNetwork(final Context context) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("网络设置")
				   .setMessage("网络不可用，是否进行设置？")
				   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							Intent intent = null;
							if(android.os.Build.VERSION.SDK_INT > 10) {
								intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
							}else{
								intent = new Intent();
								ComponentName comp = new ComponentName("com.android.settings","com.android.settings.WirelessSettiings");
								intent.setComponent(comp);
								intent.setAction("android.intent.action.VIEW");
							
							}
							context.startActivity(intent);
							
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							System.exit(0);
						}
						
					}).show();
	}
	
	
}
