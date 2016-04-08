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
import com.my.spyware.widget.ItemHeadLayout;

/** 
 * 
 * @Title: ActModifyPass 
 * @ToDo:  修改密码
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-17下午6:30:03]
 */
public class ActModifyPass extends MActivity {
    
    //新密码
    @ViewInject(R.id.edit_new)
    private EditText edit_new;
    
    //重复密码
    @ViewInject(R.id.edit_confirm)
    private EditText edit_confirm;
    
    //确定
    @ViewInject(R.id.btn_confirm)
    private Button btn_confirm;
    
    @ViewInject(R.id.head)
    private ItemHeadLayout header;
    
    @Override
    protected void create(Bundle arg0) {
        // TODO Auto-generated method stub
    	initdialog();
        setContentView(R.layout.act_modifypass);
        ViewUtils.inject(this);
        header.title.setText("修改密码");
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
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this,"请稍后···");
	}
    
    @Override
    public void dataLoad(int[] types) {
        switch (types[0]) {
            case 0:
                loadData(new Updateone[] { new Updateone("MBUserForgetPwd", new String[][] {
                        { "account_id", F.userId }, { "password", edit_new.getText().toString() },
                        { "deviceid", F.DEVICEID } }) });
                break;
        }
    }
    
    @Override
    public void disposeMessage(Son son) throws Exception {
        if (son.getMetod().equals("MBUserForgetPwd")) {
            if (son.getError() == 0) {
                F.showToast(this, "修改成功");
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
        if (TextUtils.isEmpty(edit_new.getText().toString().trim())
                || TextUtils.isEmpty(edit_confirm.getText().toString().trim())) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!inputName(edit_new.getText().toString().trim()) || !inputName(edit_confirm.getText().toString().trim())) {
            Toast.makeText(this, "密码不能含有特殊字符", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!isNotChinese(edit_new.getText().toString().trim())
                || !isNotChinese(edit_confirm.getText().toString().trim())) {
            Toast.makeText(this, "密码不能含有中文", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (edit_new.getText().toString().indexOf(" ") > -1 || edit_new.getText().toString().indexOf("　") > -1) {
            Toast.makeText(this, "新密码不能含有空格", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (edit_confirm.getText().toString().indexOf(" ") > -1 || edit_confirm.getText().toString().indexOf("　") > -1) {
            Toast.makeText(this, "确认密码不能含有空格", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (edit_new.getText().toString().trim().length() < 6 || edit_confirm.getText().toString().trim().length() < 6) {
            Toast.makeText(this, "密码不能小于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (edit_new.getText().toString().equals(edit_confirm.getText().toString())) {
            dataLoad(new int[] { 0 });
            
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.changepassword_a), Toast.LENGTH_LONG).show();
        }
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
}
