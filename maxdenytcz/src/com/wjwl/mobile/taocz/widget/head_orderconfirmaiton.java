package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class head_orderconfirmaiton extends LinearLayout {
	public TextView username, useraddress, usertel;
	public  RelativeLayout layout;
	public head_orderconfirmaiton(Context context) {
		super(context);
		initview();
	}

	public head_orderconfirmaiton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.head_orderconfirmation, this);
		layout=(RelativeLayout)findViewById(R.head_orderconfirmation.clic_layout1);
		username = (TextView) findViewById(R.head_orderconfirmation.username);
		useraddress = (TextView) findViewById(R.head_orderconfirmation.useraddress);
		usertel = (TextView) findViewById(R.head_orderconfirmation.tel);
//		layout.setOnClickListener(new OnClickListener(){
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i=new Intent();
//				i.setClass(getContext(), ConsigneeAddressAct.class);
//				getContext().startActivity(i);
//			}
//			
//		});
	}

	public void setUsername(CharSequence text) {
		this.username.setText(text);
	}

	public void setUseraddress(CharSequence text) {
		this.useraddress.setText(text);
	}

	public void setUsertel(CharSequence text) {
		this.usertel.setText(text);
	}
	public void setUsername(String text) {
		this.username.setText(text);
	}

	public void setUseraddress(String text) {
		this.useraddress.setText(text);
	}

	public void setUsertel(String text) {
		this.usertel.setText(text);
	}
}
