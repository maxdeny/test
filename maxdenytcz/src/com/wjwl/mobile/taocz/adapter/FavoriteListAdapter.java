package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.Item_Favorite_List;

public class FavoriteListAdapter extends MAdapter<Msg_Citem>{
	String ftype;

	public FavoriteListAdapter(Context context,List<Msg_Citem> list,String itemtype) {
		super(context,list);
		this.ftype=itemtype;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem citem = get(position);
		if (convertView == null) {
			Item_Favorite_List item = new Item_Favorite_List(this.getContext());
			convertView = item;
		}
		Item_Favorite_List item = (Item_Favorite_List)convertView;
		item.setName(citem.getItemtitle());
		item.setBusiness(citem.getItembusinessname());
		item.setPrice(Arith.to2zero(citem.getItemdiscount().equals("0.0")?citem.getItemprice():citem.getItemdiscount()));
		item.setDate(citem.getItemremtime());
		item.setFavoriteImg(citem.getItemimageurl());
		item.itemid=citem.getItemid();
		item.itemtype=citem.getItemtype();
		item.ftype=ftype;		
		return convertView;
	}
}