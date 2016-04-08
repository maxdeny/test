package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CcommentList.Msg_CcommentList;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
import com.wjwl.mobile.taocz.adapter.TczV3_GoodsBasicInfoAdapter;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.Item_groupgoodsdetails;
import com.wjwl.mobile.taocz.widget.MyGridView;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_GroupGoodsDetailsAct extends MActivity {

	private ArrayList<Map<String, Object>> mUserData;
	private LinearLayout addlayout;
	private TextView title, tczprice, oldprice;
	private DragChangeView mDragChangeView;
	private String price = "", itemid = "", specid = "", kucun = "";
	private MyGridView gridView;
	private ArrayList<Map<String, Object>> mData;
	List<Msg_Sstandard> sstandardlist;
	TczV3_GoodsBasicInfoAdapter adapter;
	List<Msg_Cpic> cpiclist;
	private Msg_CcommentList groupgoodslist;
	public static ContentImgListAdapter iaad;
	TczV3_HeadLayout headlayout;
	Button bt_buy;
	String buytype;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.groupgoodsdetails);
		setId("TczV3_GroupGoodsDetailsAct");
		itemid = getIntent().getStringExtra("itemid");
		headlayout = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_GroupGoodsDetailsAct.this.finish();
			}
		});
		bt_buy = (Button) findViewById(R.tczv3.bt_addcart);
		mDragChangeView = (DragChangeView) findViewById(R.tczv3.DragChangeView);
		addlayout = (LinearLayout) findViewById(R.tczv3.addlayout);
		title = (TextView) findViewById(R.tczv3.t_tcztitle);
		tczprice = (TextView) findViewById(R.tczv3.tczprice);
		oldprice = (TextView) findViewById(R.tczv3.marketprice);
		mUserData = new ArrayList<Map<String, Object>>();
		mData = new ArrayList<Map<String, Object>>();
		bt_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buytype = "0";
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(TczV3_GroupGoodsDetailsAct.this,
							"TczV3_GroupGoodsDetailsAct", "B", 0);
					return;
				}
				dataLoad(new int[] { 1 });
			}
		});
		dataLoad(null);
	}

	private void addGroupGoods() {
		addlayout.removeAllViews();
		for (int i = 0; i < mUserData.size(); i++) {
			LayoutInflater flater = LayoutInflater
					.from(TczV3_GroupGoodsDetailsAct.this);
			Item_groupgoodsdetails item1 = (Item_groupgoodsdetails) flater
					.inflate(R.layout.item_groupgoodsdetails, null);
			item1.initview();
			item1.setPersonImg((String) mUserData.get(i).get("img"));
			item1.setItemId((String) mUserData.get(i).get("itemid"));
			item1.setPrice((String) mUserData.get(i).get("price"));
			item1.setTitle((String) mUserData.get(i).get("title"));
			addlayout.addView(item1);
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && (son.mgetmethod.equals("V3_SCONTENT_NEW"))) {
			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
			title.setText(builder.getCitem().getIteminfo());
			// specid = builder.getCitem().getSpecid();
			// kucun = builder.getCitem().getOther1();// 库存
			price = builder.getCitem().getItemprice();
			tczprice.setText(price);// 淘常州销售价
			oldprice.setText(builder.getCitem().getItemcount());// 市场价
			// builder.getCitem().getItemselltype();// 默认规格商品类型
			// builder.getCitem().getItemsold();// 已售数量
			cpiclist = builder.getCpiclist().getCpicList();
			iaad = new ContentImgListAdapter(this, cpiclist);
			mDragChangeView.setAdapter(iaad);
			mDragChangeView.setCurrIcon(R.drawable.yes_click);
			mDragChangeView.setMoveIcon(R.drawable.yes_click);
			mDragChangeView.setNoCurrIcon(R.drawable.no_click);
			// 组合单品列表
			if (mData.size() > 0)
				mData.clear();
			groupgoodslist = builder.getCommentlist();
			for (int i = 0; i < groupgoodslist.getCommentList().size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", groupgoodslist.getComment(i).getCommentstar());
				map.put("title", groupgoodslist.getComment(i)
						.getCommentcontent());
				map.put("price", groupgoodslist.getComment(i).getCommenttime());
				map.put("itemid", groupgoodslist.getComment(i).getCommentid());
				mUserData.add(map);
			}
			addGroupGoods();
			// 推荐商品
			sstandardlist = builder.getSstandardlist().getSstandardList();
			gridView = (MyGridView) findViewById(R.tczv3.gridview);
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < sstandardlist.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", sstandardlist.get(i).getStore());
				map.put("title", sstandardlist.get(i).getFirstname());
				map.put("tcz_price", sstandardlist.get(i).getPrice());
				map.put("itemid", sstandardlist.get(i).getFirstid());
				map.put("old_price", "0");
				mData.add(map);
			}
			adapter = new TczV3_GoodsBasicInfoAdapter(
					TczV3_GroupGoodsDetailsAct.this, mData);
			gridView.setAdapter(adapter);

		}
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// F.changecart(0);
				Toast.makeText(TczV3_GroupGoodsDetailsAct.this, "添加成功",
						Toast.LENGTH_LONG).show();

				// if(umcount.equals("SelectJXGoods")){//加入至精选购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2JXShoppingCart");
				// }
				// else if(umcount.equals("SelectHDGoods")){//加入至活动购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2HDShoppingCart");
				// }
				// else if(umcount.equals("SelectSearchedGoods")){//加入至搜索购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2SearchedShoppingCart");
				// }
				// else if(umcount.equals("SelectStoreGoods")){//加入至店铺购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2StoreShoppingCart");
				// }
				// else if(umcount.equals("SelectClassifiedGoods")){//加入至分类购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2ClassifiedShoppingCart");
				// }
				// else if(umcount.equals("SelectPushedGoods")){//加入至推送购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2PushedShoppingCart");
				// }
				// else{//加入至默认购物车
				// MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this,
				// "Add2DefaultShoppingCart");
				// }
				// dataLoad(new int[] { 3 });
			} else {
				Toast.makeText(TczV3_GroupGoodsDetailsAct.this,
						retn.getErrorMsg(), Toast.LENGTH_LONG).show();
			}
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null)
			this.loadData(new Updateone[] { new Updateone("V3_SCONTENT_NEW",
					new String[][] { { "itemid", itemid }, { "flag", "4" } }), });
		else if (types[0] == 1)
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", itemid },
							{ "userid", F.USER_ID },
							{ "username", F.USERNAME }, { "flg", "4" } }), });
	}
}
