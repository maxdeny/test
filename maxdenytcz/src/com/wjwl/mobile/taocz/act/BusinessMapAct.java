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
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_Business_List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class BusinessMapAct extends MMapActivity {
	String str = "";
	static Msg_CbusinessinfoList.Builder list;
	private Button maplist, zoom_out, zoom_in;
	static View mPopView = null;
	OverItemT overitem = null;
	static boolean isList = false;
	private String[] tuds;
	static MapView mMapView;
	LocationListener mLocationListener = null;
	MyLocationOverlay mLocationOverlay = null;
	private String mjl="",vtyp="",order="";
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	protected void create(Bundle arg0) {
		Frame.MAP.create();
		str = getIntent().getStringExtra("str");
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setDrawOverlayWhenZooming(true);
		mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				if (location != null) {
					GeoPoint pt;
					int a = (int) (location.getLatitude() * 1e6);
					int b = (int) (location.getLongitude() * 1e6);
					if (a == 0 || b == 0)
						pt = new GeoPoint(31836553, 119977980);
					else
						pt = new GeoPoint(a, b);
					mMapView.getController().setCenter(pt);
				}
			}
		};
		GeoPoint pt;
		pt = new GeoPoint(31836553, 119977980);
		mMapView.getController().setCenter(pt);
		dataLoad(null);
	}

	void initMap() {
		Drawable marker = getResources().getDrawable(R.drawable.map_marker_biz);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());
		
		MyLocationOverlay mylocTest = new MyLocationOverlay(this, mMapView);
		mylocTest.enableMyLocation(); // 启用定位
		mylocTest.enableCompass();    // 启用指南针
		mMapView.getOverlays().add(mylocTest);
		
		overitem = new OverItemT(marker, this, list.getCbusinessinfoList().size());
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
		
		setContentView(R.layout.businessmap);
		tuds = getIntent().getStringArrayExtra("tuds");
		
		this.order=getIntent().getStringExtra("filter");
		this.mjl=getIntent().getStringExtra("distance");
		this.vtyp=getIntent().getStringExtra("rtype");
		
		maplist = (Button) findViewById(R.id.maplistmodel);
		zoom_out = (Button) findViewById(R.id.zoom_out);
		zoom_in = (Button) findViewById(R.id.zoom_in);
		isList = getIntent().getBooleanExtra("isList", false);
		maplist.setOnClickListener(new OnClick());
		zoom_in.setOnClickListener(new OnClick());
		zoom_out.setOnClickListener(new OnClick());
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone(
				"RLIST",
				new String[][] {
						{ "mlatitude", tuds[0] == null ? "31.805989" : tuds[0] },
						{ "mlongitude",tuds[1] == null ? "119.980623" : tuds[1] },
						{ "filter",order }, 
						{ "distance", mjl},
						{ "rtype", vtyp } }), });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("rlist")) {
			list = (Msg_CbusinessinfoList.Builder) son.build;
			if(list==null){
				return;
			}
			initMap();
		}
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
		Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
		Frame.MAP.start();
		Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
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

class OverItemT extends ItemizedOverlay<OverlayItem> {

	public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	private Drawable marker;

	public OverItemT(Drawable marker, Context context, int count) {
		super(boundCenterBottom(marker));

		this.marker = marker;

		if (BusinessMapAct.isList) {
			for (int i = 0; i < BusinessMapAct.list.getCbusinessinfoList()
					.size(); i++) {
				addPoint(i);
			}
		}
		populate();
	}

	void addPoint(int i) {
		double a = Double.parseDouble(BusinessMapAct.list
				.getCbusinessinfoList().get(i).getLatitude());
		double b = Double.parseDouble(BusinessMapAct.list
				.getCbusinessinfoList().get(i).getLongitude());
		int latitude = (int) (a * 1e6);
		int longitude = (int) (b * 1e6);
		String pname = BusinessMapAct.list.getCbusinessinfoList().get(i)
				.getBusinessname();
		String paddress = BusinessMapAct.list.getCbusinessinfoList().get(i)
				.getBusinessaddress();
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
		BusinessMapAct.mMapView.updateViewLayout(BusinessMapAct.mPopView,
				new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT, pt,
						MapView.LayoutParams.BOTTOM_CENTER));
		BusinessMapAct.mPopView.setVisibility(View.VISIBLE);
		Item_Business_List layout=(Item_Business_List) BusinessMapAct.mPopView.findViewById(R.popview.layout);
		Msg_Cbusinessinfo cbuinfo=BusinessMapAct.list.getCbusinessinfoList().get(i);
		layout.set(cbuinfo);
		return true;
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		BusinessMapAct.mPopView.setVisibility(View.GONE);
		return super.onTap(arg0, arg1);
	}
}