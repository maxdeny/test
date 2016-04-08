package com.wjwl.mobile.taocz.images;
import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

public class ImageViewTouch extends MImageView {
	
	@SuppressWarnings("unused")
	private static final String TAG = "ImageViewTouchBase";

	// This is the base transformation which is used to show the image
	// initially. The current computation for this shows the image in
	// it's entirety, letterboxing as needed. One could choose to
	// show the image as cropped instead.
	//
	// This matrix is recomputed when we go from the thumbnail image to
	// the full size image.
	protected Matrix mBaseMatrix = new Matrix();

	// This is the supplementary transformation which reflects what
	// the user has done in terms of zooming and panning.
	//
	// This matrix remains the same when we go from the thumbnail image
	// to the full size image.
	protected Matrix mSuppMatrix = new Matrix();

	// This is the final matrix which is computed as the concatentation
	// of the base matrix and the supplementary matrix.
	protected final Matrix mDisplayMatrix = new Matrix();

	// Temporary buffer used for getting the values out of a matrix.
	private final float[] mMatrixValues = new float[9];

	// The current bitmap being displayed.
	public RotateBitmap mBitmapDisplayed = new RotateBitmap(null);

	int mThisWidth = -1, mThisHeight = -1;

	public float mMaxZoom;
	public float mMinZoom;
	public float mBaseZoom;

	// ImageViewTouchBase will pass a Bitmap to the Recycler if it has finished
	// its use of that Bitmap.
	public interface Recycler {
		public void recycle(Bitmap b);
	}

	public void setRecycler(Recycler r) {
		mRecycler = r;
	}

