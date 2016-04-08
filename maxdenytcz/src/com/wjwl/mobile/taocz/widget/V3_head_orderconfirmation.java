package com.wjwl.mobile.taocz.widget;

import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class V3_head_orderconfirmation extends LinearLayout {
	public TextView useraddress, songda_time, beizhu, use_youhuiquan;
	public RelativeLayout layout;

	public V3_head_orderconfirmation(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public V3_head_orderconfirmation(Context context) {
		super(context);
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.v3_head_orderconfirmation, this);
		layout = (RelativeLayout) findViewById(R.v3_head_orderconfirmation.clic_layout1);
		useraddress = (TextView) findViewById(R.v3_head_orderconfirmation.text1);
		songda_time = (TextView) findViewById(R.v3_head_orderconfirmation.text2);
		use_youhuiquan = (TextView) findViewById(R.v3_head_orderconfirmation.text3);
		beizhu = (TextView) findViewById(R.v3_head_orderconfirmation.text4);
		// TODO Auto-generated constructor stub
	}

	public void setSongDaTime(CharSequence text) {
		this.songda_time.setText(text);
	}

	public void setUseraddress(CharSequence text) {
		this.useraddress.setText(text);
	}

	public void setBeiZhu(CharSequence text) {
		this.beizhu.setText(text);
	}

	public void setUseYouHuiQuan(String text) {
		this.use_youhuiquan.setText(text);
	}

	public void setUseraddress(String text) {
		this.useraddress.setText(text);
	}

}
