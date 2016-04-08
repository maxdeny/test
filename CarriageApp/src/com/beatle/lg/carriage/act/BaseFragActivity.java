package com.beatle.lg.carriage.act;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mdx.mobile.activity.MFragmentActivity;
import com.mdx.mobile.commons.MException;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @Title: BaseFragActivity 
 * @ToDo: 便于友盟统计 Activity统一添加友盟统计
 * @author daixiu
 * @version v 1.0
 * @date [2015-8-18上午9:45:57]
 */
public abstract class BaseFragActivity extends MFragmentActivity {
    
    @Override
    protected void initcreate(Bundle arg0) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //        showKitKat();
        //        setStatusBarDarkMode(true, this);
    }
    
    @Override
    protected void create(Bundle arg0) {
        
    }
    
    @Override
    protected void resume() {
        MobclickAgent.onPageStart(getClass().getName()); // 子页面 统计具体页面
        MobclickAgent.onResume(this); // 统计时长
        super.resume();
    }
    
    @Override
    protected void pause() {
        // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息
        MobclickAgent.onPageEnd(getClass().getName());
        MobclickAgent.onPause(this);
        super.pause();
    }
    
    @Override
    public void showError(MException me) {
        if (me.getCode() == 98) {
            showToast("连接错误,请检查网络!");
        }
        else {
            super.showError(me);
        }
    }
    
    @TargetApi(19)
    private void showKitKat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // Translucent navigation bar
            //window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            //		WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // isSdkHeigthThanKitkat = true;
        }
    }
    
    /**
     * 
     * miuiv6只支持4.4及以上版本，调用状态栏透明的方法可以直接用原生的安卓方法
     * 调用状态栏 是否为darkmode
     * 沉浸式菜单栏只能在MIUI 6的系统上实现，其他安卓系统没有效果。
     * 沉浸式效果对非MIUI系统的兼容性不会有任何影响。
     * google的actionbar存在bug，不支持沉浸代码。
     * @author Administrator
     * @param darkmode
     * @param activity 
     * @return void 
     * @throws
     */
    public void setStatusBarDarkMode(boolean darkmode, Activity activity) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 1，跳转Activity
    protected <T> void goActivity(Class<T> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    
    protected <T> void goActivity(Class<T> cls, String TAG) {
        Intent intent = new Intent(this, cls);
        intent.putExtra("TAG", TAG);
        startActivity(intent);
    }
    
    // 2,秀Toast
    protected void showToast(String show) {
        Toast.makeText(this, show, Toast.LENGTH_SHORT).show();
    }
    
    // 3,关闭Actvity
    protected void closeAct() {
        finish();
    }
}
