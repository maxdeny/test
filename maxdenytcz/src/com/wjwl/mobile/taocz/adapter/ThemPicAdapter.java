package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.ThemPicItem;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class ThemPicAdapter extends MAdapter<HashMap<String,Object>> {
	Context context;
	List<HashMap<String, Object>> list;
	public ThemPicAdapter(Context context, List<HashMap<String, Object>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map=list.get(position);
		ThemPicItem item=new ThemPicItem(context);
		item.setData(map.get("picurl").toString(), map.get("picid").toString());
		return item;
	}
	
}