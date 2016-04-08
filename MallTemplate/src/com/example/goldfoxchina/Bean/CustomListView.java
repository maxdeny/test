package com.example.goldfoxchina.Bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class CustomListView extends ListView {
	// 滑动距离及坐标
    private float  xStart, xLast;

	public CustomListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	
	 @Override
	    public boolean onInterceptTouchEvent(MotionEvent ev) {
	            switch (ev.getAction()) {
	            case MotionEvent.ACTION_DOWN:
	                    
	                    xStart = ev.getX();

	                    break;
	            case MotionEvent.ACTION_UP:

	                    xLast = ev.getX();

	                    
	                    if (xStart == xLast) {
	                            return false;   //表示向下传递事件
	                    }
	            
	            }

	            return super.onInterceptTouchEvent(ev);
	    }
	
}
