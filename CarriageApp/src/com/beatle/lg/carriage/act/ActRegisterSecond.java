package com.beatle.lg.carriage.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beatle.lg.carriage.F;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.widget.ItemHeadLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.activity.MActivity;

public class ActRegisterSecond extends BaseActivity {
    
    @ViewInject(R.id.rg_pass)
    private EditText ed_pass;
    
    @ViewInject(R.id.rg_remindpass)
    private EditText ed_remindpass;
    
    @ViewInject(R.id.img_prompt)
    private ImageView img_prompt;
    
    @ViewInject(R.id.header)
    private ItemHeadLayout header;
    
    private String password = "";
    
    private String rePassword = "";
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
        setId("ActRegisterSecond");
        setContentView(R.layout.act_register_second);
        ViewUtils.inject(this);
        header.title.setText("设置密码");
        header.btn_back.setVisibility(View.VISIBLE);
    }
    
    @OnClick({ R.id.btn_next, R.id.btn_back })
    private void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                checkRegister();
                break;
            case R.id.btn_back:
                finish();
                break;
        
        }
    }
    
    private void checkRegister() {
        password = ed_pass.getText().toString().trim();
        rePassword = ed_remindpass.getText().toString().trim();
        if (password.length() <= 0) {
            Toast toast = Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT);
            toast.show();
            ed_pass.requestFocus();
            return;
        }
        else if (!ToProof(password)) {
            Toast toast = Toast.makeText(this, "密码请输入6到18位字母、数字和下划线", Toast.LENGTH_SHORT);
            toast.show();
            ed_pass.requestFocus();
            return;
        }
        if (!password.equals(rePassword)) {
            img_prompt.setVisibility(View.VISIBLE);
            return;
        }
        img_prompt.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this, ActRegisterThree.class);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    
    public boolean ToProof(String usename) {
        if (usename.length() < 6 || usename.length() > 18)
            return false;
        
        for (int i = 0; i < usename.length(); i++) {
            if (usename.charAt(i) >= 'a' && usename.charAt(i) <= 'z' || usename.charAt(i) >= 'A'
                    && usename.charAt(i) <= 'Z' || usename.charAt(i) >= '0' && usename.charAt(i) <= '9'
                    || usename.charAt(i) == '_') {
                continue;
            }
            else
                return false;
        }
        
        return true;
    }
    
    /**
     * 
     * disposeMsg 	
     * ToDo:
     * @param type
     * @param obj
     * @see com.mdx.mobile.activity.MActivity#disposeMsg(int, java.lang.Object)
     */
    @Override
    public void disposeMsg(int type, Object obj) {
        
        if (type == 0) {
            this.finish();
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
