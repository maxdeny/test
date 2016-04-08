package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wjwl.mobile.taocz.widget.MyTczIteam;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

public class MyTczAdapter  extends BaseAdapter{
	Context c;
	List<HashMap<String,Object>> data;
	public MyTczAdapter(Context c,List<HashMap<String,Object>> data){
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
		// TODO Auto-generated method stub
		Map map=data.get(position);
		MyTczIteam item=new MyTczIteam(c);
		item.setData((Integer)map.get("pic"),
				(String)map.get("ts"));
		return item;
	}

}
