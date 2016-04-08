package com.mdx.mobile.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.mdx.mobile.InitConfig.ErrMsg;

public class ErrorDialog implements MsgDialog {
	private AlertDialog.Builder builder;
	private AlertDialog dialog;

	public ErrorDialog(Context context) {
		builder = new AlertDialog.Builder(context);
	}

	public void setMsg(ErrMsg em) {
		if(em.type==0){
			builder.setTitle("Warning");
		}
		if(em.type==1){
			builder.setTitle("Error!");
		}
		builder.setMessage(em.msg);
		builder.setPositiveButton("OK", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}

	public void show() {
		dialog=builder.create();
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	@Override
	public void toLogin() {
	}
}
