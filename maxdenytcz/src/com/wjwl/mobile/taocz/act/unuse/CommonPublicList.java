package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.baidu.mapapi.GeoPoint;
//import com.baidu.mapapi.LocationListener;
//import com.baidu.mapapi.MKAddrInfo;
//import com.baidu.mapapi.MKBusLineResult;
//import com.baidu.mapapi.MKDrivingRouteResult;
//import com.baidu.mapapi.MKLocationManager;
//import com.baidu.mapapi.MKPoiInfo;
//import com.baidu.mapapi.MKPoiResult;
//import com.baidu.mapapi.MKSearch;
//import com.baidu.mapapi.MKSearchListener;
//import com.baidu.mapapi.MKSuggestionResult;
//import com.baidu.mapapi.MKTransitRouteResult;
//import com.baidu.mapapi.MKWalkingRouteResult;
//import com.baidu.mapapi.MapController;
//import com.baidu.mapapi.MapView;
//import com.baidu.mapapi.PoiOverlay;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MMapActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.PlaceShowAdapter;
//import com.wjwl.mobile.taocz.commons.Arith;
//import com.wjwl.mobile.taocz.widget.Item_foot_placebtn;
//
//public class CommonPublicList extends MMapActivity{
//
//	static MapView mMapView;
//	LocationListener mLocationListener = null;
//	Button btnback,btnlist,btnatm,btnhospital,btnpharmacy,btntoilet,btncarpark,btnfillingstation;
//	TextView headtext;
//	public static MKPoiResult tempresult=null;
//	private MapController mapController;
//	private MKSearch mMKSearch;
//	private ListView listView;
//	List<Map<String,String>> mapdata = new ArrayList<Map<String,String>>();
//	Item_foot_placebtn foot_item;
//	GeoPoint mypt = new GeoPoint(31836553, 119977980);
//	boolean mapshowflag=true;
//	boolean first=true;
//	String tempplace="ATM";
//	
//	@Override
//	protected void create(Bundle arg0) {
//		Frame.MAP.create();
//		mMapView = (MapView) findViewById(R.commonpubliclist.map_View);
//		mMapView.setDrawOverlayWhenZooming(true);
//		mapController = mMapView.getController();
//		mapController.setZoom(13);
//		
//		initMKSearch(first);
//		first=false;
//		mLocationListener = new LocationListener() {
//			@Override
//			public void onLocationChanged(Location location) {
//				if (location != null) {
//					
//					int a = (int) (location.getLatitude() * 1e6);
//					int b = (int) (location.getLongitude() * 1e6);
//					if (a == 0 || b == 0)
//						mypt = new GeoPoint(31836553, 119977980);
//					else
//						mypt = new GeoPoint(a, b);
//				}
//			}
//		};
//		
//        listView = (ListView)findViewById(R.commonpubliclist.listview1);
//		
//		foot_item = new Item_foot_placebtn(CommonPublicList.this);
//		
//		foot_item.btnpre.setOnClickListener(new onclickListener());
//		foot_item.btnnxt.setOnClickListener(new onclickListener());
//		listView.addFooterView(foot_item);
//		
//		mMKSearch.poiSearchInCity("常州", "ATM");
//		foot_item.pageno.setText("第1页");
//	}
//
//	@Override
//	protected void initcreate(Bundle arg0) {
//		setContentView(R.layout.commonpubliclist);
//		btnback=(Button) findViewById(R.commonpubliclist.back);
//		btnlist=(Button)findViewById(R.commonpubliclist.btnlist);
//		headtext=(TextView)findViewById(R.commonpubliclist.text);
//		headtext.setText(getIntent().getStringExtra("title"));
//		
//		btnatm=(Button)findViewById(R.commonpubliclist.atm);
//		btnhospital=(Button)findViewById(R.commonpubliclist.hospital);
//		btnpharmacy=(Button)findViewById(R.commonpubliclist.pharmacy);
//		btntoilet=(Button)findViewById(R.commonpubliclist.toilet);
//		btncarpark=(Button)findViewById(R.commonpubliclist.carpark);
//		btnfillingstation=(Button)findViewById(R.commonpubliclist.fillingstation);
//		
//		btnatm.setOnClickListener(new onclickListener());
//		btnhospital.setOnClickListener(new onclickListener());
//		btnpharmacy.setOnClickListener(new onclickListener());
//		btntoilet.setOnClickListener(new onclickListener());
//		btncarpark.setOnClickListener(new onclickListener());
//		btnfillingstation.setOnClickListener(new onclickListener());
//		
//		btnback.setOnClickListener( new onclickListener());
//		btnlist.setOnClickListener( new onclickListener());
//		btnatm.setBackgroundResource(R.drawable.btn_public_ped_left_blue);
//	
//	}
//	
//	public class  onclickListener implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			foot_item.pageno.setText("第1页");
//			initMKSearch(first);
//			switch (v.getId()) {
//			case R.commonpubliclist.back:
//				finish();
//				break;
//            case R.commonpubliclist.btnlist:
//            	if(mapshowflag){
//            		mMapView.setVisibility(View.GONE);
//            		btnlist.setText("地图");
//            		mapshowflag=false;
//            	}
//            	else{
//            		mMapView.setVisibility(View.VISIBLE);
//            		btnlist.setText("列表");
//            		mapshowflag=true;
//            	}
//            	
//				break;
//            case R.commonpubliclist.atm:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "ATM");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_ped_left_blue);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_nor_rigth_red);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	tempplace="ATM";
//				break;
//            case R.commonpubliclist.hospital:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "医院");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_nor_left_red);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_ped_blue);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_nor_rigth_red);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	tempplace="医院";
//				break;
//            case R.commonpubliclist.pharmacy:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "药店");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_nor_left_red);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_ped_blue);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_nor_rigth_red);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	tempplace="药店";
//				break;
//            case R.commonpubliclist.toilet:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "厕所");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_nor_left_red);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_ped_rigth_blue);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	tempplace="厕所";
//				break;
//            case R.commonpubliclist.carpark:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "停车场");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_nor_left_red);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_nor_rigth_red);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_ped_blue);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	tempplace="停车场";
//				break;
//            case R.commonpubliclist.fillingstation:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", "加油站");
//            	btnatm.setBackgroundResource(R.drawable.btn_public_nor_left_red);
//            	btnhospital.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnpharmacy.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btntoilet.setBackgroundResource(R.drawable.btn_public_nor_rigth_red);
//            	btncarpark.setBackgroundResource(R.drawable.btn_public_nor_red);
//            	btnfillingstation.setBackgroundResource(R.drawable.btn_public_ped_blue);
//            	tempplace="加油站";
//				break;
//            case R.foot.btnpre:
//            	CommonPublicList.this.showload();
//            	mMKSearch.poiSearchInCity("常州", tempplace);
//				if(tempresult.getPageIndex() < tempresult.getNumPages()-1) {
//					mMKSearch.goToPoiPage(tempresult.getPageIndex() - 1);
//					if(tempresult.getPageIndex()==0){
//						foot_item.pageno.setText("第1页");
//						closeLoad();
//					}
//					else{
//						foot_item.pageno.setText("第"+(tempresult.getPageIndex())+"页");
//					}
//				}
//				break;
//				
//			case R.foot.btnnet:
//				CommonPublicList.this.showload();
//				mMKSearch.poiSearchInCity("常州", tempplace);
//				if(tempresult.getPageIndex() < tempresult.getNumPages()-1) {
//					mMKSearch.goToPoiPage(tempresult.getPageIndex() + 1);
//					if(tempresult.getPageIndex()==0){
//						foot_item.pageno.setText("第2页");
//					}
//					else{
//						foot_item.pageno.setText("第"+(tempresult.getPageIndex()+2)+"页");
//					}
//				}
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//	@Override
//	protected boolean isRouteDisplayed() {
//		return false;
//	}
//	
//
//	private void initMKSearch(boolean isfirst){
//		MKSearch.setPoiPageCapacity(10);
//		mMKSearch = new MKSearch();
//		mMKSearch.init(Frame.MAP.getmBMapMan(), new MySearchListener());
//	}
//	
//	public class MySearchListener implements MKSearchListener {
//		@Override
//		public void onGetAddrResult(MKAddrInfo result, int iError) {
//		}
//		@Override
//		public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {
//		}
//
//		@Override
//		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
//			if (result == null) {
//				return;
//			}
//			CommonPublicList.this.closeload();
//			mMapView.getOverlays().clear();
//			mapdata.clear();
//			PoiOverlay poioverlay = new PoiOverlay(CommonPublicList.this, mMapView);
//			poioverlay.setData(result.getAllPoi());
//			mMapView.getOverlays().add(poioverlay);
//			tempresult=result;
//			if(result.getNumPois() > 0) {
//				MKPoiInfo poiInfo = result.getPoi(0);
//				mapController.setCenter(poiInfo.pt);
//			}
//			for (MKPoiInfo poiInfo : result.getAllPoi()) {
//				Map map = new HashMap();
//				map.put("title", poiInfo.name);
//				map.put("address", poiInfo.address);
//				map.put("phoneno", poiInfo.phoneNum);
//			    float[] results=new float[1];  
//				Location.distanceBetween(mypt.getLatitudeE6()/1e6, mypt.getLongitudeE6()/1e6, poiInfo.pt.getLatitudeE6()/1e6, poiInfo.pt.getLongitudeE6()/1e6, results);  
//				map.put("distance", "距离"+Arith.to2zero((results[0]/1000)+"")+"km");
//				map.put("localpoint", poiInfo.pt.getLatitudeE6()+","+ poiInfo.pt.getLongitudeE6());
//				map.put("titletype",tempplace );
//				mapdata.add(map);
//			}
//			PlaceShowAdapter laceShowAdapter =new PlaceShowAdapter(CommonPublicList.this,mapdata);
//			listView.setAdapter(laceShowAdapter);
//	
//		}
//		@Override
//		public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {
//		}
//
//		@Override
//		public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {
//		}
//		@Override
//		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
//			
//		}
//		@Override
//		public void onGetRGCShareUrlResult(String arg0, int arg1) {
//			
//		}
//		@Override
//		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
//			
//		}
//	}
//	
//	protected void resume() {
//		Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
//		Frame.MAP.start();
//		Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//		first=true;
//	};
//	
//	protected void pause() {
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.removeUpdates(mLocationListener);
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//		if (Frame.MAP.getmBMapMan() != null)
//			Frame.MAP.stop();
//	}
//	
//	@Override
//	public void closeLoad() {
//		super.closeLoad();
//		this.LoadShow = false;
//	}
//}
