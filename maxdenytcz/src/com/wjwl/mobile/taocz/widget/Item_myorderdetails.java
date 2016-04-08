package com.wjwl.mobile.taocz.widget;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.Arith;

public class Item_myorderdetails extends LinearLayout {
	public TextView packagenum, pronum, pay, ordernum, orderstate, pstype,
			orderfrom, cancelpay, orderdate, username, useraddress, userphone;
	private LinearLayout cartslayout;
	private String businessId, packageno, orderNo;
	private RelativeLayout comment_layout;
	Context context;
	Msg_Morder_Item product;
	private Button toPay;
	private int nowIndexSize = 0;
	LinearLayout layout_paytype;
	ImageView line_paytype;

	public Item_myorderdetails(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public Item_myorderdetails(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public void init() {

		comment_layout = (RelativeLayout) findViewById(R.item_myorderdetails.commment_layout);
		packagenum = (TextView) findViewById(R.item_myorderdetails.bussinessname);
		pronum = (TextView) findViewById(R.item_myorderdetails.allpronum);
		ordernum = (TextView) findViewById(R.item_myorderdetails.ordernum);
		orderstate = (TextView) findViewById(R.item_myorderdetails.orderstate);
		pay = (TextView) findViewById(R.item_myorderdetails.allpropay);
		pstype = (TextView) findViewById(R.item_myorderdetails.pstype);
		cartslayout = (LinearLayout) findViewById(R.item_myorderdetails.addlayout);
		toPay = (Button) findViewById(R.item_myorderdetails.topay);
		cancelpay = (Button) findViewById(R.item_myorderdetails.cancelpay);
		orderdate = (TextView) findViewById(R.item_myorderdetails.orderdate);
		username = (TextView) findViewById(R.item_myorderdetails.username);
		layout_paytype = (LinearLayout) findViewById(R.item_myorderdetails.layout_pstype);
		line_paytype = (ImageView) findViewById(R.item_myorderdetails.line_payout);
		orderfrom = (TextView) findViewById(R.item_myorderdetails.orderfrom);
		useraddress= (TextView) findViewById(R.item_myorderdetails.useraddress);
		userphone= (TextView) findViewById(R.item_myorderdetails.userphone);
		toPay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MobclickAgent.onEvent(getContext(), "OrderPay4");
				F.toPay(orderNo, getContext(), "gouwu");
			}
		});
		cancelpay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Frame.HANDLES.get("MyOrderDetailsAct").get(0)
						.sent(1, new String[] { orderNo });
			}
		});

		comment_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
	}

	public void setPackageNum(CharSequence text) {
		this.packagenum.setSingleLine();
		this.packagenum.setText("包裹号：" + text);
	}

	public void setOrderDate(CharSequence text) {
		this.orderdate.setText(text);
	}

	public void setpay(CharSequence text) {
		this.pay.setText(text);
	}

	public void setUserName(CharSequence text) {
		this.username.setText(text);
	}

	public void setUserPhone(CharSequence text) {
		this.userphone.setText(text);
	}

	public void setAddress(CharSequence text) {
		this.useraddress.setText(text);
	}

	public void setpronum(String text) {
		this.pronum.setText(text);
	}

	public void setPsType(CharSequence text) {
		this.pstype.setText(text);
	}

	public void setPsTypeGone() {
		layout_paytype.setVisibility(View.GONE);
		line_paytype.setVisibility(View.GONE);
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public void setOrderNum(String text) {
		this.orderNo = text;
		this.ordernum.setText(text);
	}

	public void setOrderState(String text) {
		if (text.indexOf("未付款") >= 0 || text.indexOf("未审核") >= 0) {
			cancelpay.setVisibility(VISIBLE);
		} else {
			cancelpay.setVisibility(GONE);
		}
		if (text.indexOf("未付款") >= 0) {
			toPay.setVisibility(VISIBLE);
		} else {
			toPay.setVisibility(GONE);
		}
		this.orderstate.setText(text);
	}

	public void setOrderFrom(String text) {
		// 这里要放判断
		this.orderfrom.setText("来源：" + text);
	}

	public LinearLayout getCartsLayout() {
		return cartslayout;
	}

	public void setPackageLayout(List<Msg_MorderBusiness> list) {
		for (int j = 0; j < cartslayout.getChildCount(); j++) {
			cartslayout.getChildAt(j).setVisibility(View.GONE);
		}
		nowIndexSize = 0;
		for (int i = 0; i < list.size(); i++) {
			Msg_MorderBusiness item_bussiness = list.get(i);
			setBusinessLayout(item_bussiness.getItemList());
		}

	}

	public void setBusinessLayout(List<Msg_Morder_Item> list) {
		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			Item_item_orderconfirmation item_cart;

			if (nowIndexSize < cartslayout.getChildCount()) {
				item_cart = (Item_item_orderconfirmation) cartslayout
						.getChildAt(nowIndexSize);
				item_cart.setVisibility(View.VISIBLE);
			} else {
				LayoutInflater flater = LayoutInflater.from(this.getContext());
				item_cart = (Item_item_orderconfirmation) flater.inflate(
						R.layout.item_item_orderconfirmation, null);
				item_cart.initview();
				this.cartslayout.addView(item_cart);
			}
			if (username.getText().toString().equals("")) {// 订单收货人信息
				setUserName(product.getName());
				setAddress(product.getAddress());
				setUserPhone(product.getPhone());
			}
			item_cart.setPayCode(product.getPaycode());// 判断是否可以点击详情（1,2可以点击，其他不能）
			item_cart.setProductId(product.getLevel());// 商品ID暂时放在这个变量里面
			item_cart.setproductName(product.getProductname());
			item_cart.setproductPrice(product.getPrice());
			item_cart.setBusinessId(this.businessId);
			item_cart.setproduvtImg(product.getProductimg());
			item_cart.setproductNum(product.getItemcount());
			nowIndexSize++;
		}
	}

	public void setTotalPric(List<Msg_MorderBusiness> list) {
		float allprice = 0;
		int allcount = 0;
		for (int i = 0; i < list.size(); i++) {
			Msg_MorderBusiness bussiness = list.get(i);
			List<Msg_Morder_Item> orderitemlist = bussiness.getItemList();
			float totalPrice = 0;
			int tempnum = 0;
			Msg_Morder_Item product;
			for (int j = 0; j < orderitemlist.size(); j++) {
				product = orderitemlist.get(j);
				float price = Float.parseFloat(product.getPrice());
				float num = Float.parseFloat(product.getItemcount());
				totalPrice = totalPrice + price * num;
				tempnum = (int) (tempnum + num);
			}
			allcount = allcount + tempnum;
			allprice = allprice + totalPrice;
		}
		setpronum("共计" + String.valueOf(allcount) + "件商品");
		// String parten = "#.##";
		// DecimalFormat decimal = new DecimalFormat(parten);
		String str = Arith.to2zero(allprice + "");
		this.pay.setText("￥" + str);
	}

}
