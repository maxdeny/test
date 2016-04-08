package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class ActPay_ConfirmationAct extends MActivity {
	MImageView img;
	TextView headtitle, name, price, num, address, limit_count;
	Button bt_pay,bt_jia, bt_jian;
	TextView bt_back;
	RelativeLayout clic_layout1, clic_layout2;
	String addressid, itemid, backtype;// 农11，建8，中16
	ImageView ico_back;
	String ordernum = "";
	CheckBox checkbox2,nh_checkbox6,jh_checkbox2,zh_checkbox5;
	LinearLayout layzh,layjh,laynh,layzfb;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.actpay_confirmation);
		setId("ActPay_ConfirmationAct");
		headtitle = (TextView) findViewById(R.actpay_confirmation.headtitle);
		img = (MImageView) findViewById(R.actpay_confirmation.image);
		ico_back = (ImageView) findViewById(R.actpay_confirmation.ico_back);
		name = (TextView) findViewById(R.actpay_confirmation.name);
		price = (TextView) findViewById(R.actpay_confirmation.price);
		num = (TextView) findViewById(R.actpay_confirmation.productnumvalue);
		address = (TextView) findViewById(R.actpay_confirmation.address);
		bt_back = (TextView) findViewById(R.actpay_confirmation.back);
		bt_pay = (Button) findViewById(R.actpay_confirmation.bt_pay);
		bt_jia = (Button) findViewById(R.actpay_confirmation.btn_minus);
		bt_jian = (Button) findViewById(R.actpay_confirmation.btn_plus);
		clic_layout1 = (RelativeLayout) findViewById(R.actpay_confirmation.layout_sendtime);
		clic_layout2 = (RelativeLayout) findViewById(R.actpay_confirmation.address_layout);
		checkbox2 = (CheckBox) findViewById(R.actpay_confirmation.checkbox2); 
		nh_checkbox6 = (CheckBox) findViewById(R.actpay_confirmation.nh_checkbox6);
		jh_checkbox2 = (CheckBox) findViewById(R.actpay_confirmation.jh_checkbox2);
		zh_checkbox5= (CheckBox) findViewById(R.actpay_confirmation.zh_checkbox5);
		
		checkbox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(checkbox2.isChecked()){
					nh_checkbox6.setChecked(false);
					jh_checkbox2.setChecked(false);
					zh_checkbox5.setChecked(false);
					backtype="4";
				}
			}
		});
		
		nh_checkbox6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						
						@Override
						public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
							if(nh_checkbox6.isChecked()){
								checkbox2.setChecked(false);
								jh_checkbox2.setChecked(false);
								zh_checkbox5.setChecked(false);
								backtype="11";
							}
						}
					});
		zh_checkbox5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(zh_checkbox5.isChecked()){
						nh_checkbox6.setChecked(false);
						jh_checkbox2.setChecked(false);
						checkbox2.setChecked(false);
						backtype="16";
					}
				}
			});
		jh_checkbox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(jh_checkbox2.isChecked()){
						nh_checkbox6.setChecked(false);
						checkbox2.setChecked(false);
						zh_checkbox5.setChecked(false);
						backtype="8";
					}
				}
			});

		
		layzh= (LinearLayout) findViewById(R.actpay_confirmation.layzh);
		layjh= (LinearLayout) findViewById(R.actpay_confirmation.layjh);
		laynh= (LinearLayout) findViewById(R.actpay_confirmation.laynh);
		layzfb= (LinearLayout) findViewById(R.actpay_confirmation.layzfb);
		
				
		clic_layout1.setOnClickListener(new onclic());
		clic_layout2.setOnClickListener(new onclic());
		bt_back.setOnClickListener(new onclic());
		bt_jia.setOnClickListener(new onclic());
		bt_jian.setOnClickListener(new onclic());
		bt_pay.setOnClickListener(new onclic());
		itemid = getIntent().getStringExtra("itemid");
		dataLoad(new int[] { 1 });
	}

	public class onclic implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.actpay_confirmation.back:
				ActPay_ConfirmationAct.this.finish();
				break;
			case R.actpay_confirmation.btn_minus:
				break;
			case R.actpay_confirmation.btn_plus:
				break;
			case R.actpay_confirmation.bt_pay:
				dataLoad(new int[] { 2 });
				break;
			case R.actpay_confirmation.address_layout:
				Intent i = new Intent();
				i.putExtra("act", "ActPay_ConfirmationAct");
				i.setClass(ActPay_ConfirmationAct.this,
						ConsigneeAddressAct.class);
				startActivity(i);
				break;
			case R.actpay_confirmation.layout_sendtime:

				break;
			}
		}
	}

	private void setBackType(String type) {
		
		//layzh,layjh,laynh,layzfb
		if (type.equals("11")) {// 农行
			ico_back.setBackgroundResource(R.drawable.nyyh_nor);
			layzh.setVisibility(View.GONE);
			layjh.setVisibility(View.GONE);
			laynh.setVisibility(View.VISIBLE);
			layzfb.setVisibility(View.GONE);
			
			checkbox2.setChecked(false);
			nh_checkbox6.setChecked(true);
			jh_checkbox2.setChecked(false);
			zh_checkbox5.setChecked(false);
			
		} else if (type.equals("8")) {// 建行
			ico_back.setBackgroundResource(R.drawable.jsyh_nor);
			layzh.setVisibility(View.GONE);
			layjh.setVisibility(View.VISIBLE);
			laynh.setVisibility(View.GONE);
			layzfb.setVisibility(View.GONE);
			
			checkbox2.setChecked(false);
			nh_checkbox6.setChecked(false);
			jh_checkbox2.setChecked(true);
			zh_checkbox5.setChecked(false);
		} else if (type.equals("16")) {// 中国银行
			ico_back.setBackgroundResource(R.drawable.zgyh_nor);
			layzh.setVisibility(View.VISIBLE);
			layjh.setVisibility(View.GONE);
			laynh.setVisibility(View.GONE);
			layzfb.setVisibility(View.GONE);
			
			checkbox2.setChecked(false);
			nh_checkbox6.setChecked(false);
			jh_checkbox2.setChecked(false);
			zh_checkbox5.setChecked(true);
		}
		else{//支付宝
			layzh.setVisibility(View.VISIBLE);
			layjh.setVisibility(View.VISIBLE);
			laynh.setVisibility(View.VISIBLE);
			layzfb.setVisibility(View.VISIBLE);
			
			checkbox2.setChecked(false);
			nh_checkbox6.setChecked(false);
			jh_checkbox2.setChecked(false);
			zh_checkbox5.setChecked(false);
		}
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		// TODO Auto-generated method stub
		if (type == 1) {
			String[] str = (String[]) obj;
			address.setText(str[1]);
			addressid = str[4];
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("V3_GOSECKILL_NEW")) {
			Msg_Citem.Builder builder = (Msg_Citem.Builder) son.build;
			{
				if (!builder.getItemid().equals("0")) {
					itemid = builder.getItemid();// 活动ID，如果错误则为0
					name.setText(builder.getItemtitle()); // 商品名称
					img.setObj(builder.getItemimageurl()); // 图片url
					price.setText(builder.getItemprice()+"元"); // 价格
					backtype = builder.getItemtype();// 银行类型
					setBackType(backtype);
					dataLoad(new int[] { 3 });
					// builder.getPoints(); // 限购数量,不限购为0
				} else {
					if(builder.getItemid().equals("0")){
						AlertDialog.Builder alertDialog = new Builder(ActPay_ConfirmationAct.this);
						alertDialog.setMessage(builder.getOther1());
						alertDialog.setTitle("提示");
						alertDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						   @Override
						   public void onClick(DialogInterface dialog, int which) {
						    dialog.dismiss();
						    ActPay_ConfirmationAct.this.finish();
						   }
						  });
//						  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//						   @Override
//						   public void onClick(DialogInterface dialog, int which) {
//						    dialog.dismiss();
//						   }
//						  });

						alertDialog.create().show();
					}
//					Toast.makeText(ActPay_ConfirmationAct.this,
//							builder.getOther1(), Toast.LENGTH_SHORT).show();
				}
			}
		} else if (son.mgetmethod.equals("maddresslist")) {
			if (son.build != null) {
				Msg_Maddresslist.Builder builder = (Msg_Maddresslist.Builder) son.build;
				List<Msg_Maddress> addresslist = builder.getMaddressList();
				// String username = addresslist.get(0).getReceiver();
				String useraddress = addresslist.get(0).getDetailsaddress();
				// String usertel = addresslist.get(0).getMobile();
				address.setText(useraddress.equals("") ? "" :  useraddress);
				addressid = addresslist.get(0).getAddressid();
			} else {
				Toast.makeText(this, "您尚未设置您的收货地址!", Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("act", "ActPay_ConfirmationAct");
				i.setClass(ActPay_ConfirmationAct.this,
						ConsigneeAddressAct.class);
				startActivity(i);
			}
		} else if (son.mgetmethod.equals("V3_GOSECKILL_COMMIT_NEW")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				ordernum = retn.getErrorMsg();
				Intent i = new Intent(ActPay_ConfirmationAct.this,
						OrderEndAct.class);
				i.putExtra("classtype", "gouwu");
				i.putExtra("order_sn_main", ordernum);// 订单号
				i.putExtra("pay_type", backtype);// 银行类型
				i.putExtra("isTaoxinka", "0");
				i.putExtra("taoxkValue", "0");
				i.putExtra("isVcount", "0");
				i.putExtra("vcountValue", "0");
				startActivity(i);
				ActPay_ConfirmationAct.this.finish();
			} else {
				Toast.makeText(ActPay_ConfirmationAct.this, retn.getErrorMsg(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("V3_GOSECKILL_NEW",
					new String[][] { { "id", itemid }, { "uid", F.USER_ID } }), });
		}
		if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone(
					"V3_GOSECKILL_COMMIT_NEW", new String[][] {
							{ "uid", F.USER_ID }, { "id", itemid },
							{ "address_id", addressid } }), });
		}
		if (types[0] == 3) {
			this.loadData(new Updateone[] { new Updateone("MADDRESSLIST",
					new String[][] { { "userid", F.USER_ID },
							{ "default", "1" } }), });
		}
	}
}
