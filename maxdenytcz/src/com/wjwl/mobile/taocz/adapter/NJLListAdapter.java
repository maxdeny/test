//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.wjwl.mobile.taocz.widget.Item_NJLList;
//
//public class NJLListAdapter extends MAdapter<Msg_Cbusinessinfo> {
//
//	public NJLListAdapter(Context context, List<Msg_Cbusinessinfo> list) {
//		super(context, list);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Msg_Cbusinessinfo item_content = get(position);
//		if (convertView == null) {
//			Item_NJLList item = new Item_NJLList(this.getContext());
//			convertView = item;
//		}
//		Item_NJLList item = (Item_NJLList) convertView;
//		item.setTitle(item_content.getBusinessname());
//		item.setQuan(item_content.getRecommend());
//		item.setHui(item_content.getGoodin());
//		item.setAddress(item_content.getBusinessaddress());
//		item.setProductimg(item_content.getBusinessimage());
//		item.setBusinessId(item_content.getBusinessid());
//		item.setDistance(item_content.getRemark().replace(":", ":\n"));
//		item.setUrl(item_content.getBusinessnearby());
//		return convertView;
//	}
//
//}
