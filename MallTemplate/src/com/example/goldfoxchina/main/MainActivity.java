package com.example.goldfoxchina.main;

import com.example.goldfoxchina.net.GetNetWorkData;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * 启动页面
 * @author kysl
 *
 */
public class MainActivity extends Activity {

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        init();
	    }
	    //判断初始动画Flipper是否加载过(判断是否第一次进入)
	    public void init(){
	        SharedPreferences preferences = getSharedPreferences("InitAnimation", MODE_PRIVATE);
	        boolean isFirst = preferences.getBoolean("isFirst",false);
	        if(isFirst==false){
	            Intent intent = new Intent(MainActivity.this,InitAnimationActivity.class);
	            startActivity(intent);
	            finish();
	        }else{
	            Intent intent = new Intent(MainActivity.this,StartActivity.class);
	            startActivity(intent);
	            finish();
	        }
	    }
	    
	    /**
		 * 重写返回键事件
		 */

		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				GetNetWorkData.BackDialog(MainActivity.this);
				return true;
			}
			return false;
		}
}
