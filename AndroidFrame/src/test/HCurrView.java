package test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.mdx.android.frame.R;
import com.mdx.mobile.widget.CurrentView;
public class HCurrView extends ImageView implements CurrentView {
	protected View mParentView;
	private int currentId;
	private double top = 0, step = 0, topc, ttopc;
	private int height = 0, ttop = 0;

	public HCurrView(Context context) {
		super(context);
		init();
	}

	public HCurrView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	protected void init() {
		if (getParent() instanceof View) {
			mParentView = ((View) getParent()).findViewById(R.frame.toolbar);
		}
	}

	public void moveNow(int nind, int count, int move_direction,int ncurrentId, int currentId, Object currentObject) {
		if (currentId == this.currentId) {
			top += step;
			topc += step;
			if (nind == count) {
				top = ttop;
				topc = ttopc;
			}
		} else {
			if (mParentView == null) {
				init();
			}
			View cv = mParentView.findViewById(currentId);
			if (cv != null) {
				height = cv.getHeight() + 6;
				step = (int) ((cv.getTop()- top) * 1d) / (count * 1d);
				ttop = cv.getTop() - 3;
				ttopc = (cv.getTop() + cv.getHeight() / 2) > 0 ? (cv.getTop() + cv
						.getHeight() / 2) : ttopc;
			}
			this.currentId = currentId;
		}
		this.postInvalidate();
	}

	public void stn() {
		View cv = mParentView.findViewById(currentId);
		if (cv != null) {
			height = cv.getHeight() + 10;
			top = cv.getTop() - 5;
			topc = cv.getTop() + cv.getHeight() / 2;
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
				drawable.setBounds(getPaddingLeft(),(int) top, (int) getRight()-getPaddingRight()
						, (int)top+height);
				drawable.draw(canvas);
			} else if (drawable instanceof BitmapDrawable) {
				BitmapDrawable btd = (BitmapDrawable) drawable;
				int bw = btd.getBitmap().getHeight(), hf = bw / 2;
				drawable.setBounds(getPaddingLeft(),(int) topc - hf,
						 this.getRight()- getPaddingRight(),(int) topc - hf + bw);
				drawable.draw(canvas);
			}
		}
		if (height <= 6 && mParentView != null) {
			onrunable();
		}
	}
	
	public void onrunable(){
		this.postDelayed(new Runnable() {
			public void run() {
				LayoutParams layout = getLayoutParams();
				layout.height = mParentView.getHeight();
				setLayoutParams(layout);
				stn();
				invalidate();
			}
		}, 50);
	}

}
