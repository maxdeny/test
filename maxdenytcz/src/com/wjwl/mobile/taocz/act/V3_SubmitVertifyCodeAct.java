package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class V3_SubmitVertifyCodeAct extends MActivity {
	EditText ed_vertifycode;
	Button bt_back, bt_submit, bt_tryagain;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_submitvertifycode);
		ed_vertifycode = (EditText) findViewById(R.v3_submitvertifycode.ed_verfitycode);
		bt_submit = (Button) findViewById(R.v3_submitvertifycode.bt_submit);
		bt_tryagain = (Button) findViewById(R.v3_submitvertifycode.bt_tryagain);
		bt_back = (Button) findViewById(R.v3_submitvertifycode.back);
	}

}
