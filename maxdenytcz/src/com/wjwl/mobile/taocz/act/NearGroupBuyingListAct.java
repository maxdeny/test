package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.GroupBuyingListAct.Click;
import com.wjwl.mobile.taocz.adapter.GroupBuyAdapter;
import com.wjwl.mobile.taocz.adapter.GroupBuyAdapter1;
import com.wjwl.mobile.taocz.adapter.GroupBuyingAdapter;
import com.wjwl.mobile.taocz.adapter.TopListAdapter;
import com.wjwl.mobile.taocz.adapter.TopSecondAdapter;
import com.wjwl.mobile.taocz.data.ButtonView;
import com.wjwl.mobile.taocz.dialog.V3_Dialog2;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;

public class NearGroupBuyingListAct extends MActivity {// implements
													// OnCheckedChangeListener
	List<Msg_Citem> list;
	LinearLayout layout, linear, linear2;
	DragChangeView dragChangeView;
	GroupBuyingAdapter adapter;
	ArrayList<Map<String, Object>> mData = null;
	ListView  pp_listview1, pp_listview2, pp_listview3;
	PageListView listview;
	Button back;
	ArrayList<HashMap<String, String>> data2 = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> data4 = new ArrayList<HashMap<String, String>>();

	ArrayList<ButtonView> buttonListView1 = new ArrayList<ButtonView>();
	ArrayList<ButtonView> buttonListView3 = new ArrayList<ButtonView>();
	ArrayList<ButtonView> buttonListView2 = new ArrayList<ButtonView>();
	String fenlei = "", diqu = "", order = "";
	HashMap<String, String> map1, map2, map3, map4;
	PopupWindow pp;
	String[] stylemenu = { "川菜", "湘菜", "日本料理", "韩式烤肉", "西餐", "鄂菜" };
	View viewpp;
	Handler hd;
	Runnable rb;
	int mark = 0;
	String buttonstate = "tgcategory", pp_listview = "",keywords="",fenleiname="",
			orderfree = "0", usefree = "0";
	private TopListAdapter toplistAdapter = null;
	private TopSecondAdapter topsecondAdapter = null;
	private String businessid = "";
	private int mPage=1;
	private boolean loaddelay=true;
	private PullReloadView prv;
	private View norows;
	private String[] tuds;
	LinearLayout btn1, btn2, btn3;
	TextView text1,text2,text3;
	String second_juli[]={"全城","1000米","3000米","5000米"};
	int second_id[]={100000,1000,3000,5000};
	Button sousuo,fujin,bt_shaixuan;
	TextView title;
	
	int h=0,h2=0,h0=520;
	RelativeLayout rl1;
	LinearLayout li1;
	
	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.groupbuying);
		this.LoadShow=true;
		tuds = getIntent().getStringArrayExtra("tuds");
		fenlei=getIntent().getStringExtra("id");
		fenleiname=getIntent().getStringExtra("title");
		
		btn1 = (LinearLayout) findViewById(R.groupbuying.btn1);
		btn2 = (LinearLayout) findViewById(R.groupbuying.btn2);
		btn3 = (LinearLayout) findViewById(R.groupbuying.btn3);
		text1=(TextView) findViewById(R.groupbuying.text1);
		text2=(TextView) findViewById(R.groupbuying.text2);
		text3=(TextView) findViewById(R.groupbuying.text3);
		text3.setText("离我最近");
		back = (Button) findViewById(R.groupbuying.back);
		sousuo=(Button) findViewById(R.groupbuying.sousuo);
		sousuo.setVisibility(View.GONE);
		title=(TextView) findViewById(R.groupbuying.title);
		title.setText("附近");
		fujin=(Button) findViewById(R.groupbuying.fujin);
		fujin.setVisibility(View.GONE);
		
		bt_shaixuan = (Button) findViewById(R.groupbuying.bt_shaixuan);
		bt_shaixuan.setVisibility(View.INVISIBLE);
		bt_shaixuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_Dialog2 dia = new V3_Dialog2(NearGroupBuyingListAct.this,"nearby");
				dia.show();
			}
		});
		
		text1.setText(fenleiname);
		sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent in=new Intent(GroupBuyingListAct.this,Search_Act.class);
