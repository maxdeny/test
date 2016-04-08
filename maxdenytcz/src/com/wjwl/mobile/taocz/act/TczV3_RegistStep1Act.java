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
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_RegistStep1Act extends MActivity {
	Button bt_reg;
	EditText ed_phone;
	TczV3_HeadLayout header;
	String phone;
	TextView xieyi;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mdx.mobile.activity.MActivity#create(android.os.Bundle)
	 */
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_regstep1);
		setId("TczV3_RegistStep1Act");
		header = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		header.setTitle("输入手机号");
		header.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_RegistStep1Act.this.finish();
			}
		});
		xieyi = (TextView) findViewById(R.reg.xieyi);
		xieyi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.setClass(TczV3_RegistStep1Act.this, TczV3_XieYiAct.class);
				startActivity(i);
			}
		});
		ed_phone = (EditText) findViewById(R.reg.phonenum);
		bt_reg = (Button) findViewById(R.reg.bt_reg);
		bt_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = ed_phone.getText().toString().trim();
				if (phone.equals("")) {
					Toast.makeText(TczV3_RegistStep1Act.this, "手机号不能为空",
							Toast.LENGTH_SHORT).show();
					ed_phone.requestFocus();
					return;
				} else if (!F.isMobileNO(phone)) {
					Toast.makeText(TczV3_RegistStep1Act.this, "您输入的手机号不正确",
							Toast.LENGTH_SHORT).show();
					ed_phone.requestFocus();
					return;
				}
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
				Intent i = new Intent();
				i.putExtra("phone", phone);
				i.setClass(TczV3_RegistStep1Act.this,
						TczV3_RegistStep2Act.class);
				startActivity(i);
				TczV3_RegistStep1Act.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), "该手机号码已注册，请更换其他手机号",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("OSENDCODE",
				new String[][] { { "tel", phone }, { "sendtype", "1" } }), });
	}
}
