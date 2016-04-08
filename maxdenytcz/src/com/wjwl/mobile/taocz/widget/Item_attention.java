package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.DeleteDialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_attention extends LinearLayout {
	TextView b_name;
	TextView b_product;
	MImageView b_img;
	Button cancel;

	public Item_attention(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview();
	}

	public Item_attention(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_attention, this);
		b_img = (MImageView) this.findViewById(R.item_attention.businessimg);
		b_name = (TextView) this.findViewById(R.item_attention.businessname);
		cancel = (Button)this.findViewById(R.item_attention.bt_cancel);
		b_product = (TextView) this
				.findViewById(R.item_attention.businessproduct);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DeleteDialog dialog = new DeleteDialog(v.getContext(),"","",null);
				dialog.setTitle("关注提示");
				dialog.setInfo("取消关注？");
				dialog.show();
			}
		});
	}

	public void setBusinessImg(String str) {
		b_img.setObj(str);
	}

	public void setBusinessName(String str) {
		b_name.setText(str);
	}

	public void setBusinessPro(String str) {
		b_product.setText(str);
	}
}
