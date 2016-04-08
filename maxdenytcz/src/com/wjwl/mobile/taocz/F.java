package com.wjwl.mobile.taocz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.UrlIconLoad;
import com.taocz.citystory.txweibo.oauthv2.OAuthV2;
import com.tcz.apkfactory.data.CcommentList;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Index;
import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
import com.tcz.apkfactory.data.IsubjectList.Msg_IsubjectList;
import com.tcz.ctrl.protobuf.MModule;
import com.wjwl.mobile.taocz.act.FrameAg;
import com.wjwl.mobile.taocz.act.HotRecommendAct;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.act.Search_Act;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsListAct;
import com.wjwl.mobile.taocz.act.TczV3_LoginAct;
import com.wjwl.mobile.taocz.act.V3_IndexAct;
import com.wjwl.mobile.taocz.act.V3_ZaiXianAct;
import com.wjwl.mobile.taocz.untils.WindowSizeUtil;
//import android.util.DisplayMetrics;
//import com.wjwl.mobile.taocz.DB.WmDB;

public class F {
	public static String USER_ID = "", PASSWORD = "", USERNAME = "",
			APPID = "50a068dd", RecommendID = "56", lastimgurl = "",
			CiytRecruitid = "";
	public static List<String[]> HOTKEWORD = new ArrayList<String[]>();
	public static int Per_Page = 20, GOODSCOUNT = 0;
	public static boolean AutoLogin = true;
	public static long LastUpdateTime = 0;
	public static String deviceId = null;
	public static String modelId = null;
	public static String sdkversion = null;
	public static ArrayList<Map<String, Object>> wmtempData = new ArrayList<Map<String, Object>>();
	public static CcommentList.Msg_CcommentList.Builder FOODCATEGORY;
	public static CcommentList.Msg_CcommentList.Builder TGCATEGORY;
	public static CcommentList.Msg_CcommentList.Builder AREACATEGORY;
	public static CcommentList.Msg_CcommentList.Builder PURPOSECATEGORY;
	public static String longitude = "119.977980", latitude = "31.836553";
	public static String OPENID = "", SINA_OPENID = "", TX_OPENID = "",
			SENDPHONE = "";
	public static OAuthV2 oAuth;
	public static String searchtemp = "gouwu";
	public static UrlIconLoad FillImageLoad = new UrlIconLoad(Frame.ImageCache);
	public static Index.Msg_Index.Builder INDEXBUILDER;
	public static int Fwidth = 480;
	public static int Fhight = 800;
	public static String DEVICEID = "", PROIMG = "", PROTITLE = "";
	public static Long now;
	public static Map<String, Integer> windowSizeMap;

	public static MModule.Msg_ModuleList.Builder ModuleList;

	public static void close(Context context) {
		Frame.destory();
	}

	public static String getDeviceId(Context context) {

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		if (deviceId == null) {
			deviceId = tm.getSubscriberId();// IMSI
		}
		if (deviceId == null) {
			WifiManager wifi = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			deviceId = "MAC-" + info.getMacAddress();
		}
		return deviceId;
	}

	public static String getBuildId() {
		if (modelId == null) {
			modelId = android.os.Build.MODEL;
		}
		return modelId;
	}

