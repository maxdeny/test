package com.wjwl.mobile.taocz.boc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.chinamworld.electronicpayment.json.BOCPAYUtil;
import com.wjwl.mobile.taocz.R;

public class ShowOrderInfoActivity extends Activity {
	
	private WebView webview;
	Object keyValue=null;
	Button btn ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.show_orderinfo);
		if (!BOCPAYUtil.getInstanse().aboutMapQuery(ShowOrderInfoActivity.this)) {
			btn  = (Button)this.findViewById(R.id.btn);
			btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it =new Intent();
					it.setClass(ShowOrderInfoActivity.this, MainActivity.class);
					ShowOrderInfoActivity.this.startActivity(it);
					ShowOrderInfoActivity.this.finish();
				}
			});
		}else{
			Intent it =new Intent();
			it.setClass(ShowOrderInfoActivity.this, MainActivity.class);
			ShowOrderInfoActivity.this.startActivity(it);
			ShowOrderInfoActivity.this.finish();
		}
		
	}

}
