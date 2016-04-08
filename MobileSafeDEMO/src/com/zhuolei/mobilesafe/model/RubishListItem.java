package com.zhuolei.mobilesafe.model;

import android.graphics.drawable.Drawable;

public class RubishListItem {
	
	public String rubPackageName,rubApplicationName;
	public long rubAdvice,rubAll;
	public Drawable rubIcon;
	
	public RubishListItem(String rubPackageName,
			String rubApplicationName,long rubAdvice,long rubAll,Drawable rubIcon) {
		this.rubPackageName = rubPackageName;
		this.rubApplicationName = rubApplicationName;
		this.rubAdvice = rubAdvice;
		this.rubAll = rubAll;
		this.rubIcon = rubIcon;

	}
	
}
