package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CBill.Msg_CBill;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_BillCategory;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem.Builder;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.adapter.TakeOutBusinessMenuAdapter;
//import com.wjwl.mobile.taocz.dialog.Exitdialog;
//
//public class TakeOutBusinessMenuAct1 extends MActivity {
//
//	ListView lv;
//	ArrayList<Map<String, Object>> mData;
//	TakeOutBusinessMenuAdapter ODAdp;
//	TextView title;
//	public static TextView t_allCount;
//
//	public static int allCount;
//	Button bt_takeoutbox, bt_back;
//	private WmDB wmdb;
//	public static String businessid, businessname, startMoney, togoProduct,
//			togoTime, smallMoney, togoPlace, togoPhone, togoPhoneshort;
//	public static ArrayList<Map<String, Object>> productNumTemp;
//	public ArrayList<Msg_BillCategory> orderList;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutbusinessmenu1);
//		setId("TakeOutBussinessMenuAct1");
//
//		title = (TextView) findViewById(R.takeoutbusinessmenu1.head_title);
//		lv = (ListView) findViewById(R.takeoutbusinessmenu1.listview);
//
//		bt_takeoutbox = (Button) findViewById(R.takeoutbusinessmenu1.bt_takeoutbox);
//		bt_back = (Button) findViewById(R.takeoutbusinessmenu1.back);
//		bt_takeoutbox.setOnClickListener(new onClic());
//		bt_back.setOnClickListener(new onClic());
//		t_allCount = (TextView) findViewById(R.takeoutbusinessmenu1.count);
//		wmdb = new WmDB(TakeOutBusinessMenuAct1.this);
//		allCount = wmdb.getTakeOutAllCount();
//		businessid = getIntent().getStringExtra("businessid");
////		businessid = "1270";
//
//		// setTakeoutCount();// 获取当前餐盒的总数量
//		if (allCount > 0) {
//			t_allCount.setText("" + allCount);
//			t_allCount.setVisibility(View.VISIBLE);
//		} else {
//			t_allCount.setVisibility(View.GONE);
//		}
//
//		dataLoad(null);
//	}
//
//	public class onClic implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.takeoutbusinessmenu1.bt_takeoutbox:// 外卖盒
//				Intent i = new Intent();
//				// i.setClass(getApplication(), MyTakeOutBox.class);
//				i.setClass(getApplication(), TakeOutBoxAct.class);
//				startActivity(i);
//				TakeOutBusinessMenuAct1.this.finish();
//				break;
//			case R.takeoutbusinessmenu1.back:
//				TakeOutBusinessMenuAct1.this.finish();
//				break;
//			}
//
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMBILL",
//				new String[][] { { "businessid", businessid } }), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("wmbill")) {
//			Msg_CBill.Builder cbill = (Msg_CBill.Builder) son.build;
//
//			businessname = cbill.getCbusinessinfo().getBusinessname();
////			if(!businessname.equals("")){
//				title.setText(businessname);
//				startMoney = cbill.getCbusinessinfo().getWmStartmoney();//起送金额
//				togoProduct = cbill.getCbusinessinfo().getWmMajor();//
//				togoTime = cbill.getCbusinessinfo().getWmOpentime();
//				smallMoney = cbill.getCbusinessinfo().getWmLogisticsmoney();
//				togoPlace = cbill.getCbusinessinfo().getWmSendrange();
//				togoPhone = cbill.getCbusinessinfo().getWmOrdertel();
//				togoPhoneshort = cbill.getCbusinessinfo().getBusinessphone();
//
//				String itemid = "", itemcount = "";
//				List<Map<String, String>> maplist = wmdb.find(" BUSINESSID = '"
//						+ businessid + "'");
//				for (int i = 0; i < maplist.size(); i++) {
//					itemid = maplist.get(i).get("ITEMID");
//					itemcount = maplist.get(i).get("ITEMCOUNT");
//
//					for (int j = 0; j < cbill.getBillCategoryCount(); j++) {
//						for (int k = 0; k < cbill.getBillCategory(j)
//								.getBillitemCount(); k++) {
//							if (itemid.equals(cbill.getBillCategory(j)
//									.getBillitem(k).getBillitemid())) {
//								Builder billitem = Msg_Billitem.newBuilder();
//								billitem.setBillitemcount(itemcount);
//								billitem.setBillitemid(cbill.getBillCategory(j)
//										.getBillitem(k).getBillitemid());
//								billitem.setBillitemimage(cbill.getBillCategory(j)
//										.getBillitem(k).getBillitemimage());
//								billitem.setBilliteminfo(cbill.getBillCategory(j)
//										.getBillitem(k).getBilliteminfo());
//								billitem.setBillitemname(cbill.getBillCategory(j)
//										.getBillitem(k).getBillitemname());
//								billitem.setBillitemprice(cbill.getBillCategory(j)
//										.getBillitem(k).getBillitemprice());
//								cbill.getBillCategoryBuilder(j).setBillitem(k,
//										billitem);
//							}
//						}
//					}
//				}
//				mData = new ArrayList<Map<String, Object>>();
//				for (int i = 0; i < cbill.getBillCategoryCount(); i++) {
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("title", cbill.getBillCategory(i).getCategoryname());
//					map.put("typecount", cbill.getBillCategory(i)
//							.getCategorycount());
//					map.put("billitems", cbill.getBillCategory(i).getBillitemList());
//					map.put("categoryid", cbill.getBillCategory(i).getCategoryid());
//					mData.add(map);
//				}
//				if((mData.get(0).get("title")).equals("")&&(mData.get(0).get("categoryid")).equals("")&&(mData.get(0).get("typecount")).equals("")){
//					Toast.makeText(TakeOutBusinessMenuAct1.this, "该商家暂时没有菜单数据", Toast.LENGTH_SHORT).show();
//				}
//			else{
//				orderList = new ArrayList<Msg_BillCategory>(
//						cbill.getBillCategoryList());
//				productNumTemp = getProdutNum(orderList);
//				ODAdp = new TakeOutBusinessMenuAdapter(
//						TakeOutBusinessMenuAct1.this, mData);
//				lv.setAdapter(ODAdp);
//			}
//			}
//	}
//
//	public ArrayList<Map<String, Object>> getProdutNum(
//			ArrayList<Msg_BillCategory> order) {
//		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < order.size(); i++) {
//			for (int j = 0; j < order.get(i).getBillitemCount(); j++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("categoryId", order.get(i).getCategoryid());
//				map.put("productId", order.get(i).getBillitemList().get(j)
//						.getBillitemid());
//				map.put("productNum", order.get(i).getBillitemList().get(j)
//						.getBillitemcount() == null
//						|| order.get(i).getBillitemList().get(j)
//								.getBillitemcount().equals("") ? "0" : order
//						.get(i).getBillitemList().get(j).getBillitemcount());
//				arr.add(map);
//			}
//		}
//		return arr;
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			WmDB wmdb;
//			wmdb = new WmDB(TakeOutBusinessMenuAct1.this);
//			wmdb.deleteall();
//			this.finish();
//			return true;
//
//		}
//		return false;
//	}
//}
