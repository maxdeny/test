package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.Item_ShoppingCart;

public class ShoppingCartAdapter extends MAdapter<Msg_CitemList.Builder>{
	boolean isVisiable=false;
	List<Msg_CitemList.Builder> morderlist;
	
	public void NotifyDataSetChanged(boolean isVisiable,List<Msg_CitemList.Builder> orderlist){
		 this.isVisiable = isVisiable;
		 morderlist=orderlist;
		 this.notifyDataSetChanged();
	}
	
	public int getCount() {
        return morderlist.size();
}
	
	
	public ShoppingCartAdapter(Context context,List<Msg_CitemList.Builder> orderlist) {
		super(context,orderlist);
		 morderlist=orderlist;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_CitemList.Builder business = morderlist.get(position);
		if (convertView == null) {
			Item_ShoppingCart item = (Item_ShoppingCart) createView(R.layout.item_shoppingcart);
			item.init();
			convertView = item;
		}
		
		Item_ShoppingCart item = (Item_ShoppingCart)convertView;
		item.setBusinessName(business.getBusinessname());
		item.setFreight(business.getFreight());
		item.setTotalPric(business.getCitemList());
		item.setBusinessId(business.getBusinessid());
		item.setBusinessLayout(business.getCitemList(),isVisiable);
		item.setSendType(business.getItemtype());//配送信息
		item.setItemChecked(business.getShippingFee());
		
		item.setPostion(position);
		
		return convertView;
	}
}