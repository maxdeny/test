package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.dialogs.LoadingDialog;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class H5Web extends MActivity {

	WebView webview;
	LoadingDialog dialog;
	String url,id;
	Button back,qiandao;
	TczV3_HeadLayout hl_head;
	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.zixuninfo);
	   hl_head = (TczV3_HeadLayout) findViewById(R.zixuninfo.hl_head);
		initWebView();
		
	}
private void initWebView() {
	   
		webview = (WebView) findViewById(R.zixuninfo.webview);
		webview.getSettings().setJavaScriptEnabled(true);
//		webview.loadUrl("http://lanren.taocz.com/www/index.html");
		String myurl=getIntent().getStringExtra("url");
		webview.loadUrl(myurl==null||myurl.equals("")?"http://www.taocz.com":myurl);
		webview.requestFocus();
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
		webview.getSettings().setUseWideViewPort(true);// WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小，如下设置
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setDatabaseEnabled(true);   
		
		//启用地理定位  
		webview.getSettings().setGeolocationEnabled(true);  
		//设置定位的数据库路径  
//		webview.getSettings().setGeolocationDatabasePath(dir);   

		//最重要的方法，一定要设置，这就是出不来的主要原因

		webview.getSettings().setDomStorageEnabled(true);   
		hl_head.setLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		hl_head.setRightButton3Click(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				webview.reload();
				
			}
		});
		hl_head.setRightButton3Text("刷新");
		hl_head.setRightButton3VISIBLE();
		
		webview.setWebChromeClient(new WebChromeClient()
	        {

	            @Override
	            public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
	            {
	                // TODO Auto-generated method stub
	                return super.onJsAlert(view, url, message, result);
	            }
	            @Override
	            public void onGeolocationPermissionsShowPrompt(String origin,   
	                    GeolocationPermissions.Callback callback) {  
	         callback.invoke(origin, true, false);  
	         super.onGeolocationPermissionsShowPrompt(origin, callback);  
	     } 
	            

	        });
		 
		
		 WebViewClient wvc = new WebViewClient() {
			             @Override
			             public boolean shouldOverrideUrlLoading(WebView view, String url) {
			                 // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
			            	 if(url!=null&&url.equals("http://m.taocz.com/")){
			     				finish();
			     			}
			            	 else{
			            		 webview.loadUrl(url);
			            	 }
			     			return true;
			             }
			         };
			         webview.setWebViewClient(wvc);
	}
}
