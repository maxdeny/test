package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.PeriperalListAdapter;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;

public class PeripheralAct extends MActivity {


	Msg_CcategoryList.Builder list = null;
	private Button position;
	private ListView listview;
	String[] titles;
	int[] icons;
	SimpleAdapter sadapter;
	List<Msg_Ccategory> list_ccategory;
	LocationListener mLocationListener = null;
	String Latitude;
	String Longitude;
	private TextView address;
	boolean reLocation;
//	private PullReloadView prv;
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.peripheral);
		setId("PeripheralAct");
		address=(TextView) findViewById(R.peripheral.address);
//		prv=(PullReloadView) findViewById(R.peripheral.pullReloadView);

		Frame.MAP.create();
		init();
		locationListener();
		Frame.MAP.start();
		Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
		Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_GPS_PROVIDER);
		Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
		((LoadingDialog)loadingDialog).setText("正在定位");
		loadingDialog.show();
	}

	public void showLoad(){
		((LoadingDialog)loadingDialog).setText("正在加载");
	}
	
	void locationListener() {
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					int iLatitude = (int) (location.getLatitude() * 1e6);
					int iLongitude = (int) (location.getLongitude() * 1e6);
					Latitude = location.getLatitude()+"" ;
					Longitude = location.getLongitude()+"";
					Frame.MAP.getmBMapMan().getLocationManager().getLocationInfo();
					
					 MKSearch search = new MKSearch();
			         search.init(Frame.MAP.getmBMapMan(),new MKSearchListener() {
						
						@Override
						public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
						}
						
						@Override
						public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
							
						}
						
						@Override
						public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
							
						}
						
						@Override
						public void onGetRGCShareUrlResult(String arg0, int arg1) {
							
						}
						
						@Override
						public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
							
						}
						
						@Override
						public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
							
						}
						
						@Override
						public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
							
						}
						
						@Override
						public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
							if (arg0 == null) {
								address.setText("当前位置:未知");
					        } else {
					        	 String str=arg0.addressComponents.street+(arg0.addressComponents.streetNumber==null?"":arg0.addressComponents.streetNumber);
					        	 address.setText("当前位置:"+str);
					        }
							Frame.MAP.stop();
							dataLoadByDelay(null);
						}

						@Override
						public void onGetPoiDetailSearchResult(int arg0,
								int arg1) {
							// TODO Auto-generated method stub
							
						}
					});
			         search.reverseGeocode(new GeoPoint(iLatitude, iLongitude));
					reLocation = false;
					Frame.MAP.getmBMapMan().getLocationManager().removeUpdates(mLocationListener);
				}
			}
		};
	}
	
    
	
	private void init() {
//		titles = new String[] {
//				getResources().getString(R.string.peripheral_res),
//				getResources().getString(R.string.peripheral_snack),
//				getResources().getString(R.string.peripheral_coffer),
//				getResources().getString(R.string.peripheral_massage),
//				getResources().getString(R.string.peripheral_bar),
//				getResources().getString(R.string.peripheral_club),
//				getResources().getString(R.string.peripheral_ktv) };
//		icons = new int[] { R.drawable.peripheral_res,
//				R.drawable.peripheral_snack, R.drawable.peripheral_coffer,
//				R.drawable.peripheral_massage, R.drawable.peripheral_bar,
//				R.drawable.peripheral_club, R.drawable.peripheral_ktv };
		position = (Button) findViewById(R.peripheral.position);
		listview = (ListView) findViewById(R.peripheral.listview);
		position.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Frame.MAP.start();
				reLocation = true;
				
				Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
				Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_GPS_PROVIDER);
				Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
				
				Frame.MAP.start();
			}
		});
	}

	@Override
	public void dataLoad(int[] types) {
		
		Msg_CcategoryList.Builder ccategorylist = Msg_CcategoryList.newBuilder();
		
		{
			Msg_Ccategory.Builder ccategory = Msg_Ccategory.newBuilder();
			ccategory.setCategoryid("");
			ccategory.setCategoryname("全部");
			ccategorylist.addCcategory(ccategory);
			ccategory.clear();
		}
		
		for(int i=0;i<F.TGCATEGORY.getCommentCount();i++){
			Msg_Ccategory.Builder ccategory = Msg_Ccategory.newBuilder();
			ccategory.setCategoryid(F.TGCATEGORY.getComment(i).getCommentid());
			ccategory.setCategoryname(F.TGCATEGORY.getComment(i).getCommentcontent());
			ccategorylist.addCcategory(ccategory);
			ccategory.clear();
		}
		list_ccategory = ccategorylist.getCcategoryList();
		PeriperalListAdapter pla=new PeriperalListAdapter(this,list_ccategory,this.Latitude==null?F.latitude:this.Latitude,this.Longitude==null?F.longitude:this.Longitude);//定位为null
		listview.setAdapter(pla);
		loadingDialog.dismiss();
		
//		this.loadData(new Updateone[] { new Updateone("RCATEGORY",
//				new String[][] {{"categoryid","0"}}), });
	}

//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if(son.build!=null&&son.mgetmethod.equals("rcategory")){
//			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//			list_ccategory = builder.getCcategoryList();
//			PeriperalListAdapter pla=new PeriperalListAdapter(this,list_ccategory,this.Latitude,this.Longitude);
//			listview.setAdapter(pla);
//		}
//		prv.refreshComplete();
//	}
	
	
}