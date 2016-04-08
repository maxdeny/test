package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_Item_Comment;

public class TczV3_CommentAdapter extends MAdapter<Map<String, Object>> {
	public TczV3_CommentAdapter(Context context,
			ArrayList<Map<String, Object>> list, int Resoure) {
		super(context, list, Resoure);
		// TODO Auto-generated constructor stub
	}

	public TczV3_CommentAdapter(Context context,
			ArrayList<Map<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Map<String, Object> map = get(position);
		if (convertView == null) {
			TczV3_Item_Comment item = new TczV3_Item_Comment(this.getContext());
			convertView = item;
		}
		TczV3_Item_Comment item = (TczV3_Item_Comment) convertView;
		item.setName((String) map.get("name"));
		item.setContent((String) map.get("content"));
		item.setDate((String) map.get("date"));
		item.setRatingBar((String) map.get("starnum"));
		return convertView;

	}

}
