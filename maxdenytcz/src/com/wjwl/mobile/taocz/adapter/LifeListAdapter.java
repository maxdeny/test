package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.widget.Item_LifeList;

public class LifeListAdapter extends MAdapter<Msg_Citem> {


	public LifeListAdapter(Context context, List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem carts =   get(position);
		if (convertView == null) {
			Item_LifeList item = new Item_LifeList(this.getContext());
			convertView = item;
		}
		Item_LifeList item = (Item_LifeList) convertView;
		item.set(carts);
		return convertView;
	}
}