package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyCategoryGridViewAdapter extends BaseAdapter {
	
	private LayoutInflater inflater ;
	private List<Msg_Citem> mDataSource;
	private LayoutParams lp;
	private Object size;
	

	public MyCategoryGridViewAdapter(List<Msg_Citem> mDataSource , Context context) {
		super();
		this.mDataSource = mDataSource;
		inflater = LayoutInflater.from(context);
		lp = new LayoutParams((F.getCurrnetWindowWidth(context)-13)/4
				, ((F.getCurrnetWindowWidth(context)-13)/4-1)/2);
		size =  "."
				+ (F.getCurrnetWindowWidth(context)-13)/4 
				+ "x"
				+ ((F.getCurrnetWindowWidth(context)-13)/4-1)/2
				+ ".jpg";
	}

	@Override
	public int getCount() {
		if(null == mDataSource){
			mDataSource = new ArrayList<Msg_Citem>();
		}
		return mDataSource.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		if(position < mDataSource.size()){
			Msg_Citem item = mDataSource.get(position);
			ViewHolder holder ;
			if(null == convertView){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.home_page_category_layout, null);
				holder.category_item = (TextView) convertView.findViewById(R.id.category_item_name);
				holder.imageView = (MImageView) convertView.findViewById(R.id.category_item_img);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.category_item.setText(item.getItemtitle());
			holder.imageView.setObj(item.getOther1() + size);
			holder.imageView.setLayoutParams(lp);
		}else{
			convertView = inflater.inflate(R.layout.home_page_category_layout, null);
		}
		return convertView;
	}
	
	class ViewHolder {
		TextView category_item;
		MImageView imageView ;
	}
}
