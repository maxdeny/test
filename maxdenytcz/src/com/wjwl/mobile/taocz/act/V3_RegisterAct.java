package com.wjwl.mobile.taocz.act;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class V3_RegisterAct extends MActivity {
	EditText ed_mail, ed_pwd, ed_phone;
	String mail, pwd, secpwd, phone;
	Button bt_reg, bt_back, bt_login, bt_getcode;
	LinearLayout layout1, layout2;
	private RadioGroup radiogroup;
	CheckBox chk1, chk2;
	TextView title;
	String regtype;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.v3_register);
		regtype = "1";
		ed_pwd = (EditText) findViewById(R.v3_register.edit_password);
		// ed_secpwd = (EditText) findViewById(R.v3_register.edit_secpassword);
		ed_mail = (EditText) findViewById(R.v3_register.edit_mail);
		ed_phone = (EditText) findViewById(R.v3_register.ed_phone);
		bt_reg = (Button) this.findViewById(R.v3_register.bt_reg);
		bt_back = (Button) findViewById(R.v3_register.back);
		bt_login = (Button) findViewById(R.v3_register.login);
		title = (TextView) findViewById(R.v3_register.title);
		bt_getcode = (Button) findViewById(R.v3_register.bt_getvertifycode);
		layout1 = (LinearLayout) findViewById(R.v3_register.layout1);
		layout2 = (LinearLayout) findViewById(R.v3_register.layout2);
		radiogroup = (RadioGroup) findViewById(R.v3_register.radioGroup);
		chk1 = (CheckBox) findViewById(R.v3_register.mima);
		chk2 = (CheckBox) findViewById(R.v3_register.xieyi);
		chk1.setChecked(false);
		chk2.setChecked(true);
		chk2.setVisibility(View.GONE);
		bt_login.setOnClickListener(new reg());
		bt_reg.setOnClickListener(new reg());
		bt_back.setOnClickListener(new reg());
		bt_getcode.setOnClickListener(new reg());
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		this.Menu_Show = false;
		chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					ed_pwd.setInputType(145);
				else
					ed_pwd.setInputType(129);
			}
		});
		chk2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
			}
		});
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
	}

	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// LoadShow = true;
			switch (checkedId) {
			case R.v3_register.radio_phone:
				regtype = "1";
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
				break;
			case R.v3_register.radio_mail:
				regtype = "2";
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);
				break;
			}
		}
	}

	public class reg implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.v3_register.bt_reg:
				mail = ed_mail.getText().toString().trim();
				pwd = ed_pwd.getText().toString().trim();
				if (mail.length() <= 0) {
					Toast toast = Toast.makeText(getApplication(), "请输入邮箱地址",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_mail.requestFocus();
					return;
				} else if (!isEmail(mail)) {
					Toast toast = Toast.makeText(getApplication(),
							"您输入的邮箱地址格式有误", Toast.LENGTH_SHORT);
					toast.show();
					ed_mail.requestFocus();
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
							"密码输入有误，请6到12位字符", Toast.LENGTH_SHORT);
					toast.show();
					ed_pwd.requestFocus();
					return;
				}
				if (!chk2.isChecked()) {
					Toast toast = Toast.makeText(getApplication(),
							"请同意我们的服务条款协议！", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				dataLoad(null);
				break;
			case R.v3_register.back:
				V3_RegisterAct.this.finish();
				break;
			case R.v3_register.login:
				V3_RegisterAct.this.finish();
				break;
			case R.v3_register.bt_getvertifycode:
				if (regtype.equals("1")) {
					phone = ed_phone.getText().toString().trim();
					if (phone.length() <= 0) {
						Toast toast = Toast.makeText(getApplication(),
								"请输入您的手机号码", Toast.LENGTH_SHORT);
						toast.show();
						ed_phone.requestFocus();
						return;
					} else if (phone.length() != 11) {
						Toast toast = Toast.makeText(getApplication(),
								"您输入的手机号码有误，请重新输入", Toast.LENGTH_SHORT);
						toast.show();
						ed_phone.requestFocus();
						return;
					}
					dataLoad(new int[]{1});
				}
			}

		}
	}

	public static boolean isEmail(String strEmail) {
		String strPattern = ("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		return m.matches();
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("oregist")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "注册成功~",
						Toast.LENGTH_LONG).show();
				Frame.HANDLES.get("V3_LoginAct").get(0)
						.sent(1, new String[] { mail, pwd });
				V3_RegisterAct.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("osendcode")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "验证码已发送",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("title", phone);
				i.setClass(V3_RegisterAct.this, V3_RegesterStep2Act.class);
				startActivity(i);
				V3_RegisterAct.this.finish();
			} else {
				Toast.makeText(getApplicationContext(),"该手机号码已注册，请更换其他手机号",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		if (typs == null)
			this.loadData(new Updateone[] { new Updateone("OREGIST",
					new String[][] { { "username", mail }, { "password", pwd },
							{ "code", "t1c1z1" },{"registtype","0"} }), });
		else if (typs[0] == 1)
			this.loadData(new Updateone[] { new Updateone("OSENDCODE",
					new String[][] { { "tel", phone }, { "sendtype", "1" } }), });
	}

}
