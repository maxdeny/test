package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.base.Retn.Msg_Retn.Builder;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Order.Msg_Order;
import com.tcz.apkfactory.data.OrderList.Msg_OrderList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ShoppingCartAdapter;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.dialog.OrderTypeConfirmationDialog;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

/**
 * 查看、修改和删除购物车
 * 
 * @author Administrator
 * 
 */
public class ShoppingCartAct extends MActivity {

	TczV3_HeadLayout headlayout;
	private PageListView listview;
	public ShoppingCartAdapter SAdapter;
	@SuppressWarnings("unused")
	private TextView txttotalPrice, cart_sum, cart_totalprice;
	private View cart_bottom, cart_emp;
	private boolean isEdit = false, fromchange = false;
	private Msg_CitemList2.Builder OrderMain; // 订单
	public ArrayList<Msg_CitemList.Builder> orderList; // 订单列表
	private ArrayList<Map<String, Object>> productNum; //
	private PullReloadView prv;
	public static ArrayList<Map<String, Object>> productNumTemp;
	private Button bt_jiesuan, tocoll, tobuy;
	private String itemcount, specid, flag;
	String actfrom = "", commitdata = "";
	OrderTypeConfirmationDialog dialog;
//	Msg_CitemList2.Builder msg_Order = Msg_CitemList2.newBuilder();
	CheckBox allitemcheck;
	private NewMsgReceiver newMsgReceiver;
	boolean allcheckstate=true;
	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		if (types == null) {
			LoadShow = false;
		} else {
			LoadShow = true;
		}
		if (types == null || types[0] == 0) {
			this.loadData(new Updateone[] { new Updateone("V3_PLIST",
					new String[][] { { "userid", F.USER_ID } }), });
		} else if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("PCATENUM",
					new String[][] { { "userid", F.USER_ID }, { "flg", flag },
							{ "specid", specid }, { "newamount", itemcount } }), });
		} else if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("PDEL",
					new String[][] { { "userid", F.USER_ID }, { "flg", flag },
							{ "specid", specid } }), });
		} else if (types[0] == 3) {
			this.loadData(new Updateone[] { new Updateone("V3_SELECTGOODS",
					new String[][] { { "userid", F.USER_ID },
							{ "data", commitdata } }), });
			commitdata = "";
		}
	}
	
	

	@Override
	protected void resume() {
		// TODO Auto-generated method stub
		if(F.USER_ID.length()>0&&Frame.HANDLES.get("ShoppingCartAct").size()>0){
			fromchange=false;
			dataLoadByDelay(new int[] { 0 });
		}
		
	}
	
	
	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}



	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("V3_PLIST")) {
			listview.setAdapter(null);
			headlayout.setRightButton3Gone();
			cart_emp.setVisibility(View.VISIBLE);
			cart_bottom.setVisibility(View.INVISIBLE);
		}
		if (son.build != null && son.mgetmethod.equals("V3_PLIST")) {
			headlayout.setRightButton3VISIBLE();
			OrderMain = (Msg_CitemList2.Builder) son.build;
			orderList = new ArrayList<Msg_CitemList.Builder>(
					OrderMain.getCitemlistBuilderList()); // 定义临时列表

			for (int i = 0; i < orderList.size(); i++) {
				orderList.get(i).setShippingFee("false");
				for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
					orderList.get(i).getCitemBuilder(k).setItemlevel("false");
				}
			}

			if(fromchange){
//				SAdapter.NotifyDataSetChanged(isEdit,orderList);
				SAdapter.NotifyDataSetChanged(true,orderList);
			}else{
				SAdapter = new ShoppingCartAdapter(this, orderList);
				listview.setAdapter(SAdapter);
				SAdapter.NotifyDataSetChanged(false,orderList);
			}
			
			txttotalPrice.setText(setTotalPric(orderList));
			productNum = getProdutNum(orderList);
			productNumTemp = productNum;
			if (SAdapter.getCount() > 0) {
				cart_emp.setVisibility(View.GONE);
				cart_bottom.setVisibility(View.VISIBLE);
			}
