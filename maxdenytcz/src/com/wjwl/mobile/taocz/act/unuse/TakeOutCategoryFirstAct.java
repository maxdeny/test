package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class TakeOutCategoryFirstAct extends MActivity {
//	ListView lv;
//	Button bt_takeout;
//	TextView head_title;
//	String str_head_title;
//	PullReloadView prv;
//	ArrayList<Map<String, Object>> mData = null;
//	SimpleAdapter sa;
//	List<Msg_Ccategory> list_ccategory;
//
//	// private boolean callBack = false;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutcategoryfirst);
//		bt_takeout = (Button) findViewById(R.takeoutcategoryfirst.bt_takeout);
//		head_title = (TextView) findViewById(R.takeoutcategoryfirst.headtext);
//		prv = (PullReloadView) findViewById(R.takeoutcategoryfirst.pullReloadView);
//		lv = (ListView) findViewById(R.takeoutcategoryfirst.listview);
//		Intent intent = getIntent();
//		str_head_title = intent.getStringExtra("title");
//		head_title.setText(str_head_title);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		bt_takeout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent i1 = new Intent();
//				i1.putExtra("title", str_head_title);
//				i1.putExtra("searchPupub", 4);
//				i1.setClass(TakeOutCategoryFirstAct.this, TakeoutListAct.class);
//				startActivity(i1);
//			}
//		});
//		dataLoadByDelay(null);
//	}
//
//	public void disposeMessage(Son son) throws Exception {
//		// if (son != null && son.mgetmethod.equals("SCATEGORY")) {
//		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//		list_ccategory = builder.getCcategoryList();
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < list_ccategory.size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("categoryFirst", list_ccategory.get(i).getCategoryname());
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
//				new String[] { "categoryFirst" },
//				new int[] { R.item_merchant.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				Intent i = new Intent();
//				i.putExtra("title", list_ccategory.get(arg2).getCategoryname());
//				i.putExtra("navigation", str_head_title);
//				i.putExtra("categoryparentid", list_ccategory.get(arg2)
//						.getCategoryid());
//				i.putExtra("searchPupub",
//						getIntent().getIntExtra("searchPupub", -1));
//				i.setClass(getApplicationContext(),
//						TakeOutCategorySecondtAct.class);
//				startActivity(i);
//
//			}
//		});
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//
//		this.loadData(new Updateone[] { new Updateone("WCATEGORY",
//				new String[][] { { "categoryid", "0" } }), });
//
//	}
//}
