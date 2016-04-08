package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用适配器类
 * 
 * 包括搜索的宝贝下面的默认 最热 最新
 * 
 * 店铺下面商品展示
 * 
 * @author kysl
 * 
 */

public class InterchangeableAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public InterchangeableAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
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
			convertView = mInflater.inflate(
					R.layout.interchangeable_gridview_item, null);
			viewgroup.treasure_gridview_item_img = (ImageView) convertView
					.findViewById(R.id.treasure_gridview_item_img);
			viewgroup.tressure_gridview_item_content = (TextView) convertView
					.findViewById(R.id.tressure_gridview_item_content);
			viewgroup.treasure_gridview_item_now_price = (TextView) convertView
					.findViewById(R.id.treasure_gridview_item_now_price);
			viewgroup.treasure_gridview_item_pre_price = (TextView) convertView
					.findViewById(R.id.treasure_gridview_item_pre_price);

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
		viewgroup.treasure_gridview_item_img.setImageBitmap((Bitmap) data.get(position).get("icons"));
		viewgroup.tressure_gridview_item_content.setText((String)data.get(position).get("name"));
		viewgroup.treasure_gridview_item_pre_price.setText((String)data.get(position).get("bidPrice"));
		viewgroup.treasure_gridview_item_now_price.setText((String)data.get(position).get("sellingPrice"));

		/**
		 * 添加删除线
		 */

		if (viewgroup.treasure_gridview_item_pre_price.getText() != null
				|| viewgroup.treasure_gridview_item_pre_price.getText() != "") {
			viewgroup.treasure_gridview_item_pre_price.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG);
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
		TextView  tressure_gridview_item_content,
				treasure_gridview_item_now_price,
				treasure_gridview_item_pre_price;
		
		ImageView treasure_gridview_item_img;
	}

}
