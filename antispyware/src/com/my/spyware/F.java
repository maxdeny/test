package com.my.spyware;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.czxc.top.zgjyzs.jsonclass.Address;
import com.czxc.top.zgjyzs.jsonclass.Address.Msg_Address;
import com.mdx.mobile.Frame;
import com.mdx.mobile.cache.disc.ImageStoreCacheManage;
import com.mdx.mobile.widget.MImageView;

/**
 * 常量信息
 * 
 * @author ljl
 * @version [2014-7-16 上午11:01:50]
 */
public class F {
    // uri
    //    public static final String HOST = "http://10.49.188.109/dw/mobile";//内网
    
    public static final String HOST = "http://192.168.1.100:8080/dw/mobile";
    
    //    public static final String HOST = "http://wangcunzhou.vicp.net:33382/dw/mobile";//外网
    
    public static String USER_ID = "", VERIFY = "", DEVICEID = "", MACADDRESS = "";
    
    public static String ACCOUNT = "";
    
    public static String businessid = "", APKID = "", PASSWORD = "";
    
    public static String[] modelnames;
    
    public static String[] modelids;
    
    /** @Fields version : 系统版本号 */
    public static int version;
    
    public static boolean login, register, userData = false, AutoLogin = false;
    
    /** @Fields PAGECOUNT : 分页数 */
    public static final int PAGECOUNT = 12;
    
    /** @Fields BAIDU_MAP_APIKEY : 百度地图key 若使用2.1.2版本key可以任意使用 */
    public final static String BAIDU_MAP_APIKEY = "1F8F37297F20C7BB16E7A72D71AC6DA47D22FD89";
    
    /** @Fields BAIDU_SHARE_KEY : 百度分享key */
    public final static String BAIDU_SHARE_KEY = "1F8F37297F20C7BB16E7A72D71AC6DA47D22FD89";
    
    public static int longitude = 120000411, latitude = 31818781;/* 默认经纬度坐标公司 */
    
    public static String MYLOCAL = "您目前在:未知";/* 默认当前位置 */
    
    public final static String DB_USER = "db_user";/* sqlite用户信息 */
    
    public final static String DB_DOWNLOAD = "db_download";/* sqlite下载记录 */
    
    /** @Fields location : 百度地图地理位置 */
    public static BDLocation location;
    
    /** @Fields JPUSH_REGISTRATION_ID : JPUSH 设备注册id */
    public static String JPUSH_REGISTRATION_ID = "";
    
    /** @Fields SP_PUSH_TAG : 推送配置 */
    public final static String SP_PUSH_TAG = "my_push";
    
    public static BDLocation mLocation;
    
    public static int screenWidth, screenHeight;
    
    public static List<String> cityNames;// 城市列表目前用本地数据
    
    public static Address.Msg_AddressList.Builder ADDRESSBUILD;// 本地城市列表数据
    
    public static List<Msg_Address> provinces;// 本地城市列表数据
    
    public static List<String> provinceNames;
    
    public static String locCity = "";// 当前定位城市
    
    public static String userId = "";
    
    public static String verify = "";
    
    public static boolean isPlaySound = true;//接收到推送后是否播放声音
    
    public static boolean isVibrate = true;//接收推送后是否振动
    
    public static String uuid = "";//第三方登录注册用的uuid
    
    public static long sendLocationTime = 30000;//向后台发送位置的时间间隔 30s
    
    public static boolean isPushOk = true;
    
    /**
     * 
     * @author ljl
     * @Description: TODO
     * @param @param context getApplicationContext() 全局上下文，保证字段不会因内存问题销毁
     * @return void
     * @throws
     */
    public static void init(Context context) {
        cityNames = new ArrayList<String>();
        try {
            TelephonyManager telephonemanage = (TelephonyManager) context.getSystemService("phone");
            DEVICEID = telephonemanage.getDeviceId().replace(":", "");
            //            DEVICEID = AbDeviceUtil.getImei(context).replace(":", "");
            //            version = AbAppUtil.getApp(context, context.getPackageName()).getVersion();
            {
                Properties pro = new Properties();
                InputStream is = context.getAssets().open("bruts.properties");
                pro.load(is);
                businessid = (String) pro.get("bussesid");
                APKID = (String) pro.get("apkId");
                login = Boolean.valueOf(pro.get("login").toString());
                register = Boolean.valueOf(pro.get("register").toString());
                modelnames = pro.get("ModuleNames").toString().split(",");
                modelids = pro.get("ModuleIdsIds").toString().split(",");
                is.close();
            }
        }
        catch (Exception e) {
        }
    }
    
