package com.mdx.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class FrameLayoutFill extends FrameLayout{
	public FrameLayoutFill(Context context) {
		super(context);
	}
	
	public FrameLayoutFill(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public FrameLayoutFill(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = (int) (MeasureSpec.getSize(widthMeasureSpec)); 
		for(int i=0;i<getChildCount();i++){
			View view=getChildAt(i);
			ViewGroup.LayoutParams layout=view.getLayoutParams();
			layout.height=width/getChildCount();
			layout.width=width;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void onLayout(boolean changed,int left,int top,int right,int bottom){
		super.onLayout(changed, left, top, right, bottom);
	}
}
