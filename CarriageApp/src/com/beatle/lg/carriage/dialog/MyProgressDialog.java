package com.beatle.lg.carriage.dialog;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.beatle.lg.carriage.R;
import com.mdx.mobile.dialogs.Loading;

public class MyProgressDialog extends Dialog implements Loading{
	private TextView tv_loading;
	
	public MyProgressDialog(Context context,String title) {
		super(context, R.style.RDialog);
		this.setContentView(R.layout.loading_dialog);
		tv_loading = (TextView)findViewById(R.id.tv_loading);
		tv_loading.setText(title);
	}
	
    public MyProgressDialog setMessage(CharSequence text) {
        tv_loading.setText("请稍后");
        return this;
    }
	
}
