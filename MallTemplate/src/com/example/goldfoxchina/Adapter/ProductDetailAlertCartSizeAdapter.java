package com.example.goldfoxchina.Adapter;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductDetailAlertCartSizeAdapter extends BaseAdapter {

	private Context context;
	private String[] data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
	private int select_item_size;

	public ProductDetailAlertCartSizeAdapter(Context context, String[] data) {
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
					R.layout.product_detail_alert_gridview_item, null);
			viewgroup.alert_cart_gridview_item = (TextView) convertView
					.findViewById(R.id.alert_cart_gridview_item);

			/**
			 * 使用tag来存储数据
			 */
			convertView.setTag(viewgroup);

		} else {
			viewgroup = (viewGroup) convertView.getTag();

		}

//		this.select_item_size = ProductDetail_Alert_Cart.select_item_size;

		if (this.select_item_size == position) {

			viewgroup.alert_cart_gridview_item
					.setBackgroundResource(R.drawable.shop_cm_s);
		} else {
			viewgroup.alert_cart_gridview_item
					.setBackgroundResource(R.drawable.shop_cm_n);
		}

		/**
		 * 获取数据显示
		 */
		viewgroup.alert_cart_gridview_item.setText(data[position]);

		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView alert_cart_gridview_item;
	}

}
