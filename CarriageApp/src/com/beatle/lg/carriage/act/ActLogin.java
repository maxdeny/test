package com.beatle.lg.carriage.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.beatle.lg.carriage.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ActLogin extends BaseActivity {
    
    @ViewInject(R.id.login_account)
    private EditText ed_username;
    
    @ViewInject(R.id.login_pass)
    private EditText ed_password;
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
        setContentView(R.layout.act_login);
        ViewUtils.inject(this);
        
    }
    
    @OnClick({ R.id.btn_login, R.id.btn_reg })
    private void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_reg:
                Intent intentReg = new Intent(this, ActRegisterFirst.class);
                //                Intent intentReg = new Intent(this, ActRegisterThree.class);
                startActivity(intentReg);
                break;
            case R.id.tv_forget:
                break;
        }
    }
    
    @Override
    protected void saveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void restoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        
    }
    
}
