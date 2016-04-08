package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.Item_BusinessLiist;

public class BusinessList2Adapter extends MAdapter<Map<String, Object>> {
	Context context;
	ArrayList<Map<String, Object>> list;
	String actfrom;

	public BusinessList2Adapter(Context context,
			ArrayList<Map<String, Object>> mData, String actfrom) {
		super(context, mData);
		this.context = context;
		this.list = mData;
		this.actfrom = actfrom;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			Item_BusinessLiist item = new Item_BusinessLiist(this.getContext());
			convertView = item;
		}
		Item_BusinessLiist item = (Item_BusinessLiist) convertView;
		item.setText((String) list.get(position).get("name"));
		item.setId((String) list.get(position).get("id"));
		item.setActFrom(actfrom);
		return convertView;
	}
}
