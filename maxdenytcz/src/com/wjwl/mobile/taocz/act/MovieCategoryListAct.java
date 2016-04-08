//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class MovieCategoryListAct extends MActivity {
//	PageListView lv;
//	Button bt_takeout, bt_search;
//	EditText ed_search;
//	TextView head_title,title;
//	String str_head_title;
//	PullReloadView prv;
//	ArrayList<Map<String, Object>> mData = null;
//	SimpleAdapter sa;
//	List<Msg_Ccategory> list_ccategory;
//   
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutlist);
//		bt_takeout = (Button) findViewById(R.takeoutlist.bt_takeout);
//		bt_search = (Button) findViewById(R.takeoutlist.bt_search);
//		ed_search = (EditText) findViewById(R.takeoutlist.ed_search);
//		head_title = (TextView) findViewById(R.takeoutlist.headtext);
//		title = (TextView) findViewById(R.takeoutlist.title);
//		prv = (PullReloadView) findViewById(R.takeoutlist.pullReloadView);
//		lv = (PageListView) findViewById(R.takeoutlist.listview2);
//		bt_search.setOnClickListener(new onClic());
//		bt_takeout.setOnClickListener(new onClic());
//		bt_takeout.setVisibility(View.GONE);
//		Intent intent = getIntent();
//		str_head_title = intent.getStringExtra("title");
//		head_title.setText(str_head_title);
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < 1; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("title", "票务");
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(this, mData, R.layout.item_takeoutlist,
//				new String[] { "title" }, new int[] { R.item_takeoutlist.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new listener());
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
////		dataLoad(null);
//	}
//
//	public class listener implements OnItemClickListener {
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			// TODO Auto-generated method stub
//			Intent intent = new Intent();
//			intent.putExtra("categoryid", list_ccategory.get(arg2)
//					.getCategoryid());
//			intent.putExtra("title", list_ccategory.get(arg2).getCategoryname());
//			intent.setClass(getApplication(), MovieShowListAct.class);
//			startActivity(intent);
//		}
//	}
//
//	public class onClic implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.takeoutlist.bt_search:
//				break;
//			case R.takeoutlist.bt_takeout:
//				Intent i = new Intent();
//				i.putExtra("title", str_head_title);
//				i.setClass(getApplication(), TakeOutCategoryFirstAct.class);
//				startActivity(i);
//				break;
//			}
//
//		}
//
//	}
//
//	public void disposeMessage(Son son) throws Exception {
//		 if (son.build != null && son.mgetmethod.equals("dcategory")) {
//		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//		list_ccategory = builder.getCcategoryList();
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < list_ccategory.size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("categoryFirst", list_ccategory.get(i).getCategoryname());
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(this, mData, R.layout.item_takeoutlist,
//				new String[] { "categoryFirst" },
//				new int[] { R.item_takeoutlist.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new listener());
//		prv.refreshComplete();
//	}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("DCATEGORY",
//				new String[][] { { "categoryid", "0" } }), });
//	}
//}
