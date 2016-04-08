package com.wjwl.mobile.taocz.act;

import java.io.IOException;
import java.lang.reflect.Field;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

import com.mdx.mobile.activity.MActivity;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class ZTWebViewAct extends MActivity {
	private WebView webview = null;
	String yhurl;
	String js = "",umcount="",titlename="";
	private TczV3_HeadLayout hl_head;

	@Override
	protected void create(Bundle arg0) {
		setId("YHWebViewAct");
		setContentView(R.layout.yhwebview);
		loadingDialog.show();
		((LoadingDialog) loadingDialog).setText("正在加载...");
		hl_head = (TczV3_HeadLayout) findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		if (getIntent().getStringExtra("actfrom") != null
				&& getIntent().getStringExtra("actfrom").equals(
						"TczV3_LoginAct")) {
			hl_head.setVisibility(View.GONE);
		} else
			titlename=getIntent().getStringExtra("titlename");
		hl_head.setTitle(titlename==null?"专题活动":titlename);
		yhurl = getIntent().getStringExtra("id");
		umcount= getIntent().getStringExtra("umcount");
		initWebView();
	}

	private void initWebView() {
		js = "function buygoods(goodsId) { window.android.buygoods(goodsId); }  function panicbuygoods(goodsId) { window.android.panicbuygoods(goodsId); }";
		webview = (WebView) super.findViewById(R.id.webview);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webview.loadUrl("javascript:" + js);
				loadingDialog.dismiss();
			}
		});

		webview.getSettings().setSupportZoom(true);
		webview.getSettings().setBuiltInZoomControls(true);
		setZoomControlGone(webview);
		webview.getSettings().setJavaScriptEnabled(true); // 启用JavaScript
		webview.getSettings().setBuiltInZoomControls(true); // 控制页面缩放
		webview.addJavascriptInterface(new UseMethod(), "android");
		webview.loadUrl(yhurl);

	}

	class UseMethod {

		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				try {
					if (msg.arg1 == 1) {
						getProId(msg.arg2 + "");
					} else {
						getQGProId(msg.arg2 + "");
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			};
		};

		@JavascriptInterface
		public void buygoods(String itemid) throws IllegalArgumentException,
				IllegalStateException, IOException {
			// handler.sendEmptyMessage(Integer.parseInt(itemid));
			Message msg = new Message();
			msg.arg1 = 1;
			msg.arg2 = Integer.parseInt(itemid);
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void panicbuygoods(String itemid)
				throws IllegalArgumentException, IllegalStateException,
				IOException {
			// handler.sendEmptyMessage(Integer.parseInt(itemid));
			Message msg = new Message();
			msg.arg1 = 2;
			msg.arg2 = Integer.parseInt(itemid);
			handler.sendMessage(msg);
		}

		private void getProId(String itemid) throws IOException {

//			if(F.USER_ID.equals("")){
//			Toast.makeText(ZTWebViewAct.this, "请登录~", Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent (ZTWebViewAct.this,TczV3_LoginAct.class);
//			startActivity(intent);
//		}
//		else{
			MobclickAgent.onEvent(ZTWebViewAct.this, "SelectHDGoods");
				Intent intent = new Intent(ZTWebViewAct.this,
						TczV3_GoodsDetailsAg.class);
				intent.putExtra("itemid", itemid);
				intent.putExtra("umcount", (umcount==null||umcount.equals(""))?"SelectHDGoods":umcount);
				startActivity(intent);
//				}
		}

		private void getQGProId(String itemid) throws IOException {

			if (F.USER_ID.equals("")) {
				Toast.makeText(ZTWebViewAct.this, "请登录~", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(ZTWebViewAct.this,
						TczV3_LoginAct.class);
				startActivity(intent);
			} else {
				Intent intent = new Intent(ZTWebViewAct.this,
						ActPay_ConfirmationAct.class);
				intent.putExtra("itemid", itemid);
				startActivity(intent);
			}
		}
	}

	public void setZoomControlGone(View view) {
		Class<?> classType;
		Field field;
		try {
			classType = WebView.class;
			field = classType.getDeclaredField("mZoomButtonsController");
			field.setAccessible(true);
			ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(
					view);
			mZoomButtonsController.getZoomControls().setVisibility(View.GONE);
			try {
				field.set(view, mZoomButtonsController);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ActivityPage");
		MobclickAgent.onResume(ZTWebViewAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ActivityPage");
		MobclickAgent.onPause(ZTWebViewAct.this);
	}
}