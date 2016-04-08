//package com.wjwl.mobile.taocz.adapter;
//import java.util.List;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.wjwl.mobile.taocz.widget.Item_RestaurantDetails;
//
//public class RestaurantDetailsAdapter extends MAdapter<Msg_Cbusinessinfo> {
//	Context context;
//
//	public RestaurantDetailsAdapter(Context context,
//		List<Msg_Cbusinessinfo> list) {
//		super(context, list);
//		this.context = context;
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Msg_Cbusinessinfo item_content = get(position);
//		if (convertView == null) {
//			Item_RestaurantDetails item = new Item_RestaurantDetails(context);
//			convertView = item;
//		}
//		Item_RestaurantDetails item = (Item_RestaurantDetails) convertView;
//		item.setTitle( item_content.getBusinessname());
//		item.setDistance(item_content.getRemark());
//		item.setStar(item_content.getBusinessstars());
//		item.setRenjun(item_content.getEverycost());
//		item.setItemid(item_content.getBusinessid());
//	    item.setProductimg(item_content.getBusinessimage());
//	    item.setprovidetype(item_content.getProvidetype());
//	    item.setJuan(item_content.getRecommend());
//	    item.setHui(item_content.getGoodin());
//		return convertView;
//		
//	}
//}
