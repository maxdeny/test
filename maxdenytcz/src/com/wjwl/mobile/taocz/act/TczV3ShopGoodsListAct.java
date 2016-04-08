package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MDragChangeViewAdapter;
import com.wjwl.mobile.taocz.adapter.TczV3ShopGoodsListAdapter;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.MyGridView;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3ShopGoodsListAct extends MActivity {

	TczV3ShopGoodsListAdapter adapter;
	private ArrayList<Map<String, Object>> mData;
	private MyGridView gridView;
	TczV3_HeadLayout headlayout;
	String businessname, businessid;
	MImageView img;
	DragChangeView mDragChangeView;
	TextView goodscount, shoppoint, collection, t_allgoods, category_title;
	ImageView ico_collection;
	private PopupWindow pw_more;
	private Button btn_home_page;
	private Button btn_mine, btn_category;
	List<Msg_Citem> dataList;
	LinearLayout img_layout;
	MImageView businesspic;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_shopgoodslist);
		View more_view = LayoutInflater.from(this).inflate(R.layout.head_more,
				null);
		pw_more = new PopupWindow(more_view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pw_more.setBackgroundDrawable(new BitmapDrawable());
		pw_more.setOutsideTouchable(true);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3ShopGoodsListAct.this.finish();
			}
		});
		businessname = getIntent().getStringExtra("title");
		businessid = getIntent().getStringExtra("businessid");
		headlayout.setTitle(businessname);
		headlayout.setRightButton1Background(R.drawable.tczv3_icon_more);
		headlayout.setRightButton1Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pw_more.isShowing()) {
					pw_more.dismiss();
				} else {
					pw_more.showAsDropDown(headlayout.getButton1());
				}
			}
		});

		more_view.findViewById(R.id.li_share).setVisibility(View.GONE);
		btn_home_page = (Button) more_view.findViewById(R.id.btn_home_page);
		btn_mine = (Button) more_view.findViewById(R.id.btn_mine);
		btn_category = (Button) findViewById(R.tczv3.bt_category);
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
				Frame.HANDLES
						.closeWidthOut("FrameAg,MyAct,ShoppingCartAct,Search_Act,V3_ThreeMenuAct,HomePageAct");
				if (F.USER_ID.equals("")) {
					Intent intent = new Intent(TczV3ShopGoodsListAct.this,
							TczV3_LoginAct.class);
					startActivity(intent);
				} else {
					Frame.HANDLES.sentAll("FrameAg", 1, R.frame.more);
				}
				pw_more.dismiss();

			}
		});

		headlayout.setRightButton2Background(R.drawable.tczv3_icon_search);
		headlayout.setRightButton2Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("businessname", businessname);
				i.putExtra("businessid", businessid);
				i.putExtra("actfrom", "TczV3ShopGoodsListAct");
				i.setClass(TczV3ShopGoodsListAct.this,
						TczV3_Com_SearchAct.class);
				startActivity(i);
			}
		});
		img = (MImageView) findViewById(R.tczv3.img);
		mDragChangeView = (DragChangeView) findViewById(R.tczv3.DragChangeView);
		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setHideRadio(false);
		mDragChangeView.setAutoMove(false);
		mDragChangeView.setRadius(7);

		gridView = (MyGridView) findViewById(R.tczv3.gridview);
		goodscount = (TextView) findViewById(R.tczv3.goodscount);
		shoppoint = (TextView) findViewById(R.tczv3.pj_point);
		collection = (TextView) findViewById(R.tczv3.t_collection);
		ico_collection = (ImageView) findViewById(R.tczv3.icon_collection);
		t_allgoods = (TextView) findViewById(R.tczv3.t_allgoods);
		img_layout = (LinearLayout) findViewById(R.tczv3.img_layout);
		businesspic = (MImageView) findViewById(R.tczv3.businesspic);
		category_title = (TextView) findViewById(R.tczv3.category_title);
		t_allgoods.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.putExtra("businessid", businessid);// 1278
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("actfrom", "TczV3ShopGoodsListAct");
				i.setClass(getApplicationContext(), TczV3_GoodsListAct.class);
				getApplicationContext().startActivity(i);
			}
		});
		btn_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.putExtra("businessid", businessid);// 1278
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("actfrom", "TczV3ShopGoodsListAct");
				i.setClass(getApplicationContext(), TczV3_GoodsListAct.class);
				getApplicationContext().startActivity(i);
			}
		});
		dataLoad(new int[] { 0 });

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("V3_AD")) {
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder) son.build;
			List<Msg_CitemList> dataSource = builder.getCitemlistList();
			MDragChangeViewAdapter mDragChangeViewAdapter = new MDragChangeViewAdapter(
					dataSource.get(0).getCitemList(), this);
			mDragChangeView.setAdapter(mDragChangeViewAdapter);
			if (dataSource.get(0).getCitemList().size() > 0) {
				mDragChangeView.setVisibility(View.VISIBLE);
			} else {
				mDragChangeView.setVisibility(View.GONE);
			}

		}
		if (son.build != null && son.mgetmethod.equals("V3_STOREINFO")) {
			category_title.setVisibility(View.VISIBLE);
			t_allgoods.setVisibility(View.VISIBLE);
			mData = new ArrayList<Map<String, Object>>();
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			dataList = builder.getCitemList();
			businesspic.setObj(builder.getBusinessname() 
//					+ "."+ F.getCurrnetWindowWidth(this) / 1 + "x"
//					+ F.getCurrnetWindowWidth(this) / 3 + ".jpg"
					);
			LayoutParams lp = new LayoutParams(F.getCurrnetWindowWidth(this),
					F.getCurrnetWindowWidth(this) / 3);
			businesspic.setLayoutParams(lp);
			businesspic.setType(9);
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", dataList.get(i).getItemimageurl());
				map.put("title", dataList.get(i).getItemtitle());
				map.put("tcz_price", dataList.get(i).getItemdiscount());
				map.put("itemid", dataList.get(i).getItemid());
				map.put("old_price", dataList.get(i).getItemprice());
				mData.add(map);
			}
			adapter = new TczV3ShopGoodsListAdapter(TczV3ShopGoodsListAct.this,
					mData);
			gridView.setAdapter(adapter);
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 0) {
			this.loadData(new Updateone[] {
					new Updateone("V3_STOREINFO", new String[][] { { "storeid",
							businessid } }),
					new Updateone("V3_AD", // 1278
							new String[][] { { "ppage", "store" },
									{ "storeid", businessid } }) });
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("StorePage");
		MobclickAgent.onResume(TczV3ShopGoodsListAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("StorePage");
		MobclickAgent.onPause(TczV3ShopGoodsListAct.this);
	}

}
