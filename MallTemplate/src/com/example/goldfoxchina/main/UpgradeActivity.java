package com.example.goldfoxchina.main;

import java.util.HashMap;

import org.json.JSONException;

import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 升级
 * 
 * @author kysl
 * 
 */

public class UpgradeActivity extends Activity {

	// 返回
	private TextView up_back;

	// 邀请码
	private TextView up_code;

	// 用户名
	private TextView up_name;
	// 密码
	private TextView up_pwd;
	// 升级
	private TextView up_click;

	private Dialog dialog;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_upgrade);

		/* 初始化 */
		up_back = (TextView) findViewById(R.id.up_back);
		up_code = (TextView) findViewById(R.id.up_code);
		up_name = (TextView) findViewById(R.id.up_name);
		up_pwd = (TextView) findViewById(R.id.up_pwd);
		up_click = (TextView) findViewById(R.id.up_click);
		up_pwd.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		up_code.setText("117e16a5-1006-4c34-a156-0299804fbfe8");

		up_back.setOnClickListener(new ClickListener());
		up_click.setOnClickListener(new ClickListener());

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.up_back: // 返回
				finish();
				break;
			case R.id.up_click: // 升级按钮
				String code = up_code.getText().toString().trim();
				String name = up_name.getText().toString().trim();
				String pwd = up_pwd.getText().toString().trim();
				if (!"".equals(code) && !"".equals(name) && !"".equals(pwd)) {
					new UpUser(code, name, pwd).execute();
				} else {
					Toast.makeText(UpgradeActivity.this, "数据不能为空",
							Toast.LENGTH_SHORT).show();
				}

				break;
			default:
				break;
			}

		}

	}

	class UpUser extends
			AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {
		String code, name, pwd;

		public UpUser(String code, String name, String pwd) {
			this.code = code;
			this.name = name;
			this.pwd = pwd;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(UpgradeActivity.this, "用户升级中……请稍后")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {
			HashMap<String, String> hashMap = null;
			try {
				hashMap = GetJsonData.UpUserData(code, name, pwd);
				return hashMap;
			} catch (JSONException e) {
				return null;
			}
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {
				if ("success".equals(result.get("data"))) {
					Toast.makeText(UpgradeActivity.this, "升级成功",
							Toast.LENGTH_SHORT).show();
					intent = new Intent();
					intent.setClass(UpgradeActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				} else if ("error_account".equals(result.get("data"))) {
					Toast.makeText(UpgradeActivity.this, "用户名或密码错误",
							Toast.LENGTH_SHORT).show();
				} else if ("error_code".equals(result.get("data"))) {
					Toast.makeText(UpgradeActivity.this, "邀请码错误",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				// 如果服务器无响应或关闭，弹出提示
				GetNetWorkData.ServerMessage(UpgradeActivity.this, "");
			}
		}

	}

}
