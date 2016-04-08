package com.example.goldfoxchina.main;

import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.TextView;

public class AboutUSActivity extends Activity {

	private TextView text_about;
	private WebView web_about;
	String data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		
		text_about=(TextView) findViewById(R.id.aboutus_back);
		
		
		text_about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AboutUSActivity.this.finish();
				
			}
		});
		
		web_about=(WebView) findViewById(R.id.aboutus_webview);
		
		
		try {
			data=new GetJsonData().getAboutUS();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/**
		 * webview.load 方法会导致乱码问题
		 */
		web_about.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
		
	}

}
