package com.example.goldfoxchina.Adapter;

import java.util.ArrayList;

import com.example.goldfoxchina.Bean.DetialGallery;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PhotoGalleryAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Bitmap> bitmap;
	
	//选中的图片id
	private int id;
	
	
	
	public PhotoGalleryAdapter(Context context,ArrayList<Bitmap> bitmap) {
		this.context=context;
		this.bitmap=bitmap;
//		this.id=id;
	}

	@Override
	public int getCount() {	
		return bitmap.size();
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

         iv.setImageBitmap(bitmap.get(getId()));    
         iv.setScaleType(ImageView.ScaleType.FIT_XY);
         iv.setLayoutParams(new DetialGallery.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		return iv;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	
	
}
