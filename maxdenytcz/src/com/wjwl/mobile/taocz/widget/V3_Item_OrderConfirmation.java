package com.wjwl.mobile.taocz.widget;

import java.util.List;

import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class V3_Item_OrderConfirmation extends LinearLayout {
	public TextView businessname, hdfk;
	private LinearLayout cartslayout;
	private String businessId;
	private RelativeLayout psxx_layout;
	ImageView img_line, arraw;
	private boolean is_canhdfk;
	Context context;

	public V3_Item_OrderConfirmation(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public V3_Item_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init() {
		businessname = (TextView) findViewById(R.v3_item_orderconfirmation.name);
		hdfk = (TextView) findViewById(R.v3_item_orderconfirmation.hdfk);
		cartslayout = (LinearLayout) findViewById(R.v3_item_orderconfirmation.freelayout);
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
	}

	public void sethdfk(CharSequence text) {
		this.hdfk.setText(text);
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public LinearLayout getCartsLayout() {
		return cartslayout;
	}

	public void setBusinessLayout(List<Msg_Citem> list) {
		Msg_Citem product;
		for (int j = 0; j < cartslayout.getChildCount(); j++) {
			cartslayout.getChildAt(j).setVisibility(View.GONE);
		}

		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			V3_Item_Item_OrderConfirmation item_cart;

			if (i < cartslayout.getChildCount()) {
				item_cart = (V3_Item_Item_OrderConfirmation) cartslayout
						.getChildAt(i);
				item_cart.setVisibility(View.VISIBLE);
			} else {
				LayoutInflater flater = LayoutInflater.from(this.getContext());
				item_cart = (V3_Item_Item_OrderConfirmation) flater.inflate(
						R.layout.v3_item_item_orderconfirmation, null);
				item_cart.initview();
				this.cartslayout.addView(item_cart);
			}
			item_cart.setproductName(product.getItemtitle());
			item_cart.setproductPrice(Arith.to2zero(product.getItemprice()));
			item_cart.setBusinessId(this.businessId);
			item_cart.setProductId(product.getItemid());
			item_cart.setproductNum(product.getItemcount());
			item_cart.setId(); // this must be the last line on this
			item_cart = (V3_Item_Item_OrderConfirmation) this
					.findViewById(R.id.v3_item_item_orderconfirmation);
			item_cart.setTag(product);
			item_cart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Msg_Citem product = (Msg_Citem) v.getTag();
					Intent i = new Intent();
					i.putExtra("itemid", product.getItemid());
					i.setClass(context, ShoppingContentAct.class);
					context.startActivity(i);
				}
			});
		}
	}
}
