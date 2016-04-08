package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_Item_CDZG;

public class TczV3_CDZGAdapter extends MAdapter<Map<String, Object>> {
	public TczV3_CDZGAdapter(Context context,
			ArrayList<Map<String, Object>> list, int Resoure) {
		super(context, list, Resoure);
		// TODO Auto-generated constructor stub
	}

	public TczV3_CDZGAdapter(Context context,
			ArrayList<Map<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Map<String, Object> map = get(position);
		if (convertView == null) {
			TczV3_Item_CDZG item = new TczV3_Item_CDZG(this.getContext());
			convertView = item;
		}
		TczV3_Item_CDZG item = (TczV3_Item_CDZG) convertView;
		item.setImg((String) map.get("img"));
		item.setItemid((String) map.get("itemid"));
		item.setTitle((String) map.get("title"));
		item.setPrice((String) map.get("price"));
		item.setSellCount((String) map.get("sellcount"));
		item.setActTime((String) map.get("acttime"));
		item.setEndTime((String) map.get("endtime"));
		item.setStartTime((String) map.get("starttime"));
		item.setSellType((String) map.get("selltype"));
		item.setDate();
		return convertView;

	}

}
