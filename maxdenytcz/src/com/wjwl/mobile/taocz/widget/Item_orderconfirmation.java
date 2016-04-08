package com.wjwl.mobile.taocz.widget;

import java.text.DecimalFormat;
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

public class Item_orderconfirmation extends LinearLayout {
	public TextView businessname, pronum, pay, hdfk, psxx;
	private LinearLayout cartslayout;
	private String businessId;
	private RelativeLayout psxx_layout;
	ImageView img_line,arraw;
	private boolean is_canhdfk;
    Context context;
	public Item_orderconfirmation(Context context) {
		super(context);
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	public Item_orderconfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}

	public void init() {
		businessname = (TextView) findViewById(R.item_order_confirmation.bussinessname);
		pronum = (TextView) findViewById(R.item_order_confirmation.allpronum);
		pay = (TextView) findViewById(R.item_order_confirmation.allpropay);
		hdfk = (TextView) findViewById(R.item_order_confirmation.huodaofukuan);
		psxx = (TextView) findViewById(R.item_order_confirmation.peisongxinxi);
		psxx_layout = (RelativeLayout) findViewById(R.item_order_confirmation.clic_layout1);
		cartslayout = (LinearLayout) findViewById(R.item_order_confirmation.addlayout);
		img_line = (ImageView) findViewById(R.item_order_confirmation.line);
		setPssxVisibility();
	}

	public void setPssxVisibility() {
		if (is_canhdfk) {
			psxx.setTextColor(0xfc3b00);
			psxx.getResources().getString(R.string.orderconfirmation_text2);
			psxx_layout.setVisibility(View.VISIBLE);
			img_line.setVisibility(View.VISIBLE);
		} else {
			psxx.setTextColor(0x999999);
			psxx.getResources().getString(R.string.orderconfirmation_text3);
			psxx_layout.setVisibility(View.GONE);
			img_line.setVisibility(View.INVISIBLE);
		}
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
	}

	public void setpay(CharSequence text) {
		this.pay.setText(text);
	}

	public void setpronum(String text) {
		this.pronum.setText(text);
	}

	public void sethdfk(CharSequence text) {
		this.hdfk.setText(text);
	}

	public void setpsxx(CharSequence text) {
		this.psxx.setText(text);
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
			Item_item_orderconfirmation item_cart;

			if (i < cartslayout.getChildCount()) {
				item_cart = (Item_item_orderconfirmation) cartslayout
						.getChildAt(i);
				item_cart.setVisibility(View.VISIBLE);
			} else {
				LayoutInflater flater = LayoutInflater.from(this.getContext());
				item_cart = (Item_item_orderconfirmation) flater.inflate(
						R.layout.item_item_orderconfirmation, null);
				item_cart.initview();
				this.cartslayout.addView(item_cart);
			}
			item_cart.setproductName(product.getItemtitle());
			item_cart.setproductPrice(Arith.to2zero(product.getItemprice()));
			item_cart.setBusinessId(this.businessId);
			item_cart.setProductId(product.getItemid());
			item_cart.setproduvtImg(product.getItemimageurl());
			item_cart.setproductNum(product.getItemcount());
			item_cart.setId(); // this must be the last line on this
			item_cart=(Item_item_orderconfirmation) this.findViewById(R.id.item_item_orderconfirmation);
			item_cart.setTag(product);
			item_cart.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Msg_Citem product=(Msg_Citem) v.getTag();
				    Intent i=new Intent();
					i.putExtra("itemid",product.getItemid());
					i.setClass(context, ShoppingContentAct.class);
					context.startActivity(i);
				}});
		}
	}

	public void setTotalPric(List<Msg_Citem> list) {
		float totalPrice = 0;
		int tempnum = 0;
		Msg_Citem product;
		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			float price = Float.parseFloat(product.getItemprice());
			float num = Float.parseFloat(product.getItemcount());
			totalPrice += price * num;
			tempnum = (int) (tempnum + num);
		}
		setpronum("共计" + String.valueOf(tempnum) + "件商品");
//		String parten = "#.##";
//		DecimalFormat decimal = new DecimalFormat(parten);
		String str=Arith.to2zero(totalPrice+"");
		this.pay.setText("￥" + str);
	}

}
