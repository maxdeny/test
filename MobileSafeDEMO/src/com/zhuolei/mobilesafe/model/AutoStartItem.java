package com.zhuolei.mobilesafe.model;

import android.graphics.drawable.Drawable;

public class AutoStartItem {
	
	public Drawable appIcon;
	public String appLable;
	public String appPN;//包名用来判断是否同一个应用
	public String autoStartStyle;//启动方式： 开机启动 ；后台启动；开机启动+后台启动
	public int enableFlag;//广播enable标志   
}
