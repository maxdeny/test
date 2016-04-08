package com.mdx.mobile.service;

import com.mdx.mobile.commons.threadpool.PRunable;
import com.mdx.mobile.commons.threadpool.ThreadPool;
import com.mdx.mobile.commons.threadpool.ThreadPool.OnThreadEmpty;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public abstract class MService extends Service {
	public static MService SERVICE = null;
	public ThreadPool threadpool=new ThreadPool();
	
	@Override
	public void onCreate() {
		super.onCreate();
		threadpool.setOnThreadEmpty(new OnThreadEmpty() {
			@Override
			public void onThreadEmpty() {
				SERVICE.stopSelf();
			}
		});
		SERVICE = this;
		create();
	}

	public synchronized void stopServer() {
		if (SERVICE != null) {
			if(threadpool.getRuning().size()+threadpool.getWatrun().size()>0){
				threadpool.intermitAll();
			}
		}
	}

	public synchronized static void start(Context context,Class<?> clas) {
		if (SERVICE == null) {
			Intent intent = new Intent(context,clas);
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

	protected void execute(PRunable runable){
		if(SERVICE!=null){
			threadpool.execute(runable);
		}
	}

	protected abstract void create();
	
	public void setPoolSize(int size){
		this.threadpool.setMaxThreadSize(size);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
