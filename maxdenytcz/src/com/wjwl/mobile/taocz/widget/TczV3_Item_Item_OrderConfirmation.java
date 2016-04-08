package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TczV3_Item_Item_OrderConfirmation extends LinearLayout {
	TextView name, guige, price, count;
	MImageView img;
	String itemid;
	RelativeLayout layout_pstime;

	public TczV3_Item_Item_OrderConfirmation(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		// initView();
	}

	public TczV3_Item_Item_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// initView();
	}

	public void initView() {
		// LayoutInflater inflater = LayoutInflater.from(getContext());
		// View view = inflater.inflate(
		// R.layout.tczv3_item_item_orderconfirmation, this);
		img = (MImageView) findViewById(R.tczv3.img);
		name = (TextView) findViewById(R.tczv3.name);
		guige = (TextView) findViewById(R.tczv3.guige);
		price = (TextView) findViewById(R.tczv3.price);
		count = (TextView) findViewById(R.tczv3.count);
	}

	public void setImg(Object obj) {
		this.img.setObj(obj);
	}

	public void setName(CharSequence text) {
		this.name.setText(text);
	}

	public void setGuiGe(CharSequence text) {
		this.guige.setText(text);
	}

	public void setPrice(CharSequence text) {
		this.price.setText("￥" + text);
	}

	public void setCount(CharSequence text) {
		this.count.setText("x" + text);
	}

	public void setItemid(String text) {
		this.itemid = text;
	}
}
