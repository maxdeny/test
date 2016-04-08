package com.beatle.lg.carriage.act;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beatle.lg.carriage.F;
import com.beatle.lg.carriage.R;
import com.beatle.lg.carriage.data.EmptyResponse;
import com.beatle.lg.carriage.widget.ItemHeadLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.json.Updateone2json;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

public class ActRegisterFirst extends BaseActivity {
    
    @ViewInject(R.id.rg_phone)
    private EditText ed_phone;
    
    @ViewInject(R.id.rg_code)
    private EditText ed_vcode;
    
    @ViewInject(R.id.btn_getcode)
    private Button btn_getcode;
    
    @ViewInject(R.id.btn_next)
    private Button btn_next;
    
    @ViewInject(R.id.tv_agreement)
    private TextView tv_agreement;
    
    @ViewInject(R.id.chb_agreement)
    private CheckBox chb_agreement;
    
    @ViewInject(R.id.img_prompt)
    private ImageView img_prompt;
    
    @ViewInject(R.id.header)
    private ItemHeadLayout header;
    
    private TimeCount timeCount;// 定时器
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
        setId("ActRegisterFirst");
        setContentView(R.layout.act_register_first);
        ViewUtils.inject(this);
        header.title.setText("手机验证");
        header.btn_back.setVisibility(View.VISIBLE);
        timeCount = new TimeCount(61000, 1000);
        chb_agreement.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    btn_next.setBackgroundResource(R.drawable.btn_submit_blue);
                    btn_next.setClickable(true);
                }
                else {
                    btn_next.setBackgroundResource(R.drawable.btn_submit_grey);
                    btn_next.setClickable(false);
                }
            }
        });
    }
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0:
                loadData(new Updateone[] { new Updateone2json("RegisterGetCode", new String[][] { { "phone",
                        ed_phone.getText().toString() } }) });
                break;
            case 1:
                loadData(new Updateone[] { new Updateone2json("ValidataCode", new String[][] {
                        { "phone", ed_phone.getText().toString().trim() },
                        { "captcha", ed_vcode.getText().toString().trim() } }) });
                break;
        
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.mgetmethod.equals("RegisterGetCode")) {
            
            if (son.getError() == 200) {
                EmptyResponse emptyResponse = new EmptyResponse();
                emptyResponse.build((JSONObject) son.getBuild());
                F.showToast(this, emptyResponse.msg);
                timeCount.start();
                
            }
            else {
                F.showToast(this, son.getMsg());
            }
            
        }
        else if (son.mgetmethod.equals("ValidataCode")) {
            
            if (son.getError() == 200) {
                F.mobile = ed_phone.getText().toString();
                Intent intent = new Intent(this, ActRegisterSecond.class);
                startActivity(intent);
                
            }
            else {
                F.showToast(this, son.getMsg());
            }
            
        }
    }
    
    @OnClick({ R.id.btn_getcode, R.id.btn_next, R.id.btn_back, R.id.tv_agreement })
    private void mOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getcode:
                if (ed_phone.getText().length() != 11) {
                    F.showToast(this, "请输入11位手机号");
                    return;
                }
                dataLoad(new int[] { 0 });
                break;
            case R.id.btn_next:
                if (ed_phone.getText().length() != 11) {
                    F.showToast(this, "请输入11位手机号");
                    return;
                }
                if (F.isEmpty(ed_vcode.getText().toString().trim())) {
                    F.showToast(this, "请输入验证码");
                    return;
                }
                dataLoad(new int[] { 1 });
                F.mobile = ed_phone.getText().toString();
                // Intent intent = new Intent(this, ActRegisterSecond.class);
                // startActivity(intent);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.tv_agreement:
                // 协议
                break;
        }
    }
    
    /**
     * 
     * @Title: TimeCount
     * @ToDo: 当成功向服务器提交手机号后60秒倒计时开始
     * @author daixiu
     * @version v 1.0
     * @date [2015-8-19下午3:11:11]
     */
    class TimeCount extends CountDownTimer {
        
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        
        @Override
        public void onFinish() {
            btn_getcode.setBackgroundResource(R.drawable.btn_submit_blue);
            btn_getcode.setText("获取验证码");
            btn_getcode.setClickable(true);
        }
        
        @Override
        public void onTick(long arg0) {
            btn_getcode.setBackgroundResource(R.drawable.btn_submit_grey);
            btn_getcode.setClickable(false);
            btn_getcode.setText("获取中" + arg0 / 1000);
        }
        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timeCount.cancel();
    }
    
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
