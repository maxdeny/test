package com.wjwl.mobile.taocz.widget;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.Lihua_Act;
import com.wjwl.mobile.taocz.act.TczV3ShopGoodsListAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.commons.Arith;

public class Item_ShoppingCart extends LinearLayout {
	private TextView businessname, freight, txttotalPrice, ifsonghuo,
			shopchecktext;
	private LinearLayout cartslayout;
	private String businessId, sendtype, str_businessname;
	private Context context;
	private Msg_Citem product;
	RelativeLayout layout_businessname;
	int mpostion;
	Button shopcheck;
	List<Msg_Citem> mlist;

	public Item_ShoppingCart(Context context) {
		super(context);
		this.context = context;
	}

	public Item_ShoppingCart(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void init() {
		businessname = (TextView) findViewById(R.item_shoppingcart.businessname);
		// freight = (TextView) findViewById(R.item_shoppingcart.freight);
		txttotalPrice = (TextView) findViewById(R.item_shopping_item_item_totalprice.totalprice);
		cartslayout = (LinearLayout) findViewById(R.item_shoppingcart.cartslayout);
		ifsonghuo = (TextView) findViewById(R.item_shopping_item_item_totalprice.ifsonghuo);
		layout_businessname = (RelativeLayout) findViewById(R.item_shoppingcart.layout_businessname);
		shopcheck = (Button) findViewById(R.item_shoppingcart.shopcheck);
		shopchecktext = (TextView) findViewById(R.item_shoppingcart.shopchecktext);

		layout_businessname.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("businessid", businessId);
				i.putExtra("title", str_businessname);
				i.setClass(context, TczV3ShopGoodsListAct.class);
				context.startActivity(i);
			}
		});

		shopcheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (shopchecktext.getText().equals("false")) {
					for (int i = 0; i < mlist.size(); i++) {
						// Frame.HANDLES.get("ShoppingCartAct").get(0).sent(101,
						// new
						// String[]{mlist.get(i).getSpecid(),"true",businessId});
						Frame.HANDLES.sentAll("ShoppingCartAct", 101,
								new String[] { mlist.get(i).getSpecid(),
										"true", businessId });
					}
				} else {
					for (int i = 0; i < mlist.size(); i++) {
						// Frame.HANDLES.get("ShoppingCartAct").get(0).sent(101,
						// new
						// String[]{mlist.get(i).getSpecid(),"false",businessId});
						Frame.HANDLES.sentAll("ShoppingCartAct", 101,
								new String[] { mlist.get(i).getSpecid(),
										"false", businessId });
					}
				}
			}
		});
	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
		this.str_businessname = text.toString();
	}

	public void setFreight(CharSequence text) {
		// this.freight.setText("(运费" + text + "元)");
	}

	public void setBusinessId(String id) {
		this.businessId = id;
	}

	public void setPostion(int postion) {
		this.mpostion = postion;
	}

	public void setSendType(String sendtype) {
		this.ifsonghuo.setText(sendtype);
	}

	public void setItemChecked(String checked) {
		this.shopchecktext.setText(checked);
		if (checked.equals("true")) {
			this.shopcheck.setBackgroundResource(R.drawable.shopcart_icon_ped);
		} else {
			this.shopcheck.setBackgroundResource(R.drawable.shopcart_icon_nor);
		}
	}

	public void setBusinessLayout(List<Msg_Citem> list, boolean isVisiable) {
		mlist = list;
		for (int j = 0; j < cartslayout.getChildCount(); j++) {
			cartslayout.getChildAt(j).setVisibility(View.GONE);
		}

		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			if (!product.getItemcount().equals("")) {

				Item_ShoppingCart_Item item_cart;

				if (i < cartslayout.getChildCount()) {
					item_cart = (Item_ShoppingCart_Item) cartslayout
							.getChildAt(i);
					item_cart.setVisibility(View.VISIBLE);
				} else {
					LayoutInflater flater = LayoutInflater.from(this
							.getContext());
					item_cart = (Item_ShoppingCart_Item) flater.inflate(
							R.layout.item_shoppingcart_item, null);
					item_cart.initview();
					this.cartslayout.addView(item_cart);
				}
				item_cart.setproductName(product.getItemtitle());
				item_cart
						.setproductPrice(Arith.to2zero(product.getItemprice()));
				item_cart.setBusinessId(this.businessId);
				item_cart.setspecId(product.getSpecid());
				item_cart.setProductId(product.getItemid());
				item_cart.setproduvtImg(product.getItemimageurl());
				item_cart.setItemChecked(product.getItemlevel());
				if (product.getItemlimited().equals("")) {
					item_cart.setproductMaxNum(product.getOther1());
				} else {
					item_cart.setproductMaxNum(product.getItemlimited());
				}
				item_cart.setproductNum(product.getItemcount());
				// item_cart.setId(i); // this must be the last line on this
				item_cart.setTag(product);
				item_cart.setflag(product.getItemselltype());
				item_cart.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Msg_Citem product = (Msg_Citem) v.getTag();
						if (product.getItemselltype().equals("1")||product.getItemselltype().equals("2")) {
							Intent i = new Intent();
							i.putExtra("itemid", product.getItemid());
							i.setClass(context, TczV3_GoodsDetailsAg.class);
							context.startActivity(i);

						} else {
							// Toast.makeText(context, "组合商品不能查看详情~",
							// Toast.LENGTH_SHORT).show();
						}
					}
				});
				if (isVisiable) {
					item_cart.setEditLayoutVisible(View.VISIBLE);
					item_cart.setButtonVisible(View.VISIBLE);
				} else {
					item_cart.setEditLayoutVisible(View.GONE);
					item_cart.setButtonVisible(View.GONE);
				}
				// if (i < cartslayout.getChildCount()) {
				// item_cart = (Item_ShoppingCart_Item) cartslayout
				// .getChildAt(i);
				// item_cart.setVisibility(View.VISIBLE);
				// } else {
				// LayoutInflater flater = LayoutInflater.from(this
				// .getContext());
				// item_cart = (Item_ShoppingCart_Item) flater.inflate(
				// R.layout.item_shoppingcart_item, null);
				// item_cart.initview();
				// this.cartslayout.addView(item_cart);
				// }
				// item_cart.setproductName(product.getItemtitle());
				// item_cart
				// .setproductPrice(Arith.to2zero(product.getItemprice()));
				// item_cart.setBusinessId(this.businessId);
				// item_cart.setspecId(product.getSpecid());
				// item_cart.setProductId(product.getItemid());
				// item_cart.setproduvtImg(product.getItemimageurl());
				// if (product.getItemlimited().equals("")) {
				// item_cart.setproductMaxNum(product.getOther1());
				// } else {
				// item_cart.setproductMaxNum(product.getItemlimited());
				// }
				// item_cart.setproductNum(product.getItemcount());
				// item_cart.setId(); // this must be the last line on this
				// item_cart.setTag(product);
				// item_cart.setflag(product.getItemselltype());
				//
				// layout_businessname.setOnClickListener(new OnClickListener()
				// {
				//
				// @Override
				// public void onClick(View v) {
				// Intent i = new Intent();
				// i.putExtra("businessid", businessId);
				// i.putExtra("title", str_businessname);
				// i.setClass(context, Lihua_Act.class);
				// context.startActivity(i);
				// }
				// });
				// item_cart.setOnClickListener(new OnClickListener() {
				// @Override
				// public void onClick(View v) {
				// // TODO Auto-generated method stub
				// Msg_Citem product = (Msg_Citem) v.getTag();
				// Intent i = new Intent();
				// i.putExtra("itemid", product.getItemid());
				// i.setClass(context, V3_ShoppingDetailsAg.class);
				// context.startActivity(i);
				// }
				// });
				// if (isVisiable) {
				// item_cart.setEditLayoutVisible(View.VISIBLE);
				// item_cart.setButtonVisible(View.VISIBLE);
				// } else {
				// item_cart.setEditLayoutVisible(View.GONE);
				// item_cart.setButtonVisible(View.GONE);
				// }
			}
		}
	}

	public LinearLayout getCartsLayout() {
		return cartslayout;
	}

	public void setTotalPric(List<Msg_Citem> list) {
		float totalPrice = 0;
		Msg_Citem product;
		for (int i = 0; i < list.size(); i++) {
			product = list.get(i);
			if (!product.getItemprice().equals("")) {
				float price = Float.parseFloat(product.getItemprice());
				float num = Float.parseFloat(product.getItemcount());
				totalPrice += price * num;
			}
		}
		// String parten = "#.##";
		// DecimalFormat decimal = new DecimalFormat(parten);
		String str = Arith.to2zero(totalPrice + "");
		txttotalPrice.setText("￥" + str);
	}
}