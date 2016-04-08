package com.wjwl.mobile.taocz.images.touch;

import java.util.ArrayList;

import android.view.MotionEvent;

public class Points {
	private ArrayList<Point> points=new ArrayList<Point>();
	
	public void add(Point point){
		points.add(0, point);
	}
	
	public void add(MotionEvent event){
		points.add(0,new Point(event));
	}
	
	public void clear(){
		points.clear();
	}
	
	public int getSpeed(){
		int i=0;
		double length=0;
		for(i=1; i<points.size() && i<8;i++){
			Point pn=points.get(i-1),pl=points.get(i);
			length+=(pn.y-pl.y)/(pn.tim-pl.tim)*100;
		}
		return (int) (length/i);
	}
	
	public int getSpeedx(){
		int i=0;
		double length=0;
		for(i=1; i<points.size() && i<8;i++){
			Point pn=points.get(i-1),pl=points.get(i);
			length+=(pn.x-pl.x)/(pn.tim-pl.tim)*100;
		}
		return (int) (length/i);
	}
	
	public Point get(){
		if(points.size()>0){
			return points.get(0);
		}
		return null;
	}
	
	public Point getL(){
		if(points.size()>1){
			return points.get(1);
		}
		return get();
	}
	
	public Point get(int ind){
		if(ind<points.size()){
			return points.get(ind);
		}
		return null;
	}
	
	public int getSize(){
		return points.size();
	}
}
