package com.wjwl.mobile.taocz.services;

import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FrameAg;
import com.wjwl.mobile.taocz.act.HotRecommendAct;

public class AutoNotService extends Service {

	public static AutoNotService SERVICE = null;
	public static boolean ServerRun = true;
	public Thread threadRun;
	public static Map<String, String> newcontentid = new HashMap<String, String>();
	public static Map<String, String> tempcontentid = new HashMap<String, String>();

	@Override
	public void onCreate() {
		super.onCreate();
		SERVICE = this;
		ServerRun = true;
		threadRun = new MyThread();
		threadRun.start();
	}

	public synchronized void stopServer() {
		if (SERVICE != null && SERVICE.threadRun != null) {
			ServerRun = false;
			SERVICE.threadRun.interrupt();
		}
	}

	public synchronized static void start(Context context) {
		if (SERVICE == null) {
			Intent intent = new Intent(context, AutoNotService.class);
			intent.setFlags(BIND_AUTO_CREATE);
			context.startService(intent);
		}
	}

	public synchronized static void stop() {
		if (SERVICE != null) {
			SERVICE.stopServer();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		SERVICE = null;
	}

	class MyThread extends Thread {

		public MyThread() {
		}

		@Override
		public void run() {
			threadRun = this;
			while (ServerRun) {
				try {
					if (Frame.checkNetWork(AutoNotService.this)) {
						Bundle bundle =new Bundle();
						bundle.putString("id", F.RecommendID);
							F.noty(AutoNotService.this,
									R.drawable.default_icon, "更多优惠,就在淘常州",
									"淘常州今日优惠信息", "淘常州今日优惠信息",
									HotRecommendAct.class, bundle, 100);
					}
				} catch (Exception e) {
				}
				try {
					sleep(1000 * 60 * 60*24);
				} catch (InterruptedException e) {}
			}
			threadRun = null;
			SERVICE.stopSelf();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
