package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.widget.Item_ShoppingList1;

public class ShoppingListAdapter1 extends MAdapter<Msg_Citem> {

	public ShoppingListAdapter1(Context context, List<Msg_Citem> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem carts = get(position);
		if (convertView == null) {
			Item_ShoppingList1 item = new Item_ShoppingList1(this.getContext());
			convertView = item;
		}
		Item_ShoppingList1 item = (Item_ShoppingList1) convertView;
		item.set(carts);
		item.setbusinessNameGONE();
		return convertView;
	}
}
