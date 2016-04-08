//package com.wjwl.mobile.taocz.widget;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.TnTejiaInfo_Act;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.style.ForegroundColorSpan;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class TnTejiaIteam extends LinearLayout {
//	ImageView star_1,star_2,star_3,star_4,star_5,tejia_img,tejia_ye;
//	MImageView img;
//	TextView name,pingfen,price,juli,style;
//	LinearLayout linear;
//	String businessid;
//	String longitude;
//	String latitude;
//	String istejia,pinglnumber;
//	public TnTejiaIteam(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//		init();
//	}
//public void init(){
//	LayoutInflater f=LayoutInflater.from(getContext());
//	View v=f.inflate(R.layout.tntejiaitem, this);
//	img=(MImageView) findViewById(R.tntejiaitem.img);
//	star_1=(ImageView) findViewById(R.tntejiaitem.star_1);
//	star_2=(ImageView) findViewById(R.tntejiaitem.star_2);
//	star_3=(ImageView) findViewById(R.tntejiaitem.star_3);
//	star_4=(ImageView) findViewById(R.tntejiaitem.star_4);
//	star_5=(ImageView) findViewById(R.tntejiaitem.star_5);
//	name=(TextView) findViewById(R.tntejiaitem.title);
//	price=(TextView) findViewById(R.tntejiaitem.price);
//	pingfen=(TextView) findViewById(R.tntejiaitem.pingfen);
//	juli=(TextView) findViewById(R.tntejiaitem.juli);
//	linear=(LinearLayout) findViewById(R.tntejiaitem.linear);
//	style=(TextView) findViewById(R.tntejiaitem.style);
//	tejia_img=(ImageView) findViewById(R.tntejiaitem.tejia_img);
//	tejia_ye=(ImageView) findViewById(R.tntejiaitem.tejia_ye);
//	
//	linear.setOnClickListener(new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			Intent in=new Intent(getContext(),TnTejiaInfo_Act.class);
//			in.putExtra("businessid", businessid);
//			in.putExtra("longitude", longitude);
//			in.putExtra("latitude", latitude);
//			in.putExtra("pingfen", pingfen.getText());
//			in.putExtra("star", style.getText());
//			in.putExtra("name", name.getText());
//			in.putExtra("istejia", istejia);
//			in.putExtra("pinglnumber", pinglnumber);
//			getContext().startActivity(in);
//		}
//	});
//}
//public void setData(String i,String n,String pf,String pr,String s,String j){
////	img.setBackgroundResource(i);
//	img.setObj(i);
//	name.setText(n);
//	pingfen.setText(pf);
//	pr="￥"+pr+"起";
//	SpannableStringBuilder style1=new SpannableStringBuilder(pr); 
//	style1.setSpan(new ForegroundColorSpan(Color.RED), 1, pr.length()-1,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE ); 
//	price.setText(style1);
//	style.setText(s);
//	juli.setText("距离"+j+"km");
//}
//public void setMoreData(String businessid,String longitude,String latitude,String tejia,String pinglnumber){
//	this.businessid=businessid;
//	this.longitude=longitude;
//	this.latitude=latitude;
//	this.pinglnumber=pinglnumber;
//	istejia=tejia;
//	if(tejia.equals("1")){
//		tejia_ye.setVisibility(View.VISIBLE);
//	}else{
//		tejia_ye.setVisibility(View.INVISIBLE);
//	}
//}
//public void setTj(String s){
//	istejia=s;
//	if(s.equals("1")){
////		tejia_img.setVisibility(View.VISIBLE);
//		tejia_ye.setVisibility(View.VISIBLE);
//	}else{
////		tejia_img.setVisibility(View.INVISIBLE);
//		tejia_ye.setVisibility(View.INVISIBLE);
//	}
//	
//}
//public void setStar(String text) {
//	int cases;
//	if(text.equals("")){
//		cases=0;
//	}else{
//		cases=(int) Math.floor(Double.parseDouble(text));
//	}
//	switch (cases) {
//	case 0:
//		star_1.setBackgroundResource(R.drawable.star_empty);
//		star_2.setBackgroundResource(R.drawable.star_empty);
//		star_3.setBackgroundResource(R.drawable.star_empty);
//		star_4.setBackgroundResource(R.drawable.star_empty);
//		star_5.setBackgroundResource(R.drawable.star_empty);
//		break;
//	case 1:
//		star_1.setBackgroundResource(R.drawable.star_full);
//		star_2.setBackgroundResource(R.drawable.star_empty);
//		star_3.setBackgroundResource(R.drawable.star_empty);
//		star_4.setBackgroundResource(R.drawable.star_empty);
//		star_5.setBackgroundResource(R.drawable.star_empty);
//		break;
//	case 2:
//		star_1.setBackgroundResource(R.drawable.star_full);
//		star_2.setBackgroundResource(R.drawable.star_full);
//		star_3.setBackgroundResource(R.drawable.star_empty);
//		star_4.setBackgroundResource(R.drawable.star_empty);
//		star_5.setBackgroundResource(R.drawable.star_empty);
//		break;
//	case 3:
//		star_1.setBackgroundResource(R.drawable.star_full);
//		star_2.setBackgroundResource(R.drawable.star_full);
//		star_3.setBackgroundResource(R.drawable.star_full);
//		star_4.setBackgroundResource(R.drawable.star_empty);
//		star_5.setBackgroundResource(R.drawable.star_empty);
//		break;
//	case 4:
//		star_1.setBackgroundResource(R.drawable.star_full);
//		star_2.setBackgroundResource(R.drawable.star_full);
//		star_3.setBackgroundResource(R.drawable.star_full);
//		star_4.setBackgroundResource(R.drawable.star_full);
//		star_5.setBackgroundResource(R.drawable.star_empty);
//		break;
//	case 5:
//		star_1.setBackgroundResource(R.drawable.star_full);
//		star_2.setBackgroundResource(R.drawable.star_full);
//		star_3.setBackgroundResource(R.drawable.star_full);
//		star_4.setBackgroundResource(R.drawable.star_full);
//		star_5.setBackgroundResource(R.drawable.star_full);
//		break;
//	}
//}
//}
