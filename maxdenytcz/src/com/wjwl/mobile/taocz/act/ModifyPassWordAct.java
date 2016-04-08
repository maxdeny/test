package com.wjwl.mobile.taocz.act;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.SQLDB;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class ModifyPassWordAct extends MActivity {
	private CheckBox chk;
	String pwd, newpwd, secpwd;
	EditText ed_pwd, ed_newpwd, ed_secpwd;
	Button bt_submit;
	private SQLDB userdb;
	private TczV3_HeadLayout hl_head;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.modifypassword);
		ed_pwd = (EditText) this.findViewById(R.modifyp.edit_password);
		ed_newpwd = (EditText) this.findViewById(R.modifyp.edit_newpassword);
		ed_secpwd = (EditText) this.findViewById(R.modifyp.edit_secpassword);
		bt_submit = (Button) this.findViewById(R.modifyp.bt_submit);
		bt_submit.setOnClickListener(new modifypassword());
		chk = (CheckBox) this.findViewById(R.modifyp.checkbox);
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (chk.isChecked()) {// 显示密码
					ed_pwd.setInputType(145);
					ed_newpwd.setInputType(145);
					ed_secpwd.setInputType(145);
				} else {// 隐藏密码
					ed_pwd.setInputType(129);
					ed_newpwd.setInputType(129);
					ed_secpwd.setInputType(129);
				}
			}
		});
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ModifyPassWordAct.this.finish();
			}
		});
		hl_head.setTitle("修改密码");

	}

	public class modifypassword implements OnClickListener {
		public void onClick(View v) {

			pwd = ed_pwd.getText().toString().trim();
			newpwd = ed_newpwd.getText().toString().trim();
			secpwd = ed_secpwd.getText().toString().trim();
			if (pwd.length() <= 0) {
				Toast toast = Toast.makeText(getApplication(), "请输入原密码",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_pwd.requestFocus();
				return;
			}
			if (newpwd.length() <= 0) {
				Toast toast = Toast.makeText(getApplication(), "请输入新密码",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_newpwd.requestFocus();
				return;
			} else if (newpwd.length() < 6 || newpwd.length() > 20) {
				Toast toast = Toast.makeText(getApplication(), "新密码输入有误，请重新输入",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_newpwd.requestFocus();
				return;
			}
			if (secpwd.length() <= 0) {
				Toast toast = Toast.makeText(getApplication(), "请输入确认密码",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_secpwd.requestFocus();
				return;
			} else if (!secpwd.equals(newpwd)) {
				Toast toast = Toast.makeText(getApplication(), "确认密码有误，请重新输入",
						Toast.LENGTH_SHORT);
				toast.show();
				ed_secpwd.requestFocus();
				return;
			}
			dataLoad(null);
		}

	}

	private void ModifyUserInfo() {
		userdb = new SQLDB(ModifyPassWordAct.this);
		if (userdb.tabIsExist(userdb.tbname1)) {
			ContentValues cv = new ContentValues();
			cv.put("c_userpassword", newpwd);
			userdb.update(userdb.tbname1, "c_id", "1", cv);
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("ompassword")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "修改成功~",
						Toast.LENGTH_LONG).show();
				ModifyUserInfo();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("OMPASSWORD",
				new String[][] { { "userid", F.USER_ID },
						{ "oldpassword", pwd }, { "newpassword", newpwd } }), });
	}
}
