package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.Item_coupon;
import com.wjwl.mobile.taocz.widget.Item_coupon_out;

public class CouponAdapter extends MAdapter<Map<String, Object>> {
	public CouponAdapter(Context context,List<Map<String, Object>> list) {
		super(context,list);
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		Map<String, Object> date = get(position);
		if(date.get("CanUse").equals("1")){
			//不可用
			Item_coupon_out item = new Item_coupon_out(getContext());
			item.setData(date);
			convertView = item;
		}else{
			//可用
			Item_coupon item = new Item_coupon(getContext());
			item.setData(date);
			convertView = item;
		}
		
		return convertView;
	}
}