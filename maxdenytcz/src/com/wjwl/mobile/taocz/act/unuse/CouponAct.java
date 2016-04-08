package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.PreferentialActiviesAct.OnCheckClick;
//import com.wjwl.mobile.taocz.adapter.PreferentialActiviesAdapter;
//
//public class CouponAct extends MActivity {
//
//	private RadioGroup radiogroup;
//	private PageListView listview;
//	String ordertype = "1";
//	private int mPage = 1;
//	private View norows;
//	private PullReloadView prv;
//	private boolean loaddelay = true;
//	private ArrayList<Map<String, Object>> mData = null;
//	PreferentialActiviesAdapter adp;
//	TextView headtitle;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.preferentialactivies);
//		setId("PreferentialActiviesAct");
//		listview = (PageListView) findViewById(R.preferentialactivies.listview);
//		radiogroup = (RadioGroup) findViewById(R.preferentialactivies.radiogroup);
//		headtitle = (TextView) findViewById(R.preferentialactivies.headtitle);
//		headtitle.setText("优惠券");
//		norows = findViewById(R.id.norows);
//		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < 10; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("newprice", "￥132.00");
//			map.put("oldprice", "￥35.00");
//			map.put("title", "上海城市大酒店原价1000现价值要499");
//			map.put("buynums", "23");
//			map.put("area", "奔牛镇");
//			map.put("itemid", "");
//			mData.add(map);
//		}
//		adp = new PreferentialActiviesAdapter(CouponAct.this, mData);
//		if (mPage == 1)
//			listview.setAdapter(adp);
//		else
//			listview.addData(adp);
//		// listview.setLoadData(new PageRun() {
//		// public void run(int page) {
//		// mPage = page;
//		// if (loaddelay) {
//		// dataLoadByDelay(null);
//		// loaddelay = false;
//		// } else {
//		// dataLoad();
//		// }
//		// }
//		// });
//		//
//		// listview.setLoadView(new FootLoadingShow(this));
//		// listview.start(1);
//		// prv = (PullReloadView)
//		// findViewById(R.preferentialactivies.pullReloadView);
//		// prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//		// public void onRefresh() {
//		// listview.reload();
//		// }
//		// });
//	}
//
//	class OnCheckClick implements OnCheckedChangeListener {
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			LoadShow = true;
//			switch (checkedId) {
//			case R.shoppinglist.rbt_people:
//				ordertype = "1";
//				break;
//			case R.shoppinglist.rbt_price:
//				ordertype = "2";
//				break;
//			case R.shoppinglist.rbt_sale:
//				ordertype = "3";
//				break;
//			}
//			listview.reload();
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		// if(son.build==null){
//		// if(mPage==1){
//		// norows.setVisibility(View.VISIBLE);
//		// listview.setAdapter(null);
//		// }
//		// listview.endPage();
//		// }
//		// if (son.build != null && son.mgetmethod.equals("slist")) {
//		// Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//		// List<Msg_Citem> list_citem= builder.getCitemList();
//		// SLAdapter = new
//		// ShoppingListAdapter(PreferentialActiviesAct.this,list_citem);
//		// listview.addData(SLAdapter);
//		// if(list_citem.size()<F.Per_Page){
//		// listview.endPage();
//		// }
//		// norows.setVisibility(View.INVISIBLE);
//		// }
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		// this.loadData(new Updateone[] { new Updateone("SLIST",
//		// new String[][] {
//		// {"keywords",keywords==null?"":keywords},
//		// {"category4selfid",categoryid==null?"":categoryid},
//		// {"page_per",F.Per_Page+""},
//		// {"page",this.mPage+""},
//		// {"orderby",(ordertype!=null&&ordertype.equals("2"))?"asc":"desc"},
//		// {"hprice",filt==null?"":filt.maxPrice},
//		// {"lprice",filt==null?"":filt.minPrice},
//		// {"hcount",filt==null?"0":(filt.haveAgio?"1":"0")},
//		// {"freight",filt==null?"0":(filt.payLate?"1":"0")},
//		// {"ordertype",ordertype==null?"1":ordertype}}), });
//	}
//}
