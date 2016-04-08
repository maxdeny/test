//package com.wjwl.mobile.taocz.widget;
//
//import com.wjwl.mobile.taocz.R;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class WaimaiIteam extends LinearLayout{
//	Context context;
//	ImageView img,star_1,star_2,star_3,star_4,star_5,more;
//	TextView title,price,style,juli;
//	LinearLayout linear;
//	public WaimaiIteam(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		init();
//	}
//	public void init(){
//		LayoutInflater flater =LayoutInflater.from(getContext());
//		View view=flater.inflate(R.layout.waimaiiteam, this);
//		linear=(LinearLayout) findViewById(R.waimaiitem.linear);
//		img=(ImageView) findViewById(R.waimaiitem.img);
//		star_1=(ImageView) findViewById(R.waimaiitem.star_1);
//		star_2=(ImageView) findViewById(R.waimaiitem.star_2);
//		star_3=(ImageView) findViewById(R.waimaiitem.star_3);
//		star_4=(ImageView) findViewById(R.waimaiitem.star_4);
//		star_5=(ImageView) findViewById(R.waimaiitem.star_5);
//		title=(TextView) findViewById(R.waimaiitem.title);
//		price=(TextView) findViewById(R.waimaiitem.price);
//		style=(TextView) findViewById(R.waimaiitem.style);
//		juli=(TextView) findViewById(R.waimaiitem.juli);
//		more=(ImageView) findViewById(R.waimaiitem.more);
////		linear.setOnClickListener(new OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				// TODO Auto-generated method stub
////				
////			}
////		});
//	}
//	public void setData(int pic,String titles,String prics,String julis,String styles){
//		img.setImageResource(pic);
//		title.setText(titles);
//		style.setText(styles);
//		price.setText(prics);
//		juli.setText(julis);
//	}
//	
//	public void setStar(String text) {
//		switch (Integer.parseInt(text)) {
//		case 0:
//			star_1.setBackgroundResource(R.drawable.star_empty);
//			star_2.setBackgroundResource(R.drawable.star_empty);
//			star_3.setBackgroundResource(R.drawable.star_empty);
//			star_4.setBackgroundResource(R.drawable.star_empty);
//			star_5.setBackgroundResource(R.drawable.star_empty);
//			break;
//		case 1:
//			star_1.setBackgroundResource(R.drawable.star_full);
//			star_2.setBackgroundResource(R.drawable.star_empty);
//			star_3.setBackgroundResource(R.drawable.star_empty);
//			star_4.setBackgroundResource(R.drawable.star_empty);
//			star_5.setBackgroundResource(R.drawable.star_empty);
//			break;
//		case 2:
//			star_1.setBackgroundResource(R.drawable.star_full);
//			star_2.setBackgroundResource(R.drawable.star_full);
//			star_3.setBackgroundResource(R.drawable.star_empty);
//			star_4.setBackgroundResource(R.drawable.star_empty);
//			star_5.setBackgroundResource(R.drawable.star_empty);
//			break;
//		case 3:
//			star_1.setBackgroundResource(R.drawable.star_full);
//			star_2.setBackgroundResource(R.drawable.star_full);
//			star_3.setBackgroundResource(R.drawable.star_full);
//			star_4.setBackgroundResource(R.drawable.star_empty);
//			star_5.setBackgroundResource(R.drawable.star_empty);
//			break;
//		case 4:
//			star_1.setBackgroundResource(R.drawable.star_full);
//			star_2.setBackgroundResource(R.drawable.star_full);
//			star_3.setBackgroundResource(R.drawable.star_full);
//			star_4.setBackgroundResource(R.drawable.star_full);
//			star_5.setBackgroundResource(R.drawable.star_empty);
//			break;
//		case 5:
//			star_1.setBackgroundResource(R.drawable.star_full);
//			star_2.setBackgroundResource(R.drawable.star_full);
//			star_3.setBackgroundResource(R.drawable.star_full);
//			star_4.setBackgroundResource(R.drawable.star_full);
//			star_5.setBackgroundResource(R.drawable.star_full);
//			break;
//		}
//	}
//}
