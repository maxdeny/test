package com.mdx.mobile.widget;

import java.util.List;

public interface DragCurr {
	public void setLength(List<Integer> lengs);
	
	public void setWidth(int width);
	
	public void onScroll(int compCurr,int compLenth,int curr,int width,int movelength);
	
	public void setInd(int ind);
	
	public void setSize(int sizes);
}
