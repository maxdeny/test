package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.DatabaseHepler;
import com.wjwl.mobile.taocz.act.FiltrationAct.FiltrationParam;
import com.wjwl.mobile.taocz.adapter.TczV3_GoodsListAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.Item_Search;

public class TczV3_GoodsListAct extends MActivity {

	private PageListView listview;
	private RadioGroup radiogroup;
	private Button sousuo;
	TczV3_GoodsListAdapter SLAdapter;
	boolean isEdit = true;
	String categoryid = "", businessid = "", categoryname = "",
			businessname = "", storecateid = "", isshow = null;
	String ordertype = "1";
	String fenlei, keywords = "";
	String shangquan, category4areaid = "530";
	private Item_Search item_search;
	private View norows;
	private FiltrationParam filt = null;
	private int mPage = 1;
	private boolean loaddelay = true;
	TextView text, carnumber;
	public static RelativeLayout chart;
	Button saixuan, bt_shaixuan;
	String flag = "", specid = "", priceid = "", brandid = "", fid = "";
	private Msg_CitemList2.Builder OrderMain; // 订单
	RelativeLayout shoppingcart;
	boolean isshaixuan;
	public static boolean isphoneshop;
	private String orderby = "asc";
	Button bt_price, bt_pingjia, bt_sale, bt_more;
	boolean ischeckprice;
	TextView back;
	public static int[] ico_colors = new int[] { 0xffff3c00, 0xffab0e0e,
			0xff7e108e, 0xffab5c07 };
	public static String[] ico_str = new String[] { "特", "积", "组", "增" };
	DatabaseHepler dbHelper;
	SQLiteDatabase db;
	private PopupWindow pw_more;
	private Button btn_home_page;
	private Button btn_mine;
	String actfrom = "V3_ThreeMenuAct,Search,TczV3ShopGoodsListAct";
	public static String UMCOUNT = "";

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.tczv3_goodslist);
		setId("TczV3_GoodsListAct");
		actfrom = getIntent().getStringExtra("actfrom");
		bt_sale = (Button) findViewById(R.tczv3.rbt_sale);
		bt_pingjia = (Button) findViewById(R.tczv3.rbt_pingjia);
		bt_price = (Button) findViewById(R.tczv3.price);
		bt_shaixuan = (Button) findViewById(R.tczv3.bt_shaixuan);
		// text = (TextView) findViewById(R.tczv3.text);
		if (getIntent().getStringExtra("shaixuan") != null
				&& getIntent().getStringExtra("shaixuan").equals("true")) {
			isshaixuan = true;
			bt_shaixuan.setVisibility(View.VISIBLE);
		} else if (getIntent().getStringExtra("shaixuan") != null
				&& getIntent().getStringExtra("shaixuan").equals("false")) {
			isshaixuan = false;
			bt_shaixuan.setVisibility(View.GONE);
		}
		bt_shaixuan.setOnClickListener(new OnClick());
		// carnumber = (TextView) findViewById(R.tczv3.carnumber);
		chart = (RelativeLayout) findViewById(R.tczv3.chart);
		// carnumber.setText(F.GOODSCOUNT + "");
		isshow = getIntent().getStringExtra("isshow");
		listview = (PageListView) findViewById(R.tczv3.listview);
		bt_pingjia.setOnClickListener(new OnClick());
		bt_price.setOnClickListener(new OnClick());
		bt_sale.setOnClickListener(new OnClick());
		bt_shaixuan.setOnClickListener(new OnClick());
		if (getIntent().getCharSequenceExtra("category") != null) {
		}
		norows = findViewById(R.id.norows);
		back = (TextView) findViewById(R.tczv3.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		shoppingcart = (RelativeLayout) findViewById(R.tczv3.chart);
		shoppingcart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(TczV3_GoodsListAct.this, "TczV3_GoodsListAct",
							"", 0);
					return;
				}
