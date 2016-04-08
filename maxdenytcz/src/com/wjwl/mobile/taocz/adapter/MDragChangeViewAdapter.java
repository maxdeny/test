package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.untils.JumpUtils;

public class MDragChangeViewAdapter extends BaseAdapter {
	
	private List<Msg_Citem> mDataSource;
	private LayoutInflater inflater;
	Context mcontext;
	
	public MDragChangeViewAdapter(List<Msg_Citem> mDataSource, Context context) {
		super();
		mcontext=context;
		this.mDataSource = mDataSource;
		inflater = LayoutInflater.from(context);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		final Msg_Citem msg_citem = mDataSource.get(position);
		ViewHolder holder;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.home_page_drag_view_item, null);
			holder.imageView = (MImageView) convertView.findViewById(R.id.image_View);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.imageView.setObj(msg_citem.getOther1()
				+ "."
				+ F.getCurrnetWindowWidth(mcontext)/1 
				+ "x"
				+ F.getCurrnetWindowWidth(mcontext)/3
				+ ".jpg");
		LayoutParams lp = new LayoutParams(F.getCurrnetWindowWidth(mcontext), F.getCurrnetWindowWidth(mcontext)/3);
		holder.imageView.setLayoutParams(lp);
		holder.imageView.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				JumpUtils.jump(mcontext, "normal", msg_citem.getItemid(), msg_citem.getItemtitle(), msg_citem.getItemtype());
			}
		});
		return convertView;
	}
	class ViewHolder {
		MImageView  imageView;
	}
}
