package com.example.goldfoxchina.main;

import java.util.HashMap;

import org.json.JSONException;

import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.UserInputCheck;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 注册页面
 * 
 * @author kysl
 * 
 */

public class RegistrationActivity extends Activity {

	// 返回
	private TextView rgst_back;

	// 邀请码
//	private EditText rgst_code;
	// 用户名
	private EditText rgst_name;
	// 密码
	private EditText rgst_pwd;
	// 确认密码
	private EditText rgst_repwd;
	// 显示密码
	private CheckBox rgst_ischeck;
	// 注册
	private TextView rgst_click;
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_registration);

		// 初始化
		rgst_back = (TextView) findViewById(R.id.rgst_back);
//		rgst_code = (EditText) findViewById(R.id.rgst_code);
		rgst_name = (EditText) findViewById(R.id.rgst_name);
		rgst_pwd = (EditText) findViewById(R.id.rgst_pwd);
		rgst_repwd = (EditText) findViewById(R.id.rgst_repwd);
		rgst_ischeck = (CheckBox) findViewById(R.id.rgst_ischeck);
		rgst_click = (TextView) findViewById(R.id.rgst_click);
		
//		rgst_code.setText("117e16a5-1006-4c34-a156-0299804fbfe8");

		/* 隐藏密码 */
		rgst_pwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		rgst_repwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		rgst_back.setOnClickListener(new ClickListener());
		rgst_click.setOnClickListener(new ClickListener());
		rgst_ischeck.setOnCheckedChangeListener(new isCheckStatus());

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rgst_back: // 返回
				finish();
				break;
			case R.id.rgst_click: // 注册

//				String code = rgst_code.getText().toString().trim();
				String name = rgst_name.getText().toString().trim();
				String pwd = rgst_pwd.getText().toString().trim();
				String repwd = rgst_repwd.getText().toString().trim();
				boolean bname = UserInputCheck.checkCode(name);
				boolean bpwd = UserInputCheck.checkCode(pwd);
				boolean brepwd = UserInputCheck.checkCode(repwd);

				if (bname == true && bpwd == true && brepwd == true) {
					if (!"".equals(pwd) && !"".equals(repwd)
							&& pwd.equals(repwd)) {

//						try {
////							 String message=GetJsonData.RgstReturnData(
////									RegistrationActivity.this, code, name,
////									repwd).get("data");
//							
//							 if("success".equals(message)){
//								 Toast.makeText(RegistrationActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//								 intent=new Intent();
//								 intent.setClass(RegistrationActivity.this,LoginActivity.class);
//								 startActivity(intent);
//								 finish();
//							 }else if("error_exist".equals(message)){
//								 Toast.makeText(RegistrationActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
//							 }else if("error_code".equals(message)){
//								 Toast.makeText(RegistrationActivity.this, "邀请码无效", Toast.LENGTH_SHORT).show();
//							 }else{
//								 Toast.makeText(RegistrationActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
//							 }
//							 
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}

					} else {
						Toast.makeText(RegistrationActivity.this, "两次密码输入不一致",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(RegistrationActivity.this, "用户名或密码输入不符合要求",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
			}

		}

	}

	public class isCheckStatus implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {

				/* 显示密码 */
				rgst_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				rgst_repwd
						.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			} else {
				/* 隐藏密码 */
				rgst_pwd.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				rgst_repwd.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);

			}

		}

	}

}
