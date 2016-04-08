package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class Item_v3_index extends LinearLayout {
	MImageView img;
	String itemid;

	public Item_v3_index(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
		// TODO Auto-generated constructor stub
	}

	public Item_v3_index(Context context) {
		super(context);
		initview();
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.item_v3_index, this);
		img = (MImageView) findViewById(R.item_v3_index.img);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setItemid(String text) {
		this.itemid = text;
	}

	public void setImg(Object obj) {
		this.img.setObj(obj);

	}
}
