package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wjwl.mobile.taocz.R;

public class Item_item_orderlifedetails extends LinearLayout {
	private TextView num, state;

	public Item_item_orderlifedetails(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Item_item_orderlifedetails(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initview() {
		num = (TextView) findViewById(R.item_item_myorderlifedetails.num);
		state = (TextView) findViewById(R.item_item_myorderlifedetails.state);
	}

	public void setNum(CharSequence text) {
		this.num.setText(text);
	}

	public void setState(CharSequence text) {
		this.state.setText(text);
	}

}
