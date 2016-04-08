package com.wjwl.mobile.taocz.act;

import java.util.Calendar;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKLocationManager;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcommentList.Msg_CcommentList;
import com.tcz.apkfactory.data.Index.Msg_Index;
import com.tcz.apkfactory.data.Update.Msg_Update;
import com.tcz.ctrl.protobuf.MModule.Msg_ModuleList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;
import com.wjwl.mobile.taocz.dialog.UpdateDialog;

public class LoadingAct extends MActivity {
	public static String  updatestate="0";
	public static  int screenWidth=0;
	public static  int screenHeight=0;
	LocationListener mLocationListener = null;
	String Latitude;
	String Longitude;
	String loadingimg,key;
	MImageView bg_loading_img;
	@Override
	public void dataLoad(int[] types) {
		int version=0;
		
		try {
			version=Frame.getApp(this,getPackageName()).getVersion();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
       if(types==null){
		this.loadData(new Updateone[] { 
//			    new Updateone("v3_index",new String[][] { { "version", "android" }, { "localversion",version+""  } }),
//				new Updateone("V3_LOADIMG",new String[][] {{"phonetype","android"}}),
//				new Updateone("CKEYWORDS",new String[][] {}),
//				new Updateone("TGCATEGORY",new String[][] {}),
//				new Updateone("TGAREA",new String[][] {}),
//				new Updateone("FOODCATEGORY",new String[][] {}),
//				new Updateone("PURPOSECATEGORY",new String[][] {}),
				new Updateone("OUPDATE",new String[][] {
						{"version",version+""},
						{"packagename",getApplication().getPackageName()},
						{"deviceid",F.getDeviceId(this)},
						{"buildid",F.getBuildId()},
						{"sdkversion","android"+F.getSdkVersion()},
				}),
		});
        }
       else{
    	   this.loadData(new Updateone[] { 
    				new Updateone("COMTROL",new String[][] {}),
    				//   			    new Updateone("V3_XCECSLOGIN",new String[][] { { "key", key} })
		});
       }
		//取消通知服务
//		Data data=new Data(LoadingAct.this);
//		if(data.getAutoUpdate(false)){
//			Intent intent =new Intent();
//			intent.setClass(LoadingAct.this, AutoNotService.class);
//			startService(intent);
//		}
		
		
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
//		if((son.build!=null)&&(son.mgetmethod.equals("ckeywords"))){
//			List<String[]> listkeywordname=new ArrayList<String[]>();
//			Msg_Ckeywords.Builder builder = (Msg_Ckeywords.Builder) son.build;
//			for (int i = 0; i < builder.getKeywordnameCount(); i++) {
//				listkeywordname.add(new String[] { builder.getKeywordname(i),builder.getKeywordid(i) });
//			}
//			synchronized (F.HOTKEWORD) {
//				F.HOTKEWORD.clear();
//				F.HOTKEWORD.addAll(listkeywordname);
//			}
//		}else 
		if((son.build!=null)&&(son.mgetmethod.equals("oupdate"))){
			Msg_Update.Builder builder=(Msg_Update.Builder)son.build;
			loadingimg=builder.getApkid();
			int version=0;
			try {
				version=Frame.getApp(this,getPackageName()).getVersion();
			} catch (NameNotFoundException e) {
			}
			if(Integer.valueOf(builder.getApkversion())<=version){
				moveNext();
			}else{
				if(builder.getApkversionstr().equals("1")){//强制更新
					updatestate="1";
//					Intent i = new Intent();
//					i.setClass(this, UpdateDialog.class);
//					startActivity(i);
				    moveNext();
				}else{
					updatestate="0";
//					long nowtime=Calendar.getInstance().getTimeInMillis();
//					if(nowtime-F.LastUpdateTime>3600*24){
//						F.LastUpdateTime=nowtime;
//						Bundle bundle=new Bundle();
//						bundle.putSerializable("update", builder.build());
//						F.noty(this, R.drawable.default_icon, "检测到新版本", "检测到新版本", "version:"+builder.getApkversion(),UpdateDialog.class, bundle, 0);
//					}
					moveNext();
				}
			}
		}
		
		
		else if((son.build!=null)&&(son.mgetmethod.equals("MModuleList"))){
			F.ModuleList= (Msg_ModuleList.Builder)son.build;
			dataLoad(null);
		}
		else if((son.build!=null)&&(son.mgetmethod.equals("tgcategory"))){
			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
			F.TGCATEGORY=builder;
		}
//		else if((son.build!=null)&&(son.mgetmethod.equals("foodcategory"))){
//			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
//			F.FOODCATEGORY=builder;
//				}
		else if((son.build!=null)&&(son.mgetmethod.equals("tgarea"))){
			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
			F.AREACATEGORY=builder;
		}
//		else if((son.build!=null)&&(son.mgetmethod.equals("purposecategory"))){
//			Msg_CcommentList.Builder builder = (Msg_CcommentList.Builder) son.build;
//			F.PURPOSECATEGORY=builder;
//		}
		else if((son.build!=null)&&(son.mgetmethod.equals("v3_loadimg"))){
			com.mdx.mobile.base.Retn.Msg_Retn.Builder builder = (com.mdx.mobile.base.Retn.Msg_Retn.Builder) son.build;
			bg_loading_img.setObj(builder.getErrorMsg());
		}
		
		if (son.build != null && son.mgetmethod.equals("v3_index")) {
			F.INDEXBUILDER = (Msg_Index.Builder) son.build;
			
		}
		if (son.build != null && son.mgetmethod.equals("V3_XCECSLOGIN")) {
			Msg_Ccategory.Builder builder = (Msg_Ccategory.Builder) son.build;
			F.USERNAME=F.USER_ID=builder.getCategoryid();
			builder.getCategoryname();
			  F.GOODSCOUNT = 0;
		      Frame.HANDLES.closeIds("MyOrderListAct,MyOrderDetailsAct,MyOrderLifeDetailsAct,MyOrderReservationDetailsAct,MyOrderAg,MyOrderCancelAct");
		}
		
		
	}
	
	private void moveNext() {
		Intent i = new Intent();
		i.setClass(getApplication(), FrameAg.class);
		startActivity(i);
		LoadingAct.this.finish();
	}

	@Override
	protected void create(Bundle arg0) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setContentView(R.layout.loading);
		
		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.setDebugMode(true);
		
		bg_loading_img=(MImageView)findViewById(R.id.bg_loading_img);
		
		((LoadingDialog)loadingDialog).setText("正在加载");
//		loadingDialog.show();
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
	    int widthPixels= dm.widthPixels;
	    int heightPixels= dm.heightPixels;
//	    float density = dm.density;
//	     screenWidth = (int) (widthPixels * density) ;
//	     screenHeight = (int) (heightPixels * density) ;
	     screenWidth  =widthPixels;
	     screenHeight =heightPixels;
	     F.Fwidth=getWindowManager().getDefaultDisplay().getWidth();//720
	     F.Fhight=getWindowManager().getDefaultDisplay().getHeight();//1280
		this.Menu_Show=false;
		this.LoadShow=false;
		
		//Frame.MAP.create();
		locationListener();
		//Frame.MAP.start();
		//Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
		//Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_GPS_PROVIDER);
		//Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		
//		dataLoad(new int []{1});
		dataLoad();
//		getParameterByIntent();
	}
	
	
	
	void locationListener() {
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
//					int iLatitude = (int) (location.getLatitude() * 1e6);
//					int iLongitude = (int) (location.getLongitude() * 1e6);
					F.latitude = location.getLatitude()+"" ;
					F.longitude = location.getLongitude()+"";
					
			}
		}
		};
}

	protected void pause() {
//		loadingDialog.dismiss();
		if(Frame.MAP.getmBMapMan()!=null){
			Frame.MAP.getmBMapMan().getLocationManager()
			.removeUpdates(mLocationListener);
	Frame.MAP.getmBMapMan().getLocationManager()
			.disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	Frame.MAP.stop();
		}
			
	}
	
	public void getParameterByIntent() {  
		   Intent mIntent = this.getIntent();  
		   String pwd = mIntent.getStringExtra("userid");  
		   String username = mIntent.getStringExtra("username");  
//		   String verity = mIntent.getStringExtra("verity");  
		   //  http://api.taocz.com/mobile/tao.php?act=V3_XCECSLOGIN&app=ccategorylist&nerr_wdj=1&key=13961136682_123456&debug=1
		   if(pwd!=null||username!=null){
			   key=username+"_"+pwd;
			   dataLoad(new int []{1});
		   }
		} 
}
