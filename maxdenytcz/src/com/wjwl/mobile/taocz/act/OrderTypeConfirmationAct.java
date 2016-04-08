package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder.Builder;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class OrderTypeConfirmationAct extends MActivity {
	private TextView proinfo, allpay1, username, useraddress, usertel,
			taoxinka, allpay2,orderno,xuniyue,showmsg_tv;
	private CheckBox chk1, chk2,chk3,chk4;
	private String orderNo,order_pay_type,state,hdfk,zxzf,classtype,wmbusinessid;
    private Button bt_pay;
    private Float floatxuni,floatcard,floattemp1,floattemp2,floattotal,lastcard,lastxuni;
    private RelativeLayout   ordertype_view1,ordertype_view2;
    private String[] renturn;
    
    
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.ordertype_confirmation);
		setId("OrderTypeConfirmationAct");
		orderno=(TextView) findViewById(R.ordertype_confirmation.orderno);
		proinfo = (TextView) findViewById(R.ordertype_confirmation.proinfo);
		allpay1 = (TextView) findViewById(R.ordertype_confirmation.allpay1);
		username = (TextView) findViewById(R.ordertype_confirmation.username);
		useraddress = (TextView) findViewById(R.ordertype_confirmation.useraddress);
		usertel = (TextView) findViewById(R.ordertype_confirmation.usertel);
		taoxinka = (TextView) findViewById(R.ordertype_confirmation.taoxinka);
		xuniyue = (TextView) findViewById(R.ordertype_confirmation.xuniyue);
		allpay2 = (TextView) findViewById(R.ordertype_confirmation.allpay2);
		chk1 = (CheckBox) findViewById(R.ordertype_confirmation.checkbox1);
		chk2 = (CheckBox) findViewById(R.ordertype_confirmation.checkbox2);
		chk3 = (CheckBox) findViewById(R.ordertype_confirmation.checkbox3);
		bt_pay = (Button) findViewById(R.ordertype_confirmation.bt_pay);
		showmsg_tv = (TextView) findViewById(R.ordertype_confirmation.showmsg);
		chk4 = (CheckBox) findViewById(R.ordertype_confirmation.jh_checkbox2);
		

		ordertype_view1=(RelativeLayout) findViewById(R.ordertype_confirmation.view1);
		ordertype_view2=(RelativeLayout) findViewById(R.ordertype_confirmation.view2);
		
		orderNo=getIntent().getStringExtra("orderno");
		classtype=getIntent().getStringExtra("classtype");
		
		wmbusinessid= getIntent().getStringExtra("wmbusinessid");		
		
