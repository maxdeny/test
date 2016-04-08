package com.mdx.mobile;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.mdx.mobile.cache.Cache;
import com.mdx.mobile.manage.Handles;
import com.mdx.mobile.manage.ImageLoad;
import com.mdx.mobile.manage.UrlIconLoad;
import com.mdx.mobile.manage.UrlImageLoad;
import com.mdx.mobile.mcommons.AppInfo;
import com.mdx.mobile.mcommons.MContact;
import com.mdx.mobile.mcommons.MContacts;
import com.mdx.mobile.mcommons.MMap;

public class Frame {
    public static Context CONTEXT;
    
    public static Handles HANDLES = new Handles();
    
    public static InitConfig INITCONFIG = new InitConfig();
    
    public static final String TempPath = "";
    
    public static Cache<Object, BitmapDrawable, String> ImageCache = new Cache<Object, BitmapDrawable, String>();
    
    public static Cache<Object, BitmapDrawable, String> IconCache = new Cache<Object, BitmapDrawable, String>();
    
    public static ImageLoad IMAGELOAD = new UrlImageLoad(ImageCache);
    
    public static ImageLoad ICONLOAD = new UrlIconLoad(IconCache);
    
    public static String[][] AUTOADDPARMS = null;
    
    public static MMap MAP;
    
    private static boolean Frame_inited = false;
    
    public static boolean OnlyWifiLoadImage = false;
    
    private static String IMSI, PROVIDERSNAME, MACADDRESS, NETWORKTYPE, NETWORKSUBTYPE, NETWORKSUBNAME;
    
    /**
     * 设备MODEL
     * 
     * @return
     */
    public static String getModel() {
        return android.os.Build.MODEL;
    }
    
    /**
     * 供应商
     * 
     * @return
     */
    public static String getBrand() {
        return android.os.Build.BRAND;
    }
    
    /**
     * 设备名称
     * 
     * @return
     */
    public static String getDevice() {
        return android.os.Build.DEVICE;
    }
    
    /**
     * 制造商
     * 
     * @return
     */
    public static String getFacturer() {
        return android.os.Build.MANUFACTURER;
    }
    
    /**
     * sdk版本
     * 
     * @return
     */
    public static String getReleaseVersion() {
        String version = android.os.Build.VERSION.RELEASE;
        if (version.toUpperCase(Locale.ENGLISH).indexOf("ANDROID") >= 0) {
            version = "android " + version;
        }
        return version;
    }
    
    /**
     * 获取网络类型
     * 
     * @param context
     * @return
     */
    public static String getNetWorkSubName(Context context) {
        getNetworkType(context);
        return NETWORKSUBNAME;
    }
    
    /**
     * 获取网络类型描述
     * 
     * @param context
     * @return
     */
    public static String getNetWorkSubType(Context context) {
        getNetworkType(context);
        return NETWORKSUBTYPE;
    }
    
    /**
     * 获取手机串号
     * 
     * @param context
     * @return
     */
    public static String getImsi(Context context) {
        if (IMSI == null) {
            TelephonyManager telephonemanage = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            IMSI = telephonemanage.getDeviceId();
            PROVIDERSNAME = telephonemanage.getNetworkOperatorName();
        }
        return IMSI;
    }
    
    public static String getDeviceid(Context context) {
        String ren = getImsi(context);
        if (ren != null && ren.length() > 0) {
            return ren;
        }
        return getMacAddress(context);
    }
    
    /**
     * 获取运营商
     * 
     * @param context
     * @return
     */
    public static String getProvidersName(Context context) {
        getImsi(context);
        return PROVIDERSNAME;
    }
    
