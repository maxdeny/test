package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_orderconfirmation;

public class OrderConfirmationAdapter extends MAdapter<Msg_CitemList> {

	public OrderConfirmationAdapter(Context context,List<Msg_CitemList> orderlist) {
		super(context,orderlist);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_CitemList business = get(position);
		if (convertView == null) {
			Item_orderconfirmation item = (Item_orderconfirmation) createView(R.layout.item_order_confirmation);
			item.init();
			convertView = item;
		}
		Item_orderconfirmation item = (Item_orderconfirmation)convertView;
		item.setBusinessName(business.getBusinessname());
		item.setTotalPric(business.getCitemList());
		item.setBusinessId(business.getBusinessid());
		item.setBusinessLayout(business.getCitemList()); //this must be the last line on this
		item.sethdfk(business.getFreight());
		
		return convertView;
	}

}
