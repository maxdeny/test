package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.OrderConfirmationAdapter;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.foot_orderconfirmation;
import com.wjwl.mobile.taocz.widget.head_orderconfirmaiton;

public class OrderConfirmationAct extends MActivity {
	TextView tv_username, tv_address, tv_tel, tv_allpay;
	EditText ed_fptt;
	ListView lv;
	RelativeLayout layout1;
	OrderConfirmationAdapter OCAdp;
	// private Msg_Order.Builder OrderMain; // 订单
	// public ArrayList<Msg_OrderList> orderList; // 订单列表
	private List<Msg_Maddress> addresslist;
	private Msg_CitemList2.Builder OrderMain; // 订单
	public ArrayList<Msg_CitemList> orderList; // 订单列表
	head_orderconfirmaiton head_item;
	foot_orderconfirmation foot_item;
	private Button bt_pay;
	private String paytype, addressid, remark, total, username, useraddress,
			usertel, paytypetemp;
	private Context mContext;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setId("OrderConfirmationAct");
		setContentView(R.layout.order_confirmation);
		mContext = OrderConfirmationAct.this;
		Intent i = getIntent();
		paytype = i.getStringExtra("paytype");
		tv_allpay = (TextView) findViewById(R.order_confirmation.allpay);
		lv = (ListView) findViewById(R.order_confirmation.listview);
		bt_pay = (Button) findViewById(R.order_confirmation.bt_pay);

		foot_item = new foot_orderconfirmation(OrderConfirmationAct.this);
		head_item = new head_orderconfirmaiton(OrderConfirmationAct.this);
		head_item.layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("act", "canuse");
				i.setClass(OrderConfirmationAct.this, ConsigneeAddressAct.class);
				startActivityForResult(i, 0);
			}
		});

		bt_pay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (addressid == null || addressid.length() == 0) {
					Toast.makeText(OrderConfirmationAct.this, "请选择您的收货地址!",
							Toast.LENGTH_LONG).show();
					Intent i = new Intent();
					i.putExtra("act", "canuse");
					i.setClass(OrderConfirmationAct.this,
							ConsigneeAddressAct.class);
					startActivityForResult(i, 0);
				} else {
					dataLoad(new int[] { 1 });
				}
			}
		});
		dataLoad(new int[] { 0 });
	}

	@Override
	public void dataLoad(int[] types) {
		if ((paytype.equals("1")) || (paytype.equals("7"))) {
			paytypetemp = "1";// 货到付款
		} else {
			paytypetemp = "2";// 在线支付
		}

		if (types == null) {
			this.loadData(new Updateone[] { new Updateone("PORDER",
					new String[][] { { "userid", F.USER_ID },
							{ "order_pay_type", paytypetemp },
							{ "region_type", "ziti or default" },//V3新参数
							{ "region_id", addressid }
							}), });
		} else {
			if (types[0] == 0) {
				this.loadData(new Updateone[] { new Updateone("MADDRESSLIST",
						new String[][] { { "userid", F.USER_ID },
								{ "default", "1" } }), });
			}
			if (types[0] == 1) {
				this.loadData(new Updateone[] { new Updateone("PCOMMIT",
						new String[][] { { "userid", F.USER_ID },
								{ "pay_type", paytypetemp },
								{ "address_id", addressid },
								{ "time_stamp", remark },
								{ "mobiletype", "21" },
								{ "couponno", "" },//新加参数优惠券码
								{ "region_type", "ziti or default" },//V3新参数
								{ "IMIS", F.getDeviceId(this) },
								{ "post_invoiceTitle", foot_item.gettitle() } }), });
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("porder")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			orderList = new ArrayList<Msg_CitemList>(
					OrderMain.getCitemlistList()); // 定义临时列表
			total = Arith.to2zero(OrderMain.getCartTotal());
			tv_allpay
					.setText(total + "(邮费：" + OrderMain.getShippingFee() + ")");
			remark = OrderMain.getRemark();
			foot_item.setsendtime("预计送达时间：" + remark);
			OCAdp = new OrderConfirmationAdapter(this, orderList);

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
				head_item.setUsername(username.equals("") ? "" : "姓名:"
						+ username);
				head_item.setUseraddress(useraddress.equals("") ? "" : "地址:"
						+ useraddress);
				head_item
						.setUsertel(usertel.equals("") ? "" : "手机号:" + usertel);
				addressid = addresslist.get(0).getAddressid();
			} else {
				Toast.makeText(this, "您尚未设置您的收货地址!", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("act", "canuse");
				i.setClass(OrderConfirmationAct.this, ConsigneeAddressAct.class);
				startActivityForResult(i, 0);
			}
			dataLoad();
		}
		if (son.build != null && son.mgetmethod.equals("pcommit")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			String orderno = retn.getErrorMsg();
			if (retn.getErrorCode() == 0) {
				MobclickAgent.onEvent(mContext, "OrderSubmission");
				F.GOODSCOUNT = 0;
				Toast.makeText(this, "订单提交成功", Toast.LENGTH_LONG).show();
				String renturn[] = orderno.split("&&");
				if (paytypetemp.equals("1")) {
					Intent i = new Intent();
					i.putExtra("order_sn_main", renturn[0]);
					i.putExtra("pay_type", paytype);
					i.putExtra("isTaoxinka", "0");
					i.putExtra("taoxkValue", "0");
					i.putExtra("isVcount", "0");
					i.putExtra("vcountValue", "0");
					i.setClass(getApplication(), OrderEndAct.class);
					startActivity(i);
					OrderConfirmationAct.this.finish();
				} else {
					Intent i = new Intent();
					i.putExtra("orderno", renturn[0]);
					i.putExtra("order_pay_type", paytype);
					i.putExtra("classtype", "gouwu");
					i.setClass(getApplication(), OrderTypeConfirmationAct.class);
					startActivity(i);
					OrderConfirmationAct.this.finish();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			username = data.getStringExtra("username");
			useraddress = data.getStringExtra("useraddress");
			usertel = data.getStringExtra("usertel");
			head_item.setUsername(username);
			head_item.setUseraddress(useraddress);
			head_item.setUsertel(usertel);
			addressid = data.getStringExtra("addressid");
			dataLoad(null);
		}
	}

}
