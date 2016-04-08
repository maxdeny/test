package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.widget.Item_HotelList;

public class HotelSearchListAdapter extends MAdapter<Msg_Citem> {


	public HotelSearchListAdapter(Context context, List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem carts =   get(position);
		if (convertView == null) {
			Item_HotelList item = new Item_HotelList(this.getContext());
			convertView = item;
		}
		Item_HotelList item = (Item_HotelList) convertView;
		item.sethotelName(carts.getItemtitle());
		item.sethotelPrice(Float.parseFloat(carts.getItemprice()));
		item.sethotelAddress(carts.getItembusinessname());
		item.setId(carts.getItemid());
		item.setproduvtImg(carts.getItemimageurl());
		return convertView;
	}
}