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
//public class BusinessCircleSelectFirstAct extends MActivity {
//	TextView head_title;
//	ListView lv;
//	private PullReloadView prv;
//	private boolean callBack = false;
//	ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	// String[] category = { "休闲食品", "服装装饰", "酒水饮料","美妆护理","母婴玩具","家电数码" };
//	List<Msg_Ccategory> list_ccategory;
//
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.com_merchant_list);
//		setId("BusinessCircleSelectFirstAct");
//		head_title = (TextView) this.findViewById(R.merchant.head_title);
//		callBack = getIntent().getBooleanExtra("callback", false);
//			head_title.setText("商圈");
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
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
//		// if (son != null && son.mgetmethod.equals("SCATEGORY")) {
//		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//		list_ccategory = builder.getCcategoryList();
//
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
//				Intent i = new Intent();
//				i.putExtra("title", list_ccategory.get(arg2)
//						.getCategoryname());
//				i.putExtra("isSelect",
//						getIntent().getBooleanExtra("isSelect", false));
//				i.putExtra("categoryparentid", list_ccategory.get(arg2)
//						.getCategoryid());
//				i.putExtra("searchPupub",
//						getIntent().getIntExtra("searchPupub", -1));
//				i.setClass(getApplicationContext(), BusinessCircleSelectSecondAct.class);
//				startActivity(i);
//				BusinessCircleSelectFirstAct.this.finish();
//			}
//		});
//		prv.refreshComplete();
//	}
//
//	// }
//
//	@Override
//	public void dataLoad(int[] types) {
//
//		this.loadData(new Updateone[] { new Updateone("LCATEGORY",
//				new String[][] { { "categoryid", "0" } }), });
//
//	}
//}
