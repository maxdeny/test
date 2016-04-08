package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

public class PullRefreshView2 extends ImageView {
	private int lastTop = 0, type = 0;
	private static Drawable car, carf;
	private int top = 0;
	private boolean onloading;

	public PullRefreshView2(Context context) {
		super(context);
		if (car == null) {
			car = context.getResources().getDrawable(R.drawable.bg_pullsh_car);
			carf = context.getResources()
					.getDrawable(R.drawable.bg_pullsh_carf);
		}
	}

	public PullRefreshView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (car == null) {
			car = context.getResources().getDrawable(R.drawable.bg_pullsh_car);
			carf = context.getResources()
					.getDrawable(R.drawable.bg_pullsh_carf);
		}
	}

	public PullRefreshView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (car == null) {
			car = context.getResources().getDrawable(R.drawable.bg_pullsh_car);
			carf = context.getResources()
					.getDrawable(R.drawable.bg_pullsh_carf);
		}
	}

	public void set(int type) {
		View v=(View) getParent();
		View lite=v.findViewById(R.id.pull_car_light_loading);
		lite.setVisibility(View.INVISIBLE);
		lite.clearAnimation();
		if(type==3){
			if(!onloading){
				lite.setVisibility(View.VISIBLE);
				AlphaAnimation alan=new AlphaAnimation(0.6f, 0);
				alan.setDuration(1000);
				alan.setRepeatMode(Animation.REVERSE);
				alan.setRepeatCount(Animation.INFINITE);
				lite.setAnimation(alan);
				onloading=true;
			}
		}else{
			onloading=false;
		}
		
		// if(type==0){
		// this.clearAnimation();
		// lastType=2;
		// return;
		// }
		// if(lastType==type){
		// return;
		// }
		//
		// AnimationSet animset=new AnimationSet(false);
		//
		// TranslateAnimation tla=new TranslateAnimation(-200, 0,0, 0);
		// tla.setDuration(250);
		// tla.setFillAfter(true);
		// animset.addAnimation(tla);
		//
		//
		// if(type!=lastType){
		// lastType=type;
		// this.startAnimation(animset);
		// }
		//
	}

	public void set(int top, int height, int type) {
		this.top = (int) ((top * 1d) / (height * 1d) * (this.getWidth() + 30));
		if (lastTop < top) {
			this.type = 0;
		} else if (lastTop > top) {
			this.type = 1;
		}
		lastTop = top;
		this.invalidate();
	}

	@Override
	public void onDraw(Canvas canvas) {
		int l = top;
		int h = this.getHeight();
		if (type == 1) {
			carf.setBounds(l - 110, 0, l - 30, h);
			carf.draw(canvas);
		} else {
			car.setBounds(l - 110, 0, l - 30, h);
			car.draw(canvas);
		}
	}
}