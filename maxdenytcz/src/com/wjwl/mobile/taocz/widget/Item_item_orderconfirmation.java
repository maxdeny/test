package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;

public class Item_item_orderconfirmation extends LinearLayout {
	private MImageView productimg;
	private TextView productname, productprice, productnum;
	private String businessId, productId;
	private View view;
	public int id;
	private Context context;
	private String paycode = "";

	public Item_item_orderconfirmation(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public Item_item_orderconfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void initview() {
		productimg = (MImageView) findViewById(R.item_item_orderconfirmation.img);
		productname = (TextView) findViewById(R.item_item_orderconfirmation.name);
		productprice = (TextView) findViewById(R.item_item_orderconfirmation.price);
		productnum = (TextView) findViewById(R.item_item_orderconfirmation.num);
		view = (View) findViewById(R.item_item_orderconfirmation.clic_view);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (paycode == null)
					return;
				if (paycode.equals("1") || paycode.equals("2")) {
					Intent i = new Intent();
					i.putExtra("itemid", productId);
					i.setClass(context, TczV3_GoodsDetailsAg.class);
					context.startActivity(i);
				}
			}

		});
	}

	public void setproduvtImg(Object obj) {
		this.productimg.setObj(obj);
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public void setProductId(String id) {
		this.productId = id;
	}

	public void setPayCode(String text) {
		this.paycode = text;
	}

	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductNum(CharSequence text) {
		this.productnum.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + text);
	}

	public void setId() {

	}

}
