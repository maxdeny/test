package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.mdx.mobile.widget.CurrentView;
import com.wjwl.mobile.taocz.R;
public class CurrView extends ImageView implements CurrentView {
	protected View mParentView;
	private int currentId;
	private double left = 0, step = 0, leftc, tleftc;
	private int width = 0, tleft = 0;

	public CurrView(Context context) {
		super(context);
		init();
	}

	public CurrView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	protected void init() {
		if (getParent() instanceof View) {
			mParentView = ((View) getParent()).findViewById(R.frame.toolbar);
		}
	}

	public void moveNow(int nind, int count, int move_direction,
			int ncurrentId, int currentId, Object currentObject) {
		if (currentId == this.currentId) {
			left += step;
			leftc += step;
			if (nind == count) {
				left = tleft;
				leftc = tleftc;
			}
		} else {
			if (mParentView == null) {
				init();
			}
			View cv = mParentView.findViewById(currentId);
			if (cv != null) {
				width = cv.getWidth() + 6;
				step = (int) ((cv.getLeft() - left) * 1d) / (count * 1d);
				tleft = cv.getLeft() - 3;
				tleftc = (cv.getLeft() + cv.getWidth() / 2) > 0 ? (cv.getLeft() + cv
						.getWidth() / 2) : tleftc;
			}
			this.currentId = currentId;
		}
		this.postInvalidate();
	}

	public void stn() {
		View cv = mParentView.findViewById(currentId);
		if (cv != null) {
			width = cv.getWidth() + 10;
			left = cv.getLeft() - 5;
			leftc = cv.getLeft() + cv.getWidth() / 2;
		}
	}

	public void resetWidth() {
		this.postDelayed(new Runnable() {
			public void run() {
				LayoutParams layout = getLayoutParams();
				layout.width = mParentView.getWidth();
				setLayoutParams(layout);
				stn();
			}
		}, 50);
	}

	@Override
	public void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			super.onDraw(canvas);
		} else {
			if (drawable instanceof NinePatchDrawable) {
				drawable.setBounds((int) left, getPaddingTop(), (int) left
						+ width, this.getBottom() - getPaddingBottom());
				drawable.draw(canvas);
			} else if (drawable instanceof BitmapDrawable) {
				BitmapDrawable btd = (BitmapDrawable) drawable;
				int bw = btd.getBitmap().getWidth(), hf = bw / 2;
				drawable.setBounds((int) leftc - hf, getPaddingTop(),
						(int) leftc - hf + bw, this.getBottom()
								- getPaddingBottom());
				drawable.draw(canvas);
			}
		}
		if (width <= 6 && mParentView != null) {
			this.postDelayed(new Runnable() {
				public void run() {
					LayoutParams layout = getLayoutParams();
					layout.width = mParentView.getWidth();
					setLayoutParams(layout);
					stn();
					invalidate();
				}
			}, 50);
		}
	}

}
