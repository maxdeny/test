package com.example.musicdialog;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyDialog extends Dialog {
    
    private static int default_width = 280; //默认宽度
    private static int default_height = 180;//默认高度
     
    public MyDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }
     
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = dip2px(context, width);
        params.height = dip2px(context, height);
        window.setAttributes(params);
    }
    
	public int dip2px(Context context, float dpValue) {

		if (context == null) {
			return (int) dpValue;
		}
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
    
	
}
