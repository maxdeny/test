//package com.wjwl.mobile.taocz.widget;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.act.BookingRestaurantAct;
//import com.wjwl.mobile.taocz.act.RestaurantDetailsAct;
//import com.wjwl.mobile.taocz.act.TakeOutBusinessMenuAct1;
//import com.wjwl.mobile.taocz.act.TakeOutBussinessMenuAct;
//import com.wjwl.mobile.taocz.act.TakeOutNoMenuDetailsAct;
//
//public class Item_TakeoutList1 extends LinearLayout {
//	private MImageView productimg;
//	private TextView distance, title, takeouttype, address;
//	public String businessId, productId, productType;
//	private ImageView ico_hui, ico_quan;
//	// private int id;
//	private Context context;
//	RelativeLayout cliclayout;
//	String areaid = "";
//
//	public Item_TakeoutList1(Context context) {
//
//		super(context);
//		context = context;
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public void initview() {
//		LayoutInflater inflater = LayoutInflater.from(getContext());
//		inflater.inflate(R.layout.item_takeoutlist1, this);
//		productimg = (MImageView) findViewById(R.item_takeoutlist1.img);
//		distance = (TextView) findViewById(R.item_takeoutlist1.distance);
//		address = (TextView) findViewById(R.item_takeoutlist1.address);
//		title = (TextView) findViewById(R.item_takeoutlist1.title);
//		ico_hui = (ImageView) findViewById(R.item_takeoutlist1.hui);
//		ico_quan = (ImageView) findViewById(R.item_takeoutlist1.juan);
//		takeouttype = (TextView) findViewById(R.item_takeoutlist1.takeouttype);
//		cliclayout = (RelativeLayout) findViewById(R.item_takeoutlist1.clic);
//		cliclayout.setOnClickListener(onclick);
//	}
//
//	private OnClickListener onclick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.item_takeoutlist1.clic:
//
//				Intent i = new Intent();
//				i.putExtra("businessid", businessId);
//				if (areaid.equals("0"))
//					i.setClass(getContext(), TakeOutBusinessMenuAct1.class);
//				else
//					i.setClass(getContext(), TakeOutNoMenuDetailsAct.class);
//				getContext().startActivity(i);
//				break;
//			}
//		}
//	};
//
//	public void setQuan(String text) {
//		if (text.trim().equals("1"))
//			this.ico_quan.setVisibility(View.VISIBLE);
//		else
//			this.ico_quan.setVisibility(View.INVISIBLE);
//	}
//
//	public void setHui(String text) {
//		if (text.trim().equals("1"))
//			this.ico_hui.setVisibility(View.VISIBLE);
//		else
//			this.ico_hui.setVisibility(View.INVISIBLE);
//	}
//
//	public void setTitle(CharSequence text) {
//		this.title.setText(text);
//	}
//
//	public void setAddress(CharSequence text) {
//		this.address.setText(text);
//	}
//
//	public void setDistance(final CharSequence text) {
//		this.distance.setText("距离" + text + "公里");
//	}
//
//	public void setTakeouttype(final CharSequence text) {
//		this.takeouttype.setText(text);
//	}
//
//	public void setItemid(String text) {
//		this.productId = text;
//	}
//
//	public void setProductimg(String str) {
//		this.productimg.setObj(str);
//	}
//
//	public void setBusinessId(String str) {
//		this.businessId = str;
//	}
//
//	public void setAreaid(String text) {
//		this.areaid = text;
//	}
//}
