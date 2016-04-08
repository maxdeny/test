package com.wjwl.mobile.taocz.widget;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.GroupBuyContentsAct;
import com.wjwl.mobile.taocz.act.LifeContentAct;
import com.wjwl.mobile.taocz.act.OrderTypeConfirmationAct;
import com.wjwl.mobile.taocz.act.ShoppingContentAct;
import com.wjwl.mobile.taocz.commons.Arith;

public class Item_myorderlifedetails extends LinearLayout {
	private MImageView productimg;
	private TextView productname, productprice, productallprice, productnum,
			bussinessname, ordernum, sendmessage, ordertime;
	private LinearLayout addlayout, proinfo_layout;
	private String productid,orderNo;
	private RelativeLayout sendmessage_layout;
	Context context;
	private Button toPay;

	public Item_myorderlifedetails(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public Item_myorderlifedetails(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public void init() {
		productimg = (MImageView) findViewById(R.item_myorderlifedetails.img);
		productallprice = (TextView) findViewById(R.item_myorderlifedetails.allprice);
		productprice = (TextView) findViewById(R.item_myorderlifedetails.price);
		productnum = (TextView) findViewById(R.item_myorderlifedetails.num);
		productname = (TextView) findViewById(R.item_myorderlifedetails.name);
		bussinessname = (TextView) findViewById(R.item_myorderlifedetails.bussinessname);
		ordernum = (TextView) findViewById(R.item_myorderlifedetails.ordernum);
		ordertime = (TextView) findViewById(R.item_myorderlifedetails.time);
		sendmessage = (TextView) findViewById(R.item_myorderlifedetails.sendmessage);
		addlayout = (LinearLayout) findViewById(R.item_myorderlifedetails.addlayout);
		proinfo_layout = (LinearLayout) findViewById(R.id.item_myorderlifedetails);
		sendmessage_layout = (RelativeLayout) findViewById(R.item_myorderlifedetails.sendmessage_layout);
		sendmessage_layout.setVisibility(View.GONE);
		sendmessage_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		toPay=(Button) findViewById(R.item_myorderlifedetails.topay);
		toPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				F.toPay(orderNo, getContext(),"shenghuo");
			}
		});
		
		proinfo_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent();
				i.putExtra("itemid", productid);
				i.setClass(context, GroupBuyContentsAct.class);
				context.startActivity(i);
			}
		});
	}

	public void setOrderNum(String text) {// 商品数量
		this.orderNo=text;
		this.ordernum.setText(text);
	}
	

	public void setImg(String text) {// 商品图片
		this.productimg.setObj(text);
	}

	public void setPrice(String text) {// 商品单价
		this.productprice.setText("￥"+text);
	}

	public void setProductNum(String text) {// 商品数量
		this.productnum.setText(text);
	}

	public void setProductName(String text) { // 商品名字
		this.productname.setText(text);
	}

	public void setBussinessName(String text) { // 商家名称
		this.bussinessname.setText(text);
	}

	public void setOrderTime(String text) { // 下单时间
		this.ordertime.setText(text);
	}

	public void setAllPrice(Msg_Morder_Item product) { // 商品总价
		float price = Float.parseFloat(product.getPrice());
		float num = Float.parseFloat(product.getItemcount());
		float totalPrice = price * num;
//		String parten = "#.##";
//		DecimalFormat decimal = new DecimalFormat(parten);
		String str= Arith.to2zero(totalPrice+"");
		this.productallprice.setText("￥"+str);
	}

	public void setState(String text) { // 操作状态
//		if(text.indexOf("付款")>=0){
//			toPay.setVisibility(VISIBLE);
//		}else{
//			toPay.setVisibility(GONE);
//		}
		toPay.setVisibility(GONE);
		this.sendmessage.setText(text);
	}

	public void setProductId(String id) { // 操作状态
		this.productid = id;
	}

	public LinearLayout getCartsLayout() {
		return addlayout;
	}

	public void setBusinessLayout(ArrayList<Map<String, Object>> mData) {
		for (int j = 0; j < addlayout.getChildCount(); j++) {
			addlayout.getChildAt(j).setVisibility(View.GONE);
		}

		for (int i = 0; i < mData.size(); i++) {
			Item_item_orderlifedetails item;

			if (i < addlayout.getChildCount()) {
				item = (Item_item_orderlifedetails) addlayout.getChildAt(i);
				item.setVisibility(View.VISIBLE);
			} else {
				LayoutInflater flater = LayoutInflater.from(this.getContext());
				item = (Item_item_orderlifedetails) flater.inflate(
						R.layout.item_item_orderlifedetails, null);
				item.initview();
				this.addlayout.addView(item);
			}
			item.setNum((String) mData.get(i).get("num"));
			item.setState((String) mData.get(i).get("state"));
		}
	}

}
