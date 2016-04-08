package com.example.goldfoxchina.Bean;


import com.example.goldfoxMall.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 自定义dialog
 * context
 * msg  显示信息
 * @author kysl
 *
 */

public class CustomDialog {
	private Context context;
	private String msg;
	public CustomDialog(Context context, String msg) {
		this.context=context;
		this.msg=msg;
	}
	
    public  Dialog createLoadingDialog() {  
  
        LayoutInflater inflater = LayoutInflater.from(context);  
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view  
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局  
        // main.xml中的ImageView  
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);  
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字  
        // 加载动画  
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(  
                context, R.anim.loading_animation);  
        // 使用ImageView显示动画  
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);  
        tipTextView.setText(msg);// 设置加载信息  
  
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog  
  
        loadingDialog.setCancelable(false);// 不可以用“返回键”取消  
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.WRAP_CONTENT,  
                LinearLayout.LayoutParams.WRAP_CONTENT));// 设置布局  
        return loadingDialog;  
  
    }  
}
