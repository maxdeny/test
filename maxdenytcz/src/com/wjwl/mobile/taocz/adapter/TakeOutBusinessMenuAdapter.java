//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.Item_takeoutbusinessmenu1;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//
//public class TakeOutBusinessMenuAdapter extends MAdapter<Map<String, Object>> {
//
//	public TakeOutBusinessMenuAdapter(Context context,
//			ArrayList<Map<String, Object>> list, int Resoure) {
//		super(context, list, Resoure);
//		// TODO Auto-generated constructor stub
//	}
//
//	public TakeOutBusinessMenuAdapter(Context context,
//			ArrayList<Map<String, Object>> list) {
//		super(context, list);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		Map<String, Object> map = get(position);
//		if (convertView == null) {
//			Item_takeoutbusinessmenu1 item = (Item_takeoutbusinessmenu1) createView(R.layout.item_takeoutbusinessmenu);
//			item.initView();
//			convertView = item;
//		}
//		Item_takeoutbusinessmenu1 item = (Item_takeoutbusinessmenu1) convertView;
//		item.setcategoryId((String)map.get("categoryid"));
//		item.setTile((String) map.get("title") + "("+ (String) map.get("typecount")+")");
//		item.setTakeoutBusinessMenuLayout(((List<Msg_Billitem>)map.get("billitems")));
//		return convertView;
//	}
//
//}
