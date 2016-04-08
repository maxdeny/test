package com.example.goldfoxchina.main;

import java.util.HashMap;

import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.UserInputCheck;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录页面
 * 
 * @author kysl
 * 
 */

public class LoginActivity extends Activity {

	// 升级
	private TextView login_upgrade;

	// 用户名
	private EditText login_name;
	// 密码
	private EditText login_pwd;
	// 登录
	private TextView login_click;
	// 注册新用户
	private TextView login_new;

	private Intent intent;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);

		/* 初始化 */
		login_upgrade = (TextView) findViewById(R.id.login_upgrade);
		login_name = (EditText) findViewById(R.id.login_name);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_click = (TextView) findViewById(R.id.login_click);
		login_new = (TextView) findViewById(R.id.login_new);
		login_pwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);  //密码输入方式

		login_upgrade.setOnClickListener(new ClickListener());
		login_click.setOnClickListener(new ClickListener());
		login_new.setOnClickListener(new ClickListener());

		login_upgrade.setVisibility(View.GONE);

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_upgrade: // 升级
				// intent = new Intent();
				// intent.setClass(LoginActivity.this, UpgradeActivity.class);
				// startActivity(intent);
				break;
			case R.id.login_click: // 登录
				boolean name_flag = UserInputCheck.checkCode(login_name
						.getText().toString());
				boolean pwb_flag = UserInputCheck.checkCode(login_pwd.getText()
						.toString());
				if (name_flag == false) {
					Toast.makeText(LoginActivity.this, "用户名长度不符合要求！",
							Toast.LENGTH_LONG).show();
				}
				if (pwb_flag == false) {
					Toast.makeText(LoginActivity.this, "密码长度不符合要求！",
							Toast.LENGTH_LONG).show();
				}
				if (name_flag == true && pwb_flag == true) {
					new UserLogin(LoginActivity.this, login_name.getText()
							.toString(), login_pwd.getText().toString())
							.execute();
				}
				break;
			case R.id.login_new: // 注册新用户
				intent = new Intent();
				intent.setClass(LoginActivity.this, RegistrationActivity.class);
				startActivity(intent);

				break;
			default:
				break;
			}

		}

	}


	class UserLogin extends
			AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {
		Context context;
		String name;
		String pwd;

		public UserLogin(Context context, String name, String pwd) {
			this.context = context;
			this.name = name;
			this.pwd = pwd;
		}
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new CustomDialog(LoginActivity.this, "登录中……")
                    .createLoadingDialog();
            dialog.show();
        }
        @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            HashMap<String, String> hashMap = null;
            hashMap = GetJsonData.SignInReturnData(context, name, pwd);
			return hashMap;

        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result != null && result.size() > 0) {
                if ("error".equals(result.get("result"))) {
                    Toast.makeText(context, "登录失败,请检查用户名和密码是否正确",Toast.LENGTH_SHORT).show();
                } else {
                    CookieID.getCookieID() .setCookieid(result.get("jsessionid")); // 存放sessionid
                    if (result.containsKey("shopid")) { // 判断hashMap中是否存在key
                        CookieID.getCookieID().setShopID(result.get("shopid"));
                    } else {
                        CookieID.getCookieID().setShopID("");
                    }
                    intent = new Intent();
                    intent.setClass(LoginActivity.this, TabHostActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            } else {
                if (!"".equals(CookieID.getCookieID().getCookieID())) {
                    GetNetWorkData.ReLogin(LoginActivity.this,LoginActivity.class);
                } else {
                    // 如果服务器无响应或关闭，弹出提示
                    GetNetWorkData.ServerMessage(LoginActivity.this, "");
                }
            }
        }
    }
	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(LoginActivity.this);
			return true;
		}
		return false;
	}
}
