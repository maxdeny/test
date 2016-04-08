package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_groupgoodsdetails extends LinearLayout {
	private MImageView img;
	private String itemid;
	private TextView price, title;

	public Item_groupgoodsdetails(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Item_groupgoodsdetails(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

	}

	public void initview() {
		img = (MImageView) findViewById(R.id.img);
		price = (TextView) findViewById(R.id.price);
		title = (TextView) findViewById(R.id.title);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void setPersonImg(String object) {
		this.img.setObj(object);
		this.img.setType(9);
	}

	public void setPersonImgId(Integer object) {
		this.img.setBackgroundResource(object);
	}

	public void setItemId(String text) {
		this.itemid = text;
	}

	public void setPrice(CharSequence text) {
		this.price.setText("￥" + text);
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}
}
