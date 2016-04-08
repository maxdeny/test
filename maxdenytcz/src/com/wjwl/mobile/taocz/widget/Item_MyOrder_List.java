package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.OrderDetailsAct;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_MyOrder_List extends LinearLayout {

	private TextView name, price, business, date;
	private MImageView favoriteImg;
	private Button seebtn;

	public Item_MyOrder_List(Context context) {
		super(context);
		initview();
	}

	public Item_MyOrder_List(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_myorder_list, this);
		favoriteImg = (MImageView) findViewById(R.item_myorder_list.favoriteimg);
		name = (TextView) findViewById(R.item_myorder_list.name);
		price = (TextView) findViewById(R.item_myorder_list.price);
		business = (TextView) findViewById(R.item_myorder_list.business);
		date = (TextView) findViewById(R.item_myorder_list.date);
		
		seebtn = (Button) findViewById(R.item_myorder_list.see);
		
		seebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.setClass(getContext(), OrderDetailsAct.class);
				getContext().startActivity(i);
			}
		});
	}

	public void setName(CharSequence text) {
		this.name.setText(text);
	}

	public void setPrice(CharSequence text) {
		this.price.setText(text);
	}

	public void setBusiness(final CharSequence text) {
		this.business.setText(text);
	}

	public void setDate(CharSequence text) {
		this.date.setText(text);
	}
	public void setFavoriteImg(String str) {
		this.favoriteImg.setObj(str);
	}
	
}