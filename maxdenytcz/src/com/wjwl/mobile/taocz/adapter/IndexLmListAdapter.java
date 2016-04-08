package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
import com.wjwl.mobile.taocz.widget.Item_IndexLmList;
import com.wjwl.mobile.taocz.widget.Item_indexlist;

public class IndexLmListAdapter extends MAdapter<Msg_Isubject> {

	public IndexLmListAdapter(Context context, List<Msg_Isubject> list_subject) {
		super(context, list_subject);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Msg_Isubject app = get(position);
		if (convertView == null) {
			Item_indexlist item = new Item_indexlist(this.getContext());
			convertView = item;
		}
		Item_indexlist item = (Item_indexlist) convertView;
		item.setItemid(app.getSubjectid());
		item.setDescription(app.getDescription());
		item.setCategoryname(app.getV3Categoryname());
		item.setCategoryjumptyep(app.getV3Categoryjumptyep());
		item.setImg(app.getSubjectimgurl());
		return convertView;
	}
}
