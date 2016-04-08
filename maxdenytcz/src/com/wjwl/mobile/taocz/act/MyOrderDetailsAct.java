package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.MsgMorder.Msg_Morder;
import com.tcz.apkfactory.data.MsgMorder.Msg_Morder.Builder;
import com.tcz.apkfactory.data.Msg_Morder_Package.Msg_MorderPackage;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyOrderDetailsAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class MyOrderDetailsAct extends MActivity {
	PageListView lv;
	MyOrderDetailsAdapter ODAdp;
	private Msg_Morder.Builder OrderMain; // 订单
	public ArrayList<Msg_MorderPackage> orderpackageList; // 订单列表
	String type, orderNo;
	private int mPage = 1;
	private View norows;
	private boolean loaddelay = true;
	TczV3_HeadLayout headlayout;
	private boolean isFirst = true;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.myorderdetails);
		setId("MyOrderDetailsAct");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		// headlayout.setVisibility(View.GONE);
		headlayout.setTitle("我的订单");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyOrderDetailsAct.this.finish();
			}
		});
		lv = (PageListView) findViewById(R.myorderdetails.listview);
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
	protected void resume() {
		if (isFirst)
			isFirst = !isFirst;
		else
			lv.reload();
		MobclickAgent.onPageStart("OrderListPage");
		MobclickAgent.onResume(MyOrderDetailsAct.this);
	}


	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("OrderListPage");
		MobclickAgent.onPause(MyOrderDetailsAct.this);
	}
	
	
	@Override
	public void disposeMsg(int type, Object obj) {
		String[] resobj = (String[]) obj;
		orderNo = resobj[0];
		dataLoad(new int[] { 1 });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("myorderlist")) {
			if (mPage == 1) {
				norows.setVisibility(View.VISIBLE);
				lv.setAdapter(null);
			}
			lv.endPage();
		}
		if (son.build != null && son.mgetmethod.equals("myorderlist")) {
			OrderMain = (Builder) son.build;
			ArrayList<Msg_MorderPackage> orderList = new ArrayList<Msg_MorderPackage>(
					OrderMain.getPackageList());
			ODAdp = new MyOrderDetailsAdapter(this, orderList);
			lv.addData(ODAdp);
			if (orderList.size() < 5) {
				lv.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		}
		if (son.build != null && son.mgetmethod.equals("CANCELORDER")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				lv.reload();
			} else {
				Toast.makeText(MyOrderDetailsAct.this, retn.getErrorMsg(),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone(
					"MYORDERLIST2",
					new String[][] { { "userid", F.USER_ID }, { "type", type },
							{ "page_per", 5 + "" }, { "page", this.mPage + "" } }), });
		} else {
			this.loadData(new Updateone[] { new Updateone("CANCELORDER",
					new String[][] { { "uid", F.USER_ID }, { "oid", orderNo } }), });
		}
	}

}
