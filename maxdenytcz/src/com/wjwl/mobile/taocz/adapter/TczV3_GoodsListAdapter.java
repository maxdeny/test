package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
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
import com.wjwl.mobile.taocz.act.TczV3_GoodsListAct;
import com.wjwl.mobile.taocz.act.TczV3_GroupGoodsDetailsAct;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.commons.Arith;

public class TczV3_GoodsListAdapter extends MAdapter<Msg_Citem> {

	RelativeLayout addcar_layout;
	private TextView productname, productprice, productoriginalprice,
			businessname;
	private TextView t_ico1, t_ico2, t_ico3, t_ico4;
	RelativeLayout editlayout;
	View mclick;

	public TczV3_GoodsListAdapter(Context context, List<Msg_Citem> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final String specid;
		final String itemselltype;
		final String Other1;
		final String ishasattribute;
		final String itemid;
		Msg_Citem carts = get(position);
		if (convertView == null) {
			LayoutInflater flater = LayoutInflater.from(this.getContext());
			convertView = flater.inflate(R.layout.tczv3_item_goodslist, null);
		}
		final MImageView mimg = (MImageView) convertView
				.findViewById(R.tczv3.productimg);
		final FrameLayout fl = (FrameLayout) ((View) parent.getParent()
				.getParent()).findViewById(R.tczv3.frame);
		final TextView carnumber = (TextView) ((View) parent.getParent()
				.getParent()).findViewById(R.tczv3.carnumber);
		t_ico1 = (TextView) convertView.findViewById(R.tczv3.icon1);
		t_ico2 = (TextView) convertView.findViewById(R.tczv3.icon2);
		t_ico3 = (TextView) convertView.findViewById(R.tczv3.icon3);
		t_ico4 = (TextView) convertView.findViewById(R.tczv3.icon4);
		businessname = (TextView) convertView
				.findViewById(R.tczv3.businessname);
		mclick = convertView.findViewById(R.id.click);
		productname = (TextView) convertView.findViewById(R.tczv3.productname);
		productprice = (TextView) convertView
				.findViewById(R.tczv3.productprice);
		productoriginalprice = (TextView) convertView
				.findViewById(R.tczv3.productoriginalprice);
		addcar_layout = (RelativeLayout) convertView
				.findViewById(R.tczv3.addcar_layout);
		specid = carts.getSpecid();
		itemselltype = carts.getItemselltype();
		Other1 = carts.getOther1();
		itemid = carts.getItemid();
		ishasattribute = carts.getV3Hasattribute();
		set(carts);
		// productoriginalprice.setVisibility(View.GONE);// 隐藏了，原价
		mimg.setObj(carts.getItemimageurl());
		addcar_layout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(getContext(), "TczV3_GoodsListAct", "", 0);
				} else if (Other1.equals("0")) {// 如果已售完
					Frame.HANDLES.get("TczV3_GoodsListAct").get(0).sent(5, "");
				} else if (ishasattribute.equals("true")) {// 如果有属性
					Frame.HANDLES.get("TczV3_GoodsListAct").get(0)
							.sent(6, itemid);
				} else {
					int[] end_location = new int[2];
					carnumber.getLocationInWindow(end_location);
					int endY = end_location[1] - mimg.getTop();
					int endX = end_location[0] - mimg.getTop();

					TranslateAnimation ta = new TranslateAnimation(0, endX,0, endY);
					final MImageView mimage = new MImageView(v.getContext());
					mimage.setObj(mimg.getObj());
//					int[] location = new int[2];
//					mimg.getLocationOnScreen(location);
					int left = mimg.getLeft()
							+ ((View) mimg.getParent().getParent()).getLeft();
					int top = mimg.getTop()
							+ ((View) mimg.getParent().getParent()).getTop();
					fl.addView(mimage,
							new LayoutParams(mimg.getWidth(), mimg.getHeight()));
					fl.setPadding(left, top + 300, 0, 0);
					ta.setDuration(1000);
					mimage.startAnimation(ta);
					ta.setAnimationListener(new MyAnimationListener(fl, mimage));
					String[] str = new String[] { specid, itemselltype };
					Frame.HANDLES.get("TczV3_GoodsListAct").get(0).sent(4, str);
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

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
	}

	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥"
				+ String.valueOf(text.replace(".00", ".0")));
	}

	public void setProductoriginalprice(String text) {
		productoriginalprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		this.productoriginalprice.setText("￥"
				+ String.valueOf(text.replace(".00", ".0")));
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}

	public void setIcon(String text) {//1特，2积，3组，4增
		t_ico1.setVisibility(View.GONE);
		t_ico2.setVisibility(View.GONE);
		t_ico3.setVisibility(View.GONE);
		t_ico4.setVisibility(View.GONE);
		if (text.equals(""))
			return;
		String[] str = text.split(",");
		if (str.length > 0) {
			int a = Integer.parseInt(str[0]);
			t_ico1.setVisibility(View.VISIBLE);
			t_ico1.setBackgroundColor(TczV3_GoodsListAct.ico_colors[a-1]);
			t_ico1.setText(TczV3_GoodsListAct.ico_str[a-1]);
		}
		if (str.length > 1) {
			int a = Integer.parseInt(str[1]);
			t_ico2.setVisibility(View.VISIBLE);
			t_ico2.setBackgroundColor(TczV3_GoodsListAct.ico_colors[a-1]);
			t_ico2.setText(TczV3_GoodsListAct.ico_str[a-1]);
		}
		if (str.length > 2) {
			int a = Integer.parseInt(str[2]);
			t_ico3.setVisibility(View.VISIBLE);
			t_ico3.setBackgroundColor(TczV3_GoodsListAct.ico_colors[a-1]);
			t_ico3.setText(TczV3_GoodsListAct.ico_str[a-1]);
		}
		if (str.length > 3) {
			int a = Integer.parseInt(str[3]);
			t_ico4.setVisibility(View.VISIBLE);
			t_ico4.setBackgroundColor(TczV3_GoodsListAct.ico_colors[a-1]);
			t_ico4.setText(TczV3_GoodsListAct.ico_str[a-1]);
		}
	}

	public void set(final Msg_Citem item) {
		setproductName(item.getItemtitle());
		setproductPrice(item.getItemdiscount().equals("") ? "0.00" : Arith
				.to2zero(item.getItemdiscount()));
		if (item.getItemprice().equals("") || item.getItemprice().equals("0")
				|| item.getItemprice().equals("0.00")
				|| item.getItemprice().equals("0.0"))
			productoriginalprice.setVisibility(View.INVISIBLE);
		else {
			productoriginalprice.setVisibility(View.VISIBLE);
			setProductoriginalprice(Arith.to2zero(item.getItemprice()));
		}
		setIcon(item.getV3Freightinfo());
		setBusinessName(item.getItembusinessname());
		mclick.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(item.getItemselltype().equals("4")){
					Intent i = new Intent();
					i.putExtra("itemid", item.getSpecid());
					i.putExtra("flag", item.getItemselltype());
//					i.putExtra("umcount", TczV3_GoodsListAct.UMCOUNT);
					i.setClass(v.getContext(), TczV3_GroupGoodsDetailsAct.class);//
					getContext().startActivity(i);	
				}
				else{
					Intent i = new Intent();
					i.putExtra("itemid", item.getItemid());
					i.putExtra("umcount", TczV3_GoodsListAct.UMCOUNT);
					i.setClass(v.getContext(), TczV3_GoodsDetailsAg.class);//
					getContext().startActivity(i);	
				}
			
			}
		});

	}
}