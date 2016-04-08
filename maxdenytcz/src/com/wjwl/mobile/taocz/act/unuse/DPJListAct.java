package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ADJAdapter;
//
//public class DPJListAct extends MActivity {
//	Button bt_back;
//	TextView norows;
//	PageListView lv;
//	PullReloadView prv;
//	private ArrayList<Map<String, Object>> mData;
//	ADJAdapter adp;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.dpj);
//		bt_back=(Button)findViewById(R.dpj.back);
//		norows = (TextView) findViewById(R.id.norows);
//		prv = (PullReloadView) findViewById(R.dpj.pullReloadView);
//		lv = (PageListView) findViewById(R.dpj.listview);
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < 10; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("businessname", "话费充值专营店");
//			map.put("productname", "自动充值江苏移动话费快充100元");
//			map.put("count", "1");
//			map.put("price", "100");
//			map.put("itemid", "" + i);
//			mData.add(map);
//		}
//		adp=new ADJAdapter(DPJListAct.this,mData);
//		lv.addData(adp);
//		bt_back.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				DPJListAct.this.finish();
//			}
//		});
//	}
//
//}
