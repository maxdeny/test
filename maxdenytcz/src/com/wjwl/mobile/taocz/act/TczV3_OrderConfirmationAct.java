package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2.Builder;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.TczV3_ConfirmationAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_Foot_OrderConfirmation;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_Head_OrderConfirmation;

public class TczV3_OrderConfirmationAct extends MActivity {
	public ArrayList<Msg_CitemList.Builder> orderList; // 订单列表
	TczV3_Head_OrderConfirmation head_item;
	TczV3_Foot_OrderConfirmation foot_item;
	TczV3_ConfirmationAdapter OCAdp;
	ListView lv;
	private Context mContext;
	private String paytype = "4", addressid, remark, total, username,
			useraddress, usertel, paytypetemp="2", beizhu = "", fapiao = "",
			yhqid = "",user_mobile_prox="",user_name_prox="",proxy_address_id="",
			ztrname="",ztrphone="",region_type = "default",nortime="",wjxtime="";
	public static String ishuodao="",isziti="";
	Button bt_pay;
	TczV3_HeadLayout headlayout;
	private List<Msg_Maddress> addresslist;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_orderconfirmation);
		setId("TczV3_OrderConfirmationAct");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("订单确认");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_OrderConfirmationAct.this.finish();
			}
		});
		foot_item = new TczV3_Foot_OrderConfirmation(
				TczV3_OrderConfirmationAct.this);
		head_item = new TczV3_Head_OrderConfirmation(
				TczV3_OrderConfirmationAct.this);
		lv = (ListView) findViewById(R.tczv3.list);
		dataLoad(new int[] { 0 });
		
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("V3_PORDER")) {
			Builder OrderMain = (Msg_CitemList2.Builder) son.build;
			orderList = new ArrayList<Msg_CitemList.Builder>(
					OrderMain.getCitemlistBuilderList()); // 定义临时列表
			// total = Arith.to2zero(OrderMain.getCartTotal());
			// tv_allpay
			// .setText(total + "(邮费：" + OrderMain.getShippingFee() + ")");
			// remark = OrderMain.getRemark();
			OrderMain.getShippingFee();//订单商品总数
			OrderMain.getCartTotal();//订单总价（带运费）
			foot_item.setTotalMoney(OrderMain.getShippingFee(),OrderMain.getCartTotal(),OrderMain.getShippingFeeTotal());
			isziti=OrderMain.getOther1();//是否有不支持自提的 1不支持自提（不显示自提选项）
			ishuodao=OrderMain.getOther2();//是否有商品不支持货到付款 1不支持（不显示货到付款选项）
			if (paytypetemp.equals("2"))
				foot_item.setPayType("在线支付");
			else
				foot_item.setPayType("货到付款");
			for (int i = 0; i < orderList.size(); i++) {
				String str = orderList.get(i).getItemtotal();
				if (str.equals("")) {
					orderList.get(i).setFreight("");
				} else {
					String[] strs = str.split(",");
					orderList.get(i).setFreight(strs[0]);// 临时存放了发货时间
				}
			}
			OCAdp = new TczV3_ConfirmationAdapter(
					TczV3_OrderConfirmationAct.this, orderList);
			if (lv.getHeaderViewsCount() <= 0) {
				lv.addFooterView(foot_item, null, false);
				lv.addHeaderView(head_item, null, false);
			}
			lv.setAdapter(OCAdp);
		}
		if (son.mgetmethod.equals("maddresslist")) {
			if (son.build != null) {
				Msg_Maddresslist.Builder builder = (Msg_Maddresslist.Builder) son.build;
				addresslist = builder.getMaddressList();
				username = addresslist.get(0).getReceiver();
				useraddress = addresslist.get(0).getDetailsaddress();
				usertel = addresslist.get(0).getMobile();
				head_item.setUseraddress(useraddress.equals("") ? "" : "地址:"
						+ useraddress);
				if (!username.equals(""))
					head_item.setUsername(username.equals("") ? "" : "姓名:"
							+ username);
				if (!usertel.equals(""))
					head_item.setUsertel("手机号：" + usertel);
				addressid = addresslist.get(0).getAddressid();
			} else {
				Toast.makeText(this, "您尚未设置您的收货地址!", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("act", "canuse");
				i.putExtra("hasadd", "false");
				i.setClass(TczV3_OrderConfirmationAct.this,
						ConsigneeAddressAct.class);
				startActivityForResult(i, 0);
			}
			dataLoad(null);
		}
		if (son.build != null && son.mgetmethod.equals("v3_pcommit")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			String orderno = retn.getErrorMsg();
			if (retn.getErrorCode() == 0) {
				MobclickAgent.onEvent(TczV3_OrderConfirmationAct.this, "OrderConfirm");
				F.GOODSCOUNT = 0;
				Toast.makeText(this, "订单提交成功", Toast.LENGTH_LONG).show();
				String renturn[] = orderno.split("&&");//订单号，$result['msg'].'&&taoxinka_value='.$taoxinka_value.'&&vcount_value='.$vcount_value."&&spayid=".$spayid
				if (paytypetemp.equals("1")) {// 货到付款
					Intent i = new Intent();
					i.putExtra("orderno", renturn[0]);
					i.putExtra("classtype", "gouwu");
					i.putExtra("pay_type", paytype);
					i.putExtra("isTaoxinka", "0");
					i.putExtra("taoxkValue", "0");
					i.putExtra("isVcount", "0");
					i.putExtra("vcountValue", "0");
					// i.setClass(getApplication(), OrderEndAct.class);
					i.setClass(getApplication(), V3_HuoDaoAct.class);
					startActivity(i);
					TczV3_OrderConfirmationAct.this.finish();
				} else {// 在线支付
					Intent i = new Intent();
					i.putExtra("orderno", renturn[0]);
					i.putExtra("pay_type", paytype);
					i.putExtra("classtype", "gouwu");
					// i.setClass(getApplication(),
					// OrderTypeConfirmationAct.class);
					i.setClass(getApplication(), V3_ZaiXianAct.class);
					startActivity(i);
					TczV3_OrderConfirmationAct.this.finish();
				}

			} else {
				if (orderno.trim().equals(""))
					Toast.makeText(this, "订单提交失败", Toast.LENGTH_LONG).show();
				else
					Toast.makeText(this,
							orderno.substring(0, orderno.indexOf("&&")),
							Toast.LENGTH_LONG).show();
			}
			Frame.HANDLES.reloadAll("ShoppingCartAct");
		}
		
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone(
					"V3_PORDER",
					new String[][] { { "userid", F.USER_ID },
							{ "order_pay_type", paytypetemp },
							{ "addr_id", addressid } }), });
		} else if (types[0] == 0) {
			this.loadData(new Updateone[] { new Updateone(
					"MADDRESSLIST",
					new String[][] { { "userid", F.USER_ID}, { "default", "1" } }), });
	   } else if (types[0] == 1) {
		   
		   for(int i=0;i<orderList.size();i++){
			   if(orderList.get(i).getItemtype().equals("1")){ //物流类别（1万家鲜2普通0无送货时间）
				   wjxtime=orderList.get(i).getFreight();
			   }
			   else if(orderList.get(i).getItemtype().equals("2")){
				   nortime=orderList.get(i).getFreight();
			   }
		   }
		   
		   
			this.loadData(new Updateone[] { new Updateone("V3_PCOMMIT",
					new String[][] {
							{ "userid", F.USER_ID },
							{ "pay_type", paytypetemp },
							{ "post_bill_payment", paytype },
							{ "address_id", addressid },
							{ "customermark", beizhu },
							{ "time_stamp",nortime!=null?nortime:""  },
							{ "mobiletype", "21" },
							{ "time_stamp2", wjxtime!=null?wjxtime:"" },
							//自提参数
							{ "proxy_address_id", proxy_address_id },
							{ "user_name_prox", user_name_prox},
							{ "user_mobile_prox", user_mobile_prox },
							{ "ztname", ztrname },
							{ "ztmobile", ztrphone },
							
							{ "couponno", yhqid },// 新加参数优惠券码
							{ "region_type", region_type },// V3新参数
							{ "IMIS", F.getDeviceId(this) },
							{ "post_invoiceTitle",foot_item.getFaPiao() } }), });
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("Pay4Page");
		MobclickAgent.onResume(mContext);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("Pay4Page");
		MobclickAgent.onPause(mContext);
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		if (type == 1) {// 修改送达时间
			String[] str = (String[]) obj;
			orderList.get(Integer.parseInt(str[0])).setFreight(str[1]);
			OCAdp.notifyDataSetChanged();
		} else if (type == 2) {// 地址
			String[] str = (String[]) obj;
			username = str[0];
			usertel = str[1];
			useraddress = str[2];
			addressid = str[3];
			head_item.setUsername(username.equals("") ? "" : "姓名:" + username);
			head_item.setUsertel("手机号：" + usertel);
			head_item.setUseraddress(useraddress.equals("") ? "" : "地址:"
					+ useraddress);
		} else if (type == 3) {// 备注
			String str = (String) obj;
			beizhu = str;
			foot_item.setBeiZhu(beizhu);

		} else if (type == 4) {// 发票
			String str = (String) obj;
			fapiao = str;
			foot_item.setFaPiao(fapiao);

		} else if (type == 5) {// 支付方式
			String[] str = (String[]) obj;
			paytype = str[1];
			if ((paytype.equals("1")) || (paytype.equals("7"))) {
				paytypetemp = "1";// 货到付款
			} else {
				paytypetemp = "2";// 在线支付
			}
			
			if (paytype.equals("4")){
				foot_item.setPayType("在线支付");}
			else if (paytype.equals("1")){
				foot_item.setPayType("货到付款--现金");}
			else if (paytype.equals("7")){
				foot_item.setPayType("货到付款--POS机");
			}
		} else if (type == 6) {// 使用优惠券
			String[] str = (String[]) obj;
			yhqid = str[1];
			foot_item.setYHQ(str[0]);
		}
		else if (type == 8) {// 提交
			dataLoad(new int[]{1});
		}
		else if (type == 7) {
			String[] str = (String[]) obj;
			proxy_address_id = str[2];
			user_name_prox = str[1];
			user_mobile_prox = str[3];
			ztrname = str[4];
			ztrphone = str[5];
			head_item.setUsername("姓名:" + user_name_prox);
			head_item.setUsertel("手机号：" + user_mobile_prox);
			head_item.setUseraddress("地址:"+ ztrname);
//			dataLoad(new int[] { 0 });
		}
	}
	
	
}
