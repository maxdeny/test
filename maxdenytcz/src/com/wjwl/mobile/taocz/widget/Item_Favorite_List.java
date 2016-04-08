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
import com.wjwl.mobile.taocz.act.GroupBuyContentsAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsDetailsAg;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;
import com.wjwl.mobile.taocz.dialog.DeleteDialog;

public class Item_Favorite_List extends LinearLayout {

	private TextView name, price, business, date;
	private Button delete;
	private MImageView favoriteImg;
	public String itemid, itemtype,ftype;
	public View view;
	Context context;

	public Item_Favorite_List(Context context) {
		super(context);
		this.context = context;
		initview();
	}

	public Item_Favorite_List(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_favorite_list, this);
		favoriteImg = (MImageView) findViewById(R.item_favorite_list.favoriteimg);
		name = (TextView) findViewById(R.item_favorite_list.name);
		delete = (Button) findViewById(R.item_favorite_list.delete);
		price = (TextView) findViewById(R.item_favorite_list.price);
		business = (TextView) findViewById(R.item_favorite_list.business);
		date = (TextView) findViewById(R.item_favorite_list.date);
		view = (View) findViewById(R.item_favorite_list.clic_view);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteDialog dialog = new DeleteDialog(v.getContext(), itemid,
						itemtype,ftype);
				dialog.setTitle("收藏提示");
				dialog.setInfo("删除收藏？");
				dialog.show();
			}
		});
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				if (itemtype.equals("material")) {
					i.putExtra("itemid", itemid);
					i.setClass(context, TczV3_GoodsDetailsAg.class);
				} else {
					i.putExtra("itemid", itemid);
					i.setClass(context, GroupBuyContentsAct.class);
				}
				context.startActivity(i);
			}
		});
	}

	public void setName(CharSequence text) {
		this.name.setText(text);
	}

	public void setPrice(CharSequence text) {
		this.price.setText(text);
	}

	public void setBusiness(final CharSequence text) {
		this.business.setText(text);
	}

	public void setDate(CharSequence text) {
		this.date.setText(text);
	}
	public void setFavoriteImg(String str) {
		this.favoriteImg.setObj(str);
	}
}