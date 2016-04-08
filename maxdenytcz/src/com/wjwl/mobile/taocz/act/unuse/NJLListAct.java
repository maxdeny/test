package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.mdx.mobile.widget.PageListView.PageRun;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.NJLListAdapter;
//import com.wjwl.mobile.taocz.widget.FootLoadingShow;
//
//public class NJLListAct extends MActivity {
//	private PageListView listview;
//	String type = "1";
//	private int mPage = 1;
//	private View norows;
//	private PullReloadView prv;
//	private boolean loaddelay = true;
//	NJLListAdapter adapter;
//	String buttonstate = "tgcategory", pp_listview = "",
//			categorypurposeid = "", categoryfoodid = "", categoryareaid = "",
//			categoryfoodname = "", categoryareaname = "";
//	String longitude = F.longitude, latitude = F.latitude;
//	Button bt_back;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.njllist);
//		listview = (PageListView) findViewById(R.njllist.listview);
//		prv = (PullReloadView) findViewById(R.njllist.pullReloadView);
//		bt_back = (Button) findViewById(R.njllist.back);
//		bt_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				NJLListAct.this.finish();
//			}
//		});
//		norows = (View) findViewById(R.id.norows);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				listview.reload();
//			}
//		});
//		listview.setLoadData(new PageRun() {
//			public void run(int page) {
//				mPage = page;
//				if (loaddelay) {
//					dataLoadByDelay(null);
//					loaddelay = false;
//				} else {
//					dataLoad();
//				}
//			}
//		});
//		listview.setLoadView(new FootLoadingShow(this));
//		listview.start(1);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build == null) {
//			if (mPage == 1) {
//				norows.setVisibility(View.VISIBLE);
//				listview.setAdapter(null);
//			}
//			listview.endPage();
//		} else if (son.build != null && son.mgetmethod.equals("njllist")) {
//			Msg_CbusinessinfoList.Builder businesslist = (Msg_CbusinessinfoList.Builder) son.build;
//			adapter = new NJLListAdapter(NJLListAct.this,
//					businesslist.getCbusinessinfoList());
//			listview.addData(adapter);
//			if (businesslist.getCbusinessinfoCount() < F.Per_Page) {
//				listview.endPage();
//			}
//			norows.setVisibility(View.INVISIBLE);
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("NJLLIST",
//				new String[][] { { "page_per", F.Per_Page+"" }, { "page", this.mPage+"" },
//						{ "longitude", longitude }, { "latitude", latitude } }), });
//	}
//
//}
