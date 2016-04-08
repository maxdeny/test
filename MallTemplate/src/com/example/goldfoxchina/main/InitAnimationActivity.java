package com.example.goldfoxchina.main;



import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ViewFlipper;


/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-4-25
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class InitAnimationActivity extends Activity {
    private float downX;
    private float upX;
    private float downY;
    private float upY;
    private long downTime;
    private long upTime;
    private int screenWidthDip;
    private int screenHeightDip;
    private ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initanimation);
        getDisplayMetrics(this);
        init();//初始化设置
        onClick();//viewFlipper点击事件
    }
    //初始化设置
    public void init(){
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setDisplayedChild(0);
    }
    //viewFlipper点击事件
    public void onClick(){
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    downTime = System.currentTimeMillis();
                }else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    upX = motionEvent.getX();
                    upY = motionEvent.getY();
                    upTime = System.currentTimeMillis();
                    if(viewFlipper.getDisplayedChild()<3){
                        if(downX -upX>120&&upTime-downTime<500){
                            viewFlipper.showNext();
                        }
                    }else{   //当是最后 一张图片时，  为什么判断手势动作的区域？？？
                        if(downX<screenWidthDip-screenWidthDip/3 && downX>screenWidthDip/3&&
                            upX<screenWidthDip-screenWidthDip/3 && upX>screenWidthDip/3&&
                            downY>screenHeightDip-screenHeightDip/3&&upY>screenHeightDip-screenHeightDip/3){
                            //再次设置SharedPreferences,记录应用不是初次使用
                            SharedPreferences preferences = getSharedPreferences("InitAnimation", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("isFirst",true);
                            editor.commit();
                            //界面跳转
                            Intent i = new Intent(InitAnimationActivity.this,StartActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }
                return true;
            }
        });
    }
    public void  getDisplayMetrics(Context cx) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidthDip = dm.widthPixels;
        screenHeightDip = dm.heightPixels;
    }
    
    /**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(InitAnimationActivity.this);
			return true;
		}
		return false;
	}
}
