package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CanweiItem extends LinearLayout{
	ImageView img,star_1,star_2,star_3,star_4,star_5,youimg,huiimg;
	TextView title,price,style,juli;
	
	public CanweiItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view =flater.inflate(R.layout.canweiitem, this);
		img=(ImageView) findViewById(R.canweiitem.img);
		star_1=(ImageView) findViewById(R.canweiitem.star_1);
		star_2=(ImageView) findViewById(R.canweiitem.star_2);
		star_3=(ImageView) findViewById(R.canweiitem.star_3);
		star_4=(ImageView) findViewById(R.canweiitem.star_4);
		star_5=(ImageView) findViewById(R.canweiitem.star_5);
		youimg=(ImageView) findViewById(R.canweiitem.youimg);
		huiimg=(ImageView) findViewById(R.canweiitem.huiimg);
		title=(TextView) findViewById(R.canweiitem.title);
		price=(TextView) findViewById(R.canweiitem.price);
		style=(TextView) findViewById(R.canweiitem.style);
		juli=(TextView) findViewById(R.canweiitem.juli);
	}
	public void setData(int i,String t,String p,String s,String j){
		img.setImageResource(i);
		title.setText(t);
		price.setText(p);
		style.setText(s);
		juli.setText(j);
	}
	public void setStar(String text) {
		switch (Integer.parseInt(text)) {
		case 0:
			star_1.setBackgroundResource(R.drawable.star_empty);
			star_2.setBackgroundResource(R.drawable.star_empty);
			star_3.setBackgroundResource(R.drawable.star_empty);
			star_4.setBackgroundResource(R.drawable.star_empty);
			star_5.setBackgroundResource(R.drawable.star_empty);
			break;
		case 1:
			star_1.setBackgroundResource(R.drawable.star_full);
			star_2.setBackgroundResource(R.drawable.star_empty);
			star_3.setBackgroundResource(R.drawable.star_empty);
			star_4.setBackgroundResource(R.drawable.star_empty);
			star_5.setBackgroundResource(R.drawable.star_empty);
			break;
		case 2:
			star_1.setBackgroundResource(R.drawable.star_full);
			star_2.setBackgroundResource(R.drawable.star_full);
			star_3.setBackgroundResource(R.drawable.star_empty);
			star_4.setBackgroundResource(R.drawable.star_empty);
			star_5.setBackgroundResource(R.drawable.star_empty);
			break;
		case 3:
			star_1.setBackgroundResource(R.drawable.star_full);
			star_2.setBackgroundResource(R.drawable.star_full);
			star_3.setBackgroundResource(R.drawable.star_full);
			star_4.setBackgroundResource(R.drawable.star_empty);
			star_5.setBackgroundResource(R.drawable.star_empty);
			break;
		case 4:
			star_1.setBackgroundResource(R.drawable.star_full);
			star_2.setBackgroundResource(R.drawable.star_full);
			star_3.setBackgroundResource(R.drawable.star_full);
			star_4.setBackgroundResource(R.drawable.star_full);
			star_5.setBackgroundResource(R.drawable.star_empty);
			break;
		case 5:
			star_1.setBackgroundResource(R.drawable.star_full);
			star_2.setBackgroundResource(R.drawable.star_full);
			star_3.setBackgroundResource(R.drawable.star_full);
			star_4.setBackgroundResource(R.drawable.star_full);
			star_5.setBackgroundResource(R.drawable.star_full);
			break;
		}
	}

}
