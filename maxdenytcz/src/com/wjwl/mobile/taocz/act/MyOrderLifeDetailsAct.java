package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.tcz.apkfactory.data.Msg_Morder_Package.Msg_MorderPackage;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyOrderLifeDetailsAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class MyOrderLifeDetailsAct extends MActivity {
	PageListView lv;
	MyOrderLifeDetailsAdapter ODAdp;
	List<Msg_Morder_Item> list_citem;
	public ArrayList<Msg_MorderPackage> orderpackageList; // 订单列表
	String type;
	private View norows;
	private int mPage = 1;
	private boolean loaddelay = true;
	TczV3_HeadLayout headlayout;

	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.myorderdetails);
		setId("MyOrderLifeDetailsAct");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setVisibility(View.GONE);
		lv = (PageListView) findViewById(R.myorderdetails.listview);
		headlayout.setTitle("我的生活订单");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyOrderLifeDetailsAct.this.finish();
			}
		});
		norows = findViewById(R.myorderdetails.norows);
		Intent i = getIntent();
		type = i.getStringExtra("type");
		lv.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if (loaddelay) {
					dataLoadByDelay(null);
					loaddelay = false;
				} else {
					dataLoad();
				}
			}
		});
		lv.setLoadView(new FootLoadingShow(this));
		lv.start(1);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null) {
			if (mPage == 1) {
				lv.setAdapter(null);
				norows.setVisibility(View.VISIBLE);
			}
			lv.endPage();
		}

		if (son.build != null && son.mgetmethod.equals("myorderlist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			list_citem = builder.getItemList();
			ODAdp = new MyOrderLifeDetailsAdapter(this, list_citem);
			lv.addData(ODAdp);
			if (ODAdp.getCount() < 5) {
				lv.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		if (types == null)
			this.loadData(new Updateone[] { new Updateone(
					"MYORDERLIST",
					new String[][] { { "userid", F.USER_ID }, { "type", type },
							{ "page_per", 5 + "" }, { "page", this.mPage + "" } }), });
	}
}
