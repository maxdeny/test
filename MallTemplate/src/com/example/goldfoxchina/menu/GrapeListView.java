package com.example.goldfoxchina.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-5-4
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class GrapeListView extends ListView {
    public GrapeListView(Context context) {
        super(context);
    }

    public GrapeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrapeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.dispatchTouchEvent(ev);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
