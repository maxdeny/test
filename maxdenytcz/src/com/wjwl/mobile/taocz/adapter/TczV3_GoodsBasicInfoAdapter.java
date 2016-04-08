package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_Item_GoodsBasicInfo;

public class TczV3_GoodsBasicInfoAdapter extends MAdapter<Map<String, Object>> {
	public TczV3_GoodsBasicInfoAdapter(Context context,
			ArrayList<Map<String, Object>> list, int Resoure) {
		super(context, list, Resoure);
		// TODO Auto-generated constructor stub
	}

	public TczV3_GoodsBasicInfoAdapter(Context context,
			ArrayList<Map<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Map<String, Object> map = get(position);
		if (convertView == null) {
			TczV3_Item_GoodsBasicInfo item = new TczV3_Item_GoodsBasicInfo(this.getContext());
			convertView = item;
		}
		TczV3_Item_GoodsBasicInfo item = (TczV3_Item_GoodsBasicInfo) convertView;
		item.setImg((String) map.get("img"));
		item.setItemid((String) map.get("itemid"));
		item.setTitle((String) map.get("title"));
		item.setTczPrice((String) map.get("tcz_price"));
		item.setOldPrice((String) map.get("old_price"));
		return convertView;

	}

}
