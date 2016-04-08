package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmOrderListViewAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	public ConfirmOrderListViewAdapter(Context context,
			ArrayList<HashMap<String, Object>> data) {
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
			convertView = mInflater.inflate(
					R.layout.confirmorder_listview_item, null);
			viewgroup.confirm_item_name = (TextView) convertView
					.findViewById(R.id.confirm_item_name);
			viewgroup.confirm_item_img = (ImageView) convertView
					.findViewById(R.id.confirm_item_img);
			viewgroup.confirm_item_color = (TextView) convertView
					.findViewById(R.id.confirm_item_color);
			viewgroup.confirm_item_size = (TextView) convertView
					.findViewById(R.id.confirm_item_size);
			viewgroup.confirm_item_price = (TextView) convertView
					.findViewById(R.id.confirm_item_price);
			viewgroup.confirm_item_count = (TextView) convertView
					.findViewById(R.id.confirm_item_count);

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

		viewgroup.confirm_item_name
				.setText((String) ((HashMap<String, Object>) data.get(position))
						.get("name"));
		viewgroup.confirm_item_img
				.setImageBitmap((Bitmap) ((HashMap<String, Object>) data
						.get(position)).get("path"));
		viewgroup.confirm_item_color
				.setText((String) ((HashMap<String, Object>) data.get(position))
						.get("color"));
		viewgroup.confirm_item_size
				.setText((String) ((HashMap<String, Object>) data.get(position))
						.get("size"));
		viewgroup.confirm_item_price
				.setText((String) ((HashMap<String, Object>) data.get(position))
						.get("price"));
		viewgroup.confirm_item_count
				.setText(((HashMap<String, Object>) data.get(position))
						.get("count").toString());

		Double.valueOf((String) ((HashMap<String, Object>) data.get(position))
				.get("price"));
		Integer.valueOf(((HashMap<String, Object>) data.get(position))
				.get("count").toString());
		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {

		/**
		 * 商品名称 图片 颜色 尺码 价格 数量
		 */
		TextView confirm_item_name, confirm_item_color, confirm_item_size,
				confirm_item_price, confirm_item_count;
		ImageView confirm_item_img;

	}

}
