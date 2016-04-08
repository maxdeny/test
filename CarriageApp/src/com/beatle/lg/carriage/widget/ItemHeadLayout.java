package com.beatle.lg.carriage.widget;

import com.beatle.lg.carriage.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemHeadLayout extends RelativeLayout {
    
    public TextView title;
    
    public ImageButton btn_back;
    
    public RelativeLayout rel_item;
    
    public ItemHeadLayout(Context context) {
        super(context);
        initView();
    }
    
    public ItemHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    
    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_item_headlayout, this);
        title = (TextView) view.findViewById(R.id.title);
        btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        rel_item = (RelativeLayout) view.findViewById(R.id.rel_item);
    }
    
    public void setItemHeadBackGroud(int color) {
        //	    rel_item.setBackgroundColor(color);
        rel_item.setBackgroundResource(color);
    }
}