//			F.changecart(1);
			if (fromchange) {
				isEdit = true;
				headlayout.setRightButton3Text("完成");
			} else {
				isEdit = false;
				headlayout.setRightButton3Text("编辑");
			}
		

		} else if (son.build != null && son.mgetmethod.equals("pdel")) {
			Msg_Retn.Builder retn = (Builder) son.build;
			Toast.makeText(ShoppingCartAct.this, retn.getErrorMsg(),
					Toast.LENGTH_SHORT).show();
			F.changecart(1);
			if (SAdapter.getCount() == 0) {
				cart_emp.setVisibility(View.VISIBLE);
				cart_bottom.setVisibility(View.INVISIBLE);
				isEdit = false;
				headlayout.setRightButton3Text("编辑");
			}
			fromchange = true;
			SAdapter.NotifyDataSetChanged(true,orderList);
			dataLoad(new int[] { 0 });
		} else if (son.build != null && son.mgetmethod.equals("pcatenum")) {
//			F.changecart(1);
			Msg_Retn.Builder retn = (Builder) son.build;
			Toast.makeText(ShoppingCartAct.this, retn.getErrorMsg(),
					Toast.LENGTH_SHORT).show();
//			SAdapter.NotifyDataSetChanged(true,orderList);
			fromchange = true;
			dataLoad(new int[] { 0 });
		} else if (son.build != null && son.mgetmethod.equals("V3_SELECTGOODS")) {
			Msg_Retn.Builder retn = (Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// Toast.makeText(ShoppingCartAct.this, retn.getErrorMsg(),
				// Toast.LENGTH_SHORT).show();
				Intent i = new Intent();
				i.setClass(ShoppingCartAct.this,
						TczV3_OrderConfirmationAct.class);// V3_WriteOrderAct
				ShoppingCartAct.this.startActivity(i);
				MobclickAgent.onEvent(ShoppingCartAct.this, "SettleAccount");
				
			} else {
				Toast.makeText(ShoppingCartAct.this, retn.getErrorMsg(),
						Toast.LENGTH_SHORT).show();
			}

		}
		prv.refreshComplete();
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		LoadShow = false;
		if (type == 2) {
			String[] str = (String[]) (obj);
			specid = str[0];
			itemcount = str[1];
			flag = str[4];
			modifyProduct(str[2], str[3]);
			SAdapter = new ShoppingCartAdapter(this, orderList);
			listview.setAdapter(SAdapter);
			dataLoad(new int[] { 1 });
//			if (orderList.size() == 0)
//				return;
//			SAdapter.NotifyDataSetChanged(true,orderList);
		}
		if (type == 1) {
			String[] str = (String[]) (obj);
			deleteProduct(str[0], str[1]);
			specid = str[2];
			flag = str[3];
			dataLoad(new int[] { 2 });
//			SAdapter = new ShoppingCartAdapter(this, orderList);
//			listview.setAdapter(SAdapter);
//			if (orderList.size() == 0)
//				return;
//			SAdapter.NotifyDataSetChanged(true,orderList);
		}
		if (type == 3) {
			listview.setAdapter(null);
			headlayout.setRightButton3Gone();
			cart_emp.setVisibility(View.VISIBLE);
			cart_bottom.setVisibility(View.INVISIBLE);
		}

		if (type == 100) {
			String[] str = (String[]) (obj);// null
			specid = str[0];
			itemcount = str[1];
			flag = str[4];
			modifyProduct(str[2], str[3]);
			SAdapter = new ShoppingCartAdapter(this, orderList);
			listview.setAdapter(SAdapter);
			dataLoad(new int[] { 1 });
			if (orderList.size() == 0)
				return;
//			if (fromchange) {
//				isEdit = true;
//				headlayout.setRightButton3Text("完成");
//			} else {
//				isEdit = false;
//				headlayout.setRightButton3Text("编辑");
//			}
//			SAdapter.NotifyDataSetChanged(true,orderList);
		}
		if (type == 101) {
			String[] str = (String[]) (obj);
			for (int i = 0; i < orderList.size(); i++) {
				if (orderList.get(i).getBusinessid().equals(str[2])) {
					if (str[1].equals("true")) {
						orderList.get(i).setShippingFee("true");
					} else {
						orderList.get(i).setShippingFee("false");
					}
					for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
						if (str[0].equals(orderList.get(i).getCitem(k)
								.getSpecid())) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel(str[1]);
							break;
						}
					}
					SAdapter.NotifyDataSetChanged(false,orderList);
					headlayout.setRightButton3Text("编辑");
				}
			}
			txttotalPrice.setText(setTotalPric(orderList));
			for (int i = 0; i < orderList.size(); i++) {
				if(orderList.get(i).getShippingFee().equals("false")){
					allcheckstate=false;
					break;
				}
				else{
					allcheckstate=true;
				}
			}
			if(allcheckstate){
				allitemcheck.setChecked(true);
				allcheckstate=true;
			}
			else{
				allitemcheck.setChecked(false);
			}
		}
		if (type == 102) {
			String[] str = (String[]) (obj);
			String checkall = "";
			for (int i = 0; i < orderList.size(); i++) {
				if (orderList.get(i).getBusinessid().equals(str[2])) {
					for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
						if (str[0].equals(orderList.get(i).getCitem(k)
								.getSpecid())) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel(str[1]);
						}
						checkall = checkall
								+ orderList.get(i).getCitemBuilder(k)
										.getItemlevel();
					}
					if (checkall.indexOf("false") == -1) {
						orderList.get(i).setShippingFee("true");
					} else {
						orderList.get(i).setShippingFee("false");
					}
				}
			}
			SAdapter.NotifyDataSetChanged(false,orderList);
			headlayout.setRightButton3Text("编辑");
			txttotalPrice.setText(setTotalPric(orderList));
			for (int i = 0; i < orderList.size(); i++) {
				if(orderList.get(i).getShippingFee().equals("false")){
					allcheckstate=false;
					break;
				}else{
					allcheckstate=true;
				}
			}
			if(allcheckstate){
				allitemcheck.setChecked(true);
				allcheckstate=true;
			}else{
				allitemcheck.setChecked(false);
			}
		}
		if (type == 103) {//TczV3_GoodsListAct
			finish();
		}
	}

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.shoppingcart);
		setId("ShoppingCartAct");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("我的购物车");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShoppingCartAct.this.finish();
			}
		});
		if (getIntent().getStringExtra("actfrom").equals("FrameAg"))
			headlayout.setLeftGone();
		else
		headlayout.setLeftVisible();
		txttotalPrice = (TextView) findViewById(R.shoppingcart.cart_totalprice);
		listview = (PageListView) findViewById(R.shoppingcart.listview);
		bt_jiesuan = (Button) findViewById(R.shoppingcart.btn_pay);
		cart_emp = findViewById(R.shoppingcart.cart_emptey);
		cart_bottom = findViewById(R.shoppingcart.cart_bottomlayout);
		tobuy = (Button) findViewById(R.shoppingcart.bt_tobuy);
		tocoll = (Button) findViewById(R.shoppingcart.bt_tocoll);
		allitemcheck = (CheckBox) findViewById(R.shoppingcart.allitemcheck);
		actfrom = getIntent().getStringExtra("actfrom");
		allitemcheck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(allitemcheck.isChecked()){
					for (int i = 0; i < orderList.size(); i++) {
						orderList.get(i).setShippingFee("true");
						for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel("true");
						}
					}
				}
				else{
					for (int i = 0; i < orderList.size(); i++) {
						orderList.get(i).setShippingFee("false");
						for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel("false");
						}
					}
				}
				SAdapter.NotifyDataSetChanged(false,orderList);
				txttotalPrice.setText(setTotalPric(orderList));
			}
		});

		allitemcheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					if(allcheckstate)
					for (int i = 0; i < orderList.size(); i++) {
						orderList.get(i).setShippingFee("true");
						for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel("true");
						}
					}
				} else {
					if(allcheckstate)
					for (int i = 0; i < orderList.size(); i++) {
						orderList.get(i).setShippingFee("false");
						for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
							orderList.get(i).getCitemBuilder(k)
									.setItemlevel("false");
						}
					}
				}
				SAdapter.NotifyDataSetChanged(false,orderList);
				txttotalPrice.setText(setTotalPric(orderList));
				allcheckstate=true;
			}
		});
		tobuy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (actfrom.equals("FrameAg")) {
					Frame.HANDLES.sentAll("FrameAg", 1, R.frame.homeindex);
				} else {
					ShoppingCartAct.this.finish();
				}

			}
		});

		tocoll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(ShoppingCartAct.this, "ShoppingCartAct",
							"com.wjwl.mobile.taocz.act.FavoriteShopAct", 1);
				} else {
					Intent intent3 = new Intent();
					intent3.setClass(ShoppingCartAct.this,
							FavoriteShopAct.class).addFlags(
							Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent3);
				}

				// Intent intent3 = new Intent();
				// intent3.setClass(ShoppingCartAct.this, FavoriteAg.class)
				// .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				// ShoppingCartAct.this.startActivity(intent3);
			}
		});

		cart_bottom.setVisibility(View.INVISIBLE);
		cart_emp.setVisibility(View.GONE);

		cart_sum = (TextView) findViewById(R.shoppingcart.cart_sum);
		cart_totalprice = (TextView) findViewById(R.shoppingcart.cart_totalprice);
		bt_jiesuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// boolean istodia = false;
				//
				// for (Msg_CitemList msg : SAdapter.getList()) {
				// if (!msg.getItemtype().equals("2")) {
				// istodia = true;// 没有支持货到付款的
				// break;
				// }
				// }
				//
				// if (istodia) {
				// dialog = new OrderTypeConfirmationDialog(
				// ShoppingCartAct.this);
				// dialog.show();
				// } else {

				if (!txttotalPrice.getText().equals("￥0")) {
					StringBuffer sb = new StringBuffer();
					sb.append("[");
					for (int i = 0; i < orderList.size(); i++) {
						for (int k = 0; k < orderList.get(i).getCitemCount(); k++) {
							sb.append("{\"selected\":");
							sb.append("\""
									+ orderList.get(i).getCitemBuilder(k)
											.getItemlevel() + "\",");
							sb.append("\"specID\":");
							sb.append("\""
									+ orderList.get(i).getCitemBuilder(k)
											.getSpecid() + "\",");
							sb.append("\"flg\":");
							sb.append("\""
									+ orderList.get(i).getCitemBuilder(k)
											.getItemselltype() + "\"");
							sb.append("},");
						}
					}
					commitdata = (sb.subSequence(0, sb.length() - 1)).toString();
					commitdata = commitdata + "]";
					// Toast.makeText(ShoppingCartAct.this, commitdata,
					// Toast.LENGTH_LONG).show();
					sb = null;
					dataLoad(new int[] { 3 });
				} else {
					Toast.makeText(ShoppingCartAct.this, "请选择要结算的商品~",Toast.LENGTH_SHORT).show();
				}
			}

		});
		// dataLoadByDelay(new int[] { 0 });
		headlayout.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (productNumTemp == null || orderList == null)
					return;
				fromchange = false;
				if (!isEdit) {
					isEdit = true;
					headlayout.setRightButton3Text("完成");// btn_cart_save
					SAdapter.NotifyDataSetChanged(isEdit,orderList);
				} else {
//					for (int i = 0; i < productNumTemp.size(); i++) {
//						Map<String, Object> map = productNumTemp.get(i);
//						modifyProduct(map.get("businessId").toString(), map.get("productId").toString());
//					}
					isEdit = false;
					headlayout.setRightButton3Text("编辑");// btn_cart_save
				    saveShoppingCart();
					SAdapter.NotifyDataSetChanged(isEdit,orderList);
//					SAdapter = new ShoppingCartAdapter(v.getContext(),
//							orderList);
//					listview.setAdapter(SAdapter);
					txttotalPrice.setText(setTotalPric(orderList));
				}
			}
		});
		prv = (PullReloadView) findViewById(R.shoppingcart.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				LoadShow = true;
				dataLoad(new int[] { 0 });
				allitemcheck.setChecked(false);
			}
		});
		dataLoadByDelay(new int[] { 0 });
		
		// 新消息监听
		 newMsgReceiver = new NewMsgReceiver();
		 registerReceiver(newMsgReceiver, new IntentFilter("Receiver_ShoppingCartAct"));
				 
	}

	/**
	 * 获取修改产品数量的购物车
	 * 
	 * @return
	 */
	public Msg_CitemList2.Builder saveShoppingCart() {
		Msg_CitemList2.Builder saveOrderList = Msg_CitemList2.newBuilder();
		// saveOrderList.setId(OrderMain.getId());
		// saveOrderList.setUserId(OrderMain.getUserId());
		for (int x = 0; x < orderList.size(); x++) {
			Msg_CitemList.Builder msgorderlist = orderList.get(x);
			Msg_CitemList.Builder listBuilder = Msg_CitemList.newBuilder();
			listBuilder.setBusinessname(msgorderlist.getBusinessname());
			listBuilder.setFreight(msgorderlist.getFreight());
			listBuilder.setBusinessid(msgorderlist.getBusinessid());
			listBuilder.setItemtype(msgorderlist.getItemtype());// 配送方式
			listBuilder.setShippingFee(msgorderlist.getShippingFee());// 选中状态
			for (int y = 0; y < msgorderlist.getCitemList().size(); y++) {
				Msg_Citem.Builder build = Msg_Citem.newBuilder();
				Msg_Citem product = msgorderlist.getCitemList().get(y);
				build.setItemid(product.getItemimageurl());
				build.setSpecid(product.getItemid());
				build.setItemtype(product.getItemtype());
				build.setItemlevel(product.getItemlevel());// 选中状态
				build.setItemtitle(product.getItemtitle());
				build.setItemprice(Arith.to2zero(product.getItemprice()));
				if (product.getItemlimited().equals("")) {
					build.setItemlimited(product.getOther1());
				} else {
					build.setItemlimited(product.getItemlimited());
				}

				build.setItemcount(product.getItemcount());
				listBuilder.addCitem(product);
			}
			saveOrderList.addCitemlist(listBuilder);
		}
		return saveOrderList;
	}

	/**
	 * 计算总价
	 */
	public String setTotalPric(ArrayList<Msg_CitemList.Builder> orderList) {
		float totalPrice = 0;
		int count = 0;

		for (int i = 0; i < orderList.size(); i++) {
			Msg_CitemList.Builder OrderList = orderList.get(i);
			List<Msg_Citem> list = OrderList.getCitemList();
			Msg_Citem map;
			for (int j = 0; j < list.size(); j++) {
				map = list.get(j);
				if (map.getItemprice().equals("")) {
					orderList.remove(j);
				} else {
					if (map.getItemlevel().equals("true")) {
						float price = Float.parseFloat(map.getItemprice());
						int num = Integer.parseInt(map.getItemcount());
						totalPrice += price * num;
						count += num;
					}
				}
			}
		}
		F.GOODSCOUNT = count;
//		F.changecart(1);
		// String parten = "#.##";
		// DecimalFormat decimal = new DecimalFormat(parten);
		String str = Arith.to2zero("" + totalPrice);
		return "￥" + str;
	}

	/**
	 * 购物车中新增产品
	 * 
	 * @param busnissId
	 * @param productId
	 */
	public void insertProduct(String busnissId, String productId) {

	}

	/**
	 * 购物车中修改产品数量
	 * 
	 * @param busnissId
	 * @param productId
	 */
	public void modifyProduct(String busnissId, String productId) {
		int n = 0;
		for (int i = 0; i < productNumTemp.size(); i++) {
			if (productNumTemp.get(i).get("businessId").equals(busnissId)
					&& productNumTemp.get(i).get("productId").equals(productId)) {
				n = i;
			}
		}
		Msg_Citem.Builder Product = getProduct(busnissId, productId);
		Msg_Citem.Builder build = Msg_Citem.newBuilder();
		if(Product!=null){
			build.setItemimageurl(Product.getItemimageurl());
			build.setItemtitle(Product.getItemtitle());
			build.setItemprice(Product.getItemprice());
			build.setItemid(Product.getItemid());
			build.setSpecid(Product.getSpecid());
			build.setItemtype(Product.getItemtype());// 配送信息
			build.setItemlevel(Product.getItemlevel());// 选中状态
			build.setItemselltype(Product.getItemselltype());
			build.setItemlimited(Product.getItemlimited());
			build.setItemcount(productNumTemp.get(n).get("productNum").toString());
			Product = build;
		}
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getBusinessid().equals(busnissId)) {
				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
					if (orderList.get(i).getCitemList().get(j).getItemid()
							.equals(productId)) {
						ArrayList<Msg_Citem.Builder> productlist = getOrderProductList(orderList
								.get(i));
						Msg_CitemList.Builder inorderlist = Msg_CitemList
								.newBuilder();
						inorderlist.setBusinessname(orderList.get(i)
								.getBusinessname());
						inorderlist.setBusinessid(orderList.get(i)
								.getBusinessid());
						inorderlist.setFreight(orderList.get(i).getFreight());
						inorderlist.setItemtype(orderList.get(i).getItemtype());// 配送方式
						inorderlist.setShippingFee(orderList.get(i)
								.getShippingFee());// 选中状态
						for (int h = 0; h < productlist.size(); h++) {
							Msg_Citem.Builder ProductTemp;
							if (productlist.get(h).getItemid()
									.equals(Product.getItemid())) {
								productlist.remove(h);
								productlist.add(h, Product);
							}
							ProductTemp = productlist.get(h);
							inorderlist.addCitem(ProductTemp);
						}
						orderList.remove(i);
						orderList.add(i, inorderlist);
					}
				}
			}
		}
		txttotalPrice.setText(setTotalPric(orderList));
	}

	/**
	 * 获取购物车中单个商家的产品
	 * 
	 * @return
	 */
	public ArrayList<Msg_Citem.Builder> getOrderProductList(
			Msg_CitemList.Builder list) {
		ArrayList<Msg_Citem.Builder> arr = new ArrayList<Msg_Citem.Builder>();
		for (int i = 0; i < list.getCitemList().size(); i++) {
			arr.add(list.getCitemBuilderList().get(i));
		}
		return arr;
	}

	/**
	 * 购物车中删除产品
	 * 
	 * @param busnissId
	 * @param productId
	 */
	public void deleteProduct(String busnissId, String productId) {
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getBusinessid().equals(busnissId)) {
				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
					if (orderList.get(i).getCitemList().get(j).getItemid()
							.equals(productId)) {
						ArrayList<Msg_Citem> productlist = new ArrayList<Msg_Citem>(
								orderList.get(i).getCitemList());
						Msg_CitemList.Builder inorderlist = Msg_CitemList
								.newBuilder();
						inorderlist.setBusinessname(orderList.get(i)
								.getBusinessname());
						inorderlist.setBusinessid(orderList.get(i)
								.getBusinessid());
						inorderlist.setItemtype(orderList.get(i).getItemtype());// 配送信息
						inorderlist.setShippingFee(orderList.get(i)
								.getShippingFee());// 选中状态
						inorderlist.setFreight(orderList.get(i).getFreight());
						for (int h = 0; h < productlist.size(); h++) {
							if (productlist.get(h).getItemid()
									.equals(productId)) {
								productlist.remove(h);
								for (int x = 0; x < productNumTemp.size(); x++) {
									if (productNumTemp.get(x).get("businessId")
											.equals(busnissId)
											&& productNumTemp.get(x)
													.get("productId")
													.equals(productId)) {
										productNumTemp.remove(x);
									}
								}
								h = h - 1;
							} else {
								inorderlist.addCitem(productlist.get(h)
										.toBuilder());
							}
						}
						orderList.remove(i);
						if (productlist.size() > 0) {
							orderList.add(i, inorderlist);
						} else {
							break;
						}
					}
				}
			}
		}
		txttotalPrice.setText(setTotalPric(orderList));
	}

	/**
	 * 返回购物车中单个产品对象
	 * 
	 * @param busnissId
	 * @param productId
	 */
	public Msg_Citem.Builder getProduct(String busnissId, String productId) {
		Msg_Citem.Builder product = null;
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getBusinessid().equals(busnissId)) {
				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
					if (orderList.get(i).getCitemList().get(j).getItemid()
							.equals(productId)) {
						product = orderList.get(i).getCitemBuilderList().get(j);
					}
				}
			}
		}
		return product;
	}

	/**
	 * 返回购物车中产品ID及对应数量
	 * 
	 * @param order
	 * @return
	 */
	public ArrayList<Map<String, Object>> getProdutNum(
			ArrayList<Msg_CitemList.Builder> order) {
		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < order.size(); i++) {
			for (int j = 0; j < order.get(i).getCitemList().size(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("businessId", order.get(i).getBusinessid());
				map.put("productId", order.get(i).getCitemList().get(j)
						.getItemid());
				map.put("specid", order.get(i).getCitemList().get(j)
						.getSpecid());
				map.put("flag", order.get(i).getCitemList().get(j)
						.getItemselltype());
				map.put("productNum", order.get(i).getCitemList().get(j)
						.getItemcount());
				arr.add(map);
			}
		}
		return arr;
	}

	/**
	 * 返回购物车中产品Id集合
	 * 
	 * @param list
	 * @return
	 */
	public ArrayList<String> getProductIdList(Msg_CitemList list) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < list.getCitemList().size(); i++) {
			Msg_Citem product = list.getCitemList().get(i);
			arr.add(product.getItemid());
		}
		return arr;
	}

	/**
	 * 返回购物车中商家Id集合
	 * 
	 * @param order
	 * @return
	 */
	public ArrayList<String> getBusinessIdList(Msg_Order order) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int i = 0; i < order.getOrderListList().size(); i++) {
			Msg_OrderList business = order.getOrderListList().get(i);
			arr.add(business.getId());
		}
		return arr;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ShoppingCartPage");
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ShoppingCartPage");
		MobclickAgent.onPause(this);
	}
	
	// 消息监听
	 private class NewMsgReceiver extends BroadcastReceiver {
	 @Override
	 public void onReceive(Context context, Intent intent) {
		    fromchange=true;
			specid = intent.getStringExtra("specid");
			itemcount =intent.getStringExtra("num");
			flag = intent.getStringExtra("flag");
			if(orderList!=null){
				modifyProduct(intent.getStringExtra("businessId"), intent.getStringExtra("productId"));
				dataLoad(new int[] { 1 });
			}
	 }
	 }
}
