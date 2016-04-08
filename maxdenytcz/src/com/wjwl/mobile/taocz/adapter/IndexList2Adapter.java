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
//import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
//import com.wjwl.mobile.taocz.widget.Item_IndexList2;
//
//
//public class IndexList2Adapter extends MAdapter<Map<String, Object>> {
//
//	public IndexList2Adapter(Context context, List<Map<String, Object>> list) {
//		super(context, list);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		Map<String, Object> app = get(position);
//		if (convertView == null) {
//			Item_IndexList2 item = new Item_IndexList2(
//					this.getContext());
//			convertView = item;
//		}
//		Item_IndexList2 item = (Item_IndexList2) convertView;
//		item.setItemid((String)app.get("itemid"));
//		item.setIcon((String)app.get("icon"));
//		item.setStyle((String)app.get("img"));
//		return convertView;
//	}
//
//}
//
