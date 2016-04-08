package com.mdx.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MListView extends ListView implements MScrollAble{
	private boolean scrollAble=true;
	
	public MListView(Context context) {
		super(context);
	}

	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(scrollAble){
			return super.onInterceptTouchEvent(ev);
		}else{
			return false;
		}
	}
	
	public void setScrollAble(boolean bol){
		this.scrollAble=bol;
	}
	
	public boolean isScrollAble(){
		return this.scrollAble;
	}
}
