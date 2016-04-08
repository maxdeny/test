package com.zhuolei.mobilesafe.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;



public class NormalActivity extends Activity implements OnClickListener{
  
	
	private TextView tv_optimize,speedup,phonecharge,intercept,phonebook,payguard,killvirus;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_activity);
        findView();
        initView();

        
    }

	
	private void findView() {
		// TODO Auto-generated method stub
		tv_optimize = (TextView) findViewById(R.id.imgbtn_optimize);
		speedup = (TextView) findViewById(R.id.speedup);
		phonecharge = (TextView) findViewById(R.id.phonecharge);
		intercept = (TextView) findViewById(R.id.intercept);
		phonebook = (TextView) findViewById(R.id.addressbook);
		payguard = (TextView) findViewById(R.id.payguard);
		killvirus = (TextView) findViewById(R.id.killvirus);
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_optimize.setOnClickListener(this);
		speedup.setOnClickListener(this);
		phonecharge.setOnClickListener(this);
		intercept.setOnClickListener(this);
		phonebook.setOnClickListener(this);
		payguard.setOnClickListener(this);
		killvirus.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.imgbtn_optimize:
			break;
			
			
		case R.id.speedup:
			
			Intent intent = new Intent();
			intent.setClass(this, SpeedUpActivity.class);
			startActivity(intent);
			break;
			
			
		case R.id.phonecharge:
			break;
			
			
		case R.id.intercept:
			break;
			
			
		case R.id.addressbook:
			break;
			
			
		case R.id.payguard:
			break;
			
			
		case R.id.killvirus:
			break;
	
		}
	}
}
