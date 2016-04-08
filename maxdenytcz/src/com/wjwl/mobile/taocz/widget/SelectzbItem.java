package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectzbItem extends LinearLayout{
	ImageView img;
	TextView text;
	LinearLayout linear;
	public SelectzbItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.selectzbitem, this);
		img=(ImageView) findViewById(R.selectzbitem.img);
		text=(TextView) findViewById(R.selectzbitem.text);
		linear=(LinearLayout) findViewById(R.selectzbitem.linear);
	}
	public void setData(int i,String s){
		img.setBackgroundResource(i);
		text.setText(s);
	}
}
