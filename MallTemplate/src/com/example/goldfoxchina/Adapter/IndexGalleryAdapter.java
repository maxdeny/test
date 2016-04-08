package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Bean.DetialGallery;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 首页gallery适配
 * @author kysl
 *
 */

public class IndexGalleryAdapter extends BaseAdapter {
	
	private Context context;
	private ArrayList<HashMap<String, Object>> data;
	
	
	public IndexGalleryAdapter(Context context,ArrayList<HashMap<String, Object>> data) {
		this.context=context;
		this.data=data;
		
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
		 ImageView iv = new ImageView(context);

         iv.setImageBitmap((Bitmap) data.get(position).get("image"));
         iv.setScaleType(ImageView.ScaleType.FIT_XY);
         iv.setLayoutParams(new DetialGallery.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		return iv;
	}

}
