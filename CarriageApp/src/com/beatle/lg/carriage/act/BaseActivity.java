package com.beatle.lg.carriage.act;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.beatle.lg.carriage.dialog.MyProgressDialog;
import com.mdx.mobile.activity.MActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * 便于友盟统计
 * Activity统一添加友盟统计
 * @author ljl
 * @version [2014-8-14 上午8:55:42] 
 */
public abstract class BaseActivity extends MActivity {
    
    @Override
    protected void initcreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.initcreate(savedInstanceState);
    }
    
	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"加载中···");
	}
    
    @Override
    protected void pause() {
        // 保证 onPageEnd 在onPause 之前调用,因为 onPause 中会保存信息 
        MobclickAgent.onPageEnd(getClass().getName());
        MobclickAgent.onPause(this);
        super.pause();
    }
    
    @Override
    protected void resume() {
        MobclickAgent.onPageStart(getClass().getName()); //子页面 统计具体页面
        MobclickAgent.onResume(this); //统计时长
        super.resume();
    }
    
    /**
     * 如果需要保存额外的数据时
     * 
     * 除了系统处于内存不足的原因会摧毁activity之外, 某些系统设置的改变也会导致activity的摧毁和重建. 
     * 例如改变屏幕方向(见上例), 改变设备语言设定, 键盘弹出等
     * 
     * 调用将发生在onPause()或onStop()方法之前
     * 1.由于onSaveInstanceState()方法方法不一定会被调用, 因此不适合在该方法中保存持久化数据;
     * onSaveInstanceState()方法只适合保存瞬态数据, 比如UI控件的状态, 成员变量的值等
     * @param outState
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try {
            super.onSaveInstanceState(outState);
            //        outState.putBoolean("MyBoolean", true);
            //        outState.putDouble("myDouble", 1.9);
            //        outState.putInt("MyInt", 1);
            //        outState.putString("MyString", "Welcome back to Android");
            saveInstanceState(outState);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
    /**
     * 如果需要保存额外的数据时
     * 
     * 除了系统处于内存不足的原因会摧毁activity之外, 某些系统设置的改变也会导致activity的摧毁和重建. 
     * 例如改变屏幕方向(见上例), 改变设备语言设定, 键盘弹出等
     * 
     * 调用将发生在onPause()或onStop()方法之前
     * 1.由于onSaveInstanceState()方法方法不一定会被调用, 因此不适合在该方法中保存持久化数据;
     * onSaveInstanceState()方法只适合保存瞬态数据, 比如UI控件的状态, 成员变量的值等
     * @param outState
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    protected abstract void saveInstanceState(Bundle outState);
    
    /**
     * 获取保存额外保存的数据时
     * 
     * onRestoreInstanceState()在onStart() 和 onPostCreate(Bundle)之间调用
     *  1.由于onSaveInstanceState()方法onRestoreInstanceState方法不一定成对出现
     *  2.
     * @param savedInstanceState
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(savedInstanceState);
            restoreInstanceState(savedInstanceState);
        }
        catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
    /**
     * 获取保存额外保存的数据时
     * 
     * onRestoreInstanceState()在onStart() 和 onPostCreate(Bundle)之间调用
     *  1.由于onSaveInstanceState()方法onRestoreInstanceState方法不一定成对出现
     *  2.
     * @param savedInstanceState
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    protected abstract void restoreInstanceState(Bundle savedInstanceState);
}
