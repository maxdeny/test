package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.MyProgressBar;

public class ItemwbAct extends MActivityGroup {
	private String itemid;
	WebView tView;
	Msg_Citem citem;
	static long size = 12;
	String itemtype,liangfantuan;
	MyProgressBar progressBar;
	RelativeLayout layout;
    Button bt_back;
	@Override
	public void create(Bundle savedInstanceState) {
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.item_wb);
		this.setProgressBarVisibility(true);
		setProgressBarIndeterminateVisibility(true);
		bt_back=(Button)findViewById(R.item_wb.back);
		bt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ItemwbAct.this.finish();
			}
		});
		tView = (WebView) findViewById(R.id.item_details);
		progressBar = (MyProgressBar) findViewById(R.id.item_progressbay);
		layout = (RelativeLayout) findViewById(R.id.item_progresslayout);
		tView.getSettings().setSupportZoom(true);
		tView.getSettings().setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
		tView.getSettings().setUseWideViewPort(true);// WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小，如下设置
		tView.getSettings().setLoadWithOverviewMode(true);
		itemid = getIntent().getStringExtra("itemid");
		itemtype = getIntent().getStringExtra("itemtype");
//		liangfantuan= getIntent().getStringExtra("liangfantuan");

		tView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if (progress < 100) {
				} else {
					layout.setVisibility(View.GONE);
				}
				progressBar.setProgress(progress);
			}
		});
		if (itemtype.equals("shop")) {
			if(liangfantuan!=null){
				tView.loadUrl(Frame.INITCONFIG.mUri
						+ "/tao.php?act=V3_LIANGFAN_SCONTENT&app=scontent&nerr_wdj=1&itemid_d="
						+ itemid + "&debug=1");
			}
			else{
				tView.loadUrl(Frame.INITCONFIG.mUri
						+ "/tao.php?act=scontent&app=scontent&nerr_wdj=1&itemid_d="
						+ itemid + "&debug=1");
			}
		} else if(itemtype.equals("life")){
			tView.loadUrl(Frame.INITCONFIG.mUri
					+ "/tao.php?act=lcontent&app=scontent&nerr_wdj=1&itemid_d="
					+ itemid + "&debug=1");
		}else if(itemtype.equals("need")){
			tView.loadUrl(Frame.INITCONFIG.mUri
					+ "/tao.php?act=lcontent&app=scontent&nerr_wdj=1&itemid_d="
					+ itemid + "&debug=1"+"&xuzhi=1");
		}else if(itemtype.equals("njl")){
			tView.loadUrl(itemid);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
				finish();
		
		return true;
	}
}
