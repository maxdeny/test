package com.zhuolei.mobilesafe.components.dialog;

import com.zhuolei.mobilesafe.main.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class ExitLayer extends DialogLayer {

	private Button btnExit;

	private Button btnCancel;

	private CheckBox cbxDisAutoLogin;

	public ExitLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public ExitLayer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {

		LayoutInflater.from(context).inflate(R.layout.exit_layer, this);

		btnExit = (Button) findViewById(R.id.btnExit);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		cbxDisAutoLogin = (CheckBox) findViewById(R.id.cbxDisAutoLogin);

		cbxDisAutoLogin.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				btnExit.setTag(isChecked);
			}
		});
	}

	@Override
	public void setPositiveBtnListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		btnExit.setOnClickListener(listener);
	}

	@Override
	public void setNegativeBtnListener(OnClickListener listener) {
		// TODO Auto-generated method stub
		btnCancel.setOnClickListener(listener);
	}

}
