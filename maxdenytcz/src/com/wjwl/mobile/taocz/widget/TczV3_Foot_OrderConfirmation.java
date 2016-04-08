package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_OrderConfirmationAct;
import com.wjwl.mobile.taocz.act.V3_BeiZhuAct;
import com.wjwl.mobile.taocz.act.V3_FaPiaoAct;
import com.wjwl.mobile.taocz.act.V3_YouHuiAct;
import com.wjwl.mobile.taocz.act.V3_ZhiFuAct;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TczV3_Foot_OrderConfirmation extends LinearLayout {
	public EditText ed_fapiao, ed_beizhu;
	public TextView paytype, yhq, fapiao, beizhu,allprice;
	public RelativeLayout layout_paytype, layout_yhq;
	public LinearLayout layout_fapiao, layout_beizhu;
	public Button bt_submit;
	public TczV3_Foot_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
		// TODO Auto-generated constructor stub
	}

	public TczV3_Foot_OrderConfirmation(Context context) {
		super(context);
		initview();
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.tczv3_foot_orderconfirmation, this);
		layout_fapiao = (LinearLayout) findViewById(R.tczv3.layout_fapiao);
		layout_beizhu = (LinearLayout) findViewById(R.tczv3.layout_beizhu);
		layout_yhq = (RelativeLayout) findViewById(R.tczv3.layout_yhq);
		layout_paytype = (RelativeLayout) findViewById(R.tczv3.layout_paytype);
		allprice = (TextView) findViewById(R.tczv3.allprice);
		paytype = (TextView) findViewById(R.tczv3.paytype);
		yhq = (TextView) findViewById(R.tczv3.yhq);
		fapiao = (TextView) findViewById(R.tczv3.fapiao);
		beizhu = (TextView) findViewById(R.tczv3.beizhu);
		bt_submit= (Button) findViewById(R.tczv3.bt_submit);
		bt_submit.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0).sent(8, "");
			}
		});
		layout_fapiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getContext(), V3_FaPiaoAct.class);
				getContext().startActivity(i);
			}
		});
		layout_beizhu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getContext(), V3_BeiZhuAct.class);
				getContext().startActivity(i);
			}
		});
		layout_yhq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getContext(), V3_YouHuiAct.class);
				getContext().startActivity(i);
			}
		});
		layout_paytype.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TczV3_OrderConfirmationAct.ishuodao.equals("1")){
					Toast.makeText(getContext(), "您有不支持货到的商品~", Toast.LENGTH_SHORT).show();
				}
				else{
					Intent i = new Intent();
					i.setClass(getContext(), V3_ZhiFuAct.class);
					getContext().startActivity(i);
				}
			
			}
		});
	}

	public void setFaPiao(String text) {
		this.fapiao.setText(text);
	}

	public void setPayType(String text) {
		this.paytype.setText(text);
	}

	public void setYHQ(String text) {
		this.yhq.setText(text);
	}

	public void setBeiZhu(String text) {
		this.beizhu.setText(text);
	}

	public void setTotalMoney(String count,String total,String yunfei) {
		this.allprice.setText("共计"+count+"件商品,￥"+total+",运费"+yunfei);
	}
	
	public String getFaPiao() {
		return fapiao.getText().toString().trim();
	}

	public String getPayType() {
		return paytype.getText().toString().trim();
	}

	public String getYHQ() {
		return yhq.getText().toString().trim();
	}

	public String getBeiZhu() {
		return beizhu.getText().toString().trim();
	}
	
}
