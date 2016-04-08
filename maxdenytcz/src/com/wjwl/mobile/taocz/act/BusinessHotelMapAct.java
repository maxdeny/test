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
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class BusinessHotelMapAct extends MMapActivity {

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("RBUSINESSLIST",
				new String[][] { { "businessidlist", 3 + ""} }), });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son != null && son.metod == "RBUSINESSLIST") {
			list = (Msg_CbusinessinfoList.Builder) son.build;
			initMap();
		}
	}

	String str = "";
	static Msg_CbusinessinfoList.Builder list;
	private Button maplist, zoom_out, zoom_in;
	static View mPopView = null;
	OverItemHotel overitem = null;
	static boolean isList = false;
	private TextView txt;

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
		Frame.MAP.create();
		str = getIntent().getStringExtra("str");
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setDrawOverlayWhenZooming(true);
		txt = (TextView)findViewById(R.id.mapname);
		txt.setText(R.string.hotelmapname);
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
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
		overitem = new OverItemHotel(marker, this,
				list.getCbusinessinfoList().size());
		mMapView.getOverlays().add(overitem);
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		mMapView.addView(mPopView, new MapView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, null,
				MapView.LayoutParams.TOP_LEFT));
		mPopView.setVisibility(View.GONE);

		mMapView.getController().setZoom(13);
	}
	
	@Override
	protected void initcreate(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.businessmap);
		maplist = (Button) findViewById(R.id.maplistmodel);
		maplist.setVisibility(View.GONE);
		zoom_out = (Button) findViewById(R.id.zoom_out);
		zoom_in = (Button) findViewById(R.id.zoom_in);
		isList = getIntent().getBooleanExtra("isList", false);
		maplist.setOnClickListener(new OnClick());
		zoom_in.setOnClickListener(new OnClick());
		zoom_out.setOnClickListener(new OnClick());
	}

	class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (maplist.equals(v)) {
				finish();
			}
			if (zoom_in.equals(v)) {
				if (!mMapView.getController().zoomOut())
					zoom_in.setEnabled(false);
				zoom_out.setEnabled(true);
			}
			if (zoom_out.equals(v)) {
				if (!mMapView.getController().zoomIn())
					zoom_out.setEnabled(false);
				zoom_in.setEnabled(true);
			}
		}
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

class OverItemHotel extends ItemizedOverlay<OverlayItem> {

	public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;

	public OverItemHotel(Drawable marker, Context context, int count) {
		super(boundCenterBottom(marker));

		this.marker = marker;

		if (BusinessHotelMapAct.isList) {
			for (int i = 0; i < BusinessHotelMapAct.list.getCbusinessinfoList().size(); i++) {
				addPoint(i);
			}
		}
		populate();
	}

	void addPoint(int i) {
		double a = Double.parseDouble(BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getLatitude());
		double b = Double.parseDouble(BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getLongitude());
		int latitude = (int)(a*1e6);
		int longitude = (int)(b*1e6);
		String pname = BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getBusinessname();
		String paddress = BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getBusinessaddress();
		GeoPoint p = new GeoPoint(latitude, longitude);
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
		// 更新标记位置,并使之显示
		GeoPoint pt = mGeoList.get(i).getPoint();
		BusinessHotelMapAct.mMapView.updateViewLayout(BusinessHotelMapAct.mPopView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, pt,
						MapView.LayoutParams.BOTTOM_CENTER));
		BusinessHotelMapAct.mPopView.setVisibility(View.VISIBLE);
//		TextView tName = (TextView) BusinessHotelMapAct.mPopView
//				.findViewById(R.id.name);
//		TextView tAddress = (TextView) BusinessHotelMapAct.mPopView
//				.findViewById(R.id.address);
//		TextView tDescription = (TextView) BusinessHotelMapAct.mPopView
//				.findViewById(R.id.description);
//		tName.setText(BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getBusinessname());
//		tAddress.setText(BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getBusinessaddress());
//		tDescription.setText(BusinessHotelMapAct.list.getCbusinessinfoList().get(i).getEnvironment());
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		BusinessHotelMapAct.mPopView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}