package com.example.goldfoxchina.Adapter;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchItemShopListViewAdapter extends BaseAdapter {
	
	private Context context;
	private String[] data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	
	public SearchItemShopListViewAdapter(Context context, String[] data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		
		return data.length;
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
					R.layout.fragment_search_shop_item, null);
			viewgroup.fragment_shop_img = (ImageView) convertView
					.findViewById(R.id.fragment_shop_img);
			viewgroup.fragment_shop_name = (TextView) convertView
					.findViewById(R.id.fragment_shop_name);
			viewgroup.shop_present = (TextView) convertView
					.findViewById(R.id.shop_present);
			

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
		viewgroup.fragment_shop_name.setText(data[position]);

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView fragment_shop_name,shop_present;
		ImageView fragment_shop_img;
	}

}
