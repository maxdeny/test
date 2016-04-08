//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.wjwl.mobile.taocz.widget.TnTejiaInfoIteam;
//import com.wjwl.mobile.taocz.widget.TnTejiaIteam;
//import com.wjwl.mobile.taocz.widget.TnTejiaPLIteam;
//import com.wjwl.mobile.taocz.widget.WaimaiIteam;
//
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ListAdapter;
//
//public class TnTejia_Adapter extends BaseAdapter {
//	Context c;
//	List<HashMap<String, Object>> data;
//	String actname;
//
//	public TnTejia_Adapter(Context c, List<HashMap<String, Object>> data,
//			String actname) {
//		this.c = c;
//		this.data = data;
//		this.actname = actname;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return data.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		Map map = data.get(position);
////		if (actname.equals("TnTejia_Act")) {
////			if (convertView == null) {
////				convertView = new TnTejiaIteam(c);
////			}
////			((TnTejiaIteam) convertView).setData((String) map.get("pic"),
////					(String) map.get("name"), (String) map.get("pingfen"),
////					(String) map.get("price"), (String) map.get("style"),
////					(String) map.get("juli"));
////			((TnTejiaIteam) convertView).setMoreData(
////					(String) map.get("businessid"),
////					(String) map.get("longitude"),
////					(String) map.get("latitude"), (String) map.get("tejia"),"");
////			((TnTejiaIteam) convertView).setStar((String) map.get("star"));
////		} else 
//			if (actname.equals("TnTejiaInfo_Act")) {
//			if (convertView == null) {
//				convertView = new TnTejiaInfoIteam(c);
//			}
//			((TnTejiaInfoIteam) convertView).setData((String) map.get("img"),
//					(String) map.get("title"), (String) map.get("taop"),
//					(String) map.get("tep"), (String) map.get("roomstyle"),
//					(String) map.get("mp"), (String) map.get("itemid"),
//					(String) map.get("itemstate"),(String)map.get("istejia"),
//					(String)map.get("wifi"));
//		} else if (actname.equals("TnTejiaInfo_Act1")) {
//			if (convertView == null) {
//				convertView = new TnTejiaPLIteam(c);
//			}
//			((TnTejiaPLIteam) convertView)
//					.setData((String) map.get("contents"));
//		}
//		return convertView;
//	}
//}
