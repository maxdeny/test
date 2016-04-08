package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.widget.PageListView;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.TczV3_AllOrderListAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_AllOrderListAct extends MActivity {
	TczV3_AllOrderListAdapter adp;
	TczV3_HeadLayout headlayout;
	PageListView lv;
	public ArrayList<Msg_CitemList> orderList; // 订单列表

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_allorderlist);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("全部订单");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_AllOrderListAct.this.finish();
			}
		});
		lv = (PageListView) findViewById(R.tczv3.list);
		adp = new TczV3_AllOrderListAdapter(TczV3_AllOrderListAct.this,
				orderList);
		lv.setAdapter(adp);
	}

}
