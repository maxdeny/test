package com.zhuolei.mobilesafe.components.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public abstract class DialogLayer extends LinearLayout {

	public DialogLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DialogLayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setValue(int id, String value) {
	}

	public void setValue(int id, int value) {
	}

	abstract public void setPositiveBtnListener(OnClickListener listener);

	abstract public void setNegativeBtnListener(OnClickListener listener);
}
