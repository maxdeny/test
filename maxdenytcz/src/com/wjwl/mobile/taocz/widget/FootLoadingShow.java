package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


public class FootLoadingShow extends LinearLayout {	
	public FootLoadingShow(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public FootLoadingShow(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.item_footloadingshow, this);
	}
}
