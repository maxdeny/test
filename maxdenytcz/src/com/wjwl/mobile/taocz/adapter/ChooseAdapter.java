package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.ChooseItem;
public class ChooseAdapter extends MAdapter<Map<String, Object>> {
	Context context;
	ArrayList<Map<String, Object>> list;
	String mynavtype;

	public ChooseAdapter(Context context,
			ArrayList<Map<String, Object>> allmData,String navtype) {
		super(context, allmData);
		this.context = context;
		this.list = allmData;
		this.mynavtype=navtype;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChooseItem item = new ChooseItem(context);
		item.setText((String) list.get(position).get("name"));
		item.setId((String) list.get(position).get("id"));
		item.setNavtype(mynavtype);
		return item;
	}

}
