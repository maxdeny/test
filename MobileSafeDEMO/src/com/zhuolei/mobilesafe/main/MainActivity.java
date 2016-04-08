package com.zhuolei.mobilesafe.main;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.zhuolei.mobilesafe.network.NetworkInfos;

public class MainActivity extends Activity {

	
	ImageView start_img;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        //initView();
        networkCheck();
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		networkCheck();
	}


	private void findView() {
		// TODO Auto-generated method stub
	}

	private void initView() {
		// TODO Auto-generated method stub
		
	}
    
    
	private void networkCheck() {
		// TODO Auto-generated method stub
		NetworkInfos networkInfo = NetworkInfos.getNetworkInfo();
		boolean bConnFlag = networkInfo.isConn(this);
		if(!bConnFlag) {
			networkInfo.setNetwork(this);
			
		}else{
			checkVersion();	
		}
		
	}

	/**
	 * 版本检查
	 */
	private void checkVersion() {
		// TODO Auto-generated method stub
		boolean bupdate = false;
		if(!bupdate) {
			Intent intent = new Intent(MainActivity.this, TabhostActivity.class);
            startActivity(intent);
            this.finish();
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
