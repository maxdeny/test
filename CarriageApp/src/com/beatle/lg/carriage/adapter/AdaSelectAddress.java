package com.beatle.lg.carriage.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.beatle.lg.carriage.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mdx.mobile.adapter.MAdapter;

public class AdaSelectAddress extends MAdapter<PoiInfo> {

	private List<PoiInfo> list = new ArrayList<PoiInfo>();

	private Context context;

	private LayoutInflater mInflater;

	public AdaSelectAddress(Context context, List<PoiInfo> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_select_address_header, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_map_address.setText(getItem(position).name);
		holder.tv_map_address_detail.setText(getItem(position).address);
		
		return convertView;
	}

	public class ViewHolder {

		@ViewInject(R.id.tv_map_address)
		public TextView tv_map_address;
		
		@ViewInject(R.id.tv_map_address_detail)
		public TextView tv_map_address_detail;

		public ViewHolder(View contentView) {
			ViewUtils.inject(this, contentView);
		}
	}

}
