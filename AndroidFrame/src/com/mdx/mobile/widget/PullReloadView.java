package com.mdx.mobile.widget;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class PullReloadView extends FrameLayout implements MScrollAble{
	private float lPoint = 0,nMove=0,lx,ly,mx=0,my=0;
	private boolean isRfresh = false,moveOver=true;
	private long lastloadtime = System.currentTimeMillis();
	private int pushHeight = 75,mf=0;
	private PullView mPullView;
	private OnRefreshListener mOnRefreshListener;
	private long minLoadTime=0;
	private int ntype=0;
	private boolean scrollable=true;
	private int orientation=LinearLayout.VERTICAL;
	
	public PullReloadView(Context context) {
		super(context);
	}

	public PullReloadView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void resetTouch() {
		lPoint = 0;
		nMove = 0;
		mf=0;
		lx=0;
		ly=0;
		mx=0;
		my=0;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int w = r - l;
		if (this.getChildCount() > 1) {
			int x = 0, y = 0;
			if (isVertical()) {
				y = this.getChildAt(0).getHeight();
				pushHeight = y;
			} else {
				x = this.getChildAt(0).getWidth();
				pushHeight = x;
			}
			int top=-y;
			this.getChildAt(0).layout(l, top, l + this.getChildAt(0).getWidth(),
					top+ this.getChildAt(0).getHeight());
			this.getChildAt(1).layout(x, 0, x + w, this.getChildAt(1).getHeight());
		}
	}

	private void setPullLoadView() {
		if (this.getChildCount() > 1) {
			View view = this.getChildAt(0);
			if (view instanceof PullView) {
				this.mPullView = (PullView) view;
			} else {
				throw new IllegalStateException("PullView Error");
			}
		}
	}
	
	private void scroll(float mv){
		if(isVertical()){
			this.scrollBy(0, -(int)mv);
			return;
		}
		this.scrollBy( -(int)mv,0);
	}
	
	private void scrolt(float mv){
		if(isVertical()){
			this.scrollTo(0, (int)mv);
			return;
		}
		this.scrollTo( (int)mv,0);
	}
	
	private float getL(MotionEvent event){
		if(isVertical()){
			return event.getY();
		}
		return event.getX();
	}
	
	private float sl(){
		if(isVertical()){
			return this.getScrollY();
		}
		return this.getScrollX();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(this.getChildCount()<=1 || !scrollable || !isCanMove(this.getChildAt(1))){
			return super.dispatchTouchEvent(ev);
		}
		if (isRfresh) {
			return true;
		}
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			resetTouch();
			lPoint =getL(ev);
			lx=ev.getX();
			ly=ev.getY();
			setPtype(0);
		} else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
			if (sl() <= 0) {
				nMove = getL(ev) - lPoint;
				mx+=Math.abs(ev.getX()-lx);
				my+=Math.abs(ev.getY()-ly);
				if(my<50 && mf==0){
					mf=1;
				}else if(mx<50 && mf==0){
					mf=2;
				}
				if(!isVertical()){
					if(mf==1 || mf==0){
						return super.dispatchTouchEvent(ev);
					}
				}else{
					if(mf==2 || mf==0){
						return super.dispatchTouchEvent(ev);
					}
				}
				if (isCanMove(this.getChildAt(1))) {
					if (sl() - nMove >= 0) {
						nMove = 0;
					}
					if(!moveOver){
						if(sl()-nMove<0){
							nMove=sl();
						}
					}
					this.scroll(nMove);
				}
				setPTime();
				if(minLoadTime>=0 && (System.currentTimeMillis()-lastloadtime)<minLoadTime){
					setPtype(4);
				}else if (this.sl() <= -pushHeight) {
					setPtype(1);
				} else {
					setPtype(2);
				}
				setPScroll();
				if (sl() != 0) {
					if (isVertical() && my > 30) {
						super.dispatchTouchEvent(createCancelEvent(ev));
					}
					if (!isVertical() && mx > 30) {
						super.dispatchTouchEvent(createCancelEvent(ev));
					}
				}
			}
			lPoint =getL(ev);
		} else if (ev.getAction() == MotionEvent.ACTION_UP) {
			if (this.getScrollY() <= -pushHeight) {
				this.scrollTo(0, -pushHeight);
				onRefresh();
			}else{
				this.scrolt(0);
			}
			resetTouch();
			setPtype(9);
		}
		if(sl()!=0){
			super.dispatchTouchEvent(ev);
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	private MotionEvent createCancelEvent(MotionEvent ev){
		return MotionEvent.obtain(ev.getDownTime(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, ev.getX(), ev.getY(), 0);
	}
	
	private void setPtype(int type) {
		ntype=type;
		if (mPullView != null) {
			mPullView.setType(type,this.getOrientation());
		}
	}

	private void setPTime() {
		if (mPullView != null) {
			mPullView.setTime(System.currentTimeMillis() - lastloadtime);
		}
	}

	private void setPScroll() {
		float scroll=-sl();
		if (mPullView != null) {
			mPullView.setScroll(scroll,this.getOrientation(),ntype);
		}
	}
	

	public void onRefresh() {
		if(minLoadTime>=0 && (System.currentTimeMillis()-lastloadtime)<minLoadTime){
			return;
		}
		isRfresh = true;
		setPtype(3);
		setPScroll();
		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}

	public void setAdapter(BaseAdapter adapter) {
		if (this.getChildCount() > 1) {
			View view = this.getChildAt(1);
			if (view instanceof AbsListView) {
				AbsListView alv = (AbsListView) view;
				alv.setAdapter(adapter);
			}
		}
	}

	public void addCView(View child) {
		addCView(child, -1, null);
	}

	public void addCView(View child, int index) {
		addCView(child, index, null);
	}

	public void addCView(View child, ViewGroup.LayoutParams params) {
		addCView(child, -1, params);
	}

	public void addCView(View child, int index, ViewGroup.LayoutParams params) {
		if (this.getChildCount() > 0) {
			View view = this.getChildAt(1);
			if (view instanceof AbsListView) {
				return;
			} else if (view instanceof ScrollView) {
				ScrollView sv = (ScrollView) view;
				if (sv.getChildCount() > 0) {
					View nv = sv.getChildAt(0);
					naddview(nv, child, index, params);
				}
			} else {
				naddview(view, child, index, params);
			}
		}
	}

	private void naddview(View p, View c, int index,
			ViewGroup.LayoutParams params) {
		if (p instanceof LinearLayout) {
			((LinearLayout) p).addView(c, index, params);
		} else if (p instanceof FrameLayout) {
			((FrameLayout) p).addView(c, index, params);
		} else if (p instanceof RelativeLayout) {
			((RelativeLayout) p).addView(c, index, params);
		}
	}

	@Override
	public void addView(View child) {
		checkaddview();
		super.addView(child);
		setPullLoadView();
	}

	@Override
	public void addView(View child, int index) {
		checkaddview();
		super.addView(child, index);
		setPullLoadView();
	}

	@Override
	public void addView(View child, ViewGroup.LayoutParams params) {
		checkaddview();
		super.addView(child, params);
		setPullLoadView();
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		checkaddview();
		super.addView(child, index, params);
		setPullLoadView();
	}

	private void checkaddview() {
		if (getChildCount() > 1) {
			throw new IllegalStateException(
					"ScrollView can host only two direct child");
		}
	}

	private boolean isCanMove(View view) {
		if(!isVertical()){
			return true;
		}
		if (view instanceof AbsListView) {
			AbsListView alv = ((AbsListView) this.getChildAt(1));
			boolean bol = false;
			if (alv.getChildCount() > 0) {
				bol = alv.getChildAt(0).getTop() == alv.getPaddingTop();
			}
			return alv.getFirstVisiblePosition() <= 0 && bol;
		}
		return view.getScrollY() == 0;
	}

	public void onRefreshComplete() {
		lastloadtime = System.currentTimeMillis();
		isRfresh = false;
		this.scrolt(0);
	}
	
	public void refreshComplete(){
		onRefreshComplete();
	}

	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	private boolean isVertical() {
		return this.getOrientation() == LinearLayout.VERTICAL;
	}

	public boolean isMoveOver() {
		return moveOver;
	}

	public void setMoveOver(boolean moveOver) {
		this.moveOver = moveOver;
	}

	public long getMinLoadTime() {
		return minLoadTime;
	}

	public void setMinLoadTime(long minLoadTime) {
		this.minLoadTime = minLoadTime;
	}

	@Override
	public void setScrollAble(boolean bol) {
		this.scrollable=bol;
	}

	@Override
	public boolean isScrollAble() {
		return this.scrollable;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
}
