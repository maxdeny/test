package com.my.spyware.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.example.antispyware.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.http.Son;
import com.mdx.mobile.http.Updateone;
import com.mdx.mobile.http.json.Updateone2json;
import com.mdx.mobile.tasks.MException;
import com.my.spyware.F;
import com.my.spyware.MApplication;
import com.my.spyware.dialog.MyProgressDialog;
import com.my.spyware.util.GPSUtil;
import com.my.spyware.widget.ItemGuideOverlay;
import com.my.spyware.widget.NewAlertDialogWithTitle;
import com.my.spyware.widget.OnNewClickListener;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xcecs.data.dw.DW_User.MsgUserInfo;
import com.xcecs.data.dw.DW_User.MsgUserList;
import com.xcecs.data.dw.DW_User.MsgUserList.Builder;

/**
 * 首页
 * 
 * @Title: ActHome
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-14下午4:41:04]
 */
public class ActHome extends MActivity implements BDLocationListener,
		OnGetGeoCoderResultListener, OnMarkerClickListener {

	public static final String TAG = "ActHome";

	@ViewInject(R.id.img_qiyemap_relocation)
	private ImageView img_qiyemap_relocation;

	@ViewInject(R.id.bmapView)
	private MapView mMapView;

	private BaiduMap mBaiduMap;

	/* 定位相关 */
	LocationClient mLocClient;

	BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
			.fromResource(R.drawable.ic_current_location);

	BitmapDescriptor locationMark = BitmapDescriptorFactory
			.fromResource(R.drawable.location_mark);

	private LocationMode mCurrentMode = LocationMode.NORMAL;

	GeoCoder mSearch;// 搜索模块

	private boolean isFirstLoc = true;

	private double latitude, longitude;// 纬度、经度

	private Marker mMarkerLocation;

	private Vibrator vibrator;

	private List<MsgUserInfo> userInfos = new ArrayList<MsgUserInfo>();

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.act_home);
		ViewUtils.inject(this);
		initBaiduMap();
		setListener();
	}

	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 0:
			loadData(new Updateone[] { new Updateone("MBUserList",
					new String[][] { { "account_id", F.userId },
							{ "deviceid", F.DEVICEID } }) });
			break;
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.getMetod().equals("MBUserList")) {
			if (son.getError() == 0) {
				MsgUserList.Builder builder = (Builder) son.getBuild();
				userInfos = builder.getUserListList();
				if (userInfos.size() > 0) {

					isFirstLoc = true;
					setMarker();
				}
			} else {
				F.showToast(this, son.getMsg());
			}
		}

	}

	private void setMarker() {
		if (F.mLocation == null || mMapView == null) {
			Log.v(TAG, "onreceivelocation =null");
			return;
		}

		MyLocationData locData = new MyLocationData.Builder().accuracy(0)
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(F.mLocation.getLatitude())
				.longitude(F.mLocation.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		if (isFirstLoc) {
			isFirstLoc = false;
			clearOverlay();
			// 初始化当前位置的经纬度，并定位到当前位置
			latitude = F.mLocation.getLatitude();// 纬度
			longitude = F.mLocation.getLongitude();// 经度
			LatLng ll = new LatLng(latitude, longitude);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.setMapStatus(u);
			if (userInfos != null && userInfos.size() > 0) {
				mappingGuides2Overlay(userInfos);
			}

		}
	}

	private void setListener() {
		// TODO Auto-generated method stub
		mBaiduMap.setOnMarkerClickListener(this);
	}

	private void initBaiduMap() {
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.showZoomControls(false);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
				mCurrentMode, false, mCurrentMarker));
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setAddrType("all");//
		mLocClient.setLocOption(option);
		// mLocClient.start();
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);
		if (!GPSUtil.isGpsOpen(this))
			showGpsNotOpenDialog();
	}

	public void goNextPage() {
		// this.startActivity(new Intent(this, ActNavigation.class));
		this.finish();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		// TODO Auto-generated method stub
		// map view 销毁后不在处理新接收的位置
		if (location == null || mMapView == null) {
			Log.v(TAG, "onreceivelocation =null");
			return;
		}

		MyLocationData locData = new MyLocationData.Builder().accuracy(0)
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(100).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);
		if (isFirstLoc) {
			isFirstLoc = false;
			clearOverlay();
			// 初始化当前位置的经纬度，并定位到当前位置
			latitude = location.getLatitude();// 纬度
			longitude = location.getLongitude();// 经度
			LatLng ll = new LatLng(latitude, longitude);
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
			mBaiduMap.setMapStatus(u);
			if (userInfos != null && userInfos.size() > 0) {
				mappingGuides2Overlay(userInfos);
			}

		}
	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
		// TODO Auto-generated method stub
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			F.showToast(this, "抱歉，未能找到结果");
			return;
		}
		clearOverlay();
		// 初始化当前位置的经纬度，并定位到当前位置
		LatLng latlng = result.getLocation();
		latitude = latlng.latitude;
		longitude = latlng.longitude;
		OverlayOptions ooA = new MarkerOptions().position(latlng)
				.icon(locationMark).zIndex(100);
		mMarkerLocation = (Marker) (mBaiduMap.addOverlay(ooA));
		mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(latlng,
				14.0f));
		// 初始化地址信息
		AddressComponent component = result.getAddressDetail();
		String locationStr = component.city + component.district
				+ component.street + component.streetNumber;
		if (locationStr.contains("null"))
			locationStr = "暂时无法获得您的位置";
		if (userInfos != null && userInfos.size() > 0) {
			mappingGuides2Overlay(userInfos);
		}
	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay() {
		mBaiduMap.clear();
	}

	private void mappingGuides2Overlay(List<MsgUserInfo> list) {
		for (int i = 0; i < list.size(); i++)
			addOverlayAt(list.get(i), i);
	}

	private void addOverlayAt(final MsgUserInfo guide, final int index) {
		if (F.isEmpty(guide.getLat()) || F.isEmpty(guide.getLng())
				|| guide.getLat().equals("null")
				|| guide.getLng().equals("null")) {
			Log.v(TAG, "经纬度不存在");
			return;
		}
		Log.v(TAG, guide.getAccount() + "guide.lng" + guide.getLng() + "lat:"
				+ guide.getLat());
		LatLng latlng = new LatLng(Double.parseDouble(guide.getLng() + 2),
				Double.parseDouble(guide.getLat() + 2));
		ItemGuideOverlay igo = new ItemGuideOverlay(this);
		// igo.setContent(guide, new ImageLoadingListener() {
		//
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// //
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view, FailReason
		// failReason) {
		// initGuideMarker(igo, latlng, index, guide.getId());
		// }
		//
		// @Override
		// public void onLoadingComplete(String imageUri, View view, Bitmap
		// loadedImage) {
		// initGuideMarker(igo, latlng, index, guide.getId());
		// }
		//
		// @Override
		// public void onLoadingCancelled(String imageUri, View view) {
		// //
		// }
		// });

		igo.setContent(guide);
		initGuideMarker(igo, latlng, index, guide.getId());
	}

	private void initGuideMarker(ItemGuideOverlay overlay, LatLng ll,
			int index, String id) {
		LatLng latlng = new LatLng(F.mLocation.getLatitude(),
				F.mLocation.getLongitude());
		BitmapDescriptor bd = BitmapDescriptorFactory.fromView(overlay);
		OverlayOptions oo = new MarkerOptions().position(ll).icon(bd)
				.zIndex(index);
		Marker marker = (Marker) (mBaiduMap.addOverlay(oo));
		Bundle bundle = new Bundle();// 傳參導遊id
		bundle.putString("id", id);
		marker.setExtraInfo(bundle);
		bd.recycle();
	}

	// GPS未打开提示对话框
	private void showGpsNotOpenDialog() {
		showNewAlertDialogWithTitle("温馨提示", "请打开GPS，以便获得\n准确的位置定位", "取消", "确定",
				new OnNewClickListener() {

					@Override
					public void onRightClick() {// 确定
						gotoGpsSystemSetting();
					}

					@Override
					public void onLeftClick() {// 取消
						//
					}
				});
	}

	protected void showNewAlertDialogWithTitle(String title, String msg,
			String btnLeft, String btnRight, OnNewClickListener l) {
		NewAlertDialogWithTitle nadwt = new NewAlertDialogWithTitle(this);
		nadwt.setTitle(title);
		nadwt.setMessage(msg);
		nadwt.setLeftButton(btnLeft);
		nadwt.setRightButton(btnRight);
		nadwt.setOnNewClickListener(l);
		nadwt.show();
	}

	// 去GPS设置页面
	private void gotoGpsSystemSetting() {
		startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}

	@Override
	public void showError(MException me) {
		super.showError(me);
	}

	@OnClick({ R.id.img_qiyemap_relocation })
	public void mOnClick(View view) {
		switch (view.getId()) {
		case R.id.img_qiyemap_relocation:
			isFirstLoc = true;
			setMarker();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		super.disposeMsg(type, obj);
		if (type == 0) {
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
			vibrator.vibrate(pattern, 2); // 重复两次上面的pattern 如果只想震动一次，index设为-1
		}

	}
	
	

	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"请稍后···");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		initdialog();
		dataLoad(new int[] { 0 });
		((MApplication) getApplication()).isSendLocation = true;
		((MApplication) getApplication()).sendLocation();
	}

}
