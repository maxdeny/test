package com.wjwl.mobile.taocz.widget;

import java.util.List;

import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TczV3_Item_AllOrderList extends LinearLayout {
	public TextView order_num, allprice, allcount, bt_1, bt_2;
	private LinearLayout cartslayout;
	private String businessId, state;
	private RelativeLayout layout_more;
	Context context;

	public TczV3_Item_AllOrderList(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		// initView();
	}

	public TczV3_Item_AllOrderList(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// initView();
	}

	public void initView() {
		order_num = (TextView) findViewById(R.tczv3.order_num);
		allprice = (TextView) findViewById(R.tczv3.allprice);
		allcount = (TextView) findViewById(R.tczv3.allprice);
		cartslayout = (LinearLayout) findViewById(R.v3_item_orderconfirmation.freelayout);

	}

	public void setOrderState(String text) {
		this.state = text;
	}

	public void setOrderNum(CharSequence text) {
		this.order_num.setText(text);
	}

	public void setBusiness_AllPrice(CharSequence text) {
		this.allprice.setText(text);
	}

	public void setBusiness_AllCount(CharSequence text) {
		this.allcount.setText(text);
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
		int num = 0;//用来设置最多显示2条数据
		if (list.size() > 2)
			num = 2;
		else
			num = list.size();
		for (int i = 0; i < num; i++) {
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
				initView();
				this.cartslayout.addView(item_cart);
			}
			item_cart.setImg(product.getItemimageurl());
			item_cart.setName(product.getItemtitle());
			item_cart.setGuiGe(product.getItembusinessname());
			item_cart.setPrice(Arith.to2zero(product.getItemprice()));
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
