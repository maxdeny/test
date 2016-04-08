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

public class V3_BeiZhuAct extends MActivity {
	TczV3_HeadLayout head;
	EditText edit;
	protected String str;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_beizhu);
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		edit = (EditText) findViewById(R.v3_beizhu.edit);
		head.setTitle("订单备注");
		head.setRightButton3Text("保存");
		head.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_BeiZhuAct.this.finish();
			}
		});
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str = edit.getText().toString().trim();
				Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0).sent(3, str);
				V3_BeiZhuAct.this.finish();
			}
		});
	}

}