	private Recycler mRecycler;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mThisWidth = right - left;
		mThisHeight = bottom - top;
		Runnable r = mOnLayoutRunnable;
		if (r != null) {
			mOnLayoutRunnable = null;
			r.run();
		}
		if (mBitmapDisplayed.getBitmap() != null) {
			getProperBaseMatrix(mBitmapDisplayed, mBaseMatrix);
			setImageMatrix(getImageViewMatrix());
		}
	}

	protected Handler mHandler = new Handler();

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap, 0);
	}

	private void setImageBitmap(Bitmap bitmap, int rotation) {
		super.setImageBitmap(bitmap);
		Drawable d = getDrawable();
		if (d != null) {
			d.setDither(true);
		}

		Bitmap old = mBitmapDisplayed.getBitmap();
		mBitmapDisplayed.setBitmap(bitmap);
		mBitmapDisplayed.setRotation(rotation);

		if (old != null && old != bitmap && mRecycler != null) {
			//mRecycler.recycle(old);
		}
	}

	public void clear() {
		setImageBitmapResetBase(null, true);
	}

	private Runnable mOnLayoutRunnable = null;

	public void setImageBitmapResetBase(String url) {
		this.setObj(url);
	}
	
	public void setImageDrawable(String url) {
		this.setImage(Drawable.createFromPath(new File(url).getAbsolutePath()));
	}
	
	
	@Override
	public void onDraw(Canvas canvas){
		if(obj!=null){
			if(drawable==null){
				drawable=imageload.get(obj);
				if(drawable==null && !loaded && !loading){
					loading=true;
					loadimage(obj);
				}
				if(drawable!=null){
					setImageBitmapResetBase(((BitmapDrawable)drawable).getBitmap(),true);
				}else{
					setImageBitmapResetBase(BitmapFactory.decodeResource(getResources(),R.drawable.default_h_bg ),true);
				}
			}
		}
		super.onDraws(canvas);
	}
	
	public void recycle(){
		drawable=null;
		loaded=false;
		clear();
	}
	
	public void setImageBitmapResetBase(final Bitmap bitmap,final boolean resetSupp) {
		setImageRotateBitmapResetBase(new RotateBitmap(bitmap), resetSupp);
	}

	public void setImageRotateBitmapResetBase(final RotateBitmap bitmap,
			final boolean resetSupp) {
		final int viewWidth = getWidth();

		if (viewWidth <= 0) {
			mOnLayoutRunnable = new Runnable() {
				public void run() {
					setImageRotateBitmapResetBase(bitmap, resetSupp);
				}
			};
			return;
		}

		if (bitmap.getBitmap() != null) {
			getProperBaseMatrix(bitmap, mBaseMatrix);
			setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
		} else {
			mBaseMatrix.reset();
			setImageBitmap(null);
		}

		if (resetSupp) {
			mSuppMatrix.reset();
		}
		setImageMatrix(getImageViewMatrix());
		mMaxZoom = maxZoom();
		mMinZoom = minZoom();
		mBaseZoom = getScale(mBaseMatrix);
	}

	// Center as much as possible in one or both axis. Centering is
	// defined as follows: if the image is scaled down below the
	// view's dimensions then center it (literally). If the image
	// is scaled larger than the view and is translated out of view
	// then translate it back into view (i.e. eliminate black bars).
	public void center(boolean horizontal, boolean vertical) {
		centerCharge(horizontal, vertical, false);
	}

	private void centerCharge(boolean horizontal, boolean vertical,
			boolean hasAni) {
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		Matrix m = getImageViewMatrix();

		RectF rect = new RectF(0, 0, mBitmapDisplayed.getBitmap().getWidth(),
				mBitmapDisplayed.getBitmap().getHeight());

		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}

		postTranslate(deltaX, deltaY);

		if (hasAni) {
		} else {
			setImageMatrix(getImageViewMatrix());
		}
	}

	protected void centerWithAni(boolean horizontal, boolean vertical) {
		centerCharge(horizontal, vertical, true);
	}

	public ImageViewTouch(Context context) {
		super(context);
		init();
	}

	public ImageViewTouch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setScaleType(ImageView.ScaleType.MATRIX);
	}

	public float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(mMatrixValues);
		return mMatrixValues[whichValue];
	}

	public float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	public float getScale() {
		return getScale(mSuppMatrix);
	}

	public void getProperBaseMatrix(RotateBitmap bitmap, Matrix matrix) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();

		float w = bitmap.getWidth();
		float h = bitmap.getHeight();
		matrix.reset();

		float widthScale = Math.min(viewWidth / w, 3.0f);
		float heightScale = Math.min(viewHeight / h, 3.0f);

		float scale = Math.min(widthScale, heightScale);

		matrix.postConcat(bitmap.getRotateMatrix());

		matrix.postScale(scale, scale);

		matrix.postTranslate((viewWidth - w * scale) / 2F, (viewHeight - h
				* scale) / 2F);
	}

	public Matrix getImageViewMatrix() {
		mDisplayMatrix.set(mBaseMatrix);
		mDisplayMatrix.postConcat(mSuppMatrix);
		return mDisplayMatrix;
	}

	static final float SCALE_RATE = 1.25F;

	protected float maxZoom() {
		if (mBitmapDisplayed.getBitmap() == null) {
			return 1F;
		}

		float fw = (float) mBitmapDisplayed.getWidth() / (float) mThisWidth;
		float fh = (float) mBitmapDisplayed.getHeight() / (float) mThisHeight;
		float max = Math.max(fw, fh) * 4;
		return max;
	}

	protected float minZoom() {
		float baseScale = getScale(mBaseMatrix);
		if (baseScale < 1) {
			return 1F;
		} else {
			return 1F / baseScale;
		}
	}

	protected void zoomTo(float scale, float centerX, float centerY) {
		if (scale > mMaxZoom) {
			scale = mMaxZoom;
		}

		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	protected void zoomTo(final float scale, final float centerX,
			final float centerY, final float durationMs) {
		final float incrementPerMs = (scale - getScale()) / durationMs;
		final float oldScale = getScale();
		final long startTime = System.currentTimeMillis();

		mHandler.post(new Runnable() {
			public void run() {
				long now = System.currentTimeMillis();
				float currentMs = Math.min(durationMs, now - startTime);
				float target = oldScale + (incrementPerMs * currentMs);
				zoomTo(target, centerX, centerY);

				if (currentMs < durationMs) {
					mHandler.post(this);
				}
			}
		});
	}

	public void zoomTo(float scale) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		zoomTo(scale, cx, cy);
	}

	public void zoomToPoint(float scale, float pointX, float pointY) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		panBy(cx - pointX, cy - pointY);
		zoomTo(scale, cx, cy);
	}

	public void zoomToNoCenter(float scale, float centerX, float centerY) {
		// if (scale > mMaxZoom) {
		// scale = mMaxZoom;
		// }

		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
	}

	public class MatrixTransformAnimation extends Animation {
		Matrix mFrom;
		Matrix mTo;

		public MatrixTransformAnimation(Matrix from, Matrix to) {
			mFrom = from;
			mTo = to;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			Matrix matrix = t.getMatrix();
			// matrix.set(mMatrix);
			float from = getValue(mFrom, Matrix.MSCALE_X);
			float to = getValue(mTo, Matrix.MSCALE_X);
			matrix.setScale(from / to, from / to);
		}
	}

	public void zoomToNoCenterValue(float scale, float centerX, float centerY) {
		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		getImageViewMatrix();
	}

	public void zoomToNoCenterWithAni(float scale, float toScale,
			float centerX, float centerY) {
		ScaleAnimation animation = new ScaleAnimation(scale, toScale, scale,
				toScale, centerX, centerY);
		animation.setDuration(300);
		animation.setAnimationListener(new ScaleAnimation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// setImageMatrix(getImageMatrix());
			}
		});
		startAnimation(animation);

		// float oldScale = getScale();
		// float deltaScale = scale / oldScale;
		// mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		// Animation animation = new MatrixTransformAnimation(
		// getImageViewMatrix(), getImageViewMatrix());
		// animation.setDuration(300);
		// startAnimation(animation);
	}

	public void zoomIn() {
		zoomIn(SCALE_RATE);
	}

	public void zoomOut() {
		zoomOut(SCALE_RATE);
	}

	public void zoomIn(float rate) {
		if (getScale() >= mMaxZoom) {
			return; // Don't let the user zoom into the molecular level.
		}
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		mSuppMatrix.postScale(rate, rate, cx, cy);
		setImageMatrix(getImageViewMatrix());
	}

	protected void zoomOut(float rate) {
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		// Zoom out to at most 1x.
		Matrix tmp = new Matrix(mSuppMatrix);
		tmp.postScale(1F / rate, 1F / rate, cx, cy);

		if (getScale(tmp) < mMinZoom) {
			mSuppMatrix.setScale(mMinZoom, mMinZoom, cx, cy);
		}
		// if (getScale(tmp) < 1F) {
		// mSuppMatrix.setScale(1F, 1F, cx, cy);
		// }
		else {
			mSuppMatrix.postScale(1F / rate, 1F / rate, cx, cy);
		}
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	protected void postTranslate(float dx, float dy) {
		mSuppMatrix.postTranslate(dx, dy);
	}

	public void panBy(float dx, float dy) {
		postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());
	}
}