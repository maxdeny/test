package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class V3_RegesterStep3Act extends MActivity {
	EditText ed_password;
	Button bt_submit, bt_back;
	String tel, pwd;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_setuppassword);
		tel = getIntent().getStringExtra("tel");
		ed_password = (EditText) findViewById(R.v3_setuppassword.ed_password);
		bt_submit = (Button) findViewById(R.v3_setuppassword.bt_submit);
		bt_back = (Button) findViewById(R.v3_setuppassword.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_RegesterStep3Act.this.finish();
			}
		});
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pwd = ed_password.getText().toString().trim();
				if (pwd.length() < 0) {
					Toast.makeText(V3_RegesterStep3Act.this, "请输入6到20位密码",
							Toast.LENGTH_SHORT).show();
					ed_password.requestFocus();
					return;
				} else if (pwd.length() > 20) {
					Toast.makeText(V3_RegesterStep3Act.this, "请输入6到20位密码",
							Toast.LENGTH_SHORT).show();
					ed_password.requestFocus();
					return;
				}
				dataLoad(null);
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("oregist")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "注册成功~",
						Toast.LENGTH_LONG).show();
				Frame.HANDLES.get("V3_LoginAct").get(0)
						.sent(1, new String[] { tel, pwd });
				V3_RegesterStep3Act.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {

		this.loadData(new Updateone[] { new Updateone("OREGIST",
				new String[][] { { "username", tel }, { "password", pwd },
						{ "code", "t1c1z1" }, { "registtype", "1" } }), });

	}

}
