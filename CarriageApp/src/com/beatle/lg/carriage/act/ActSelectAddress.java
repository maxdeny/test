package com.beatle.lg.carriage.act;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.beatle.lg.carriage.MApplication;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.adapter.AdaSelectAddress;
import com.beatle.lg.carriage.widget.ItemHeadLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 
 * @Title: ActMapShopManager
 * @ToDo: 商家管理 地址描点
 * @author Administrator
 * @version v 1.0
 * @date [2016-1-6上午10:07:24]
 */
public class ActSelectAddress extends BaseActivity implements OnGetGeoCoderResultListener {
    
    @ViewInject(R.id.header)
    private ItemHeadLayout header;
    
    @ViewInject(R.id.view_map)
    private MapView mMapView;
    
    @ViewInject(R.id.tv_map_sure)
    private TextView tv_map_sure;
    
    @ViewInject(R.id.lv_map_result)
    private ListView lv_map_result;
    
    // 经纬度
    private LatLng target;
    
    private GeoCoder mSearch = null;
    
    // 定位相关
    LocationClient mLocClient;
    
    public MyLocationListenner myListener = new MyLocationListenner();
    
    private LocationMode mCurrentMode;
    
    BitmapDescriptor mCurrentMarker;
    
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;
    
    BaiduMap mBaiduMap;
    
    private LayoutInflater layoutInflater;
    
    boolean isFirstLoc = true; // 是否首次定位
    
    private AdaSelectAddress adapter;
    
    private String address = null;
    
    // 经纬度
    private Double lng, lat;
    
    private View mapheadview;
    
    private TextView name;
    
    private TextView address_detail;
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
        this.setContentView(R.layout.act_select_address);
        ViewUtils.inject(this);
        header.title.setText("地址选择");
        header.btn_back.setVisibility(View.VISIBLE);
        layoutInflater = LayoutInflater.from(this);
        mapheadview = layoutInflater.inflate(R.layout.item_select_address_header, null);
        name = (TextView) mapheadview.findViewById(R.id.tv_map_address);
        address_detail = (TextView) mapheadview.findViewById(R.id.tv_map_address_detail);
        mCurrentMode = LocationMode.NORMAL;
        
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.view_map);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setBuildingsEnabled(false);
        // 定位初始化
        mLocClient = ((MApplication) getApplication()).mLocationClient;
        mLocClient.stop();
        mLocClient.registerLocationListener(myListener);
        mLocClient.start();
        //        mLocClient = new LocationClient(this);
        //        mLocClient.registerLocationListener(myListener);
        //        LocationClientOption option = new LocationClientOption();
        //        option.setOpenGps(true); // 打开gps
        //        option.setCoorType("bd09ll"); // 设置坐标类型
        //        option.setScanSpan(1000);
        //        mLocClient.setLocOption(option);
        //        mLocClient.start();
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        //地图状态改变的监听事件
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                // TODO Auto-generated method stub
                target = mapStatus.target;
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(target));
            }
            
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                // TODO Auto-generated method stub
                
            }
        });
        setListener();
    }
    
    public void setListener() {
        lv_map_result.setOnItemClickListener(new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                // mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                // .location(adapter.getItem(position).location));
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(adapter.getItem(position - 1).location));
                address = adapter.getItem(position - 1).address;
                lng = adapter.getItem(position - 1).location.longitude;
                lat = adapter.getItem(position - 1).location.latitude;
            }
        });
        
        tv_map_sure.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("address", address);
                intent.putExtra("lng", lng);
                intent.putExtra("lat", lat);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        header.btn_back.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }
    
    //反Geo搜索的监听事件
    @Override
    public void onGetReverseGeoCodeResult(final ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(ActSelectAddress.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
        //		Toast.makeText(ActSelectAddress.this, result.getAddress(),
        //				Toast.LENGTH_LONG).show();
        adapter = new AdaSelectAddress(this, result.getPoiList());
        TextView name = (TextView) mapheadview.findViewById(R.id.tv_map_address);
        TextView address_detail = (TextView) mapheadview.findViewById(R.id.tv_map_address_detail);
        name.setText("[当前]" + result.getAddressDetail().street);
        address_detail.setText(result.getAddress());
        lv_map_result.removeHeaderView(mapheadview);
        lv_map_result.addHeaderView(mapheadview);
        mapheadview.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
                address = result.getAddress();
                lng = result.getLocation().longitude;
                lat = result.getLocation().latitude;
            }
        });
        lv_map_result.setAdapter(adapter);
        
    }
    
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100)
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude())
                    .build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    
    @Override
    protected void saveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    
    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
