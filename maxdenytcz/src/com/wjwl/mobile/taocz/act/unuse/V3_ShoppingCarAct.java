package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.base.Retn.Msg_Retn.Builder;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
//import com.tcz.apkfactory.data.Order.Msg_Order;
//import com.tcz.apkfactory.data.OrderList.Msg_OrderList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ShoppingCartAdapter;
//import com.wjwl.mobile.taocz.commons.Arith;
//import com.wjwl.mobile.taocz.dialog.OrderTypeConfirmationDialog;
//
///**
// * 查看、修改和删除购物车
// * 
// * @author Administrator
// * 
// */
//public class V3_ShoppingCarAct extends MActivity {
//
//	private ImageButton imgBtn_img;
//	private PageListView listview;
//	public ShoppingCartAdapter SAdapter;
//	@SuppressWarnings("unused")
//	private TextView txttotalPrice, cart_sum, cart_totalprice;
//	private View cart_bottom, cart_emp;
//	private boolean isEdit = false;
//	private Msg_CitemList2.Builder OrderMain; // 订单
//	public ArrayList<Msg_CitemList> orderList; // 订单列表
//	private ArrayList<Map<String, Object>> productNum; //
//	private PullReloadView prv;
//	public static ArrayList<Map<String, Object>> productNumTemp;
//	private Button bt_jiesuan, tocoll, tobuy;
//	private String itemcount, specid, flag;
//	String actfrom=null;
//	OrderTypeConfirmationDialog dialog;
//	Msg_CitemList2.Builder msg_Order = Msg_CitemList2.newBuilder();
//
//	@Override
//	public void dataLoad(int[] types) {
//		// TODO Auto-generated method stub
//		if (types == null) {
//			LoadShow = false;
//		} else {
//			LoadShow = true;
//		}
//		if (types == null || types[0] == 0) {
//			this.loadData(new Updateone[] { new Updateone("PLIST",
//					new String[][] { { "userid", F.USER_ID } }), });
//		} else if (types[0] == 1) {
//			this.loadData(new Updateone[] { new Updateone("PCATENUM",
//					new String[][] { { "userid", F.USER_ID }, { "flg", flag },
//							{ "specid", specid }, { "newamount", itemcount } }), });
//		} else if (types[0] == 2) {
//			this.loadData(new Updateone[] { new Updateone("PDEL",
//					new String[][] { { "userid", F.USER_ID }, { "flg", flag },
//							{ "specid", specid } }), });
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build == null && son.mgetmethod.equals("plist")) {
//			listview.setAdapter(null);
//			imgBtn_img.setVisibility(View.GONE);
//			cart_emp.setVisibility(View.VISIBLE);
//			cart_bottom.setVisibility(View.INVISIBLE);
//		}
//		if (son.build != null && son.mgetmethod.equals("plist")) {
//			imgBtn_img.setVisibility(View.VISIBLE);
//			OrderMain = (Msg_CitemList2.Builder) son.build;
//			orderList = new ArrayList<Msg_CitemList>(
//					OrderMain.getCitemlistList()); // 定义临时列表
//			SAdapter = new ShoppingCartAdapter(this, orderList);
//			listview.setAdapter(SAdapter);
//			txttotalPrice.setText(setTotalPric(orderList));
//			productNum = getProdutNum(orderList);
//			productNumTemp = productNum;
//			if (SAdapter.getCount() > 0) {
//				cart_emp.setVisibility(View.GONE);
//				cart_bottom.setVisibility(View.VISIBLE);
//			}
//			F.changecart(1);
//			isEdit = false;
//			imgBtn_img.setImageResource(R.drawable.btn_cart_edit);
//		} else if (son.build != null && son.mgetmethod.equals("pdel")) {
//			Msg_Retn.Builder retn = (Builder) son.build;
//			Toast.makeText(V3_ShoppingCarAct.this, retn.getErrorMsg(),
//					Toast.LENGTH_SHORT).show();
//			F.changecart(1);
//			if (SAdapter.getCount() == 0) {
//				cart_emp.setVisibility(View.VISIBLE);
//				cart_bottom.setVisibility(View.INVISIBLE);
//				isEdit = false;
//				imgBtn_img.setImageResource(R.drawable.btn_cart_edit);
//			}
//			SAdapter.NotifyDataSetChanged(true);
//		} else if (son.build != null && son.mgetmethod.equals("pcatenum")) {
//			F.changecart(1);
//			Msg_Retn.Builder retn = (Builder) son.build;
//			Toast.makeText(V3_ShoppingCarAct.this, retn.getErrorMsg(),
//					Toast.LENGTH_SHORT).show();
//			SAdapter.NotifyDataSetChanged(true);
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		// TODO Auto-generated method stub
//		LoadShow = false;
//		if (type == 2) {
//			String[] str = (String[]) (obj);
//			specid = str[0];
//			itemcount = str[1];
//			flag = str[4];
//			modifyProduct(str[2], str[3]);
//			SAdapter = new ShoppingCartAdapter(this, orderList);
//			listview.setAdapter(SAdapter);
//			dataLoad(new int[] { 1 });
//			if (orderList.size() == 0)
//				return;
//			SAdapter.NotifyDataSetChanged(true);
//		}
//		if (type == 1) {
//			String[] str = (String[]) (obj);
//			deleteProduct(str[0], str[1]);
//			specid = str[2];
//			flag = str[3];
//			dataLoad(new int[] { 2 });
//			SAdapter = new ShoppingCartAdapter(this, orderList);
//			listview.setAdapter(SAdapter);
//			if (orderList.size() == 0)
//				return;
//			SAdapter.NotifyDataSetChanged(true);
//
//		}
//	}
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.shoppingcart);
//		setId("V3_ShoppingCartAct");
//		imgBtn_img = (ImageButton) findViewById(R.shoppingcart.imgBtn_img);
//		txttotalPrice = (TextView) findViewById(R.shoppingcart.cart_totalprice);
//		listview = (PageListView) findViewById(R.shoppingcart.listview);
//		bt_jiesuan = (Button) findViewById(R.shoppingcart.btn_pay);
//		cart_emp = findViewById(R.shoppingcart.cart_emptey);
//		cart_bottom = findViewById(R.shoppingcart.cart_bottomlayout);
//		tobuy = (Button) findViewById(R.shoppingcart.bt_tobuy);
//		tocoll = (Button) findViewById(R.shoppingcart.bt_tocoll);
//		
//		actfrom=getIntent().getStringExtra("actfrom");
//
//		tobuy.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(actfrom!=null){
//					V3_ShoppingCarAct.this.finish();
//				}else{
//					Frame.HANDLES.sentAll("FrameAg", 1, R.frame.index);
//				}
//				
//			}
//		});
//
//		tocoll.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent3 = new Intent();
//				intent3.setClass(V3_ShoppingCarAct.this, FavoriteAg.class)
//						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//				V3_ShoppingCarAct.this.startActivity(intent3);
//			}
//		});
//
//		cart_bottom.setVisibility(View.INVISIBLE);
//		cart_emp.setVisibility(View.GONE);
//
//		cart_sum = (TextView) findViewById(R.shoppingcart.cart_sum);
//		cart_totalprice = (TextView) findViewById(R.shoppingcart.cart_totalprice);
//		bt_jiesuan.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				boolean istodia = false;
//
//				for (Msg_CitemList msg : SAdapter.getList()) {
//					if (!msg.getItemtype().equals("2")) {
//						istodia = true;// 没有支持活到付款的
//						break;
//					}
//				}
//
//				if (istodia) {
//					dialog = new OrderTypeConfirmationDialog(
//							V3_ShoppingCarAct.this);
//					dialog.show();
//				} else {
//					Intent i = new Intent();
//					i.putExtra("paytype", "4");
//					i.setClass(V3_ShoppingCarAct.this, OrderConfirmationAct.class);
//					V3_ShoppingCarAct.this.startActivity(i);
//				}
//			}
//
//		});
//		// dataLoadByDelay(new int[] { 0 });
//		imgBtn_img.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (productNumTemp == null || orderList == null)
//					return;
//
//				if (!isEdit) {
//					isEdit = true;
//					imgBtn_img.setImageResource(R.drawable.btn_cart_save);
//					SAdapter.NotifyDataSetChanged(isEdit);
//				} else {
//					for (int i = 0; i < productNumTemp.size(); i++) {
//						Map<String, Object> map = productNumTemp.get(i);
//						modifyProduct(map.get("businessId").toString(), map
//								.get("productId").toString());
//					}
//					isEdit = false;
//					imgBtn_img.setImageResource(R.drawable.btn_cart_edit);
//					SAdapter.NotifyDataSetChanged(isEdit);
//					msg_Order = saveShoppingCart();
//					SAdapter = new ShoppingCartAdapter(v.getContext(),
//							orderList);
//					listview.setAdapter(SAdapter);
//					txttotalPrice.setText(setTotalPric(orderList));
//				}
//			}
//		});
//		prv = (PullReloadView) findViewById(R.shoppingcart.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				LoadShow = true;
//				dataLoad(new int[] { 0 });
//			}
//		});
//		dataLoadByDelay(new int[] { 0 });
//	}
//
//	/**
//	 * 获取修改产品数量的购物车
//	 * 
//	 * @return
//	 */
//	public Msg_CitemList2.Builder saveShoppingCart() {
//		Msg_CitemList2.Builder saveOrderList = Msg_CitemList2.newBuilder();
//		// saveOrderList.setId(OrderMain.getId());
//		// saveOrderList.setUserId(OrderMain.getUserId());
//		for (int x = 0; x < orderList.size(); x++) {
//			Msg_CitemList msgorderlist = orderList.get(x);
//			Msg_CitemList.Builder listBuilder = Msg_CitemList.newBuilder();
//			listBuilder.setBusinessname(msgorderlist.getBusinessname());
//			listBuilder.setFreight(msgorderlist.getFreight());
//			listBuilder.setBusinessid(msgorderlist.getBusinessid());
//
//			for (int y = 0; y < msgorderlist.getCitemList().size(); y++) {
//				Msg_Citem.Builder build = Msg_Citem.newBuilder();
//				Msg_Citem product = msgorderlist.getCitemList().get(y);
//				build.setItemid(product.getItemimageurl());
//				build.setSpecid(product.getItemid());
//				build.setItemtitle(product.getItemtitle());
//				build.setItemprice(Arith.to2zero(product.getItemprice()));
//				if (product.getItemlimited().equals("")) {
//					build.setItemlimited(product.getOther1());
//				} else {
//					build.setItemlimited(product.getItemlimited());
//				}
//
//				build.setItemcount(product.getItemcount());
//				listBuilder.addCitem(product);
//			}
//			saveOrderList.addCitemlist(listBuilder);
//		}
//		return saveOrderList;
//	}
//
//	/**
//	 * 计算总价
//	 */
//	public String setTotalPric(ArrayList<Msg_CitemList> orderList) {
//		float totalPrice = 0;
//		int count = 0;
//
//		for (int i = 0; i < orderList.size(); i++) {
//			Msg_CitemList OrderList = orderList.get(i);
//			List<Msg_Citem> list = OrderList.getCitemList();
//			Msg_Citem map;
//			for (int j = 0; j < list.size(); j++) {
//				map = list.get(j);
//				if (map.getItemprice().equals("")) {
//					orderList.remove(j);
//				} else {
//					float price = Float.parseFloat(map.getItemprice());
//					int num = Integer.parseInt(map.getItemcount());
//					totalPrice += price * num;
//					count += num;
//				}
//			}
//		}
//		F.GOODSCOUNT = count;
//
//		// String parten = "#.##";
//		// DecimalFormat decimal = new DecimalFormat(parten);
//		String str = Arith.to2zero(totalPrice + "");
//		return str;
//	}
//
//	/**
//	 * 购物车中新增产品
//	 * 
//	 * @param busnissId
//	 * @param productId
//	 */
//	public void insertProduct(String busnissId, String productId) {
//
//	}
//
//	/**
//	 * 购物车中修改产品数量
//	 * 
//	 * @param busnissId
//	 * @param productId
//	 */
//	public void modifyProduct(String busnissId, String productId) {
//		int n = 0;
//		for (int i = 0; i < productNumTemp.size(); i++) {
//			if (productNumTemp.get(i).get("businessId").equals(busnissId)
//					&& productNumTemp.get(i).get("productId").equals(productId)) {
//				n = i;
//			}
//		}
//		Msg_Citem Product = getProduct(busnissId, productId);
//		Msg_Citem.Builder build = Msg_Citem.newBuilder();
//
//		build.setItemimageurl(Product.getItemimageurl());
//		build.setItemtitle(Product.getItemtitle());
//		build.setItemprice(Product.getItemprice());
//		build.setItemid(Product.getItemid());
//		build.setSpecid(Product.getSpecid());
//		build.setItemselltype(Product.getItemselltype());
//		build.setItemlimited(Product.getItemlimited());
//		build.setItemcount(productNumTemp.get(n).get("productNum").toString());
//		Product = build.build();
//		for (int i = 0; i < orderList.size(); i++) {
//			if (orderList.get(i).getBusinessid().equals(busnissId)) {
//				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
//					if (orderList.get(i).getCitemList().get(j).getItemid()
//							.equals(productId)) {
//						ArrayList<Msg_Citem> productlist = getOrderProductList(orderList
//								.get(i));
//						Msg_CitemList.Builder orderlist = Msg_CitemList
//								.newBuilder();
//						orderlist.setBusinessname(orderList.get(i)
//								.getBusinessname());
//						orderlist.setBusinessid(orderList.get(i)
//								.getBusinessid());
//						orderlist.setFreight(orderList.get(i).getFreight());
//						for (int h = 0; h < productlist.size(); h++) {
//							Msg_Citem.Builder ProductTemp;
//							if (productlist.get(h).getItemid()
//									.equals(Product.getItemid())) {
//								productlist.remove(h);
//								productlist.add(h, Product);
//							}
//							ProductTemp = productlist.get(h).toBuilder();
//							orderlist.addCitem(ProductTemp);
//						}
//						orderList.remove(i);
//						orderList.add(i, orderlist.build());
//					}
//				}
//			}
//		}
//		txttotalPrice.setText(setTotalPric(orderList));
//	}
//
//	/**
//	 * 获取购物车中单个商家的产品
//	 * 
//	 * @return
//	 */
//	public ArrayList<Msg_Citem> getOrderProductList(Msg_CitemList list) {
//		ArrayList<Msg_Citem> arr = new ArrayList<Msg_Citem>();
//		for (int i = 0; i < list.getCitemList().size(); i++) {
//			arr.add(list.getCitemList().get(i));
//		}
//		return arr;
//	}
//
//	/**
//	 * 购物车中删除产品
//	 * 
//	 * @param busnissId
//	 * @param productId
//	 */
//	public void deleteProduct(String busnissId, String productId) {
//		for (int i = 0; i < orderList.size(); i++) {
//			if (orderList.get(i).getBusinessid().equals(busnissId)) {
//				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
//					if (orderList.get(i).getCitemList().get(j).getItemid()
//							.equals(productId)) {
//						ArrayList<Msg_Citem> productlist = new ArrayList<Msg_Citem>(
//								orderList.get(i).getCitemList());
//						Msg_CitemList.Builder orderlist = Msg_CitemList
//								.newBuilder();
//						orderlist.setBusinessname(orderList.get(i)
//								.getBusinessname());
//						orderlist.setBusinessid(orderList.get(i)
//								.getBusinessid());
//						orderlist.setFreight(orderList.get(i).getFreight());
//						for (int h = 0; h < productlist.size(); h++) {
//							if (productlist.get(h).getItemid()
//									.equals(productId)) {
//								productlist.remove(h);
//								for (int x = 0; x < productNumTemp.size(); x++) {
//									if (productNumTemp.get(x).get("businessId")
//											.equals(busnissId)
//											&& productNumTemp.get(x)
//													.get("productId")
//													.equals(productId)) {
//										productNumTemp.remove(x);
//									}
//								}
//								h = h - 1;
//							} else {
//								orderlist.addCitem(productlist.get(h)
//										.toBuilder());
//							}
//						}
//						orderList.remove(i);
//						if (productlist.size() > 0) {
//							orderList.add(i, orderlist.build());
//						} else {
//							break;
//						}
//					}
//				}
//			}
//		}
//		txttotalPrice.setText(setTotalPric(orderList));
//	}
//
//	/**
//	 * 返回购物车中单个产品对象
//	 * 
//	 * @param busnissId
//	 * @param productId
//	 */
//	public Msg_Citem getProduct(String busnissId, String productId) {
//		Msg_Citem product = null;
//		for (int i = 0; i < orderList.size(); i++) {
//			if (orderList.get(i).getBusinessid().equals(busnissId)) {
//				for (int j = 0; j < orderList.get(i).getCitemList().size(); j++) {
//					if (orderList.get(i).getCitemList().get(j).getItemid()
//							.equals(productId)) {
//						product = orderList.get(i).getCitemList().get(j);
//					}
//				}
//			}
//		}
//		return product;
//	}
//
//	/**
//	 * 返回购物车中产品ID及对应数量
//	 * 
//	 * @param order
//	 * @return
//	 */
//	public ArrayList<Map<String, Object>> getProdutNum(
//			ArrayList<Msg_CitemList> order) {
//		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < order.size(); i++) {
//			for (int j = 0; j < order.get(i).getCitemList().size(); j++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("businessId", order.get(i).getBusinessid());
//				map.put("productId", order.get(i).getCitemList().get(j)
//						.getItemid());
//				map.put("specid", order.get(i).getCitemList().get(j)
//						.getSpecid());
//				map.put("flag", order.get(i).getCitemList().get(j)
//						.getItemselltype());
//				map.put("productNum", order.get(i).getCitemList().get(j)
//						.getItemcount());
//				arr.add(map);
//			}
//		}
//		return arr;
//	}
//
//	/**
//	 * 返回购物车中产品Id集合
//	 * 
//	 * @param list
//	 * @return
//	 */
//	public ArrayList<String> getProductIdList(Msg_CitemList list) {
//		ArrayList<String> arr = new ArrayList<String>();
//		for (int i = 0; i < list.getCitemList().size(); i++) {
//			Msg_Citem product = list.getCitemList().get(i);
//			arr.add(product.getItemid());
//		}
//		return arr;
//	}
//
//	/**
//	 * 返回购物车中商家Id集合
//	 * 
//	 * @param order
//	 * @return
//	 */
//	public ArrayList<String> getBusinessIdList(Msg_Order order) {
//		ArrayList<String> arr = new ArrayList<String>();
//		for (int i = 0; i < order.getOrderListList().size(); i++) {
//			Msg_OrderList business = order.getOrderListList().get(i);
//			arr.add(business.getId());
//		}
//		return arr;
//	}
//}
