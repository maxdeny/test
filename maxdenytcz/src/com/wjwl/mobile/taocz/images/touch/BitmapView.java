package com.wjwl.mobile.taocz.images.touch;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BitmapView extends View {
	private Bitmap bitmap;
	private String path;
	private double zoomsize=0,minzoomsize;
	private Point zoompoint=new Point(),screenpoint=new Point();
	private Area photo=new Area(0,0,0,0);
	private Paint paint=new Paint();
	private float lastleng;
	private boolean end=false;
	private int speedx=0,speedy=0;
	private Thread autothread;
	private Points points = new Points();
	
	public BitmapView(Context context,String path) {
		super(context);
		this.path=path;
		this.load(context);
	}

	private void load(Context context){
		this.setBackgroundColor(0xff000000);
		bitmap=BitmapFactory.decodeFile(path);
	}
	
	@Override
	public void onDraw(Canvas canvas){
		drawbitmap(canvas);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		stopthread();
		int action=event.getAction();
		if(action==MotionEvent.ACTION_DOWN){
			
		}else if(action==MotionEvent.ACTION_CANCEL || action==MotionEvent.ACTION_OUTSIDE || action==MotionEvent.ACTION_UP){
			lastleng=0;
			speedx=points.getSpeedx();
			speedy=points.getSpeed();
			points.clear();
			autothread=new AutoMove();
			autothread.start();
		}else{
			if(event.getPointerCount()>1){
				double xlen=event.getX(0)-event.getX(1),ylen=event.getY(0)-event.getY(1);
				double leng=xlen*xlen+ylen*ylen;
				if(lastleng==0){lastleng=(float) leng;}
				screenpoint.set((event.getX(0)+event.getX(1))/2,(event.getY(0)+event.getY(1))/2);
				zoomsize=zoomsize*leng/lastleng>minzoomsize?zoomsize*leng/lastleng:minzoomsize;
				zoomsize=zoomsize>4?4:zoomsize;
				lastleng=(float) leng;
				zoompoint.x=(screenpoint.x-photo.L())/photo.W();
				zoompoint.y=(screenpoint.y-photo.T())/photo.H();
			}else{
				Point lastpoint;
				if(points.getSize()>0){
					lastpoint=points.get();
				}else{
					lastpoint=new Point(event);
				}
				float mx=event.getX()-lastpoint.x,my=event.getY()-lastpoint.y;
				screenpoint.x+=mx;
				screenpoint.y+=my;
				points.add(event);
			}
		}
		this.invalidate();
		return true;
	}
	
	
	
	private synchronized void drawbitmap(Canvas canvas){
		if(bitmap==null){return;}
		double l=0,t=0,w=bitmap.getWidth(),h=bitmap.getHeight(),sw=this.getWidth(),sh=this.getHeight();
		if(zoomsize==0){
			if(sh<h || sh<w){
				if(sh/h<sw/w){
					zoomsize=sh/h;
				}else{
					zoomsize=sw/w;
				}
			}else{
				zoomsize=1;
			}
			minzoomsize=zoomsize;
			if(zoomsize>0){
				w=w*zoomsize;
				h=h*zoomsize;
			}
			l=sw/2-w/2;
			t=sh/2-h/2;
		}else{
			if(zoomsize>0){
				w=w*zoomsize;
				h=h*zoomsize;
			}
			int x=(int) (zoompoint.x*w),y=(int) (zoompoint.y*h),sx=(int) screenpoint.x,sy=(int) screenpoint.y;
	
			l=sx-x;
			t=sy-y;
		}
		Log.d("photo", end+"");
		if(end){
			end=false;
			if(w>sw){
				l=l>0?0:(l+w<sw?sw-w:l);
			}else{
				l=sw/2-w/2;
			}
			if(h>sh){
				t=t>0?0:(t+h<sh?sh-h:t);
			}else{
				t=sh/2-h/2;
			}
		}
		zoompoint.clear();
		screenpoint.set((float)l, (float)t);
		photo.set(l, t, w, h);
		drawBitmap(canvas, bitmap,photo.Rect(),paint);
	}
	
	public void destroy(){
		if(bitmap==null){return;}
		bitmap.recycle();
	}
	
	
	private void stopthread(){
		if(autothread==null){
			return;
		}
   		this.speedx=0;
   		this.speedy=0;
   		try {
			autothread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		autothread=null;
	}
	
    class AutoMove extends Thread{
    	int thread=-1;
    	double sw=getWidth(),sh=getHeight();
    	public AutoMove(){}
    	
        public void run() {
        	thread=50;
        	for(int i=0;i<thread && Math.abs(speedx)/10+Math.abs(speedy)/10>1;i++){
        		screenpoint.x+=speedx/10;
				screenpoint.y+=speedy/10;
				if(photo.L()>0 || photo.R()<sw){
					if(photo.L()>0){
						screenpoint.x=0;
					}
					if(photo.R()<sw){
						screenpoint.x=(float) (sw-photo.W());
					}
					speedx=0;
				}
				if(photo.T()>0 || photo.B()<sh){
					if(photo.T()>0){
						screenpoint.y=0;
					}
					if(photo.B()<sh){
						screenpoint.y=(float) (sh-photo.H());
					}
					speedy=0;
				}
				if(speedx+speedy==0){
					break;
				}
				hand.sendMessage(hand.obtainMessage(1));
				wsleep();
        	}
            end=true;
            wsleep();
            Log.d("photo", "end"+end);
            hand.sendMessage(hand.obtainMessage(1));
        }
    }
    
    private void wsleep(){
    	try {
			Thread.sleep(15);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private Handler hand=new Handler(){
		 @Override 
		 public void handleMessage(Message msg) {
			 if(msg.what==1){
				 invalidate();
			 }
      }
	};
	
	
	
	public static void drawBitmap(Canvas canvas, Bitmap bitmap, Rect situation,
			Paint paint) {
		canvas.drawBitmap(bitmap,
				new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
				situation, paint);
	}

	/**
	 * 绘制图片
	 * 
	 * @param canvas
	 * @param bitmap
	 * @param situation
	 */
	public static void drawBitmap(Canvas canvas, Bitmap bitmap, Paint paint) {
		canvas.drawBitmap(bitmap,
				new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
				new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);
	}

	/**
	 * 绘制图片
	 * 
	 * @param canvas
	 * @param bitmap
	 * @param situation
	 */
	public static void drawBitmap(Canvas canvas, Bitmap bitmap, Rect stu,
			Rect situation, Paint paint) {
		canvas.drawBitmap(bitmap, stu, situation, paint);
	}
	
}
