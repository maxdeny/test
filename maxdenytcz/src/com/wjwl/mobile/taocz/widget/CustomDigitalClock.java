/*
 * Copyright (C) 2012 The * Project
 * All right reserved.
 * Version 1.00 2012-2-11
 * Author veally@foxmail.com
 */
package com.wjwl.mobile.taocz.widget;

import java.util.Calendar;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.DigitalClock;

import com.wjwl.mobile.taocz.F;

/**
 *  倒计时控件
 * @author 
 */
public class CustomDigitalClock extends DigitalClock {

	Calendar mCalendar;
	private final static String m12 = "h:mm aa";
	private final static String m24 = "k:mm";
	private FormatChangeObserver mFormatChangeObserver;
	private boolean istext=false;
	private Runnable mTicker;
	private Handler mHandler;
	private long endTime;
	private ClockListener mClockListener;
	private long mnow;

	private boolean mTickerStopped = false;

	@SuppressWarnings("unused")
	private String mFormat;
	String time;
	String[] data;

	public CustomDigitalClock(Context context) {
		super(context);
		initClock(context);
	}

	public CustomDigitalClock(Context context, AttributeSet attrs) {
		super(context, attrs);
		initClock(context);
	}

	private void initClock(Context context) {

		if (mCalendar == null) {
			mCalendar = Calendar.getInstance();
		}

		mFormatChangeObserver = new FormatChangeObserver();
		getContext().getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, mFormatChangeObserver);

