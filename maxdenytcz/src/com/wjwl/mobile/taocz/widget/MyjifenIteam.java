package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyjifenIteam extends LinearLayout {
	TextView jifencount,starttime,fromorder;
	public MyjifenIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.taoxincarditem, this);
		jifencount=(TextView) findViewById(R.taoxincarditem.cardsnumber);
		starttime=(TextView) findViewById(R.taoxincarditem.money);
		fromorder=(TextView) findViewById(R.taoxincarditem.time);
	}
	public void setData(String c,String m,String t){
		jifencount.setText("积分数："+c);
		starttime.setText("生效时间：￥"+m);
		fromorder.setText("来源包裹号："+t);
	}

}
