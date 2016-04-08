package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Item_HotelList extends LinearLayout {

	private MImageView hotelimg;
	private ImageView itemsold;
	private TextView hotelname, hotelprice, hotelAddress;
	private RelativeLayout editlayout;
	private String id;

	public Item_HotelList(Context context) {
		super(context);
		initview();

	}

	public Item_HotelList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_hotellist, this);
		itemsold = (ImageView) findViewById(R.item_hotellist.hotelarr);
		hotelimg = (MImageView) findViewById(R.item_hotellist.hotelimg);
		hotelname = (TextView) findViewById(R.item_hotellist.hotelname);
		hotelprice = (TextView) findViewById(R.item_hotellist.hotelprice);
		hotelAddress = (TextView) findViewById(R.item_hotellist.hoteladdress);
	}

	public void sethotelName(CharSequence text) {
		this.hotelname.setText(text);
	}
	
	public void setHotelImg(Object obj){
		this.hotelimg.setObj(obj);
		
	}

	public void sethotelPrice(float text) {
		this.hotelprice.setText(Arith.to2zero(String.valueOf(text)));
	}

	public void setproduvtImg(Object obj) {
		this.hotelimg.setObj(obj);
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	public void sethotelAddress(CharSequence text) {
		this.hotelAddress.setText(text);
	}
	
	public void setId(String id) {
		this.id=id;
	}
}