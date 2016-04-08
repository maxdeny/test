package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.data.CategoryFilterChild;
import com.wjwl.mobile.taocz.data.CategoryFilterGroup;

public class CategoryFilterAdapter extends BaseExpandableListAdapter {
	private Context ctx;
	private List<CategoryFilterGroup> groupList;
	LayoutInflater inflater;
	
	public CategoryFilterAdapter(Context ctx, List<CategoryFilterGroup> groupList){
		this.ctx = ctx;
		this.groupList = groupList;
		inflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getGroupCount() {
		return groupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groupList.get(groupPosition).getChildList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groupList.get(groupPosition).getChildList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final CategoryFilterGroup group = (CategoryFilterGroup) getGroup(groupPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.filter_group_item, null);
		}
		TextView groupText = (TextView) convertView.findViewById(R.id.groupText);
		CheckBox groupCheckBox = (CheckBox) convertView.findViewById(R.id.groupCheckBox);
		groupText.setText(group.getName());
		groupCheckBox.setChecked(group.isChecked());
		groupCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				group.changeChecked();
//				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CategoryFilterChild child = (CategoryFilterChild) getChild(groupPosition, childPosition);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.filter_child_item, null);
		}
		TextView childText = (TextView) convertView.findViewById(R.id.childText);
		CheckBox childCheckBox = (CheckBox) convertView.findViewById(R.id.childCheckBox);
		childText.setText(child.getItemName());
		childCheckBox.setChecked(child.isChecked());
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
}
