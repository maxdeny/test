package com.wjwl.mobile.taocz.dialog;

import com.mdx.mobile.dialogs.Loading;
import com.wjwl.mobile.taocz.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class LoadingDialog extends Dialog implements Loading{
	private TextView tv_loading;
	
	public LoadingDialog(Context context) {
		super(context, R.style.RDialog);
		this.setContentView(R.layout.loading_dialog);
		tv_loading = (TextView)findViewById(R.loading.tv_loading);
		tv_loading.setText("加载中...");
	}
	
	public void setText(String text){
		tv_loading.setText(text);
	}
	
	public LoadingDialog setMessage(CharSequence text){
		tv_loading.setText(text);
		return this;
	}
}
