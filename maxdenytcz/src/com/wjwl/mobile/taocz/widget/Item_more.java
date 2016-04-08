package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_more extends LinearLayout {

	private MImageView img;
	// private String itemid;
	private String androidUrl;
	TextView title;
	public Item_more(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Item_more(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void initview() {
		img = (MImageView) findViewById(R.more.img);
		title= (TextView) findViewById(R.more.title);
		img.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!androidUrl.equals("")) {
					Uri uri = Uri.parse(androidUrl);
					Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
					getContext().startActivity(downloadIntent);
				}
			}
		});
	}

	
	
	public void setTitle(String object) {
		this.title.setText(object);
	}

	
	public void setImg(String object) {
		this.img.setObj(object);
		this.img.setType(9);
	}

	/*
	 * public void setItemId(String text) { this.itemid = text; }
	 */
	public void setDownloadUrl(String url) {
		this.androidUrl = url;
	}
}
