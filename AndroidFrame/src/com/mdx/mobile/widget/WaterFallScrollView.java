package com.mdx.mobile.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;
import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.widget.PageListView.PageRun;

public class WaterFallScrollView extends ScrollView {
	private WaterFallView mWaterFallView;
	protected boolean havepage=false,reload=true,loading=false;
	protected PageRun mLoadData;
	private int page=1;

	public WaterFallScrollView(Context context) {
		super(context);
		init(context);
	}

	public WaterFallScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public WaterFallScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mWaterFallView = new WaterFallView(context);
		mWaterFallView.initView(context);
		super.addView(mWaterFallView);
	}
	
	public void setColumn(int column){
		mWaterFallView.column_count=column;
		mWaterFallView.initView(getContext());
	}

	@SuppressWarnings("rawtypes")
	public void setAdapter(MAdapter adapter) {
		this.mWaterFallView.setAdapter(adapter);
	}

	@SuppressWarnings("rawtypes")
	public void addAdapter(MAdapter adapter) {
		this.mWaterFallView.addAdapter(adapter);
	}

	public void addView(View v) {
		this.mWaterFallView.addView(v);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		this.mWaterFallView.removeImageByNoShow(getScrollX(),getScrollY(),getHeight(),getWidth());
		if(this.getChildCount()>=0){
			int height=this.getChildAt(0).getHeight();
			if(height<=t+this.getHeight() && !loading && havepage){
				run();
			}
		}
	}
	
	public void endPage(){
		havepage=false;
		loading=false;
		mWaterFallView.removeLoadingView();
	}
	
	public void setLoadView(View loadingView) {
		this.mWaterFallView.setLoadingView(loadingView);
	}
	
	public void reload(){
		page=1;
		reload=true;
		havepage=true;
		loading=false;
		start(page);
	}
	
	public void start(int page){
		this.page=page;
		havepage=true;
		loading=false;
		run();
	}
	
	private void run(){
		loading=true;
		if(mLoadData!=null){
			mLoadData.run(page);
		}
	}
	
	public void addData(MAdapter<?> list){
		loading=false;
		page++;
		if(reload){
			reload=false;
			setAdapter(list);
			return;
		}
		addAdapter(list);
	}
	
	public void setLoadData(PageRun mLoadData) {
		this.mLoadData = mLoadData;
	}
	
	public MAdapter<?> getAdapter(){
		return this.mWaterFallView.getMadapter();
	}
}
