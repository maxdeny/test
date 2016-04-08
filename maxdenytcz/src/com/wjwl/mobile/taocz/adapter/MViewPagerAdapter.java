package com.wjwl.mobile.taocz.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MViewPagerAdapter extends PagerAdapter {

	private List<View> pagerViews;

	public MViewPagerAdapter(List<View> pagerViews) {
		super();
		this.pagerViews = pagerViews;
	}

	@Override
	public int getCount() {
		if (pagerViews != null){
            return pagerViews.size();
        }
		return 0;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}


	@Override
	public void destroyItem(View container, int arg1, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView((View)object);
	}

	@Override
	public void finishUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(pagerViews.get(position), 0);
		 return pagerViews.get(position);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
