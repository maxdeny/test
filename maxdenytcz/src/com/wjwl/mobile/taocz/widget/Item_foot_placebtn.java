package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_foot_placebtn extends LinearLayout {
	public Button btnpre,btnnxt;
	public TextView pageno;
	
	
	public Item_foot_placebtn(Context context) {
		super(context);
		initview();
	}
	public Item_foot_placebtn(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_foot_placebtn, this);
		btnpre=(Button)findViewById(R.foot.btnpre);
		btnnxt=(Button)findViewById(R.foot.btnnet);
		pageno=(TextView)findViewById(R.foot.pages);
	}
}
