package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.wjwl.mobile.taocz.widget.TaoxinCardIteam;

public class MyTxUAdapter extends MAdapter<Msg_Morder_Item> {

	public MyTxUAdapter(Context context, List<Msg_Morder_Item> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
	if(convertView==null){
		convertView=new TaoxinCardIteam(this.getContext());
	}
	((TaoxinCardIteam) convertView).setData(get(position).getName(),
			get(position).getProductname(),
			get(position).getItemcount(),
			get(position).getPaytime(),
			get(position).getTotal()
			);
		return convertView;
	}

}
