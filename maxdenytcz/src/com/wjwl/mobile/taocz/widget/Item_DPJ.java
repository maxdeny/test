//package com.wjwl.mobile.taocz.widget;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.MyAct;
//import com.wjwl.mobile.taocz.act.PJAct;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class Item_DPJ extends LinearLayout {
//	MImageView img;
//	TextView businessname, productname, count, price;
//	String itemid;
//	LinearLayout clic_layout;
//	Button bt_dpj;
//
//	public Item_DPJ(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//		initView();
//	}
//
//	public Item_DPJ(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		initView();
//	}
//
//	private void initView() {
//		// TODO Auto-generated method stub
//		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_dpj, this);
//		clic_layout = (LinearLayout) findViewById(R.item_dpj.item_dpj);
//		img = (MImageView) findViewById(R.item_dpj.img);
//		businessname = (TextView) findViewById(R.item_dpj.businessname);
//		productname = (TextView) findViewById(R.item_dpj.productname);
//		count = (TextView) findViewById(R.item_dpj.count);
//		price = (TextView) findViewById(R.item_dpj.price);
//		bt_dpj = (Button) findViewById(R.item_dpj.bt_dpj);
//		clic_layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Intent i = new Intent();
////				i.setClass(getContext(), MyAct.class);
////				getContext().startActivity(i);
//			}
//		});
//		bt_dpj.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent();
//				i.setClass(getContext(), PJAct.class);
//				getContext().startActivity(i);
//			}
//		});
//	}
//
//	public void setImg(Object obj) {
//		this.img.setObj(obj);
//	}
//
//	public void setBusinessName(CharSequence text) {
//		this.businessname.setText(text);
//	}
//
//	public void setProductName(CharSequence text) {
//		this.productname.setText(text);
//	}
//
//	public void setCount(CharSequence text) {
//		this.count.setText("共" + text + "件商品");
//	}
//
//	public void setPrice(CharSequence text) {
//		this.price.setText("￥" + text);
//	}
//
//	public void setItemid(String itemid) {
//		this.itemid = itemid;
//	}
//}
