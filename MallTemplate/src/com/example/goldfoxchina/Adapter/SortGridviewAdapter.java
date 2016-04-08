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

/**
 * 商品分类 gridview适配
 * 
 * @author kysl
 * 
 */

public class SortGridviewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public SortGridviewAdapter(Context context,
			ArrayList<HashMap<String, Object>> data) {

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
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position,  View convertView, ViewGroup parent) {
		viewGroup viewgroup;

		if (convertView == null) {
			viewgroup = new viewGroup();
			convertView = mInflater.inflate(R.layout.sort_gridview_item, null);
			viewgroup.sort_gridview_item_title = (TextView) convertView
					.findViewById(R.id.sort_gridview_item_title);
			viewgroup.sort_gridview_item_content = (TextView) convertView
					.findViewById(R.id.sort_gridview_item_content);
			viewgroup.sort_gridview_item_img=(ImageView) convertView.findViewById(R.id.sort_gridview_item_img);
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
		viewgroup.sort_gridview_item_title.setText((String)data.get(position).get("name"));
		viewgroup.sort_gridview_item_content.setText((String)data.get(position).get("description"));
		viewgroup.sort_gridview_item_img.setImageBitmap((Bitmap)data.get(position).get("icon"));
		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView sort_gridview_item_title, sort_gridview_item_content;
		ImageView sort_gridview_item_img;

	}

}
