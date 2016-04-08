package com.zhuolei.mobilesafe.clean;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.zhuolei.mobilesafe.base.MyBaseActivity;
import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.main.R.id;
import com.zhuolei.mobilesafe.main.R.layout;


public class ForceClean extends MyBaseActivity implements OnClickListener{
	
	private LinearLayout force_speed,unstall,privacy_clean,save_picspace,soft_move;
	
	@Override
	protected void setContentView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_forceclean);
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		force_speed = (LinearLayout) findViewById(R.id.forcespeed);
		unstall = (LinearLayout) findViewById(R.id.unstall);
		privacy_clean = (LinearLayout) findViewById(R.id.privacy_clean);
		save_picspace = (LinearLayout) findViewById(R.id.save_space);
		soft_move = (LinearLayout) findViewById(R.id.soft_move);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		force_speed.setOnClickListener(this);
		unstall.setOnClickListener(this);
		privacy_clean.setOnClickListener(this);
		save_picspace.setOnClickListener(this);
		soft_move.setOnClickListener(this);
	}

	@Override
	protected void controll() {
		// TODO Auto-generated method stub
		//处理存储进度
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.forcespeed:;
			Intent intent = new Intent(this,StrongSpeedUp.class);
			startActivity(intent);
			break;
		case R.id.unstall:
			Intent intent_unstall = new Intent(this,PreUnstall.class);
			startActivity(intent_unstall);
			break;
		case R.id.privacy_clean:
			Intent intent_privacy = new Intent(this,PrivaceClean.class);
			startActivity(intent_privacy);
			break;
		case R.id.save_space:
			Intent intent_savespace = new Intent(this,SavePicSpace.class);
			startActivity(intent_savespace);
			break;
		case R.id.soft_move:
			Intent intent_softmove = new Intent(this,SoftMove.class);
			startActivity(intent_softmove);
			break;
		}
	}
		

}
