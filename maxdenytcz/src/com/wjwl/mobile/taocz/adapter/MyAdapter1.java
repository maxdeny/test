package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.wjwl.mobile.taocz.widget.Item_LifeList;
import com.wjwl.mobile.taocz.widget.TuangouOrderItem;

public class MyAdapter1 extends MAdapter<Msg_Morder_Item> {


	public MyAdapter1(Context context, List<Msg_Morder_Item> list) {
		super(context,list);
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Morder_Item item =   get(position);
		if(convertView==null){
			convertView=new TuangouOrderItem(this.getContext());
		}
		((TuangouOrderItem) convertView).setData(item.getProductname(),
				item.getPaytime(),
				item.getLevel(),
				item.getTotal(),
				 item.getItemcount(),
				item.getBusinessstate());
		return convertView;
	}
}
