package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class GetPassWordAct extends MActivity {
	EditText ed_name, ed_phone, ed_newpwd, ed_vercode;
	String name, phone, newpwd, vercode;
	Button bt_getcode, bt_submit;
	int temp;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.getpassword);
		ed_name = (EditText) findViewById(R.getp.edit_accname);
		ed_phone = (EditText) findViewById(R.getp.edit_phone);
		ed_newpwd = (EditText) findViewById(R.getp.edit_newpassword);
		ed_vercode = (EditText) findViewById(R.getp.edit_vercode);
		bt_getcode = (Button) findViewById(R.getp.bt_getvercode);
		bt_submit = (Button) findViewById(R.getp.bt_submit);
		bt_submit.setOnClickListener(new getpwd());
		bt_getcode.setOnClickListener(new getpwd());
	}

	public class getpwd implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.getp.bt_submit:
				name = ed_name.getText().toString().trim();
				vercode = ed_vercode.getText().toString().trim();
				phone = ed_phone.getText().toString().trim();
				newpwd = ed_newpwd.getText().toString();

				if (name.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入用户名",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_name.requestFocus();
					return;
				}
				if (phone.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(),
							"请输入11位手机号码", Toast.LENGTH_SHORT);
					toast.show();
					ed_phone.requestFocus();
					return;
				} else if (phone.length() != 11) {
					Toast toast = Toast.makeText(getApplication(),
							"手机号码有误，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_phone.requestFocus();
					return;
				}

				if (newpwd.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入新密码",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_newpwd.requestFocus();
					return;
				} else if (newpwd.length() < 6 || newpwd.length() > 20) {
					Toast toast = Toast.makeText(getApplication(),
							"请输入6到20位新密码", Toast.LENGTH_SHORT);
					toast.show();
					ed_newpwd.requestFocus();
					return;
				}
				if (vercode.length() < 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入验证码",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_vercode.requestFocus();
					return;
				} else if (vercode.length() != 4) {
					Toast toast = Toast.makeText(getApplication(),
							"验证码有误，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_vercode.requestFocus();
					return;
				}
				temp = 0;
				dataLoad(null);
				break;
			case R.getp.bt_getvercode:
				phone = ed_phone.getText().toString().trim();
				if (phone.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(),
							"请输入11位手机号码", Toast.LENGTH_SHORT);
					toast.show();
					ed_phone.requestFocus();
					return;
				} else if (phone.length() != 11) {
					Toast toast = Toast.makeText(getApplication(),
							"手机号码格输入有误，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_phone.requestFocus();
					return;
				}
				temp = 1;
				dataLoad(null);
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("ogpassword")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "您的密码设置成功",
						Toast.LENGTH_LONG).show();
				GetPassWordAct.this.finish();
			} else {
				if( retn.getErrorMsg().trim().equals(""))
					Toast.makeText(getApplicationContext(),"密码设置失败，请检查你的账号或手机号号码是否有误",
							Toast.LENGTH_LONG).show();
				else
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		} else if (son.build != null && son.mgetmethod.equals("osendcode")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "验证码已发送，请注意查收",
						Toast.LENGTH_LONG).show();
			} else {
				if( retn.getErrorMsg().trim().equals(""))
					Toast.makeText(getApplicationContext(),"验证码发送失败",
							Toast.LENGTH_LONG).show();
				else
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		switch (temp) {
		case 0:
			this.loadData(new Updateone[] { new Updateone("OGPASSWORD",
					new String[][] { { "username", name },
							{ "newpassword", newpwd }, { "tel", phone },
							{ "code", vercode } }), });
			break;
		case 1:
			this.loadData(new Updateone[] { new Updateone("OSENDCODE",
					new String[][] { { "tel", phone }, { "sendtype", "1" } }), });
			break;
		}

	}

}
