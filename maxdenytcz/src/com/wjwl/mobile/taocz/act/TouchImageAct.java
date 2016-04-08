package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.images.GestureDetector;
import com.wjwl.mobile.taocz.images.ImageViewTouch;
import com.wjwl.mobile.taocz.images.PagerAdapter;
import com.wjwl.mobile.taocz.images.ScaleGestureDetector;
import com.wjwl.mobile.taocz.images.ViewPager;

public class TouchImageAct extends Activity{
	private static final String TAG = TouchImageAct.class.getSimpleName();
	private static final int SHOW_HIDE_CONTROL_ANIMATION_TIME = 500;
	private static final int PAGER_MARGIN_DP = 40;
	private ViewPager mViewPager;
	private ViewGroup mHeader;
	private TextView mPageShwo;
	private ZoomControls mZoomButtons;
	private ImagePagerAdapter mPagerAdapter;
	private GestureDetector mGestureDetector;
	private ScaleGestureDetector mScaleGestureDetector;
	private boolean mPaused;
	private boolean mOnScale = false;
	private boolean mOnPagerScoll = false;
	private boolean mControlsShow = false;
	private List<String> mImageList;
	private int mPosition;
	private String gettype=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mHeader = (ViewGroup) findViewById(R.id.ll_header);
		mPageShwo = (TextView) findViewById(R.id.tv_page);
		mZoomButtons = (ZoomControls) findViewById(R.id.zoomButtons);
		mZoomButtons.setZoomSpeed(100);
		mZoomButtons.setOnZoomInClickListener(new OnClickListener() {
			public void onClick(View v) {
				getCurrentImageView().zoomIn();
				updateZoomButtonsEnabled();
			}
		});
		mZoomButtons.setOnZoomOutClickListener(new OnClickListener() {
			public void onClick(View v) {
				getCurrentImageView().zoomOut();
				updateZoomButtonsEnabled();
			}
		});

