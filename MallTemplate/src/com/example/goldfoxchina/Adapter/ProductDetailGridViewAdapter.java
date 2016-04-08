package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 商品详情页面图片展示
 * 
 * @author kysl
 * 
 */
public class ProductDetailGridViewAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Bitmap> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public ProductDetailGridViewAdapter(Context context, ArrayList<Bitmap> data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {

		return data.size()-1;
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
					R.layout.productdetail_gridview_item, null);
			viewgroup.productdetail_gridview_item = (ImageView) convertView
					.findViewById(R.id.productdetail_gridview_item);

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
		if (position < data.size()-1) {
			viewgroup.productdetail_gridview_item.setImageBitmap(data
					.get(position + 1)); // 不取第一张图片      
		}
		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		ImageView productdetail_gridview_item;
	}

}
