package com.beatle.lg.carriage;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.beatle.lg.carriage.data.CityInfo;
import com.czxc.top.zgjyzs.jsonclass.Address;
import com.czxc.top.zgjyzs.jsonclass.Address.Msg_Address;
import com.mdx.mobile.Frame;
import com.mdx.mobile.cache.ImageStoreCacheManage;
import com.mdx.mobile.widget.MImageView;

/**
 * 常量信息
 */
public class F {

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

	public static String userId = "";// 用户id

	public static String carrierId = "";

	public static String deviceId = "";

	public static String mobile = "15295067010";

	public static boolean isPushOk = true;

	public static final String ALIHOST = "http://hhyapp.oss-cn-shanghai.aliyuncs.com/";

	public static String selectCity = "";// 通过城市列表选择的当前城市

	public static String selectStartAddress = "";// 出发城市

	public static String selectEndAddress = "";// 到达城市

	public static List<CityInfo> cityList;

	/**
	 * 
	 * @author
	 * @Description: TODO
	 * @param @param context getApplicationContext() 全局上下文，保证字段不会因内存问题销毁
	 * @return void
	 * @throws
	 */
	public static void init(Context context) {
		cityNames = new ArrayList<String>();
		try {
			TelephonyManager telephonemanage = (TelephonyManager) context
					.getSystemService("phone");
			deviceId = telephonemanage.getDeviceId().replace(":", "");

		} catch (Exception e) {
		}
	}

	/** 默认传参 */
	public static void setAutoPost() {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("userId", F.userId);
		hashMap.put("carrierId", F.carrierId);
		hashMap.put("deviceid", F.deviceId);
		// Frame.setAutoAddParms(hashMap);
		
	}

	/** 获取登录保存的数据 */
	public static void getLoginData(Context context) {
		SharedPreferences preferences = context.getSharedPreferences(
				"LoginCookie", Context.MODE_PRIVATE);
		F.userId = preferences.getString("USER_ID", "");
		F.carrierId = preferences.getString("CARRIERId", "");

	}

	public static void setLogin(Context context, String userId, String verify) {
		SharedPreferences preferences = context.getSharedPreferences(
				"LoginCookie", Context.MODE_PRIVATE);
		F.userId = userId;
		F.carrierId = verify;
		Editor edit = preferences.edit();
		edit.putString("USER_ID", userId);
		edit.putString("VERIFY", verify);
		edit.commit();
		setAutoPost();
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
		} else {
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
		} else {
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
		} else {
			sb.append(Frame.INITCONFIG.getUri() + "/" + url);
		}
		// 拼接方法
		int w = (view.getWidth() == 0 ? 70 : view.getWidth());
		int h = 0;
		if (useCDN) {
			sb.append(URLEncoder.encode("?fileImgSize") + "="
					+ URLEncoder.encode(w + "x" + h));
		} else {
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
		} else if (str.length() == 0) {
			return true;
		} else {
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
			} else {
				hour = minute / 60;
				if (hour > 99)
					return "99:59:59";
				minute = minute % 60;
				second = time - hour * 3600 - minute * 60;
				timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":"
						+ unitFormat(second);
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
