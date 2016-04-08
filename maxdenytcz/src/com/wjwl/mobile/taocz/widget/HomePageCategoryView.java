package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class HomePageCategoryView extends LinearLayout {
	
	private LayoutInflater inflater;
	
	public HomePageCategoryView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		inflater = LayoutInflater.from(context);
		mFinder();
	}

	public HomePageCategoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		mFinder();
	}

	public HomePageCategoryView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		mFinder();
	}

	private void mFinder() {
		inflater.inflate(R.layout.home_page_category_layout,this);
	}
	
	
}
