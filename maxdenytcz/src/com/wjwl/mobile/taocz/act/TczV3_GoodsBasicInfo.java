package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Attribute.Msg_AttributeValue;
import com.tcz.apkfactory.data.Attribute.Msg_Store;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList.Builder;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
import com.wjwl.mobile.taocz.adapter.TczV3_GoodsBasicInfoAdapter;
import com.wjwl.mobile.taocz.untils.Arith;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.MyGridView;

public class TczV3_GoodsBasicInfo extends MActivity {
	TczV3_GoodsBasicInfoAdapter adapter;
	private ArrayList<Map<String, Object>> mData;
	private MyGridView gridView;
	String specid, price, flag, store, businessid, itemid, qgid, businessnames,
			str_iscollection = "false";
	public static String environmentstar;
	List<Msg_Cpic> cpiclist;
	DragChangeView mDragChangeView;
	public static ContentImgListAdapter iaad;
	List<Msg_Ccomment> ccommentlist;
	Msg_Cbusinessinfo cbusinessinfo;
	Msg_Citem citem;
	TextView title, tczprice, oldprice, sellnum, yunfei, wuliu, acttime,
			xiangou_num, shengyu_time, qianggou_guige, guige, act_content,
			businessname, myd;
	RelativeLayout clic_layout1;
	Button qianggou_price, qianggou_noprice, bt_buy;
	Timer timer = new Timer();
	Long recLen;
	Msg_Cstars cstars;
	RelativeLayout layout_qianggou, layout_guige, layout_act;
	Builder mAttribute;
	boolean bolean = false;
	LinearLayout clic_collection;
	ImageView ico_collection;
	TextView t_collection, t_xiangou,scjprice;
	List<Msg_Sstandard> sstandardlist;
	String buytype = "0", businessName = "",umcount="";// 记录是普通购买还是抢购
	RelativeLayout layout_shop;
	public static String goodstitle="",goodssell="";
	

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_goodsbasicinfo);
		itemid = getIntent().getStringExtra("itemid");
		umcount=getIntent().getStringExtra("umcount")==null?"":getIntent().getStringExtra("umcount");
		mDragChangeView = (DragChangeView) findViewById(R.tczv3.DragChangeView);
		tczprice = (TextView) findViewById(R.tczv3.tczprice);
		oldprice = (TextView) findViewById(R.tczv3.marketprice);
		title = (TextView) findViewById(R.tczv3.t_tcztitle);
		wuliu = (TextView) findViewById(R.tczv3.pstype);
		layout_qianggou = (RelativeLayout) findViewById(R.tczv3.layout_qianggou);
		t_xiangou = (TextView) findViewById(R.tczv3.xianggou);
		qianggou_price = (Button) findViewById(R.tczv3.qianggou_price);
		qianggou_noprice = (Button) findViewById(R.tczv3.qianggou_noprice);
		qianggou_guige = (TextView) findViewById(R.tczv3.qgguige);
		guige = (TextView) findViewById(R.tczv3.guige);
		shengyu_time = (TextView) findViewById(R.tczv3.acttime);
		act_content = (TextView) findViewById(R.tczv3.act_content);
		layout_guige = (RelativeLayout) findViewById(R.tczv3.layout_guige);
		businessname = (TextView) findViewById(R.tczv3.businessname);
		myd = (TextView) findViewById(R.tczv3.myd);
		layout_act = (RelativeLayout) findViewById(R.tczv3.layout_act);
		clic_collection = (LinearLayout) findViewById(R.tczv3.clic_collection);
		layout_shop = (RelativeLayout) findViewById(R.tczv3.layout_shop);
		ico_collection = (ImageView) findViewById(R.tczv3.icon_collection);
		t_collection = (TextView) findViewById(R.tczv3.t_collection);
		scjprice = (TextView) findViewById(R.tczv3.scjprice);
		bt_buy = (Button) findViewById(R.tczv3.bt_addcart);
		dataLoad(new int[] { 0 });
		layout_guige.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TczV3_GoodsBasicInfo.this,
						AttributeAct.class);
				intent.putExtra("itemid", itemid);
				intent.putExtra("flag", flag);
				intent.putExtra("specid", specid);
				startActivityForResult(intent, 1);
			}
		});
		clic_collection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(TczV3_GoodsBasicInfo.this,
							"TczV3_GoodsBasicInfo", "C", 0);
					return;
				}
				if (str_iscollection.equals("true"))
					Toast.makeText(getApplication(), "该商品已被收藏！",
							Toast.LENGTH_SHORT).show();
				else
					collection();
			}
		});
		bt_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buytype = "0";
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(TczV3_GoodsBasicInfo.this,
							"TczV3_GoodsBasicInfo", "B", 0);
					return;
				}
				buy();
			}
		});
		qianggou_price.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buytype = "1";
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(TczV3_GoodsBasicInfo.this,
							"TczV3_GoodsBasicInfo", "B", 0);
					return;
				}
				buy2();
			}
		});
		layout_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("title", businessName);
				i.putExtra("businessid", businessid);
				i.setClass(TczV3_GoodsBasicInfo.this,
						TczV3ShopGoodsListAct.class);
				startActivity(i);
			}
		});
	}

	public void collection() {
		dataLoad(new int[] { 2 });
	}

	public void disposeMsg(int type, Object obj) {
		if (type == 86) {
			if (F.USER_ID != null && F.USER_ID.length() > 0) {
				if ("B".equals(obj)) {
					if (buytype.equals("0"))
						buy();
					else if (buytype.equals("1"))
						buy2();
				} else if ("C".equals(obj)) {
					collection();
				}
			}

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && (son.mgetmethod.equals("V3_SCONTENT"))) {
			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
			cpiclist = builder.getCpiclist().getCpicList();
			iaad = new ContentImgListAdapter(this, cpiclist);
			mDragChangeView.setAdapter(iaad);
			mDragChangeView.setCurrIcon(R.drawable.yes_click);
			mDragChangeView.setMoveIcon(R.drawable.yes_click);
			mDragChangeView.setNoCurrIcon(R.drawable.no_click);
			ccommentlist = builder.getCommentlist().getCommentList();// 评论
			cbusinessinfo = builder.getCbusinessinfo();
			businessid = cbusinessinfo.getBusinessid();
			businessName = cbusinessinfo.getBusinessname();
			businessname.setText("店铺:  " + businessName);
			// 是否被收藏 builder.getCitem().getPoints() 1,0
			if (builder.getCitem().getPoints().equals("1")) {
				setIsCollection(true);
			} else if (builder.getCitem().getPoints().equals("0")) {
				setIsCollection(false);
			}
			TczV3_GoodsDetailsAg.radio_comment.setText("评价("
					+ ccommentlist.size() + ")");
			citem = builder.getCitem();
			F.PROIMG = cpiclist.get(0).getImageurl();
			F.PROTITLE = builder.getCitem().getIteminfo();
			citem = builder.getCitem();
			cstars = builder.getCstars();
			specid = citem.getSpecid();// id
			flag = citem.getItemselltype();// 特价
			store = citem.getOther1();// 库存 为0是已经售完
			goodstitle=citem.getIteminfo();
			goodssell=citem.getItemsold();
			title.setText(citem.getIteminfo());
			if (citem.getV3Istczsell().equals("")) {
				layout_act.setVisibility(View.GONE);
			}
			act_content.setText(citem.getV3Istczsell());
			wuliu.setText(Html.fromHtml(citem.getV3Freightinfo()).toString()
					.replace("&&", "\n"));// 物流
			
			String[] allprice=Arith.returnpricr(citem.getItemprice(), citem.getItemdiscount(), citem.getItemcount());
			//淘常州价
			price = allprice[0];// 折扣价
			tczprice.setText("￥" +price);
			//市场价
			oldprice.setText("￥" + allprice[1]);
			oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			oldprice.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			if (allprice[1].equals("0.0")||allprice[1].equals("0")
					|| allprice[1].equals("0.00")||allprice[1].equals("")) {
				oldprice.setVisibility(View.INVISIBLE);
			}
			else{
				scjprice.setVisibility(View.VISIBLE);
				oldprice.setVisibility(View.VISIBLE);
			}
			
			environmentstar = cstars.getEnvironmentstar().split(",")[3];
			myd.setText("  " + cstars.getMatchstar());
			qgid = citem.getOther2();
			if (qgid!=null&&qgid.length() > 0&&!qgid.equals("0")) {
				qianggou_price.setText("立即抢购     ￥"
						+ citem.getV3Purchaseprice());
				// if (citem.getV3Purchaselimit().equals("0"))
				// xiangou_num.setVisibility(View.GONE);
				// else
				// xiangou_num.setText("每人限购" + citem.getV3Purchaselimit()
				// + "件");
				qianggou_guige.setText("规格："
						+ (citem.getItemaddr().equals("") ? "默认规格" : citem
								.getItemaddr()));
				if (!citem.getV3Purchaselimit().equals(""))
					t_xiangou
							.setText("每人限购" + citem.getV3Purchaselimit() + "件");
				 //抢购规格ID
				flag = "2";// 抢购
				if (citem.getV3Remainingtime() != null
						&& citem.getV3Remainingtime().length() > 1&&!citem.getV3Remainingtime().equals("0")) {
					recLen = Long.parseLong(citem.getV3Remainingtime());
					timer.schedule(task, 1000, 1000);// 递减
				} else {
					shengyu_time.setText("尚未开始");
					qianggou_price.setVisibility(View.GONE);
					qianggou_noprice.setVisibility(View.VISIBLE);
				}
				layout_qianggou.setVisibility(View.VISIBLE);
			} else {
				layout_qianggou.setVisibility(View.GONE);
			}
			// sellnum.setText("已售:" + citem.getItemsold());
			// sellnum.setVisibility(View.GONE);
			// cstars = builder.getCstars();
			String[] str = new String[] {
					citem.getIteminfo() + ",淘常州价:" + citem.getItemdiscount()
							+ "元", citem.getIteminfo() };

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
					TczV3_GoodsBasicInfo.this, mData);
			gridView.setAdapter(adapter);

		}
		if (son.build != null && son.mgetmethod.equals("attribute")) {
			mAttribute = (Builder) son.build;
			setAttribute(mAttribute);
		}
		if (son.build != null && son.mgetmethod.equals("ofavorite")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(TczV3_GoodsBasicInfo.this, "添加收藏成功",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(TczV3_GoodsBasicInfo.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			setIsCollection(true);
		}
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// F.changecart(0);
				Toast.makeText(TczV3_GoodsBasicInfo.this, "添加成功",
						Toast.LENGTH_LONG).show();
				
				if(umcount.equals("SelectJXGoods")){//加入至精选购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2JXShoppingCart");
				}
				else if(umcount.equals("SelectHDGoods")){//加入至活动购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2HDShoppingCart");
				}
				else if(umcount.equals("SelectSearchedGoods")){//加入至搜索购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2SearchedShoppingCart");		
								}
				else if(umcount.equals("SelectStoreGoods")){//加入至店铺购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2StoreShoppingCart");
				}
				else if(umcount.equals("SelectClassifiedGoods")){//加入至分类购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2ClassifiedShoppingCart");
				}
				else if(umcount.equals("SelectPushedGoods")){//加入至推送购物车 
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2PushedShoppingCart");
				}
				else{//加入至默认购物车
					MobclickAgent.onEvent(TczV3_GoodsBasicInfo.this, "Add2DefaultShoppingCart");
				}
				
				
				// dataLoad(new int[] { 3 });
			} else {
				Toast.makeText(TczV3_GoodsBasicInfo.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}

	}

	public void setIsCollection(Boolean iscollection) {
		if (iscollection) {
			str_iscollection = "true";
			ico_collection
					.setBackgroundResource(R.drawable.tczv3_icon_btn_faved);
			t_collection.setText("已收藏");
		} else {
			str_iscollection = "false";
			ico_collection.setBackgroundResource(R.drawable.tczv3_icon_btn_fav);
			t_collection.setText("收藏");
		}
	}

	private void setAttribute(Msg_AttributeList.Builder mbuild) {
		List<Msg_Store> storelist = mbuild.getStoreList();
		List<Msg_AttributeValue> attributv = mbuild.getAttributeValueList();
		if (storelist.size() > 1) {// 判断规格
			StringBuffer stb = new StringBuffer();
			for (Msg_Store msg : storelist) {
				if (specid == null) {
					specid = msg.getSpecid();
				}
				if (msg.getSpecid().equals(specid)) {
					if (attributv.size() == 0) {
						layout_guige.setVisibility(View.GONE);
					} else if (attributv.size() >= 1) {
						layout_guige.setVisibility(View.VISIBLE);
						stb.append(attributv.get(0).getName());
						stb.append(":");
						stb.append(msg.getFirst());
						if (attributv.size() > 1) {
							stb.append("; ");
							stb.append(attributv.get(1).getName());
							stb.append(":");
							stb.append(msg.getSecond());
						}
						guige.setText(stb.toString());
					}
				}
			}
		} else {
			bolean = true;
			layout_guige.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			specid = data.getStringExtra("specid");
			bolean = true;
			setAttribute(mAttribute);
		}
	}

	public void buy() {
		if (bolean) {
			dataLoad(new int[] { 1 });
		} else {
			Intent intent = new Intent(this, AttributeAct.class);
			intent.putExtra("itemid", itemid);
			intent.putExtra("specid", specid);
			intent.putExtra("umcount", umcount);
			intent.putExtra("flag", flag);// flag
			startActivityForResult(intent, 1);
		}
	}

	public void buy2() {
		dataLoad(new int[] { 4 });
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 0) {
			if (getIntent().getStringExtra("tejia") != null) {
				this.loadData(new Updateone[] {
						new Updateone("V3_SCONTENT", new String[][] {
								{ "itemid", itemid }, { "userid", F.USER_ID },
								{ "isindex", "1" } }),
						new Updateone("ATTRIBUTE", new String[][] { {
								"goods_id", itemid } }), });
			} else {
				this.loadData(new Updateone[] {
						new Updateone("V3_SCONTENT", new String[][] {
								{ "itemid", itemid }, { "userid", F.USER_ID } }),
						new Updateone("ATTRIBUTE", new String[][] { {
								"goods_id", itemid } }), });
			}
		} else if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("OFAVORITE",
					new String[][] { { "itemid", itemid },
							{ "calss", "material" }, { "userid", F.USER_ID },
							{ "price", price } }), });
		} else if (types[0] == 1)
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", specid },
							{ "userid", F.USER_ID },
							{ "username", F.USERNAME }, { "flg", flag } }), });
		else if (types[0] == 4)
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", qgid },
							{ "userid", F.USER_ID },
							{ "username", F.USERNAME }, { "flg", "2" } }), });

	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {

			runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					recLen--;
					shengyu_time.setText("剩余：" + formatTime(recLen));
					if (recLen < 0) {
						timer.cancel();
						// 抢购时间结束，不可点击
						qianggou_price.setClickable(false);
						qianggou_price
								.setBackgroundResource(R.drawable.un_clickable);
					}
				}
			});
		}
	};

	public static String formatTime(Long ms) {
		// Integer ss = 1000;
		Integer ss = 1;
		Integer mi = ss * 60;
		Integer hh = mi * 60;
		Integer dd = hh * 24;

		Long day = ms / dd;
		Long hour = (ms - day * dd) / hh;
		Long minute = (ms - day * dd - hour * hh) / mi;
		Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		Long milliSecond = ms - day * dd - hour * hh - minute * mi - second
				* ss;

		StringBuffer sb = new StringBuffer();
		if (day > 0) {
			sb.append(day + "天");
		}
		if (hour > 0) {
			sb.append(hour + "小时");
		}
		if (minute > 0) {
			sb.append(minute + "分");
		}
		if (second > 0) {
			sb.append(second + "秒");
		}
		// if(milliSecond > 0) {
		// sb.append(milliSecond+"毫秒");
		// }
		return sb.toString();
	}
}
