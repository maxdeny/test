package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.wjwl.mobile.taocz.widget.Item_Business_List;

public class BusinessListAdapter extends MAdapter<Msg_Cbusinessinfo>{

	public BusinessListAdapter(Context context,List<Msg_Cbusinessinfo> businessinfo) {
		super(context,businessinfo);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Cbusinessinfo businessinfo = get(position);
		if (convertView == null) {
			Item_Business_List item = new Item_Business_List(this.getContext());
			convertView = item;
		}
		Item_Business_List item = (Item_Business_List)convertView;
		item.set(businessinfo);
		return convertView;
	}
}