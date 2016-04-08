//package com.wjwl.mobile.taocz.adapter;
//
//import java.io.File;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.mdx.mobile.adapter.MAdapter;
//import com.wjwl.mobile.taocz.R;
//
//public class WMPicAdapter extends MAdapter<Map<String, Object>> {
//	private LayoutInflater inflater;
//
//	public WMPicAdapter(Context c, List<Map<String, Object>> mData) {
//		super(c, mData);
//		this.inflater = LayoutInflater.from(c);
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		Map<String, Object> posobj=get(position);
//		ViewHolder viewHolder;
//		if (convertView == null) {
//			viewHolder = new ViewHolder();
//			convertView = inflater.inflate(R.layout.wmgridviewitem, null);
//			viewHolder.img = (ImageView) convertView.findViewById(R.cityrecruit.wmpic_item);
//			convertView.setTag(viewHolder);
//		} else {
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
////		Bitmap bm = BitmapFactory.decodeFile((String) mData.get(position).get(
////				"imageItem"));
////      setImageBitmap(bm);
//		if((posobj.get("contents")).toString().indexOf("_")!=-1){
//			viewHolder.img.setBackgroundDrawable(Drawable.createFromPath(new File((String)posobj.get("contents")).getAbsolutePath()));
//		}
//		else{
//			viewHolder.img.setBackgroundResource((Integer) posobj.get("contents"));
//		}
//		
//		return convertView;
//	}
//
//	class ViewHolder {
//		ImageView img;
//	}
//}