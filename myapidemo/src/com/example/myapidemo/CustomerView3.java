package com.example.myapidemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CustomerView3 extends View {
    
    private int color = 0;  
    private String text = "点击我刷新";  
    private Paint mPaint;  
    private int mAscent;  
    
    public CustomerView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();  
        mPaint.setStyle(Style.FILL);  
        mPaint.setTextSize(35.0f);  
        setPadding(20, 60, 0, 0); //设置padding  
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (color > 2) {
            color = 0;
        }
        switch (color) {
            case 0:
                mPaint.setColor(Color.GREEN);
                break;
            case 1:
                mPaint.setColor(Color.RED);
                break;
            case 2:
                mPaint.setColor(Color.BLUE);
                break;
            
            default:
                break;
        }
        canvas.drawText(text, getPaddingLeft(), getPaddingTop(), mPaint);  
    }
    
    public void changeColor() { //为了让外面调用  
        color++;
    }
    
    
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));  
    }  
  
    private int measureWidth(int measureSpec) {  
        int result = 0;  
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
  
        if (specMode == MeasureSpec.EXACTLY) {  
            // We were told how big to be  
            result = specSize;  
        } else {  
            // Measure the text  
            result = (int) mPaint.measureText(text) + getPaddingLeft() + getPaddingRight();  
            if (specMode == MeasureSpec.AT_MOST) {  
                // Respect AT_MOST value if that was what is called for by  
                // measureSpec  
                result = Math.min(result, specSize);// 60,480  
            }  
        }  
  
        return result;  
    }  
  
    private int measureHeight(int measureSpec) {  
        int result = 0;  
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
  
        mAscent = (int) mPaint.ascent();  
        if (specMode == MeasureSpec.EXACTLY) {  
            // We were told how big to be  
            result = specSize;  
        } else {  
            // Measure the text (beware: ascent is a negative number)  
            result = (int) (-mAscent + mPaint.descent()) + getPaddingTop() + getPaddingBottom();  
            if (specMode == MeasureSpec.AT_MOST) {  
                // Respect AT_MOST value if that was what is called for by  
                // measureSpec  
                result = Math.min(result, specSize);  
            }  
        }  
        return result;  
    }  
    
}
