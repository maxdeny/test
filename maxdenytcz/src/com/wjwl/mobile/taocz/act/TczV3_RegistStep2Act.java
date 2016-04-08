package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_RegistStep2Act extends MActivity {
	TczV3_HeadLayout headlayout;
	Button bt_next, bt_getvertifycode;
	String phone, vertifycode;
	EditText ed_vertifycode;
	TextView phonenum, xieyi;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdx.mobile.activity.MActivity#create(android.os.Bundle)
	 */
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_regstep2);
		setId("TczV3_RegistStep2Act");
		phone = getIntent().getStringExtra("phone");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		phonenum = (TextView) findViewById(R.reg.phonenum);
		phonenum.setText(phone);
		xieyi = (TextView) findViewById(R.reg.xieyi);
		xieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(TczV3_RegistStep2Act.this, TczV3_XieYiAct.class);
				startActivity(i);
			}
		});
		headlayout.setTitle("输入验证码");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_RegistStep2Act.this.finish();
			}
		});
		ed_vertifycode = (EditText) findViewById(R.reg.ed_vertifycode);
		bt_getvertifycode = (Button) findViewById(R.reg.bt_getvertify);
		bt_next = (Button) findViewById(R.reg.bt_reg);
		bt_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				vertifycode = ed_vertifycode.getText().toString().trim();
				if (vertifycode.length() <= 0) {
					Toast.makeText(TczV3_RegistStep2Act.this, "请输入验证码",
							Toast.LENGTH_SHORT).show();
					ed_vertifycode.requestFocus();
					return;
				} else if (vertifycode.length() != 4) {
					Toast.makeText(TczV3_RegistStep2Act.this, "验证码输入有误",
							Toast.LENGTH_SHORT).show();
					ed_vertifycode.requestFocus();
					return;
				}
				dataLoad(new int[] { 1 });
			}
		});
		bt_getvertifycode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dataLoad(null);
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("osendcode")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "验证码已发送",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("v3_verification")) {// 验证验证码
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "认证通过",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("phone", phone);
				i.setClass(TczV3_RegistStep2Act.this,
						TczV3_RegistStep3Act.class);
				startActivity(i);
				TczV3_RegistStep2Act.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		if (typs == null)
			this.loadData(new Updateone[] { new Updateone("OSENDCODE",
					new String[][] { { "tel", phone }, { "sendtype", "1" } }), });
		else if (typs[0] == 1)
			this.loadData(new Updateone[] { new Updateone("V3_VERIFICATION",
					new String[][] { { "phoneno", phone },
							{ "registcode", vertifycode },
							{ "registtyep", "1" } }), });

	}
}
