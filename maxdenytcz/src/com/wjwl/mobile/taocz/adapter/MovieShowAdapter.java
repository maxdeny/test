package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.Item_movielist;

public class MovieShowAdapter extends MAdapter<Msg_Citem> {
	public MovieShowAdapter(Context context, List<Msg_Citem> list) {
		super(context,list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Citem product =   get(position);
		if (convertView == null) {
			Item_movielist item = new Item_movielist(this.getContext());
			convertView = item;
		}
		Item_movielist item = (Item_movielist) convertView;
		item.setProductName(product.getItemtitle());
		item.setProductPrice(Float.parseFloat(product.getItemdiscount().equals("")?"0":Arith.to2zero(product.getItemdiscount())));
		item.setStar(product.getItemstar().equals("")?"0":product.getItemstar());
		item.setSell(product.getItemsold());
		item.setId(product.getItemid());
		item.setProductImage(product.getItemimageurl());
		return convertView;
	}
}