		setFormat();
	}

	@Override
	protected void onAttachedToWindow() {
		mTickerStopped = false;
		super.onAttachedToWindow();
		if(mHandler!=null){
			mTicker.run();
			return;
		}
		mHandler = new Handler();

		/**
		 * requests a tick on the next hard-second boundary
		 */
		mTicker = new Runnable() {
			public void run() {
				if (mTickerStopped)
					return;
//				long currentTime = System.currentTimeMillis();//注意：系统时间是变动的
				long currentTime = getNow();
				if (currentTime / 1000 == endTime / 1000 - 5 * 60) {
					mClockListener.remainFiveMinutes();
				}
				long distanceTime = endTime - currentTime;
				distanceTime /= 1000;
				if (distanceTime == 0) {
					istext=true;
					setText(Html.fromHtml("<font color=\"#ff0000\">0</font></font><font color=\"#ffffff\">天</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">时</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">分</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">秒</font>"));
					onDetachedFromWindow();
					if(mClockListener!=null){
						mClockListener.timeEnd();
					}
				} else if (distanceTime < 0) {
					istext=true;
					setText(Html.fromHtml("<font color=\"#ff0000\">0</font></font><font color=\"#ffffff\">天</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">时</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">分</font><font color=\"#ff0000\">0</font><font color=\"#ffffff\">秒</font>"));
					onDetachedFromWindow();
					if(mClockListener!=null){
						mClockListener.timeEnd();
					}
				} else {
					data=dealTime(distanceTime).split(":");
//					Html.fromHtml("<font color=\"#333333\">买家收货信息：</font><font color=\"#ff0000\">"	+ F.getAddress(trade) + "</font>")
					istext=true;
					setText(Html.fromHtml("<font color=\"#ffffff\">剩余</font><font color=\"#ff0000\">"+data[0]+"</font><font color=\"#ffffff\">天</font><font color=\"#ff0000\">"+data[1]+"</font><font color=\"#ffffff\">时</font><font color=\"#ff0000\">"+data[2]+"</font><font color=\"#ffffff\">分</font><font color=\"#ff0000\">"+data[3]+"</font><font color=\"#ffffff\">秒</font>"));
					
//					setText("秒杀将于"+data[0]+"天"+data[1]+"时"+data[2]+"分"+data[3]+"秒");
				}
				invalidate();
				mClockListener.secondChange(mnow);
				long now = SystemClock.uptimeMillis();//开机时间
				long next = now + (1000 - now % 1000);
				mHandler.postAtTime(mTicker, next);
			}
		};
		mTicker.run();
	}
	
	@Override
	public synchronized void setText(CharSequence text, BufferType type) {
		if(istext){
			istext=false;
			super.setText(text, type);
		}
	}

	private void setTimeText(long distanceTime){
		distanceTime /= 1000;
		if (distanceTime == 0) {
			istext=true;
			setText(Html.fromHtml("<font color=\"#ff0000\">0</font></font><font color=\"#333333\">天</font><font color=\"#ff0000\">0</font><font color=\"#333333\">时</font><font color=\"#ff0000\">0</font><font color=\"#333333\">分</font><font color=\"#ff0000\">0</font><font color=\"#333333\">秒</font>"));
			onDetachedFromWindow();
		} else if (distanceTime < 0) {
			istext=true;
			setText(Html.fromHtml("<font color=\"#ff0000\">0</font></font><font color=\"#333333\">天</font><font color=\"#ff0000\">0</font><font color=\"#333333\">时</font><font color=\"#ff0000\">0</font><font color=\"#333333\">分</font><font color=\"#ff0000\">0</font><font color=\"#333333\">秒</font>"));
			onDetachedFromWindow();
		} else {
			data=dealTime(distanceTime).split(":");
			istext=true;
			setText(Html.fromHtml("<font color=\"#333333\">剩余</font><font color=\"#ff0000\">"+data[0]+"</font><font color=\"#333333\">天</font><font color=\"#ff0000\">"+data[1]+"</font><font color=\"#333333\">时</font><font color=\"#ff0000\">"+data[2]+"</font><font color=\"#333333\">分</font><font color=\"#ff0000\">"+data[3]+"</font><font color=\"#333333\">秒</font>"));
			
//			setText("秒杀将于"+data[0]+"天"+data[1]+"时"+data[2]+"分"+data[3]+"秒");
		}
	}
	/**
	 * deal time string
	 * 
	 * @param time
	 * @return
	 */
	public static String dealTime(long time) {
		StringBuffer returnString = new StringBuffer();
		long day = time / (24 * 60 * 60);
		long hours = (time % (24 * 60 * 60)) / (60 * 60);
		long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
		long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
		String dayStr = String.valueOf(day);
		String hoursStr = timeStrFormat(String.valueOf(hours));
		String minutesStr = timeStrFormat(String.valueOf(minutes));
		String secondStr = timeStrFormat(String.valueOf(second));

		returnString.append(dayStr).append(":").append(hoursStr).append(":").append(minutesStr).append(":").append(secondStr);
		return returnString.toString();
	}

	/**
	 * format time
	 * 
	 * @param timeStr
	 * @return
	 */
	private static String timeStrFormat(String timeStr) {
		switch (timeStr.length()) {
		case 1:
			timeStr = "0" + timeStr;
			break;
		}
		return timeStr;
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
//		mTickerStopped = true;
	}

	/**
	 * Clock end time from now on.
	 * 
	 * @param endTime
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
		long time=this.endTime-this.getNow();
		setTimeText(time);
	}

	/**
	 * Pulls 12/24 mode from system settings
	 */
	private boolean get24HourMode() {
		return android.text.format.DateFormat.is24HourFormat(getContext());
	}

	private void setFormat() {
		if (get24HourMode()) {
			mFormat = m24;
		} else {
			mFormat = m12;
		}
	}

	private class FormatChangeObserver extends ContentObserver {
		public FormatChangeObserver() {
			super(new Handler());
		}

		@Override
		public void onChange(boolean selfChange) {
			setFormat();
		}
	}

	public void setClockListener(ClockListener clockListener) {
		this.mClockListener = clockListener;
	}

	public interface ClockListener{
		void timeEnd();
		void remainFiveMinutes();
		void secondChange(long now);
	}

	public void setNow(long now){
		this.mnow=now;
		long time=this.endTime-this.getNow();
		setTimeText(time);
		mTickerStopped = false;
	}
	
	public long getNow(){
		return mnow*1000+(System.currentTimeMillis()-F.now);
	}
	public void markinit(){
		mTickerStopped = false;
	}
	public long getNow1(){
		return mnow+(System.currentTimeMillis()-F.now)/1000;
	}
}