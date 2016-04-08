package com.wjwl.mobile.taocz.untils;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class WindowSizeUtil {
	public static Map<String, Integer> getSize(Context context){
		DisplayMetrics metric = new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        Map<String, Integer> contentMap = new HashMap<String, Integer>();
        contentMap.put("width", width);
        contentMap.put("height", height);
        return contentMap;
	}
}
