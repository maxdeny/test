package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchItemTreasureListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> arraylist;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public SearchItemTreasureListViewAdapter(Context context, ArrayList<HashMap<String, Object>> arraylist) {
		this.context = context;
		this.arraylist = arraylist;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return arraylist.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		viewGroup viewgroup;

		if (convertView == null) {
			viewgroup = new viewGroup();
			convertView = mInflater.inflate(
					R.layout.fragment_search_treasure_item, null);
			viewgroup.fragment_listview_item = (TextView) convertView
					.findViewById(R.id.fragment_listview_item);

			/**
			 * 使用tag来存储数据
			 */
			convertView.setTag(viewgroup);

		} else {
			viewgroup = (viewGroup) convertView.getTag();
		}

		/**
		 * 获取数据显示
		 */
		viewgroup.fragment_listview_item.setText((String)arraylist.get(position).get("name"));

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView fragment_listview_item;
	}

}
