package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_child;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class SocendMenuAdapter extends  MAdapter<Msg_Ccomment_child> {
	int position = -1;
	
	public SocendMenuAdapter(Context context, List<Msg_Ccomment_child> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	
	public void setSelect(int s) {
		position = s;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Ccomment_child data=get(position);
		LayoutInflater f=LayoutInflater.from(getContext());
		View view=f.inflate(R.layout.secondmenu, null);
		MImageView img=(MImageView) view.findViewById(R.id.img);
		TextView text=(TextView) view.findViewById(R.id.text);
		text.setText(data.getCommentcontent());
		if (this.position == position) {
			view.setBackgroundResource(R.drawable.jiantou2);
		}
		return view;
	}

	
}