//		addressid=getIntent().getStringExtra("addressid");
//		userid=getIntent().getStringExtra("userid");
//		order_pay_type=getIntent().getStringExtra("order_pay_type");
//		total=getIntent().getStringExtra("total");
//		
//		floattotal=Float.parseFloat(total);
//		
//		usernamestring=getIntent().getStringExtra("username");
//		useraddressstring=getIntent().getStringExtra("useraddress");
//		usertelstring=getIntent().getStringExtra("usertel");
//		post_invoiceTitle=getIntent().getStringExtra("fptt");

		
//		if(order_pay_type.equals("4")){
//			state="在线支付";
//		}else{
//			state="货到付款";
//		}

		chk1.setOnCheckedChangeListener(new checkboxListener());
		chk2.setOnCheckedChangeListener(new checkboxListener());
		chk3.setOnCheckedChangeListener(new checkboxListener());
		chk4.setOnCheckedChangeListener(new checkboxListener());
		
		bt_pay.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				 i.putExtra("order_sn_main",orderNo );
				 //支付宝  钱不够默认支付宝支付
				 if(chk2.isChecked()){
					 chk4.setChecked(false);
					 i.putExtra("pay_type","4" );
				 }
				 else{
					 i.putExtra("pay_type","4" );
				 }//建行支付
				 if(chk4.isChecked()){
					 chk2.setChecked(false);
					 i.putExtra("pay_type","8" );
				 }
				 else{
					 i.putExtra("pay_type","4" );
				 }//淘心卡
				if(chk1.isChecked()){
					i.putExtra("isTaoxinka","1" );
					i.putExtra("taoxkValue",lastcard+"" );
				}
				else{
					i.putExtra("isTaoxinka","0" );
					i.putExtra("taoxkValue","0" );
				}//虚拟帐户
				if(chk3.isChecked()){
					 i.putExtra("isVcount","1" );
					 i.putExtra("vcountValue",lastxuni+"" );
				}
				else{
					i.putExtra("isVcount","0" );
					i.putExtra("vcountValue","0" );
				}
				 if(chk1.isChecked()==false&&chk3.isChecked()==false&&chk2.isChecked()==false&&chk4.isChecked()==false){
					 Toast.makeText(OrderTypeConfirmationAct.this, "请选择一种支付方式!", Toast.LENGTH_SHORT).show();
				 }else{
					 i.putExtra("classtype", classtype);
					 if(wmbusinessid!=null){
						 i.putExtra("wmbusinessid", wmbusinessid);
					 }
					 i.setClass(OrderTypeConfirmationAct.this, OrderEndAct.class);
						startActivity(i);
				 }
			}});
		dataLoad(null);
	}

	public class checkboxListener implements
			CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.ordertype_confirmation.checkbox1://淘心卡
				if (isChecked) {
					if(chk3.isChecked()==true){
						if(floattemp1<0){
							floattemp2=floatcard+floattemp1;
							if(floattemp2>=0){
								showmsg_tv.setText("本次虚拟账户支付"+floatxuni+"。淘心卡支付："+floattemp1*(-1));
								lastcard=floatxuni;
								lastxuni=floattemp1*(-1);
							}
							else{
								showmsg_tv.setText("您的账户余额不够支付，请选择其他支付方式。");
							}
						}else{
							chk1.setChecked(false);
						}
						
					}else{
						floattemp1=floatcard-floattotal;
						if(floattemp1>=0){
							showmsg_tv.setText("本次淘心卡支付："+floattotal);
							lastcard=floattotal;
							lastxuni=(float) 0;
						}else{
							showmsg_tv.setText("您的淘心卡余额不够支付，请选择其他支付方式。");
						}
					}
				} 
				else{
					showmsg_tv.setText("");
					chk3.setChecked(false);
				}

				break;
			case R.ordertype_confirmation.checkbox3://虚拟账户
				if (isChecked) {
					if(chk1.isChecked()==true){
						if(floattemp1<0){
							floattemp2=floatxuni+floattemp1;
							if(floattemp2>=0){
								showmsg_tv.setText("本次淘心卡支付"+floatcard+"。虚拟账户支付："+floattemp1*(-1));
								lastcard=floatcard;
								lastxuni=floattemp1*(-1);
							}
							else{
								showmsg_tv.setText("您的账户余额不够支付，请选择其他支付方式。");
							}
						}
						else{
							chk3.setChecked(false);
						}
					}else{
						floattemp1=floatxuni-floattotal;
						if(floattemp1>=0){
							showmsg_tv.setText("本次虚拟账户支付："+floattotal);
							lastcard=(float) 0;
							lastxuni=floattotal;
						}else{
							showmsg_tv.setText("您的虚拟账户余额不够支付，请选择其他支付方式。");
						}
					}
				} else{
					showmsg_tv.setText("");
					chk1.setChecked(false);
				}
				break;
			case R.ordertype_confirmation.checkbox2://支付宝
				if(chk2.isChecked()){
					chk4.setChecked(false);
				}
				break;
			case R.ordertype_confirmation.jh_checkbox2://建行
				if(chk4.isChecked()){
					chk2.setChecked(false);
				}
				break;
			}
		}
	}

	//http://tcz/api/mobile/tao.php?app=payorder&act=PFROMORDER&debug=1&flg=1&userid=10&order_sn_main=1111	
	@Override
	public void dataLoad(int[] types) {
//		if(classtype.equals("shenghuo")){
//			this.loadData(new Updateone[] { new Updateone("TGPFROMORDER",
//					new String[][] { { "userid", F.USER_ID},{},{"order_sn_main",orderNo}}), });
//		}
//		else{
			this.loadData(new Updateone[] { new Updateone("PFROMORDER",
					new String[][] { { "userid", F.USER_ID},{},{"order_sn_main",orderNo}}), });
//		}
		
	}


	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && (son.mgetmethod.equals("tgpfromorder")||son.mgetmethod.equals("PFROMORDER"))) {
			Msg_Payorder.Builder build=(Builder) son.build;
			hdfk=build.getHdfk();
//			build.getZxzf();
//			build.getTotal();
			//zxzf=build.getZxzf();
			proinfo.setText("您需要支付：");
			allpay1.setText(build.getTotal()+"元");
			username.setText(build.getAddress().getReceiver().equals("")?"":build.getAddress().getReceiver());
			useraddress.setText(build.getAddress().getDetailsaddress().equals("")?"":build.getAddress().getDetailsaddress());
			usertel.setText(build.getAddress().getMobile().equals("")?"":build.getAddress().getMobile());
			floattotal=Float.parseFloat(hdfk);//订单总金额
			allpay2.setText(floattotal+"元");
			
			floatcard=Float.parseFloat((build.getCard().equals("")?"0":build.getCard()));//淘心卡余额
			floatxuni=Float.parseFloat((build.getXuni().equals("")?"0":build.getXuni()));//虚拟账户
			taoxinka.setText(floatcard+"");
			xuniyue.setText(floatxuni+"");
			if(floatxuni==0){
				ordertype_view2.setVisibility(8);
			}else{
				if(floatxuni>=floattotal&&floatxuni>=floatcard){
					chk1.setChecked(false);
					chk2.setChecked(false);
					chk3.setChecked(true);
					chk4.setChecked(false);
				}
				else if(floatxuni>=floattotal&&floatxuni<floatcard){
					chk1.setChecked(true);
					chk2.setChecked(false);
					chk3.setChecked(false);
					chk4.setChecked(false);
				}
				else{
					chk1.setChecked(false);
					chk2.setChecked(true);
					chk3.setChecked(false);
					chk4.setChecked(false);
				}
			}
			if(floatcard==0){
				ordertype_view1.setVisibility(8);
			}else{
				if(floatcard>=floattotal&&floatcard>=floatxuni){
					chk1.setChecked(true);
					chk2.setChecked(false);
					chk3.setChecked(false);
					chk4.setChecked(false);
				}
				else if(floatcard>=floattotal&&floatcard<floatxuni){
					chk1.setChecked(false);
					chk2.setChecked(false);
					chk3.setChecked(true);
					chk4.setChecked(false);
				}
				else{
					chk1.setChecked(false);
					chk2.setChecked(true);
					chk3.setChecked(false);
					chk4.setChecked(false);
				}
			}

//			if (build.getErrorCode() == 0) {
//				orderno.setText("订单号:"+orderNo);
//				Toast.makeText(OrderTypeConfirmationAct.this, "订单提交成功",
//						Toast.LENGTH_LONG).show();
//			}else{
//				Toast.makeText(OrderTypeConfirmationAct.this, renturn[0],
//						Toast.LENGTH_LONG).show();
//			}
			for(MHandler hand:Frame.HANDLES.get("ShoppingCartAct")){
				hand.reload();
			}
		}
	}
}
