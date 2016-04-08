package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.Item_scenicList;

public class ScenicLsitAdapter extends MAdapter<Msg_Citem> {

	public ScenicLsitAdapter(Context context, List<Msg_Citem> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem info = get(position);
		if (convertView == null) {
			Item_scenicList item = new Item_scenicList(this.getContext());
			convertView = item;
		}
		Item_scenicList item = (Item_scenicList) convertView;
		item.setTitle(info.getItemtitle());
		item.setPrice(Arith.to2zero(info.getItemprice()));
		item.setDistance(info.getIteminfo());
		item.setStartImg(Integer.parseInt(info.getItemstar()));
		item.setImg(info.getItemimageurl());
		return convertView;
	}
}