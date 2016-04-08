package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2.Builder;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.HeadLayout;

public class V3_WriteOrderAct extends MActivity {
	HeadLayout head;
	LinearLayout zhifu_linear, youhui_linear, beizhu_linear, fapiao_linear,
			shangpin_linear, wanjiaxian_linear;
	TextView zhifu_text, name_text, address_text, mobile_text, telphone_text,
			time_text, time2_text, youhui_text, beizhu_text, fapiao_text,
			shangpin_text, yunfei, youhui, heji, no_hdfk_text;
	Button tijiao, bt_back;
	String addressid = "", yhqid = "", useraddress, username, mobile, telphone,
			remark, paytype = "4", region_type = "default", allpay,user_mobile_prox="",
			allgoodspay, beizhu = "",user_name_prox="",proxy_address_id="",ztrname="",ztrphone="";
	private String paytypetemp = "2";
	List<Msg_Maddress> addresslist;
	private Builder OrderMain;
	private ArrayList<Msg_CitemList> orderList;
	RelativeLayout address_linear, hdfk_layout, time_linear;
	public static List<Msg_CitemList> citemlist2;
	String[] senttimes;
	String[] tempstr;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_writeorder);
		setId("V3_WriteOrderAct");
		head = (HeadLayout) findViewById(R.v3_writeorder.head);
		head.setTitle("填写订单信息");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_WriteOrderAct.this.finish();
			}
		});
		head.setRightGone();
		// zhifu_linear,address_linear,time_linear,youhui_linear,beizhu_linear,fapiao_linear,shangpin_linear;
		zhifu_linear = (LinearLayout) findViewById(R.v3_writeorder.zhifu_linear);
		zhifu_text = (TextView) findViewById(R.v3_writeorder.zhifu_text);
		address_linear = (RelativeLayout) findViewById(R.v3_writeorder.address_linear);
		address_text = (TextView) findViewById(R.v3_writeorder.address_text);
		name_text = (TextView) findViewById(R.v3_writeorder.name_text);
		mobile_text = (TextView) findViewById(R.v3_writeorder.phone_text);
		telphone_text = (TextView) findViewById(R.v3_writeorder.tel_text);
		time_linear = (RelativeLayout) findViewById(R.v3_writeorder.time_linear);
		time_text = (TextView) findViewById(R.v3_writeorder.time_text);
		time2_text = (TextView) findViewById(R.v3_writeorder.time2_text);
		youhui_linear = (LinearLayout) findViewById(R.v3_writeorder.youhui_linear);
		youhui_text = (TextView) findViewById(R.v3_writeorder.youhui_text);
		beizhu_linear = (LinearLayout) findViewById(R.v3_writeorder.beizhu_linear);
		beizhu_text = (TextView) findViewById(R.v3_writeorder.beizhu_text);
		fapiao_linear = (LinearLayout) findViewById(R.v3_writeorder.fapiao_linear);
		fapiao_text = (TextView) findViewById(R.v3_writeorder.fapiao_text);
		shangpin_linear = (LinearLayout) findViewById(R.v3_writeorder.shangpin_linear);
		shangpin_text = (TextView) findViewById(R.v3_writeorder.shangpin_text);
		wanjiaxian_linear = (LinearLayout) findViewById(R.v3_writeorder.wanjiaxian_linear);
		hdfk_layout = (RelativeLayout) findViewById(R.v3_writeorder.no_hdfk_layout);
		no_hdfk_text = (TextView) findViewById(R.v3_writeorder.no_hdfk_text);
		yunfei = (TextView) findViewById(R.v3_writeorder.yunfei);
		yunfei.setText("￥0");
		youhui = (TextView) findViewById(R.v3_writeorder.youhui);
		youhui.setText("￥0");
		heji = (TextView) findViewById(R.v3_writeorder.heji);
		heji.setText("￥0");
		tijiao = (Button) findViewById(R.v3_writeorder.tijiao);
		zhifu_text.setText("在线支付");
		zhifu_linear.setOnClickListener(new Click());
		address_linear.setOnClickListener(new Click());
		time_linear.setOnClickListener(new Click());
		youhui_linear.setOnClickListener(new Click());
		beizhu_linear.setOnClickListener(new Click());
		fapiao_linear.setOnClickListener(new Click());
		shangpin_linear.setOnClickListener(new Click());
		tijiao.setOnClickListener(new Click());
		hdfk_layout.setOnClickListener(new Click());
		dataLoad(new int[] { 0 });
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		if (type == 1) {// 支付方式
			String[] str = (String[]) obj;
			zhifu_text.setText(str[0]);
			paytype = str[1];
			if (!str[1].equals("4")) {
				dataLoad(new int[] { 2 });
			} else
				hdfk_layout.setVisibility(View.GONE);
			dataLoad(null);
		} else if (type == 2) {// 地址信息
			String[] str = (String[]) obj;
			name_text.setVisibility(View.VISIBLE);
			name_text.setText(str[0]);
			address_text.setVisibility(View.VISIBLE);
			address_text.setText(str[1]);
			if (str[2].equals(""))
				mobile_text.setVisibility(View.GONE);
			else {
				mobile_text.setVisibility(View.VISIBLE);
				mobile_text.setText(str[2]);
			}
			if (str[3].equals(""))
				telphone_text.setVisibility(View.GONE);
			else {
				telphone_text.setVisibility(View.VISIBLE);
				telphone_text.setText(str[3]);
			}
			addressid = str[4];
			proxy_address_id = "";
			user_name_prox = "";
			user_mobile_prox = "";
			dataLoad(null);
		} else if (type == 3) {// 发送时间
			time_text.setText((String) obj);
		} else if (type == 4) {// 优惠信息
			String[] str = (String[]) obj;
			youhui_text.setText(str[0] + str[2] + "元");
			yhqid = str[1];
			if ((Double.parseDouble(allpay) - Double.parseDouble(str[2])) <= 0) {
				heji.setText("￥" + 0);
			} else {
				heji.setText("￥"
						+ Arith.to2zero(""
								+ ((Double.parseDouble(allpay) - Double
										.parseDouble(str[2])))));
			}

			youhui.setText("￥" + str[2]);
		} else if (type == 5) {
			beizhu = (String) obj;
			beizhu_text.setText(beizhu);
		} else if (type == 6) {
			fapiao_text.setText((String) obj);
		} else if (type == 7) {
			String[] str = (String[]) obj;
			name_text.setText(str[0] + " " + str[1]);
			proxy_address_id = str[2];
			user_name_prox = str[1];
			user_mobile_prox = str[3];
			ztrname = str[4];
			ztrphone = str[5];
			name_text.setText(user_name_prox);
			name_text.setVisibility(View.VISIBLE);
			address_text.setVisibility(View.GONE);
			mobile_text.setVisibility(View.GONE);
			telphone_text.setVisibility(View.GONE);
//			dataLoad(new int[] { 0 });
		}
	}

	class Click implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.equals(zhifu_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this, V3_ZhiFuAct.class);
				startActivity(i);
			} else if (v.equals(address_linear)) {// 使用以前
				Intent i = new Intent(V3_WriteOrderAct.this,
						V3_AddressAct.class);// ConsigneeAddressAct
				startActivity(i);
			} else if (v.equals(time_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this,
						V3_SentTimeSelectAct.class);
				i.putExtra("senttimes", senttimes);
				startActivity(i);
			} else if (v.equals(youhui_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this, V3_YouHuiAct.class);
				i.putExtra("allpay", allgoodspay);
				i.putExtra("jumptype", "gouwu");
				startActivity(i);
			} else if (v.equals(beizhu_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this, V3_BeiZhuAct.class);
				startActivity(i);
			} else if (v.equals(fapiao_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this, V3_FaPiaoAct.class);
				startActivity(i);
			} else if (v.equals(shangpin_linear)) {
				Intent i = new Intent(V3_WriteOrderAct.this,
						V3_QingDanAct.class);
				i.putExtra("paytypetemp", paytypetemp);
				i.putExtra("region_type", region_type);
				i.putExtra("addressid", addressid);
				startActivity(i);
			} else if (v.equals(tijiao)) {

				if ((addressid != null && addressid.length() > 0)||(proxy_address_id != null && proxy_address_id.length() > 0)) {
					dataLoad(new int[] { 1 });
				} else {
					Toast.makeText(V3_WriteOrderAct.this, "请选择收货地址",
							Toast.LENGTH_SHORT).show();
				}

				// if (zhifu_text.getText() != null
				// && zhifu_text.getText().length() > 0
				// && zhifu_text.getText().equals("在线支付")) {
				// Intent i = new Intent(V3_WriteOrderAct.this,
				// V3_ZaiXianAct.class);
				// startActivity(i);
				// } else if (zhifu_text.getText() != null
				// && zhifu_text.getText().length() > 0
				// && zhifu_text.getText().equals("货到付款")) {
				// Intent i = new Intent(V3_WriteOrderAct.this,
				// V3_HuoDaoAct.class);
				// startActivity(i);
				// }
			} else if (v.equals(hdfk_layout)) {
				Intent i = new Intent(V3_WriteOrderAct.this,
						V3_NoSupportHDFKAct.class);
				startActivity(i);
			}

		}

	}

	// 使用String的split 方法
	public static String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("porder")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			orderList = new ArrayList<Msg_CitemList>(
					OrderMain.getCitemlistList()); // 定义临时列表
			allpay = Arith.to2zero(OrderMain.getShippingFeeTotal());
			allgoodspay = Arith.to2zero(OrderMain.getCartTotal());
			heji.setText("￥" + Arith.to2zero(OrderMain.getShippingFeeTotal()));
			yunfei.setText("￥" + OrderMain.getShippingFee());
			remark = OrderMain.getRemark();
			if (!remark.equals("")) {
				senttimes = Arith.convertStrToArray(remark);
				time_text.setText(senttimes[0]);
				time_linear.setOnClickListener(new Click());
			} else
				time_linear.setVisibility(View.GONE);
			if (!OrderMain.getRemarktime().equals("")) {
				tempstr = Arith.convertStrToArray(OrderMain
						.getRemarktime());
				time2_text.setText(tempstr[0]);
			} else
				wanjiaxian_linear.setVisibility(View.GONE);

		}

		if (son.mgetmethod.equals("maddresslist")) {
			if (son.build != null) {
				Msg_Maddresslist.Builder builder = (Msg_Maddresslist.Builder) son.build;
				addresslist = builder.getMaddressList();
				username = addresslist.get(0).getReceiver();
				useraddress = addresslist.get(0).getDetailsaddress();
				mobile = addresslist.get(0).getMobile();
				telphone = addresslist.get(0).getTelphone();
				name_text.setVisibility(View.VISIBLE);
				address_text.setVisibility(View.VISIBLE);
				address_text.setText(useraddress.equals("") ? "" : "地址:"
						+ useraddress);
				name_text.setText(username.equals("") ? "" : "姓名:" + username);
				if (mobile.equals(""))
					mobile_text.setVisibility(View.GONE);
				else {
					mobile_text.setVisibility(View.VISIBLE);
					mobile_text.setText("手机号：" + mobile);
				}
				if (telphone.equals(""))
					telphone_text.setVisibility(View.GONE);
				else {
					telphone_text.setVisibility(View.VISIBLE);
					telphone_text.setText("固定电话： " + telphone);
				}
				addressid = addresslist.get(0).getAddressid();
			} else {
				Toast.makeText(this, "您尚未设置您的收货地址!", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("act", "canuse");
				i.setClass(V3_WriteOrderAct.this, ConsigneeAddressAct.class);
				startActivityForResult(i, 0);
			}
			dataLoad();
		}
		if (son.build != null && son.mgetmethod.equals("pcommit")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			String orderno = retn.getErrorMsg();
			if (retn.getErrorCode() == 0) {
				MobclickAgent.onEvent(V3_WriteOrderAct.this, "OrderSubmission");
				F.GOODSCOUNT = 0;
				Toast.makeText(this, "订单提交成功", Toast.LENGTH_LONG).show();
				String renturn[] = orderno.split("&&");
				if (paytypetemp.equals("1")) {// 货到付款
					Intent i = new Intent();
					i.putExtra("orderno", renturn[0]);
					i.putExtra("classtype", "gouwu");
					i.putExtra("pay_type", paytype);
					i.putExtra("isTaoxinka", "0");
					i.putExtra("taoxkValue", "0");
					i.putExtra("isVcount", "0");
					i.putExtra("vcountValue", "0");
					i.putExtra("umcount", "OrderPay4Now");
					// i.setClass(getApplication(), OrderEndAct.class);
					i.setClass(getApplication(), V3_HuoDaoAct.class);
					startActivity(i);
					V3_WriteOrderAct.this.finish();
				} else {// 在线支付
					Intent i = new Intent();
					i.putExtra("orderno", renturn[0]);
					i.putExtra("pay_type", paytype);
					i.putExtra("classtype", "gouwu");
					i.putExtra("umcount", "OrderPay4Now");
					// i.setClass(getApplication(),
					// OrderTypeConfirmationAct.class);
					i.setClass(getApplication(), V3_ZaiXianAct.class);
					startActivity(i);
					V3_WriteOrderAct.this.finish();
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
		} else if (son.build != null && son.mgetmethod.equals("v3_plorder")) {
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder) son.build;
			citemlist2 = builder.getCitemlistList();
			if (citemlist2.size() > 0) {
				hdfk_layout.setVisibility(View.VISIBLE);
				if (citemlist2.size() > 2) {
					no_hdfk_text.setText(citemlist2.get(0).getBusinessname()
							+ "," + citemlist2.get(1).getBusinessname()
							+ "。。。不支持货到付款，不参与此次计算");
				} else
					no_hdfk_text.setText(citemlist2.get(0).getBusinessname()
							+ "不支持货到付款，不参与此次计算");
			}
		}
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
							{ "region_type", region_type },// V3新参数
							{ "region_id", addressid } }), });
		} else if (types[0] == 0) {
			this.loadData(new Updateone[] { new Updateone("MADDRESSLIST",
					new String[][] { { "userid", F.USER_ID },
							{ "default", "1" } }), });
		} else if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("PCOMMIT",
					new String[][] {
							{ "userid", F.USER_ID },
							{ "pay_type", paytypetemp },
							{ "post_bill_payment", paytype },
							{ "address_id", addressid },
							{ "customermark", beizhu },
							{ "time_stamp",time_text.getText().toString().trim() },
							{ "mobiletype", "21" },
							{ "time_stamp2", tempstr!=null?tempstr[0]:"" },
							//自提参数
							{ "proxy_address_id", proxy_address_id },
							{ "user_name_prox", user_name_prox},
							{ "user_mobile_prox", user_mobile_prox },
							{ "ztname", ztrname },
							{ "ztmobile", ztrphone },
							
							{ "couponno", yhqid },// 新加参数优惠券码
							{ "region_type", region_type },// V3新参数
							{ "IMIS", F.getDeviceId(this) },
							{ "post_invoiceTitle",fapiao_text.getText().toString().trim() } }), });
		} else if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("V3_PLORDER",
					new String[][] { { "userid", F.USER_ID },
							{ "support", "0" } }), });
		}

	}
}
