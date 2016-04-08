package com.wjwl.mobile.taocz.act.unuse;
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
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class BusinessCircleSelectSecondAct extends MActivity {
//
//	TextView head_title;
//	private String str_head_title;
//	private String navigation;
//	private ListView lv;
//	private ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	private List<Msg_Ccategory> list_ccategory;
//	// private String categorysubid;
//	private String categoryid;
//	private PullReloadView prv;
//	private String name_category;
//
//	// String[] category = { "茅台老窖","真果粒橙","雪碧", "可乐", "百事可乐", "青岛啤酒", "哈尔滨啤酒"
//	// };
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.com_merchant_list);
//		setId("CategorySecondAct");
//		head_title = (TextView) this.findViewById(R.merchant.head_title);
//		Intent intent = this.getIntent();
//		navigation = intent.getStringExtra("navigation");
//		str_head_title = intent.getStringExtra("title");
//		head_title.setText(str_head_title);
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
//		mData = new ArrayList<Map<String, Object>>();
//		categoryid = intent.getStringExtra("categoryparentid");
//		prv = (PullReloadView) findViewById(R.merchant.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		dataLoadByDelay(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null) {
//			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//			list_ccategory = builder.getCcategoryList();
//			mData = new ArrayList<Map<String, Object>>();
//			for (int i = 0; i < list_ccategory.size(); i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("categorySecond", list_ccategory.get(i)
//						.getCategoryname());
//				mData.add(map);
//			}
//			sa = new SimpleAdapter(this, mData,
//					R.layout.item_com_merchant_list,
//					new String[] { "categorySecond" },
//					new int[] { R.item_merchant.text });
//			lv.setAdapter(sa);
//			lv.setOnItemClickListener(new OnItemClickListener() {
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					SaiXuan_Act.text1.setText(list_ccategory.get(arg2)
//							.getCategoryname());
//					BusinessCircleSelectSecondAct.this.finish();
//
//				}
//			});
//			prv.refreshComplete();
//		}
//
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//
//		this.loadData(new Updateone[] { new Updateone("LCATEGORY",
//				new String[][] { { "categoryid", categoryid } }), });
//
//	}
//
//}
