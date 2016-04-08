package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.dialogs.MDialog;
import com.wjwl.mobile.taocz.R;

public class ComDialog extends MDialog {
	TextView text;
	String str;
	Button close;

	public ComDialog(Context context) {
		super(context,R.style.RDialog);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.com_dialog);
		text = (TextView) findViewById(R.com_dialog.Info);
		close = (Button) findViewById(R.com_dialog.close);
		close.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ComDialog.this.cancel();
				ComDialog.this.dismiss();
			}
		});
	}

	public void setText(CharSequence str) {
		text.setText(str);
	}
}
