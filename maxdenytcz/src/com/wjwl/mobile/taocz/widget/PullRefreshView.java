package com.wjwl.mobile.taocz.widget;



import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class PullRefreshView extends ImageView{
	private int lastType=2;

	public PullRefreshView(Context context) {
		super(context);
	}
	
	public PullRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public PullRefreshView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	public void set(int type){
		if(type==0){
			this.clearAnimation();
			lastType=2;
			return;
		}
		if(lastType==type){
			return;
		}
		RotateAnimation rotate= new RotateAnimation(type==2?-180:0,type==2?0:180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotate.setInterpolator(new LinearInterpolator());
		rotate.setDuration(250);
		rotate.setFillAfter(true);
		if(type!=lastType){
			lastType=type;
			this.startAnimation(rotate);
		}
		
	}

	public void set(int top,int height,int type){
		
	}
}
