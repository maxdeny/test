package com.wjwl.mobile.taocz.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wjwl.mobile.taocz.widget.SearchIteam;

public class SearchAdapter  extends BaseAdapter{
	Context c;
	List<HashMap<String,Object>> data;
	public SearchAdapter(Context c,List<HashMap<String,Object>> data){
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
		SearchIteam item=new SearchIteam(c);
		if(((String)map.get("style")).equals("gouwu")){
			item.setName((String)map.get("name"));//+"-购物"
		}
//		else{
//			item.setName((String)map.get("name")+"-团购");
//		}
		item.setStyle((String)map.get("style"));
		return item;
	}

}
