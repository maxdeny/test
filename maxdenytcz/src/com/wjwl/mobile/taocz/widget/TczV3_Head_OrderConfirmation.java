package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ConsigneeAddressAct;
import com.wjwl.mobile.taocz.act.V3_AddressAct;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TczV3_Head_OrderConfirmation extends LinearLayout {
	public TextView username, useraddress, usertel;
	public LinearLayout layout;

	public TczV3_Head_OrderConfirmation(Context context) {
		super(context);
		initview();
	}

	public TczV3_Head_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.tczv3_head_ordercomfirmation, this);
		layout = (LinearLayout) findViewById(R.tczv3.clic_layout1);
		username = (TextView) findViewById(R.tczv3.username);
		useraddress = (TextView) findViewById(R.tczv3.useraddress);
		usertel = (TextView) findViewById(R.tczv3.tel);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getContext(), V3_AddressAct.class);
				getContext().startActivity(i);
			}
		});
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
