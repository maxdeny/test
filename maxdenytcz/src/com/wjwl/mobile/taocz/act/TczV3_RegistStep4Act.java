package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_RegistStep4Act extends MActivity {
	TextView t_phone, t_username;
	Button bt_reg;
	String phone, username;
	TczV3_HeadLayout headlayout;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_regstep4);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("注册成功");
		headlayout.setLeftGone();
		phone = getIntent().getStringExtra("phone");
		username = getIntent().getStringExtra("username");
		t_phone = (TextView) findViewById(R.reg.phone);
		t_username = (TextView) findViewById(R.reg.username);
		t_phone.setText("手机号：" + phone);
		t_username.setText("用户名：" + username);
		bt_reg = (Button) findViewById(R.reg.bt_reg);
		bt_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Frame.HANDLES.get("TczV3_LoginAct").get(0).sent(1, username);
				TczV3_RegistStep4Act.this.finish();
			}
		});
	}

}