package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class RegisterAct extends MActivity {
	EditText ed_usm, ed_pwd, ed_secpwd;
	String usm, pwd, secpwd;
	Button bt_reg, bt_back, bt_setup;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.register1);
		ed_pwd = (EditText) findViewById(R.reg1.edit_password);
		ed_secpwd = (EditText) findViewById(R.reg1.edit_secpassword);
		ed_usm = (EditText) findViewById(R.reg1.edit_accname);
		bt_reg = (Button) this.findViewById(R.reg1.bt_reg);
		bt_setup = (Button) findViewById(R.reg1.setup);
		bt_back = (Button) findViewById(R.reg1.back);
		bt_reg.setOnClickListener(new reg());
		bt_setup.setOnClickListener(new reg());
		bt_back.setOnClickListener(new reg());
		this.Menu_Show = false;
	}

	public class reg implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.reg1.bt_reg:
				usm = ed_usm.getText().toString().trim();
				pwd = ed_pwd.getText().toString().trim();
				secpwd = ed_secpwd.getText().toString().trim();
				if (usm.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入用户名",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_usm.requestFocus();
					return;
				} else if (usm.length() < 3 || usm.length() > 12) {
					Toast toast = Toast.makeText(getApplication(),
							"用户名输入有误,请输入3到12位字符", Toast.LENGTH_SHORT);
					toast.show();
					ed_usm.requestFocus();
					return;
				}
				if (pwd.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入密码",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_pwd.requestFocus();
					return;
				} else if (pwd.length() < 6 || pwd.length() > 20) {
					Toast toast = Toast.makeText(getApplication(),
							"密码输入有误，请3到12位字符", Toast.LENGTH_SHORT);
					toast.show();
					ed_pwd.requestFocus();
					return;
				}
				if (secpwd.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入确认密码",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_secpwd.requestFocus();
					return;
				} else if (!secpwd.equals(pwd)) {
					Toast toast = Toast.makeText(getApplication(),
							"确认密码有误，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_secpwd.requestFocus();
					return;
				}

				dataLoad(null);
				break;
			case R.reg1.back:
				RegisterAct.this.finish();
				break;
			case R.reg1.setup:
				Intent i = new Intent();
				i.setClass(getApplicationContext(), SystemSetupAct.class);
				startActivity(i);
				break;
			}

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("oregist")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "注册成功~",
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {

		this.loadData(new Updateone[] { new Updateone("OREGIST",
				new String[][] { { "username", usm }, { "password", pwd },
						{ "code", "t1c1z1" } }), });

	}

}