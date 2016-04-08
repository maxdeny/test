package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MListAdapter extends MAdapter<Msg_Billitem> {

	Msg_Billitem billitem;
	public MListAdapter(Context context, List<Msg_Billitem> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		billitem=getItem(position);
		LayoutInflater f=LayoutInflater.from(getContext());
		View view=f.inflate(R.layout.mlistview_item, null);
		TextView text1=(TextView) view.findViewById(R.id.text1);
		TextView text2=(TextView) view.findViewById(R.id.text2);
		text1.setText(billitem.getBillitemid());
		text2.setText(billitem.getBilliteminfo());
		return view;
	}

}
