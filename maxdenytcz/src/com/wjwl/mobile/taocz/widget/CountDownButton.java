package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.Button;

public class CountDownButton extends Button {
	public Runnable mTicker;
	public Handler mHandler;

	private boolean mTickerStopped = false;

	private OnCountDownListener onCountDownListener;// 监听回调
	private int count = 10;// 倒计时的步数
	private CharSequence text;// 原始文字

	public CountDownButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CountDownButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CountDownButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		text = getText();
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		mTickerStopped = true;
	}

	@Override
	public void onAttachedToWindow() {
		mTickerStopped = false;
		super.onAttachedToWindow();
		mHandler = new Handler();

		/**
		 * requests a tick on the next hard-second boundary
		 */
		mTicker = new Runnable() {
			public void run() {
				if (mTickerStopped)
					return;
				if (count <= 0) {
					if (onCountDownListener != null)
						onCountDownListener.onFinish();
					return;
				}
				count--;
				setText(text + "(" + count + ")");
				if (onCountDownListener != null)
					onCountDownListener.onTick();
				invalidate();
				long now = SystemClock.uptimeMillis();
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
		mTicker.run();
	}

	public interface OnCountDownListener {
		public void onFinish();

		public void onTick();
	}

	public void setOnCountDownListener(OnCountDownListener onCountDownListener) {
		this.onCountDownListener = onCountDownListener;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		if (count < 0) {
			this.count = 0;
			return;
		}
		this.count = count;
	}
}