		final float scale = getResources().getDisplayMetrics().density;
		int pagerMarginPixels = (int) (PAGER_MARGIN_DP * scale + 0.5f);
		mViewPager.setPageMargin(pagerMarginPixels);
		mViewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));

		mPagerAdapter = new ImagePagerAdapter();
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mPageChangeListener);
		setupOnTouchListeners(mViewPager);

		ArrayList<String> list=this.getIntent().getStringArrayListExtra("list");
		gettype=this.getIntent().getStringExtra("type"); 
		
		mImageList = new ArrayList<String>();
		if(list!=null){
			mImageList.addAll(list);
		}
		mPosition = 0;

		mViewPager.setCurrentItem(mPosition, false);
		updateShowInfo();
		hideControls();
	}

	private void updateShowInfo() {
		if (mImageList.size() > 0) {
			mPageShwo.setText(String.format("%d/%d", mPosition + 1,
					mImageList.size()));
		}
	}


	private void updateZoomButtonsEnabled() {
		ImageViewTouch imageView = getCurrentImageView();
		if (imageView != null) {
			float scale = imageView.getScale();
			mZoomButtons.setIsZoomInEnabled(scale < imageView.mMaxZoom);
			mZoomButtons.setIsZoomOutEnabled(scale > imageView.mMinZoom);
		}
	}

	private void showControls() {
		AlphaAnimation animation = new AlphaAnimation(0f, 1f);
		animation.setFillAfter(true);
		animation.setDuration(SHOW_HIDE_CONTROL_ANIMATION_TIME);
		mZoomButtons.startAnimation(animation);
		mHeader.startAnimation(animation);

		mControlsShow = true;
		mZoomButtons.setVisibility(View.VISIBLE);
		mHeader.setVisibility(View.VISIBLE);
	}

	private void hideControls() {
		AlphaAnimation animation = new AlphaAnimation(1f, 0f);
		animation.setFillAfter(true);
		animation.setDuration(SHOW_HIDE_CONTROL_ANIMATION_TIME);
		mZoomButtons.startAnimation(animation);
		mHeader.startAnimation(animation);

		mControlsShow = false;
		mZoomButtons.setVisibility(View.GONE);
		mHeader.setVisibility(View.GONE);
	}


	@Override
	public void onStart() {
		super.onStart();
		mPaused = false;
	}

	@Override
	public void onStop() {
		super.onStop();
		mPaused = true;
	}

	private void setupOnTouchListeners(View rootView) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
			mScaleGestureDetector = new ScaleGestureDetector(this,
					new MyOnScaleGestureListener());
		}
		mGestureDetector = new GestureDetector(this, new MyGestureListener());

		OnTouchListener rootListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
			
				// NOTE: gestureDetector may handle onScroll..
				if (!mOnScale) {
					if (!mOnPagerScoll) {
						mGestureDetector.onTouchEvent(event);
					}
				}

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
					if (!mOnPagerScoll) {
						mScaleGestureDetector.onTouchEvent(event);
					}
				}

				ImageViewTouch imageView = getCurrentImageView();
				if(imageView==null || imageView.mBitmapDisplayed==null || imageView.mBitmapDisplayed.getBitmap()==null){
					return false;
				}
				if (!mOnScale) {
					Matrix m = imageView.getImageViewMatrix();
					RectF rect = new RectF(0, 0, imageView.mBitmapDisplayed
							.getBitmap().getWidth(), imageView.mBitmapDisplayed
							.getBitmap().getHeight());
					m.mapRect(rect);
					if (!(rect.right > imageView.getWidth() + 0.1 && rect.left < -0.1)) {
						try {
							mViewPager.onTouchEvent(event);
						} catch (ArrayIndexOutOfBoundsException e) {
						}
					}
				}
				return true;
			}
		};

		rootView.setOnTouchListener(rootListener);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent m) {
		if (mPaused)
			return true;
		// delayHideControls();
		return super.dispatchTouchEvent(m);
	}

	@Override
	protected void onDestroy() {
		ImageViewTouch imageView = getCurrentImageView();
		imageView.recycle();
		imageView.clear();
		super.onDestroy();
	}

	private ImageViewTouch getCurrentImageView() {
		return (ImageViewTouch) mPagerAdapter.views.get((mViewPager
				.getCurrentItem()));
	}

	ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
		public void onPageSelected(int position, int prePosition) {
			ImageViewTouch preImageView = mPagerAdapter.views.get(prePosition);
			if (preImageView != null) {
				preImageView.setImageBitmapResetBase(
						preImageView.mBitmapDisplayed.getBitmap(), true);
			}
			mPosition = position;

			updateZoomButtonsEnabled();
			updateShowInfo();
		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// Log.d(TAG, "onPageScrolled");
			mOnPagerScoll = true;
		}

		public void onPageScrollStateChanged(int state) {
			// Log.d(TAG, "onPageScrollStateChanged: " + state);
			if (state == ViewPager.SCROLL_STATE_DRAGGING) {
				mOnPagerScoll = true;
			} else if (state == ViewPager.SCROLL_STATE_SETTLING) {
				mOnPagerScoll = false;
			} else {
				mOnPagerScoll = false;
			}
		}
	};

	private class MyGestureListener extends
			GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// Log.d(TAG, "gesture onScroll");
			if (mOnScale) {
				return true;
			}
			if (mPaused) {
				return false;
			}
			ImageViewTouch imageView = getCurrentImageView();
			imageView.panBy(-distanceX, -distanceY);
			imageView.center(true, true);

			// 超出边界效果去掉这个
			imageView.center(true, true);

			return true;
		}

		@Override
		public boolean onUp(MotionEvent e) {
			// getCurrentImageView().center(true, true);
			return super.onUp(e);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			if (mControlsShow) {
				hideControls();
			} else {
				updateZoomButtonsEnabled();
				showControls();
			}

			return true;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			if (mPaused) {
				return false;
			}
			ImageViewTouch imageView = getCurrentImageView();
			// Switch between the original scale and 3x scale.
			if (imageView.mBaseZoom < 1) {
				if (imageView.getScale() > 2F) {
					imageView.zoomTo(1f);
				} else {
					imageView.zoomToPoint(3f, e.getX(), e.getY());
				}
			} else {
				if (imageView.getScale() > (imageView.mMinZoom + imageView.mMaxZoom) / 2f) {
					imageView.zoomTo(imageView.mMinZoom);
				} else {
					imageView.zoomToPoint(imageView.mMaxZoom, e.getX(),
							e.getY());
				}
			}

			updateZoomButtonsEnabled();
			return true;
		}
	}

	private class MyOnScaleGestureListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {

		float currentScale;
		float currentMiddleX;
		float currentMiddleY;

		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {

			updateZoomButtonsEnabled();

			final ImageViewTouch imageView = getCurrentImageView();

			Log.d(TAG, "currentScale: " + currentScale + ", maxZoom: "
					+ imageView.mMaxZoom);
			if (currentScale > imageView.mMaxZoom) {
				imageView
						.zoomToNoCenterWithAni(currentScale
								/ imageView.mMaxZoom, 1, currentMiddleX,
								currentMiddleY);
				currentScale = imageView.mMaxZoom;
				imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
						currentMiddleY);
			} else if (currentScale < imageView.mMinZoom) {
				imageView.zoomToNoCenterWithAni(currentScale,
						imageView.mMinZoom, currentMiddleX, currentMiddleY);
				currentScale = imageView.mMinZoom;
				imageView.zoomToNoCenterValue(currentScale, currentMiddleX,
						currentMiddleY);
			} else {
				imageView.zoomToNoCenter(currentScale, currentMiddleX,
						currentMiddleY);
			}

			imageView.center(true, true);

			// NOTE: 延迟修正缩放后可能移动问题
			imageView.postDelayed(new Runnable() {
				public void run() {
					mOnScale = false;
				}
			}, 300);
			// Log.d(TAG, "gesture onScaleEnd");
		}

		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			// Log.d(TAG, "gesture onScaleStart");
			mOnScale = true;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector, float mx, float my) {
			// Log.d(TAG, "gesture onScale");
			ImageViewTouch imageView = getCurrentImageView();
			float ns = imageView.getScale() * detector.getScaleFactor();

			currentScale = ns;
			currentMiddleX = mx;
			currentMiddleY = my;

			if (detector.isInProgress()) {
				imageView.zoomToNoCenter(ns, mx, my);
			}
			return true;
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {
		public Map<Integer, ImageViewTouch> views = new HashMap<Integer, ImageViewTouch>();

		@Override
		public int getCount() {
			// Log.d(TAG, "getCount");
			return mImageList.size();
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// Log.d(TAG, "instantiateItem");
			ImageViewTouch imageView = new ImageViewTouch(
					TouchImageAct.this);
			imageView.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			imageView.setBackgroundColor(Color.BLACK);
			imageView.setFocusableInTouchMode(true);

			try {
				String str=mImageList.get(position);
				if(gettype!=null&&gettype.equals("local")){
					imageView.setImageDrawable(str);
				}
				else{
				    imageView.setImageBitmapResetBase(str);
				}
				((ViewPager) container).addView(imageView);
				views.put(position, imageView);
			} catch (Exception e) {
				e.printStackTrace();
			}
			

			return imageView;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			ImageViewTouch imageView = (ImageViewTouch) object;
			imageView.recycle();
			((ViewPager) container).removeView(imageView);
			views.remove(position);
		}

		@Override
		public void startUpdate(View container) {
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((ImageViewTouch) object);
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}
	}
}
