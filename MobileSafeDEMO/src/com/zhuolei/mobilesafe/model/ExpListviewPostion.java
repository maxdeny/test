package com.zhuolei.mobilesafe.model;

public class ExpListviewPostion {
	private int mGroupPosition = 0; //��λ��
	private int mChildPosition = 0; //���б�λ��
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
