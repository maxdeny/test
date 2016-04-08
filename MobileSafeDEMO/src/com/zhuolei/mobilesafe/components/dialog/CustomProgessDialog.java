package com.zhuolei.mobilesafe.components.dialog;

import com.zhuolei.mobilesafe.util.DisplayUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;


public class CustomProgessDialog extends CustomDialog {

	private boolean needShowContinue;

	private ZhuoleiProgressBarLayer progressBar;

	private static final int PROGRESS_DIALOG_HEIGHT = 75;

	
	
	public CustomProgessDialog(Context context, String text) {
		super(context, null, WindowManager.LayoutParams.WRAP_CONTENT, PROGRESS_DIALOG_HEIGHT);
		// TODO Auto-generated constructor stub
		progressBar = new ZhuoleiProgressBarLayer(getContext());
		progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(getContext(), PROGRESS_DIALOG_HEIGHT)));

		progressBar.setText(text);

		setView(progressBar);

		setCancelable(false);

		setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}
				return false;
			}
		});
	}
	
	public CustomProgessDialog(Context context, int text) {
		super(context, null, WindowManager.LayoutParams.WRAP_CONTENT, PROGRESS_DIALOG_HEIGHT);
		// TODO Auto-generated constructor stub
		progressBar = new ZhuoleiProgressBarLayer(getContext());
		progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, DisplayUtil.dip2px(getContext(), PROGRESS_DIALOG_HEIGHT)));

		progressBar.setText(text);

		setView(progressBar);

		setCancelable(false);

		setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_SEARCH) {
					return true;
				}
				return false;
			}
		});
	}
	

	
	public void setText(String text){
		progressBar.setText(text);
	}
	public void setText(int text) {
		progressBar.setText(text);
	}

	@Override
	public void show() {
		if (isShowing()) {
			return;
		}
		progressBar.startRotateAnim();
		super.show();
	}

	public void setNeedShowContinue(boolean b) {
		this.needShowContinue = b;
	}

	@Override
	public void dismiss() {

		if (needShowContinue) {
			needShowContinue = false;
			return;
		}

		super.dismiss();
		progressBar.stopRotateAnim();
	}

	
}
