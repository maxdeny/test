package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class V3_TCard_ChongZhi extends MActivity {
	TextView tv1, tv2, headtitle;
	EditText ed_1, ed_2;
	Button bt_submit, bt_back;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_com_chongzhi);
		headtitle = (TextView) findViewById(R.v3_com_chongzhi.headtitle);
		tv1 = (TextView) findViewById(R.v3_com_chongzhi.tv_1);
		tv2 = (TextView) findViewById(R.v3_com_chongzhi.tv_2);
		ed_1 = (EditText) findViewById(R.v3_com_chongzhi.ed_1);
		ed_2 = (EditText) findViewById(R.v3_com_chongzhi.ed_2);
		bt_submit = (Button) findViewById(R.v3_com_chongzhi.bt_submit);
		bt_back = (Button) findViewById(R.v3_com_chongzhi.back);
		headtitle.setText("淘心卡充值");
		tv1.setText("卡号：");
		tv2.setText("密码：");
		ed_1.setHint("请输入15位卡号");
		ed_2.setHint("请输入密码");
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_TCard_ChongZhi.this.finish();
			}
		});
	}

}
