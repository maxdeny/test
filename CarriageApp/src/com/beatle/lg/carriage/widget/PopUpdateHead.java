package com.beatle.lg.carriage.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;

import com.beatle.lg.carriage.R;
import com.mdx.mobile.dialogs.CanShow;

public class PopUpdateHead implements CanShow, OnClickListener {
    
    private Context context;
    
    private PopupWindow popwindow;
    
    private View popview;
    
    private View view;
    
    private Button mPai, mBendi, mCancle;
    
    private TakePhotoListener takePhotoListener;
    
    public PopUpdateHead(Context context, View view) {
        super();
        this.context = context;
        this.view = view;
        LayoutInflater flater = LayoutInflater.from(this.context);
        popview = flater.inflate(R.layout.pop_upload_photo, null);
        mPai = (Button) popview.findViewById(R.id.btn_take_photo);
        mBendi = (Button) popview.findViewById(R.id.btn_album);
        mCancle = (Button) popview.findViewById(R.id.btn_cancel);
        
        mPai.setOnClickListener(this);
        mBendi.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        
        popwindow = new PopupWindow(popview, android.view.ViewGroup.LayoutParams.FILL_PARENT,
                android.view.ViewGroup.LayoutParams.FILL_PARENT);
        popwindow.setBackgroundDrawable(new BitmapDrawable(context.getResources()));
        popwindow.setTouchable(true);
        popwindow.setOutsideTouchable(false);
        popwindow.setFocusable(true);
    }
    
    @Override
    public void setType(int type) {
        
    }
    
    @Override
    public void show() {
        // popwindow.showAsDropDown(view, 5, 30);
        popwindow.showAtLocation(view, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
    }
    
    @Override
    public void hide() {
        // TODO Auto-generated method stub
        popwindow.dismiss();
    }
    
    @Override
    public boolean isShow() {
        // TODO Auto-generated method stub
        return popwindow.isShowing();
    }
    
    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.btn_take_photo:
                if (context != null) {
                    takePhotoListener.takePhoto();
                }
                break;
            case R.id.btn_album:
                if (context != null) {
                    takePhotoListener.getByAlbum();
                }
                break;
            case R.id.btn_cancel:
                this.hide();
                break;
            
            default:
                break;
        }
        
    }
    
    public void setTakePhotoListener(TakePhotoListener takePhotoListener) {
        this.takePhotoListener = takePhotoListener;
    }
    
    public interface TakePhotoListener {
        
        void takePhoto();
        
        void getByAlbum();
        
        void cancle();
        
    }
    
}
