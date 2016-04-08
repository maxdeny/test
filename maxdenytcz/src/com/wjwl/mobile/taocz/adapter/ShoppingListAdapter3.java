package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.commons.Arith;

public class ShoppingListAdapter3 extends MAdapter<Msg_Citem> {

	RelativeLayout addcar_layout;
	private TextView productname, productprice, businessName, itemsold,
			productoriginalprice, yishou;
	RelativeLayout editlayout;
	View mclick;
	

	public ShoppingListAdapter3(Context context, List<Msg_Citem> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final String specid , itemselltype ;
		final String itemid ;
		final String ishasattribute;
		Msg_Citem carts = get(position);
		if (convertView == null) {
			LayoutInflater flater = LayoutInflater.from(this.getContext());
			convertView = flater.inflate(R.layout.item_shoppinglist, null);
		}
		final MImageView mimg = (MImageView) convertView
				.findViewById(R.item_shoppinglist.productimg);
		final FrameLayout fl = (FrameLayout) ((View) parent.getParent()
				.getParent()).findViewById(R.shoppinglist.frame);
		final TextView carnumber = (TextView) ((View) parent.getParent()
				.getParent()).findViewById(R.shoppinglist.carnumber);

		mclick = convertView.findViewById(R.id.click);
		itemsold = (TextView) convertView
				.findViewById(R.item_shoppinglist.buyover);
		productname = (TextView) convertView
				.findViewById(R.item_shoppinglist.productname);
//		productname.getPaint().setFakeBoldText(true);
		productprice = (TextView) convertView
				.findViewById(R.item_shoppinglist.productprice);
		businessName = (TextView) convertView
				.findViewById(R.item_shoppinglist.businessname);
		productoriginalprice = (TextView) convertView
				.findViewById(R.item_shoppinglist.productoriginalprice);
		addcar_layout = (RelativeLayout) convertView
				.findViewById(R.item_shoppinglist.addcar_layout);
		yishou = (TextView) convertView
				.findViewById(R.item_shoppinglist.yishou);
		yishou.setVisibility(View.GONE);
		set(carts);
		specid = carts.getSpecid();
		itemselltype = carts.getItemselltype();
		itemid = carts.getItemid();
		ishasattribute = carts.getV3Hasattribute();
		// productoriginalprice.setVisibility(View.GONE);//隐藏了，原价
		mimg.setObj(carts.getItemimageurl());
		addcar_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(getContext(), "Lihua_Act", "", 0);
				} else if (ishasattribute.equals("true")) {// 如果有属性
					Frame.HANDLES.get("Lihua_Act").get(0).sent(6, itemid);
				} else {
					int[] end_location = new int[2];
					carnumber.getLocationInWindow(end_location);
					int endY = end_location[1] - mimg.getTop();

					TranslateAnimation ta = new TranslateAnimation(mimg
							.getLeft(), 30, mimg.getTop(), 700);
					final MImageView mimage = new MImageView(v.getContext());
					mimage.setObj(mimg.getObj());
					int[] location = new int[2];
					mimg.getLocationOnScreen(location);
					int left = mimg.getLeft()
							+ ((View) mimg.getParent().getParent()).getLeft();
					int top = mimg.getTop()
							+ ((View) mimg.getParent().getParent()).getTop();
					fl.addView(mimage,
							new LayoutParams(mimg.getWidth(), mimg.getHeight()));
					fl.setPadding(left, top + 100, 0, 0);
					ta.setDuration(1000);
					mimage.startAnimation(ta);
					ta.setAnimationListener(new MyAnimationListener(fl, mimage));
					String[] str = new String[] { specid, itemselltype };
					Frame.HANDLES.get("Lihua_Act").get(0).sent(4, str);
				}

			}
		});
		return convertView;
	}

	public class MyAnimationListener implements AnimationListener {

		FrameLayout myfl;
		MImageView mymimage;

		public MyAnimationListener(FrameLayout fl, MImageView mimage) {
			this.myfl = fl;
			this.mymimage = mimage;
		}

		@Override
		public void onAnimationEnd(android.view.animation.Animation animation) {
		}

		@Override
		public void onAnimationRepeat(android.view.animation.Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(android.view.animation.Animation animation) {
			myfl.removeView(mymimage);
		}

	}

	public void setitemSold(CharSequence text) {
		this.itemsold.setText(text);
	}

	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + String.valueOf(text.replace(".00", ".0")));
	}

	public void setProductoriginalprice(String text) {
		this.productoriginalprice.setText("￥" + String.valueOf(text.replace(".00", ".0")));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	public void setbusinessName(CharSequence text) {
		this.businessName.setText(text);
		this.businessName.setVisibility(View.INVISIBLE);
	}

	public void set(final Msg_Citem item) {
		setproductName(item.getItemtitle());
		setproductPrice(item.getItemdiscount().equals("") ? "0.00" : Arith
				.to2zero(item.getItemdiscount()));
		setbusinessName("已售:" + item.getItemsold());
		if (item.getItemprice().equals("") || item.getItemprice().equals("0")
				|| item.getItemprice().equals("0.00")
				|| item.getItemprice().equals("0.0"))
			productoriginalprice.setVisibility(View.INVISIBLE);
		else {
			productoriginalprice.setVisibility(View.VISIBLE);
			setProductoriginalprice(Arith.to2zero(item.getItemprice()));
		}
		productoriginalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		productoriginalprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		// setitemSold(item.getItemsold());
		mclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("itemid", item.getItemid());
				i.setClass(v.getContext(), V3_ShoppingDetailsAg.class);//TczV3_GoodsDetailsAg  V3_ShoppingDetailsAg
				getContext().startActivity(i);
			}
		});

	}
}