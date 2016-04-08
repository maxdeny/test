package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.wjwl.mobile.taocz.widget.Item_myorderreservationdetails;

public class MyOrderReservationDetailsAdapter  extends MAdapter<Msg_Morder_Item>{

	public MyOrderReservationDetailsAdapter(Context context,
			List<Msg_Morder_Item> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Morder_Item item_reservation =   get(position);
		if (convertView == null) {
			Item_myorderreservationdetails item = new Item_myorderreservationdetails(this.getContext());
			convertView = item;
		}
		Item_myorderreservationdetails item = (Item_myorderreservationdetails) convertView;
		item.setServiceName(item_reservation.getProductname());
		item.setState(item_reservation.getPackagestate());
		item.setLevel(item_reservation.getLevel());
		item.setDate(item_reservation.getPaytime());
		item.setUserName(item_reservation.getName());
		item.setTel(item_reservation.getPhone());
		item.setAddress(item_reservation.getAddress());
		return convertView;
	}

}