    /**
     * 获取mac地址
     * 
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        if (MACADDRESS == null) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            MACADDRESS = info.getMacAddress();
        }
        return MACADDRESS;
    }
    
    /**
     * 获取网络类型
     * 
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conMan.getActiveNetworkInfo();
        if (info == null) {
            NETWORKTYPE = "NONE";
            NETWORKSUBNAME = "NONE";
            NETWORKSUBTYPE = "NONE";
            return NETWORKTYPE;
        }
        switch (info.getType()) {
            case ConnectivityManager.TYPE_MOBILE:
                NETWORKTYPE = "MOBILE";
                NETWORKSUBTYPE = info.getSubtypeName();
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        NETWORKSUBNAME = "unknown";
                        break;
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                        NETWORKSUBNAME = "2G 1xRTT";
                        break;
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        NETWORKSUBNAME = "2G CDMA";
                        break;
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                        NETWORKSUBNAME = "2G EDGE";
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        NETWORKSUBNAME = "3G EVDO_0";
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        NETWORKSUBNAME = "3G EVDO_A";
                        break;
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                        NETWORKSUBNAME = "2G GPRS";
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        NETWORKSUBNAME = "HSDPA";
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                        NETWORKSUBNAME = "HSPA";
                        break;
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                        NETWORKSUBNAME = "HSUPA";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                        NETWORKSUBNAME = "UMTS";
                        break;
                }
                break;
            case ConnectivityManager.TYPE_WIFI:
                NETWORKTYPE = "WIFI";
                NETWORKSUBNAME = "WIFI";
                NETWORKSUBTYPE = "WIFI";
                break;
            default:
                NETWORKTYPE = "NONE";
                NETWORKSUBNAME = "NONE";
                NETWORKSUBTYPE = "NONE";
                break;
        }
        return NETWORKTYPE;
    }
    
    public static void init(Context context) {
        if (!Frame_inited) {
            CONTEXT = context.getApplicationContext();
            reInit(context);
        }
    }
    
    public static byte[] getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo info = iter.next();
            String packageName = info.packageName;
            if (packageName.equals("com.test.test")) {
                return info.signatures[0].toByteArray();
            }
        }
        return null;
    }
    
    public static byte[] getSelfSign(Context context) throws NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo app = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
        return app.signatures[0].toByteArray();
    }
    
    public static String getPublicKey(byte[] signature) {
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            
            String publickey = cert.getPublicKey().toString();
            publickey = publickey.substring(publickey.indexOf("modulus: ") + 9,
                    publickey.indexOf("\n", publickey.indexOf("modulus:")));
            return publickey;
        }
        catch (CertificateException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void destory() {
        HANDLES.closeAll();
        if (MAP != null) {
            MAP.distory();
        }
    }
    
    public static void reInit(Context context) {
        INITCONFIG = new InitConfig(context);
        if (INITCONFIG.mMapKey != null && INITCONFIG.mMapKey.length() > 0) {
            MAP = new MMap(context, null);
        }
        Frame_inited = true;
    }
    
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        }
        else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分前";
        }
        else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        }
        else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }
    
    public static String getFileSize(Object obj) {
        if (obj == null) {
            return "";
        }
        long sized = Long.parseLong(obj.toString());
        double size = sized;
        if (size > 1024) {
            size = size / 1024;
            if (size > 1024) {
                size = size / 1024;
                if (size > 1024) {
                    size = size / 1024d;
                    return Dou2Str(size, 2) + " GB";
                }
                else {
                    return Dou2Str(size, 2) + " MB";
                }
            }
            else {
                return Dou2Str(size, 0) + " KB";
            }
        }
        else {
            return Dou2Str(size, 0) + " B";
        }
    }
    
    public static String Dou2Str(double d, int count) {
        double dou = Math.abs(d);
        int rate = 1;
        for (int i = 0; i < count; i++) {
            rate *= 10;
        }
        dou = Math.round(dou * rate);
        dou = dou / rate;
        if (dou - Math.round(dou) == 0) {
            return (d < 0 ? "-" : "") + String.valueOf((int) dou);
        }
        return (d < 0 ? "-" : "") + String.valueOf(dou);
    }
    
    public static boolean checkNetWork(Context context) {
        ConnectivityManager nw = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = nw.getActiveNetworkInfo();
        if (netinfo == null) {
            return false;
        }
        return netinfo.isAvailable();
    }
    
    public static int getNetWorkType(Context context) {
        ConnectivityManager nw = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = nw.getActiveNetworkInfo();
        if (netinfo == null) {
            return 0;
        }
        if (!netinfo.isAvailable()) {
            return 0;
        }
        if (netinfo.getTypeName().toUpperCase(Locale.ENGLISH).equals("WIFI")) {
            return 1;
        }
        return 2;
        
    }
    
    public static List<AppInfo> getAppList(Context context) {
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        List<AppInfo> list = new ArrayList<AppInfo>();
        for (PackageInfo packinfo : packages) {
            AppInfo apk = new AppInfo();
            ApplicationInfo app = packinfo.applicationInfo;
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                apk.setName(app.loadLabel(context.getPackageManager()).toString());
                apk.setPackage(packinfo.packageName);
                apk.setVersion(packinfo.versionCode);
                apk.setVersionName(packinfo.versionName);
                if (!apk.getPackage().startsWith("com.wjwl.apkfactory")) {
                    list.add(apk);
                }
            }
        }
        return list;
    }
    
    public static Drawable getIcon(Context context, String packageName) throws NameNotFoundException {
        PackageInfo pack = context.getPackageManager().getPackageInfo(packageName, 0);
        ApplicationInfo app = pack.applicationInfo;
        return app.loadIcon(context.getPackageManager());
    }
    
    public static AppInfo getApp(Context context, String packageName) throws NameNotFoundException {
        PackageInfo pack = context.getPackageManager().getPackageInfo(packageName, 0);
        AppInfo apk = new AppInfo();
        ApplicationInfo app = pack.applicationInfo;
        apk.setName(app.loadLabel(context.getPackageManager()).toString());
        apk.setPackage(pack.packageName);
        apk.setVersion(pack.versionCode);
        apk.setVersionName(pack.versionName);
        return apk;
    }
    
    public static void install(Context context, String path) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    public static void open(Context context, String packag) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packag);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    
    public static void deleteApp(Context context, String packag) {
        Uri packageURI = Uri.parse("package:" + packag);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
    }
    
    public static List<MContact> getContacts(Context context) {
        return getContacts(context, null);
    }
    
    public static List<MContact> getContacts(Context context, MContacts.OnContactAddListener onadd) {
        MContacts conts = new MContacts();
        return conts.getContact(context, onadd);
    }
    
    public static Drawable getContantPhoto(Context context, MContact contact) {
        MContacts conts = new MContacts();
        return conts.getPhoto(context, contact);
    }
    
    public static void noty(Context context, int icon, String tickertext, String title, String text, Class<?> clas,
            Object bundle, int id) {
        if (context == null) {
            return;
        }
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
        mNotificationManager.cancel(id);
        CharSequence tickerText = tickertext;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        CharSequence contentTitle = title;
        CharSequence contentText = text;
        Intent notificationIntent = new Intent(context, clas);
        if (bundle != null) {
            if (bundle instanceof Bundle) {
                notificationIntent.putExtras((Bundle) bundle);
            }
            else if (bundle instanceof Map) {
                for (Object key : ((Map<?, ?>) bundle).keySet()) {
                    Object v = ((Map<?, ?>) bundle).get(key);
                    if (v instanceof Serializable) {
                        notificationIntent.putExtra(key.toString(), (Serializable) v);
                    }
                    else if (v instanceof Boolean) {
                        notificationIntent.putExtra(key.toString(), (Boolean) v);
                    }
                    else if (v instanceof String) {
                        notificationIntent.putExtra(key.toString(), (String) v);
                    }
                    else if (v instanceof Integer) {
                        notificationIntent.putExtra(key.toString(), (Integer) v);
                    }
                    else if (v instanceof Float) {
                        notificationIntent.putExtra(key.toString(), (Float) v);
                    }
                    else if (v instanceof Double) {
                        notificationIntent.putExtra(key.toString(), (Double) v);
                    }
                }
            }
        }
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        mNotificationManager.notify(id, notification);
    }
}
