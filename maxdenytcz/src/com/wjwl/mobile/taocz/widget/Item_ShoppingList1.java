package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.commons.Arith;

public class Item_ShoppingList1 extends LinearLayout {

	private MImageView productimg;
	private TextView productname, productprice, businessName, itemsold,
			productoriginalprice;
	private RelativeLayout editlayout;
	private View mclick;
	private Msg_Citem mitem;

	public Item_ShoppingList1(Context context) {
		super(context);
		initview();

	}

	public Item_ShoppingList1(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void set(Msg_Citem item) {
		this.mitem = item;
		setproductName(item.getItemtitle());
		setproductPrice(item.getItemdiscount().equals("") ? "0.00" : Arith
				.to2zero(item.getItemdiscount()));
		setbusinessName(item.getItembusinessname());
		if (item.getItemprice().equals("") || item.getItemprice().equals("0")
				|| item.getItemprice().equals("0.00")
				|| item.getItemprice().equals("0.0"))
			productoriginalprice.setVisibility(View.INVISIBLE);
		else {
			productoriginalprice.setVisibility(View.VISIBLE);
			setProductoriginalprice(Arith.to2zero(item.getItemprice()));
		}
		setitemSold(item.getItemsold());
		setproductImage(item.getItemimageurl());
		mclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("itemid", mitem.getItemid());
				i.setClass(v.getContext(), ShoppingContentAct.class);
				getContext().startActivity(i);
			}
		});
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_shoppinglist, this);
		mclick = findViewById(R.id.click);
		itemsold = (TextView) findViewById(R.item_shoppinglist.buyover);
		productimg = (MImageView) findViewById(R.item_shoppinglist.productimg);
		productname = (TextView) findViewById(R.item_shoppinglist.productname);
		productprice = (TextView) findViewById(R.item_shoppinglist.productprice);
		businessName = (TextView) findViewById(R.item_shoppinglist.businessname);
		productoriginalprice = (TextView) findViewById(R.item_shoppinglist.productoriginalprice);
	}

	public void setproductImage(String text) {
		this.productimg.setObj(text);
	}

	public void setitemSold(CharSequence text) {
		this.itemsold.setText(text);
	}

	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + String.valueOf(text));
	}

	public void setProductoriginalprice(String text) {
		this.productoriginalprice.setText("￥" + String.valueOf(text));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	public void setbusinessName(CharSequence text) {
		this.businessName.setText(text);
	}

	public void setbusinessNameGONE() {
		this.businessName.setVisibility(View.GONE);
	}
}