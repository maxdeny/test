package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.GroupBuyContentsAct;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GroupBuyItem1 extends LinearLayout {
	private MImageView productimg;
	private TextView newprice, oldprice, businessname, businessaddress, buynum;
	public String businessId, productId, productType, id;
	public Button bt_see, bt_map;
	private Context context;
	private View clic_view;
	// ImageView tj_img;
	private ImageView newproduct, mianyuyue;
	TextView name,yishou;

	public GroupBuyItem1(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
		this.context = context;
	}

	public void init() {
		LayoutInflater flater = LayoutInflater.from(getContext());
//		View view = flater.inflate(R.layout.groupbuyitem, this);
		View view = flater.inflate(R.layout.groupbuyitem1, this);
		newprice = (TextView) view.findViewById(R.groupbuyitem.price);
		oldprice = (TextView) view.findViewById(R.groupbuyitem.yprice);
		businessname = (TextView) view.findViewById(R.groupbuyitem.contents);
		buynum = (TextView) view.findViewById(R.groupbuyitem.number);
		businessaddress = (TextView) view.findViewById(R.groupbuyitem.place);
		productimg = (MImageView) view.findViewById(R.groupbuyitem.img);
		name=(TextView) view.findViewById(R.groupbuyitem.name);
		yishou=(TextView) view.findViewById(R.groupbuyitem.yishou);
		// tj_img=(ImageView) findViewById(R.groupbuyitem.tj_img);
		oldprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		// businessaddress = (TextView)
		// findViewById(R.item_groupbuying.businessaddress);
		// zhekou = (TextView) findViewById(R.item_groupbuying.zhekou);
		// bt_see = (Button) findViewById(R.item_groupbuying.bt_see);
		// bt_map = (Button) findViewById(R.item_groupbuying.bt_map);
		newproduct = (ImageView) view.findViewById(R.groupbuyitem.newproduct);
		mianyuyue = (ImageView) view.findViewById(R.groupbuyitem.mianyuyue);
		clic_view = (View) view.findViewById(R.groupbuyitem.linear);
		clic_view.setOnClickListener(onclick);
		// bt_see.setOnClickListener(onclick);
		// bt_map.setOnClickListener(onclick);
	}

	private OnClickListener onclick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.item_groupbuying.bt_see:
			case R.groupbuyitem.linear:
				Intent i = new Intent();
				// if (productType.equals("service")) {
				// i.putExtra("itemid", productId);
				// i.setClass(context, LifeContentAct.class);
				// } else if (productType.equals("material")) {
				// i.putExtra("itemid", productId);
				// i.setClass(context, ShoppingContentAct.class);
				// }
				i.putExtra("itemid", productId);
//				i.setClass(context, LifeContentAct.class);
				i.setClass(context, GroupBuyContentsAct.class);
				context.startActivity(i);
				break;
			case R.item_groupbuying.bt_map:
				break;
			}
		}
	};
	public void setData1(String na, String c,String p, String y, String n,
			String id) {
		name.setText(na);
		businessname.setText(c);
		newprice.setText("￥" + p);
		oldprice.setText("￥" + y);
		yishou.setText("已售:"+n);
		productId = id;
	}

	public void setData(String na,String c, String p, String y, String a,
			String id) {
		name.setText(na);
		businessname.setText(c);
		newprice.setText("￥" + p);
		oldprice.setText("￥" + y);
		yishou.setText(a);
		productId = id;
	}
	public void setName(String n) {
		
	}

	public void setNewPrice(CharSequence text) {
		this.newprice.setText("￥" + text);
	}

	public void setOldPrice(CharSequence text) {
		this.oldprice.setText("原价：" + text);
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText("店铺：" + text);
	}

	public void setBusinessAddress(CharSequence text) {
		this.businessaddress.setText(text);
	}

	// public void setTitle(CharSequence text) {
	// this.title.setText(text);
	// }
	//
	// public void setZheKou(CharSequence text) {
	// this.zhekou.setText(text + "折");
	// }

	public void setBuyNum(CharSequence text) {
		this.buynum.setText(text + "人订购");
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

	public void setNewProduct(String text) {
		if (text.equals("true"))
			newproduct.setVisibility(View.VISIBLE);
		else
			newproduct.setVisibility(View.GONE);
	}

	public void setManYuYue(String text) {
		if (text.equals("true"))
			mianyuyue.setVisibility(View.VISIBLE);
		else
			mianyuyue.setVisibility(View.GONE);
	}
}
