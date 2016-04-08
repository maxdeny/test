package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.wjwl.mobile.taocz.widget.Item_ContentImgList;

public class ContentImgListAdapter extends MAdapter<Msg_Cpic> {
	public MImageView mimage;

	public ContentImgListAdapter(Context context, List<Msg_Cpic> list) {
		super(context, list);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Cpic app = get(position);
		if (convertView == null) {
			Item_ContentImgList item = new Item_ContentImgList(
					this.getContext());
			convertView = item;
		}
		Item_ContentImgList item = (Item_ContentImgList) convertView;
//		item.set(app);
		item.set2(app, getList());
		if (position == 0)
			mimage = item.mimage;
		return convertView;
	}
}
