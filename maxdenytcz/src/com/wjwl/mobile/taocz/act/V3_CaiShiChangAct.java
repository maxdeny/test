package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FiltrationAct.FiltrationParam;
import com.wjwl.mobile.taocz.act.ShoppingListAct.OnClick;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter4;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.Item_Search;

public class V3_CaiShiChangAct extends MActivity {
	private PageListView listview;
	private RadioGroup radiogroup;
	private Button btn_category, btn_business, btn_select, back, sousuo;
	ShoppingListAdapter4 SLAdapter;
	boolean isEdit = true;
	String categoryid = "", isshow = null;
	String ordertype = "1";
	String fenlei, keywords = "";
	String shangquan, category4areaid = "530";
	String categoryname;
	private PullReloadView prv;
	private Item_Search item_search;
	private View norows;
	private FiltrationParam filt = null;
	private int mPage = 1;
	private boolean loaddelay = true;
	TextView text, carnumber;
	public static RelativeLayout chart;
	Button saixuan;
	String flag = "", specid = "", vid = "";
	private Msg_CitemList2.Builder OrderMain; // 订单
	private RelativeLayout shoppingcart;
	Button business_shaixuan;
	private String businessid = "";
	private String orderby = "asc";// 升序
	Button bt_price;
	RadioButton rbt_1, rbt_2;
	boolean ischeckprice;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.shoppinglist);
		setId("V3_CaiShiChangAct");
		//categoryid=getIntent().getStringExtra("categoryid");
		business_shaixuan = (Button) findViewById(R.shoppinglist.business_shaixuan);
		business_shaixuan.setVisibility(View.VISIBLE);
		carnumber = (TextView) findViewById(R.shoppinglist.carnumber);
		chart = (RelativeLayout) findViewById(R.shoppinglist.chart);
		carnumber.setText(F.GOODSCOUNT + "");
		isshow = getIntent().getStringExtra("isshow");
		rbt_1 = (RadioButton) findViewById(R.shoppinglist.rbt_people);
		rbt_2 = (RadioButton) findViewById(R.shoppinglist.rbt_sale);
		listview = (PageListView) findViewById(R.shoppinglist.listview);
		radiogroup = (RadioGroup) findViewById(R.shoppinglist.radiogroup);
		btn_category = (Button) findViewById(R.shoppinglist.btn_category);
		btn_business = (Button) findViewById(R.shoppinglist.btn_business);
		btn_select = (Button) findViewById(R.shoppinglist.btn_select);
		shoppingcart = (RelativeLayout) findViewById(R.shoppinglist.chart);
		bt_price = (Button) findViewById(R.shoppinglist.price);
		shoppingcart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_CaiShiChangAct.this, "V3_CaiShiChangAct", "",
							0);
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("actfrom", "ShoppingListAct");
				intent.setClass(V3_CaiShiChangAct.this, ShoppingCartAct.class);
				startActivity(intent);
			}
		});
		bt_price.setOnClickListener(new OnClick());
		business_shaixuan.setOnClickListener(new OnClick());
		btn_category.setOnClickListener(new OnClick());
		btn_business.setVisibility(View.GONE);
		btn_business.setOnClickListener(new OnClick());
		btn_select.setOnClickListener(new OnClick());
		if (getIntent().getCharSequenceExtra("category") != null) {
			btn_category.setText(getIntent().getCharSequenceExtra("category"));
		}
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		norows = findViewById(R.id.norows);
		btn_business.setVisibility(View.GONE);
		// item_search = (Item_Search)findViewById(R.shoppinglist.item_search);
		// item_search.setSearchDefault(getIntent().getIntExtra("searchPupub",
		// 0));
		back = (Button) findViewById(R.shoppinglist.back);
		text = (TextView) findViewById(R.shoppinglist.text);
		if (getIntent().getStringExtra("category") != null)
			text.setText(getIntent().getStringExtra("category"));
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Frame.HANDLES.get("Search_Act").size() != 0) {
					if (isshow != null) {
						Frame.HANDLES.get("Search_Act").get(0)
								.sent(2, new String[] { keywords });
					} else {
						Frame.HANDLES.get("Search_Act").get(0)
								.sent(1, new String[] { keywords });
					}
				}
				finish();
			}
		});
		saixuan = (Button) findViewById(R.shoppinglist.saixuan);
		if (getIntent().getBooleanExtra("saixuan", false) == true) {
			saixuan.setVisibility(View.VISIBLE);
		} else {
			saixuan.setVisibility(View.GONE);
		}
		saixuan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(V3_CaiShiChangAct.this,
						FruitCategoryAct.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				in.putExtra("navtype", "vmarket");
				in.putExtra("categoryid", categoryid);
				startActivity(in);

			}
		});
		sousuo = (Button) findViewById(R.shopinglist.sousuo);
		sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(V3_CaiShiChangAct.this, Search_Act.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		});

		setSearch(getIntent());
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

		prv = (PullReloadView) findViewById(R.shoppinglist.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
	}

	private void setSearch(Intent intent) {
		categoryid = intent.getStringExtra("categoryid");
		fenlei = intent.getStringExtra("category");
		String keytype = intent.getStringExtra("type");
		keywords = intent.getStringExtra("keywords");
		if (keywords != null) {
			text.setText(keywords);
			// item_search.set(keywords,keytype);
		}
	}

	class OnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (btn_category.equals(v)) {
				keywords = "";
				Intent intent = new Intent(v.getContext(),
						CategoryFirstAct.class);
				intent.putExtra("isSelect", true);
				intent.putExtra("title", "购物");
				if (Frame.HANDLES.get("CategoryFirstAct").size() > 0)
					Frame.HANDLES.closeOne("CategoryFirstAct");
				if (Frame.HANDLES.get("CategorySecondAct").size() > 0)
					Frame.HANDLES.closeOne("CategorySecondAct");
				v.getContext().startActivity(intent);
			} else if (btn_business.equals(v)) {
				Intent i = new Intent(v.getContext(), BusinessGroupAllAct.class);
				i.putExtra("navtype", "shop");
				v.getContext().startActivity(i);
			} else if (btn_select.equals(v)) {
				Intent i = new Intent(v.getContext(), FiltrationAct.class);
				i.putExtra("navtype", "shop");
				if (filt != null) {
					i.putExtra("filter", filt);
				}
				v.getContext().startActivity(i);
			} else if (business_shaixuan.equals(v)) {
				Intent i1 = new Intent(v.getContext(),
						BusinessCategoryAct.class);
				i1.putExtra("categoryid", categoryid);
				i1.putExtra("actfrom", "V3_CaiShiChangAct");
				v.getContext().startActivity(i1);
			} else if (bt_price.equals(v)) {
				ischeckprice = true;
				rbt_1.setChecked(false);
				rbt_2.setChecked(false);
				LoadShow = true;
				if (orderby.equals("asc")) {
					orderby = "desc";// 降序
					bt_price.setBackgroundResource(R.drawable.v3_radio43);
				} else {
					orderby = "asc";// 升序
					bt_price.setBackgroundResource(R.drawable.v3_radio42);
				}
				ordertype = "2";
				ischeckprice = false;
				listview.reload();
			}
		}

	}

	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (ischeckprice)
				return;
			LoadShow = true;
			switch (checkedId) {
			case R.shoppinglist.rbt_people:
				ordertype = "1";
				bt_price.setBackgroundResource(R.drawable.v3_radio3);
				break;
			// case R.shoppinglist.rbt_price:
			// ordertype = "2";
			// break;
			case R.shoppinglist.rbt_sale:
				ordertype = "3";
				bt_price.setBackgroundResource(R.drawable.v3_radio3);
				break;
			}
			listview.reload();
		}
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 6) {
			Toast.makeText(V3_CaiShiChangAct.this, "该商品有多种规格需要选择！",
					Toast.LENGTH_SHORT).show();
			Intent i = new Intent();
			i.putExtra("itemid", (String) obj);
			i.setClass(V3_CaiShiChangAct.this, V3_ShoppingDetailsAg.class);
			startActivity(i);
		} else if (type == 1) {
			this.btn_business.setText(shangquan == null ? "商圈" : shangquan);
			fenlei = ((Msg_Ccategory) obj).getCategoryname();
			categoryid = ((Msg_Ccategory) obj).getCategoryid();
			this.btn_category.setText(fenlei);
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 2) {
			this.btn_category.setText(fenlei == null ? "分类" : fenlei);
			shangquan = ((Msg_Ccategory) obj).getCategoryname();
			category4areaid = ((Msg_Ccategory) obj).getCategoryid();
			this.btn_business.setText(shangquan);
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
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
		} else if (type == 4) {
			String[] str = (String[]) obj;
			specid = str[0];
			flag = str[1];
			dataLoad(new int[] { 1 });
		} else if (type == 8) {
			String[] str = (String[]) obj;
			categoryid = str[0];
			loaddelay = true;
			this.LoadShow = true;
			listview.reload();
		} else if (type == 9) {
			String[] str = (String[]) obj;
			businessid = str[0];
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
		}
		if (son.build != null && son.mgetmethod.equals("v3_slist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem = builder.getCitemList();
			SLAdapter = new ShoppingListAdapter4(V3_CaiShiChangAct.this,
					list_citem);
			listview.addData(SLAdapter);
			if (list_citem.size() < F.Per_Page) {
				listview.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		}
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// F.changecart(0);
				Toast.makeText(V3_CaiShiChangAct.this, "添加成功",
						Toast.LENGTH_LONG).show();
				dataLoad(new int[] { 2 });
			} else {
				Toast.makeText(V3_CaiShiChangAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("plist")) {
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
			carnumber.setVisibility(View.VISIBLE);
			F.GOODSCOUNT = count;
			carnumber.setText(F.GOODSCOUNT + "");
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
		prv.refreshComplete();
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

	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone(
					"V3_SLIST",
					new String[][] {
							{ "keywords", keywords == null ? "" : keywords },
							{ "category4selfid",
									categoryid == null ? "" : categoryid },
							{ "page_per", F.Per_Page + "" },// 30
							// { "vid", vid },// 30
							{ "page", mPage + "" },
							{ "orderby", orderby },
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
		carnumber.setText(F.GOODSCOUNT + "");
	}

}