    /** 默认传参 */
    public static void setAutoPost() {
        // HashMap<String, String> hashMap = new HashMap<String, String>();
        // hashMap.put("userId", F.userId);
        // hashMap.put("verify", F.verify);
        // hashMap.put("deviceid", F.DEVICEID);
        // Frame.setAutoAddParms(hashMap);
        //
        //
        // Frame.AUTOADDPARMS = new String[][] { { "appid", F.APKID }, {
        // "api_platform", "1" },
        // { "api_version",
        // Frame.INITCONFIG.thridKey.getAichuan_srccode_android() } };
    }
    
    /** 获取登录保存的数据 */
    public static void getLoginData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("LoginCookie", Context.MODE_PRIVATE);
        F.userId = preferences.getString("USER_ID", "");
        F.verify = preferences.getString("VERIFY", "");
        
    }
    
    public static void setLogin(Context context, String userId, String verify) {
        SharedPreferences preferences = context.getSharedPreferences("LoginCookie", Context.MODE_PRIVATE);
        F.userId = userId;
        F.verify = verify;
        Editor edit = preferences.edit();
        edit.putString("USER_ID", userId);
        edit.putString("VERIFY", verify);
        edit.commit();
        //        setAutoPost();
    }
    
    /**
     * 清除缓存
     * 
     * @author Administrator
     * @Title: close
     * @Description: TODO
     * @param @param context 为null 不删除图片sd卡缓存；若非null 则删除
     * @return void
     * @throws
     */
    public static void close(Context context) {
        Frame.HANDLES.closeAll();
        Frame.IconCache.clean();
        if (null == context)
            return;
        File dPath = ImageStoreCacheManage.getDPath(context);
        if (dPath.isDirectory()) {
            dPath.delete();
        }
    }
    
    public static void setPush(Context context) {
        if (isPushOk) {
            JPushInterface.resumePush(context.getApplicationContext());
        }
        else {
            JPushInterface.stopPush(context.getApplicationContext());
        }
    }
    
    /**
     * 拼接url
     * 
     * @author ljl
     * @Title: getFullUrl
     * @Description: TODO
     * @param @param url
     * @param @return
     * @return String
     * @throws
     */
    public static String getFullUrl(String url) {
        if (null == url) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        if (url.indexOf("http://") >= 0 || url.indexOf("https://") >= 0) {
            sb.append(url);
        }
        else {
            sb.append(Frame.INITCONFIG.getUri() + "/" + url);
        }
        return sb.toString();
    }
    
    /** 图片拼接路径 */
    public static String getFullUrl(boolean useCDN, String url, MImageView view) {
        if (TextUtils.isEmpty(url) || view == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        if (url.indexOf("http://") >= 0 || url.indexOf("https://") >= 0) {
            sb.append(url);
        }
        else {
            sb.append(Frame.INITCONFIG.getUri() + "/" + url);
        }
        // 拼接方法
        int w = (view.getWidth() == 0 ? 70 : view.getWidth());
        int h = 0;
        if (useCDN) {
            sb.append(URLEncoder.encode("?fileImgSize") + "=" + URLEncoder.encode(w + "x" + h));
        }
        else {
            sb.append("&fileImgSize" + "=" + w + "x" + h);
        }
        return sb.toString();
    }
    
    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    
    public static void showToast(Context context, int text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        else if (str.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public static void close() {
        Frame.HANDLES.closeAll();
        Frame.IconCache.clean();
    }
    
    public static void getWindowInfo(Activity activity) {
        // TODO Auto-generated method stub
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        
    }
    
    /**
     * 转时间为00:00格式 ToDo：
     * 
     * @author Administrator
     * @param time
     *            分钟
     * @return
     * @return String
     * @throws
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            }
            else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }
    
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
    
}
