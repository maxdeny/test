package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.V3_Item_OrderConfirmation;

public class V3_OrderConfirmationAdapter extends MAdapter<Msg_CitemList> {

	public V3_OrderConfirmationAdapter(Context context,
			List<Msg_CitemList> orderlist) {
		super(context, orderlist);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_CitemList business = get(position);
		if (convertView == null) {
			V3_Item_OrderConfirmation item = (V3_Item_OrderConfirmation) createView(R.layout.v3_item_orderconfirmaiton);
			item.init();
			convertView = item;
		}
		V3_Item_OrderConfirmation item = (V3_Item_OrderConfirmation) convertView;
		item.setBusinessName(business.getBusinessname());
		item.setBusinessId(business.getBusinessid());
		item.setBusinessLayout(business.getCitemList()); // this must be the															// last line on this
		item.sethdfk(business.getFreight());
		return convertView;
	}

}