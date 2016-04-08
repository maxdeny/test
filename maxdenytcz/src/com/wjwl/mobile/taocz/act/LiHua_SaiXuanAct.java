package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class LiHua_SaiXuanAct extends MActivity {

	Button bt_submit, bt_back;
	TextView text1, text2;
	RelativeLayout layout1, layout2;
	String filterid = "";
	String priceid = "";
	String categoryid = "", keywords = "";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_shaixuan);
		setId("LiHua_SaiXuanAct");
		categoryid = getIntent().getStringExtra("categoryid");
		if (getIntent().getStringExtra("keywords") == null)
			keywords = "";
		else
			keywords = getIntent().getStringExtra("keywords");
		bt_submit = (Button) findViewById(R.v3_shaixuan.submit);
		bt_back = (Button) findViewById(R.v3_shaixuan.back);
		text1 = (TextView) findViewById(R.v3_shaixuan.text1);
		text2 = (TextView) findViewById(R.v3_shaixuan.text2);
		layout1 = (RelativeLayout) findViewById(R.v3_shaixuan.layout1);
		layout2 = (RelativeLayout) findViewById(R.v3_shaixuan.layout2);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LiHua_SaiXuanAct.this.finish();
			}
		});
		bt_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] str = new String[] { filterid, priceid };
				Frame.HANDLES.get("Lihua_Act").get(0).sent(7, str);
				LiHua_SaiXuanAct.this.finish();
			}
		});
		layout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("filtertype", "brand");
				i.putExtra("categoryid", categoryid);// 分类id--测试"52282"
				i.putExtra("keywords", keywords);
				i.putExtra("title", "品牌");
				i.putExtra("actfrom", "LiHua_SaiXuanAct");
				i.setClass(LiHua_SaiXuanAct.this, V3_SaiXuanListAct.class);
				startActivity(i);
			}
		});
		layout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("filtertype", "price");
				i.putExtra("categoryid", categoryid);
				i.putExtra("keywords", keywords);
				i.putExtra("title", "价格");
				i.putExtra("actfrom", "LiHua_SaiXuanAct");
				i.setClass(LiHua_SaiXuanAct.this, V3_SaiXuanListAct.class);
				startActivity(i);
			}
		});
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		String[] str = (String[]) obj;
		if (type == 1) {
			text1.setText(str[0]);
			filterid = str[1];
		} else if (type == 2) {
			text2.setText(str[0]);
			priceid = str[1];
		}
	}
}
