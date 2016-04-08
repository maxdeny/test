package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.main.SortItemActivity;
import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 商品子类 适配
 * listview适配类
 * @author kysl
 * 
 */
public class SortItemListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	private int select_item;

	public SortItemListViewAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {

		return data.size();
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
			convertView = mInflater.inflate(R.layout.sort_item_listview_item,
					null);
			viewgroup.sort_item_listview_item_text = (TextView) convertView
					.findViewById(R.id.sort_item_listview_item_text);
			viewgroup.sort_item_listview_item_bg = (TextView) convertView
					.findViewById(R.id.sort_item_listview_item_bg);

			/**
			 * 使用tag来存储数据
			 */
			convertView.setTag(viewgroup);

		} else {
			viewgroup = (viewGroup) convertView.getTag();
		}

		this.select_item = SortItemActivity.select_item;


			if (this.select_item == position) {
				viewgroup.sort_item_listview_item_text.setTextSize(25);    //选中的Item字体：25px
				viewgroup.sort_item_listview_item_text.setTextColor(Color.rgb(87,198,215)); // 粉色
				viewgroup.sort_item_listview_item_bg.setBackgroundResource(R.drawable.line_p_02);
			} else {
				viewgroup.sort_item_listview_item_text.setTextSize(20); // 未选中的Item字体：20px
				viewgroup.sort_item_listview_item_text.setTextColor(Color.rgb(0, 0, 0)); // 黑色
				viewgroup.sort_item_listview_item_bg.setBackgroundResource(0);    //设置为无背景
			}
		
		/**
		 * 获取数据显示
		 */
		viewgroup.sort_item_listview_item_text.setText(data.get(position).get("name").toString().trim());

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView sort_item_listview_item_text, sort_item_listview_item_bg;
	}

}
