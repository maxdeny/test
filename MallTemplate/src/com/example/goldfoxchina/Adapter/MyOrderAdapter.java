package com.example.goldfoxchina.Adapter;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyOrderAdapter extends BaseAdapter {
	private Context context;
	private String[] data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	private String status;

	public MyOrderAdapter(Context context, String[] data,String status) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);
		this.status=status;
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
					R.layout.fragment_myorder_item, null);
			viewgroup.myorder_status = (TextView) convertView
					.findViewById(R.id.myorder_status);
			viewgroup.myorder_shop_img = (TextView) convertView
					.findViewById(R.id.myorder_shop_img);
			viewgroup.myorder_shop_name = (TextView) convertView
					.findViewById(R.id.myorder_shop_name);
			viewgroup.myorder_shop_color = (TextView) convertView
					.findViewById(R.id.myorder_shop_color);
			viewgroup.myorder_shop_size = (TextView) convertView
					.findViewById(R.id.myorder_shop_size);
			viewgroup.myorder_shop_price = (TextView) convertView
					.findViewById(R.id.myorder_shop_price);
			viewgroup.myorder_shop_scount = (TextView) convertView
					.findViewById(R.id.myorder_shop_scount);
			viewgroup.myorder_totalprice = (TextView) convertView
					.findViewById(R.id.myorder_totalprice);
			

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
		viewgroup.myorder_shop_name.setText(data[position]);
		viewgroup.myorder_status.setText(status);

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView myorder_status,myorder_shop_img,myorder_shop_name,myorder_shop_color,myorder_shop_size,myorder_shop_price,myorder_shop_scount,myorder_totalprice;
	}

}
