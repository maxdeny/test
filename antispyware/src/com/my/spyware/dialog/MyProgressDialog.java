package com.my.spyware.dialog;


import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.antispyware.R;
import com.mdx.mobile.dialogs.imple.Loading;

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
