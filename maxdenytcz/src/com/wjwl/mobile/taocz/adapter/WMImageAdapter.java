package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.Map;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class WMImageAdapter extends BaseAdapter {
	private Context mContext;
	ArrayList<Map<String, Object>> mData;
	private LayoutInflater inflater;

	public WMImageAdapter(Context c, ArrayList<Map<String, Object>> mData) {
		mContext = c;
		this.mData = mData;
		this.inflater = LayoutInflater.from(c);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_wm_main, null);
			viewHolder.img = (ImageView) convertView
					.findViewById(R.item_wm_main.image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Bitmap bm = BitmapFactory.decodeFile((String) mData.get(position).get(
				"contents"));
		viewHolder.img.setImageBitmap(bm);
		//
		return convertView;
	}

	class ViewHolder {
		ImageView img;
	}

}