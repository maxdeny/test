package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_XieYiAct extends MActivity {

	private TczV3_HeadLayout header;
	private WebView xieyi;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.xieyi);
		setId("TczV3_XieYiAct");
		header = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		header.setTitle("注册协议");
		header.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_XieYiAct.this.finish();
			}
		});
		xieyi = (WebView) findViewById(R.id.xieyi);
		WebSettings wSet = xieyi.getSettings();
		wSet.setJavaScriptEnabled(true);
		// wView.loadUrl("file:///android_asset/index.html");
		// wView.loadUrl("content://com.android.htmlfileprovider/sdcard/index.html");
		wSet.setDefaultTextEncodingName("UTF-8");
		xieyi.loadUrl("file:///android_asset/xieyi.html");
	}

}
