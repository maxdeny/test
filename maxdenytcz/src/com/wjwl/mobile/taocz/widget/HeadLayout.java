package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeadLayout extends LinearLayout {
	Button left, right;
	TextView text;

	public HeadLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public HeadLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public void init() {
		LayoutInflater f = LayoutInflater.from(getContext());
		View v = f.inflate(R.layout.headlayout, this);
		left = (Button) findViewById(R.headlayout.back);
		right = (Button) findViewById(R.headlayout.btn_select);
		text = (TextView) findViewById(R.headlayout.text);
	}

	public void setTitle(String s) {
		text.setText(s);
	}

	public void setLeftText(String s) {
		left.setText(s);
	}

	public void setRightText(String s) {
		right.setText(s);
	}

	public void setLeftClick(OnClickListener on) {
		left.setOnClickListener(on);
	}

	public void setRightClick(OnClickListener on) {
		right.setOnClickListener(on);
	}

	public void setLeftGone() {
		left.setVisibility(View.GONE);
	}

	public void setRightGone() {
		right.setVisibility(View.GONE);
	}

	public void setRightVISIBLE() {
		right.setVisibility(View.VISIBLE);
	}
}
