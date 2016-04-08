package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_Item_OrderConfirmation;

public class TczV3_ConfirmationAdapter extends MAdapter<Msg_CitemList.Builder> {

	public TczV3_ConfirmationAdapter(Context context,
			List<Msg_CitemList.Builder> orderlist) {
		super(context, orderlist);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_CitemList.Builder business = get(position);
		if (convertView == null) {
			TczV3_Item_OrderConfirmation item = (TczV3_Item_OrderConfirmation) createView(R.layout.tczv3_item_orderconfirmation);
			item.initView();
			convertView = item;
		}
		TczV3_Item_OrderConfirmation item = (TczV3_Item_OrderConfirmation) convertView;
		item.setBusinessName(business.getBusinessname());
		item.setBusiness_AllPrice(business.getTotal());
		item.setBusiness_AllCount(business.getItemcount());
		item.setStr_PS_Time(business.getItemtotal());
		item.setPosition("" + position);
		item.setPS_Time(business.getFreight());
		item.setPS_Info(business.getShippingFee());
		item.setBusinessId(business.getBusinessid());
		item.setBusinessLayout(business.getCitemList()); // this must be the
															// last line on this

		return convertView;
	}

}
