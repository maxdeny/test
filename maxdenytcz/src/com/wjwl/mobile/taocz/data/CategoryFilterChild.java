package com.wjwl.mobile.taocz.data;

import java.util.Observable;
import java.util.Observer;

public class CategoryFilterChild extends Observable implements Observer{
	private String itemName;
	private String itemId;
	private boolean isChecked;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
	public void changeChecked(){
		isChecked = !isChecked;
		setChanged();
		notifyObservers();
	}
	
	@Override
	public void update(Observable observable, Object data) {
		if (data instanceof Boolean) {
			this.isChecked = (Boolean) data;
		}
	}
	
}
