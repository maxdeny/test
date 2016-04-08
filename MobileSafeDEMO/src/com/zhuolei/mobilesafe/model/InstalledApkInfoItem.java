package com.zhuolei.mobilesafe.model;

import android.graphics.drawable.Drawable;

public class InstalledApkInfoItem {
	
	public Drawable appIcon;
	public String appLable;
	public String appPN;//包名用来判断是否同一个应用
	public String version; //版本  普通应用专用
	public String reminder;//提示  系统应用专用   谨慎卸载--核心应用
	public String safeVersion; //是否是正版   0 -- 1 -- 2 -- 正版  - 安全 - 不安全
	public long softSize; //软件大小
	
}
