package com.zhuolei.mabilesafe.adapter;

import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.zhuolei.mobilesafe.main.R;
import com.zhuolei.mobilesafe.model.AutoStartItem;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{
	
	private static final String TAG = "MyExpandableListAdapter";
	private LayoutInflater mChildInflater; //用于加载listitem的布局xml
	private LayoutInflater mGroupInflater; //用于加载group的布局xml
	private Context context;
	private List<String> groups;
	private List<List<AutoStartItem>> childs;
	
	public MyExpandableListAdapter(Context context,List<String> groups,List<List<AutoStartItem>> childs) {
		super();
		this.context = context;
		this.groups = groups;
		this.childs = childs;
		mChildInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.v(TAG, "groups size:" + String.valueOf(groups.size()));
		Log.v(TAG, "childs size:" + String.valueOf(childs.size()));
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		Log.v(TAG, "getGroupCount():");
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getChildrenCount():" + String.valueOf(childs.get(groupPosition).size()));
		return childs.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getGroup():" + String.valueOf(groupPosition));
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getchild():" + String.valueOf(childPosition));
		return childs.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getgroupid():" + String.valueOf(groupPosition));
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getchildid():" + String.valueOf(childPosition));
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		String lable = groups.get(groupPosition);
//		Log.v(TAG, "groupposition:" + String.valueOf(groupPosition));
//		return getGenericView(lable);
		
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mGroupInflater.inflate(R.layout.strongspeedup_groups,null);
			holder = new ViewHolder();
			holder.appName = (TextView) convertView.findViewById(R.id.group);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.appName.setText(groups.get(groupPosition));
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.v(TAG, "getchildview():" + String.valueOf(childs.size()));
		ViewHolder holder = null;
		if (convertView == null) {
			if(groupPosition == 0){
				convertView = mChildInflater.inflate(R.layout.child_common,null);
			}
			else if(groupPosition == 1){
				convertView = mChildInflater.inflate(R.layout.child_system,null);
			}
			else{
				convertView = mChildInflater.inflate(R.layout.child_whitelist,null);
			}
			holder = new ViewHolder();
			holder.appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
			holder.appName = (TextView) convertView.findViewById(R.id.app_name);
			holder.startStyle = (TextView) convertView.findViewById(R.id.app_startstyle);
			holder.toggleBtn = (ToggleButton) convertView.findViewById(R.id.toggle_button);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		AutoStartItem autoStartItem = childs.get(groupPosition).get(childPosition);
		if (autoStartItem != null) {
			holder.appIcon.setImageDrawable(autoStartItem.appIcon);
			holder.appName.setText(autoStartItem.appLable);
			holder.startStyle.setText(autoStartItem.autoStartStyle);
			if(autoStartItem.enableFlag == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || autoStartItem.enableFlag == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
				holder.toggleBtn.setBackgroundResource(R.drawable.switch_allow);
				Log.v(TAG, "ENABLED AND DEFAULT enableflag:"+String.valueOf(autoStartItem.enableFlag));
			}
			else if(autoStartItem.enableFlag == PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
				holder.toggleBtn.setBackgroundResource(R.drawable.switch_fobidden);
				Log.v(TAG, "ENABLED AND DEFAULT enableflag:"+String.valueOf(autoStartItem.enableFlag));
			}
			//holder.packageName = autoStartItem.appPN;
			Log.v(TAG, "appname:" + autoStartItem.appLable);
		}
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private TextView  getGenericView(String string ) 
    {
        AbsListView.LayoutParams  layoutParams =new AbsListView.LayoutParams(
             ViewGroup.LayoutParams.MATCH_PARENT,
             ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView  textView =new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER_VERTICAL |Gravity.LEFT);
        textView.setPadding(60, 10, 0, 10);
        textView.setTextSize(18);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setText(string);
        return textView;
     }

	class ViewHolder {
		ImageView appIcon;
		TextView appName;
		TextView startStyle;
		String packageName;
		ToggleButton toggleBtn;
	}
	 
}
