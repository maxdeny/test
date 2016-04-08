package com.beatle.lg.carriage.widget;

import android.content.ClipData.Item;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beatle.lg.carriage.R;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xcecs.data.dw.DW_User.MsgUserInfo;

public class ItemGuideOverlay extends LinearLayout {
    
    private Context mContext;
    
    private TextView tvName;
    
    private TextView tv_classify;
    
    public ItemGuideOverlay(Context context) {
        super(context);
        this.mContext = context;
        inflate(context, R.layout.item_guide_overlay, this);
        init();
    }
    
    private void init() {
        tvName = (TextView) findViewById(R.id.tv_name);
        tv_classify = (TextView) findViewById(R.id.tv_classify);
    }
    
    public void setContent(MsgUserInfo item, ImageLoadingListener listener) {
        tvName.setText(item.getAccount());
        if(item.getStatus().equals("0")){
        	 tv_classify.setText("黑名单");
        }else{
        	 tv_classify.setText("白名单");
        }
       
    }
    
    public void setContent(MsgUserInfo item) {
        tvName.setText(item.getAccount());
        if(item.getStatus().equals("0")){
             tv_classify.setText("黑名单");
        }else{
             tv_classify.setText("白名单");
        }
       
    }
    
}
