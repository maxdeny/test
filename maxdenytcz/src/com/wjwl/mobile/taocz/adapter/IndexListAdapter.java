package com.wjwl.mobile.taocz.adapter;

import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.Isubject.Msg_Isubject;
import com.wjwl.mobile.taocz.widget.Item_index;

public class IndexListAdapter extends MAdapter<Msg_Isubject> {

	public IndexListAdapter(Context context, List<Msg_Isubject> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Isubject app = get(position);
		if (convertView == null) {
			Item_index item = new Item_index(
					this.getContext());
			convertView = item;
		}
		Item_index item = (Item_index) convertView;
		item.setItemid(app.getSubjectid());
		item.setDescription(app.getDescription());
		item.setCategoryname(app.getV3Categoryname());
		item.setCategoryjumptyep(app.getV3Categoryjumptyep());
		item.setImg(app.getSubjectimgurl()+".480x100.jpg");
		item.setTitle(app.getDescription());
		item.setType(app.getV3Categoryjumptyep());
		item.setJhType(app.getJumptype());
		item.setYHurl(app.getJumptype());
		
		return convertView;
	}

}
