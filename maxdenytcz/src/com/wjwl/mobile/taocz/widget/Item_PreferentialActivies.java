//package com.wjwl.mobile.taocz.widget;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.CouponContentAct;
//
//public class Item_PreferentialActivies extends LinearLayout {
//
//	private MImageView img;
//	private TextView newprice, oldprice, title, buynums, area;
//	private RelativeLayout mclick;
//	private String productId;
//
//	public Item_PreferentialActivies(Context context) {
//		super(context);
//		initview();
//
//	}
//
//	public Item_PreferentialActivies(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initview();
//	}
//
//	void initview() {
//		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_preferentialactivies, this);
//		mclick = (RelativeLayout) findViewById(R.item_preferentialactivies.clic);
//		newprice = (TextView) findViewById(R.item_preferentialactivies.newprice);
//		oldprice = (TextView) findViewById(R.item_preferentialactivies.oldprice);
//		img = (MImageView) findViewById(R.item_preferentialactivies.img);
//		title = (TextView) findViewById(R.item_preferentialactivies.title);
//		buynums = (TextView) findViewById(R.item_preferentialactivies.buynums);
//		area = (TextView) findViewById(R.item_preferentialactivies.area);
//		mclick.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent();
//				i.putExtra("itemid", productId);
//				i.setClass(v.getContext(), CouponContentAct.class);
//				getContext().startActivity(i);
//			}
//		});
//	}
//
//	public void setproductImage(String text) {
//		this.img.setObj(text);
//	}
//
//	public void setbuynums(CharSequence text) {
//		this.buynums.setText( text);
//	}
//
//	public void settitle(CharSequence text) {
//		this.title.setText(text);
//	}
//
//	public void setnewPrice(CharSequence text) {
//		this.newprice.setText("￥" + String.valueOf(text));
//	}
//
//	public void setoldprice(CharSequence text) {
//		this.oldprice.setText("￥" + String.valueOf(text));
//	}
//
//	public void setarea(CharSequence text) {
//		this.area.setText(text);
//	}
//
//	public void setproductId(String text) {
//		this.productId = text;
//	}
//
//}