package com.mdx.mobile.mcommons;

import android.content.Context;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.MKLocationManager;
import com.mdx.mobile.Frame;

public class MMap {
	private Context context = null;
	private BMapManager mBMapMan = null;
	private MKGeneralListener getnerallListener;
	private boolean m_bKeyRight = true;
	private boolean started;

	public MMap(Context context, MKGeneralListener getnerallListener){
		this.context=context;
		this.getnerallListener=getnerallListener;
	}
	
	class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			if(getnerallListener!=null){
				getnerallListener.onGetNetworkState(iError);
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if(getnerallListener!=null){
				getnerallListener.onGetPermissionState(iError);
			}
		}
	}

	public void create() {
		mBMapMan = new BMapManager(context);
		mBMapMan.init(Frame.INITCONFIG.mMapKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);
		mBMapMan.getLocationManager().disableProvider(MKLocationManager.MK_GPS_PROVIDER);
		mBMapMan.getLocationManager().disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	}
	
	public void gps(){
		mBMapMan.getLocationManager().enableProvider(MKLocationManager.MK_GPS_PROVIDER);
	}
	
	public void network(){
		mBMapMan.getLocationManager().enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	}
	
	public void distory(){
		if (mBMapMan != null) {
			mBMapMan.stop();
			mBMapMan.destroy();
			mBMapMan = null;
			m_bKeyRight=true;
		}
	}
	
	public void start(){
		if(!started){
			mBMapMan.start();
			started=true;
		}
	}
	
	public void stop(){
		if(started){
			if(mBMapMan!=null){
				mBMapMan.stop();
			}
			started=false;
		}
	}

	public boolean isKeyRight() {
		return m_bKeyRight;
	}
	
	public BMapManager getmBMapMan() {
		return mBMapMan;
	}

	public void setGetnerallListener(MKGeneralListener getnerallListener) {
		this.getnerallListener = getnerallListener;
	}
	
	
	
	
}
