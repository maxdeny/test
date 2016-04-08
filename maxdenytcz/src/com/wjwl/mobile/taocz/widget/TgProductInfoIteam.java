package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TgProductInfoIteam extends LinearLayout {
	TextView name,number,price;
	LinearLayout linear;
	public TgProductInfoIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.tgproductinfoitem, this);
		name=(TextView) findViewById(R.tgproductinfoitem.name);
		number=(TextView) findViewById(R.tgproductinfoitem.number);
		price=(TextView) findViewById(R.tgproductinfoitem.price);
		linear=(LinearLayout) findViewById(R.tgproductinfoitem.linear);
	}
	public void setData(String n,String n1,String p){
		name.setText(n);
		number.setText("预定数量："+n1);
		price.setText("单价：￥"+p);
	}
}
