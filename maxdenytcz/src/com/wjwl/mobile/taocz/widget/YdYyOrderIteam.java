package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.YdYyOrderInfoAct;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YdYyOrderIteam extends LinearLayout {
	TextView name,paytime,eattime,state;
	String orderno;
	LinearLayout linear;
	public YdYyOrderIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.ydyyorderitem, this);
		name=(TextView) findViewById(R.ydyyorderitem.name);
		paytime=(TextView) findViewById(R.ydyyorderitem.paytime);
		eattime=(TextView) findViewById(R.ydyyorderitem.eattime);
		state=(TextView) findViewById(R.ydyyorderitem.state);
		linear=(LinearLayout) findViewById(R.ydyyorderitem.linear);
		linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getContext(),YdYyOrderInfoAct.class);
				i.putExtra("orderno", orderno);
				getContext().startActivity(i);
			}
		});
	}
	public void setData(String n,String p,String e,String s){
		name.setText(n);
		paytime.setText("下单时间:"+p);
		eattime.setText("就餐时间:"+e);
		state.setText("订单状态:"+s);
	}
	public void setId(String orderno){
		this.orderno=orderno;
	}
}
