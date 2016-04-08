package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YouhuiItem extends LinearLayout {
	ImageView img;
	TextView name,money,time;
	public YouhuiItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.youhuiitem, this);
//		img=(ImageView) findViewById(R.youhuiitem.img);
		name=(TextView) findViewById(R.youhuiitem.name);
		money=(TextView) findViewById(R.youhuiitem.money);
		time=(TextView) findViewById(R.youhuiitem.time);
		
	}
	public void setData(String n,String m,String t){
//		img.setBackgroundResource(i);
		name.setText(n);
		money.setText("￥"+m);
		time.setText("截止日期:"+t);
	}

}
