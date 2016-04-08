package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class V3_ShopMoneyPayAct extends MActivity {
	TextView tv1, tv2, headtitle, text1;
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
		headtitle.setText("移动商城币支付");
		tv1.setText("手机号：");
		tv2.setText("密码：");
		ed_1.setHint("请输入11位手机号码");
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
				V3_ShopMoneyPayAct.this.finish();
			}
		});
		text1.setText(Html
				.fromHtml("<font color=\"#666666\">忘记密码？</font><font color=\"#999999\">"
						+ "编辑短信“"
						+ "</font><font color=\"#fc3b00\">cx</font><font color=\"#999999\">"
						+ "”到“"
						+ "</font><font color=\"#fc3b00\">"
						+ "106583410167"
						+ "</font><font color=\"#999999\">"
						+ "”找回" + "</font>"));
	}

}