//				if(Frame.HANDLES.get("TczV3_GoodsListAct").size()>0){
////					Frame.HANDLES.close("ShoppingCartAct");
//					Frame.HANDLES.sentAll("ShoppingCartAct", 103, "");
//					Frame.HANDLES.get("ShoppingCartAct").size();
//				}
				Intent intent = new Intent();
				intent.putExtra("actfrom", "TczV3_GoodsListAct");
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				intent.setClass(TczV3_GoodsListAct.this, ShoppingCartAct.class);
				startActivity(intent);
			}
		});

		sousuo = (Button) findViewById(R.tczv3.bt_search);
		sousuo.setOnClickListener(new OnClick());
		setSearch(getIntent());
		if (businessid.equals("")) {
			bt_shaixuan.setText("筛选");
		} else {
			bt_shaixuan.setText("分类筛选");
		}
		listview.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if (loaddelay) {
					dataLoadByDelay(null);
					loaddelay = false;
				} else {
					dataLoad();
				}
			}
		});

		listview.setLoadView(new FootLoadingShow(this));
		listview.start(1);
		View more_view = LayoutInflater.from(this).inflate(R.layout.head_more,
				null);
		pw_more = new PopupWindow(more_view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pw_more.setBackgroundDrawable(new BitmapDrawable());
		pw_more.setOutsideTouchable(true);
		bt_more = (Button) findViewById(R.tczv3.bt_more);
		bt_more.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (pw_more.isShowing()) {
					pw_more.dismiss();
				} else {
					pw_more.showAsDropDown(bt_more);
				}
			}
		});
		more_view.findViewById(R.id.li_share).setVisibility(View.GONE);
		btn_home_page = (Button) more_view.findViewById(R.id.btn_home_page);
		btn_mine = (Button) more_view.findViewById(R.id.btn_mine);
		btn_home_page.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Frame.HANDLES.closeWidthOut("FrameAg");
				Frame.HANDLES.sentAll("FrameAg", 1, R.frame.homeindex);
				pw_more.dismiss();
			}
		});
		btn_mine.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Frame.HANDLES.closeWidthOut("FrameAg");
				if (F.USER_ID.equals("")) {
					Intent intent = new Intent(TczV3_GoodsListAct.this,
							TczV3_LoginAct.class);
					startActivity(intent);
				} else {
					Frame.HANDLES.sentAll("FrameAg", 1, R.frame.more);
				}

				pw_more.dismiss();

			}
		});
	}

	private void setSearch(Intent intent) {
		if (getIntent().getStringExtra("categoryid") != null) {
			categoryid = intent.getStringExtra("categoryid");
			fenlei = intent.getStringExtra("category");
			UMCOUNT = "SelectClassifiedGoods";
			MobclickAgent.onEvent(this, "SelectClassifiedGoods");
		}
		if (getIntent().getStringExtra("businessid") != null) {
			businessid = getIntent().getStringExtra("businessid");
			businessname = getIntent().getStringExtra("businessname");
			UMCOUNT = "SelectStoreGoods";
			MobclickAgent.onEvent(this, "SelectStoreGoods");
		}
		// String keytype = intent.getStringExtra("type");
		keywords = intent.getStringExtra("keywords");
		sousuo.setText(keywords);
		if (keywords != null && keywords.length() > 0) {
			UMCOUNT = "SelectSearchedGoods";
			MobclickAgent.onEvent(this, "SelectSearchedGoods");
			dbHelper = new DatabaseHepler(TczV3_GoodsListAct.this);
			db = dbHelper.getWritableDatabase();
			String sql1 = "select contents from logcat where contents='"
					+ keywords + "' and styles='gouwu'";
			Cursor c = db.rawQuery(sql1, null);
			if (c != null && c.getCount() > 0) {
			} else {
				String sql = "insert into logcat(contents,styles) values(?,?)";
				db.execSQL(sql, new String[] { keywords, "gouwu" });
			}
			c.close();
			db.close();
			dbHelper.close();
		}
	}

	class OnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (sousuo.equals(v)) {
				Intent i = new Intent(TczV3_GoodsListAct.this,
						TczV3_Com_SearchAct.class);
				startActivity(i);
			} else if (bt_shaixuan.equals(v)) {
				// Intent i=new Intent();
				// i.setClass(TczV3_GoodsListAct.this, CategoryFilterAct.class);
				// startActivity(i);
				// if (!businessid.equals("") || !categoryid.equals("")) {
				// Intent in = new Intent(TczV3_GoodsListAct.this,
				// CategoryFilterAct.class);
				Intent in = new Intent(TczV3_GoodsListAct.this,
						TczV3_ShaiXuanAct.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				in.putExtra("actfrom", "TczV3_GoodsListAct");
				if (actfrom!=null&&actfrom.equals("TczV3ShopGoodsListAct"))
					in.putExtra("businessid", businessid);
				else
					in.putExtra("categoryid", categoryid);
				in.putExtra("keywords", keywords);
				startActivity(in);
				// } else
				// Toast.makeText(TczV3_GoodsListAct.this, "无法进行筛选",
				// Toast.LENGTH_SHORT).show();
			} else if (bt_price.equals(v)) {
				ischeckprice = true;
				bt_sale.setBackgroundResource(R.drawable.v3_radio5);
				bt_pingjia.setBackgroundResource(R.drawable.v3_radio5);
				LoadShow = true;
				if (orderby.equals("asc")) {
					orderby = "desc";
					bt_price.setBackgroundResource(R.drawable.v3_radio4_2);
				} else {
					orderby = "asc";
					bt_price.setBackgroundResource(R.drawable.v3_radio4_1);
				}
				bt_pingjia.setTextColor(0xff333333);
				bt_price.setTextColor(0xffffffff);
				bt_sale.setTextColor(0xff333333);
				ordertype = "2";
				ischeckprice = false;
				listview.reload();
			} else if (bt_pingjia.equals(v)) {
				LoadShow = true;
				ordertype = "1";
				orderby = "desc";
				bt_pingjia.setBackgroundResource(R.drawable.v3_radio6);
				bt_price.setBackgroundResource(R.drawable.v3_radio3);
				bt_sale.setBackgroundResource(R.drawable.v3_radio5);
				bt_pingjia.setTextColor(0xffffffff);
				bt_price.setTextColor(0xff333333);
				bt_sale.setTextColor(0xff333333);
				listview.reload();
			} else if (bt_sale.equals(v)) {
				LoadShow = true;
				ordertype = "3";
				orderby = "desc";
				bt_pingjia.setBackgroundResource(R.drawable.v3_radio5);
				bt_price.setBackgroundResource(R.drawable.v3_radio3);
				bt_sale.setBackgroundResource(R.drawable.v3_radio6);
				bt_pingjia.setTextColor(0xff333333);
				bt_price.setTextColor(0xff333333);
				bt_sale.setTextColor(0xffffffff);
				listview.reload();
			}

		}

	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 6) {
			// String[] str = (String[]) obj;
			// specid = str[0];
			// flag = str[1];
			Toast.makeText(TczV3_GoodsListAct.this, "该商品有多种规格需要选择！",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent();
			i.putExtra("itemid", (String) obj);
			i.putExtra("umcount", TczV3_GoodsListAct.UMCOUNT);
			i.setClass(TczV3_GoodsListAct.this, TczV3_GoodsDetailsAg.class);
			startActivity(i);
		} else if (type == 5) {
			Toast.makeText(TczV3_GoodsListAct.this, "该商品已售完！",
					Toast.LENGTH_SHORT).show();
		} else if (type == 4) {
			String[] str = (String[]) obj;
			specid = str[0];
			flag = str[1];
			dataLoad(new int[] { 1 });
		} else if (type == 3) {
			filt = (FiltrationParam) obj;
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 37) {
			Intent intent = (Intent) obj;
			setSearch(intent);
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 7) {// 帅选
			String[] str = (String[]) obj;
			brandid = str[0];
			priceid = str[1];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 8) {// 水果分类
			String[] str = (String[]) obj;
			brandid = str[0];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 9) {
			String[] str = (String[]) obj;
			businessid = str[0];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 10) {
			String[] str = (String[]) obj;
			categoryid = str[0];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 2) {
			String[] str = (String[]) obj;
			categoryid = "";
			businessid = str[0];
			storecateid = str[1];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		}

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null) {
			if (mPage == 1) {
				norows.setVisibility(View.VISIBLE);
				listview.setAdapter(null);
			}
			listview.endPage();
		} else if (son.build != null && son.mgetmethod.equals("slist_new")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem = builder.getCitemList();
			SLAdapter = new TczV3_GoodsListAdapter(TczV3_GoodsListAct.this,
					list_citem);
			listview.addData(SLAdapter);
			if (list_citem.size() < F.Per_Page) {
				listview.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		} else if (son.build != null && son.mgetmethod.equals("v3_slist_wei")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem = builder.getCitemList();
			SLAdapter = new TczV3_GoodsListAdapter(TczV3_GoodsListAct.this,
					list_citem);
			listview.addData(SLAdapter);
			if (list_citem.size() < F.Per_Page) {
				listview.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		} else if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// Toast.makeText(TczV3_GoodsListAct.this, "添加成功",
				// Toast.LENGTH_LONG)
				// .show();
				dataLoad(new int[] { 2 });
			} else {
				Toast.makeText(TczV3_GoodsListAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		} else if (son.build != null && son.mgetmethod.equals("plist")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			int count = 0;
			for (int i = 0; i < OrderMain.getCitemlistList().size(); i++) {
				for (int j = 0; j < OrderMain.getCitemlist(i).getCitemList()
						.size(); j++) {
					int num = Integer.parseInt(OrderMain.getCitemlist(i)
							.getCitem(j).getItemcount());
					count += num;
				}
			}
			// carnumber.setVisibility(View.VISIBLE);
			// F.GOODSCOUNT = count;
			// carnumber.setText(F.GOODSCOUNT + "");
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
	}

	@Override
	public void closeLoad() {
		super.closeLoad();
		this.LoadShow = false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 37) {
			if (resultCode == RESULT_OK) {
				String type = data.getStringExtra("type");
				String text = data.getStringExtra("text");
				item_search.set(text, type);
			}
		}
	}

	// http://api.taocz.com/mobile/tao.php?app=citemlist&act=slist&ordertype=1&orderby=desc&debug=1&page_per=20&page=946&keywords=
	// &hprice=&lprice=&hcount=0&freight=0&category4selfid
	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone(
					"SLIST_NEW",
					new String[][] {
							{ "keywords", keywords == null ? "" : keywords },
							{ "category4selfid",
									categoryid == null ? "" : categoryid },
							{ "page_per", F.Per_Page + "" },
							{ "page", mPage + "" },
							{ "orderby", orderby },
							{ "storecateid",
									storecateid == null ? "" : storecateid },
							{ "hprice", filt == null ? "" : filt.maxPrice },
							{ "lprice", filt == null ? "" : filt.minPrice },
							{
									"hcount",
									filt == null ? "0" : (filt.haveAgio ? "1"
											: "0") },
							{
									"freight",
									filt == null ? "0" : (filt.payLate ? "1"
											: "0") },
							{ "ordertype", ordertype == null ? "1" : ordertype },
							// { "fid", fid },
							{ "priceid", priceid }, { "brandid", brandid },
							{ "businessid", businessid } }), });
		} else if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", specid },
							{ "userid", F.USER_ID }, { "flg", flag } }), });
		} else if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("PLIST",
					new String[][] { { "userid", F.USER_ID } }), });
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (Frame.HANDLES.get("Search_Act").size() != 0) {
				if (isshow != null) {
					Frame.HANDLES.get("Search_Act").get(0)
							.sent(2, new String[] { keywords });
				} else {
					Frame.HANDLES.get("Search_Act").get(0)
							.sent(1, new String[] { keywords });
				}
			}
			this.finish();
			return true;
		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// carnumber.setText(F.GOODSCOUNT + "");
	}

}