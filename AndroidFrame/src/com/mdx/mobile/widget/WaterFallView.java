package com.mdx.mobile.widget;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.mdx.mobile.adapter.MAdapter;

public class WaterFallView extends LinearLayout {
	private ArrayList<LinearLayout> waterfall_items;
	private int item_width=0;
	public int column_count = 3;// 显示列数
	private int[] bottomIndex;
	private int[] lineIndex;
	private int[] column_height;// 每列的高度
	private SparseArray<Integer>[] pin_mark = null;
	private static final int UPREFRESH = 2;
	private int refreshType = UPREFRESH;
	private View mLoadingView;
	
	@SuppressWarnings("rawtypes")
	private MAdapter madapter;
	
	public WaterFallView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WaterFallView(Context context) {
		super(context);
	}

	@SuppressLint("UseSparseArrays")
    @SuppressWarnings("unchecked")
	public void initView(Context context){
		column_height = new int[column_count];
		pin_mark = new SparseArray[column_count];
		this.lineIndex = new int[column_count];
		this.bottomIndex = new int[column_count];
		for (int i = 0; i < column_count; i++) {
			lineIndex[i] = -1;
			bottomIndex[i] = -1;
			pin_mark[i] = new SparseArray<Integer>();
		}
		waterfall_items = new ArrayList<LinearLayout>();
		this.setOrientation(HORIZONTAL);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(item_width==0){
			readyView();
			fillView(this.madapter);
		}
	}
	
	public void removeImageByNoShow(int sx,int sy,int h,int w) {
		for (LinearLayout ll : waterfall_items) {
			for (int j = 0; j < ll.getChildCount(); j++) {
				View child = ll.getChildAt(j);
				int[] ints = new int[4];
				child.getLocationInWindow(ints);
				if (child.getRight() < sx || child.getBottom() < sy
						|| child.getTop() > sy + h
						|| child.getLeft() > sx + w) {
					if (child instanceof WaterFallItem) {
						((WaterFallItem) child).clearImage();
					}
				}
			}
		}
	}
	
	private void readyView(){
		item_width = (int) ((double)getWidth() / (double)column_count);
		for (int i = 0; i < column_count; i++) {
			LinearLayout itemLayout = new LinearLayout(getContext());
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(item_width,LayoutParams.WRAP_CONTENT);
			itemLayout.setOrientation(LinearLayout.VERTICAL);
			waterfall_items.add(itemLayout);
			this.addView(itemLayout,itemParam);
		}
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void setAdapter(MAdapter adapter){
		this.madapter=adapter;
		for(LinearLayout ll:waterfall_items){
			ll.removeAllViews();
			column_height = new int[column_count];
		}
		fillView(madapter);
	}
	
	@SuppressWarnings({ "rawtypes" })
	private void fillView(MAdapter adapter) {
		if (item_width > 0) {
			removeLoadingView();
			if (adapter != null) {
				for (int i = 0; i < adapter.getCount(); i++) {
					View v = adapter.getView(i, null, null);
					int wspec = MeasureSpec.makeMeasureSpec(item_width,
							MeasureSpec.EXACTLY);
					if (v instanceof WaterFallItem) {
						WaterFallItem wfi = (WaterFallItem) v;
						wfi.makeMeasure(item_width);
					} else {
						v.measure(wspec, 0);
					}
					addView(v);
				}
			}
			addLoadingView();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addAdapter(MAdapter adapter){
		if(this.madapter==null){
			this.madapter=adapter;
		}else{
			this.madapter.AddAll(adapter);
		}
		fillView(adapter);
	}
	
	public void setLoadingView(View loading){
		this.mLoadingView=loading;
		fillView(null);
	}
	
	public void addView(View v) {
		int h = v.getMeasuredHeight();
		int columnIndex =getMinValue(column_height);
		int wspec = MeasureSpec.makeMeasureSpec(item_width, MeasureSpec.EXACTLY);
		v.measure(wspec, 0);
		lineIndex[columnIndex]++;
		column_height[columnIndex] += h;
		SparseArray<Integer> hashMap = pin_mark[columnIndex];
		if (refreshType == UPREFRESH) {
			hashMap.put(lineIndex[columnIndex], column_height[columnIndex]);// 第index个view所在的高度
			waterfall_items.get(columnIndex).addView(v);
		} else {
			for (int i = lineIndex[columnIndex] - 1; i >= 0; i--) {
				hashMap.put(i + 1, hashMap.get(i) + h);
			}
			hashMap.put(0, h);
			waterfall_items.get(columnIndex).addView(v, 0);
		}
		bottomIndex[columnIndex] = lineIndex[columnIndex];
	}
	
	public void removeLoadingView(){
		if(this.mLoadingView !=null && mLoadingView.getParent()!=null){
			if(mLoadingView.getParent() instanceof ViewGroup){
				((ViewGroup)mLoadingView.getParent()).removeView(mLoadingView);
			}
		}
	}
	
	private void addLoadingView(){
		if(this.mLoadingView !=null && mLoadingView.getParent()==null){
			this.addView(mLoadingView);
		}
	}
	
	
	private int getMinValue(int[] array) {
		int m = 0;
		int length = array.length;
		for (int i = 0; i < length; ++i) {
			if (array[i] < array[m]) {
				m = i;
			}
		}
		return m;
	}

	public MAdapter<?> getMadapter() {
		return madapter;
	}
}
