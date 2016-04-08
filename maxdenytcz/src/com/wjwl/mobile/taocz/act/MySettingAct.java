package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.ExitLogindialog;
import com.wjwl.mobile.taocz.dialog.UpdateDialog;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class MySettingAct extends MActivity implements OnClickListener {

	private TczV3_HeadLayout hl_head;
	private RelativeLayout re_change_pwd;
	private RelativeLayout re_check_version;
	private RelativeLayout re_suggest;
	private RelativeLayout re_about_us;
	private Button btn_logout;
	TextView version;

	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.my_setting_act);
		setId("MySettingAct");
		mFInder();
		mBInder();
		mIniter();
	}

	private void mFInder() {
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		re_change_pwd = (RelativeLayout) findViewById(R.id.re_change_pwd);
		re_check_version = (RelativeLayout) findViewById(R.id.re_check_version);
		re_suggest = (RelativeLayout) findViewById(R.id.re_suggest);
		re_about_us = (RelativeLayout) findViewById(R.id.re_about_us);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		version=(TextView)findViewById(R.id.version);
		version.setText(getVersion());
	}

	private void mBInder() {
		hl_head.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MySettingAct.this.finish();
			}
		});
		re_change_pwd.setOnClickListener(this);
		re_check_version.setOnClickListener(this);
		re_suggest.setOnClickListener(this);
		re_about_us.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
	}

	private void mIniter() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.re_change_pwd:
			intent = new Intent(getApplicationContext(),
					ModifyPassWordAct.class);
			break;
		case R.id.re_about_us:
			intent = new Intent(getApplicationContext(), AboutAct.class);
			break;
		case R.id.re_check_version:
			intent = new Intent(getApplicationContext(), UpdateDialog.class);
			break;
		case R.id.re_suggest:
			// intent = new Intent(getApplicationContext(), FeedBackAct.class);
			Toast.makeText(MySettingAct.this, "请联系网站客服!", Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.btn_logout:
			ExitLogindialog ex = new ExitLogindialog(MySettingAct.this);
			ex.show();
			break;

		default:
			break;
		}
		if (null != intent) {
			MySettingAct.this.startActivity(intent);
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return "当前版本号  " + version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
