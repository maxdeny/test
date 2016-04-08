package com.my.spyware.act;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.antispyware.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.http.Son;
import com.mdx.mobile.http.Updateone;
import com.my.spyware.F;
import com.my.spyware.MApplication;
import com.my.spyware.dialog.MyProgressDialog;
import com.my.spyware.widget.ItemHeadLayout;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.xcecs.data.dw.DW_User.MsgUserInfo;
import com.xcecs.data.dw.DW_User.MsgUserInfo.Builder;

/**
 * @Title: ActLogin
 * @ToDo: 登陆
 * @author M2_
 * @version v 1.0
 * @date [2015年4月23日下午2:48:55]
 */
public class ActLogin extends MActivity {
    
    // 用户名
    @ViewInject(R.id.et_username)
    private EditText et_username;
    
    // 密码
    @ViewInject(R.id.et_password)
    private EditText et_password;
    
    // 登录
    @ViewInject(R.id.bt_login)
    private Button bt_login;
    
    // 忘记密码
    @ViewInject(R.id.tv_forget)
    private TextView tv_forget;
    
    //注册
    @ViewInject(R.id.tv_register)
    private TextView tv_register;
    
    // qq
    @ViewInject(R.id.llQQ)
    private LinearLayout llQQ;
    
    // sina
    @ViewInject(R.id.llSina)
    private LinearLayout llSina;
    
    @ViewInject(R.id.head)
    private ItemHeadLayout header;
    
    private static final String TAG = "ActLogin";
    
    private int access = 0;
    
    private String userName = "";
    
    private String password = "";
    
    private UMShareAPI mShareAPI = null;
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
    	initdialog();
        setContentView(R.layout.act_login);
        ViewUtils.inject(this);
        header.title.setText("登录");
        header.btn_back.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                
                finish();
            }
        });
        
        mShareAPI = UMShareAPI.get(this);
        initUmeng();
    }
    
	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"请稍后···");
	}
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0:
                loadData(new Updateone[] { new Updateone("MBUserLogin", new String[][] { { "account", userName },
                        { "password", password }, { "uuid", F.uuid }, { "deviceid", F.DEVICEID } }) });
                break;
            case 1:
                loadData(new Updateone[] { new Updateone("MBUserRegister", new String[][] { { "account", "" },
                        { "password", password }, { "deviceid", F.DEVICEID }, { "email", "" }, { "uuid", F.uuid } }) });
                break;
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.getMetod().equals("MBUserLogin")) {
            if (son.getError() == 0) {
                if (son.build != null) {
                    MsgUserInfo.Builder builder = (Builder) son.getBuild();
                    F.setLogin(this, builder.getId(), builder.getVerify());
                    F.ACCOUNT = builder.getAccount();
                    Intent intent = new Intent(this, TabhostActivity.class);
                    startActivity(intent);
                    
                }
                else {
                    F.showToast(this, "登录失败");
                }
                
            }
            else {
                F.showToast(this, son.getMsg());
            }
            
        }
        else if (son.getMetod().equals("MBUserRegister")) {
            if (son.getError() == 0) {
                MsgUserInfo.Builder builder = (Builder) son.getBuild();
                password = "123456";
                userName = builder.getAccount();
                Toast.makeText(getApplicationContext(),
                        "请注意：QQ登录的初始密码为123456，请及时修改！" + "您的用户名是：" + builder.getAccount().toString(),
                        Toast.LENGTH_LONG).show();
              
                dataLoad(new int[] { 0 });
            }
            else {
                //                F.showToast(this, son.getMsg());
                dataLoad(new int[] { 0 });
            }
        }
        
    }
    
    @OnClick({ R.id.bt_login, R.id.llQQ, R.id.llSina, R.id.tv_register, R.id.tv_forget })
    private void mOnclick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:// 注册
                Intent intentReg = new Intent(this, ActRegister.class);
                startActivity(intentReg);
                break;
            case R.id.tv_forget:// 忘记密码
                Intent intent = new Intent(this, ActForgetPass.class);
                startActivity(intent);
                break;
            case R.id.bt_login:// 登录
                doSubmit();
                break;
            case R.id.llQQ:// qq登录
                
                doLogin();
                break;
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            
            close();
            return true;
        }
        return false;
    }
    
    public void close() {
        
        if (!ExitHelp.getExit()) {
            ExitHelp.setExit(true);
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    
                    ExitHelp.setExit(false);
                }
            };
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }
        else {
            ((MApplication) getApplication()).isSendLocation = false;
            ((MApplication) getApplication()).sendLocation();
            F.close();
        }
    }
    
    public static class ExitHelp {
        public static Boolean isExit = false;
        
        public static void setExit(boolean exit) {
            
            isExit = exit;
        }
        
        public static boolean getExit() {
            
            return isExit;
        }
    }
    
    /** 登陆 */
    private void doSubmit() {
        
        userName = et_username.getText().toString();
        password = et_password.getText().toString();
        
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (userName.indexOf(" ") > -1 || userName.indexOf("　") > -1) {
            Toast.makeText(this, "用户名不能含有空格", Toast.LENGTH_SHORT).show();
            et_username.requestFocus();
            
            return;
        }
        
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
        F.uuid = "";
        dataLoad(new int[] { 0 });
        
        //        Intent intent = new Intent(this, TabhostActivity.class);
        //        startActivity(intent);
        
    }
    
    private void initUmeng() {
        // TODO Auto-generated method stub
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    
    private void doLogin() {
        SHARE_MEDIA platform = SHARE_MEDIA.QQ;
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
    }
    
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            mShareAPI.getPlatformInfo(ActLogin.this, platform, umAuthInfo);
        }
        
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "验证取消", Toast.LENGTH_SHORT).show();
        }
    };
    
    private UMAuthListener umAuthInfo = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            
            if (data != null) {
                
                F.uuid = data.get("openid");
                dataLoad(new int[] { 1 });
            }
        }
        
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
        }
        
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
        }
    };
    
    /**
     * 
     * onActivityResult     
     * ToDo: 如果有客户端 此段代码必加
     * @param requestCode
     * @param resultCode
     * @param data
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
    
}
