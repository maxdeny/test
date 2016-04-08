package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class AccSetupAct extends MActivity {

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.accsetup);
		View view = (View) this.findViewById(R.accsetup.click);
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (F.USER_ID != "" && F.USER_ID != null) {
					Intent i = new Intent();
					i.setClass(getApplicationContext(), ModifyPassWordAct.class);
					startActivity(i);
					AccSetupAct.this.finish();
				} else
					Toast.makeText(getApplication(), "您的账号未登陆，无法修改密码",
							Toast.LENGTH_SHORT).show();
			}
		});
	}
}
