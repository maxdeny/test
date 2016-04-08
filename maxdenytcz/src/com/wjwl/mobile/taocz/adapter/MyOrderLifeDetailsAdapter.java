package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_myorderlifedetails;

public class MyOrderLifeDetailsAdapter extends MAdapter<Msg_Morder_Item> {
	public MyOrderLifeDetailsAdapter(Context context, List<Msg_Morder_Item> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Morder_Item item_life = get(position);
		if (convertView == null) {
			Item_myorderlifedetails item = (Item_myorderlifedetails) createView(R.layout.item_myorderlifedetails);
			item.init();
			convertView = item;
		}
		Item_myorderlifedetails item = (Item_myorderlifedetails) convertView;
		item.setBussinessName(item_life.getBusinessname());
		item.setPrice(item_life.getPrice());
		item.setAllPrice(item_life);
		item.setProductNum(item_life.getItemcount());
		item.setImg(item_life.getProductimg());
		item.setProductName(item_life.getProductname());
        item.setOrderNum(item_life.getPhone());
        item.setOrderTime(item_life.getPaytime());
        item.setProductId(item_life.getLevel());//设置商品ID
        item.setState(item_life.getPackagestate());
        ArrayList<Map<String, Object>> mData = null;
        mData = new ArrayList<Map<String, Object>>();
        String[] arraypaycode=item_life.getPaycode().split(",");
        String[] arraypackagestate=item_life.getPackagestate().split(",");
		for (int i = 0; i < arraypaycode.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("num",arraypaycode[i] );
			map.put("state",arraypackagestate[i]);
			mData.add(map);
		}
		item.setBusinessLayout(mData);
		return convertView;
	}
}
