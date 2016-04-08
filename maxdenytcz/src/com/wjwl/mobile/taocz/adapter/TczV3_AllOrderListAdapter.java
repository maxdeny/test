package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_Item_AllOrderList;

public class TczV3_AllOrderListAdapter extends MAdapter<Msg_CitemList> {
	public TczV3_AllOrderListAdapter(Context context,
			List<Msg_CitemList> orderlist) {
		super(context, orderlist);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_CitemList business = get(position);
		if (convertView == null) {
			TczV3_Item_AllOrderList item = (TczV3_Item_AllOrderList) createView(R.layout.tczv3_item_allorderlist);
			item.initView();
			convertView = item;
		}
		TczV3_Item_AllOrderList item = (TczV3_Item_AllOrderList) convertView;
		// item.setOrderNum(business.getTotal());
//		item.setOrderState(text);
		item.setBusiness_AllPrice(business.getTotal());
		item.setBusiness_AllCount(business.getItemcount());
		item.setBusinessId(business.getBusinessid());
		item.setBusinessLayout(business.getCitemList()); // this must be the
															// last line on this

		return convertView;
	}

}
