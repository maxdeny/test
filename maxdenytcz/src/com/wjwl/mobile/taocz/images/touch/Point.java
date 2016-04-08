package com.wjwl.mobile.taocz.images.touch;

import android.view.MotionEvent;

public class Point {
	public float x,y,pressure,size;
	public long tim=0;
	public int actiontype=0;
	public Point(){}
	
	public Point(MotionEvent event){
		set(event);
	}
	
	public void set(MotionEvent event){
		x=event.getX();
		y=event.getY();
		pressure=event.getPressure();
		size=event.getSize();
		tim=event.getEventTime();
		actiontype=event.getAction();
	}
	
	
	public void set(float x ,float y){
		this.x=x;
		this.y=y;
	}
	
	public void clear(){
		x=0;
		y=0;
		tim=0;
		pressure=0;
		size=0;
	}
}
