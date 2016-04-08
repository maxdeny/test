package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BianmingItem extends LinearLayout{
	ImageView img;
	TextView text;
	public BianmingItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view =flater.inflate(R.layout.bianmingitem, this);
		img=(ImageView) view.findViewById(R.bianmingitem.img);
		text=(TextView) findViewById(R.bianmingitem.text);
	}
	public void setData(int i,String t){
		img.setImageResource(i);
		text.setText(t);
	}

}
