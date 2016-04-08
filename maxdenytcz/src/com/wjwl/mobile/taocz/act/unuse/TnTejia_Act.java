package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PageListView.PageRun;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.TnTejia_Adapter;
//import com.wjwl.mobile.taocz.adapter.TnTejia_Adapter1;
//import com.wjwl.mobile.taocz.widget.FootLoadingShow;
//
//public class TnTejia_Act extends MActivity {
//	RadioButton btn1, btn2, btn3;
//	Button btn4;
//	PageListView listview;
//	TextView text;
//	Button back, saixuan;
//	private int a, b, c = a = b = 0;
//	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
//	HashMap<String, Object> map;
//	String categoryareaid = "", starval = "", distance = "", keywords = "",
//			perpage = "", page = "", longitude = "", latitude = "",
//			ordertype = "", order = "";
//	private int mPage=1;
//	private boolean loaddelay=true;
//	private PullReloadView prv;
//	private View norows;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.tntejia);
//		back = (Button) findViewById(R.tntejia.back);
//		saixuan = (Button) findViewById(R.tntejia.saixuan);
//		btn1 = (RadioButton) findViewById(R.tntejia.btn1);
//		btn2 = (RadioButton) findViewById(R.tntejia.btn2);
//		btn3 = (RadioButton) findViewById(R.tntejia.btn3);
//		btn4 = (Button) findViewById(R.tntejia.btn4);
//		text = (TextView) findViewById(R.tntejia.text);
//		listview = (PageListView) findViewById(R.tntejia.listview);
//		norows=findViewById(R.tntejia.norows);
//
//		btn1.setOnClickListener(new OnClickListener1());
//		btn2.setOnClickListener(new OnClickListener1());
//		btn3.setOnClickListener(new OnClickListener1());
//		btn4.setOnClickListener(new OnClickListener1());
//		back.setOnClickListener(new OnClickListener1());
//		saixuan.setOnClickListener(new OnClickListener1());
//		if (getIntent().getStringExtra("keywords") != null) {
//			keywords = getIntent().getStringExtra("keywords");
//		}
//		
//		listview.setLoadData(new PageRun() {
//			public void run(int page) {
//				mPage = page;
//				if(loaddelay){
//					dataLoadByDelay(null);
//					loaddelay=false;
//				}else{
//					dataLoad(null);
//				}
//			}
//		});
//		listview.setLoadView(new FootLoadingShow(this));
//		listview.start(1);
//		
//		prv=(PullReloadView) findViewById(R.tntejia.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				listview.reload();
//			}
//		});
////		dataLoad(null);
//	}
//
//	public class OnClickListener1 implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if (v.equals(back)) {
//				finish();
//			} else if (v.equals(saixuan)) {
//				Intent i = new Intent();
//				i.putExtra("act", "canuse");
//				i.setClass(TnTejia_Act.this, SaiXuan_Act.class);
//				startActivityForResult(i, 0);
//			} else if (v.equals(btn4)) {
//				Intent in = new Intent(TnTejia_Act.this, Search_Act.class);
//				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(in);
//			} else if (v.equals(btn1)) {
//				ordertype = "asc";
//				order="1";//距离
////				dataLoad(null);
//				listview.reload();
//			} else if (v.equals(btn2)) {
//				ordertype = "desc";
//				order="3";//星级
////				dataLoad(null);
//				listview.reload();
//			} else if (v.equals(btn3)) {
//				ordertype = "asc";
//				order="2";//价格
////				dataLoad(null);
//				listview.reload();
//			}
//
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) {
//		case RESULT_OK:
//			categoryareaid = data.getStringExtra("areaid");
//			distance = data.getStringExtra("distance");
//			starval = data.getStringExtra("stars");
//			listview.reload();
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if(son.build==null){
//			if(mPage==1){
//				norows.setVisibility(View.VISIBLE);
//				listview.setAdapter(null);
//			}
//			listview.endPage();
//		}
//		if (son.build != null && son.mgetmethod.equals("jyjdlist")) {
//			Msg_CbusinessinfoList.Builder builder = (Msg_CbusinessinfoList.Builder) son.build;
//			List<Msg_Cbusinessinfo> datas = builder.getCbusinessinfoList();
//			listview.addData(new TnTejia_Adapter1(TnTejia_Act.this, datas));
//			if(datas.size()<1000){
//				listview.endPage();
//			}
//			norows.setVisibility(View.INVISIBLE);
//		}
//		prv.refreshComplete();
//	}
//	
//	@Override
//	public void closeLoad() {
//		super.closeLoad();
//		this.LoadShow = false;
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("JYJDLIST",
//				new String[][] { { "categoryareaid", categoryareaid },{ "starval", starval } ,{ "distance", distance }
//				,{ "keywords", keywords },{ "perpage", "1000" },{ "page", "1"}
//				,{ "longitude", F.longitude },{ "latitude", F.latitude }
//				//,{ "ordertype", ordertype }
//				,{ "order", order }
//				
//		}), });
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		super.onKeyDown(keyCode, event);
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if(Frame.HANDLES.get("Search_Act").size()!=0){
//				Frame.HANDLES.get("Search_Act").get(0).sent(1, new String[]{keywords});
//			}
//			this.finish();
//			return true;
//		}
//		return false;
//	}
//}
