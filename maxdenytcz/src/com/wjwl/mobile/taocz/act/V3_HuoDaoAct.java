package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder.Builder;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_HuoDaoAct extends MActivity{
	 TczV3_HeadLayout head;
	 LinearLayout shangcheng;
	 TextView counts,showmsg_tv,needs,yue,xuni_yue,ydcoin,shangchengbi,alpay;
	 CheckBox chk1,chk3;
	 private Float floatxuni,floatcard,floattemp1,floattemp2,floattotal,lastcard,lastxuni,hdfk,ydbi;
	 private String orderNo,order_pay_type,state,hdfk2,zxzf,classtype,wmbusinessid;
	 float ydbitemp=(float)0.0;
	 RelativeLayout layout_tx,layout_xn;
	 Button btn_topay;
	 String order_sn_main, pay_type, isTaoxinka, taoxkValue, isVcount,vcountValue, isempty,
	 str_hdfk,umcount;
	 
	//checkbox1 淘心卡   checkbox3虚拟账户    checkbox2支付宝     jh_checkbox2建行
	 static int hd_ydcount=0;
	 
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_huodao);
		setId("V3_HuoDaoAct");
		head=(TczV3_HeadLayout) findViewById(R.v3_huodao.head);
		head.setRightButton3Text("淘心卡充值");
		head.setTitle("货到付款");
//		floatxuni=(float) 10.0;
//		floatcard=(float)5.0;
//		
//		hdfk=(float)8.0;
//		floattotal=hdfk;
		Intent i = getIntent();
		orderNo=i.getStringExtra("orderno");
		classtype=i.getStringExtra("classtype");
		
		pay_type = i.getStringExtra("pay_type");
		isTaoxinka = i.getStringExtra("isTaoxinka");
		taoxkValue = i.getStringExtra("taoxkValue");
		isVcount = i.getStringExtra("isVcount");
		vcountValue = i.getStringExtra("vcountValue");
		umcount=i.getStringExtra("umcount");
		
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setRightButton3Click(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(V3_HuoDaoAct.this,V3_ChongZhiAct.class);
				i.putExtra("actfrom", "V3_ZaiXianAct");
				startActivity(i);
			}
		});
		
		shangcheng=(LinearLayout) findViewById(R.v3_huodao.shangcheng);
		shangcheng.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(floattotal>0){
				// TODO Auto-generated method stub
				Intent i=new Intent(V3_HuoDaoAct.this,V3_ShangChengAct.class);
				i.putExtra("ydcount", hd_ydcount+"");
				i.putExtra("order_sn_main", orderNo);
				i.putExtra("actfrom", "V3_ZaiXianAct");
				startActivity(i);
				}
				else{
					Toast.makeText(V3_HuoDaoAct.this, "足够支付本次订单，无需使用移动商城币",Toast.LENGTH_SHORT).show();
				}
