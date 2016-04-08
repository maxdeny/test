package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyGridViewAdapter extends BaseAdapter {
	
	private LayoutInflater inflater ;
	private List<Msg_Citem> mDataSource;
	private LayoutParams lp;
	private String size;
	

	public MyGridViewAdapter(List<Msg_Citem> list, Context context) {
		super();
		this.inflater = LayoutInflater.from(context);
		this.mDataSource  = list;
		lp = new LayoutParams((F.getCurrnetWindowWidth(context)-11)/2
				, ((F.getCurrnetWindowWidth(context)-11)/2-1)/2);
		size =  "."
				+ (F.getCurrnetWindowWidth(context)-11)/2
				+ "x"
				+ ((F.getCurrnetWindowWidth(context)-11)/2-1)/2 
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
		Msg_Citem item = mDataSource.get(position);
		ViewHolder holder ;
		if(null == convertView){
			convertView = inflater.inflate(R.layout.home_page_recommend_item, null);
			holder = new ViewHolder();
			holder.imageView = (MImageView) convertView.findViewById(R.id.miv_item_pic);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imageView.setObj(item.getOther1() + size);
		holder.imageView.setLayoutParams(lp);
		return convertView;
	}
	class ViewHolder{
		MImageView imageView;
	}
}
