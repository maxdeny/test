package com.mdx.mobile.widget;

import java.util.ArrayList;

import com.mdx.mobile.mcommons.Scroller;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class DragView extends FrameLayout implements MScrollAble {
	private VelocityTracker mVelocityTracker;
	private boolean mIsBeingDragged = false,mIsTouch=false,mAutoTo=true,mAutoMiddle=true,mAutoRun=false;
	private static final int INVALID_POINTER = -1;
	private int mActivePointerId = INVALID_POINTER;
	private Scroller mScroller;
	private float mLastMotionX;
	private int mTouchSlop;
	private int mMinimumVelocity;
	private int mMaximumVelocity;
	private int mRealWidth,mScrollWidth,mInd=0,mLastInd=0,mAutoTimes=3000;
	private int mMoveType=0,mMoveLength=0,mMoveStep;
	private DragCurr mDragCurr;
	private ArrayList<Integer> mListLength=new ArrayList<Integer>();
	private Thread auThread=null;
	private boolean isScrollAble=true;
	private boolean isparentscrollable=true;
	private int moveend=200,movetime=500,movenext=1000;
	private SparseArray<View> mRemovedViewMap=new SparseArray<View>();
	private boolean startscroll=false;

	public DragView(Context context) {
		super(context);
		init();
	}

	public DragView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DragView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private void init() {
		mScroller = new Scroller(getContext());
		final ViewConfiguration configuration = ViewConfiguration.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
		int parentLeft = getPaddingLeft();
		int parentRight = r - l - getPaddingRight();
		int parentTop = getPaddingTop();
		int parentBottom = b - t - getPaddingBottom();
		int rightLeft=0;
		int showed = 0;
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				if(child.getTag()==null){
					child.setTag(i);
				}
				LayoutParams lp = (LayoutParams) child.getLayoutParams();
				int width = child.getMeasuredWidth();
				int height = child.getMeasuredHeight();
				int gravity = lp.gravity;
				
				if (showed == 0) {
					parentLeft = (r - l - width) / 2;
				}
				
				rightLeft= (r - l - width) / 2;

				int childLeft = parentLeft;
				int childTop = parentTop;

				if (gravity != -1) {
					final int horizontalGravity = gravity
							& Gravity.HORIZONTAL_GRAVITY_MASK;
					final int verticalGravity = gravity
							& Gravity.VERTICAL_GRAVITY_MASK;

					switch (horizontalGravity) {
					case Gravity.LEFT:
						childLeft = parentLeft + lp.leftMargin;
						break;
					case Gravity.CENTER_HORIZONTAL:
						childLeft = parentLeft
								+ (parentRight - parentLeft - width) / 2
								+ lp.leftMargin - lp.rightMargin;
						break;
					case Gravity.RIGHT:
						childLeft = parentRight - width - lp.rightMargin;
						break;
					default:
						childLeft = parentLeft + lp.leftMargin;
					}

					switch (verticalGravity) {
					case Gravity.TOP:
						childTop = parentTop + lp.topMargin;
						break;
					case Gravity.CENTER_VERTICAL:
						childTop = parentTop
								+ (parentBottom - parentTop - height) / 2
								+ lp.topMargin - lp.bottomMargin;
						break;
					case Gravity.BOTTOM:
						childTop = parentBottom - height - lp.bottomMargin;
						break;
					default:
						childTop = parentTop + lp.topMargin;
					}
				}
				child.layout(childLeft, childTop, childLeft + width, childTop+ height);
				parentLeft = childLeft + width;
				showed += 1;
			}
		}
		if (mMoveType == 3) {
			removeNoShow();
		}
		this.mScrollWidth=parentLeft-getWidth()+rightLeft;
		this.mRealWidth=this.mScrollWidth+getWidth();
		if(mDragCurr!=null){
			mDragCurr.setWidth(mRealWidth);
			mListLength.clear();
			for(int i=0;i<getChildCount()+mRemovedViewMap.size();i++){
				if(i<getChildCount()){
					mListLength.add(getChildAt(i).getWidth());
				}else{
					int key=mRemovedViewMap.keyAt(i-getChildCount());
					mListLength.add(mRemovedViewMap.get(key).getWidth());
				}
			}
			mDragCurr.setLength(mListLength);
			mDragCurr.setSize(getChildCount()+mRemovedViewMap.size());
			mDragCurr.setInd(this.mInd);
		}
		if(mScroller.isFinished()){
			updateScroll();
		}else{
			autoRun();
		}
	}
	
	private void updateScroll(){
		if(startscroll){
			autoRun();
			startscroll=false;
		}
	}

	private void autoRun() {
		post(new Runnable() {
			public void run() {
				requestIlayout();
			}
		});
	}
	
	private void requestIlayout(){
		requestLayout();
		invalidate();
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(!isScrollAble){
			return false;
		}
		
		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
			return true;
		}

		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_MOVE: {
			final int activePointerId = mActivePointerId;
			if (activePointerId == INVALID_POINTER) {
				break;
			}

			final int pointerIndex = ev.findPointerIndex(activePointerId);
			final float x = ev.getX(pointerIndex);
			int yDiff = (int) Math.abs(x - mLastMotionX);
			if (yDiff > mTouchSlop) {
				mIsBeingDragged = true;
				mLastMotionX =x;
			}
			break;
		}

		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();
			mLastMotionX = x;
			mActivePointerId = ev.getPointerId(0);
			mIsBeingDragged = !mScroller.isFinished();
			if(auThread!=null){
				auThread.interrupt();
				auThread=null;
			}
			mIsTouch=true;
			break;
		}

		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mIsBeingDragged = false;
			mActivePointerId = INVALID_POINTER;
			postInvalidate();
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		}
		return mIsBeingDragged;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
			return false;
		}

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);

		final int action = ev.getAction();

		switch (action & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN: {
			final float x = ev.getX();

			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}

			mIsBeingDragged = true;
			mIsTouch=true;
			
			if(auThread!=null){
				auThread.interrupt();
				auThread=null;
			}
			
			mLastMotionX = x;
			mActivePointerId = ev.getPointerId(0);
			break;
		}
		case MotionEvent.ACTION_MOVE:
			if(this.mMoveType==3){
				setScrollAbleParent(false);
			}
			if (mIsBeingDragged && mActivePointerId>=0 && getChildCount()>0) {
				final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
				if(activePointerIndex>=0){
					if(this.mMoveType!=3){
						setScrollAbleParent(false);
					}
					final float x = ev.getX(activePointerIndex);
					final int deltaX = (int) (mLastMotionX - x);
					mMoveLength+=deltaX;
					mLastMotionX = x;
					if(haseParent() && this.mMoveType!=3){
						if(getScrollX()<=0){
							if(deltaX<0){
								touchEnd();
								return false;
							}
						}else if(getScrollX()>=this.mScrollWidth){
							if(deltaX>0){
								touchEnd();
								return false;
							}
						}
						if(getScrollX()+deltaX<0){
							scrollMTo(0, 0);
							break;
						}else if(getScrollX()+deltaX>mScrollWidth){
							scrollMTo(mScrollWidth, 0);
							break;
						}
					}
					scrollMBy(deltaX, 0);
					if(this.mMoveType==3){
			    		requestLayout();
			    	}
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mIsBeingDragged && getChildCount()>0) {
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
				int initialVelocitx = (int) velocityTracker.getXVelocity();

				fling(-initialVelocitx);

				touchEnd();
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (mIsBeingDragged) {
				touchEnd();
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			break;
		}
		return true;
	}

	private void touchEnd() {
		if (getChildCount() > 0) {
			if(this.mMoveType!=3){
				setScrollAbleParent(true);
			}
			mActivePointerId = INVALID_POINTER;
			mIsBeingDragged = false;
			mIsTouch = false;
			mMoveLength = 0;
			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
		}
	}
	
    public void fling(int velocityX) {
    	if(getScrollX()<0){
    		mScroller.startScroll(getScrollX(), 0,-getScrollX(), 0,moveend);
    		startscroll=true;
    	}else if(getScrollX()>this.mScrollWidth){
    		int dx=getScrollX()-this.mScrollWidth;
    		mScroller.startScroll(getScrollX(), 0,-dx, 0,moveend);
    		startscroll=true;
    	}else {
    		switch(this.mMoveType){
    		case 0:
    			if(Math.abs(velocityX) > mMinimumVelocity){
    	            mScroller.fling(getScrollX(), getScrollY(), velocityX, 0, 0,this.mScrollWidth, 0, 0);
    	            startscroll=true;
    	            if(mAutoMiddle){
    	            	int to=getMaxTo(mScroller.getFinalX());
    	            	mScroller.setFinalX(to);
    	            }
    	        }
    			break;
    		case 1:
    			if(Math.abs(velocityX)>600 && Math.abs(this.mMoveLength)<getChildAt(this.mLastInd).getWidth()/2){
    				int addind=velocityX/Math.abs(velocityX);
    				moveTo(mInd+addind);
    	        }
    			break;
    		case 3:
    			if(Math.abs(velocityX)>600){
    				int addind=velocityX/Math.abs(velocityX);
    				moveTo(mInd+addind);
    	        }
    			break;
    		}
    	}
    	invalidate();
    }
    
    public void moveTo(int ind){
    	moveTo(ind,movetime);
    }
    
    public void moveTo(int ind,int delety){
    	if(ind>=0 && ind<getChildCount()){
        	int scrollx=getScrollX();
        	int middlex=scrollx+getWidth()/2;
    		View view=getChildAt(ind);
    		final int ywz=middlex-(view.getLeft()+view.getWidth()/2);
    		mScroller.startScroll(getScrollX(), 0,-ywz, 0,delety);
    		startscroll=true;
    	}
    }
    
    private int getMaxTo(int scrollx){
    	int middlex=scrollx+getWidth()/2;
    	View selectView=null;
    	for(int i=0;i<getChildCount();i++){
    		View view=getChildAt(i);
    		if(view.getLeft()<middlex && view.getRight()>=middlex){
    			selectView=view;
    			break;
    		}
    	}
    	
    	return (selectView.getLeft()+selectView.getWidth()/2)-getWidth()/2;
    }
    
    
    private boolean haseParent(){
    	View view=this;
    	while(view.getParent() instanceof View){
    		view=(View) view.getParent();
    		if(view instanceof MScrollAble){
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean checkSelectView(){
    	int scrollx=getScrollX();
    	int middlex=scrollx+getWidth()/2;
    	View selectView=null;
    	for(int i=0;i<getChildCount();i++){
    		View view=getChildAt(i);
    		if(view.getLeft()<middlex && view.getRight()>=middlex){
    			selectView=view;
    			this.mLastInd=this.mInd;
    			if(this.mMoveType==3){
    				int ind=(Integer) view.getTag();
    				if(this.mInd!=ind){
    					this.requestLayout();
    					this.mInd=ind;
    				}
    			}else{
    				this.mInd=i;
    			}
    			if(this.mDragCurr!=null && this.mLastInd!=this.mInd){
		    		this.mDragCurr.setInd(this.mInd);
		    	}
    			break;
    		}
    	}
    	if(selectView==null){
    		return true;
    	}
		final int ywz=middlex-(selectView.getLeft()+selectView.getWidth()/2);
		if(Math.abs(ywz)>0){
			mScroller.startScroll(getScrollX(), 0,-ywz, 0,movenext);
			return true;
		}
		return false;
    }

    @Override
	public void computeScroll() {
    	boolean invalidata=false;
		if (mScroller.computeScrollOffset()) {
			scrollMTo(mScroller.getCurrX(), mScroller.getCurrY());
			invalidata=true;
		}
		
		if(mScroller.isFinished() && !mIsTouch && mAutoTo){
			invalidata=checkSelectView();
		}
		if(invalidata){
			postInvalidate();
		}
	}
    
    private View getSelectView(){
    	int scrollx=getScrollX();
    	int middlex=scrollx+getWidth()/2;
    	
    	View selectView=null;
    	for(int i=0;i<getChildCount();i++){
    		View view=getChildAt(i);
    		if(view.getLeft()<middlex && view.getRight()>=middlex){
    			selectView=view;
    			if(i!=this.mInd && !mIsTouch  ){
    				this.mLastInd=this.mInd;
    			}
    			if(this.mMoveType==3){
    				int ind=(Integer) view.getTag();
    				if(this.mInd!=ind){
    					this.requestLayout();
    					this.mInd=ind;
    				}
    			}else{
    				this.mInd=i;
    			}
    			if(this.mDragCurr!=null){
		    		this.mDragCurr.setInd(this.mLastInd);
		    	}
    			break;
    		}
    	}
    	return selectView;
    }
    
    
    private synchronized void setScrollAbleParent(boolean bol){
    	if(isparentscrollable==bol){
    		return;
    	}
    	View view=this;
    	while(view.getParent() instanceof View){
    		view=(View) view.getParent();
    		if(view instanceof MScrollAble){
    			MScrollAble scable=(MScrollAble) view;
    			scable.setScrollAble(bol);
    		}
    	}
    	isparentscrollable=bol;
    }

    @Override
    public void onScrollChanged(int l,int t,int oldl,int oldt){
    	super.onScrollChanged(l, t, oldl, oldt);
    	if(getChildCount()==0){
    		return;
    	}
    	if(mMoveType==3){
    		return;
    	}
    	View view=getChildAt(this.mLastInd);
    	int middlex=getScrollX()+getWidth()/2;
    	
    	getSelectView();
    	
    	int position=view.getLeft()+view.getWidth()/2;
    	int length=0;
    	if(position<middlex){
    		if(this.mLastInd+1<getChildCount()){
    			View vm=getChildAt(this.mLastInd+1);
    			length=vm.getWidth()/2+view.getWidth()/2;
    		}else{
    			length=view.getWidth();
    		}
    	}else if(position>middlex){
    		if(this.mLastInd>0){
    			View vm=getChildAt(this.mLastInd-1);
    			length=vm.getWidth()/2+view.getWidth()/2;
    		}else{
    			length=view.getWidth();
    		}
    	}
    	if(Math.abs(Math.abs(middlex-position)-length)==0){
    		this.mLastInd=this.mInd;
    	}
    	if(this.mDragCurr!=null && Math.abs(middlex-position)>4){
    		if(mMoveType==3){
    			this.mDragCurr.setInd(this.mInd);
    		}else{
    			this.mDragCurr.onScroll(middlex-position, length,getScrollX(), this.mScrollWidth,this.mMoveLength);
    		}
    	}
    }
    
	private void removeNoShow() {
		boolean remove=false;
		for(int i=0;i<getChildCount();i++){
			View child=getChildAt(i);
			if (child.getRight() - getScrollX() <= 0 || child.getBottom() < 0
					|| child.getTop() > getTop()
					|| child.getLeft() - getScrollX() >= getRight()){
				this.offerMap(child);
				removeViewInLayout(child);
				remove=true;
			}
		}
		if(remove && getChildCount()<=1){
			scrollMTo(0, 0);
			fillShow(false);
		}else{
			fillShow(true);
		}
	}
	
	private void fillShow(boolean bol){
		int childcunt=getChildCount()+mRemovedViewMap.size();
		if(childcunt<=1){
			return;
		}
		int ind=mInd;
		
		boolean leftadd=true, rightadd=true;
		int scrollx=getScrollX();
		for(int i=0;i<getChildCount();i++){
			View view=getChildAt(i);
			if(view.getLeft()-scrollx<=getLeft()){
				leftadd=false;
			}
			if(view.getRight()-scrollx>=getRight()){
				rightadd=false;
			}
		}
		int key=0;
		if(leftadd && bol){
			if(ind==0){
				key=childcunt-1;
				if(mRemovedViewMap.get(key)==null){
					return;
				}
				addViewInLayout(mRemovedViewMap.get(key), 0, mRemovedViewMap.get(key).getLayoutParams());
				mRemovedViewMap.remove(key);
			}else{
				key=ind-1;
				if(mRemovedViewMap.get(key)==null){
					return;
				}
				addViewInLayout(mRemovedViewMap.get(key), 0, mRemovedViewMap.get(key).getLayoutParams());
				mRemovedViewMap.remove(key);
			}
			scrollMBy(getWidth(), 0);
		}
		if(rightadd){
			if(ind==childcunt-1){
				key=0;
				if(mRemovedViewMap.get(key)==null){
					return;
				}
				addViewInLayout(mRemovedViewMap.get(key), -1, mRemovedViewMap.get(key).getLayoutParams());
				mRemovedViewMap.remove(key);
			}else{
				key=ind+1;
				if(mRemovedViewMap.get(key)==null){
					return;
				}
				addViewInLayout(mRemovedViewMap.get(key), -1, mRemovedViewMap.get(key).getLayoutParams());
				mRemovedViewMap.remove(key);
			}
		}
	}
	
	protected final void offerMap(View view){
		this.mRemovedViewMap.put((Integer)view.getTag(), view);
	}
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
    	super.dispatchDraw(canvas);
    	autorun();
    }
    
	private class Aumove implements Runnable{
		public void run() {
			try {
				Thread.sleep(mAutoTimes);
				post(new Runnable() {
					public void run() {
						next();
					}
				});
			} catch (Exception e) {
				return;
			} finally {
				auThread = null;
			}
		}
	}
    
	
	private void autorun(){
		if(mAutoRun){
			if(auThread==null){
				auThread =new Thread(new Aumove());
				auThread.start();
			}
		}
	}
    
	public void next() {
		if(auThread!=null){
			auThread.interrupt();
			auThread=null;
		}
		if(mInd==0){
			mMoveStep=1;
		}
		if(mInd==getChildCount()-1){
			mMoveStep=-1;
		}
		if(mInd+mMoveStep<getChildCount()){
			moveTo(mInd+mMoveStep,movenext);
			invalidate();
		}
	}

	public boolean isAutoTo() {
		return mAutoTo;
	}

	public void setAutoTo(boolean mAutoTo) {
		this.mAutoTo = mAutoTo;
	}

	public boolean isAutoMiddle() {
		return mAutoMiddle;
	}

	public void setAutoMiddle(boolean mAutoMiddle) {
		this.mAutoMiddle = mAutoMiddle;
	}

	public int getCurr() {
		return mInd;
	}

	public void setCurr(int mInd) {
		moveTo(mInd,1);
		invalidate();
	}

	public void setDragCurr(DragCurr mDragCurr) {
		this.mDragCurr = mDragCurr;
	}

	public boolean isAutoRun() {
		return mAutoRun;
	}

	public void setAutoRun(boolean mAutoRun) {
		this.mAutoRun = mAutoRun;
	}

	public int getAutoTimes() {
		return mAutoTimes;
	}

	public void setAutoTimes(int mAutoTimes) {
		this.mAutoTimes = mAutoTimes;
	}

	public int getMoveType() {
		return mMoveType;
	}

	public void setMoveType(int mMoveType) {
		this.mMoveType = mMoveType;
	}

	public int getTouchSlop() {
		return mTouchSlop;
	}

	public void setTouchSlop(int mTouchSlop) {
		this.mTouchSlop = mTouchSlop;
	}

	@Override
	public void setScrollAble(boolean bol) {
		isScrollAble=bol;
	}

	@Override
	public boolean isScrollAble() {
		return isScrollAble;
	}

	public int getMoveend() {
		return moveend;
	}

	/**
	 * 设置弹动的时间
	 * @param movenext
	 */
	public void setMoveend(int moveend) {
		this.moveend = moveend;
	}

	public int getMovetime() {
		return movetime;
	}

	/**
	 * 设置移动的时间
	 * @param movenext
	 */
	public void setMovetime(int movetime) {
		this.movetime = movetime;
	}

	public int getMovenext() {
		return movenext;
	}

	public void addView(View child, int index) {
		child.setTag(getChildCount() + mRemovedViewMap.size());
		super.addView(child, index);
	}
	
	/**
	 * 设置移动到下一个的时间
	 * @param movenext
	 */
	public void setMovenext(int movenext) {
		this.movenext = movenext;
	}
	
	
	private void scrollMTo(int x,int y){
		scrollTo(x,y);
	}
	
	private void scrollMBy(int x,int y){
	}
}