	public static Msg_IsubjectList.Builder getIndexCategory() {
		Msg_IsubjectList.Builder isubjectList = Msg_IsubjectList.newBuilder();
		Msg_Isubject.Builder isubject1 = Msg_Isubject.newBuilder();
		isubject1.setV3Categoryid("0");
		isubject1.setSubjectimgurl(R.drawable.lft + "");
		isubject1.setV3Categoryjumptyep("1");
		isubject1.setV3Categoryname("量贩团");
		isubjectList.addIsubject(isubject1);

		Msg_Isubject.Builder isubject3 = Msg_Isubject.newBuilder();
		isubject3.setV3Categoryid("2878,2879,56715,56577,56717,2881,2880");
		isubject3.setSubjectimgurl(R.drawable.sxsc + "");
		isubject3.setV3Categoryjumptyep("6");
		isubject3.setV3Categoryname("果蔬生鲜");
		isubjectList.addIsubject(isubject3);

		Msg_Isubject.Builder isubject7 = Msg_Isubject.newBuilder();
		isubject7.setV3Categoryid("");
		isubject7.setSubjectimgurl(R.drawable.wdp + "");
		isubject7.setV3Categoryjumptyep("7");
		isubject7.setV3Categoryname("微店铺");
		isubjectList.addIsubject(isubject7);

		Msg_Isubject.Builder isubject5 = Msg_Isubject.newBuilder();
		isubject5.setV3Categoryid("1176");
		isubject5.setSubjectimgurl(R.drawable.ytbh + "");
		isubject5.setV3Categoryjumptyep("4");
		isubject5.setV3Categoryname("名品折扣");
		isubjectList.addIsubject(isubject5);

		Msg_Isubject.Builder isubject6 = Msg_Isubject.newBuilder();
		isubject6.setV3Categoryid("1278,1279");
		isubject6.setSubjectimgurl(R.drawable.mdl + "");
		isubject6.setV3Categoryjumptyep("4");
		isubject6.setV3Categoryname("麦德龙");
		isubjectList.addIsubject(isubject6);

		Msg_Isubject.Builder isubject8 = Msg_Isubject.newBuilder();
		isubject8.setV3Categoryid("885");
		isubject8.setSubjectimgurl(R.drawable.lhck + "");
		isubject8.setV3Categoryjumptyep("4");
		isubject8.setV3Categoryname("丽华快餐");
		isubjectList.addIsubject(isubject8);

		Msg_Isubject.Builder isubject2 = Msg_Isubject.newBuilder();
		isubject2.setV3Categoryid("1446");
		isubject2.setSubjectimgurl(R.drawable.cgx + "");
		isubject2.setV3Categoryjumptyep("4");
		isubject2.setV3Categoryname("菜根香");
		isubjectList.addIsubject(isubject2);

		Msg_Isubject.Builder isubject4 = Msg_Isubject.newBuilder();
		isubject4.setV3Categoryid("3407");
		isubject4.setSubjectimgurl(R.drawable.v3_icon_index7 + "");
		isubject4.setV3Categoryjumptyep("2");
		isubject4.setV3Categoryname("常州移动");
		isubjectList.addIsubject(isubject4);

		return isubjectList;
	}

	public static String getSdkVersion() {
		if (sdkversion == null) {
			sdkversion = android.os.Build.VERSION.RELEASE;
		}
		return sdkversion;
	}

	public static void changecart(int i) {
		FrameAg.setcartval(GOODSCOUNT + "", i);

	}

	public static void indexTo(Context context, Msg_Isubject subject) {
		Intent intent = new Intent();
		intent.putExtra("id", subject.getSubjectid());
		intent.putExtra("title", subject.getDescription());
		intent.setClass(context, HotRecommendAct.class);
		context.startActivity(intent);
	}

	public static void indexTo(Context context, Msg_Cpic cpic) {
		Intent intent = new Intent();
		switch (Integer.valueOf(cpic.getJumptype().trim())) {
		case 1:
			intent.putExtra("id", cpic.getProid());
			intent.setClass(context, HotRecommendAct.class);
			context.startActivity(intent);
			break;
		case 2:
			intent.putExtra("itemid", cpic.getProid());
			intent.setClass(context, ShoppingContentAct.class);
			context.startActivity(intent);
			break;
		case 4:
			intent.putExtra("itemid", cpic.getProid());
			intent.setClass(context, LifeContentAct.class);
			context.startActivity(intent);
			break;
		}
	}

