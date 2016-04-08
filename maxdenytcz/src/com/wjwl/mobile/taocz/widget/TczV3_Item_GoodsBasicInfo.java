package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_GoodsBasicInfo;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TczV3_Item_GoodsBasicInfo extends LinearLayout {

	MImageView img;
	TextView title;
	TextView tcz_price;
	TextView old_price;
	String itemid;
	LinearLayout cliclayout;

	public TczV3_Item_GoodsBasicInfo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public TczV3_Item_GoodsBasicInfo(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater flater = LayoutInflater.from(getContext());
		View view = flater.inflate(R.layout.tczv3_item_goodsbasicinfo, this);
		img = (MImageView) findViewById(R.tczv3.img);
		title = (TextView) findViewById(R.tczv3.title);
		tcz_price = (TextView) findViewById(R.tczv3.tcz_price);
		old_price = (TextView) findViewById(R.tczv3.old_price);
		cliclayout = (LinearLayout) findViewById(R.tczv3.cliclayout);
		cliclayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
               Intent i=new Intent();
               i.putExtra("itemid", itemid);
               i.setClass(getContext(), TczV3_GoodsDetailsAg.class);
               getContext().startActivity(i);
			}
		});
	}

	public void setImg(Object obj) {
		this.img.setObj(obj);
	}

	public void setTczPrice(CharSequence text) {
		this.tcz_price.setText("￥" + text);
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setOldPrice(String text) {
		if(text.equals("0")){
			this.old_price.setVisibility(View.GONE);
		}
		else{
			this.old_price.setVisibility(View.VISIBLE);
			this.old_price.setText("￥" + text);
		}
	}

	public void setItemid(String text) {
		this.itemid = text;
	}

}
