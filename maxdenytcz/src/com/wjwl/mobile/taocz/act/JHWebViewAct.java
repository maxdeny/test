package com.wjwl.mobile.taocz.act;

import java.io.IOException;
import java.lang.reflect.Field;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder.Builder;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;
import com.wjwl.mobile.taocz.widget.HeadLayout;

public class JHWebViewAct extends MActivity {
	private WebView webview = null;
	String jhurl="",order_sn_main="",isclear="",bankname="",umcout="";
	HeadLayout head;
	@Override
	public void create(Bundle savedInstanceState) {
		setContentView(R.layout.jhwebview);
		jhurl=getIntent().getStringExtra("jhurl");
//		jhurl="https://ibsbjstar.ccb.com.cn/app/ccbMain?MERCHANTID=105320453110036&POSID=792414231&BRANCHID=320000000&ORDERID=131225160449659&PAYMENT=0.10&CURCODE=01&TXCODE=520100&REMARK1=131225162843212&REMARK2=&MAC=d6c2b6bc5d39111ea3a3ffcf4579e4c3";
		order_sn_main=getIntent().getStringExtra("order_sn_main");
		umcout=getIntent().getStringExtra("umcout");
		bankname=getIntent().getStringExtra("bankname");
		head=(HeadLayout) findViewById(R.id.v3_head);
		head.setRightGone();
		if(bankname!=null||bankname.equals("11")){
			head.setTitle("农行支付");
		}
		else{
			head.setTitle("建行支付");
		}
		head.setLeftClick(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dataLoad();
			}
		});
		initWebView();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			dataLoad();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initWebView() {
		((LoadingDialog)loadingDialog).setText("正在加载");
		loadingDialog.show();
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl(jhurl);
		webview.requestFocus();
		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
		webview.getSettings().setUseWideViewPort(true);// WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小，如下设置
		webview.getSettings().setLoadWithOverviewMode(true);

		
		
		webview.setWebChromeClient(new WebChromeClient()
	        {

	            @Override
	            public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
	            {
	                // TODO Auto-generated method stub
	                return super.onJsAlert(view, url, message, result);
	            }

	        });
		 
		 
		
		 WebViewClient wvc = new WebViewClient() {
			             @Override
			             public boolean shouldOverrideUrlLoading(WebView view, String url) {
			                 webview.loadUrl(url);
			                 return false;
			             }
			             @Override
			            public void onPageFinished(WebView view, String url) {
			            // TODO Auto-generated method stub
			            super.onPageFinished(view, url);
			            loadingDialog.dismiss();
			            }
			         };
			         webview.setWebViewClient(wvc);
	}
	
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("PFROMORDER")) {
			if(umcout!=null&&umcout.equals("OrderPay4")){
				MobclickAgent.onEvent(JHWebViewAct.this, "OrderPay4Completed");
			}
			else{
				MobclickAgent.onEvent(JHWebViewAct.this, "OrderPay4Success");
			}
			Msg_Payorder.Builder build=(Builder) son.build;
			isclear=build.getHdfk();
			Frame.HANDLES.get("OrderEndAct").get(0).sent(1, new String[]{order_sn_main,isclear});
            finish();
            
			
		}
	}
	
	
	@Override
	public void dataLoad(int[] types) {
			this.loadData(new Updateone[] { new Updateone("PFROMORDER",
					new String[][] { { "userid", F.USER_ID},{},{"order_sn_main",order_sn_main}}), });
	}
	

	class PlayerMethod {
		AssetManager asm = getResources().getAssets();
		 @JavascriptInterface
		public void playOk(String ok) throws IllegalArgumentException,
				IllegalStateException, IOException {
			 Toast.makeText(JHWebViewAct.this, ok, Toast.LENGTH_SHORT).show();
			 Intent data=new Intent();  
	         data.putExtra("orderid", "1000");  
             setResult(100, data);  
             finish();  
		}
	}

}