//				if(floattotal>0){
//					ydbi=(float)2.0;
//					ydbitemp=ydbitemp+ydbi;
//					hdfk=hdfk-ydbi;
//					floattotal=hdfk;
//					needs.setText(floattotal+"元");
//					ydcoin.setText("移动商城币支付:"+ydbitemp+"元");
//					chk1.setChecked(false);
//					chk3.setChecked(false);
//					showmsg_tv.setText("");
//				}
//				else{
//					Toast.makeText(V3_HuoDaoAct.this, "足够支付本次订单，无需使用移动商城币",Toast.LENGTH_SHORT).show();
//				}
				
			}
		});
		counts=(TextView) findViewById(R.v3_huodao.counts);
		showmsg_tv=(TextView) findViewById(R.v3_huodao.taoxin);
		needs=(TextView) findViewById(R.v3_huodao.needs);
		yue=(TextView) findViewById(R.v3_huodao.yue);
		xuni_yue=(TextView) findViewById(R.v3_huodao.xuni_yue);
		ydcoin=(TextView) findViewById(R.v3_huodao.ydcoin);
		btn_topay=(Button) findViewById(R.v3_huodao.topay);
		shangchengbi=(TextView) findViewById(R.v3_huodao.shangcheng_value);
		alpay=(TextView) findViewById(R.v3_huodao.pay);
		
		btn_topay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("classtype", classtype);
				i.putExtra("order_sn_main", orderNo);
				i.putExtra("pay_type", pay_type);
				i.putExtra("isTaoxinka", chk1.isChecked()==true?"1":"0");
				i.putExtra("taoxkValue", lastcard+"");
				i.putExtra("isVcount", chk3.isChecked()==true?"1":"0");
				i.putExtra("vcountValue", lastxuni+"");
				i.putExtra("umcout", umcount);
				i.setClass(getApplication(), OrderEndAct.class);
				startActivity(i);
				MobclickAgent.onEvent(V3_HuoDaoAct.this, "OrderPay4Now");
				
				V3_HuoDaoAct.this.finish();
			}
		});

		layout_xn=(RelativeLayout) findViewById(R.v3_huodao.layout_xn);
		layout_tx=(RelativeLayout) findViewById(R.v3_huodao.layout_tx);
		
		chk1=(CheckBox) findViewById(R.v3_huodao.tx_choose);
		chk3=(CheckBox) findViewById(R.v3_huodao.xuni_choose);
		
		
		chk1.setOnCheckedChangeListener(new checkboxListener());
		chk3.setOnCheckedChangeListener(new checkboxListener());
		
		dataLoad(null);
	}

	
	public class checkboxListener implements
	CompoundButton.OnCheckedChangeListener {

@Override
public void onCheckedChanged(CompoundButton buttonView,
		boolean isChecked) {
	switch (buttonView.getId()) {
	case R.v3_huodao.tx_choose://淘心卡
		if (isChecked) {
			if(chk3.isChecked()==true){
				if(floattemp1<0){
					floattemp2=floatcard+floattemp1;
					if(floattemp2>=0){
//						showmsg_tv.setText("本次虚拟账户支付"+floatxuni+"元。淘心卡支付："+floattemp1*(-1)+"元");
						alpay.setText("￥"+(Double.parseDouble(floatxuni+"")+Double.parseDouble((floattemp1*(-1))+""))+"元");
						lastcard=floatxuni;
						lastxuni=floattemp1*(-1);
						needs.setText((floattotal-floatxuni+floattemp1)+"元");
					}
					else{
						showmsg_tv.setText("您的账户余额不够支付，请选择其他支付方式。");
						Toast.makeText(V3_HuoDaoAct.this, "您的账户余额不够支付，请选择其他支付方式。", Toast.LENGTH_LONG).show();
						alpay.setText("￥"+floattotal+"元");
						needs.setText("￥"+(floattotal-floatxuni)+"元");
					}
				}else{
					chk1.setChecked(false);
				}
				
			}else{
				floattemp1=floatcard-floattotal;
				if(floattemp1>=0){
//					showmsg_tv.setText("本次淘心卡支付："+floattotal+"元");
					alpay.setText("￥"+floattotal+"元");
					lastcard=floattotal;
					lastxuni=(float) 0;
					needs.setText((floattotal-floattotal)+"元");
				}else{
					showmsg_tv.setText("您的淘心卡余额不够支付，请选择其他支付方式。");
					Toast.makeText(V3_HuoDaoAct.this, "您的淘心卡余额不够支付，请选择其他支付方式。", Toast.LENGTH_LONG).show();
					alpay.setText("￥"+floatcard+"元");
					needs.setText("￥"+(floattotal-floatcard)+"元");
				}
			}
		} 
		else{
//			showmsg_tv.setText("");
			alpay.setText("￥0元");
			needs.setText(floattotal+"元");
			chk3.setChecked(false);
		}

		break;
	case R.v3_huodao.xuni_choose://虚拟账户
		if (isChecked) {
			if(chk1.isChecked()==true){
				if(floattemp1<0){
					floattemp2=floatxuni+floattemp1;
					if(floattemp2>=0){
//						showmsg_tv.setText("本次淘心卡支付"+floatcard+"。虚拟账户支付："+floattemp1*(-1)+"元");
						alpay.setText("￥"+(Double.parseDouble(floatcard+"")+Double.parseDouble((floattemp1*(-1))+""))+"元");
						lastcard=floatcard;
						lastxuni=floattemp1*(-1);
						needs.setText((floattotal-floatcard+floattemp1)+"元");
					}
					else{
						showmsg_tv.setText("您的账户余额不够支付，请选择其他支付方式。");
						Toast.makeText(V3_HuoDaoAct.this, "您的账户余额不够支付，请选择其他支付方式。", Toast.LENGTH_LONG).show();
						alpay.setText("￥"+floattotal+"元");
						needs.setText("￥"+(floattotal-floatcard+floattemp1)+"元");
					}
				}
				else{
					chk3.setChecked(false);
				}
			}else{
				floattemp1=floatxuni-floattotal;
				if(floattemp1>=0){
//					showmsg_tv.setText("本次虚拟账户支付："+floattotal+"元");
					alpay.setText("￥"+floattotal+"元");
					lastcard=(float) 0;
					lastxuni=floattotal;
					needs.setText((floattotal-floattotal)+"元");
				}else{
					showmsg_tv.setText("您的虚拟账户余额不够支付，请选择其他支付方式。");
					Toast.makeText(V3_HuoDaoAct.this, "您的虚拟账户余额不够支付，请选择其他支付方式。", Toast.LENGTH_LONG).show();
					alpay.setText("￥"+floatxuni+"元");
					needs.setText("￥"+(floattotal-floatxuni)+"元");
				}
			}
		} else{
//			showmsg_tv.setText("");
			alpay.setText("￥0元");
			needs.setText(floattotal+"元");
			chk1.setChecked(false);
		}
		break;
	}
}
}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		if(type==1){
			shangchengbi.setText("移动商城币支付:"+obj.toString()+"元");
			hd_ydcount=hd_ydcount+1;
			
			ydbitemp=(Float.parseFloat(obj.toString()));
			hdfk=hdfk-ydbitemp;
			if(hdfk<0){
				floattotal=(float)0;
			}else{
				floattotal=hdfk;
			}
			needs.setText(floattotal+"元");
			ydcoin.setText("移动商城币支付:"+ydbitemp+"元");
			chk1.setChecked(false);
			chk3.setChecked(false);
//			showmsg_tv.setText("");
			alpay.setText("￥0元");
		}
		if(type==2){
			dataLoad(null);
		}
	}
	
	
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("PFROMORDER")) {
			Msg_Payorder.Builder build=(Builder) son.build;
			str_hdfk=build.getHdfk();
			floatcard=Float.parseFloat((build.getCard().equals("")?"0":build.getCard()));//淘心卡余额
			floatxuni=Float.parseFloat((build.getXuni().equals("")?"0":build.getXuni()));//虚拟账户
			
			hdfk=Float.parseFloat(str_hdfk);//订单总金额
			floattotal=hdfk;
			
			counts.setText(hdfk+"元");
			yue.setText(floatcard+"元");
			xuni_yue.setText(floatxuni+"元");
			needs.setText(hdfk+"元");
			
			
			if(floatxuni==0){
				layout_xn.setVisibility(View.GONE);
			}else{
				if(floatxuni>=floattotal&&floatxuni>=floatcard){
					chk1.setChecked(false);
					chk3.setChecked(true);
				}
				else if(floatxuni>=floattotal&&floatxuni<floatcard){
					chk1.setChecked(true);
					chk3.setChecked(false);
				}
				else{
					chk1.setChecked(false);
					chk3.setChecked(false);
				}
			}
	        if(floatcard==0){
	        	layout_tx.setVisibility(View.GONE);
			}else{
				if(floatcard>=floattotal&&floatcard>=floatxuni){
					chk1.setChecked(true);
					chk3.setChecked(false);
				}
				else if(floatcard>=floattotal&&floatcard<floatxuni){
					chk1.setChecked(false);
					chk3.setChecked(true);
				}
				else{
					chk1.setChecked(false);
					chk3.setChecked(false);
				}
			}

			for(MHandler hand:Frame.HANDLES.get("ShoppingCartAct")){
				hand.reload();
			}
		}
	}
	
	
	@Override
	public void dataLoad(int[] types) {
			this.loadData(new Updateone[] { new Updateone("PFROMORDER",
					new String[][] { { "userid", F.USER_ID},{},{"order_sn_main",orderNo}}), });
	}

}
