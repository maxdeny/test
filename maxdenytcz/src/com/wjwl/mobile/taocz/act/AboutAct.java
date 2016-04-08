package com.wjwl.mobile.taocz.act;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class AboutAct extends MActivity {
	TextView version;
	private TczV3_HeadLayout hl_head;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.about);
		
		hl_head = (TczV3_HeadLayout)findViewById(R.id.hl_head);
		hl_head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AboutAct.this.finish();
			}
		});
		hl_head.setTitle("关于我们");
		version = (TextView) findViewById(R.about.version);
		String versionname;// 版本号
		try {
			PackageManager pm = getPackageManager();
			PackageInfo pi = pm.getPackageInfo("com.wjwl.mobile.taocz", 0);
			versionname = pi.versionName;// 获取在AndroidManifest.xml中配置的版本号
		} catch (PackageManager.NameNotFoundException e) {
			versionname = "";
		}
		version.setText("Version" + versionname);
	}
}
