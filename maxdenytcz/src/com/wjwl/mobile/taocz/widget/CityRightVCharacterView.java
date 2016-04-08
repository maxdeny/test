package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CityRightVCharacterView extends View {
	private String[] az = null;

	public void setB(String[] b) {
		this.az = b;
	}

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;

	public CityRightVCharacterView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public CityRightVCharacterView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CityRightVCharacterView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#00000000"));//按下背景发生变化
		}
		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / az.length;
		for (int i = 0; i < az.length; i++) {
			paint.setColor(Color.BLACK);
			paint.setTextSize(singleHeight-2);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(az[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(az[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * az.length);

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			showBkg = true;
			if (oldChoose != c && listener != null) {
				if (c >0 && c < az.length) { //如果第一个字母是#，无效点击的话，条件变为c>0
					listener.onTouchingLetterChanged(az[c]);
					choose = c;
					invalidate();
				}
			}

			break;
		case MotionEvent.ACTION_MOVE:
			if (oldChoose != c && listener != null) {
				if (c >0 && c < az.length) {  //如果第一个字母是#，无效点击的话，条件变为c>0
					listener.onTouchingLetterChanged(az[c]);
					choose = c;
					invalidate();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			showBkg = false;
			choose = -1;
			invalidate();
			break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(
			OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}