package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjwl.mobile.taocz.R;

public class TopsecondItem extends LinearLayout{
	TextView text,number;
	ImageView img;
	LinearLayout choose;
	String id,mark;
	public TopsecondItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public TopsecondItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater inflater=LayoutInflater.from(getContext());
		View view=inflater.inflate(R.layout.topseconditem, this);
		text=(TextView) view.findViewById(R.topseconditem.text);
		choose=(LinearLayout) view.findViewById(R.topseconditem.choose); 
//		choose.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_bg));
		number=(TextView) view.findViewById(R.topseconditem.number);
		
	}
	public void setText(String s){
		text.setText(s);
	}
	public void setId(String s){
		id=s;
	}
	public void setMark(String mark){
		this.mark=mark;
		if(mark.equals("1"))
			number.setVisibility(View.VISIBLE);
		else 
			number.setVisibility(View.INVISIBLE);
	}
	public void setNumber(String nb){
		number.setText(nb);
	}

}
