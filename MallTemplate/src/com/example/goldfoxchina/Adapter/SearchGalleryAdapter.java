package com.example.goldfoxchina.Adapter;

import com.example.goldfoxchina.Bean.DetialGallery;
import com.example.goldfoxMall.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SearchGalleryAdapter extends BaseAdapter {

	private Context context;
	private Integer[] mImageIds={R.drawable.default_01,R.drawable.default_01,R.drawable.default_01,R.drawable.default_01};
	
	
	
	public SearchGalleryAdapter(Context context) {
		this.context=context;
		
	}

	@Override
	public int getCount() {	
		return mImageIds.length;
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
		 ImageView iv = new ImageView(context);

         iv.setImageResource(mImageIds[position]);
         iv.setScaleType(ImageView.ScaleType.FIT_XY);
         iv.setLayoutParams(new DetialGallery.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		return iv;
	}

}
