package com.wjwl.mobile.taocz.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.HeadLayout;

public class ServiceConfirmAct extends MActivity {
	String specid, citemid, card, xuni;
	EditText ed_phone;
	Button bt_send, bt_pay;
	TextView bussinessname, productname, price, num;
	MImageView img;
	private Msg_Citem citem;
	String tel, ordernostring;
	String[] renturn;
	private Context mContext;
	Button jia, jian;
	EditText ed_num;
	int count = 1;
	HeadLayout head;
	// TextView ed_phone;
	LinearLayout youhui_l;
	TextView zongjia, heji;
	Double prices;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		// setContentView(R.layout.serviceconfirm);
		setContentView(R.layout.serviceconfirm1);
		setId("ServiceConfirmAct");

		mContext = ServiceConfirmAct.this;
		Intent i = getIntent();
		specid = i.getStringExtra("specid");
		head = (HeadLayout) findViewById(R.id.head);
		head.setRightGone();
		head.setTitle("提交订单");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ed_phone = (EditText) findViewById(R.serviceconfirm.edit_phone);
		// ed_phone = (TextView) findViewById(R.serviceconfirm.phone);
		bt_send = (Button) findViewById(R.serviceconfirm.bt_send);
		// bussinessname = (TextView)
		// findViewById(R.serviceconfirm.bussinessname);
		productname = (TextView) findViewById(R.serviceconfirm.name);
		price = (TextView) findViewById(R.serviceconfirm.price);
		youhui_l = (LinearLayout) findViewById(R.serviceconfirm.youhui_l);
//		if (!F.SENDPHONE.equals(""))
//		ed_phone.setText(F.SENDPHONE);
		youhui_l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ServiceConfirmAct.this,
						V3_YouHuiAct.class);
				in.putExtra("goodsid", specid);
				in.putExtra("jumptype", "shenghuo");
				startActivity(in);
			}
		});
		heji = (TextView) findViewById(R.serviceconfirm.heji);
		zongjia = (TextView) findViewById(R.serviceconfirm.zongjia);
		// num = (TextView) findViewById(R.serviceconfirm.num);
		jia = (Button) findViewById(R.serviceconfirm.minus);
		jian = (Button) findViewById(R.serviceconfirm.plus);
		ed_num = (EditText) findViewById(R.serviceconfirm.ed_num);
		ed_num.setText("1");
		img = (MImageView) findViewById(R.serviceconfirm.img);
		bt_pay = (Button) findViewById(R.serviceconfirm.bt_pay);
		bt_pay.setOnClickListener(new onclic());
		jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				count++;
				ed_num.setText(count + "");
				prices = Double.parseDouble(price.getText().toString());
				heji.setText(count * prices + "");
				zongjia.setText(count * prices + "");
			}
		});
		jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (count > 1) {
					count--;
					ed_num.setText(count + "");
					prices = Double.parseDouble(price.getText().toString());
					heji.setText(count * prices + "");
					zongjia.setText(count * prices + "");
				}
			}
		});
		dataLoad(new int[] { 0 });
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// String[] str = (String[]) obj;
		// youhui_text.setText(str[0] + str[2] + "元");
		// yhqid = str[1];
		// if ((Double.parseDouble(allpay) - Double.parseDouble(str[2])) <= 0) {
		// heji.setText("￥" + 0);
		// }
		// else{
		// heji.setText("￥"
		// + (Double.parseDouble(allpay) - Double.parseDouble(str[2])));
		// }
		//
		// youhui.setText("￥" + str[2]);
	}

	public class onclic implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.serviceconfirm.bt_pay:
				tel = ed_phone.getText().toString().trim();
				if (tel.length() < 0) {
					Toast.makeText(getApplication(), "请输入手机号码",
							Toast.LENGTH_SHORT).show();
					ed_phone.requestFocus();
					return;
				} else if (tel.length() != 11) {
					Toast.makeText(getApplication(), "手机号码输入不正确，请重新输入",
							Toast.LENGTH_SHORT).show();
					ed_phone.requestFocus();
					return;
				}
				dataLoad(new int[] { 1 });
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("tgpservice")) {
			Msg_Citem.Builder citem = (Msg_Citem.Builder) son.build;
			citemid = citem.getItemid();
			specid = citem.getSpecid();
			ed_phone.setText(citem.getOther2());
			// bussinessname.setText(citem.getItembusinessname());
			productname.setText(citem.getItemtitle());
			price.setText(Arith.to2zero(citem.getItemdiscount()));
			prices = Double.parseDouble(price.getText().toString());
			heji.setText(count * prices + "");
			zongjia.setText(count * prices + "");
			img.setObj(citem.getItemimageurl());
			if(citem.getOther2()!=null&&citem.getOther2().length()>1){
				ed_phone.setText(citem.getOther2());//setOther2(value)
			}
			// num.setText("1");
		} else if (son.build != null && son.mgetmethod.equals("ppay_s")) {
			F.SENDPHONE = tel;
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			retn.getErrorMsg();
			retn.getErrorCode();
			if (retn.getErrorCode() == 0) {
				MobclickAgent.onEvent(mContext, "OrderSubmission");
				ordernostring = retn.getErrorMsg();
				renturn = ordernostring.split("&&");
				Intent intent = new Intent();
				intent.putExtra("orderno", renturn[0]);// 订单号
				intent.putExtra("classtype", "shenghuo");
				intent.putExtra("pay_type", "4");
				intent.setClass(ServiceConfirmAct.this, V3_ZaiXianAct.class);
				startActivity(intent);
				finish();
			}
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 0) {
			this.loadData(new Updateone[] { new Updateone("TGPSERVICE",
					new String[][] { { "specid", specid },
							{ "user_id", F.USER_ID }, { "num", "1" } }), });
		}
		if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("PPAY_S",
					new String[][] { { "specid", specid },
							{ "IMIS", F.getDeviceId(this) },
							{ "userid", F.USER_ID }, { "mobiletype", "21" },
							{ "phone_num", tel },
							{ "num", ed_num.getText().toString() } }), });
		}
	}

}
