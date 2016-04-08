package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.tcz.apkfactory.data.RetnPay.Msg_RetnPay;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class JH_ZFAct extends MActivity {
	TextView allprice, paytype, name, address, phone, tel;
	Button bt_submit;
	private String addressid;
	LinearLayout layout_address;
	String out_trade_no;
    Button bt_back;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.jh_zf);
		setId("JH_ZFAct");
		bt_back=(Button)findViewById(R.jhhd.bt_submit);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JH_ZFAct.this.finish();
			}
		});
		layout_address = (LinearLayout) findViewById(R.jh_zf.layout_address);
		allprice = (TextView) findViewById(R.jh_zf.allprice);
		paytype = (TextView) findViewById(R.jh_zf.paytype);
		name = (TextView) findViewById(R.jh_zf.name);
		address = (TextView) findViewById(R.jh_zf.address);
		phone = (TextView) findViewById(R.jh_zf.phone);
		tel = (TextView) findViewById(R.jh_zf.tel);
		bt_submit = (Button) findViewById(R.jh_zf.bt_submit);
		paytype.setText("建设银行");
		layout_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("act", "JH_ZFAct");
				i.setClass(JH_ZFAct.this, ConsigneeAddressAct.class);
				startActivity(i);
			}
		});
		bt_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataLoad(new int[] { 1 });
			}
		});
		dataLoad(new int[] { 0 });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("v3_addactivity")) {
			Msg_Citem.Builder builder = (Msg_Citem.Builder) son.build;
			allprice.setText("￥" + builder.getItemcount());
			paytype.setText(builder.getItemtype().equals("8")?"建设银行":"w未知");

		} else if (son.mgetmethod.equals("maddresslist")) {
			if (son.build != null) {
				Msg_Maddresslist.Builder builder = (Msg_Maddresslist.Builder) son.build;
				List<Msg_Maddress> addresslist = builder.getMaddressList();

				String username = addresslist.get(0).getReceiver();
				String useraddress = addresslist.get(0).getDetailsaddress();
				String userphone = addresslist.get(0).getMobile();
				String usertel = addresslist.get(0).getTelphone();
				name.setText(username.equals("") ? "" : username);
				address.setText(useraddress.equals("") ? "" : useraddress);
				phone.setText(userphone.equals("") ? "" : userphone);
				tel.setText(usertel.equals("") ? "" : usertel);
				addressid = addresslist.get(0).getAddressid();
			} else {
				Toast.makeText(this, "您尚未设置您的收货地址!", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("act", "JH_ZFAct");
				i.setClass(JH_ZFAct.this, ConsigneeAddressAct.class);
				startActivity(i);
			}
			dataLoad(null);
		} else if (son.build != null && son.mgetmethod.equals("v3_payactivity")) {
			Msg_RetnPay.Builder retn = (Msg_RetnPay.Builder) son.build;
			String retncode = retn.getRetncode();
			if (retncode.equals("0")) {
				MobclickAgent.onEvent(JH_ZFAct.this, "OrderSubmission");
				F.GOODSCOUNT = 0;
				Toast.makeText(this, "订单提交成功", Toast.LENGTH_LONG).show();
				out_trade_no = retn.getOutTradeNo();
				Intent i = new Intent();
				i.putExtra("order_sn_main", out_trade_no);
				i.putExtra("classtype", "jhgouwu");
				i.putExtra("jhpaymoney", retn.getPaymoney());
				i.putExtra("pay_type", "8");
				i.putExtra("isTaoxinka", "0");
				i.putExtra("taoxkValue", "0");
				i.putExtra("isVcount", "0");
				i.putExtra("vcountValue", "0");
				i.setClass(getApplication(), OrderEndAct.class);
				startActivity(i);
				JH_ZFAct.this.finish();
			}
			else{
				Toast.makeText(JH_ZFAct.this, retn.getOutTradeNo(), Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		if (type == 1) {// 地址信息
			String[] str = (String[]) obj;
			name.setText(str[0]);
			address.setText(str[1]);
			if (str[2].equals(""))
				phone.setVisibility(View.GONE);
			else {
				phone.setVisibility(View.VISIBLE);
				phone.setText(str[2]);
			}
			if (str[3].equals(""))
				tel.setVisibility(View.GONE);
			else {
				tel.setVisibility(View.VISIBLE);
				tel.setText(str[3]);
			}
			addressid = str[4];
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types == null) {
			this.loadData(new Updateone[] { new Updateone("V3_ADDACTIVITY",
					new String[][] { { "id", "144" }, { "uid", F.USER_ID } }), });
		} else if (types[0] == 0) {
			this.loadData(new Updateone[] { new Updateone(
					"MADDRESSLIST",
					new String[][] { { "userid", F.USER_ID }, { "default", "1" } }), });
		} else if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("V3_PAYACTIVITY",
					new String[][] { { "uid", F.USER_ID }, { "id", "144" },//83562
							{ "pay_type", "8" }, { "region_id", addressid } }), });
		}
	}
}
