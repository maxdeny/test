package com.example.goldfoxchina.Bean;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;


/**
 * 重写gallery 的onFling方法，每次滑动一张
 * @author kysl
 *
 */

public class DetialGallery extends Gallery {

	public DetialGallery(Context context) {
		super(context);
	}
	
	/**
	 * 不能少
	 * @param context
	 * @param attrs
	 */
	public DetialGallery(Context context,AttributeSet attrs) {
		super(context,attrs);
	}
	
	

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		return false;
	}



}
