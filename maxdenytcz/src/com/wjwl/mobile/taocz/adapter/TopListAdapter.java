package com.wjwl.mobile.taocz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.data.ButtonLayoutHolder;
import com.wjwl.mobile.taocz.data.ButtonView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TopListAdapter  extends BaseAdapter {
	Context content;
	ArrayList<ButtonView> arrayList = null;
	LayoutInflater inflater;
	View view;
	ButtonLayoutHolder buttonLayoutHolder;
	LinearLayout buttonLayout = null;
	TextView buttonText = null;
	TextView button,number;
	String mark;

	private int selectedPosition = -1;// 选中的位置

	public TopListAdapter(Context c,ArrayList<ButtonView> buttonListView,String mark) {
		// TODO Auto-generated constructor stub
		content=c;
		arrayList = buttonListView;
		this.mark=mark;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater = (LayoutInflater) content.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.top_select_item, null, false);
		buttonLayout=(LinearLayout) view.findViewById(R.topselect.linearlayout);
		buttonText=(TextView) view.findViewById(R.topselect.textview);
		button=(TextView) view.findViewById(R.topselect.number);
//		number=view.findViewById(R.topselect)
		if(mark.equals("1"))
			button.setVisibility(View.VISIBLE);
		else 
			button.setVisibility(View.GONE);
//		if(position==0){
//			button.setVisibility(View.GONE);
//		}
		
		if (selectedPosition == position) {
			buttonText.setSelected(true);
			buttonText.setPressed(true);
//			buttonLayout.setBackgroundDrawable(content.getResources().getDrawable(R.color.allbg));//R.drawable.spinner_bg
			buttonLayout.setBackgroundResource(R.drawable.v3_list2_bg);//R.drawable.spinner_bg
			button.setSelected(true);
		} else {
			buttonText.setSelected(false);
			buttonText.setPressed(false);
			buttonLayout.setBackgroundColor(Color.TRANSPARENT);  
			button.setSelected(false);
		}
		button.setText(arrayList.get(position).number);
		buttonText.setText(arrayList.get(position).cname);
		return view;
	}
}
