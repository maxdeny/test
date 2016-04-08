package com.example.goldfoxchina.main;

import com.example.goldfoxMall.R;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created with IntelliJ IDEA.
 * User: Lenovo
 * Date: 14-4-29
 * Time: 下午9:03
 * To change this template use File | Settings | File Templates.
 */
public class WebViewActivity extends Activity {
    private String way;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hzy_webview);
        way = getIntent().getStringExtra("way");
        init();
        loadGoogle();
    }
    //初始化
    private void init(){
        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
    }
    //打开网页
    private void loadGoogle(){
        webView.loadUrl(way);
    }
    
}
