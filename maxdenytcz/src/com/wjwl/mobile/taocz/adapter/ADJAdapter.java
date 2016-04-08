//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.wjwl.mobile.taocz.widget.Item_DPJ;
//
//public class ADJAdapter extends MAdapter<Map<String, Object>> {
//	public ADJAdapter(Context context, List<Map<String, Object>> list) {
//		super(context, list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Map<String, Object> date = get(position);
//		if (convertView == null) {
//			Item_DPJ item = new Item_DPJ(this.getContext());
//			convertView = item;
//		}
//		Item_DPJ item = (Item_DPJ) convertView;
//		item.setImg((String) date.get("img"));
//		item.setBusinessName((String) date.get("businessname"));
//		item.setProductName((String) date.get("productname"));
//		item.setCount((String) date.get("count"));
//		item.setPrice((String) date.get("price"));
//		item.setItemid((String) date.get("itemid"));
//
//		return convertView;
//	}
//}