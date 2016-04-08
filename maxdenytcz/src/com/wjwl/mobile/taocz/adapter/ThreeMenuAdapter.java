package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment_grandchild;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ThreeMenuAdapter extends MAdapter<Msg_Ccomment_grandchild> {
	int position = -1;

	public ThreeMenuAdapter(Context context, List<Msg_Ccomment_grandchild> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	public void setSelect(int s) {
		position = s;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Msg_Ccomment_grandchild data = get(position);
		LayoutInflater f = LayoutInflater.from(getContext());
		View view = f.inflate(R.layout.threemenu, null);
		MImageView img = (MImageView) view.findViewById(R.id.img);
		TextView text = (TextView) view.findViewById(R.id.text);
		// img.setObj(data.getCommentpeople());
		text.setText(data.getCommentcontent());
		if (this.position == position) {
			text.setTextColor(0xffffffff);// shenhui
		} else
			text.setTextColor(0xff333333);
		return view;
	}

}
