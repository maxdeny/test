//package com.wjwl.mobile.taocz.widget;
//
//import java.util.Map;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.CommonPublicDetails;
//
//public class Item_PlaceList extends LinearLayout {
//
//	private MImageView productimg;
//	private TextView title,address;
//	private Button distance;
//	private RelativeLayout mclick;
//    private Map<String,String> tempplacemsg;
//	
//	public Item_PlaceList(Context context) {
//		super(context);
//		initview();
//
//	}
//
//	public Item_PlaceList(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initview();
//	}
//
//	public void set(Map<String,String> placemsg){
//		tempplacemsg=placemsg;
//		setTitle(placemsg.get("title"));
//		setDistance(placemsg.get("distance"));
//		setAddress(placemsg.get("address"));
//		
//		mclick.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra("localpoint", tempplacemsg.get("localpoint"));
//				intent.putExtra("address", tempplacemsg.get("address"));
//				intent.putExtra("distance", tempplacemsg.get("distance"));
//				intent.putExtra("title", tempplacemsg.get("title"));
//				intent.putExtra("phoneno", tempplacemsg.get("phoneno"));
//				intent.putExtra("showtype", "list");
//				intent.putExtra("titletype", tempplacemsg.get("titletype"));
//				intent.setClass(getContext(), CommonPublicDetails.class);
//                v.getContext().startActivity(intent);
//			}
//		});
//		
//		distance.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra("localpoint", tempplacemsg.get("localpoint"));
//				intent.putExtra("distance", tempplacemsg.get("distance"));
//				intent.putExtra("address", tempplacemsg.get("address"));
//				intent.putExtra("title", tempplacemsg.get("title"));
//				intent.putExtra("showtype", "map");
//				intent.putExtra("titletype", tempplacemsg.get("titletype"));
//				intent.setClass(getContext(), CommonPublicDetails.class);
//                v.getContext().startActivity(intent);
//				
//			}
//		});
//	}
//	
//	void initview() {
//		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_place_list, this);
//		title = (TextView) findViewById(R.place.title);
//		address = (TextView) findViewById(R.place.address);
//		distance = (Button) findViewById(R.place.distance);
//		mclick=(RelativeLayout) findViewById(R.place.layout);
//	}
//
//	public void setproductImage(String text) {
//		this.productimg.setObj(text);
//	}
//	
//	public void setTitle(String text) {
//		this.title.setText(text);
//	}
//	public void setDistance(String text) {
//		this.distance.setText(text);
//	}
//
//	public void setAddress(String text) {
//		this.address.setText(text);
//	}
//
//}
