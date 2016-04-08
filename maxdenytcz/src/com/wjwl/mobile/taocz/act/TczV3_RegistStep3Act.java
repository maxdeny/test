package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_RegistStep3Act extends MActivity {
	EditText ed_username, ed_psw;
	TczV3_HeadLayout header;
	String username, psw, phone;
	CheckBox chk;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_regstep3);
		setId("TczV3_RegistStep3Act");
		phone = getIntent().getStringExtra("phone");
		header = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		header.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_RegistStep3Act.this.finish();
			}
		});
		header.setTitle("设置会员名和密码");
		header.setRightButton3Text("完成");
		header.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				psw = ed_psw.getText().toString().trim();
				username = ed_username.getText().toString().trim();
				if (username.length() < 0) {
					Toast.makeText(TczV3_RegistStep3Act.this, "请输入用户名",
							Toast.LENGTH_SHORT).show();
					ed_username.requestFocus();
					return;
				} else if (username.length() > 20) {
					Toast.makeText(TczV3_RegistStep3Act.this, "请输入6到20位用户名",
							Toast.LENGTH_SHORT).show();
					ed_username.requestFocus();
					return;
				}
				if (psw.length() < 0) {
					Toast.makeText(TczV3_RegistStep3Act.this, "请输入6到20位密码",
							Toast.LENGTH_SHORT).show();
					ed_psw.requestFocus();
					return;
				} else if (psw.length() > 20) {
					Toast.makeText(TczV3_RegistStep3Act.this, "请输入6到20位密码",
							Toast.LENGTH_SHORT).show();
					ed_psw.requestFocus();
					return;
				}
				dataLoad(null);
			}
		});
		chk = (CheckBox) findViewById(R.reg.chk);
		ed_username = (EditText) findViewById(R.reg.ed_username);
		ed_psw = (EditText) findViewById(R.reg.ed_psw);
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					ed_psw.setInputType(145);
				} else {
					ed_psw.setInputType(129);
				}
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("v3_oregist")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// Toast.makeText(getApplicationContext(), "注册成功~",
				// Toast.LENGTH_LONG).show();
				// Frame.HANDLES.get("V3_LoginAct").get(0)
				// .sent(1, new String[] { phone, psw });
				Intent i = new Intent();
				username = ed_username.getText().toString().trim();
				i.putExtra("phone", phone);
				i.putExtra("username", username);
				i.setClass(TczV3_RegistStep3Act.this,
						TczV3_RegistStep4Act.class);
				startActivity(i);
				TczV3_RegistStep3Act.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {

		this.loadData(new Updateone[] { new Updateone("V3_OREGIST",
				new String[][] { { "tel", phone }, { "username", username },
						{ "password", psw }, { "registtype", "1" } }), });

	}

}
