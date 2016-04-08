package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;

import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.CouponAdapter;

public class MyCouponUselistAct extends MActivity {
	TextView norows;
	PullReloadView prv;
	PageListView lv;
	CouponAdapter adp;
	private ArrayList<Map<String, Object>> mData;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.couponlist);
		lv = (PageListView) findViewById(R.id.listview);
		prv = (PullReloadView) findViewById(R.id.pullReloadView);
		norows = (TextView) findViewById(R.id.norows);
		mData = new ArrayList<Map<String, Object>>();
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				if (mData.size() > 0) {
					mData.clear();
				}
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("v3_couponlist")) {
			norows.setVisibility(View.VISIBLE);
			lv.setAdapter(null);
		} else if (son.build != null && son.mgetmethod.equals("v3_couponlist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list = builder.getCitemList();
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("Coupon_Type", list.get(i).getItemid());
				map.put("Coupon_Money", list.get(i).getItemprice());
				map.put("Coupon_Info", list.get(i).getItemtitle());
				map.put("Date", list.get(i).getItemremtime());
				map.put("CanUse", list.get(i).getItemtype());// 1为过期
				map.put("background", "1");
				mData.add(map);
			}
			adp = new CouponAdapter(MyCouponUselistAct.this, mData);
			lv.setAdapter(adp);
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone("V3_COUPONLIST",
					new String[][] { { "type", "2" }, { "uid", F.USER_ID }}), });
		}
	}
}
