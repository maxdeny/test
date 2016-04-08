package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_scenicList extends LinearLayout {

	private MImageView img;
	private TextView title, distance, price;
	private ImageView star1, star2, star3, star4, star5;

	public Item_scenicList(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview();

	}

	public Item_scenicList(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_sceniclist, this);
		img = (MImageView) findViewById(R.item_sceniclist.productimg);
		title = (TextView) findViewById(R.item_sceniclist.scenicname);
		distance = (TextView) findViewById(R.item_sceniclist.distance);
		price = (TextView) findViewById(R.item_sceniclist.price);
		star1 = (ImageView) findViewById(R.item_sceniclist.star1);
		star2 = (ImageView) findViewById(R.item_sceniclist.star2);
		star3 = (ImageView) findViewById(R.item_sceniclist.star3);
		star4 = (ImageView) findViewById(R.item_sceniclist.star4);
		star5 = (ImageView) findViewById(R.item_sceniclist.star5);
	}

	public void setTitle(String str) {
		this.title.setText(str);
	}

	public void setDistance(String str) {
		this.distance.setText(str);
	}

	public void setPrice(String str) {
		this.price.setText("￥" + str);
	}

	public void setStartImg(int i) {
		switch (i) {
		case 0:
			star1.setImageResource(R.drawable.sceniclist_star_gray);
			star2.setImageResource(R.drawable.sceniclist_star_gray);
			star3.setImageResource(R.drawable.sceniclist_star_gray);
			star4.setImageResource(R.drawable.sceniclist_star_gray);
			star5.setImageResource(R.drawable.sceniclist_star_gray);
			break;
		case 1:
			star1.setImageResource(R.drawable.sceniclist_star_red);
			star2.setImageResource(R.drawable.sceniclist_star_gray);
			star3.setImageResource(R.drawable.sceniclist_star_gray);
			star4.setImageResource(R.drawable.sceniclist_star_gray);
			star5.setImageResource(R.drawable.sceniclist_star_gray);
			break;
		case 2:
			star1.setImageResource(R.drawable.sceniclist_star_red);
			star2.setImageResource(R.drawable.sceniclist_star_red);
			star3.setImageResource(R.drawable.sceniclist_star_gray);
			star4.setImageResource(R.drawable.sceniclist_star_gray);
			star5.setImageResource(R.drawable.sceniclist_star_gray);
			break;
		case 3:
			star1.setImageResource(R.drawable.sceniclist_star_red);
			star2.setImageResource(R.drawable.sceniclist_star_red);
			star3.setImageResource(R.drawable.sceniclist_star_red);
			star4.setImageResource(R.drawable.sceniclist_star_gray);
			star5.setImageResource(R.drawable.sceniclist_star_gray);
			break;
		case 4:
			star1.setImageResource(R.drawable.sceniclist_star_red);
			star2.setImageResource(R.drawable.sceniclist_star_red);
			star3.setImageResource(R.drawable.sceniclist_star_red);
			star4.setImageResource(R.drawable.sceniclist_star_red);
			star5.setImageResource(R.drawable.sceniclist_star_gray);
			break;
		case 5:
			star1.setImageResource(R.drawable.sceniclist_star_red);
			star2.setImageResource(R.drawable.sceniclist_star_red);
			star3.setImageResource(R.drawable.sceniclist_star_red);
			star4.setImageResource(R.drawable.sceniclist_star_red);
			star5.setImageResource(R.drawable.sceniclist_star_red);
			break;
		}
	}

	public void setImg(String str) {
		this.img.setObj(str);
	}
}