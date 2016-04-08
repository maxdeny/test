/**
 * Project Name:360yata File Name:DialogManager.java Package
 * Name:com.xcds.mobile.yata.util Date:2014-10-17上午11:04:17 Copyright (c) 2014,
 * chenzhou1025@126.com All Rights Reserved.
 * 
 */

package com.my.spyware.bean;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * ClassName:DialogManager <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2014-10-17 上午11:04:17 <br/>
 * 
 * @author Administrator
 * @version
 * @since JDK 1.6
 * @see
 */
public class DialogManager {
    
    public interface OnMDialogListener {
        void onSureClick(DialogInterface dialog, int which);
        
        void onCancleClick(DialogInterface dialog, int which);
    }
    
    public static Dialog getPayChosenDialog(Context context, final OnMDialogListener dialogListener) {
        Builder builder = new android.app.AlertDialog.Builder(context);
        // 设置对话框的图标
        // builder.setIcon(R.drawable.header);
        // 设置对话框的标题
        builder.setTitle("请选择付款的方式");
        // 0: 默认第一个单选按钮被选中
        builder.setItems(new String[] { "支付宝支付"}, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // String
                // hoddy=getResources().getStringArray(R.array.hobby)[which];
                // editText.setText("您选择了： "+hoddy);
                if (dialogListener != null) {
                    dialogListener.onSureClick(dialog, which);
                }
                dialog.dismiss();
            }
        });
        
        // 添加一个确定按钮
        builder.setPositiveButton("取    消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onCancleClick(dialog, which);
                }
                dialog.dismiss();
            }
        });
        // 创建一个单选按钮对话框
        return builder.create();
    }
    
    public static Dialog getAlertDeleteDialog(Context context, final OnMDialogListener dialogListener) {
        Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("警告");
        builder.setMessage("您确定要删除这项吗？");
        builder.setNegativeButton("取消", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onCancleClick(dialog, which);
                }
                dialog.dismiss();
            }
            
        });
        builder.setPositiveButton("确定", new OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null) {
                    dialogListener.onSureClick(dialog, which);
                }
                
                dialog.dismiss();
            }
            
        });
        return builder.create();
    }
    
}
