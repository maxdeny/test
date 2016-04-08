package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.Item_attention;

public class AttentionAdapter extends MAdapter<Map<String, Object>> {

	public AttentionAdapter(Context context, List<Map<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		Map<String, Object> map = get(position);
		if (convertView == null) {
			Item_attention item = new Item_attention(this.getContext());
			convertView = item;
		}
		Item_attention item = (Item_attention) convertView;
		item.setBusinessImg((String) map.get("b_img"));
		item.setBusinessName((String) map.get("b_name"));
		item.setBusinessPro((String) map.get("b_product"));
		return convertView;
	}

}
