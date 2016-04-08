package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.widget.Item_RanqiList;
import com.wjwl.mobile.taocz.widget.Item_ShoppingList;

public class RanqiListAdapter extends MAdapter<Msg_Citem> {


	public RanqiListAdapter(Context context, List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem carts =   get(position);
		if (convertView == null) {
			Item_RanqiList item = new Item_RanqiList(this.getContext());
			convertView = item;
		}
		Item_RanqiList item = (Item_RanqiList) convertView;
		item.set(carts);
		item.setBusinessNameGone();
		return convertView;
	}
}