	public static void searchTo(Context context, String keyWord, String keyType) {
		keyType = "gouwu";
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("title", keyWord);
		intent.putExtra("type", keyType);
		intent.putExtra("keywords", keyWord);
		intent.putExtra("actfrom", "Search");
		if (context instanceof V3_IndexAct || context instanceof Search_Act) {
			intent.putExtra("shaixuan", "false");
		} else {
			intent.putExtra("shaixuan", "true");
		}
		intent.putExtra("actfrom", "F");
		if (keyType.equals("gouwu")) {
			intent.setClass(context, TczV3_GoodsListAct.class);
			intent.putExtra("shaixuan", "true");
			if (context instanceof TczV3_GoodsListAct) {
				Frame.HANDLES.sentAll("TczV3_GoodsListAct", 37, intent);// TczV3_GoodsListAct
																		// ShoppingListAct
				return;
			}
		}
		// else if(keyType.equals("shenghuo")){
		// // intent.setClass(context, LifeListAct.class);
		// // if(context instanceof LifeListAct){
		// // Frame.HANDLES.sentAll("LifeListAct", 37, intent);
		// // return;
		// // }
		// intent.putExtra("act", "SearchIteam");
		// intent.setClass(context, GroupBuyingListAct.class);
		// }
		// else if(keyType.equals("dianyingyanchu")){
		// intent.setClass(context, MovieShowListAct.class);
		// if(context instanceof MovieShowListAct){
		// Frame.HANDLES.sentAll("MovieShowListAct", 37, intent);
		// return;
		// }
		// }
		context.startActivity(intent);
	}

	public static void toSearch(Context context, String keyWord, String keyType) {
		Intent search = new Intent(context, Search_Act.class);// 替换SearchAct原震动搜索Act
		search.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		search.putExtra("keyWord", keyWord);
		search.putExtra("keyType", keyType);
		if (context instanceof MActivity) {
			search.putExtra("fromAct", ((MActivity) context).getId());
			((MActivity) context).startActivityForResult(search, 37);
		} else {
			context.startActivity(search);
		}
	}

	public static void indexTo(Context context, Object obj) {
		if (obj instanceof Msg_Cpic) {
			indexTo(context, (Msg_Cpic) obj);
		} else if (obj instanceof Msg_Isubject) {
			indexTo(context, (Msg_Isubject) obj);
		}
	}

	public static ArrayList<Map<String, Object>> searchPopub = new ArrayList<Map<String, Object>>();

	public static void getSearchPopub() {
		if (searchPopub.size() == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("key", "gouwu");
			map.put("value", "购物");
			searchPopub.add(map);
			// map = new HashMap<String, Object>();
			// map.put("key", "shenghuo");
			// map.put("value", "团购");
			// searchPopub.add(map);
			// map = new HashMap<String, Object>();
			// map.put("key", "dianyingyanchu");
			// map.put("value", "电影");
			// searchPopub.add(map);
		}
	}

	public static void toLogin(Context context, String from, String fromdo,
			int type) {
		Intent intent = new Intent();
		intent.putExtra("FromId", from);
		intent.putExtra("FromDo", fromdo);
		intent.putExtra("FromType", type);
		intent.setClass(context, TczV3_LoginAct.class);
		context.startActivity(intent);
	}

	public static void toPay(String orderno, Context context, String type) {
		Intent i = new Intent();
		i.putExtra("orderno", orderno);
		i.putExtra("classtype", type);
		i.putExtra("umcount", "OrderPay4");
		i.setClass(context, V3_ZaiXianAct.class);
		context.startActivity(i);
	}

	public static void noty(Context context, int icon, String tickertext,
			String title, String text, Class<?> clas, Bundle bundle, int id) {
		if (context == null) {
			return;
		}
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(ns);
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
			notificationIntent.putExtras(bundle);
		}
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);
		mNotificationManager.notify(id, notification);
	}

	/**
	 * 判断手机号格式是否正确
	 * 
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static int getCurrnetWindowWidth(Context context) {
		if (null == windowSizeMap) {
			windowSizeMap = WindowSizeUtil.getSize(context);
		}
		return windowSizeMap.get("width");

	}

	public static int getCurrnetWindowHeight(Context context) {
		if (null == windowSizeMap) {
			windowSizeMap = WindowSizeUtil.getSize(context);
		}
		return windowSizeMap.get("height");
	}
}
