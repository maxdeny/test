package com.zhuolei.mobilesafe.components.dialog;

import com.zhuolei.mobilesafe.util.DisplayUtil;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyDialog extends Dialog {
    
    private static int default_width = 280; //Ĭ�Ͽ��
    private static int default_height = 180;//Ĭ�ϸ߶�
     
    public MyDialog(Context context, View layout, int style) {
        this(context, default_width, default_height, layout, style);
    }
     
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = DisplayUtil.dip2px(context, default_width);
        params.height = DisplayUtil.dip2px(context, default_height);
        window.setAttributes(params);
    }
     
}
