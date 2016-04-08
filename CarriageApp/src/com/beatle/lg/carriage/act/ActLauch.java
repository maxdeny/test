package com.beatle.lg.carriage.act;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.jpush.android.api.JPushInterface;

import com.beatle.lg.carriage.F;
import com.beatle.lg.carriage.MApplication;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.data.CityList;
import com.beatle.lg.carriage.dialog.MyProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @Title: ActLauch
 * @ToDo: TODO启动页面
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-14下午2:27:00]
 */
public class ActLauch extends BaseActivity {

	private Handler mHandler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			switch (msg.what) {
			case 0:
				moveNext();
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		initdialog();
		setContentView(R.layout.act_lauch);
		ViewUtils.inject(this);

		/* 友盟 */
		MobclickAgent.setDebugMode(true);
		/* 友盟统计策略 禁止默认的页面统计方式(针对包含fragment的工程)，这样将不会再自动统计Activity */
		MobclickAgent.openActivityDurationTrack(false);
		/**
		 * 友盟发送策略 在程序的入口 Activity 中添加 mode1:启动时发送;mode2:按间隔发送 ,在友盟后台设置
		 */
		MobclickAgent.updateOnlineConfig(this);
		// 设置默认传参
		initJpush();
		F.getLoginData(this);
		F.setAutoPost();
		dataLoad(new int[] { 0 });
		((MApplication) getApplication()).startLocation();
		mHandler.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 0:
			loadData(new Updateone[] { new Updateone2json("AddressList",
					new String[][] {}) });
			break;

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.mgetmethod.equals("AddressList")) {

			if (son.getError() == 200) {
				CityList cityList = new CityList();
				cityList.build((JSONObject) son.getBuild());
				if (cityList.cityList.size() > 0) {
					F.cityList = cityList.cityList;
				}

			} else {
				F.showToast(this, son.getError());
			}

		}
	}

	private void initJpush() {
		// TODO Auto-generated method stub
		/* 设置开启日志,发布时请关闭日志 */
		JPushInterface.setDebugMode(true);
		/* 初始化 JPush */
		JPushInterface.init(this);
	}

	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this, "请稍后···");
	}

	public void moveNext() {
		F.getLoginData(ActLauch.this);
		if (F.isEmpty(F.carrierId)) {
			move2Login();
			// move2Frame();
		} else {
			move2Frame();
		}
	}

	private void move2Frame() {
		Intent i = new Intent();
		// i.setClass(this, TabhostActivity.class);
		startActivity(i);
		this.finish();

	}

	public void move2Login() {
		Intent i = new Intent();
		i.setClass(this, ActLogin.class);
		startActivity(i);
		this.finish();
	}

	@Override
	protected void saveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void restoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
