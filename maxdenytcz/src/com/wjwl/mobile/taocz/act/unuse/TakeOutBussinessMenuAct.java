package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CBill.Msg_CBill;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_BillCategory;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem.Builder;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.adapter.TakeOutBusinessMenuAdapter;
//import com.wjwl.mobile.taocz.dialog.TakeOutShowProductInfoDialog;
//import com.wjwl.mobile.taocz.widget.head_takeoutbusinessmenu;
//
//public class TakeOutBussinessMenuAct extends MActivity {
//	ListView lv;
//	ArrayList<Map<String, Object>> mData;
//	TakeOutBusinessMenuAdapter ODAdp;
//	TextView title;
//	public static TextView t_allCount;
//	head_takeoutbusinessmenu head_item;
//	public static int allCount;
//	Button bt_1, bt_2, bt_3, bt_4;
//	private WmDB wmdb;
//	public static String businessid,businessname,startMoney,togoProduct,togoTime,smallMoney,togoPlace,togoPhone ,togoPhoneshort;
//	public static ArrayList<Map<String, Object>> productNumTemp;
//	public ArrayList<Msg_BillCategory> orderList;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutbusinessmenu);
//		setId("TakeOutBussinessMenuAct");
//	
//		title = (TextView) findViewById(R.takeoutbusinessmenu.head_title);
//		lv = (ListView) findViewById(R.takeoutbusinessmenu.listview);
//		bt_1 = (Button) findViewById(R.takeoutbusinessmenu.bt_1);
//		bt_2 = (Button) findViewById(R.takeoutbusinessmenu.bt_2);
//		bt_3 = (Button) findViewById(R.takeoutbusinessmenu.bt_3);
//		bt_4 = (Button) findViewById(R.takeoutbusinessmenu.bt_4);
//		bt_1.setOnClickListener(new onClic());
//		bt_2.setOnClickListener(new onClic());
//		bt_3.setOnClickListener(new onClic());
//		bt_4.setOnClickListener(new onClic());
//		t_allCount = (TextView) findViewById(R.takeoutbusinessmenu.count);
//		wmdb = new WmDB(TakeOutBussinessMenuAct.this);
//		allCount = wmdb.getTakeOutAllCount();
//		businessid=getIntent().getStringExtra("businessid");
//		
//	//	setTakeoutCount();// 获取当前餐盒的总数量
//		if (allCount > 0) {
//			t_allCount.setText("" + allCount);
//			t_allCount.setVisibility(View.VISIBLE);
//		} else {
//			t_allCount.setVisibility(View.GONE);
//		}
//		head_item = new head_takeoutbusinessmenu(TakeOutBussinessMenuAct.this);
//		
//
//		dataLoad(null);
//	}
//
//	public class onClic implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.takeoutbusinessmenu.bt_1:// 送餐信息
//				TakeOutShowProductInfoDialog dialog = new TakeOutShowProductInfoDialog(
//						TakeOutBussinessMenuAct.this);
//				dialog.show();
//				break;
//			case R.takeoutbusinessmenu.bt_2:// 拨打电话
//				Intent intent = new Intent(Intent.ACTION_DIAL,
//						Uri.parse("tel:"+togoPhone));
//				startActivity(intent);
//
//				break;
//			case R.takeoutbusinessmenu.bt_3:// 外卖盒
//				Intent i = new Intent();
//				i.setClass(getApplication(), TakeOutBoxAct.class);
//				startActivity(i);
//				TakeOutBussinessMenuAct.this.finish();
//				break;
//			case R.takeoutbusinessmenu.bt_4:// 收集令
//				if (F.USER_ID == null || F.USER_ID.length() == 0) {
//					F.toLogin(TakeOutBussinessMenuAct.this,"TakeOutBussinessMenuAct" ,"CiytRecruitAct", 0);
//				}
//				else{
//				Intent i4 = new Intent();
//				i4.setClass(TakeOutBussinessMenuAct.this, CiytRecruitAct.class);
//				startActivity(i4);
//				}
//				break;
//			}
//
//		}
//	}
//
//	
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMBILL",new String[][] {{"businessid",businessid}}), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("wmbill")){
//			Msg_CBill.Builder cbill = (Msg_CBill.Builder) son.build;
//			head_item.setBusinessName(cbill.getCbusinessinfo().getBusinessname());
//			head_item.setTime(cbill.getCbusinessinfo().getWmOpentime());
//			head_item.setAddress(cbill.getCbusinessinfo().getBusinessaddress());
//			head_item.setImg(cbill.getCbusinessinfo().getBusinessimage());			
//			businessname=cbill.getCbusinessinfo().getBusinessname();
//			title.setText(businessname);
//			startMoney=cbill.getCbusinessinfo().getWmStartmoney();
//			togoProduct=cbill.getCbusinessinfo().getWmMajor();
//			togoTime=cbill.getCbusinessinfo().getWmOpentime();
//			smallMoney=cbill.getCbusinessinfo().getWmLogisticsmoney();
//			togoPlace=cbill.getCbusinessinfo().getWmSendrange();
//			togoPhone=cbill.getCbusinessinfo().getWmOrdertel();
//			togoPhoneshort=cbill.getCbusinessinfo().getBusinessphone();
//			lv.addHeaderView(head_item, null, false);
//			
//			
//			String itemid="",itemcount="";
//			List<Map<String,String>> maplist=wmdb.find(" BUSINESSID = '"+businessid+"'");
//			for(int i=0;i<maplist.size();i++){
//				itemid=maplist.get(i).get("ITEMID");
//				itemcount=maplist.get(i).get("ITEMCOUNT");
//			
//			for(int j=0;j<cbill.getBillCategoryCount();j++){
//				for(int k=0;k<cbill.getBillCategory(j).getBillitemCount();k++){
//					if(itemid.equals(cbill.getBillCategory(j).getBillitem(k).getBillitemid())){
//						Builder billitem = Msg_Billitem.newBuilder();
//						billitem.setBillitemcount(itemcount);
//						billitem.setBillitemid(cbill.getBillCategory(j).getBillitem(k).getBillitemid());
//						billitem.setBillitemimage(cbill.getBillCategory(j).getBillitem(k).getBillitemimage());
//						billitem.setBilliteminfo(cbill.getBillCategory(j).getBillitem(k).getBilliteminfo());
//						billitem.setBillitemname(cbill.getBillCategory(j).getBillitem(k).getBillitemname());
//						billitem.setBillitemprice(cbill.getBillCategory(j).getBillitem(k).getBillitemprice());
//						cbill.getBillCategoryBuilder(j).setBillitem(k, billitem);
//					}
//				}
//			}
//			}
//			mData = new ArrayList<Map<String, Object>>();
//			for(int i=0;i<cbill.getBillCategoryCount();i++){
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("title", cbill.getBillCategory(i).getCategoryname());
//				map.put("typecount", cbill.getBillCategory(i).getCategorycount());
//				map.put("billitems", cbill.getBillCategory(i).getBillitemList());
//				map.put("categoryid", cbill.getBillCategory(i).getCategoryid());
//				mData.add(map);
//			}
//			
//			
//			orderList = new ArrayList<Msg_BillCategory>(cbill.getBillCategoryList());
//			
//			
//			
//			productNumTemp=getProdutNum(orderList);
//			ODAdp = new TakeOutBusinessMenuAdapter(TakeOutBussinessMenuAct.this,mData);
//			lv.setAdapter(ODAdp);
//		}
//		
//	}
//	public ArrayList<Map<String, Object>> getProdutNum(ArrayList<Msg_BillCategory> order) {
//		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < order.size(); i++) {
//			for (int j = 0; j < order.get(i).getBillitemCount(); j++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("categoryId", order.get(i).getCategoryid());
//				map.put("productId", order.get(i).getBillitemList().get(j).getBillitemid());
//				map.put("productNum", order.get(i).getBillitemList().get(j).getBillitemcount()==null||order.get(i).getBillitemList().get(j).getBillitemcount().equals("")?"0":order.get(i).getBillitemList().get(j).getBillitemcount());
//				arr.add(map);
//			}
//		}
//		return arr;
//	}
//	
////	private void setTakeoutCount() {
////		int[] tempcount = null;
////		if (takoutdb.tabIsExist(takoutdb.tbname2)) {
////			Cursor cs = takoutdb.query(takoutdb.tbname2);
////			if (cs.getCount() > 0) {
////				tempcount = takoutdb.getInt(cs, "takeoutcount");
////			}
////			if (tempcount == null)
////				return;
////			for (int i = 0; i < tempcount.length; i++) {
////				allCount = allCount + tempcount[i];
////			}
////			takoutdb.close();
////		}
////	}
//}
