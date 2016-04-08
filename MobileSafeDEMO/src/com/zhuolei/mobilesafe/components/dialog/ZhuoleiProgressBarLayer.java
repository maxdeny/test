package com.zhuolei.mobilesafe.components.dialog;

import com.zhuolei.mobilesafe.main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ZhuoleiProgressBarLayer extends LinearLayout {

	private ImageView imgLogoCircle;

	public ZhuoleiProgressBarLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public ZhuoleiProgressBarLayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.progress_bar,
				this);

		imgLogoCircle = (ImageView) findViewById(R.id.imgLogoCircle);

	}

	public void startRotateAnim() {
		RotateAnimation anim = (RotateAnimation) AnimationUtils.loadAnimation(getContext(),
				R.anim.rotate);
		anim.setRepeatCount(Animation.INFINITE);
		imgLogoCircle.startAnimation(anim);
	}

	public void stopRotateAnim() {
		imgLogoCircle.clearAnimation();
	}

	public void setText(int resid) {
		((TextView) findViewById(R.id.txtProgressContent)).setText(resid);
	}
	
	public void setText(String text){
		((TextView) findViewById(R.id.txtProgressContent)).setText(text);
	}
}
