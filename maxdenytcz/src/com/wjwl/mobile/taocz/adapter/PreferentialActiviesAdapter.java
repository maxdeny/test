//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.ArrayList;
//import java.util.Map;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import com.mdx.mobile.adapter.MAdapter;
//import com.wjwl.mobile.taocz.widget.Item_PreferentialActivies;
//
//public class PreferentialActiviesAdapter extends MAdapter<Map<String, Object>> {
//
//	public PreferentialActiviesAdapter(Context context,
//			ArrayList<Map<String, Object>> list) {
//		super(context, list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Map<String, Object> item_content = get(position);
//		if (convertView == null) {
//			Item_PreferentialActivies item = new Item_PreferentialActivies(
//					this.getContext());
//			convertView = item;
//		}
//		Item_PreferentialActivies item = (Item_PreferentialActivies) convertView;
//		item.setnewPrice((String) item_content.get("newprice"));
//		item.setoldprice((String) item_content.get("oldprice"));
//		item.settitle((String) item_content.get("title"));
//		item.setarea((String) item_content.get("area"));
//		item.setbuynums((String) item_content.get("buynums"));
//		item.setproductId((String) item_content.get("itemid"));
//		item.setproductImage((String) item_content.get("pic"));
//		return convertView;
//	}
//}