//				in.putExtra("style", "shenghuo");
//				startActivity(in);
				finish();
				Frame.HANDLES.get("FrameAg").get(0).sent(1, R.frame.myinfo);
			}
		});
		prv=(PullReloadView) findViewById(R.groupbuying.pullReloadView);
		Intent i = getIntent();
		if (i.getStringExtra("act") != null) {
			if (i.getStringExtra("act").equals("RestaurantDetailsAct")||i.getStringExtra("act").equals("LifeContentAct"))
				businessid = i.getStringExtra("businessid");

			if (i.getStringExtra("act").equals("BookingAppointmentAct")) {
				fenlei = i.getStringExtra("itemid");
				text1.setText(i.getStringExtra("itemname"));
				back.setText("预订预约");
			}
			if (i.getStringExtra("act").equals("IndexAct")) {
				fenlei = i.getStringExtra("itemid");
				text1.setText(i.getStringExtra("itemname"));
				back.setText("首页");
			}
			if (i.getStringExtra("act").equals("PeripheralAct")) {
				back.setText("周边");
			}
		}
		if(i.getStringExtra("keywords")!=null){
			keywords=getIntent().getStringExtra("keywords");
		}
		listview = (PageListView) findViewById(R.groupbuying.listview);
		viewpp = findViewById(R.groupbuying.view);

		LayoutInflater flater = LayoutInflater.from(getApplication());
		View view = flater.inflate(R.layout.pp_content, null);
		pp_listview1 = (ListView) view.findViewById(R.pp.listview1);
		pp_listview2 = (ListView) view.findViewById(R.pp.listview2);
		pp_listview3 = (ListView) view.findViewById(R.pp.listview3);
		linear = (LinearLayout) view.findViewById(R.pp.linear);
		linear2 = (LinearLayout) view.findViewById(R.pp.linear2);
		pp_listview1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				toplistAdapter.setSelectedPosition(arg2);
				toplistAdapter.notifyDataSetInvalidated();

				linear.setVisibility(View.VISIBLE);
				if (buttonstate.equals("tgcategory")) {
					if (buttonListView1.get(arg2).textViewId == 0) {
						pp.dismiss();
						text1.setText("全部");
						fenlei = "0";
//						dataLoad(null);
						listview.reload();
					} else {
						data2.clear();
						map2 = new HashMap<String, String>();
						map2.put("secondmenuname",F.TGCATEGORY.getComment(arg2 - 1).getCommentcontent());
						map2.put("secondmenuid",F.TGCATEGORY.getComment(arg2 - 1).getCommentid());
						map2.put("secondmenunumber",F.TGCATEGORY.getComment(arg2 - 1).getCommentpeople());
						data2.add(map2);
						for (int o = 0; o < F.TGCATEGORY.getComment(arg2 - 1).getCommentCount(); o++) {
							map2 = new HashMap<String, String>();
							map2.put("secondmenuname",F.TGCATEGORY.getComment(arg2 - 1).getComment(o).getCommentcontent());
							map2.put("secondmenuid",F.TGCATEGORY.getComment(arg2 - 1).getComment(o).getCommentid());
							map2.put("secondmenunumber",F.TGCATEGORY.getComment(arg2 - 1).getComment(o).getCommentpeople());
							data2.add(map2);
						}
						topsecondAdapter = new TopSecondAdapter(
								NearGroupBuyingListAct.this, data2, "1");
						pp_listview2.setAdapter(topsecondAdapter);
					}
				} else if (buttonstate.equals("areacategory")) {
//					if (buttonListView3.get(arg2).textViewId == 0) {
//						pp.dismiss();
//						text2.setText("全城");
//						diqu = "0";
//						businessid = "";
//						listview.reload();
//					} else {
//						data4.clear();
//						map4 = new HashMap<String, String>();
//						map4.put("secondmenuname", F.AREACATEGORY
//								.getComment(arg2 - 1)
//								.getCommentcontent());
//						map4.put("secondmenuid",
//								F.AREACATEGORY.getComment(arg2 - 1)
//										.getCommentid());
//						data4.add(map4);
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
//								NearGroupBuyingListAct.this, data4, "");
//						pp_listview2.setAdapter(topsecondAdapter);
//					}
					//距离分类
					pp.dismiss();
					text2.setText(second_juli[arg2]);
					diqu = second_id[arg2]+"";
					listview.reload();
				}
			}
		});
		pp_listview3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pp.dismiss();
				text3.setText(buttonListView2.get(arg2).cname);
				order = buttonListView2.get(arg2).textViewId + "";
