package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.HotelCity.Msg_HotelCity;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CitySearchAdapter extends MAdapter<Msg_HotelCity> {
	private LayoutInflater layoutInflater;
	Context context;

	public CitySearchAdapter(Context context, List<Msg_HotelCity> cityList) {
		super(context,cityList);
		layoutInflater = LayoutInflater.from(context);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Msg_HotelCity hotelCity = (Msg_HotelCity)get(position);
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.item_list, null);
			holder = new ViewHolder();
			holder.firstCharHintTextView = (TextView) convertView
					.findViewById(R.id.text_first_char_hint);
			holder.firstCharHintLine = (ImageView) convertView
					.findViewById(R.id.text_first_char_line);
			holder.nameTextView = (TextView) convertView
					.findViewById(R.id.text_website_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameTextView.setText(hotelCity.getHotelCityName());
		if(position==0){
			holder.firstCharHintLine.setVisibility(View.GONE);
		}
		return convertView;
	}

	public final class ViewHolder {
		public TextView firstCharHintTextView;
		public TextView nameTextView;
		public ImageView firstCharHintLine;
	}
}