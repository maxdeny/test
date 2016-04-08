package com.zhuolei.mobilesafe.luncher;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.zhuolei.mobilesafe.main.MainActivity;
import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.main.TabhostActivity;
import com.zhuolei.mobilesafe.network.NetworkInfos;
import com.zhuolei.mobilesafe.util.AnimationUtil;


public class SplashActivity extends Activity {
	private boolean bConnFlag =false;
	private static final long DELAY_TIME = 3000L;
	private int done = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		//networkCheck();
		if(bConnFlag){
			checkVersion();	
			redirectByTime();
			done = 1;
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		networkCheck();
		if(bConnFlag && done ==0){
			checkVersion();	
			redirectByTime();
		}
	}
	
	private void redirectByTime() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this,TabhostActivity.class));
				AnimationUtil.finishActivityAnimation(SplashActivity.this);
			}
		}, DELAY_TIME);
	}
	
	private void networkCheck() {
		// TODO Auto-generated method stub
		NetworkInfos networkInfo = NetworkInfos.getNetworkInfo();
		bConnFlag = networkInfo.isConn(this);
		if(!bConnFlag) {
			networkInfo.setNetwork(this);
		}
		
	}

	/**
	 * 版本检查
	 */
	private void checkVersion() {
		// TODO Auto-generated method stub
		boolean bupdate = false;
		if(!bupdate) {
//			Intent intent = new Intent(this, TabhostActivity.class);
//            startActivity(intent);
//            this.finish();
		}else {
			new DownloadApp().execute();
		}
	}



	
	
	/**
	 * 
	 * @author Administrator
	 *版本下载 未实现
	 */
    class DownloadApp extends AsyncTask<Void, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}
    	
    }
    
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
        if ((keyCode == KeyEvent.KEYCODE_BACK)) { 
             System.exit(0); 
             return true; 
        }else { 
            return super.onKeyDown(keyCode, event); 
        } 
           
    }
	
	
}
