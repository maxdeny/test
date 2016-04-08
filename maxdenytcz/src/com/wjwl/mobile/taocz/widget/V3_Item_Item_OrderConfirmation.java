package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class V3_Item_Item_OrderConfirmation extends LinearLayout {
	private TextView productname, productprice, productnum;
	private String businessId, productId;
	private View view;
	public int id;
	private Context context;

	public V3_Item_Item_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public V3_Item_Item_OrderConfirmation(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		productname = (TextView) findViewById(R.v3_item_item_orderconfirmation.title);
		productprice = (TextView) findViewById(R.v3_item_item_orderconfirmation.price);
		productnum = (TextView) findViewById(R.v3_item_item_orderconfirmation.num);
		view = (View) findViewById(R.v3_item_item_orderconfirmation.clic_view);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("itemid", productId);
				i.setClass(context, ShoppingContentAct.class);
				context.startActivity(i);
			}

		});
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public void setProductId(String id) {
		this.productId = id;
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
