package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_Index_More extends LinearLayout {

	private MImageView img;
	private String itemid;
	TextView price;

	public Item_Index_More(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Item_Index_More(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		img = (MImageView) findViewById(R.v3_index_head.item_index_img);
		price = (TextView) findViewById(R.v3_index_head.zsth_price);
		img.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}
		});
	}

	public void setPrice(String text) {
		this.price.setText("￥" + text);
	}

	public void setImg(String object) {
		this.img.setObj(object);
		this.img.setType(9);
	}

	public void setItemId(String text) {
		this.itemid = text;
	}

}
