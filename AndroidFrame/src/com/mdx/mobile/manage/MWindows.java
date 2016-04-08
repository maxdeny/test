package com.mdx.mobile.manage;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;

public class MWindows {
	public static int width = 480, height = 800, index = 0;
	public static float density=1.5f;
	private static boolean ischeck = false;

	public static int getWidth(Context ctx) {
		if (!ischeck) {
			init(ctx);
		}
		return width;
	}

	public static void init(Context ctx) {
		if (ctx instanceof Activity) {
			Activity activ = (Activity) ctx;
			Display dis = activ.getWindowManager().getDefaultDisplay();
			DisplayMetrics dm = activ.getResources().getDisplayMetrics();
			density=dm.density;
			width = dis.getWidth();
			height = dis.getHeight();
			ischeck = true;
		}
	}

	public static int getHeight(Context ctx) {
		if (!ischeck) {
			init(ctx);
		}
		return height;
	}
}
