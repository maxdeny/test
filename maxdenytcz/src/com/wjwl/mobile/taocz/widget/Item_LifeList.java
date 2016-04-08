package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Item_LifeList extends LinearLayout {

	private MImageView productimg;
	private TextView productname, productprice, businessName,buyover,
			productoriginalprice;
	private RelativeLayout editlayout;
	private View mclick;
	private Msg_Citem mitem;

	public Item_LifeList(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview();

	}

	public Item_LifeList(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void set(Msg_Citem carts){
		this.mitem=carts;
		setproductName(carts.getItemtitle());
		setproductPrice(carts.getItemdiscount().equals("")?"0.00":Arith.to2zero(carts.getItemdiscount()));
		setbusinessName(carts.getItembusinessname());
		setbuyover(carts.getItemsold());
		setproductImage(carts.getItemimageurl());
		mclick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent();
				i.putExtra("itemid",mitem.getItemid() );
				i.setClass(v.getContext(), LifeContentAct.class);
				getContext().startActivity(i);
			}
		});
	}
	
	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_lifelist, this);
		mclick=findViewById(R.id.click);
		productimg = (MImageView) findViewById(R.item_life.productimg);
		productname = (TextView) findViewById(R.item_life.productname);
		productprice = (TextView) findViewById(R.item_life.newprice);
		buyover = (TextView) findViewById(R.item_life.buyover);
		businessName = (TextView) findViewById(R.item_life.businessname);
	}

	public void setproductImage(String text) {
		this.productimg.setObj(text);
	}
	
	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}
	public void setbuyover(CharSequence text) {
		this.buyover.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + String.valueOf(text));
	}

	public void setProductoriginalprice(String text) {
		this.productoriginalprice.setText("￥" + String.valueOf(text));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	public void setbusinessName(CharSequence text) {
		this.businessName.setText(text);
	}
}