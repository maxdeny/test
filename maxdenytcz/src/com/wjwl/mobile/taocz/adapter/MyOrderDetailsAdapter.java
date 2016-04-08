package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.tcz.apkfactory.data.Msg_Morder_Package.Msg_MorderPackage;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_myorderdetails;

public class MyOrderDetailsAdapter extends MAdapter<Msg_MorderPackage> {

	public MyOrderDetailsAdapter(Context context, List<Msg_MorderPackage> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_MorderPackage item_package = get(position);
		if (convertView == null) {
			Item_myorderdetails item = (Item_myorderdetails) createView(R.layout.item_myorderdetails);
			item.init();
			convertView = item;
		}
		Item_myorderdetails item = (Item_myorderdetails) convertView;
		item.setOrderNum(item_package.getOrderno().trim());
		item.setOrderDate(item_package.getOrdertime());
		item.setPackageNum(item_package.getPackage().trim());
		item.setTotalPric(item_package.getBusinessList());
		item.setOrderState(item_package.getOrderstate().trim());
		item.setOrderFrom(item_package.getSendtype().trim());
		String sendtime = item_package.getHopetime().trim();
		if (sendtime != null && sendtime != "")
			item.setPsType(sendtime);
		else
			item.setPsTypeGone();
		item.setPackageLayout(item_package.getBusinessList());

		return convertView;
	}
}
