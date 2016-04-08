package com.wjwl.mobile.taocz.dialog;

import android.content.Context;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class AffirmDialog extends MDialog {
	TextView title, text;
	public Button bt_submit, bt_cancel;

	public AffirmDialog(Context context) {
		super(context, R.style.RDialog);
		mcreate();
		// TODO Auto-generated constructor stub
	}

	public void mcreate() {
		setContentView(R.layout.update);
		title = (TextView) findViewById(R.update.title);
		text = (TextView) findViewById(R.update.text);
		bt_submit = (Button) findViewById(R.update.bt_submit);
		bt_cancel = (Button) findViewById(R.update.bt_cancel);
		text.setText("确定是否删除该地址！");
//		bt_submit.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		bt_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel();
				dismiss();
			}
		});

	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
	}

}
