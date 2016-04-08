package com.wjwl.mobile.taocz.data;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterGroup {
	private String name;
	private boolean isChecked;
	private List<CategoryFilterChild> childList = new ArrayList<CategoryFilterChild>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public List<CategoryFilterChild> getChildList() {
		return childList;
	}
	public void setChildList(List<CategoryFilterChild> childList) {
		this.childList = childList;
	}
	
}
