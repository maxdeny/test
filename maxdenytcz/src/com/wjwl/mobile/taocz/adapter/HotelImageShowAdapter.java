package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.widget.Item_HotelImageShow;

public class HotelImageShowAdapter extends MAdapter<Msg_Cpic> {

	public HotelImageShowAdapter(Context context, List<Msg_Cpic> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Cpic app = get(position);
		if (convertView == null) {
			Item_HotelImageShow item = new Item_HotelImageShow(this.getContext());
			convertView = item;
		}
		Item_HotelImageShow item = (Item_HotelImageShow)convertView;
		item.setImg(app.getImageurl());
		return convertView;
	}
}
