package com.wjwl.mobile.taocz.act;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.SQLDB;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;

public class LoginAct extends MActivity {

	Button bt_login, bt_reg, bt_back, bt_setup;
	CheckBox chk;
	private String username = "", password = "", userid = "";
	private EditText usm, pwd;
	private SQLDB userdb;
	private String rememberPassword = "1", mFrom = "", mdo = "";
	private int mfromType = 0;

	protected void create(Bundle arg0) {
		setContentView(R.layout.login);
		bt_login = (Button) this.findViewById(R.login.bt_login);
		bt_reg = (Button) this.findViewById(R.login.bt_reg);
		bt_back=(Button) this.findViewById(R.login.back);
		bt_setup=(Button) this.findViewById(R.login.setup);
		chk = (CheckBox) this.findViewById(R.login.checkbox);
		usm = (EditText) findViewById(R.login.edit_accname);
		pwd = (EditText) findViewById(R.login.edit_password);
		mFrom = getIntent().getStringExtra("FromId");
		mdo = getIntent().getStringExtra("FromDo");
		mfromType = getIntent().getIntExtra("FromType", 0);
		userdb = new SQLDB(LoginAct.this);
		getUserInfo();
		chk.setChecked(true);
		bt_login.setOnClickListener(new login());
		bt_reg.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent();
				i.setClass(getApplicationContext(), RegisterAct.class);
				startActivity(i);
				LoginAct.this.finish();
			}

		});
        bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LoginAct.this.finish();
			}
		});
        bt_setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(getApplicationContext(), SystemSetupAct.class);
				startActivity(i);
				LoginAct.this.finish();
			}
		});
		((LoadingDialog) loadingDialog).setText("正在登录...");
		this.Menu_Show = false;

	}

	public class login implements OnClickListener {
		public void onClick(View v) {
			if (chk.isChecked())
				rememberPassword = "1";
			else
				rememberPassword = "0";
			username = usm.getText().toString().trim();
			password = pwd.getText().toString();

			if (username.length() <= 0) {
				Toast toast = Toast.makeText(LoginAct.this, "请输入用户名",
						Toast.LENGTH_SHORT);
				toast.show();
				usm.requestFocus();
				return;
			} else if (username.length() > 20) {
				Toast toast = Toast.makeText(LoginAct.this, "输入用户名有误，请重新输入",
						Toast.LENGTH_SHORT);
				toast.show();
				usm.requestFocus();
				return;
			}
			if (password.length() <= 0) {
				Toast toast = Toast.makeText(LoginAct.this, "请输入密码",
						Toast.LENGTH_SHORT);
				toast.show();
				pwd.requestFocus();
				return;
			} else if (username.length() > 20) {
				Toast toast = Toast.makeText(LoginAct.this, "输入密码有误，请重新输入",
						Toast.LENGTH_SHORT);
				toast.show();
				pwd.requestFocus();
				return;
			}
			dataLoad(null);
		}
	}

	@Override
	public void finish() {
		if (mfromType == 0 && mFrom != null && mFrom.length() > 0) {
			Frame.HANDLES.sentAll(this.mFrom, 86, mdo);
		}
		super.finish();
	}

	public void finishOk() {
		switch (mfromType) {
		case 1:
			try {
				Class<?> clazz = Class.forName(mdo);
				Intent intent3 = new Intent();
				intent3.setClass(this, clazz).addFlags(
						Intent.FLAG_ACTIVITY_SINGLE_TOP);
				this.startActivity(intent3);
			} catch (Exception e) {
			}
			break;
		case 2:
			break;
		}
		finish();
	}

	private void getUserInfo() {
		if (userdb.tabIsExist(userdb.tbname1)) {
			Cursor cs = userdb.query(userdb.tbname1);
			if (cs.getCount() > 0) {
				cs.moveToFirst();
				rememberPassword = cs.getString(cs
						.getColumnIndex("c_rememberpassword"));
				if (rememberPassword.equals("1")) {
					username = cs.getString(cs.getColumnIndex("c_username"));
					String tempPassword = cs.getString(cs
							.getColumnIndex("c_userpassword"));
					try {
						password = Arith.decrypt("www.taocz.com", tempPassword);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					userid = cs.getString(cs.getColumnIndex("c_userid"));
					usm.setText(username);
					pwd.setText(password);

					if (cs != null) {
						cs.close();
					}
					if (userdb != null) {
						userdb.close();
					}
				}
			}
		}
	}

	private void setUserInfo() {
		String tempPassword = null;
		if (userdb.tabIsExist(userdb.tbname1)) {
			Cursor cs = userdb.query(userdb.tbname1);
			ContentValues cv = new ContentValues();
			cv.put("c_userid", userid);
			cv.put("c_username", username);
			try {
				tempPassword = Arith.encrypt("www.taocz.com", password);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cv.put("c_userpassword", tempPassword);
			cv.put("c_rememberpassword", rememberPassword);
			if (cs.getCount() > 0)
				userdb.update(userdb.tbname1, "c_id", "1", cv);
			else
				userdb.insert(userdb.tbname1, cv);
			userdb.close();

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("login")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if ((retn.getErrorCode() == 0) || (retn.getErrorCode() == 3)) {
				if (retn.getErrorMsg() != "" || retn.getErrorMsg() != null) {
					userid = retn.getErrorMsg().substring(
							retn.getErrorMsg().lastIndexOf(',') + 1,
							retn.getErrorMsg().length());
					F.USER_ID = userid;
					F.PASSWORD = password;
					F.USERNAME = username;
					setUserInfo();
				}
				Frame.HANDLES.reloadAll("ShoppingCartAct,MyInfoAct,MyTaoczAct");
				finishOk();
			} else {
				Toast.makeText(getApplicationContext(), "登录失败,请检查用户名和密码是否正确",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("LOGIN", new String[][] {
				{ "username", username }, { "password", password } }), });
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (userdb != null) {
			userdb.close();
		}
	}

}
