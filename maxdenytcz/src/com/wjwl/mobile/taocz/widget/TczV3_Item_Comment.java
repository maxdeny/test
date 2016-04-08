package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.wjwl.mobile.taocz.R;

public class TczV3_Item_Comment extends LinearLayout {

	private TextView name, content, date;
	private RatingBar rat;
	private String itemid;

	public TczV3_Item_Comment(Context context) {
		super(context);
		initview();
	}

	public TczV3_Item_Comment(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.tczv3_item_comment, this);
		name = (TextView) findViewById(R.tczv3.name);
		content = (TextView) findViewById(R.tczv3.content);
		rat = (RatingBar) findViewById(R.tczv3.ratingBar);
		date = (TextView) findViewById(R.tczv3.date);

	}

	public void setName(CharSequence text) {
		this.name.setText(text);
	}

	public void setContent(CharSequence text) {
		this.content.setText(text);
	}

	public void setDate(CharSequence text) {
		this.date.setText(text);
	}

	public void setString(String str) {
		this.itemid = str;
	}

	public void setRatingBar(String text) {
		if (text.equals(""))
			return;
		int starnum = Integer.parseInt(text);
		rat.setRating(starnum);
	}
}