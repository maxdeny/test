/*
 * 文件名: ToastShow.java 版 权： Copyright Huawei Tech. Co. Ltd. All Rights Reserved.
 * 描 述: [该类的简要描述] 创建人: ryan 创建时间:2013-11-18
 * 
 * 修改人： 修改时间: 修改内容：[修改内容]
 */
package com.mdx.mobile.mcommons;

import android.content.Context;

/**
 * [一句话功能简述]<BR>
 * [功能详细描述]
 * 
 * @author ryan
 * @version [2013-11-18 下午1:19:47]
 */
public class ToastShow {
    public static void Toast(Context context, String str) {
        android.widget.Toast.makeText(context, str,android.widget.Toast.LENGTH_LONG ).show();
    }
    
    public static void Toast(Context context, String str,int length) {
        android.widget.Toast.makeText(context, str,length ).show();
    }
}
