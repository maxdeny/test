package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;

public class TczV3_Item_ShopGoodsList extends LinearLayout {

	MImageView img;
	TextView title;
	TextView tcz_price;
	TextView old_price;
	String itemid;
	LinearLayout cliclayout;

	public TczV3_Item_ShopGoodsList(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public TczV3_Item_ShopGoodsList(Context context, AttributeSet attrs) {
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
				 i.putExtra("umcount", "SelectStoreGoods");
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

	public void setOldPrice(CharSequence text) {
		if(null == text){
			this.old_price.setVisibility(View.GONE);
		}
		this.old_price.setText("￥" + text);
		this.old_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); 
	}

	public void setItemid(String text) {
		this.itemid = text;
	}

}