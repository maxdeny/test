package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.Gallery;

public class CategoryGallery extends Gallery {

	private static final int OFFSETX = 100;
	float mLastMotionX,mLastMotionY;
	 private int mTouchSlop;//手指大小的距离

	public CategoryGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 init();
	}

	public CategoryGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		 init();
	}

	public CategoryGallery(Context context) {
		super(context);
		 init();
	}

	private void init() {
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }
	
	float startX;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		  final int action = ev.getAction();//获取触摸事件类型
	        final float x = ev.getX();//每次触摸事件的x坐标
	        final float y = ev.getY();//每次触摸事件的y坐标
	        switch (action) {
	        case MotionEvent.ACTION_DOWN://按下事件
	            mLastMotionX = x;//初始化每次触摸事件的x方向的初始坐标，即手指按下的x方向坐标
	            mLastMotionY = y;//初始化每次触摸事件的y方向的初始坐标，即手指按下的y方向坐标
	            break;

	        case MotionEvent.ACTION_MOVE:
	            final int deltaX = (int) (mLastMotionX - x);//每次滑动事件x方向坐标与触摸事件x方向初始坐标的距离
	            final int deltaY = (int) (mLastMotionY - y);//每次滑动事件y方向坐标与触摸事件y方向初始坐标的距离
	            boolean xMoved = Math.abs(deltaX) > mTouchSlop && Math.abs(deltaY/deltaX) < 1;
	            //判断触摸事件处理的传递方向，该业务中是，
	            //x方向的距离大于手指，并且y方向滑动的距离小于x方向的滑动距离时，Gallery消费掉此次触摸事件
	            //如果需要，请在您的业务中，改变判断的逻辑
	            if (xMoved) {//Gallery需要消费掉此次触摸事件
	                return true;//返回true就不会将此次触摸事件传递给子View了，我的业务中是ListView
	            }
	            break;
	        }
	        return false;//将此次触摸事件传递给子View，即ListView
	}

}