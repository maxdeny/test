package com.my.spyware.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antispyware.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.http.Son;
import com.mdx.mobile.http.Updateone;
import com.mdx.mobile.http.json.Updateone2json;
import com.my.spyware.F;
import com.my.spyware.dialog.MyProgressDialog;
import com.my.spyware.util.StringUtil;
import com.my.spyware.widget.ItemHeadLayout;

/** 
 * 忘记密码
 * @Title: ActForgetPass 
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-23下午5:19:51]
 */
public class ActForgetPass extends MActivity {
    
    //邮箱
    @ViewInject(R.id.edit_email)
    private EditText edit_email;
    
    //确定
    @ViewInject(R.id.btn_confirm)
    private Button btn_confirm;
    
    @ViewInject(R.id.head)
    private ItemHeadLayout header;
    
    private String email = "";
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
    	initdialog();
        setContentView(R.layout.act_forgetpass);
        ViewUtils.inject(this);
        header.title.setText("忘记密码");
        header.btn_back.setVisibility(View.VISIBLE);
        header.btn_back.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
    }
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0:
                loadData(new Updateone[] { new Updateone("MBFindPassByEmail", new String[][] { { "email", email },
                        { "deviceid", F.DEVICEID } }) });
                break;
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.getMetod().equals("MBUserForgetPwd")) {
            if (son.getError() == 0) {
                F.showToast(this, "提交成功，请到邮箱查收");
                this.finish();
            }
            else {
                F.showToast(this, son.getMsg());
            }
        }
        
    }
    
    @OnClick({ R.id.btn_confirm })
    private void mOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                submitNewPass();
                break;
        
        }
    }
    
    private void submitNewPass() {
        // TODO Auto-generated method stub
        email = edit_email.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!StringUtil.isEmail(email)) {
            
            Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
            edit_email.requestFocus();
            return;
            
        }
        dataLoad(new int[] { 0 });
    }
    
    /**
     * @param 用户名是否含有特殊字符
     * @return
     */
    public static boolean inputName(String name) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        if (m.find()) {
            return false;
            
        }
        else {
            return true;
        }
    }
    
    /**
     * 校验邮件地址是否有中文
     * 
     * @param str
     * @return
     */
    private boolean isNotChinese(String str) {
        if (str.length() < str.getBytes().length) {
            return false;
        }
        else {
            return true;
        }
    }
    
	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"请稍后···");
	}
}
