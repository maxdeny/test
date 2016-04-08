package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Point;
//import android.graphics.drawable.Drawable;
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.mapapi.GeoPoint;
//import com.baidu.mapapi.ItemizedOverlay;
//import com.baidu.mapapi.LocationListener;
//import com.baidu.mapapi.MKAddrInfo;
//import com.baidu.mapapi.MKBusLineResult;
//import com.baidu.mapapi.MKDrivingRouteResult;
//import com.baidu.mapapi.MKLocationManager;
//import com.baidu.mapapi.MKPlanNode;
//import com.baidu.mapapi.MKPoiResult;
//import com.baidu.mapapi.MKSearch;
//import com.baidu.mapapi.MKSearchListener;
//import com.baidu.mapapi.MKSuggestionResult;
//import com.baidu.mapapi.MKTransitRouteResult;
//import com.baidu.mapapi.MKWalkingRouteResult;
//import com.baidu.mapapi.MapController;
//import com.baidu.mapapi.MapView;
//import com.baidu.mapapi.OverlayItem;
//import com.baidu.mapapi.Projection;
//import com.baidu.mapapi.RouteOverlay;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MMapActivity;
//import com.wjwl.mobile.taocz.R;
//
//public class CommonPublicDetails extends MMapActivity {
//	
//	private boolean showflag=true;
//	static MapView mMapView;
//	public TextView title,address,distance,phone,text;
//	Button  btnlist,btnback,btntogo;
//	private MapController mapController;
//	LocationListener mLocationListener = null;
//	GeoPoint mypt = new GeoPoint(31836553, 119977980);
//	GeoPoint placept;
//	static LinearLayout mPopView = null;
//	public double lat,lon;
//	OverItemT overitem = null;
//	MKSearch mSearch = null;
//	String mypostion;
//	RelativeLayout placelayout;
//	@Override
//	protected void create(Bundle arg0) {
//		address.setText(getIntent().getStringExtra("address"));
//		phone.setText(getIntent().getStringExtra("phoneno"));
//		distance.setText(getIntent().getStringExtra("distance"));
//		title.setText(getIntent().getStringExtra("title"));
//		text.setText(getIntent().getStringExtra("title"));
//		String[] pointstr= new String[]{};
//		pointstr=getIntent().getStringExtra("localpoint").split(",");
//		lat=Double.parseDouble(pointstr[0]);
//		lon=Double.parseDouble(pointstr[1]);
//		
//		Frame.MAP.create();
//		mMapView = (MapView) findViewById(R.commonpublicdetails.map_View);
//		mMapView.setDrawOverlayWhenZooming(true);
//		mapController = mMapView.getController();
//		mMapView.setVisibility(View.INVISIBLE);
//		
//		mapController.setZoom(13);
//		placept = new GeoPoint((int)(lat),(int)(lon));
//		mapController.setCenter(placept);
//		
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
//				Drawable marker = getResources().getDrawable(R.drawable.iconmarka);  //得到需要标在地图上的资源
//				marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker
//						.getIntrinsicHeight());   //为maker定义位置和边界
//				
//				overitem = new OverItemT(marker, this, 3);
//				mMapView.getOverlays().add(overitem); //添加ItemizedOverlay实例到mMapView
//		
//				mPopView=(LinearLayout)super.getLayoutInflater().inflate(R.layout.popview, null);
//				mMapView.addView( mPopView,
//		                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//		                		null, MapView.LayoutParams.TOP_LEFT));
//				mPopView.setVisibility(View.GONE);
//				
//				
//				  mSearch = new MKSearch();
//			        mSearch.init(Frame.MAP.getmBMapMan(), new MKSearchListener(){
//
//						public void onGetDrivingRouteResult(MKDrivingRouteResult res,
//								int error) {
//							// 错误号可参考MKEvent中的定义
//							CommonPublicDetails.this.closeload();
//							CommonPublicDetails.mPopView.setVisibility(View.GONE);
//							if (error != 0 || res == null) {
//								Toast.makeText(CommonPublicDetails.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//								return;
//							}
//							RouteOverlay routeOverlay = new RouteOverlay(CommonPublicDetails.this, mMapView);
//						    // 此处仅展示一个方案作为示例
//						    routeOverlay.setData(res.getPlan(0).getRoute(0));
//						    mMapView.getOverlays().clear();
//						    mMapView.getOverlays().add(routeOverlay);
//						    mMapView.invalidate();
//						    
//						    mMapView.getController().animateTo(res.getStart().pt);
//						}
//
//						public void onGetTransitRouteResult(MKTransitRouteResult res,
//								int error) {
//						}
//						public void onGetWalkingRouteResult(MKWalkingRouteResult res,
//								int error) {
//						}
//						public void onGetAddrResult(MKAddrInfo res, int error) {
//							mypostion=res.addressComponents.street+(res.addressComponents.streetNumber==null?"":res.addressComponents.streetNumber);
//					        	
//						}
//						public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
//						}
//						public void onGetBusDetailResult(MKBusLineResult result, int iError) {
//						}
//
//						@Override
//						public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
//						}
//			            @Override
//			            public void onGetRGCShareUrlResult(String arg0, int arg1) {
//			            }
//			        });
//	}
//
//	@Override
//	protected void initcreate(Bundle arg0) {
//		setContentView(R.layout.commonpublicdetails);
//		
//		address=(TextView)findViewById(R.commonpublicdetails.address);
//		phone=(TextView)findViewById(R.commonpublicdetails.phone);
//		distance=(TextView)findViewById(R.commonpublicdetails.distance);
//		title=(TextView)findViewById(R.commonpublicdetails.title);
//		text=(TextView)findViewById(R.commonpublicdetails.text);
//		btnlist=(Button)findViewById(R.commonpublicdetails.btnlist);
//		btnback=(Button)findViewById(R.commonpublicdetails.back);
//		btntogo=(Button)findViewById(R.commonpublicdetails.togo);
//		placelayout=(RelativeLayout)findViewById(R.commonpublicdetails.place);
//		
//		btnlist.setOnClickListener(new onclickListener());
//		btnback.setOnClickListener(new onclickListener());
//		btntogo.setOnClickListener(new onclickListener());
//		btnback.setText(this.getIntent().getStringExtra("titletype"));
//	}
//	
//	public class  onclickListener implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.commonpublicdetails.back:
//				finish();
//				break;
//            case R.commonpublicdetails.btnlist:
//            	if(showflag){
//            		placelayout.setVisibility(View.GONE);
//            		mMapView.setVisibility(View.VISIBLE);
//            		btnlist.setText("列表");
//            		showflag=false;
//            	}
//            	else{
//            		placelayout.setVisibility(View.VISIBLE);
//            		mMapView.setVisibility(View.INVISIBLE);
//            		btnlist.setText("地图");
//            		showflag=true;
//            	}
//            	break;
//			case R.commonpublicdetails.togo:
//				placelayout.setVisibility(View.GONE);
//				mMapView.setVisibility(View.VISIBLE);
//				btnlist.setText("列表");
//				showflag=false;
//				SearchButtonProcess(v);
//				break;
//
//            default:
//				break;
//			}
//		}
//	}
//	
//	void SearchButtonProcess(View v) {
//		
//		MKPlanNode stNode = new MKPlanNode();
//		stNode.pt=mypt;
//		MKPlanNode enNode = new MKPlanNode();
//		enNode.pt=placept;
//			mSearch.drivingSearch("常州", stNode, "常州", enNode);
//		CommonPublicDetails.this.showload();
//	}
//
//	@Override
//	protected boolean isRouteDisplayed() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	protected void resume() {
//		Frame.MAP.getmBMapMan().getLocationManager().requestLocationUpdates(mLocationListener);
//		Frame.MAP.start();
//		Frame.MAP.getmBMapMan().getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//	};
//	
//	protected void pause() {
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.removeUpdates(mLocationListener);
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//
//		if (Frame.MAP.getmBMapMan() != null)
//			Frame.MAP.stop();
//	}
//	
//	
//	class OverItemT extends ItemizedOverlay<OverlayItem> {
//
//		public List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
//		private Drawable marker;
//		private Context mContext;
//
//		private double mLat1 = lat; // point1纬度
//		private double mLon1 = lon; // point1经度
//
//
//		public OverItemT(Drawable marker, Context context, int count) {
//			super(boundCenterBottom(marker));
//
//			this.marker = marker;
//			this.mContext = context;
//
//			// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
//			GeoPoint p1 = new GeoPoint((int) (mLat1 ), (int) (mLon1 ));
//			
//			// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
//			mGeoList.add(new OverlayItem(p1, "", text.getText().toString()));
//			populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
//		}
//
//		public void updateOverlay()
//		{
//			populate();
//		}
//
//		@Override
//		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
//
//			// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
//			Projection projection = mapView.getProjection(); 
//			for (int index = size() - 1; index >= 0; index--) { // 遍历mGeoList
//				OverlayItem overLayItem = getItem(index); // 得到给定索引的item
//
//				String title = overLayItem.getTitle();
//				// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
//				Point point = projection.toPixels(overLayItem.getPoint(), null); 
//
//				// 可在此处添加您的绘制代码
//				Paint paintText = new Paint();
//				paintText.setColor(Color.BLUE);
//				paintText.setTextSize(15);
//				canvas.drawText(title, point.x-30, point.y, paintText); // 绘制文本
//			}
//
//			super.draw(canvas, mapView, shadow);
//			//调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
//			boundCenterBottom(marker);
//		}
//
//		@Override
//		protected OverlayItem createItem(int i) {
//			// TODO Auto-generated method stub
//			return mGeoList.get(i);
//		}
//
//		@Override
//		public int size() {
//			// TODO Auto-generated method stub
//			return mGeoList.size();
//		}
//		@Override
//		// 处理当点击事件
//		protected boolean onTap(int i) {
//			setFocus(mGeoList.get(i));
//			CommonPublicDetails.mPopView.removeAllViews();
//			// 更新气泡位置,并使之显示
//			GeoPoint pt = mGeoList.get(i).getPoint();
//			CommonPublicDetails.mMapView.updateViewLayout( CommonPublicDetails.mPopView,
//	                new MapView.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//	                		pt, MapView.LayoutParams.BOTTOM_CENTER));
//			CommonPublicDetails.mPopView.setVisibility(View.VISIBLE);
//			
//			TextView textView= new TextView(mContext);
//			textView.setText(mGeoList.get(i).getSnippet());
//			CommonPublicDetails.mPopView.addView(textView);
//			
//			Toast.makeText(this.mContext, mGeoList.get(i).getSnippet(),
//					Toast.LENGTH_SHORT).show();
//			return true;
//		}
//
//		@Override
//		public boolean onTap(GeoPoint arg0, MapView arg1) {
//			// TODO Auto-generated method stub
//			CommonPublicDetails.mPopView.setVisibility(View.GONE);
//			return super.onTap(arg0, arg1);
//		}
//	}
//}
