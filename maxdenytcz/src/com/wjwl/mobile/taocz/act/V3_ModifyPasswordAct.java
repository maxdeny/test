package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class V3_ModifyPasswordAct extends MActivity {
	EditText ed_password;
	Button bt_submit, bt_back;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_modifypassword);
		bt_submit = (Button) findViewById(R.v3_modifypassword.bt_submit);
		bt_back = (Button) findViewById(R.v3_modifypassword.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_ModifyPasswordAct.this.finish();
			}
		});
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
	}

}
