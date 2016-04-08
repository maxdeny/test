package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_Com_SearchAct extends MActivity {

	private TczV3_HeadLayout headlayout;
	private EditText ed_search;
	String actfrom = "", keyword;
	Button bt_search;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_com_search);
		actfrom = getIntent().getStringExtra("actfrom");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_Com_SearchAct.this.finish();
			}
		});
		ed_search = (EditText) findViewById(R.tczv3.ed_search);
		bt_search = (Button) findViewById(R.tczv3.bt_search);
		bt_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				keyword = ed_search.getText().toString().trim();
				if (keyword.equals("")) {
					Toast.makeText(TczV3_Com_SearchAct.this, "请输入搜索内容",
							Toast.LENGTH_SHORT).show();
					ed_search.requestFocus();
					return;
				}
				F.searchTo(TczV3_Com_SearchAct.this,keyword,"gouwu");
				finish();
			}
		});
	}

}
