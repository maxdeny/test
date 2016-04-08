package com.zhuolei.mobilesafe.components.dialog;

import com.zhuolei.mobilesafe.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;



public class SelecetDialog extends CustomProgessDialog {

	public SelecetDialog(Context context, int text) {
		super(context, text);
		// TODO Auto-generated constructor stub
		View view=LayoutInflater.from(context).inflate(R.layout.selcet_dialog, null);
		
		setBottomView(view);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		super.show();
	}
}
