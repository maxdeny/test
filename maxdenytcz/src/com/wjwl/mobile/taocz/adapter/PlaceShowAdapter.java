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
//import com.wjwl.mobile.taocz.widget.Item_PlaceList;
//
//public class PlaceShowAdapter extends MAdapter <Map<String,String>>{
//	
//	public PlaceShowAdapter(Context context, List<Map<String,String>> list) {
//		super(context,list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Map placemsg =   get(position);
//		if (convertView == null) {
//			Item_PlaceList item = new Item_PlaceList(this.getContext());
//			convertView = item;
//		}
//		Item_PlaceList item = (Item_PlaceList) convertView;
//		item.set(placemsg);
//		return convertView;
//	}
//}
