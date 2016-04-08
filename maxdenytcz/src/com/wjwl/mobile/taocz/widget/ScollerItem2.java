package com.wjwl.mobile.taocz.widget;


import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.V3_IndexAct;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ScollerItem2 extends LinearLayout{
	TextView zhekou,contents,pricex,pricey,zhekou2,contents2,pricex2,pricey2;
	MImageView img,img2;
	RelativeLayout linear,linear2;
	String id,id2;
	Context mcontext;
	
	public ScollerItem2(Context context, AttributeSet attrs) {
		super(context, attrs);
		mcontext=context;
		// TODO Auto-generated constructor stub
		init();
	}
	public ScollerItem2(Context context) {
		super(context);
		mcontext=context;
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater f=LayoutInflater.from(getContext());
		View v=f.inflate(R.layout.scolleritem2, this);
		zhekou=(TextView) v.findViewById(R.scolleritema1.zhekou);
		contents=(TextView) v.findViewById(R.scolleritema1.contents);
		pricex=(TextView) v.findViewById(R.scolleritema1.pricex);
		pricey=(TextView) v.findViewById(R.scolleritema1.pricey);
		pricey.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		pricey.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		img=(MImageView) findViewById(R.scolleritema1.img);
		linear=(RelativeLayout) findViewById(R.scolleritema1.linear);
//		linear.setGravity(Gravity.CENTER_HORIZONTAL);
//		LayoutParams params = new LayoutParams((F.Fwidth-72)/2,LayoutParams.FILL_PARENT);
//		linear.setLayoutParams(params);
		
		zhekou2=(TextView) v.findViewById(R.scolleritema2.zhekou);
		contents2=(TextView) v.findViewById(R.scolleritema2.contents);
		pricex2=(TextView) v.findViewById(R.scolleritema2.pricex);
		pricey2=(TextView) v.findViewById(R.scolleritema2.pricey);
		pricey2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		pricey2.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG |Paint.ANTI_ALIAS_FLAG);
		img2=(MImageView) findViewById(R.scolleritema2.img);
		linear2=(RelativeLayout) findViewById(R.scolleritema2.linear);
//		linear.setGravity(Gravity.CENTER_HORIZONTAL);
//		LayoutParams params = new LayoutParams((F.Fwidth-72)/2,LayoutParams.FILL_PARENT);
//		linear.setLayoutParams(params);
		
//		linear.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(mcontext, V3_ShoppingDetailsAg.class);
//				i.putExtra("itemid", id);
//				i.putExtra("from", "index");
//				mcontext.startActivity(i);
//			}
//		});
//		
//        linear2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(mcontext, V3_ShoppingDetailsAg.class);
//				i.putExtra("itemid", id2);
//				i.putExtra("from", "index");
//				mcontext.startActivity(i);
//			}
//		});
	}

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
	
	public void setData2(Msg_Cpic d){
		zhekou2.setText(d.getV3Discount()+"折");
		if(d.getImageurl()!=null&&d.getImageurl().length()>0){
			img2.setObj(d.getImageurl());
		}
		contents2.setText(d.getV3Iteminfo());
		pricex2.setText(d.getV3Itemdiscount());
		pricey2.setText("￥"+d.getV3Itemprice());
		id2=d.getProid();
	}
}
