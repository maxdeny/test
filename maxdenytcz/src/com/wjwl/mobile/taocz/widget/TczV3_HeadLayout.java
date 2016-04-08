package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TczV3_HeadLayout extends LinearLayout {
	TextView back, headtitle;
	Button bt_1, bt_2, bt_3;

	public TczV3_HeadLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public TczV3_HeadLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater f = LayoutInflater.from(getContext());
		View v = f.inflate(R.layout.tczv3_headlayout, this);
		back = (TextView) findViewById(R.tczv3.back);
		bt_1 = (Button) findViewById(R.tczv3.bt_1);
		bt_2 = (Button) findViewById(R.tczv3.bt_2);
		bt_3 = (Button) findViewById(R.tczv3.bt_3);
		headtitle = (TextView) findViewById(R.tczv3.headtitle);
	}

	public void setTitle(String s) {
		headtitle.setText(s);
	}

	public void setLeftText(String s) {
		back.setText(s);
	}

	public void setLeftGone() {
		back.setVisibility(View.GONE);
	}

	public void setLeftVisible() {
		back.setVisibility(View.VISIBLE);
	}

	public void setLeftClick(OnClickListener on) {
		back.setOnClickListener(on);
	}

	public void setRightButton1Click(OnClickListener on) {
		bt_1.setVisibility(View.VISIBLE);
		bt_1.setOnClickListener(on);
	}

	public void setRightButton1Background(int res) {
		bt_1.setBackgroundResource(res);
	}

	public void setRightButton1Gone() {
		bt_1.setVisibility(View.GONE);
	}

	public void setRightButton1VISIBLE() {
		bt_1.setVisibility(View.VISIBLE);
	}

	public void setRightButton2Click(OnClickListener on) {
		bt_2.setVisibility(View.VISIBLE);
		bt_2.setOnClickListener(on);
	}

	public void setRightButton2Background(int res) {
		bt_2.setBackgroundResource(res);
	}

	public void setRightButton2Gone() {
		bt_2.setVisibility(View.GONE);
	}

	public void setRightButton2VISIBLE() {
		bt_2.setVisibility(View.VISIBLE);
	}

	public void setRightButton3Text(String text) {
		bt_3.setText(text);
	}

	public void setRightButton3Click(OnClickListener on) {
		bt_3.setVisibility(View.VISIBLE);
		bt_3.setOnClickListener(on);
	}

	public void setRightButton3Background(int res) {
		bt_3.setBackgroundResource(res);
	}

	public void setRightButton3Gone() {
		bt_3.setVisibility(View.GONE);
	}

	public void setRightButton3VISIBLE() {
		bt_3.setVisibility(View.VISIBLE);
	}

	public Button getButton1() {
		if (null == bt_1) {
			bt_1 = new Button(getContext());
		}
		return bt_1;
	}
}
