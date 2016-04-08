package com.mdx.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

public class FillLine extends RadioGroup {
	
	public FillLine(Context context) {
		super(context);
	}
	
	public FillLine(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onLayout(boolean changed,int l,int t,int r,int b){
		super.onLayout(changed, l, t, r, b);
		int len=0;
		for(int i=0;i<this.getChildCount();i++){
			View view=this.getChildAt(i);
			len+=view.getWidth();
		}
		len=len+getPaddingLeft()+getPaddingRight();
		int skip=(int) ((((r-l)-len)*1f)/((this.getChildCount()-1)*1f));
		int left=getPaddingLeft();
		for(int i=0;i<this.getChildCount();i++){
			View view=this.getChildAt(i);
			view.layout(left,view.getTop(),left+view.getWidth(),view.getBottom());
			left+=view.getWidth()+skip;
		}
	}
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
	
}
