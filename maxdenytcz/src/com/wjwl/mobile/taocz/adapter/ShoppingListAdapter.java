package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_ShoppingList;

public class ShoppingListAdapter extends MAdapter<Msg_Citem> {


	public ShoppingListAdapter(Context context, List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem carts =   get(position);
		if (convertView == null) {
			Item_ShoppingList item = new Item_ShoppingList(this.getContext());
			convertView = item;
		}
		Item_ShoppingList item = (Item_ShoppingList) convertView;
		item.set(carts);
		
		return convertView;
	}
}