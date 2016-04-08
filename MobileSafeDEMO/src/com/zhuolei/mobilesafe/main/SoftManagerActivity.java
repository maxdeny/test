package com.zhuolei.mobilesafe.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zhuolei.mobilesafe.base.MyBaseActivity;
import com.zhuolei.mobilesafe.softmanager.ApkManager;
import com.zhuolei.mobilesafe.softmanager.SoftAnalyse;
import com.zhuolei.mobilesafe.softmanager.SoftMove;
import com.zhuolei.mobilesafe.softmanager.SoftUninstall;

public class SoftManagerActivity extends MyBaseActivity implements OnClickListener{
	   
	
	private TextView tv_mobileNecessary;
	private TextView tv_securityMarket;
	private TextView tv_softUpdata;
	private TextView tv_softUninstall;
	private TextView tv_apkManager;
	private TextView tv_softMove;
	private TextView tv_softanalyse;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.soft_activity);
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		tv_mobileNecessary = (TextView) findViewById(R.id.softnecessary);
		tv_securityMarket = (TextView) findViewById(R.id.securitymarket);
		tv_softUpdata = (TextView) findViewById(R.id.softupdata);
		tv_softUninstall = (TextView) findViewById(R.id.softuninstall);
		tv_apkManager = (TextView) findViewById(R.id.apkmanager);
		tv_softMove = (TextView) findViewById(R.id.softmove);
		tv_softanalyse = (TextView) findViewById(R.id.softanalyse);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		tv_mobileNecessary.setOnClickListener(this);
		tv_securityMarket.setOnClickListener(this);
		tv_softUpdata.setOnClickListener(this);
		tv_softUninstall.setOnClickListener(this);
		tv_apkManager.setOnClickListener(this);
		tv_softMove.setOnClickListener(this);
		tv_softanalyse.setOnClickListener(this);
	}

	@Override
	protected void controll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.softnecessary:
			//打开手机软件程序  安卓市场 360软件管理 等等
			break;
		case R.id.securitymarket:
			//打开手机软件程序  安卓市场 360软件管理 等等
			break;
		case R.id.softupdata:
			//打开手机软件程序  安卓市场 360软件管理 等等
			break;
		case R.id.softuninstall:
			Intent unInstall = new Intent(this,SoftUninstall.class);
			startActivity(unInstall);
			break;
		case R.id.apkmanager:
			Intent apkManager = new Intent(this,ApkManager.class);
			startActivity(apkManager);
			break;
		case R.id.softmove:
			Intent softMove = new Intent(this,SoftMove.class);
			startActivity(softMove);
			break;
		case R.id.softanalyse:
			Intent softAnalyse = new Intent(this,SoftAnalyse.class);
			startActivity(softAnalyse);
			break;
		}
	}
}