//package com.wjwl.mobile.taocz.widget;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.BookingHotelAct;
//import com.wjwl.mobile.taocz.act.TnTejiaInfo_Act;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class TnTejiaInfoIteam extends LinearLayout {
//	MImageView img;
//	TextView name, style, te_price, men_price, tao_price;
//	String itemid,taop,tep;
//	boolean iscanbooking;
//	Button bt_canbooking;
//    String istejia,title;
//	public TnTejiaInfoIteam(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		init();
//	}
//
//	public void init() {
//		LayoutInflater f = LayoutInflater.from(getContext());
//		View v = f.inflate(R.layout.tntejiainfoitem, this);
//		img = (MImageView) findViewById(R.tntejiainfoitem.img);
//		name = (TextView) findViewById(R.tntejiainfoitem.title);
//		style = (TextView) findViewById(R.tntejiainfoitem.style);
//		te_price = (TextView) findViewById(R.tntejiainfoitem.te_price);
//		tao_price = (TextView) findViewById(R.tntejiainfoitem.tao_price);
//		men_price = (TextView) findViewById(R.tntejiainfoitem.men_price);
//		bt_canbooking = (Button) findViewById(R.tntejiainfoitem.canbooking);
//		bt_canbooking.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (iscanbooking) {
//                     Intent i=new Intent();
//                     i.putExtra("title", title);
//                     i.putExtra("itemid", itemid);
//                     i.putExtra("istejia", istejia);
//                     if(!istejia.equals("0")){
//                    	 i.putExtra("price", tep);
//                     }
//                     else{
//                    	 i.putExtra("price", taop);
//                     }
//                     i.putExtra("hotelid", TnTejiaInfo_Act.hotelid);
//                     i.setClass(getContext(), BookingHotelAct.class);
//                     getContext().startActivity(i);
//				} else {
//				}
//			}
//		});
//	}
//
//	public void setData(String imgurl, String title, String taop, String tep,
//			String roomstyle, String mp, String itemid, String itemstate,String istejia,String wifi) {
//		img.setObj(imgurl);
//		name.setText(title);
//		this.title=title;
//		style.setText("床型:"+roomstyle);
//		tao_price.setText("淘常州价：￥" + taop);
//		te_price.setText("￥" + tep);
//		if(!istejia.equals("0"))
//			te_price.setVisibility(View.VISIBLE);
//		else
//			te_price.setVisibility(View.INVISIBLE);
////		te_price.setVisibility(View.GONE);
//		men_price.setText("门市价￥" + mp);
//		this.itemid = itemid;
//		this.taop=taop;
//		this.istejia=istejia;
//		this.tep=tep;
//		if (itemstate.equals("1")) {
//			bt_canbooking.setBackgroundResource(R.drawable.icon_ding);
//			bt_canbooking.setText("订");
//			iscanbooking = true;
//
//		} else if (itemstate.equals("0")) {
//			bt_canbooking.setBackgroundResource(R.drawable.icon_man);
//			bt_canbooking.setText("满");
//			iscanbooking = false;
//		}
//		if(wifi.equals("1")){
//			img.setVisibility(View.VISIBLE);
//		}else{
//			img.setVisibility(View.GONE);
//		}
//
//	}
//}
