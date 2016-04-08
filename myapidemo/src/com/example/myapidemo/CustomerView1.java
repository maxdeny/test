package com.example.myapidemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CustomerView1 extends View{

    
    private Paint mPaint;  
    // private static final String mText = "drawText";  
    private String mText = "drawText";  
    public CustomerView1(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    

    public CustomerView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Style.FILL);
        canvas.drawRect(new Rect(10, 10, 100, 100), mPaint);
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(35f);
        canvas.drawText(mText, 10, 60, mPaint);
        
    }
    
    
    
}
