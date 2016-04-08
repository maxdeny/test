package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

public class Item_movielist extends LinearLayout{
	private MImageView productimg;
	private TextView productname, productprice,sell,
			productoriginalprice,star1,star2,star3,star4,star5;
	private RelativeLayout editlayout;
	private String id;

	public Item_movielist(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview();

	}

	public Item_movielist(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_more_list, this);
		productimg = (MImageView) findViewById(R.item_movielist.productimg);
		productname = (TextView) findViewById(R.item_movielist.productname);
		productprice = (TextView) findViewById(R.item_movielist.price);
		sell = (TextView) findViewById(R.item_life.businessname);
		star1=(TextView)findViewById(R.item_movielist.logistics_star1);
		star2=(TextView)findViewById(R.item_movielist.logistics_star2);
		star3=(TextView)findViewById(R.item_movielist.logistics_star3);
		star4=(TextView)findViewById(R.item_movielist.logistics_star4);
		star5=(TextView)findViewById(R.item_movielist.logistics_star5);
	}

	public void setProductImage(String text) {
		this.productimg.setObj(text);
	}
	
	public void setProductName(CharSequence text) {
		this.productname.setText(text);
	}
	public void setSell(CharSequence text) {
		this.sell.setText("卖出："+text);
	}

	public void setProductPrice(float text) {
		this.productprice.setText("￥" + String.valueOf(text));
	}

	public void setProductoriginalprice(float text) {
		this.productoriginalprice.setText("￥" + String.valueOf(text));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}
	
	public void setStar(CharSequence text){
		switch(Integer.valueOf(text.toString().trim())){
		case 0:
			star1.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star2.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star3.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star4.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star5.setBackgroundResource(R.drawable.sceniclist_star_gray);
			break;
		case 1:
			star1.setBackgroundResource(R.drawable.sceniclist_star_red);
			star2.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star3.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star4.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star5.setBackgroundResource(R.drawable.sceniclist_star_gray);
			break;
		case 2:
			star1.setBackgroundResource(R.drawable.sceniclist_star_red);
			star2.setBackgroundResource(R.drawable.sceniclist_star_red);
			star3.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star4.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star5.setBackgroundResource(R.drawable.sceniclist_star_gray);
			break;
		case 3:
			star1.setBackgroundResource(R.drawable.sceniclist_star_red);
			star2.setBackgroundResource(R.drawable.sceniclist_star_red);
			star3.setBackgroundResource(R.drawable.sceniclist_star_red);
			star4.setBackgroundResource(R.drawable.sceniclist_star_gray);
			star5.setBackgroundResource(R.drawable.sceniclist_star_gray);
			break;
		case 4:
			star1.setBackgroundResource(R.drawable.sceniclist_star_red);
			star2.setBackgroundResource(R.drawable.sceniclist_star_red);
			star3.setBackgroundResource(R.drawable.sceniclist_star_red);
			star4.setBackgroundResource(R.drawable.sceniclist_star_red);
			star5.setBackgroundResource(R.drawable.sceniclist_star_gray);
			break;
		case 5:
			star1.setBackgroundResource(R.drawable.sceniclist_star_red);
			star2.setBackgroundResource(R.drawable.sceniclist_star_red);
			star3.setBackgroundResource(R.drawable.sceniclist_star_red);
			star4.setBackgroundResource(R.drawable.sceniclist_star_red);
			star5.setBackgroundResource(R.drawable.sceniclist_star_red);
			break;
		}
	}
	public void setId(String id) {
		this.id=id;
	}
}
