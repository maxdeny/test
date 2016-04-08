package com.zhuolei.mobilesafe.util;

import android.content.Context;

public class DisplayUtil {
	private static int[] screen_size = { 0, 0 };
	
	public static void setScreenSize(int w, int h) {
		screen_size[0] = Math.min(w, h);
		screen_size[1] = Math.max(w, h);
	}

	public static int[] getScreenSize() {
		return screen_size;
	}

	public static int dip2px(Context context, float dpValue) {

		if (context == null) {
			return (int) dpValue;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
