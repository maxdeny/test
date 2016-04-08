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

public class CustomerView2 extends View{

    
    private Paint mPaint2;  
    // private static final String mText = "drawText";  
    private String mText = "drawText";  
    public CustomerView2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    

    public CustomerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint2 = new Paint();  
        // TypedArray是存放资源的array,1.通过上下文得到这个数组,attrs是构造函数传进来的,对应attrs.xml  
        //        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView2);  
        //        // 获得xml里定义的属性,格式为 名称_属性名 后面是默认值  
        //        int textColor = a.getColor(R.styleable.CustomView2_textColor, 0xFFFFFFFF);  
        //        float textSize = a.getDimension(R.styleable.CustomView2_textSize, 35);  
        //        mPaint2.setColor(textColor);  
        //        mPaint2.setTextSize(textSize);  
        //        // 为了保持以后使用该属性一致性,返回一个绑定资源结束的信号给资源  
        //        a.recycle();  
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        mPaint2.setStyle(Style.FILL);  
        canvas.drawText(mText, 10, 60, mPaint2); 
        
    }
    
    
    
}
