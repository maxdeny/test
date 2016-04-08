//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnLongClickListener;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.Item_takeoutbox;
//
//public class TakeOutBoxAdapter extends MAdapter<Map<String, Object>> {
//
//	public TakeOutBoxAdapter(Context context,
//			ArrayList<Map<String, Object>> list, int Resoure) {
//		super(context, list, Resoure);
//		// TODO Auto-generated constructor stub
//	}
//
//	public TakeOutBoxAdapter(Context context,
//			ArrayList<Map<String, Object>> list) {
//		super(context, list);
//		// TODO Auto-generated constructor stub
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		Map<String, Object> map = get(position);
//		if (convertView == null) {
//			Item_takeoutbox item = (Item_takeoutbox) createView(R.layout.item_takeoutbox);
//			item.initView();
//			convertView = item;
//		}
//		Item_takeoutbox item = (Item_takeoutbox) convertView;
//		item.setPeiSongFei(((String) map.get("peisongfei")).trim());
//		item.setHeJi((Float.parseFloat(((String) map.get("heji")).equals("")?"0":(String) map.get("heji"))+Float.parseFloat((((String)map.get("peisongfei")).equals("")?"0":(String)map.get("peisongfei"))))+"");
//		item.setBussinessId(((String) map.get("businessid")).trim());
//		item.setBussinessName(((String) map.get("businessname")).trim());
//		item.setAddress(((String) map.get("address")).trim());
//		item.setAddressid(((String) map.get("addressid")).trim());
//		//item.setPayType(((String) map.get("paytype")).trim());
//		item.setPayType("货到付款");
//		item.setNeed(((String) map.get("need")).trim());
//		item.setListMsg_Billitem((List<Msg_Billitem>) map.get("items"));
//		item.setTakeoutBoxLayout();
//		item.setPosition("" + position);
//		
//		return convertView;
//
//	}
//
//}
