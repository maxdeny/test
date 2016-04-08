package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaoxinCardIteam extends LinearLayout {
	TextView cardsnumber,money,time,remainde,ordernumber;
	public TaoxinCardIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.taoxincarditem, this);
		cardsnumber=(TextView) findViewById(R.taoxincarditem.cardsnumber);
		money=(TextView) findViewById(R.taoxincarditem.money);
		time=(TextView) findViewById(R.taoxincarditem.time);
		remainde=(TextView) findViewById(R.taoxincarditem.remainde);
		ordernumber=(TextView) findViewById(R.taoxincarditem.ordernumber);
	}
	public void setData(String o, String c,String m,String t,String r){
		if(null!=o&&!o.equals("")){
			ordernumber.setVisibility(VISIBLE);
			ordernumber.setText("订单号:"+o);
			cardsnumber.setText("卡号:"+c);
			money.setText("面额:￥"+m);
			time.setText("有效时间:"+t);
			remainde.setText("余额:￥"+r);
		}
		else{
			cardsnumber.setText("卡号："+c);
			money.setText("消费额：￥"+m);
			time.setText("使用时间："+t);
			remainde.setText("余额：￥"+r);
		}
	}

}
