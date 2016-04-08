package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.mdx.mobile.widget.PageListView.PageRun;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.PreferentialActiviesAdapter;
//import com.wjwl.mobile.taocz.adapter.TakeoutListAdapter1;
//import com.wjwl.mobile.taocz.adapter.TopListAdapter;
//import com.wjwl.mobile.taocz.adapter.TopSecondAdapter;
//import com.wjwl.mobile.taocz.data.ButtonView;
//import com.wjwl.mobile.taocz.widget.FootLoadingShow;
//
//public class TakeOutListAct1 extends MActivity {
//	private RadioGroup radiogroup;
//	private PageListView listview;
//	String type = "1";
//	private int mPage = 1;
//	private View norows;
//	private PullReloadView prv;
//	private boolean loaddelay = true;
//	private ArrayList<Map<String, Object>> mData = null;
//	PreferentialActiviesAdapter adp;
//	TextView headtitle;
//	List<Msg_Cbusinessinfo> list_businessinfo;
//	TextView tv_nodata;
//	PopupWindow pp;
//	View viewpp;
//	Handler hd;
//	Runnable rb;
//	ListView pp_listview1, pp_listview2, pp_listview3;
//	LinearLayout layout, linear, linear2;
//	String buttonstate = "tgcategory", pp_listview = "",
//	// categorypurposeid = "", categoryfoodid = "", categoryareaid = "",
//			categoryfoodname = "", categoryareaname = "";
//	private TopListAdapter toplistAdapter = null;
//	private TopSecondAdapter topsecondAdapter = null;
//	ArrayList<HashMap<String, String>> data2 = new ArrayList<HashMap<String, String>>();
//	ArrayList<HashMap<String, String>> data4 = new ArrayList<HashMap<String, String>>();
//	RadioButton btn1, btn2, btn3;
//	ArrayList<ButtonView> buttonListView1 = new ArrayList<ButtonView>();
//	ArrayList<ButtonView> buttonListView3 = new ArrayList<ButtonView>();
//	ArrayList<ButtonView> buttonListView2 = new ArrayList<ButtonView>();
//	String order = "", date = "";
//	HashMap<String, String> map1, map2, map3, map4;
//	String longitude = "", latitude = "", keywords = "", categoryareaid1 = "",
//			categoryfoodid1 = "";
//	Button bt_back;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutlist1);
//		setId("TakeoutListAct1");
//		radiogroup = (RadioGroup) findViewById(R.takeoutlist1.radiogroup);
//		listview = (PageListView) findViewById(R.takeoutlist1.listview);
//		// ///////////////////
//		norows = (View) findViewById(R.takeoutlist1.view);
//		viewpp = findViewById(R.takeoutlist1.view);
//		btn3 = (RadioButton) findViewById(R.takeoutlist1.rbt_1);
//		btn1 = (RadioButton) findViewById(R.takeoutlist1.rbt_2);
//		btn2 = (RadioButton) findViewById(R.takeoutlist1.rbt_3);
//		bt_back = (Button) findViewById(R.takeoutlist1.back);
//		bt_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if(Frame.HANDLES.get("Search_Act").size()!=0){
//					Frame.HANDLES.get("Search_Act").get(0).sent(1, new String[]{keywords});
//				}
//				TakeOutListAct1.this.finish();
//			}
//		});
//		btn1.setOnClickListener(new Click());
//		btn2.setOnClickListener(new Click());
//		btn3.setOnClickListener(new Click());
//		tv_nodata = (TextView) findViewById(R.takeoutlist1.no_data);
//		LayoutInflater flater = LayoutInflater.from(getApplication());
//		View view = flater.inflate(R.layout.pp_content, null);
//		pp_listview1 = (ListView) view.findViewById(R.pp.listview1);
//		pp_listview2 = (ListView) view.findViewById(R.pp.listview2);
//		pp_listview3 = (ListView) view.findViewById(R.pp.listview3);
//		linear = (LinearLayout) view.findViewById(R.pp.linear);
//		linear2 = (LinearLayout) view.findViewById(R.pp.linear2);
//		pp_listview1.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				toplistAdapter.setSelectedPosition(arg2);
//				toplistAdapter.notifyDataSetInvalidated();
//
//				linear.setVisibility(View.VISIBLE);
//				if (buttonstate.equals("tgcategory")) {
//					if (buttonListView1.get(arg2).textViewId == 0) {
//						pp.dismiss();
//						btn1.setText("全部");
//						categoryfoodid1 = "";
////						dataLoad(new int[] { 1 });
//						listview.reload();
//					} else {
//						data2.clear();
//						for (int o = 0; o < F.FOODCATEGORY.getComment(arg2 - 1)
//								.getCommentCount(); o++) {
//							map2 = new HashMap<String, String>();
//							map2.put("secondmenuname", F.FOODCATEGORY
//									.getComment(arg2 - 1).getComment(o)
//									.getCommentcontent());
//							map2.put("secondmenuid",
//									F.FOODCATEGORY.getComment(arg2 - 1)
//											.getComment(o).getCommentid());
//							data2.add(map2);
//						}
//						topsecondAdapter = new TopSecondAdapter(
//								TakeOutListAct1.this, data2, "1");
//						pp_listview2.setAdapter(topsecondAdapter);
//
//						pp_listview2.setVisibility(View.GONE);
//						pp.dismiss();
//						btn1.setText(F.FOODCATEGORY.getComment(arg2 - 1)
//								.getCommentcontent());
//						categoryfoodid1 = F.FOODCATEGORY.getComment(arg2 - 1)
//								.getCommentid();
////						dataLoad(null);
//						listview.reload();
//					}
//				} else if (buttonstate.equals("areacategory")) {
//					if (buttonListView3.get(arg2).textViewId == 0) {
//						pp.dismiss();
//						btn2.setText("全城");
//						categoryareaid1 = "";
////						dataLoad(null);
//						listview.reload();
//					} else {
//						data4.clear();
//						for (int o = 0; o < F.AREACATEGORY.getComment(arg2 - 1)
//								.getCommentCount(); o++) {
//							map4 = new HashMap<String, String>();
//							map4.put("secondmenuname", F.AREACATEGORY
//									.getComment(arg2 - 1).getComment(o)
//									.getCommentcontent());
//							map4.put("secondmenuid",
//									F.AREACATEGORY.getComment(arg2 - 1)
//											.getComment(o).getCommentid());
//							data4.add(map4);
//						}
//						topsecondAdapter = new TopSecondAdapter(
//								TakeOutListAct1.this, data4, "");
//						pp_listview2.setVisibility(View.VISIBLE);
//						pp_listview2.setAdapter(topsecondAdapter);
//					}
//				}
//			}
//		});
//		// pp_listview3.setOnItemClickListener(new OnItemClickListener() {
//		// @Override
//		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//		// long arg3) {
//		// pp.dismiss();
//		// btn3.setText(buttonListView2.get(arg2).cname);
//		// order = "" + (arg2 + 1);
//		// dataLoad(new int[] { 1 });
//		// }
//		// });
//		pp_listview2.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				pp.dismiss();
//				if (buttonstate.equals("tgcategory")) {
//					btn1.setText(data2.get(arg2).get("secondmenuname"));
//					categoryfoodid1 = data2.get(arg2).get("secondmenuid");
////					dataLoad(null);
//					listview.reload();
//				} else if (buttonstate.equals("areacategory")) {
//					btn2.setText(data4.get(arg2).get("secondmenuname"));
//					categoryareaid1 = data4.get(arg2).get("secondmenuid");
////					dataLoad(null);
//					listview.reload();
//				}
//			}
//		});
//		pp = new PopupWindow(view, LayoutParams.WRAP_CONTENT, 520, true);
//		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
//
//		hd = new Handler();
//		rb = new Runnable() {
//			@Override
//			public void run() {
//				if (pp.isShowing()) {
//
//				} else {
//					viewpp.setVisibility(View.GONE);
//				}
//				hd.postDelayed(rb, 10);
//			}
//		};
//		hd.postDelayed(rb, 10);
//
//		// ///////外卖外送搜索按钮，只传递一个keyword
//		if (getIntent().getStringExtra("search") != null
//				&& getIntent().getStringExtra("search").endsWith("search")
//				&& getIntent().getStringExtra("keywords") != null) {
//			keywords = getIntent().getStringExtra("keywords");
//		}
//		// ////////外卖外送附件按钮，直接使用F经纬
//		// if(getIntent().getStringExtra("fujin")!=null&&
//		// getIntent().getStringExtra("fujin").endsWith("fujin")){
//		// longitude=F.longitude;
//		// latitude=F.latitude;
//		// }
//		// ///////菜系，id
//		if (getIntent().getStringExtra("act") != null
//				&& getIntent().getStringExtra("act").endsWith(
//						"CookingStyleSelect1Act")) {
//			btn1.setText(getIntent().getStringExtra("categoryfoodname"));
//			categoryfoodid1 = getIntent().getStringExtra("categoryfoodid");
//		}
//		// ///////地区，id
//		if (getIntent().getStringExtra("act") != null
//				&& getIntent().getStringExtra("act").endsWith(
//						"AreaSelectSecondAct")) {
//			btn2.setText(getIntent().getStringExtra("categoryareaname"));
//			categoryareaid1 = getIntent().getStringExtra("categoryareaid");
//		}
//
//		prv = (PullReloadView) findViewById(R.takeoutlist1.pullReloadView);
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
//					dataLoad(null);
//				}
//			}
//		});
//		listview.setLoadView(new FootLoadingShow(this));
//		listview.start(1);
//	}
//
//	class Click implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.takeoutlist1.rbt_2:
//				buttonstate = "tgcategory";
//				buttonListView1.clear();
//				ButtonView a2 = new ButtonView(0, "全部", "");
//				buttonListView1.add(a2);
//				for (int i = 0; i < F.FOODCATEGORY.getCommentList().size(); i++) {
//					ButtonView a = new ButtonView(
//							Integer.parseInt(F.FOODCATEGORY.getComment(i)
//									.getCommentid()), F.FOODCATEGORY
//									.getComment(i).getCommentcontent(), "");
//					buttonListView1.add(a);
//				}
//				toplistAdapter = new TopListAdapter(TakeOutListAct1.this,
//						buttonListView1, "");
//				pp_listview1.setAdapter(toplistAdapter);
//
//				pp.showAsDropDown(btn3, 0, 0);
//				pp_listview1.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				linear2.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//				break;
//			case R.takeoutlist1.rbt_3:
//				buttonstate = "areacategory";
//				buttonListView3.clear();
//				ButtonView a1 = new ButtonView(0, "全城", "");
//				buttonListView3.add(a1);
//				for (int i = 0; i < F.AREACATEGORY.getCommentList().size(); i++) {
//					ButtonView a = new ButtonView(
//							Integer.parseInt(F.AREACATEGORY.getComment(i)
//									.getCommentid()), F.AREACATEGORY
//									.getComment(i).getCommentcontent(), "");
//					buttonListView3.add(a);
//				}
//				toplistAdapter = new TopListAdapter(TakeOutListAct1.this,
//						buttonListView3, "");
//				pp_listview1.setAdapter(toplistAdapter);
//
//				pp.showAsDropDown(btn3, 0, 0);
//				pp_listview1.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				linear2.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//				break;
//			case R.takeoutlist1.rbt_1:
//				// buttonstate = "order";
//				// buttonListView2.clear();
//				// ButtonView a = new ButtonView(1, "点评数高到低");
//				// ButtonView b = new ButtonView(2, "点评数低到高");
//				// ButtonView c = new ButtonView(3, "好评率高到低");
//				// ButtonView d = new ButtonView(4, "好评率低到高");
//				// ButtonView e = new ButtonView(5, "人均由高到低");
//				// ButtonView f = new ButtonView(6, "人均由低到高");
//				// buttonListView2.add(a);
//				// buttonListView2.add(b);
//				// buttonListView2.add(c);
//				// buttonListView2.add(d);
//				// buttonListView2.add(e);
//				// buttonListView2.add(f);
//				// toplistAdapter = new TopListAdapter(
//				// TakeOutListAct1.this, buttonListView2,"");
//				// pp_listview3.setAdapter(toplistAdapter);
//				// pp.showAsDropDown(btn3, 0, 0);
//				// linear2.setVisibility(View.VISIBLE);
//				// linear.setVisibility(View.GONE);
//				// pp_listview1.setVisibility(View.GONE);
//				// viewpp.setVisibility(View.VISIBLE);
//				keywords = "";
//				categoryareaid1 = "";
//				categoryfoodid1 = "";
////				dataLoad(null);
//				listview.reload();
//				break;
//			}
//			// listview.reload();
//		}
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
////			prv.setVisibility(View.GONE);
////			tv_nodata.setVisibility(View.VISIBLE);
//		}
//		if (son.build != null && son.mgetmethod.equals("wmwslist")) {
//			Msg_CbusinessinfoList.Builder businesslist = (Msg_CbusinessinfoList.Builder) son.build;
//			list_businessinfo = businesslist.getCbusinessinfoList();
////			mData = new ArrayList<Map<String, Object>>();
////			for (int i = 0; i < list_businessinfo.size(); i++) {
////				Map<String, Object> map = new HashMap<String, Object>();
////				map.put("img", list_businessinfo.get(i).getBusinessimage());
////				map.put("title", list_businessinfo.get(i).getBusinessname());
////				map.put("businessid", list_businessinfo.get(i).getBusinessid());
////				mData.add(map);
////			}
//			TakeoutListAdapter1 adapter = new TakeoutListAdapter1(
//					TakeOutListAct1.this, list_businessinfo);
//			listview.addData(adapter);
//			if (list_businessinfo.size() < F.Per_Page) {
//				listview.endPage();
//			}
//			norows.setVisibility(View.INVISIBLE);
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMWSLIST",
//				new String[][] { { "categoryfoodid", categoryfoodid1 },
//						{ "categoryareaid", categoryareaid1 },
//						{ "keywords", keywords }, { "page_per", F.Per_Page+"" },
//						{ "page", mPage+""}, { "longitude", F.longitude },
//						{ "latitude", F.latitude } }), });
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
