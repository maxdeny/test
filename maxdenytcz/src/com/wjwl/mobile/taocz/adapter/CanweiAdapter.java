package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wjwl.mobile.taocz.widget.BianmingItem;
import com.wjwl.mobile.taocz.widget.CanweiItem;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class CanweiAdapter extends BaseAdapter {
	Context c;
	List<HashMap<String,Object>> data;
	public CanweiAdapter(Context c,List<HashMap<String,Object>> data) {
		// TODO Auto-generated constructor stub
		this.c=c;
		this.data=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Map map=data.get(position);
		CanweiItem item=new CanweiItem(c);
		item.setData((Integer)map.get("pic"), (String)map.get("title"), (String)map.get("price"), (String)map.get("style"),
				(String)map.get("juli"));
		item.setStar((String)map.get("star"));
		return item;
	}
}
