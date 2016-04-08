package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.LocationListener;
import com.baidu.mapapi.MKLocationManager;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MyLocationOverlay;
import com.baidu.mapapi.OverlayItem;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MMapActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class BusinessGuidance extends MMapActivity {

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("RBUSINESSLIST",
				new String[][] { { "businessidlist", id + ""} },
				Msg_CbusinessinfoList.newBuilder()), });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son != null && son.metod == "RBUSINESSLIST") {
			list = (Msg_CbusinessinfoList.Builder) son.build;
			initMap();
		}
	}

	static View mPopView = null;
	OverItemTT overitem = null;
	static int id = -1;
	static Msg_CbusinessinfoList.Builder list;
	static MapView mMapView;
	LocationListener mLocationListener = null;
	MyLocationOverlay mLocationOverlay = null;

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
//		Frame.MAP.create();
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setDrawOverlayWhenZooming(true);
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		id = Integer.parseInt(getIntent().getStringExtra("id"));
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					if (id == -1) {
						GeoPoint pt;
						int a = (int) (location.getLatitude() * 1e6);
						int b = (int) (location.getLongitude() * 1e6);
						if(a==0 || b ==0)
							pt = new GeoPoint(31836553,119977980);
						else
							pt = new GeoPoint(a,b);
						mMapView.getController().setCenter(pt);
					}
				}
			}
		};
		GeoPoint pt;
		pt = new GeoPoint(31836553,119977980);
		mMapView.getController().setCenter(pt);
		dataLoad(null);
	}

	void initMap(){
		Drawable marker = getResources().getDrawable(R.drawable.map_marker_biz);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		overitem = new OverItemTT(marker, this,
				list.getCbusinessinfoList().size());
		mMapView.getOverlays().add(overitem);
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView(mPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);
		mMapView.getController().setZoom(13);
		if (id > -1)
			overitem.onTap(0);
	}
	
	@Override
	protected void initcreate(Bundle arg0) {
		setContentView(R.layout.businessguidance);

	}
	@Override
	protected void pause() {
		Frame.MAP.getmBMapMan().getLocationManager().removeUpdates(mLocationListener);
		mLocationOverlay.disableMyLocation();
		mLocationOverlay.disableCompass(); // 关闭指南针
	
		Frame.MAP.getmBMapMan().getLocationManager().disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
		Frame.MAP.stop();
	}

	@Override
	protected void resume() {
		Frame.MAP.start();
		mLocationOverlay.enableMyLocation();
		mLocationOverlay.enableCompass(); // 打开指南针

		Frame.MAP.getmBMapMan().getLocationManager()
				.requestLocationUpdates(mLocationListener);
		Frame.MAP.getmBMapMan().getLocationManager()
				.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);

	}
}

class OverItemTT extends ItemizedOverlay<OverlayItem> {

	public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;

	public OverItemTT(Drawable marker, Context context, int count) {
		super(boundCenterBottom(marker));
		this.marker = marker;
		if (BusinessGuidance.id > -1) {
			addPoint(0);
			double a = Double.parseDouble(BusinessGuidance.list.getCbusinessinfoList().get(0).getLatitude());
			double b = Double.parseDouble(BusinessGuidance.list.getCbusinessinfoList().get(0).getLongitude());
			GeoPoint pt = new GeoPoint((int)(a*1e6),(int)(b*1e6));
			BusinessGuidance.mMapView.getController().setCenter(pt);
		}
		populate();
	}

	void addPoint(int i) {
		double a = Double.parseDouble(BusinessGuidance.list.getCbusinessinfoList().get(i).getLatitude());
		double b = Double.parseDouble(BusinessGuidance.list.getCbusinessinfoList().get(i).getLongitude());
		
		String pname = BusinessGuidance.list.getCbusinessinfoList().get(i).getBusinessname();
		String paddress = BusinessGuidance.list.getCbusinessinfoList().get(i).getBusinessaddress();
		GeoPoint p = new GeoPoint((int)(a*1e6),(int)(b*1e6));
		mGeoList.add(new OverlayItem(p, pname, paddress));
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mGeoList.get(i);
	}

	@Override
	public int size() {
		return mGeoList.size();
	}

	// 处理图上标记点击事件
	@Override
	protected boolean onTap(int i) {
		setFocus(mGeoList.get(i));
		GeoPoint pt = mGeoList.get(i).getPoint();
		BusinessGuidance.mMapView.updateViewLayout(BusinessGuidance.mPopView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, pt,
						MapView.LayoutParams.BOTTOM_CENTER));
		BusinessGuidance.mPopView.setVisibility(View.VISIBLE);
//		TextView tName = (TextView) BusinessGuidance.mPopView
//				.findViewById(R.id.name);
//		TextView tAddress = (TextView) BusinessGuidance.mPopView
//				.findViewById(R.id.address);
//		TextView tDescription = (TextView) BusinessGuidance.mPopView
//				.findViewById(R.id.description);
//		if (BusinessGuidance.id > -1) {
//			tName.setText(BusinessGuidance.list.getCbusinessinfoList().get(i).getBusinessname());
//			tAddress.setText(BusinessGuidance.list.getCbusinessinfoList().get(i).getBusinessaddress());
//			tDescription.setText(BusinessGuidance.list.getCbusinessinfoList().get(i).getEnvironment());
//		}
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		BusinessGuidance.mPopView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}