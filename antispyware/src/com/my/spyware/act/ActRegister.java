package com.my.spyware.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.mdx.mobile.utils.verify.Md5;
import com.my.spyware.F;
import com.my.spyware.dialog.MyProgressDialog;
import com.my.spyware.util.StringUtil;
import com.my.spyware.widget.ItemHeadLayout;
import com.xcecs.data.dw.DW_User.MsgUserInfo;
import com.xcecs.data.dw.DW_User.MsgUserInfo.Builder;

/**
 * 注册
 * 
 * @Title: ActRegister
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-17上午9:33:10]
 */
public class ActRegister extends MActivity {

	// 用户名
	@ViewInject(R.id.et_username)
	private EditText et_username;

	// 密码
	@ViewInject(R.id.et_password)
	private EditText et_password;

	// 密码重复
	@ViewInject(R.id.password_check)
	private EditText password_check;

	@ViewInject(R.id.edit_email)
	private EditText edit_email;

	// 注册
	@ViewInject(R.id.bt_register)
	private Button bt_register;

	@ViewInject(R.id.head)
	private ItemHeadLayout header;

	private String username, psw_chk, password, email;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		initdialog();
		setContentView(R.layout.act_register);
		ViewUtils.inject(this);
		header.title.setText("注册");
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
		loadingDialog = new MyProgressDialog(this, "请稍后···");
	}

	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 0:
			loadData(new Updateone[] { new Updateone("MBUserRegister",
					new String[][] { { "methodno", "MBUserRegister" },
							{ "account", username }, { "password", password },
							{ "deviceid", F.DEVICEID }, { "email", email } }) });
			break;
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.getMetod().equals("MBUserRegister")) {
			if (son.getError() == 0) {
				F.showToast(this, "注册成功！");
				this.finish();
			} else {
				F.showToast(this, son.getMsg());
			}
		}

	}

	@OnClick({ R.id.bt_register })
	private void mOnclick(View view) {
		switch (view.getId()) {
		case R.id.bt_register:
			doSubmit();
			break;

		}
	}

	public void doSubmit() {

		username = et_username.getText().toString();
		psw_chk = password_check.getText().toString().trim();
		password = et_password.getText().toString().trim();
		email = edit_email.getText().toString().trim();

		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, getResources().getString(R.string.error_name),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (!inputName(username)) {
			Toast.makeText(this,
					getResources().getString(R.string.error_name_a),
					Toast.LENGTH_SHORT).show();

			return;
		}

		if (username.length() < 5 || username.length() > 16) {
			Toast.makeText(this,
					getResources().getString(R.string.register_name_length),
					Toast.LENGTH_SHORT).show();
			et_username.requestFocus();
			return;
		}

		if (username.indexOf(" ") > -1 || username.indexOf("　") > -1) {
			Toast.makeText(this, "用户名不能含有空格", Toast.LENGTH_SHORT).show();
			et_username.requestFocus();
			return;
		}

		if (password.length() < 6) {
			Toast.makeText(this,
					getResources().getString(R.string.error_password_a),
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this,
					getResources().getString(R.string.error_password),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (!isNotChinese(password)) {
			Toast.makeText(this, "密码中不能含有中文", Toast.LENGTH_SHORT).show();
			et_password.requestFocus();
			return;
		}

		if (password.indexOf(" ") > -1 || password.indexOf("　") > -1) {
			Toast.makeText(this, "密码中不能含有空格", Toast.LENGTH_SHORT).show();
			et_password.requestFocus();
			return;
		}

		if (!psw_chk.equals(password)) {
			Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
			password_check.requestFocus();
			return;
		}
		if (TextUtils.isEmpty(email)) {
			Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
			edit_email.requestFocus();
			return;
		}
		if (!StringUtil.isEmail(email)) {
			Toast.makeText(this, "邮箱格式不对", Toast.LENGTH_SHORT).show();
			edit_email.requestFocus();
			return;
		}

		String secretPass = "";
		try {
			secretPass = Md5.md5(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		dataLoad(new int[] { 0 });
	}

	/**
	 * tesh ToDo：
	 * 
	 * @author Administrator
	 * @param name
	 * @return
	 * @return boolean
	 * @throws
	 */
	public boolean inputName(String name) {

		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(name);
		if (m.find()) {
			return false;

		} else {
			return true;
		}
	}

	/**
	 * 
	 * ToDo：校验是否有中文
	 * 
	 * @author Administrator
	 * @param str
	 * @return
	 * @return boolean
	 * @throws
	 */
	private boolean isNotChinese(String str) {

		if (str.length() < str.getBytes().length) {
			return false;
		} else {
			return true;
		}
	}

}