//				dataLoad(null);
				listview.reload();
			}
		});
		pp_listview2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				pp.dismiss();
				businessid = "";
				if (buttonstate.equals("tgcategory")) {
					text1.setText(data2.get(arg2).get("secondmenuname"));
					fenlei = data2.get(arg2).get("secondmenuid");
//					dataLoad(null);
					listview.reload();
				} else if (buttonstate.equals("areacategory")) {
					text2.setText(data4.get(arg2).get("secondmenuname"));
					diqu = data4.get(arg2).get("secondmenuid");
//					dataLoad(null);
					listview.reload();
				}
			}
		});
		
		rl1=(RelativeLayout) findViewById(R.groupbuying.rl1);
		li1=(LinearLayout) findViewById(R.groupbuying.li1);
		{// 计算title高度，来显示pp位置，getHight不可行
			ViewTreeObserver vto2 = rl1.getViewTreeObserver();
			vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					rl1.getViewTreeObserver().removeGlobalOnLayoutListener(
							this);
					h = rl1.getHeight();//66
				}
			});
			ViewTreeObserver vto3 = li1.getViewTreeObserver();
			vto3.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					li1.getViewTreeObserver().removeGlobalOnLayoutListener(
							this);
					h2 = li1.getHeight();//54
				}
			});
		}
		h0=getWindowManager().getDefaultDisplay().getHeight()-156;
		
		
		pp = new PopupWindow(view, LayoutParams.WRAP_CONTENT, h0, true);
		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));

		hd = new Handler();
		rb = new Runnable() {
			@Override
			public void run() {
				if (pp.isShowing()) {

				} else {
					viewpp.setVisibility(View.GONE);
				}
				hd.postDelayed(rb, 10);
			}
		};
		hd.postDelayed(rb, 10);
		btn1.setOnClickListener(new Click());
		btn2.setOnClickListener(new Click());
		btn3.setOnClickListener(new Click());
		back.setOnClickListener(new Click());
		
		listview.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if(loaddelay){
					dataLoadByDelay(null);
					loaddelay=false;
				}else{
					dataLoad();
				}
			}
		});
		listview.setLoadView(new FootLoadingShow(this));
		listview.start(1);
		
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
		
	}

	public class Click implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.groupbuying.btn1:
				buttonstate = "tgcategory";
				buttonListView1.clear();
				int numbers=0;
				for (int i = 0; i < F.TGCATEGORY.getCommentList().size(); i++) {
					int number=Integer.parseInt(F.TGCATEGORY.getComment(i).getCommentpeople());
					numbers+=number;
				}
				ButtonView a2 = new ButtonView(0, "全部",numbers+"");
				buttonListView1.add(a2);
				for (int i = 0; i < F.TGCATEGORY.getCommentList().size(); i++) {
					ButtonView a = new ButtonView(Integer.parseInt(F.TGCATEGORY
							.getComment(i).getCommentid()), F.TGCATEGORY
							.getComment(i).getCommentcontent(),
							F.TGCATEGORY.getComment(i).getCommentpeople());
							
					buttonListView1.add(a);
				}
				toplistAdapter = new TopListAdapter(NearGroupBuyingListAct.this,
						buttonListView1, "1");
				pp_listview1.setAdapter(toplistAdapter);
				// toplistAdapter.setSelectedPosition(mark);
				// toplistAdapter.notifyDataSetInvalidated();
				// mark=arg2;

				pp.showAsDropDown(btn1, 0, 0);
				pp_listview1.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				linear2.setVisibility(View.GONE);
				viewpp.setVisibility(View.VISIBLE);
				break;
			case R.groupbuying.btn2:
				buttonstate = "areacategory";
				buttonListView3.clear();
