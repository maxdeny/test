package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.CBill.Msg_CBill;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_BillCategory;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.tcz.apkfactory.data.CBillList.Msg_CBillList;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.R.takeoutbox;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.adapter.TakeOutBoxAdapter;
//import com.wjwl.mobile.taocz.widget.Item_takeoutbox;
//
//public class TakeOutBoxAct extends MActivity {
//	ListView lv;
//	ArrayList<Map<String, Object>> mData;
//	TakeOutBoxAdapter ODAdp;
//	private WmDB wmDB;
//	private PullReloadView prv;
//	public static ArrayList<Map<String, Object>> productNumTemp;
//	public ArrayList<Msg_CBill> orderList;
//	public String address_id, store_id, totalprice, pay_type, shippingprice,
//			postscript, spec_id, count, username, userid,phoneno;
//	int temp;
//	private TextView tv_body;
//	Button back;
////	LinearLayout lv;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutbox);
//		setId("TakeOutBoxAct");
//		lv = (ListView) findViewById(R.takeoutbox.listview);
//		tv_body=(TextView) findViewById(R.takeoutbox.head_body);
//		back=(Button) findViewById(R.takeoutbox.back);
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//		temp = 0;
//		dataLoad(new int[]{1});
//	}
//
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		if(type==86){
//			temp = 0;
//			dataLoad(new int[]{1});
//		}else{
//		String[] str = (String[]) obj;
//		switch (type) {
//		case 1:
//			mData.get(Integer.valueOf(str[0])).put("address", str[1]);
//			mData.get(Integer.valueOf(str[0])).put("addressid", str[2]);
//			ODAdp.notifyDataSetChanged();
//			break;
//		case 2:
//			if (str[1].equals("1"))
//				mData.get(Integer.valueOf(str[0])).put("paytype", "货到付款");
//			else if (str[1].equals("2"))
//				mData.get(Integer.valueOf(str[0])).put("paytype", "在线支付");
//			ODAdp.notifyDataSetChanged();
//			break;
//		case 3:
//			mData.get(Integer.valueOf(str[0])).put("need", str[1]);
//			ODAdp.notifyDataSetChanged();
//			break;
//		case 4:
//				pay_type = str[0];
//				totalprice = str[1];
//				postscript = str[2];
//				shippingprice = str[3];
//				address_id = str[4];
//				store_id = str[5];
//				spec_id = str[6];
//				count = str[7];
//				phoneno=str[8];
//				username = F.USERNAME;
//				userid = F.USER_ID;
//				temp = 1;
//				dataLoad(new int[]{2});
//			break;
//		case 5:
//			String businessid = (String) (mData.get(Integer.parseInt(str[0])))
//					.get("businessid");
//			mData.remove(Integer.parseInt(str[0]));
//			ODAdp.remove(ODAdp.get(Integer.parseInt(str[0])));
//			ODAdp.notifyDataSetInvalidated();
//			wmDB.Deletebybusinessid(businessid);
//			Frame.HANDLES.close("TakeOutBussinessMenuAct");
//			Toast.makeText(TakeOutBoxAct.this, "该餐盒已删除成功", Toast.LENGTH_SHORT)
//					.show();
//			break;
//		case 6:
//			temp = 0;
//			dataLoad(new int[]{1});
//			break;
//			
//		}
//		F.wmtempData=mData;
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		if(types[0]==1){
//			switch (temp) {
//			case 0:
//				wmDB = new WmDB(TakeOutBoxAct.this);
//				Msg_CBill.Builder cbill = Msg_CBill.newBuilder();
//				List<Map<String, String>> businessidlistmap = new ArrayList<Map<String, String>>();
//				List<Map<String, String>> itemlistmap = new ArrayList<Map<String, String>>();
//				businessidlistmap = wmDB.findallbusinessid();
//				if(businessidlistmap.size()>0){
//					Msg_CBillList.Builder billlist = Msg_CBillList.newBuilder();
//					for (int i = 0; i < businessidlistmap.size(); i++) {
//						String tempbuinessid = businessidlistmap.get(i).get(
//								"BUSINESSID");
//						Msg_BillCategory.Builder billCategory = Msg_BillCategory
//								.newBuilder();
//						Msg_Cbusinessinfo.Builder businessinfo = Msg_Cbusinessinfo
//								.newBuilder();
//						businessinfo.setBusinessid(tempbuinessid);
//						cbill.setCbusinessinfo(businessinfo);
//						itemlistmap = wmDB.find("BUSINESSID = '" + tempbuinessid + "'");
//						for (int j = 0; j < itemlistmap.size(); j++) {
//							Msg_Billitem.Builder billitem = Msg_Billitem.newBuilder();
//							billitem.setBillitemid(itemlistmap.get(j).get("ITEMID"));
//							billitem.setBillitemcount(itemlistmap.get(j).get(
//									"ITEMCOUNT"));
//							billCategory.addBillitem(billitem);
//							billitem.clear();
//						}
//
//						cbill.addBillCategory(billCategory);
//						billCategory.clear();
//						billlist.addCBill(cbill);
//						cbill.clear();
//					}
//					this.loadData(new Updateone[] { new Updateone("OWMMOD", billlist,
//							0, Msg_CBillList.newBuilder()), });
//				}
//				else{
//					lv.setVisibility(View.GONE);
//					tv_body.setVisibility(View.VISIBLE);
//				}
//				
//				break;
//			case 1:
//				break;
//			}
//		}
//	    if(types[0]==2){
//	    	this.loadData(new Updateone[] { new Updateone("OWMCOMMIT",
//					new String[][] {
//	    			//life.php?app=msg_retn&act=owmcommit&debug=1&store_id=111&totalprice=534&
//	    			//postscript=备注&sex=1&
//	    			//ids=10,11,12&nums=3,5,9&user_id=123&address_id=37323
//							{ "pay_type", pay_type }, { "totalprice", totalprice.replace("元", "") },
//							{ "postscript", postscript }, { "shippingprice", shippingprice.replace("元", "") }
//							, { "address_id", address_id }, { "store_id", store_id }
//							, { "ids", spec_id }, { "nums", count }, { "sex", "1" }, { "phoneno", phoneno }
//							, { "username", username }, { "user_id", userid }}), });
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.mgetmethod.equals("owmmod") && son.build != null) {
//			Msg_CBillList.Builder billlist = (com.tcz.apkfactory.data.CBillList.Msg_CBillList.Builder) son.build;
//			mData = new ArrayList<Map<String, Object>>();
//			for (int p = 0; p < billlist.getCBillCount(); p++) {
//
//				for (int i = 0; i < billlist.getCBill(p).getBillCategoryCount(); i++) {
//					
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("businessname", billlist.getCBill(p)
//							.getCbusinessinfo().getBusinessname());
//					map.put("businessid", billlist.getCBill(p)
//							.getCbusinessinfo().getBusinessid());
//					map.put("peisongfei", billlist.getCBill(p).getSendprice());
//					map.put("heji", billlist.getCBill(p).getTotalprice());
//					map.put("items", billlist.getCBill(p).getBillCategory(i)
//							.getBillitemList());
//					
//					if(F.wmtempData.size()!=0){
//						for(int h=0;h<F.wmtempData.size();h++){
//							if(billlist.getCBill(p).getCbusinessinfo().getBusinessname().equals(F.wmtempData.get(h).get("businessname")))
//							{
//								map.put("address", F.wmtempData.get(h).get("address"));
//								map.put("addressid", F.wmtempData.get(h).get("addressid"));
//								map.put("need", F.wmtempData.get(h).get("need"));
//								map.put("paytype", F.wmtempData.get(h).get("paytype"));
//							}
//						}
//					}else{
//						map.put("address", "");
//						map.put("addressid", "");
//						map.put("need", "");
//						map.put("paytype", "");
//					}
//					
//					mData.add(map);
//				}
//			}
//			orderList = new ArrayList<Msg_CBill>(billlist.getCBillList());
//			productNumTemp = getProdutNum(orderList);
//			ODAdp = new TakeOutBoxAdapter(TakeOutBoxAct.this, mData);
//			lv.setAdapter(ODAdp);
////			Item_takeoutbox item=(Item_takeoutbox) lv;
////			item.setPeiSongFei(billlist.getCBill(0).getSendprice());
////			item.setHeJi((Float.parseFloat(billlist.getCBill(0).getTotalprice().equals("")?"0":billlist.getCBill(0).getTotalprice()+Float.parseFloat(billlist.getCBill(0).getSendprice().equals("")?"0":billlist.getCBill(0).getSendprice())))+"");
////			item.setBussinessId(billlist.getCBill(0)
////					.getCbusinessinfo().getBusinessid());
////			item.setBussinessName(billlist.getCBill(0)
////					.getCbusinessinfo().getBusinessname());
////			if(F.wmtempData.size()!=0){
////				for(int h=0;h<F.wmtempData.size();h++){
////					if(billlist.getCBill(0).getCbusinessinfo().getBusinessname().equals(F.wmtempData.get(h).get("businessname")))
////					{	item.setAddress(F.wmtempData.get(h).get("address")+"");
////						item.setPayType(F.wmtempData.get(h).get("paytype")+"");
////						item.setNeed(F.wmtempData.get(h).get("need")+"");
////						item.setAddressid(F.wmtempData.get(h).get("addressid")+"");
////						item.setPosition("" + h);
////					}
////				}
////			}else{
////				item.setAddress("");
////				item.setPayType("");
////				item.setNeed("");
////				item.setAddressid("");
////			}
//		}
//		if (son.mgetmethod.equals("owmcommit") && son.build != null) {
//			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
//			if(retn.getErrorCode()==0&&retn.getErrorMsg().equals("休息中")){
//				Toast.makeText(TakeOutBoxAct.this, "已经过了营业时间，欢迎明日再购买。", Toast.LENGTH_SHORT).show();
//			}
//			else if(retn.getErrorCode()==0){
//				if(pay_type.equals("1")){//货到付款
//					Intent i = new Intent();
//					i.putExtra("order_sn_main", retn.getErrorMsg());
//					i.putExtra("pay_type", "1");
//					i.putExtra("isTaoxinka", "0");
//					i.putExtra("taoxkValue", "0");
//					i.putExtra("isVcount", "0");
//					i.putExtra("vcountValue", "0");
//					i.putExtra("wmbusinessid", store_id);
//					i.setClass(TakeOutBoxAct.this, OrderEndAct.class);
//					startActivity(i);
//					Frame.HANDLES.close("TakeOutBussinessMenuAct");
//				}
//				else if(pay_type.equals("2")){//在线支付
//					Intent intent =new Intent();
//					intent.putExtra("orderno", retn.getErrorMsg());
//					intent.putExtra("classtype", "gouwu");
//					intent.putExtra("wmbusinessid", store_id);
//					intent.setClass(TakeOutBoxAct.this, OrderTypeConfirmationAct.class);
//					startActivity(intent);
//				}
//			}else 
//				Toast.makeText(getApplication(), retn.getErrorMsg(), Toast.LENGTH_SHORT).show();
//		}
//		else if (son.build==null){
//			tv_body.setVisibility(View.VISIBLE);
//		}
//		
//		
//		
//	}
//
//	public ArrayList<Map<String, Object>> getProdutNum(
//			ArrayList<Msg_CBill> order) {
//		ArrayList<Map<String, Object>> arr = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < order.size(); i++) {
//			for (int j = 0; j < order.get(i).getBillCategory(0)
//					.getBillitemCount(); j++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("businessId", order.get(i).getCbusinessinfo()
//						.getBusinessid());
//				map.put("productId", order.get(i).getBillCategory(0)
//						.getBillitem(j).getBillitemid());
//				map.put("productNum", order.get(i).getBillCategory(0)
//						.getBillitem(j).getBillitemcount() == null
//						|| order.get(i).getBillCategory(0).getBillitem(j)
//								.getBillitemcount().equals("") ? "0" : order
//						.get(i).getBillCategory(0).getBillitem(j)
//						.getBillitemcount());
//				arr.add(map);
//			}
//		}
//		return arr;
//	}
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			WmDB wmdb;
//			wmdb = new WmDB(TakeOutBoxAct.this);
//			wmdb.deleteall();
//			this.finish();
//			return true;
//			
//		}
//		return false;
//	}
//	
//	@Override
//	protected void destroy() {
//		F.wmtempData.clear();
//	}
//
//}
