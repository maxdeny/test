package com.wjwl.mobile.taocz.widget;
import com.mdx.mobile.manage.MWindows;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

public class LoadIng extends android.view.View{
	private Drawable loading_s,loading_n;
	private int ind=0,ov=1;
	private boolean runing=false;
	private Runnable runable=new Runnable() {
		public void run() {
			ind+=ov;
			if(ind==4){
				ov=-1;
			}
			if(ind==0){
				ov=1;
			}
			LoadIng.this.invalidate();
			runing=false;
		}
	};
	
	
	public LoadIng(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
 
	public LoadIng(Context context) {
		super(context);
		init(context);
	}
	public LoadIng(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	

	private void init(Context context){
		loading_s=context.getResources().getDrawable(R.drawable.loading_s);
		loading_n=context.getResources().getDrawable(R.drawable.loading_n);
		this.setLayoutParams(new LayoutParams(100, 7));
	}

	@Override
	public void onDraw(Canvas canvas){
		int top=0,width=0;
		for(int i=0;i<5;i++){
			top=ind==i?0:2;
			width=ind==i?14:10;
			Drawable drawable=ind==i?loading_s:loading_n;
			drawable.setBounds(i*30, top, width+i*30, width+top);
			drawable.draw(canvas);
		}
		if(!runing){
			int ll[]=new int[2];
    		this.getLocationOnScreen(ll);
    		int l=ll[0],t=ll[1];
    		int w=this.getWidth(),h=this.getHeight();
    		if(t+h<0 || t>MWindows.getHeight(this.getContext()) || l+w<0 || l>MWindows.getWidth(this.getContext())){
    			return;
    		}else{
    			this.runing=true;
    			this.postDelayed(runable, 200);
    		}
		}
	}
}
