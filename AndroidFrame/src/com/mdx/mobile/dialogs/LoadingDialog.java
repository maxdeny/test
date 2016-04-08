package com.mdx.mobile.dialogs;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog implements Loading{
	private ProgressDialog mpdialog;
	
	public LoadingDialog(Context context) {
		mpdialog = new ProgressDialog(context);  
		mpdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mpdialog.setMessage("Please Wait...");
		mpdialog.setIndeterminate(true); 
		mpdialog.setCancelable(false);
	}

	public void show() {
		mpdialog.show();
	}
	
	public void dismiss(){
		mpdialog.dismiss();
	}
}
