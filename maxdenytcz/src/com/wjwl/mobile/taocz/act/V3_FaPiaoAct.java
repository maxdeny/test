package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_FaPiaoAct extends MActivity {
	TczV3_HeadLayout head;
	EditText edit;
	String str;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_beizhu);
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		edit = (EditText) findViewById(R.v3_beizhu.edit);
		head.setTitle("发票抬头");
		head.setRightButton3Text("保存");
		head.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_FaPiaoAct.this.finish();
			}
		});
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edit.getText().toString().trim().equals("")) {
					Toast.makeText(V3_FaPiaoAct.this, "请输入公司全称或个人姓名",
							Toast.LENGTH_SHORT).show();
					edit.requestFocus();
					return;
				} else
					str = edit.getText().toString().trim();
				Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0)
						.sent(4, str);
				V3_FaPiaoAct.this.finish();
			}
		});
		edit.setHint("输入公司全称或者个人姓名");
	}
}
