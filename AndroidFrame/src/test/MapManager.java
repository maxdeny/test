package test;

import android.content.Context;
import android.util.Log;
import com.baidu.mapapi.*;

public class MapManager {
	
	static MapManager mDemoApp;
	
	//�ٶ�MapAPI�Ĺ�����
	BMapManager mBMapMan = null;
	
	// ��ȨKey
	// TODO: ���������Key,
	// �����ַ��http://dev.baidu.com/wiki/static/imap/key/
	String mStrKey = "8907E092BFB7B8200A09B1BD279775CE30AE2F27";
	boolean m_bKeyRight = true;	// ��ȨKey��ȷ����֤ͨ��
	
	// �����¼�������������ͨ�������������Ȩ��֤�����
	static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
			}
		}
	}

    public void Create(Context context) {
		Log.v("BMapApiDemoApp", "onCreate");
		mDemoApp = this;
		mBMapMan = new BMapManager(context);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);
		mBMapMan.getLocationManager().disableProvider(MKLocationManager.MK_GPS_PROVIDER);
		mBMapMan.getLocationManager().disableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
	}

	public void onTerminate() {
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
	}

}
