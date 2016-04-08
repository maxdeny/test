package com.wjwl.mobile.taocz.widget;

import java.util.List;

import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.act.V3_SentTimeSelectAct;
import com.wjwl.mobile.taocz.act.V3_WriteOrderAct;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TczV3_Item_OrderConfirmation extends LinearLayout {
	public TextView businessname, b_allprice, b_allcount, ps_info, ps_time;
	private LinearLayout cartslayout;
	private String businessId;
	private RelativeLayout layout_pstime;
	ImageView img_line, arraw;
	private boolean is_canhdfk;
	Context context;
	String position;
	String[] times = null;

	public TczV3_Item_OrderConfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		// initView();
	}

	public TczV3_Item_OrderConfirmation(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// initView();
	}

	public void initView() {
		businessname = (TextView) findViewById(R.tczv3.businessname);
		ps_info = (TextView) findViewById(R.tczv3.ps_info);
		ps_time = (TextView) findViewById(R.tczv3.ps_time);
		b_allprice = (TextView) findViewById(R.tczv3.allprice);
		b_allcount = (TextView) findViewById(R.tczv3.allcount);
		cartslayout = (LinearLayout) findViewById(R.tczv3.freelayout);
		layout_pstime = (RelativeLayout) findViewById(R.tczv3.layout_pstime);
		layout_pstime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ps_time.getText().toString().equals("")) {
					return;
				}
				Intent i = new Intent();
				i.setClass(getContext(), V3_SentTimeSelectAct.class);
				i.putExtra("senttimes", times);
				i.putExtra("position", position);
				getContext().startActivity(i);
			}
		});
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
	}

	public void setBusiness_AllPrice(CharSequence text) {
		this.b_allprice.setText(text);
	}

	public void setBusiness_AllCount(CharSequence text) {
		this.b_allcount.setText(text);
	}

	public void setPS_Time(String text) {
		this.ps_time.setText(text);
	}

	public void setStr_PS_Time(String text) {
		if (!text.equals(""))
			this.times = text.split(",");
	}

	public void setPS_Info(String text) {
		this.ps_info.setText(text);
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
			TczV3_Item_Item_OrderConfirmation item_cart;
			if (i < cartslayout.getChildCount()) {
				item_cart = (TczV3_Item_Item_OrderConfirmation) cartslayout
						.getChildAt(i);
				item_cart.setVisibility(View.VISIBLE);
			} else {
				LayoutInflater flater = LayoutInflater.from(this.getContext());
				item_cart = (TczV3_Item_Item_OrderConfirmation) flater.inflate(
						R.layout.tczv3_item_item_orderconfirmation, null);
				item_cart.initView();
				this.cartslayout.addView(item_cart);
			}
			item_cart.setImg(product.getItemimageurl());
			item_cart.setName(product.getItemtitle());
			item_cart.setGuiGe(product.getItembusinessname());
			item_cart.setPrice(Arith.to2zero(product.getItemdiscount()));
			item_cart.setCount(product.getItemcount());
			item_cart.setItemid(product.getItemid());
			item_cart = (TczV3_Item_Item_OrderConfirmation) this
					.findViewById(R.tczv3.item_item_orderconfirmation);
			item_cart.setTag(product);
			item_cart.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// Msg_Citem product = (Msg_Citem) v.getTag();
					// Intent i = new Intent();
					// i.putExtra("itemid", product.getItemid());
					// i.setClass(context, ShoppingContentAct.class);
					// context.startActivity(i);
				}
			});
		}
	}
}
