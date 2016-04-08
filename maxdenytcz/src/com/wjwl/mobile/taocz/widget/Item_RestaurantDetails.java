//package com.wjwl.mobile.taocz.widget;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.BookingRestaurantAct;
//import com.wjwl.mobile.taocz.act.IndexAct;
//import com.wjwl.mobile.taocz.act.OneKeyOK_Act;
//import com.wjwl.mobile.taocz.act.RestaurantDetailsAct;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class Item_RestaurantDetails extends LinearLayout {
//	private MImageView productimg;
//	private ImageView star1, star2, star3, star4, star5;
//	private TextView distance, title, renjun,providetype;
//	public String businessId, productId, productType;
//	// private int id;
//	public Button bt_yuding;
//	private Context context;
//	RelativeLayout cliclayout;
//	ImageView juan,hui;
//
//	public Item_RestaurantDetails(Context context) {
//		super(context);
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public void initview() {
//		LayoutInflater inflater = LayoutInflater.from(getContext());
//		inflater.inflate(R.layout.item_restaurantdetails, this);
//		productimg = (MImageView) findViewById(R.item_restaurantdetails.img);
//		star1 = (ImageView) findViewById(R.item_restaurantdetails.star1);
//		star2 = (ImageView) findViewById(R.item_restaurantdetails.star2);
//		star3 = (ImageView) findViewById(R.item_restaurantdetails.star3);
//		star4 = (ImageView) findViewById(R.item_restaurantdetails.star4);
//		star5 = (ImageView) findViewById(R.item_restaurantdetails.star5);
//		distance = (TextView) findViewById(R.item_restaurantdetails.distance);
//		providetype =(TextView)findViewById(R.item_restaurantdetails.zhekou);
//		title = (TextView) findViewById(R.item_restaurantdetails.title);
//		renjun = (TextView) findViewById(R.item_restaurantdetails.renjun);
//		cliclayout = (RelativeLayout) findViewById(R.item_restaurantdetails.clic);
//		bt_yuding = (Button) findViewById(R.item_restaurantdetails.bt_yuding);
//		bt_yuding.setOnClickListener(onclick);
//		cliclayout.setOnClickListener(onclick);
//		juan=(ImageView) findViewById(R.item_restaurantdetails.juan);
//		hui=(ImageView) findViewById(R.item_restaurantdetails.hui);
//	}
//
//	private OnClickListener onclick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.item_restaurantdetails.bt_yuding:
//				Intent ydintent = new Intent();
//				ydintent.putExtra("businessid", productId);
//				ydintent.setClass(getContext(), OneKeyOK_Act.class);
//				getContext().startActivity(ydintent);
//				break;
//			case R.item_restaurantdetails.clic:
//				Intent i1 = new Intent();
//				i1.putExtra("itemid", productId);
//				i1.setClass(getContext(), RestaurantDetailsAct.class);
//				getContext().startActivity(i1);
//				break;
//			}
//		}
//	};
//
//	public void setStar(String text) {
//		switch (Integer.parseInt(text)) {
//		case 0:
//			star1.setBackgroundResource(R.drawable.merchant_star_gray);
//			star2.setBackgroundResource(R.drawable.merchant_star_gray);
//			star3.setBackgroundResource(R.drawable.merchant_star_gray);
//			star4.setBackgroundResource(R.drawable.merchant_star_gray);
//			star5.setBackgroundResource(R.drawable.merchant_star_gray);
//			break;
//		case 1:
//			star1.setBackgroundResource(R.drawable.merchant_star);
//			star2.setBackgroundResource(R.drawable.merchant_star_gray);
//			star3.setBackgroundResource(R.drawable.merchant_star_gray);
//			star4.setBackgroundResource(R.drawable.merchant_star_gray);
//			star5.setBackgroundResource(R.drawable.merchant_star_gray);
//			break;
//		case 2:
//			star1.setBackgroundResource(R.drawable.merchant_star);
//			star2.setBackgroundResource(R.drawable.merchant_star);
//			star3.setBackgroundResource(R.drawable.merchant_star_gray);
//			star4.setBackgroundResource(R.drawable.merchant_star_gray);
//			star5.setBackgroundResource(R.drawable.merchant_star_gray);
//			break;
//		case 3:
//			star1.setBackgroundResource(R.drawable.merchant_star);
//			star2.setBackgroundResource(R.drawable.merchant_star);
//			star3.setBackgroundResource(R.drawable.merchant_star);
//			star4.setBackgroundResource(R.drawable.merchant_star_gray);
//			star5.setBackgroundResource(R.drawable.merchant_star_gray);
//			break;
//		case 4:
//			star1.setBackgroundResource(R.drawable.merchant_star);
//			star2.setBackgroundResource(R.drawable.merchant_star);
//			star3.setBackgroundResource(R.drawable.merchant_star);
//			star4.setBackgroundResource(R.drawable.merchant_star);
//			star5.setBackgroundResource(R.drawable.merchant_star_gray);
//			break;
//		case 5:
//			star1.setBackgroundResource(R.drawable.merchant_star);
//			star2.setBackgroundResource(R.drawable.merchant_star);
//			star3.setBackgroundResource(R.drawable.merchant_star);
//			star4.setBackgroundResource(R.drawable.merchant_star);
//			star5.setBackgroundResource(R.drawable.merchant_star);
//
//			break;
//		}
//	}
//
//	public void setTitle(CharSequence text) {
//		this.title.setText(text);
//	}
//
//	public void setRenjun(String text) {
//		String str = text.substring(0, text.indexOf("."));
//		this.renjun.setText("人均:￥" + str);
//	}
//
//	public void setDistance(final CharSequence text) {
//		this.distance.setText(text+"公里");
//	}
//
//	public void setItemid(String text) {
//		this.productId = text;
//	}
//
//	public void setProductimg(String str) {
//		this.productimg.setObj(str);
//	}
//	public void setprovidetype(CharSequence text) {
//		this.providetype.setText(text);
//	}
//	public void setJuan(String s){
//		if(s.equals("1")){
//			juan.setVisibility(View.VISIBLE);
//		}else{
//			juan.setVisibility(View.INVISIBLE);
//		}
//	}
//	public void setHui(String s){
//		if(s.equals("1")){
//			hui.setVisibility(View.VISIBLE);
//		}else{
//			hui.setVisibility(View.INVISIBLE);
//		}
//	}
//}
