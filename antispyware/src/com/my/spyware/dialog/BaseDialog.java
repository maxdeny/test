package com.my.spyware.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;
import android.view.Window;

import com.my.spyware.effects.BaseEffects;
import com.my.spyware.effects.Effectstype;


public abstract class BaseDialog extends Dialog implements OnShowListener {

	protected Context mContext;
	protected View mainView;

	public BaseDialog(Context context) {
		super(context);
		this.mContext = context;
	}

	public BaseDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(getlayoutResID());
		View v = getWindow().getDecorView();
		v.setBackgroundResource(android.R.color.transparent);
		setCancelable(false);
		setCanceledOnTouchOutside(false);
	}

	protected abstract int getlayoutResID();

	protected void init() {
		initView();
		setListener();
	}

	protected abstract void initView();

	protected void setListener() {
		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface arg0) {
		BaseEffects animator = initEffectStype().getAnimator();
		animator.start(mainView);
	}

	protected Effectstype initEffectStype() {
		return Effectstype.SlideBottom;// default SlideBottom
	}

}
