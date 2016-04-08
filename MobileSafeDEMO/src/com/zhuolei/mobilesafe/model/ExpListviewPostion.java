package com.zhuolei.mobilesafe.model;

public class ExpListviewPostion {
	private int mGroupPosition = 0; //父位置
	private int mChildPosition = 0; //子列表位置
	private static ExpListviewPostion mExpListviewPostion;
	
	public static ExpListviewPostion getInstance(){
		if(mExpListviewPostion == null){
			mExpListviewPostion = new ExpListviewPostion();
		}
		return mExpListviewPostion;
		
	}
	public int getGroupPosition() {
		return mGroupPosition;
	}
	public void setGroupPosition(int mGroupPosition) {
		this.mGroupPosition = mGroupPosition;
	}
	public int getChildPosition() {
		return mChildPosition;
	}
	public void setChildPosition(int mChildPosition) {
		this.mChildPosition = mChildPosition;
	}
}
