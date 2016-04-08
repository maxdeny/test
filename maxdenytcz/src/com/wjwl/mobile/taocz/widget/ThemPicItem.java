package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

public class ThemPicItem extends LinearLayout{
	MImageView img;
	String id,jumptype;
	public ThemPicItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.thempicitem, this);
		img=(MImageView) findViewById(R.thempicitem.img1);
//		img.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//			
//		}
//		});
	}
	
	public void setImg(String imgurl){
		img.setObj(imgurl);
	}
	public void setData(String imgurl,String id){
		img.setObj(imgurl);
		this.id=id;
	}

}

