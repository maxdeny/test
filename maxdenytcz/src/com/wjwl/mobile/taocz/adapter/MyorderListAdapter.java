package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.Item_MyOrder_List;

public class MyorderListAdapter extends MAdapter<Msg_Citem>{

	public MyorderListAdapter(Context context,List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem citem = get(position);
		if (convertView == null) {
			Item_MyOrder_List item = new Item_MyOrder_List(this.getContext());
			convertView = item;
		}
		Item_MyOrder_List item = (Item_MyOrder_List)convertView;
		item.setName(citem.getItemtitle());
		item.setBusiness(citem.getItembusinessname());
		item.setPrice(Arith.to2zero(citem.getItemprice()));
		item.setDate(citem.getItemremtime());
		item.setFavoriteImg(citem.getItemimageurl());
		return convertView;
	}
}