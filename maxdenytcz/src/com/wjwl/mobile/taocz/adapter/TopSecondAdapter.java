package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mdx.mobile.adapter.MAdapter;
import com.wjwl.mobile.taocz.widget.TopsecondItem;
public class TopSecondAdapter extends BaseAdapter {
	Context context;
	ArrayList<HashMap<String, String>> list;
	String mark;

	public TopSecondAdapter(Context context,ArrayList<HashMap<String, String>> allmData,String mark) {
		this.context = context;
		this.list = allmData;
		this.mark=mark;
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			TopsecondItem item = new TopsecondItem(context);
			convertView = item;
		}
		TopsecondItem item=(TopsecondItem)convertView;
		if(position==0){
			item.setText("全部");
		}else{
			item.setText((String) list.get(position).get("secondmenuname"));
		}
//		if(mark.equals("1")){
//			if(position==0){
//				item.setText("全部");
//			}else{
//				item.setText((String) list.get(position).get("secondmenuname"));
//			}
//		}else{
//			item.setText((String) list.get(position).get("secondmenuname"));
//		}
		
		item.setId((String) list.get(position).get("secondmenuid"));
		item.setNumber((String) list.get(position).get("secondmenunumber"));
		item.setMark(mark);
		return item;
	}

}