//				ButtonView a1 = new ButtonView(0, "全城","");
//				buttonListView3.add(a1);
//				for (int i = 0; i < F.AREACATEGORY.getCommentList().size(); i++) {
//					ButtonView a = new ButtonView(
//							Integer.parseInt(F.AREACATEGORY.getComment(i)
//									.getCommentid()), F.AREACATEGORY
//									.getComment(i).getCommentcontent(),"");
//					buttonListView3.add(a);
//				}
				//距离分类
				for (int i = 0; i <second_juli.length; i++) {
					ButtonView a = new ButtonView(
							second_id[i],second_juli[i], "");
					buttonListView3.add(a);
				}
				toplistAdapter = new TopListAdapter(NearGroupBuyingListAct.this,
						buttonListView3, "");
				pp_listview1.setAdapter(toplistAdapter);

				pp.showAsDropDown(btn1, 0, 0);
				pp_listview1.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				linear2.setVisibility(View.GONE);
				viewpp.setVisibility(View.VISIBLE);
				break;
			case R.groupbuying.btn3:
				buttonstate = "order";
				buttonListView2.clear();
				ButtonView d = new ButtonView(5, "离我最近","");
				ButtonView a = new ButtonView(1, "销量高到低","");
				ButtonView b = new ButtonView(2, "价格低到高","");
				ButtonView c = new ButtonView(3, "好评高到低","");
				ButtonView e = new ButtonView(4, "最新发布","");
				buttonListView2.add(d);
				buttonListView2.add(a);
				buttonListView2.add(b);
				buttonListView2.add(c);
				buttonListView2.add(e);
				toplistAdapter = new TopListAdapter(NearGroupBuyingListAct.this,
						buttonListView2, "");
				pp_listview3.setAdapter(toplistAdapter);
				pp.showAsDropDown(btn1, 0, 0);
				linear2.setVisibility(View.VISIBLE);
				linear.setVisibility(View.GONE);
				pp_listview1.setVisibility(View.GONE);
				viewpp.setVisibility(View.VISIBLE);
				break;
			case R.groupbuying.back:
				if(Frame.HANDLES.get("Search_Act").size()!=0){
					Frame.HANDLES.get("Search_Act").get(0).sent(1, new String[]{keywords});
				}
				NearGroupBuyingListAct.this.finish();
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		
		if (son.build == null) {
			if (mPage == 1) {
//				norows.setVisibility(View.VISIBLE);
				Toast.makeText(getApplication(), "抱歉，无相关商品", 1).show();
				listview.setAdapter(null);
			}
			listview.endPage();
		}
		if (son.build != null && son.mgetmethod.equals("nearbytglist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list = new ArrayList<Msg_Citem>();
			list = builder.getCitemList();
			listview.addData(new GroupBuyAdapter1(NearGroupBuyingListAct.this,
					list,"NearGroupBuyingListAct"));//设计图样式，后台提供数据不全
//			listview.addData(new GroupBuyAdapter(NearGroupBuyingListAct.this,
//					list));
			if (builder.getCitemList().size() < F.Per_Page) {
				listview.endPage();
			}
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
			// 销量高到低、价格低到高、好评高到低，参数1，2，3
			this.loadData(new Updateone[] { new Updateone("NEARBYTGLIST",
					new String[][] {
							{ "categoryareaid",diqu.equals("0") ? "" :  diqu},
							{ "orderfree", orderfree },
							{ "usefree", usefree },
							{ "latitude", tuds[0] == null ? "31.805989" : tuds[0] },
							{ "longitude",tuds[1] == null ? "119.980623" : tuds[1] },
							{ "categoryid", fenlei.equals("0") ? "" : fenlei },
							{ "perpage", F.Per_Page + "" }, { "page", this.mPage+"" },
							{ "order", order.equals("") ? "5" : order }

					}), });
	}
	public void disposeMsg(int type, Object obj) {

		if (type == 1) {
			Boolean[] temp = (Boolean[]) obj;
			if (temp[0])
				orderfree = "1";
			else
				orderfree = "0";
			if (temp[1])
				usefree = "1";
			else
				usefree = "0";
			listview.reload();
		}
	}
}

