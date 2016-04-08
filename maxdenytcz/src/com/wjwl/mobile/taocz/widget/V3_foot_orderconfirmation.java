package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class V3_foot_orderconfirmation extends LinearLayout {
	public EditText ed_title;

	public V3_foot_orderconfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
		// TODO Auto-generated constructor stub
	}

	public V3_foot_orderconfirmation(Context context) {
		super(context);
		initview();
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.v3_foot_orderconfirmation, this);
		ed_title = (EditText) findViewById(R.v3_foot_orderconfirmation.ed_fapiaotaitou);
	}

	public String gettitle() {
		return ed_title.getText().toString().trim();
	}

}
