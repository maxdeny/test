package com.wjwl.mobile.taocz.widget;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

public class head_takeoutbusinessmenu extends LinearLayout {
	public TextView businessname, time, address;
	public MImageView img;

	public head_takeoutbusinessmenu(Context context) {
		super(context);
		initview();
	}

	public head_takeoutbusinessmenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.head_takeoutbusinessmenu, this);
		businessname = (TextView) findViewById(R.head_takeoutbusiness.bussinessname);
		address = (TextView) findViewById(R.head_takeoutbusiness.address);
		time = (TextView) findViewById(R.head_takeoutbusiness.time);
		img = (MImageView) findViewById(R.head_takeoutbusiness.img);

	}

	public void setBusinessName(CharSequence text) {
		this.businessname.setText(text);
	}

	public void setImg(Object obj) {
		this.img.setObj(obj);
	}

	public void setAddress(CharSequence text) {
		this.address.setText(text);
	}

	public void setTime(CharSequence text) {
		this.time.setText(text);
	}

}
