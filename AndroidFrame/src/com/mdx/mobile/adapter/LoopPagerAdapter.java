package com.mdx.mobile.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public abstract class LoopPagerAdapter<T> extends PagerAdapter{
	private Stack<View> mViews=new Stack<View>();
	private List<T> mDataList=new ArrayList<T>();
	private Context context;
	private ViewPager mViewPager;
	private int offset=0;
	public final static int NOWCURR=100000;
	
	public LoopPagerAdapter(Context context,List<T> list){
		this.context=context;
		mDataList=list;
		offset=-NOWCURR%list.size();
	}
	
	public Context getContext(){
		return context;
	}
	
	public int getSize(){
		return mDataList.size();
	}
	
	@Override
	public int getCount() {
		return  mDataList.size()+offset;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	
	public abstract View getView(int position, View convertView) ;
	
	
	@Override
	public Object instantiateItem(View rootview, int position) {
		mViewPager=(ViewPager) rootview;
		View contentView=null;
		if(mViews.size()>0){
			contentView=mViews.pop();
		}
		contentView=getView(position, contentView);
		mViewPager.addView(contentView);
		return contentView;
	}

	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		((ViewPager) arg0).removeView((View) arg2);
		mViewPager=(ViewPager) arg0;
		mViews.push((View) arg2);
	}
	
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	public T get(int position){
		return mDataList.get(getPosition(position));
	}
	
	public T getItem(int position){
		return mDataList.get(position);
	}
	
	private int getPosition(int position){
		return (position+offset)%getSize();
	}
	
	public void addLeft(T item){
		offset=1;
		this.notifyDataSetChanged();
		offset=0;
		mDataList.add(0,item);
		mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
		this.notifyDataSetChanged();
	}
	
	public void addRight(T item){
		mDataList.add(item);
		this.notifyDataSetChanged();
	}
}
