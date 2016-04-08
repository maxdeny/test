package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.format.Time;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.mdx.mobile.widget.PageListView.PageRun;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.RestaurantDetailsAdapter;
//import com.wjwl.mobile.taocz.adapter.TopListAdapter;
//import com.wjwl.mobile.taocz.adapter.TopSecondAdapter;
//import com.wjwl.mobile.taocz.data.ButtonView;
//import com.wjwl.mobile.taocz.widget.FootLoadingShow;
//
//public class RestaurantDetailsListAct extends MActivity {
//
//	private PageListView listview;
//	String ordertype = "1";
//	private int mPage = 1;
//	private View norows;
//	private PullReloadView prv;
//	private boolean loaddelay = true;
//	private ArrayList<Map<String, Object>> mData = null;
//	RestaurantDetailsAdapter adp;
//	TextView headtitle;
//	PopupWindow pp;
//	View viewpp;
//	Handler hd;
//	Runnable rb;
//	ListView pp_listview1, pp_listview2, pp_listview3;
//	LinearLayout layout, linear, linear2;
//	String buttonstate = "tgcategory", pp_listview = "",
//			categorypurposeid = "", categoryfoodid = "", categoryareaid = "",
//			categoryfoodname = "", categoryareaname = "",keywords="";
//	private TopListAdapter toplistAdapter = null;
//	private TopSecondAdapter topsecondAdapter = null;
//	ArrayList<HashMap<String, String>> data2 = new ArrayList<HashMap<String, String>>();
//	ArrayList<HashMap<String, String>> data4 = new ArrayList<HashMap<String, String>>();
//	Button btn1, btn2, btn3,bt_back;
//	ArrayList<ButtonView> buttonListView1 = new ArrayList<ButtonView>();
//	ArrayList<ButtonView> buttonListView3 = new ArrayList<ButtonView>();
//	ArrayList<ButtonView> buttonListView2 = new ArrayList<ButtonView>();
//	String fenlei = "", diqu = "", order = "", date = "";
//	HashMap<String, String> map1, map2, map3, map4;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.restaurantdetailslist);
//		setId("RestaurantDetailsListAct");
//		
//		bt_back = (Button) findViewById(R.restaurantdetailslist.back);
//		bt_back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(Frame.HANDLES.get("Search_Act").size()!=0){
//					Frame.HANDLES.get("Search_Act").get(0).sent(1, new String[]{keywords});
//				}
//				RestaurantDetailsListAct.this.finish();
//			}
//		});
//		
//		Time time = new Time();
//		time.setToNow();
//		String  act=getIntent().getStringExtra("act");
//		if (time.month + 1 < 10)
//			date = "" + time.year + "0" + (time.month + 1) + time.monthDay;
//		else
//			date = "" + time.year + (time.month + 1) + time.monthDay;
//		if (null!=act&&act.equals("BookingAppointmentAct")) {
//			date = getIntent().getStringExtra("date");
//			categoryareaid = getIntent().getStringExtra("categoryareaid");
//			categoryareaname = getIntent().getStringExtra("categoryareaname");
//		} else if (null!=act&&act.equals("CookingStyleSelect1Act")) {
//			categoryfoodid = getIntent().getStringExtra("categoryfoodid");
//			categoryfoodname = getIntent().getStringExtra("categoryfoodname");
//		} else if (null!=act&&act.equals("AreaSelectSecondAct")) {
//			categoryareaid = getIntent().getStringExtra("categoryareaid");
//			categoryareaname = getIntent().getStringExtra("categoryareaname");
//		} else if (null!=act&&act.equals("EatPurposeSecondAct"))
//			categorypurposeid = getIntent().getStringExtra("categorypurposeid");
//
//		listview = (PageListView) findViewById(R.restaurantdetailslist.listview);
//		headtitle = (TextView) findViewById(R.restaurantdetailslist.headtitle);
//		headtitle.setText("餐厅列表");
//		norows = findViewById(R.id.norows);
//		viewpp = findViewById(R.restaurantdetailslist.view);
//		btn1 = (Button) findViewById(R.restaurantdetailslist.rbt_people);
//		btn2 = (Button) findViewById(R.restaurantdetailslist.rbt_price);
//		btn3 = (Button) findViewById(R.restaurantdetailslist.rbt_sale);
//		btn1.setOnClickListener(new Click());
//		btn2.setOnClickListener(new Click());
//		btn3.setOnClickListener(new Click());
//		if (categoryfoodname == null || categoryfoodname.equals(""))
//			btn1.setText("菜系");
//		else
//			btn1.setText(categoryfoodname);
//		if (categoryareaname == null || categoryareaname.equals(""))
//			btn2.setText("商圈");
//		else
//			btn2.setText(categoryareaname);
//		// ////////////////////////////////////////////////////////////
//		if(getIntent().getStringExtra("keywords")!=null){
//			keywords=getIntent().getStringExtra("keywords");
//		}
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
//						categoryfoodid = "";
////						dataLoad(null);
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
//								RestaurantDetailsListAct.this, data2, "1");
//						pp_listview2.setAdapter(topsecondAdapter);
//						pp_listview2.setVisibility(View.GONE);
//						pp.dismiss();
//						btn1.setText(F.FOODCATEGORY
//								.getComment(arg2-1).getCommentcontent());
//						categoryfoodid= F.FOODCATEGORY.getComment(arg2-1)
//								.getCommentid();
////						dataLoad(null);
//						listview.reload();
//					}
//				} else if (buttonstate.equals("areacategory")) {
//					if (buttonListView3.get(arg2).textViewId == 0) {
//						pp.dismiss();
//						btn2.setText("全城");
//						categoryareaid = "";
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
//								RestaurantDetailsListAct.this, data4, "");
//						pp_listview2.setVisibility(View.VISIBLE);
//						pp_listview2.setAdapter(topsecondAdapter);
//					}
//
//				}
//			}
//		});
//		pp_listview3.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				pp.dismiss();
//				btn3.setText(buttonListView2.get(arg2).cname);
//				order = "" + (arg2 + 1);
////				dataLoad(null);
//				listview.reload();
//			}
//		});
//		pp_listview2.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				pp.dismiss();
//				if (buttonstate.equals("tgcategory")) {
//					btn1.setText(data2.get(arg2).get("secondmenuname"));
//					categoryfoodid = data2.get(arg2).get("secondmenuid");
////					dataLoad(null);
//					listview.reload();
//				} else if (buttonstate.equals("areacategory")) {
//					btn2.setText(data4.get(arg2).get("secondmenuname"));
//					categoryareaid = data4.get(arg2).get("secondmenuid");
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
//		prv = (PullReloadView) findViewById(R.restaurantdetailslist.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				listview.reload();
//			}
//		});
//	}
//
//	class Click implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.restaurantdetailslist.rbt_people:
//				buttonstate = "tgcategory";
//				buttonListView1.clear();
//				ButtonView a2 = new ButtonView(0, "全部","");
//				buttonListView1.add(a2);
//				for (int i = 0; i < F.FOODCATEGORY.getCommentList().size(); i++) {
//					ButtonView a = new ButtonView(
//							Integer.parseInt(F.FOODCATEGORY.getComment(i)
//									.getCommentid()), F.FOODCATEGORY
//									.getComment(i).getCommentcontent(),
//									F.FOODCATEGORY
//									.getComment(i).getCommentpeople());
//					buttonListView1.add(a);
//				}
//				toplistAdapter = new TopListAdapter(
//						RestaurantDetailsListAct.this, buttonListView1, "");
//				pp_listview1.setAdapter(toplistAdapter);
//
//				pp.showAsDropDown(btn1, 0, 0);
//				pp_listview1.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				linear2.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//				break;
//			case R.restaurantdetailslist.rbt_price:
//				buttonstate = "areacategory";
//				buttonListView3.clear();
//				ButtonView a1 = new ButtonView(0, "全城","");
//				buttonListView3.add(a1);
//				for (int i = 0; i < F.AREACATEGORY.getCommentList().size(); i++) {
//					ButtonView a = new ButtonView(
//							Integer.parseInt(F.AREACATEGORY.getComment(i)
//									.getCommentid()), F.AREACATEGORY
//									.getComment(i).getCommentcontent(),"");
//					buttonListView3.add(a);
//				}
//				toplistAdapter = new TopListAdapter(
//						RestaurantDetailsListAct.this, buttonListView3, "");
//				pp_listview1.setAdapter(toplistAdapter);
//
//				pp.showAsDropDown(btn1, 0, 0);
//				pp_listview1.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				linear2.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//				break;
//			case R.restaurantdetailslist.rbt_sale:
//				buttonstate = "order";
//				buttonListView2.clear();
//				ButtonView a = new ButtonView(1, "点评数高到低","");
//				ButtonView b = new ButtonView(2, "点评数低到高","");
//				ButtonView c = new ButtonView(3, "好评率高到低","");
//				ButtonView d = new ButtonView(4, "好评率低到高","");
//				ButtonView e = new ButtonView(5, "人均由高到低","");
//				ButtonView f = new ButtonView(6, "人均由低到高","");
//				buttonListView2.add(a);
//				buttonListView2.add(b);
//				buttonListView2.add(c);
//				buttonListView2.add(d);
//				buttonListView2.add(e);
//				buttonListView2.add(f);
//				toplistAdapter = new TopListAdapter(
//						RestaurantDetailsListAct.this, buttonListView2, "");
//				pp_listview3.setAdapter(toplistAdapter);
//				pp.showAsDropDown(btn3, 0, 0);
//				linear2.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				pp_listview1.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//				break;
//			}
////			listview.reload();
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
//		} else if (son.build != null && son.mgetmethod.equals("yylist")) {
//			Msg_CbusinessinfoList.Builder builder = (Msg_CbusinessinfoList.Builder) son.build;
//			adp = new RestaurantDetailsAdapter(RestaurantDetailsListAct.this,
//					builder.getCbusinessinfoList());
//			listview.addData(adp);
//			if (builder.getCbusinessinfoList().size() < F.Per_Page) {
//				listview.endPage();
//			}
//			norows.setVisibility(View.INVISIBLE);
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("YYLIST", new String[][] {
//				{ "categorypurposeid", "" },
//				{ "categoryfoodid", categoryfoodid },
//				{ "perpage", F.Per_Page + "" }, { "page", this.mPage + "" },
//				{ "categoryareaid", categoryareaid }, { "keywords", keywords},
//				{ "yydate", "" }, { "order", order },
//				{ "longitude", F.longitude }, { "latitude", F.latitude } }), });
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
