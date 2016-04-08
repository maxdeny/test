package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
////import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.TakeOutAct;
//
//public class TakeOutCategorySecondtAct extends MActivity {
//	TextView head_title, title;
//	String str_head_title, str_title;
//	String navigation;
//	PageListView lv;
//	ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	List<Msg_Ccategory> list_ccategory;
//	String categorysubid;
//	String categoryid;
//	//private PullReloadView prv;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutlist2);
//		setId("CategorySecondAct");
//		Intent intent = this.getIntent();
//		str_head_title = intent.getStringExtra("navigation");
//		str_title = intent.getStringExtra("title");
//		head_title = (TextView) findViewById(R.takeoutlist2.headtext);
//		title = (TextView) findViewById(R.takeoutlist2.text);
//		head_title.setText(str_head_title);
//		title.setText(str_title);
//		lv = (PageListView) findViewById(R.takeoutlist2.listview);
//		//prv = (PullReloadView) findViewById(R.takeoutlist2.pullReloadView);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//		list_ccategory = builder.getCcategoryList();
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < list_ccategory.size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("categorySecond", list_ccategory.get(i).getCategoryname());
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(this, mData, R.layout.item_takeoutlist,
//				new String[] { "categorySecond" },
//				new int[] { R.item_takeoutlist.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				if (getIntent().getBooleanExtra("isSelect", false)) {
//					Frame.HANDLES
//							.get("LifeListAct")
//							.get(0)
//							.sent(1,
//									list_ccategory.get(arg2)
//											.getCategoryname());
//					Frame.HANDLES.closeOne("CategoryFirstAct");
//					Frame.HANDLES.closeOne("CategorySecondAct");
//				} else {
//					Intent intent = new Intent(TakeOutCategorySecondtAct.this,
//							TakeOutAct.class);
//					intent.putExtra("searchPupub",
//							getIntent().getIntExtra("searchPupub", -1));
//					intent.putExtra("category", list_ccategory.get(arg2)
//							.getCategoryname());
//					intent.putExtra("categoryid", list_ccategory.get(arg2)
//							.getCategoryid());
//					startActivity(intent);
//				}
//			}
//			
//		});
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//
//		this.loadData(new Updateone[] { new Updateone("WCATEGORY",
//				new String[][] { { "categoryid", categoryid } }), });
//
//	}
//
//}
