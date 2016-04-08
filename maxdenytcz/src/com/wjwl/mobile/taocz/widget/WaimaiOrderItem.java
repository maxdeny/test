package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WaimaiOrderItem extends LinearLayout {
	TextView name,date,ordernumber,who,money,phone,state;
	public WaimaiOrderItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.waimaiorderitem, this);
		name=(TextView) findViewById(R.waimaiorderitem.name);
		date=(TextView) findViewById(R.waimaiorderitem.date);
		ordernumber=(TextView) findViewById(R.waimaiorderitem.ordernuber);
		who=(TextView) findViewById(R.waimaiorderitem.who);
		money=(TextView) findViewById(R.waimaiorderitem.money);
		state=(TextView) findViewById(R.waimaiorderitem.state);
		phone=(TextView) findViewById(R.waimaiorderitem.phone);
	}
	public void setData(String n,String d,String o,String w,String p,String m,String s){
		name.setText(n);
		date.setText("订单日期 :"+d);
		ordernumber.setText("订单号码 :"+o);
		who.setText("预订人 :"+w);
		phone.setText("预订人手机 :"+p);
		String ls="共计 :￥"+m;
		SpannableStringBuilder style=new SpannableStringBuilder(ls); //SpannableStringBuilder实现CharSequence接口 
    	style.setSpan(new ForegroundColorSpan(Color.RED), 3, ls.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE ); 
		money.setText(style);
		state.setText(s);
	}
}
