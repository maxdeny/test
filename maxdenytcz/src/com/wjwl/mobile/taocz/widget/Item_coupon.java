package com.wjwl.mobile.taocz.widget;

import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wjwl.mobile.taocz.R;

public class Item_coupon extends LinearLayout {

	private TextView coupon_money, coupon_info, coupon_type, date;
	ImageView canuse;
	private LinearLayout layout;
	private String itemid;

	public Item_coupon(Context context) {
		super(context);
		initview();

	}

	public Item_coupon(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_coupon, this);
		flater.inflate(R.layout.item_new_coupon, this);
		layout=(LinearLayout)findViewById(R.id.layout);
		coupon_money = (TextView) findViewById(R.id.coupon_money);
		coupon_info = (TextView) findViewById(R.id.coupon_info);
		coupon_type = (TextView) findViewById(R.id.coupon_type);
		date = (TextView) findViewById(R.id.coupon_data);
		canuse = (ImageView) findViewById(R.id.coupon_canuse);
	}

	public void setData(Map<String, Object> data){
		
		this.coupon_money.setText((String)data.get("Coupon_Money"));
		this.coupon_info.setText((String)data.get("Coupon_Info"));
		this.coupon_type.setText((String)data.get("Coupon_Type"));
		
		
		this.date.setText((String)data.get("Date"));
		this.itemid = (String)data.get("itemid");
		
		/*String canUse = (String)data.get("CanUse");
		if (canUse != null && canUse.equals("1")){
			this.canuse.setBackgroundResource(R.drawable.icon_quan_out);
			this.layout.setBackgroundResource(R.drawable.icon_quan_border_gray);
		}else{
			this.canuse.setBackgroundResource(R.drawable.icon_coupon);
			this.layout.setBackgroundResource(R.drawable.icon_quan_border);
//			this.layout.setBackground(getResources().getDrawable(R.drawable.icon_quan_border));
		}*/
	}
}
