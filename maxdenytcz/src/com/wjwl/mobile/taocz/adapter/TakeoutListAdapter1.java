//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.List;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.wjwl.mobile.taocz.widget.Item_TakeoutList1;
//
//public class TakeoutListAdapter1 extends MAdapter<Msg_Cbusinessinfo> {
//	public TakeoutListAdapter1(Context context,
//			List<Msg_Cbusinessinfo> list) {
//		super(context, list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Msg_Cbusinessinfo item_content = get(position);
//		if (convertView == null) {
//			Item_TakeoutList1 item = new Item_TakeoutList1(
//					this.getContext());
//			convertView = item;
//		}
//		Item_TakeoutList1 item = (Item_TakeoutList1) convertView;
//		item.setTakeouttype(item_content.getProvidetype());
//		item.setTitle(item_content.getBusinessname());
//		item.setQuan(item_content.getRecommend());
//		item.setHui(item_content.getGoodin());
//		item.setAddress(item_content.getBusinessaddress());
//		item.setProductimg(item_content.getBusinessimage());
//		item.setBusinessId(item_content.getBusinessid());
//		item.setDistance(item_content.getRemark());
//		item.setAreaid(item_content.getWmAreaid());
//		return convertView;
//	}
//}
