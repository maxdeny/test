package com.example.goldfoxchina.main;

import java.util.HashMap;

import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxchina.util.FileCache;
import com.example.goldfoxchina.util.SPfSaveData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 启动页
 * 
 * @author kysl
 * 
 */
public class StartActivity extends Activity {

	private Dialog dialog;
	private long preTime;
	private long nowTime;
	private boolean tag;
	private String strtime="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_start);
		
		if(!GetNetWorkData.isConn(this)){    //判断本地网络
			GetNetWorkData.setNetworkMethod(this);
		}else{
			new Check().execute();
		}
		
		
	}

	class Check extends AsyncTask<Void, String, String> {
		boolean flag;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(StartActivity.this, "")
					.createLoadingDialog();
			dialog.show();
			
			GetNetWorkData.isConn(StartActivity.this);  //zh—多余
			
		}

		@Override
		protected String doInBackground(Void... params) {

			HashMap<String, String> hashmap=GetJsonData
					.SignInReturnData(StartActivity.this, "", "");
			
			if(hashmap!=null){
				strtime =hashmap.get("time").trim();
			}
			return strtime;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			
			if(!"".equals(result)){
				
				/** 缓存超过三天清理掉 */
				if (nowTime - preTime > Config.TIME) {
					tag = new FileCache(StartActivity.this).clear();

					if (tag == true) {
						Toast.makeText(StartActivity.this, "文件已清除",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(StartActivity.this, "文件不存在",
								Toast.LENGTH_SHORT).show();
					}
					SPfSaveData.getspf(StartActivity.this).WriteData("time",
							strtime);
				}

				Intent intent = new Intent();
				intent.setClass(StartActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				
			}else{
				GetNetWorkData.ServerMessage(StartActivity.this, "");
			}
			dialog.dismiss();

		}
	}

}
