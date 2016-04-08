package com.mdx.mobile.server;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

import com.mdx.mobile.base.Notify_Data.Msg_Notify_Data.Builder;
import com.mdx.mobile.log.MLog;
import com.mdx.mobile.server.MQTTConnection.OnContentListener;

public class NotifyService extends Service {
	public static final String TAG = "DemoPushService";
	public static final String PREF_STARTED = "isStarted";
	public static final String PREF_DEVICE_ID = "deviceID";
	public static final String PREF_RETRY = "retryInterval";
	public static String MQTT_HOST = "tcp://notify.iappk.com:1883";
	private static long KEEP_ALIVE_INTERVAL = 1000 * 60;
	private static NotifyService mself;
	private static String MQTT_CLIENT_ID = "iappk";
	private static String INITTOPIC = "";
	private NotificationManager mNotifMan;
	private static MQTTConnection mConnection;
	private SharedPreferences mPrefs;
	private boolean mStarted, isInited = false;
	private static OnNotifyListener ONNOTIFYLISTENER;

	public static void actionStart(Context ctx) {
		Log.d("ft", "start");
		ctx.getSharedPreferences(TAG, MODE_PRIVATE).edit()
				.putBoolean(PREF_STARTED, true).commit();
		Intent i = new Intent(ctx, NotifyService.class);
		ctx.startService(i);
	}

	public static void actionStop(Context ctx) {
		Log.d("ft", "stop");
		ctx.getSharedPreferences(TAG, MODE_PRIVATE).edit()
				.putBoolean(PREF_STARTED, false).commit();
		
		if (mself != null) {
			mself.stopSelf();
		}
	}
	
	public static boolean isStarted(Context context){
		return 	context.getSharedPreferences(TAG, MODE_PRIVATE).getBoolean(PREF_STARTED, false);
	}

	public static String getClientId() {
		return MQTT_CLIENT_ID;
	}

	public static void setNotify(Context ctx, String topic,  Class<?> clas){
		setNotify(ctx,topic,null,null,0,clas);
	}
	
	public static void setNotify(Context ctx, String topic, String clientid,  Class<?> clas){
		setNotify(ctx,topic,clientid,null,0,clas);
	}
	
	public static void setNotify(Context ctx, String topic, String clientid,
			String host, long interval, Class<?> clas) {
		Editor edit = ctx.getSharedPreferences("NOTIFYPARAMS", MODE_PRIVATE).edit();
		if(topic!=null){
			edit.putString("TOPIC", topic);
		}
		if(clientid!=null){
			edit.putString("CLIENTID", clientid);
		}
		if(host!=null){
			edit.putString("HOST", host);
		}
		if(interval>0){
			edit.putLong("INTERVAL", interval);
		}
		if(clas!=null){
			edit.putString("CLASS", clas.getName());
		}
		edit.commit();
	}

	private void initData() {
		if (isInited) {
			return;
		}
		SharedPreferences sp = getSharedPreferences("NOTIFYPARAMS",	MODE_PRIVATE);
		INITTOPIC = sp.getString("TOPIC", "");
		MQTT_CLIENT_ID = sp.getString("CLIENTID", MQTT_CLIENT_ID);
		MQTT_HOST = sp.getString("HOST", MQTT_HOST);
		KEEP_ALIVE_INTERVAL = sp.getLong("INTERVAL", KEEP_ALIVE_INTERVAL);
		String clas = sp.getString("CLASS", null);
		if (clas != null) {
			try {
				Class<?> notify=Class.forName(clas);
				Object obj=notify.newInstance();
				if(obj!=null && obj instanceof OnNotifyListener){
					ONNOTIFYLISTENER=(OnNotifyListener) obj;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initData();
		mself = this;
		mNotifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
		handleCrashedService();
	}

	private void handleCrashedService() {
		if (wasStarted() == true) {
			stopKeepAlives();
			start();
		}
	}

	private synchronized void start() {
		if (mStarted == true) {
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				connect();
			}
		}).start();
	}

	private synchronized void connect() {
		try {
			mConnection = new MQTTConnection(MQTT_HOST, INITTOPIC, this);
			mConnection.setOnContentListener(mContentListener);
			mConnection.connect();
		} catch (Exception e) {
			e.printStackTrace();
			stopSelf();
			startKeepAlives();
		}
		mStarted = true;
	}

	private synchronized void stop() {
		if (mStarted == false) {
			return;
		}
		mStarted = false;
		if (mConnection != null) {
			mConnection.disconnect();
			mConnection = null;
		}
		if (!mPrefs.getBoolean(PREF_STARTED, true)) {
			stopKeepAlives();
		}
	}

	private void startKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, NotifyService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + KEEP_ALIVE_INTERVAL,
				KEEP_ALIVE_INTERVAL, pi);
	}

	private void stopKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, NotifyService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private boolean wasStarted() {
		return mPrefs.getBoolean(PREF_STARTED, true);
	}

	@Override
	public void onDestroy() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				stop();
			}
		}).start();
		super.onDestroy();
		mself = null;
		actionStart(this );
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private OnContentListener mContentListener = new OnContentListener() {

		@Override
		public void onRecive(Builder build) {
			MLog.D(build.getNotifyDescribe());
			if (ONNOTIFYLISTENER != null) {
				ONNOTIFYLISTENER.onNotify(getApplicationContext(), build,
						mNotifMan);
			}
		}

		@Override
		public void onContentLost() {
			startKeepAlives();
			stopSelf();
			MLog.D("stop");
		}

		@Override
		public void onContent() {
			MLog.D("start");
		}
	};

	public interface OnNotifyListener {
		public void onNotify(Context context, Builder build,
				NotificationManager NotifMan);
	}
}
