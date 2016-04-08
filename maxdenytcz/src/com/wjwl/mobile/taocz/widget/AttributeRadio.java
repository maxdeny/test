package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

public class AttributeRadio extends RadioButton{

	public AttributeRadio(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}


	public AttributeRadio(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public AttributeRadio(Context context) {
		super(context);
		init();
	}

	public void init(){
		this.setButtonDrawable(R.drawable.default_none);
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_attribute_checkbox));
		this.setTextSize(18);
		this.setGravity(Gravity.CENTER);
		this.setTextColor(0xff333333);
		this.setPadding(12,5,12, 5);
	}
}
