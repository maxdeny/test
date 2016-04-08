package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.Item_v3_index;

public class V3_IndexAdapter extends MAdapter<Map<String, Object>> {
	public V3_IndexAdapter(Context context,
			ArrayList<Map<String, Object>> list, int Resoure) {
		super(context, list, Resoure);
		// TODO Auto-generated constructor stub
	}

	public V3_IndexAdapter(Context context, ArrayList<Map<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Map<String, Object> map = get(position);
		if (convertView == null) {
			Item_v3_index item = new Item_v3_index(this.getContext());
			convertView = item;
		}
		Item_v3_index item = (Item_v3_index) convertView;
		item.setImg((String) map.get("url"));
		item.setItemid((String) map.get("itemid"));
		return convertView;

	}

}
