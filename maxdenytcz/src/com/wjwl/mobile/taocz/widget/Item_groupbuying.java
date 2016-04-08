package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;

public class Item_groupbuying extends LinearLayout {
	private MImageView productimg;
	private TextView newprice, oldprice, title, businessname, businessaddress,
			buynum, zhekou;
	public String businessId, productId, productType;
	//private int id;
	public Button bt_see, bt_map;
	private Context context;
	private View clic_view;

	public Item_groupbuying(Context context) {
		super(context);
		this.context = context;
		initview();
		// TODO Auto-generated constructor stub
	}

	public Item_groupbuying(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.item_groupbuying, this);
		productimg = (MImageView) findViewById(R.item_groupbuying.img);
		newprice = (TextView) findViewById(R.item_groupbuying.newprice);
		oldprice = (TextView) findViewById(R.item_groupbuying.oldprice);
		title = (TextView) findViewById(R.item_groupbuying.title);
		businessname = (TextView) findViewById(R.item_groupbuying.businessname);
		businessaddress = (TextView) findViewById(R.item_groupbuying.businessaddress);
		buynum = (TextView) findViewById(R.item_groupbuying.buynum);
		zhekou = (TextView) findViewById(R.item_groupbuying.zhekou);
		bt_see = (Button) findViewById(R.item_groupbuying.bt_see);
		bt_map = (Button) findViewById(R.item_groupbuying.bt_map);
		clic_view = (View) findViewById(R.item_groupbuying.clic_view);
		clic_view.setOnClickListener(onclick);
		bt_see.setOnClickListener(onclick);
		bt_map.setOnClickListener(onclick);
	}

	private OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.item_groupbuying.bt_see:
			case R.item_groupbuying.clic_view:
				Intent i = new Intent();
				if (productType.equals("service")) {
					i.putExtra("itemid", productId);
					i.setClass(context, LifeContentAct.class);
				} else if (productType.equals("material")) {
					i.putExtra("itemid", productId);
					i.setClass(context, ShoppingContentAct.class);
				}
				context.startActivity(i);
				break;
			case R.item_groupbuying.bt_map:
				break;
			}
		}
	};

	public void setNewPrice(CharSequence text) {
		this.newprice.setText("￥" + text);
	}

	public void setOldPrice(CharSequence text) {
		this.oldprice.setText(text);
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText("店铺：" + text);
	}

	public void setBusinessAddress(CharSequence text) {
		this.businessaddress.setText(text);
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setZheKou(CharSequence text) {
		this.zhekou.setText(text + "折");
	}

	public void setBuyNum(CharSequence text) {
		this.buynum.setText(text);
	}

	public void setproduvtImg(Object obj) {
		this.productimg.setObj(obj);
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public void setProductId(String id) {
		this.productId = id;
	}

	public void setProductType(String type) {
		this.productType = type;
	}
}
