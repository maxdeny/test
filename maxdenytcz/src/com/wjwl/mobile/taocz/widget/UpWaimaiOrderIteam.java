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

public class UpWaimaiOrderIteam extends LinearLayout {
	TextView name,date,ordernumber,who,money,phone,state,beizu;
	String id;
	public UpWaimaiOrderIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.upwaimaiorderitem, this);
		name=(TextView) findViewById(R.upmaimaiorderitem.name);
		date=(TextView) findViewById(R.upmaimaiorderitem.date);
		ordernumber=(TextView) findViewById(R.upmaimaiorderitem.ordernuber);
		who=(TextView) findViewById(R.upmaimaiorderitem.who);
		money=(TextView) findViewById(R.upmaimaiorderitem.money);
		state=(TextView) findViewById(R.upmaimaiorderitem.state);
		phone=(TextView) findViewById(R.upmaimaiorderitem.phone);
		beizu=(TextView) findViewById(R.upmaimaiorderitem.beizu);
	}
	public void setData(String n,String d,String o,String w,String p,String m,String s,String b){
		name.setText(n);
		date.setText("订餐时间 :"+d);
		ordernumber.setText("订单明细 :"+o);
		who.setText("送餐地址 :"+w);
		phone.setText("付款方式 :"+p);
		String ls="消费金额 :￥"+m;
		SpannableStringBuilder style=new SpannableStringBuilder(ls); //SpannableStringBuilder实现CharSequence接口 
    	style.setSpan(new ForegroundColorSpan(Color.RED), 5, ls.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE ); 
		money.setText(style);
		state.setText(s);
		beizu.setText("备注:"+b);
	}
	public void setId(String id){
		this.id=id;
	}
}

