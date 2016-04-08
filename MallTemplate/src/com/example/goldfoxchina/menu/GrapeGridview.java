package com.example.goldfoxchina.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-4-28
 * Time: 下午12:51
 * To change this template use File | Settings | File Templates.
 */
public class GrapeGridview extends GridView {
    public GrapeGridview(Context context) {
        super(context);
// TODO Auto-generated constructor stub
    }
    public GrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    public GrapeGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
// TODO Auto-generated constructor stub
    }

    //通过重新dispatchTouchEvent方法来禁止滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
// TODO Auto-generated method stub
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;//禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
}
