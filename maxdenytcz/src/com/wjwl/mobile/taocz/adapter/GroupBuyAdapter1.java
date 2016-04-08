package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.GroupBuyItem;
import com.wjwl.mobile.taocz.widget.GroupBuyItem1;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class GroupBuyAdapter1 extends MAdapter<Msg_Citem> {
	String from;
	public GroupBuyAdapter1(Context context, List<Msg_Citem> list,String from) {
		super(context, list);
		this.from=from;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem item_group = get(position);
		if (convertView == null) {
			GroupBuyItem1 item = new GroupBuyItem1(this.getContext());
			convertView = item;
		}
		GroupBuyItem1 item = (GroupBuyItem1) convertView;
		item.setproduvtImg(item_group.getItemimageurl());
		if(from.equals("GroupBuyingListAct")){
			item.setData1(item_group.getItembusinessname(),item_group.getItemtitle(), 
					item_group.getItemdiscount(), item_group.getItemprice(),
					item_group.getItemsold(),
					item_group.getItemid());
		}else if(from.equals("NearGroupBuyingListAct")){
		item.setData(item_group.getItembusinessname(),item_group.getItemtitle(), 
				item_group.getItemdiscount(), item_group.getItemprice(),
				item_group.getOther1(), item_group.getItemid());
		}
		item.setNewProduct(item_group.getItemlevel());
		item.setManYuYue(item_group.getOther2());
		return convertView;
	}
}
