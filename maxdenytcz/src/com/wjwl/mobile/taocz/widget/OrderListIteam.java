package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.JyTjOrderAct;
import com.wjwl.mobile.taocz.act.MyOrderDetailsAct;
import com.wjwl.mobile.taocz.act.MyOrderReservationDetailsAct;
import com.wjwl.mobile.taocz.act.TuangouOrderAct;
import com.wjwl.mobile.taocz.act.YdYyOrderAct;
//import com.wjwl.mobile.taocz.act.UpWaimaiOrderAct;

public class OrderListIteam extends LinearLayout{
	TextView name,number;
	LinearLayout linear;
	public OrderListIteam(Context context) {
		super(context);
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.orderlistitem, this);
		linear=(LinearLayout) findViewById(R.orderlistitem.linear);
		number=(TextView) findViewById(R.orderlistitem.number);
		name=(TextView) findViewById(R.orderlistitem.name);
		linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(name.getText().equals("外卖订单")){
//					Intent i=new Intent(getContext(),UpWaimaiOrderAct.class);//WaimaiOrderAct
//					getContext().startActivity(i);
				}else if(name.getText().equals("话费订单")){
//					Intent i=new Intent(getContext(),HuafeiOrderAct.class);
//					getContext().startActivity(i);
				}else if(name.getText().equals("团购订单")){
					Intent i=new Intent(getContext(),TuangouOrderAct.class);
					getContext().startActivity(i);
				}else if(name.getText().equals("购物订单")){
					Intent i=new Intent(getContext(),MyOrderDetailsAct.class);
					i.putExtra("type", "1");
					getContext().startActivity(i);
				}else if(name.getText().equals("便民预约")){
					Intent i=new Intent(getContext(),MyOrderReservationDetailsAct.class);
					i.putExtra("type", "3");
					getContext().startActivity(i);
				}else if(name.getText().equals("酒店订单")){
					Intent i=new Intent(getContext(),JyTjOrderAct.class);
					getContext().startActivity(i);
				}else if(name.getText().equals("预定预约订单")){
					Intent i=new Intent(getContext(),YdYyOrderAct.class);
					getContext().startActivity(i);
				}
			}
		});
	}
	public void setData(String s,String s1){
		name.setText(s);
		number.setText(s1);
	}
}
