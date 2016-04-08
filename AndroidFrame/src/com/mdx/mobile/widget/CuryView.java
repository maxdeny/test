package com.mdx.mobile.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CuryView extends View implements DragCurr{
	private int size = 3;
	private int step = 30;
	private float radius = 5,linw=2,part=0;
	private int w, h;
	private Paint paint = new Paint(3);
	private int cur=0;
	private int currIcon=0,noCurrIcon=0,moveIcon=0,background=0;
	private Runnable runable;
	private Rect rect=new Rect();

	public CuryView(Context context) {
		super(context);
	}

	public CuryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		w = r - l;
		h = b - t;
	}
	
	public void setCur(int cur){
		this.cur=cur;
		part=0;
		this.invalidate();
	}
	
	public void setSize(int size){
		this.size=size;
	}
	
	public void onDraw(Canvas canvas) {
		if(size<=0){
			return;
		}
		int hw = (size - 1) * step;
		int s = w / 2 - hw / 2, t = h / 2;
		if(background!=0){
			Drawable draw=getResources().getDrawable(background);
			if(draw!=null){
				rect.set(s-(int)(radius*2),t-(int)(radius*2),s+hw+(int)(radius*2),t+(int)(radius*2));
				draw.setBounds(rect);
				draw.draw(canvas);
			}
		}
		paint.setColor(0xff0000ff);
		paint.setStrokeWidth(linw);
		for (int i = 0; i < size; i++) {
			drawO(s+i*step,t,canvas);
		}
		drawC(s+cur*step+part*step,t,canvas);
		if(runable!=null){
			runable.run();
		}
	}
	
	private void drawC(float x,float y,Canvas canvas){
		if(part==0){
			if(currIcon==0){
				canvas.drawCircle(x, y, radius-2, paint);
			}else{
				drawBitmap(currIcon,x,y,canvas);
			}
		}else{
			if(moveIcon==0){
				canvas.drawCircle(x, y, radius, paint);
			}else{
				drawBitmap(moveIcon,x,y,canvas);
			}
		}
		
	}
	
	private void drawO(float x,float y,Canvas canvas){
		if(noCurrIcon==0){
			paint.setStyle(Paint.Style.STROKE);
			canvas.drawCircle(x, y, radius, paint);
			paint.setStyle(Paint.Style.FILL);
		}else{
			drawBitmap(noCurrIcon,x,y,canvas);
		}
	}
	
	private void drawBitmap(int res,float x,float y,Canvas canvas){
		drawBitamp(res, x, y, radius*2, radius*2, canvas);
	}
	
	private void drawBitamp(int res,float x,float y,float w,float h,Canvas canvas){
		BitmapDrawable bitmap=(BitmapDrawable) getResources().getDrawable(res);
		int bw=bitmap.getBitmap().getWidth(),bh=bitmap.getBitmap().getHeight();
		if(w==0){
			w=bw;
		}
		if(h==0){
			h=bh;
		}
		float l=x-w/2,t=y-h/2;
		bitmap.setBounds(new Rect((int)l,(int)t,(int)(l+w),(int)(t+h)));
		bitmap.draw(canvas);
	}

	public void setCurrIcon(int currIcon) {
		this.currIcon = currIcon;
	}

	public void setNoCurrIcon(int noCurrIcon) {
		this.noCurrIcon = noCurrIcon;
	}

	public void setMoveIcon(int moveIcon) {
		this.moveIcon = moveIcon;
	}
	
	public void setBackground(int res){
		this.background=res;
	}

	public void setRunable(Runnable runable) {
		this.runable = runable;
	}

	public void setStep(int step) {
		this.step = step;
		this.invalidate();
	}

	public void setRadius(float radius) {
		this.radius = radius;
		this.invalidate();
	}

	public void setLength(List<Integer> lengs) {
		
	}

	public void setWidth(int width) {
		
	}

	public void onScroll(int compCurr, int compLenth, int curr, int width,int movelength) {
		if(Math.abs(compCurr)==Math.abs(compLenth)){
			this.cur+=compCurr/Math.abs(compCurr);
			part=0;
		}else{
			part=(compCurr*1f)/(compLenth*1f);
		}
		this.invalidate();
	}

	public void setInd(int ind) {
		setCur(ind);
	}
	
}
