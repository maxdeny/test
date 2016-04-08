package com.wjwl.mobile.taocz.act;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.RouteOverlay;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MMapActivity;
import com.wjwl.mobile.taocz.R;

public class Business_MapAct extends MMapActivity {
	static MapView mMapView;
	LocationListener mLocationListener = null;
	String b_longitude = "31836553", b_latitude = "119977980";
	GeoPoint placept;
	GeoPoint mypt = new GeoPoint(31836553, 119977980);
	MKSearch mSearch = null;
	String mypostion;
	int i_latitude, i_longitude;
    Button bt_back;
	Handler handler = new Handler();
	Runnable updateThread = new Runnable() {
		@Override
		public void run() {
			SearchButtonProcess();
			handler.removeCallbacks(updateThread);
		}

	};

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Frame.MAP.create();
		bt_back=(Button)findViewById(R.business_map.back);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Business_MapAct.this.finish();
			}
		});
		mMapView = (MapView) findViewById(R.business_map.map_View);
		b_latitude = getIntent().getStringExtra("b_latitude");
		b_longitude = getIntent().getStringExtra("b_longitude");
		if (!b_latitude.equals("") && !b_longitude.equals("")) {
			i_latitude = (int) (Double.parseDouble(b_latitude) * 1e6);
			i_longitude = (int) (Double.parseDouble(b_longitude) * 1e6);
		}
		MyLocationOverlay mylocTest = new MyLocationOverlay(this, mMapView);
		mylocTest.enableMyLocation(); // 启用定位
		mMapView.getOverlays().add(mylocTest);
		mMapView.getController().setZoom(13);

		mMapView.setDrawOverlayWhenZooming(true);

		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					int a = (int) (location.getLatitude() * 1e6);
					int b = (int) (location.getLongitude() * 1e6);
					if (a == 0 || b == 0)
						mypt = new GeoPoint(31836553, 119977980);
					else
						mypt = new GeoPoint(a, b);
				}
				mMapView.getController().animateTo(mypt);
			}
		};

		mSearch = new MKSearch();
		mSearch.init(Frame.MAP.getmBMapMan(), new MKSearchListener() {

			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				Business_MapAct.this.closeload();
				if (error != 0 || res == null) {
					Toast.makeText(Business_MapAct.this, "抱歉，未找到结果",
							Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(
						Business_MapAct.this, mMapView);
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();

				mMapView.getController().animateTo(res.getStart().pt);
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
				mypostion = res.addressComponents.street
						+ (res.addressComponents.streetNumber == null ? ""
								: res.addressComponents.streetNumber);

			}

			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

			@Override
			public void onGetRGCShareUrlResult(String arg0, int arg1) {
			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});

		handler.postDelayed(updateThread, 2000);

	}

	void SearchButtonProcess() {
		MKPlanNode stNode = new MKPlanNode();
		stNode.pt = mypt;
		MKPlanNode enNode = new MKPlanNode();
		enNode.pt = new GeoPoint(i_latitude, i_longitude);
		mSearch.drivingSearch("常州", stNode, "常州", enNode);
		Business_MapAct.this.showload();
	}

	@Override
	protected void initcreate(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.business_map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void resume() {
		Frame.MAP.getmBMapMan().getLocationManager()
				.requestLocationUpdates(mLocationListener);
		Frame.MAP.start();
		Frame.MAP.getmBMapMan().getLocationManager()
				.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	};

	protected void pause() {
		Frame.MAP.getmBMapMan().getLocationManager()
				.removeUpdates(mLocationListener);
		Frame.MAP.getmBMapMan().getLocationManager()
				.disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		if (Frame.MAP.getmBMapMan() != null)
			Frame.MAP.stop();
	}

}
