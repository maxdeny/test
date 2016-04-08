package com.wjwl.mobile.taocz.adapter;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.wjwl.mobile.taocz.widget.Item_Periperal_List;

public class PeriperalListAdapter extends MAdapter<Msg_Ccategory> {
	private String latitude, longitude; 

	public PeriperalListAdapter(Context context, List<Msg_Ccategory> list,String latitude,String longitude) {
		super(context, list);
		this.latitude=latitude;
		this.longitude=longitude;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Ccategory app = get(position);
		if (convertView == null) {
			Item_Periperal_List item = new Item_Periperal_List(this.getContext());
			convertView = item;
		}
		Item_Periperal_List item = (Item_Periperal_List)convertView;
		item.set(app,latitude, longitude,getList());
		return convertView;
	}
}
