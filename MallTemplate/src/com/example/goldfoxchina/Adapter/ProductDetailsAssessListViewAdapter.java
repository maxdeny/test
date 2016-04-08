package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 商品评价 listview adapter
 * 
 * @author kysl
 * 
 */
public class ProductDetailsAssessListViewAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

	public ProductDetailsAssessListViewAdapter(Context context, ArrayList<HashMap<String, Object>> data) {
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
			convertView = mInflater
					.inflate(R.layout.assess_listview_item, null);
			viewgroup.buyers_head = (TextView) convertView
					.findViewById(R.id.buyers_head);
			viewgroup.buyers_name = (TextView) convertView
					.findViewById(R.id.buyers_name);
			viewgroup.buyers_content = (TextView) convertView
					.findViewById(R.id.buyers_content);
			viewgroup.buyers_time = (TextView) convertView
					.findViewById(R.id.buyers_time);
			
			viewgroup.rb_ratingbar=(RatingBar) convertView.findViewById(R.id.rb_ratingbar);
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
		viewgroup.buyers_name.setText((String)data.get(position).get("sellerName"));
		viewgroup.buyers_content.setText((String)data.get(position).get("content"));
		viewgroup.buyers_time.setText((String)data.get(position).get("createTime"));
		viewgroup.rb_ratingbar.setRating(Float.valueOf((String)data.get(position).get("score")));
		
		return convertView;
	}

	/**
	 * 控件存放
	 * 
	 * @author kysl
	 * 
	 */
	public final class viewGroup {
		TextView buyers_head, buyers_name, buyers_content, buyers_time;
		RatingBar rb_ratingbar;
		
	}

}
