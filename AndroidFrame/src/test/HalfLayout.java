package test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HalfLayout extends LinearLayout{

	public HalfLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public HalfLayout(Context context) {
		super(context);
	}

	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = (int) (MeasureSpec.getSize(widthMeasureSpec))/getChildCount(); 
		for(int i=0;i<getChildCount();i++){
			View view=getChildAt(i);
			ViewGroup.LayoutParams layout=view.getLayoutParams();
			layout.height=width;
			layout.width=width;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	public void onLayout(boolean changed,int l,int t,int r,int b){
		super.onLayout(changed, l, t, r, b);
	}
}
