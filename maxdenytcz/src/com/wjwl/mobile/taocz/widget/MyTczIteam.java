package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyTczIteam extends LinearLayout{
	TextView ts;
	ImageView img;
	public MyTczIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.mytczitem, this);
//		ts=(TextView) findViewById(R.mytczitem.ts);
		img=(ImageView) findViewById(R.mytczitem.img);
	}
	public void setData(int i,String s){
		img.setImageResource(i);
//		ts.setText(s);
	}
}
