package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.OrderConfirmationAdapter;
import com.wjwl.mobile.taocz.commons.Arith;

public class V3_NoSupportHDFKAct extends MActivity {

	private ArrayList<Msg_CitemList> orderList;
	private OrderConfirmationAdapter OCAdp;
	private ListView lv;
	private Context mContext;
	RelativeLayout bottomlayout;
	Button bt_back;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setId("OrderConfirmationAct");
		setContentView(R.layout.order_confirmation);
		bt_back = (Button) findViewById(R.order_confirmation.back);
		bottomlayout = (RelativeLayout) findViewById(R.order_confirmation.bottom_layout);
		bottomlayout.setVisibility(View.GONE);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_NoSupportHDFKAct.this.finish();
			}
		});
		mContext = V3_NoSupportHDFKAct.this;
		MobclickAgent.setDebugMode(true);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);
		// MobclickAgent.setUpdateOnlyWifi(false);
		// MobclickAgent.setDefaultReportPolicy(this,
		// ReportPolicy.BATCH_BY_INTERVAL, 5*1000);
		// MobclickAgent.onError(this);
		MobclickAgent.updateOnlineConfig(this);
		Intent i = getIntent();
		// paytype = i.getStringExtra("paytype");
		// tv_allpay = (TextView) findViewById(R.order_confirmation.allpay);
		lv = (ListView) findViewById(R.order_confirmation.listview);
		// bt_pay = (Button) findViewById(R.order_confirmation.bt_pay);
		// bt_pay.setVisibility(View.GONE);

		orderList = new ArrayList<Msg_CitemList>(V3_WriteOrderAct.citemlist2); // 定义临时列表
		OCAdp = new OrderConfirmationAdapter(this, orderList);
		lv.setAdapter(OCAdp);
	}

}
