package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class Item_HotelImageShow extends LinearLayout {
	private MImageView mimage;
	private View click;

	public Item_HotelImageShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
		// TODO Auto-generated constructor stub
	}

	public Item_HotelImageShow(Context context) {
		super(context);
		initview();
		// TODO Auto-generated constructor stub
	}

	private void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_index_ad_img, this);
		mimage = (MImageView) findViewById(R.item_iad.image);
		click = findViewById(R.item_iad.click);
		mimage.setType(0);
		click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setImg(String obj) {
		mimage.setObj(obj);
	}
}
