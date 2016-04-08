package com.wjwl.mobile.taocz.widget;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class foot_orderconfirmation extends LinearLayout {
public EditText ed_title;
public TextView sendtime;
	public foot_orderconfirmation(Context context) {
		super(context);
		initview();
	}
	public foot_orderconfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.foot_orderconfirmation, this);
		ed_title = (EditText) findViewById(R.foot_orderconfirmation.ed_fapiaotaitou);
	}
	public String gettitle (){
		return ed_title.getText().toString().trim();
	}
	public void setsendtime(String remark){
		sendtime = (TextView) findViewById(R.foot_orderconfirmation.tx_sendtime);
		sendtime.setText(remark);
	}
}
