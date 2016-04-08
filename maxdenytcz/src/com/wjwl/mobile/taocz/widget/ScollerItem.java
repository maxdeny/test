package com.wjwl.mobile.taocz.widget;


import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScollerItem extends LinearLayout{
	TextView zhekou,contents,pricex,pricey;
	MImageView img;
	LinearLayout linear;
	String id;
	
	public ScollerItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}
	public ScollerItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.scolleritem, this);
		zhekou=(TextView) v.findViewById(R.scolleritem.zhekou);
		contents=(TextView) v.findViewById(R.scolleritem.contents);
		pricex=(TextView) v.findViewById(R.scolleritem.pricex);
		pricey=(TextView) v.findViewById(R.scolleritem.pricey);
		pricey.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		pricey.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		img=(MImageView) findViewById(R.scolleritem.img);
		linear=(LinearLayout) findViewById(R.scolleritem.linear);
		linear.setGravity(Gravity.CENTER_HORIZONTAL);
		LayoutParams params = new LayoutParams((F.Fwidth-72)/2,LayoutParams.FILL_PARENT);
		linear.setLayoutParams(params);
	}
//	public void setZheKou(String s){
//		zhekou.setText(s+"折");
//	}
//	public String getZheKou(){
//		return zhekou.getText().toString();
//	}
//	public void setContents(String s){
//		contents.setText(s);
//	}
//	public void setPricey(String s){
//		pricey.setText(s);
//	}
//	public void setPricex(String s){
//		pricex.setText(s);
//	}
//	public void setImg(Object o){
//		img.setObj(o);
//	}
	public void setData(Msg_Cpic d){
		zhekou.setText(d.getV3Discount()+"折");
		if(d.getImageurl()!=null&&d.getImageurl().length()>0){
			img.setObj(d.getImageurl());
		}
		contents.setText(d.getV3Iteminfo());
		pricex.setText(d.getV3Itemdiscount());
		pricey.setText("￥"+d.getV3Itemprice());
		id=d.getProid();
	}
}
