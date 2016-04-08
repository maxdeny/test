package com.beatle.lg.carriage.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.dialog.BaseDialog;
import com.beatle.lg.carriage.effects.Effectstype;

public class NewAlertDialogWithTitle extends BaseDialog implements OnClickListener {
    
    private TextView mTitle, mMessage;
    
    private Button mLeftBtn, mRightBtn;
    
    private OnNewClickListener listener;
    
    public NewAlertDialogWithTitle(Context context) {
        super(context);
        init();
    }
    
    @Override
    protected int getlayoutResID() {
        return R.layout.new_alert_dialog_with_title;
    }
    
    @Override
    protected void initView() {
        mainView = findViewById(R.id.main);
        mTitle = (TextView) findViewById(R.id.new_alert_title);
        mMessage = (TextView) findViewById(R.id.new_alert_message);
        mLeftBtn = (Button) findViewById(R.id.new_alert_btn_left);
        mRightBtn = (Button) findViewById(R.id.new_alert_btn_right);
    }
    
    public void setTitle(CharSequence message) {
        mTitle.setText(message);
    }
    
    public void setTitle(int messageId) {
        mTitle.setText(messageId);
    }
    
    public void setMessage(CharSequence message) {
        mMessage.setText(message);
    }
    
    public void setMessage(int messageId) {
        mMessage.setText(messageId);
    }
    
    public void setLeftButton(CharSequence text) {
        mLeftBtn.setText(text);
    }
    
    public void setLeftButton(int textId) {
        mLeftBtn.setText(textId);
    }
    
    public void setRightButton(CharSequence text) {
        mRightBtn.setText(text);
    }
    
    public void setRightButton(int textId) {
        mRightBtn.setText(textId);
    }
    
    public void setOnNewClickListener(OnNewClickListener l) {
        this.listener = l;
    }
    
    @Override
    protected void setListener() {
        super.setListener();
        mLeftBtn.setOnClickListener(this);
        mRightBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_alert_btn_left://
                if (listener != null)
                    listener.onLeftClick();
                break;
            case R.id.new_alert_btn_right://
                if (listener != null)
                    listener.onRightClick();
                break;
            default:
                break;
        }
        dismiss();
    }
    
    @Override
    protected Effectstype initEffectStype() {
        return Effectstype.RotateBottom;
    }
    
}
