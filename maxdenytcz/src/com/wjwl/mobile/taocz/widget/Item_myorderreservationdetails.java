package com.wjwl.mobile.taocz.widget;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_myorderreservationdetails extends LinearLayout {
	public TextView servicename, state, level, date, username, tel, address;

	public Item_myorderreservationdetails(Context context) {
		super(context);
		initview();
		// TODO Auto-generated constructor stub
	}

	public Item_myorderreservationdetails(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
		// TODO Auto-generated constructor stub
	}

	private void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_myorderreservationdetails, this);
		servicename = (TextView) findViewById(R.item_myorderreservationdetails.servicename);
		state = (TextView) findViewById(R.item_myorderreservationdetails.state);
		level = (TextView) findViewById(R.item_myorderreservationdetails.level);
		date = (TextView) findViewById(R.item_myorderreservationdetails.time);
		username = (TextView) findViewById(R.item_myorderreservationdetails.username);
		tel = (TextView) findViewById(R.item_myorderreservationdetails.tel);
		address = (TextView) findViewById(R.item_myorderreservationdetails.address);
	}

	public void setServiceName(CharSequence text) {
		this.servicename.setText(text);
	}

	public void setState(CharSequence text) {
		this.state.setText(text);
	}

	public void setLevel(CharSequence text) {
		this.level.setText(text);
	}

	public void setDate(CharSequence text) {
		this.date.setText(text);
	}

	public void setUserName(CharSequence text) {
		this.username.setText(text);
	}

	public void setTel(CharSequence text) {
		this.tel.setText(text);
	}

	public void setAddress(CharSequence text) {
		this.address.setText(text);
	}

}
