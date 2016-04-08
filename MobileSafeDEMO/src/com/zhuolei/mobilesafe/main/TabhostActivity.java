package com.zhuolei.mobilesafe.main;


import com.zhuolei.mobilesafe.clean.StrongSpeedUp;
import com.zhuolei.mobilesafe.softmanager.SoftUninstall;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.TabHost;

public class TabhostActivity extends TabActivity implements OnCheckedChangeListener{
	 
	private Intent intent_speed,intent_health,intent_safe,intent_soft;
	private RadioButton radio_speed,radio_health,radio_safe,radio_soft;
	private TabHost tabHost;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabhost);
        tabHost = this.getTabHost();
        findView();
        initView();
        addTabSpec();
        
    }
	

	private void findView() {
		// TODO Auto-generated method stub
		radio_speed = (RadioButton) findViewById(R.id.radio_speed);
		radio_health = (RadioButton) findViewById(R.id.radio_health);
		radio_safe = (RadioButton) findViewById(R.id.radio_safe);
		radio_soft = (RadioButton) findViewById(R.id.radio_soft);
	
	}

	private void initView() {
		// TODO Auto-generated method stub
		radio_speed.setOnCheckedChangeListener(this);
		radio_health.setOnCheckedChangeListener(this);
		radio_safe.setOnCheckedChangeListener(this);
		radio_soft.setOnCheckedChangeListener(this);

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_speed:
				tabHost.setCurrentTabByTag("speed");
				break;
			case R.id.radio_health:
				tabHost.setCurrentTabByTag("health");
				break;
			case R.id.radio_safe:
				tabHost.setCurrentTabByTag("safe");
				break;
			case R.id.radio_soft:
				tabHost.setCurrentTabByTag("soft");
				break;
			}
		}
	}
	
	
		private void addTabSpec() {
			
			TabHost.TabSpec tabSpec_speed = tabHost.newTabSpec("speed");
			tabSpec_speed.setIndicator("speed", null);
			intent_speed = new Intent();
			intent_speed.setClass(this, NormalActivity.class);
			tabSpec_speed.setContent(intent_speed);
			tabHost.addTab(tabSpec_speed);
			
			TabHost.TabSpec tabSpec_health = tabHost.newTabSpec("health");
			tabSpec_health.setIndicator("health", null);
	        intent_health = new Intent();
	        intent_health.setClass(this, SoftManagerActivity.class);
	       // intent_health.setClass(this, StrongSpeedUp.class);
	        tabSpec_health.setContent(intent_health);
	        tabHost.addTab(tabSpec_health);

			TabHost.TabSpec tabSpec_safe = tabHost.newTabSpec("safe");
			tabSpec_safe.setIndicator("safe", null);
	        intent_safe = new Intent();
	       // intent_safe.setClass(this, SafeActivity.class);
	        intent_safe.setClass(this, SoftUninstall.class);
	        tabSpec_safe.setContent(intent_safe);
	        tabHost.addTab(tabSpec_safe);

			TabHost.TabSpec tabSpec_soft = tabHost.newTabSpec("soft");
			tabSpec_soft.setIndicator("soft", null);
			intent_soft = new Intent();
			intent_soft.setClass(this, PrivacyProActivity.class);
			tabSpec_soft.setContent(intent_soft);
			tabHost.addTab(tabSpec_soft);

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
