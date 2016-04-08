package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TuangouProductInfoAct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TuangouOrderItem extends LinearLayout {
	TextView name,date,ordernumber,numbers,who,money,phone,state;
	LinearLayout linear;
	String order_num;
	public TuangouOrderItem(Context context) {
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
		money=(TextView) findViewById(R.waimaiorderitem.phone);
		numbers=(TextView) findViewById(R.waimaiorderitem.beitai);
		state=(TextView) findViewById(R.waimaiorderitem.money);
		numbers.setVisibility(View.VISIBLE);
		who=(TextView) findViewById(R.waimaiorderitem.who);
		who.setVisibility(View.GONE);
		linear=(LinearLayout) findViewById(R.waimaiorderitem.linear);
		linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
						Intent i=new Intent(getContext(),TuangouProductInfoAct.class);
						i.putExtra("ordernumber",order_num);
						getContext().startActivity(i);
			}
		});
	}
	public void setData(String n,String d,String o,String m,String nb,String s){
		name.setText(n);
		date.setText("订单日期:"+d);
		ordernumber.setText("订单号码:"+o);
		order_num=o;
		String ls="总价:￥"+m;
		SpannableStringBuilder style=new SpannableStringBuilder(ls); //SpannableStringBuilder实现CharSequence接口 
    	style.setSpan(new ForegroundColorSpan(Color.RED), 3, ls.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE ); 
		money.setText(style);
		numbers.setText("共"+nb+"件商品");
		state.setText("订单状态:"+s);
	}
}

