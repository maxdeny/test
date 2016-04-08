package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.goldfoxMall.R;

public class CartListViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public CartListViewAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
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
			convertView = mInflater.inflate(R.layout.cart_listview_item,
					null);
			viewgroup.cart_seller_title = (TextView) convertView
					.findViewById(R.id.cart_seller_title);
			viewgroup.cart_seller_content = (TextView) convertView
					.findViewById(R.id.cart_seller_content);
			viewgroup.cart_seller_head=(ImageView) convertView.findViewById(R.id.cart_seller_head);

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
		viewgroup.cart_seller_title.setText((String)data.get(position).get("name"));
		viewgroup.cart_seller_content.setText((String)data.get(position).get("description"));
		viewgroup.cart_seller_head.setImageBitmap((Bitmap)data.get(position).get("logo"));
		

		return convertView;
	}
	
	
	/**
	 * 控件存放
	 *
	 * @author kysl
	 *
	 */
	public final class viewGroup {
		TextView  cart_seller_title,cart_seller_content;
		ImageView cart_seller_head;
	}

}
