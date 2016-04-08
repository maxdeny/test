//package com.wjwl.mobile.taocz.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.wjwl.mobile.taocz.widget.Item_Takeout_ImgShow;
//
//public class ImageListAdapter extends MAdapter<Msg_Billitem> {
//	public ImageListAdapter(Context context, List<Msg_Billitem> list) {
//		super(context, list);
//	}
//
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Msg_Billitem billitem = get(position);
//		if (convertView == null) {
//			Item_Takeout_ImgShow item = new Item_Takeout_ImgShow(
//					this.getContext());
//			convertView = item;
//		}
//		Item_Takeout_ImgShow item = (Item_Takeout_ImgShow) convertView;
//		item.set(billitem);
//        item.set2(billitem,getList());
//		return convertView;
//	}
//}
