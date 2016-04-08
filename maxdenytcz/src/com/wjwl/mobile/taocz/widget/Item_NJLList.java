//package com.wjwl.mobile.taocz.widget;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.ItemwbAct;
//import com.wjwl.mobile.taocz.act.TakeOutBusinessMenuAct1;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class Item_NJLList extends LinearLayout {
//	private MImageView productimg;
//	private TextView distance, title, address;
//	public String businessId, productId;
//	private ImageView ico_hui, ico_quan;
//	// private int id;
//	private Context context;
//	RelativeLayout cliclayout;
//	private String url;
//
//	public Item_NJLList(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Item_NJLList(Context context) {
//		super(context);
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public void initview() {
//		LayoutInflater inflater = LayoutInflater.from(getContext());
//		inflater.inflate(R.layout.item_njllist, this);
//		productimg = (MImageView) findViewById(R.item_njllist.img);
//		distance = (TextView) findViewById(R.item_njllist.distance);
//		address = (TextView) findViewById(R.item_njllist.address);
//		title = (TextView) findViewById(R.item_njllist.title);
//		ico_hui = (ImageView) findViewById(R.item_njllist.hui);
//		ico_quan = (ImageView) findViewById(R.item_njllist.juan);
//		cliclayout = (RelativeLayout) findViewById(R.item_njllist.clic);
//		cliclayout.setOnClickListener(onclick);
//	}
//
//	private OnClickListener onclick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.item_njllist.clic:
//				 Intent i = new Intent();
//				 i.putExtra("itemid", url);
//				 i.putExtra("itemtype", "njl");
//				 i.setClass(getContext(), ItemwbAct.class);
//				 getContext().startActivity(i);
//				 break;
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
//		this.distance.setText(text);
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
//	public void setUrl(String str) {
//		this.url = str;
//	}
//}
