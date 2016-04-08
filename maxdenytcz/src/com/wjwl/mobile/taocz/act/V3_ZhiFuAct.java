package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_ZhiFuAct extends MActivity {
	TczV3_HeadLayout head;
	TextView text;
	RadioGroup group, group1;
	RadioButton btn1, btn2, xianjin, pos;
	String[] temp_str = new String[] { "货到付款--现金", "1" };;
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_zhifu);
		head = (TczV3_HeadLayout) findViewById(R.v3_zhifu.header);
		text = (TextView) findViewById(R.v3_zhifu.text);
		group = (RadioGroup) findViewById(R.v3_zhifu.group);
		btn1 = (RadioButton) findViewById(R.v3_zhifu.zaixian);
		btn2 = (RadioButton) findViewById(R.v3_zhifu.huodao);
		group1 = (RadioGroup) findViewById(R.v3_zhifu.payTypeGroup);
		xianjin = (RadioButton) findViewById(R.v3_zhifu.xianjin);
		pos = (RadioButton) findViewById(R.v3_zhifu.pos);
		head.setTitle("支付方式");
		head.setRightButton3Text("完成");
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (btn1.isChecked()) {
					// Intent i=new
					// Intent(V3_ZhiFuAct.this,V3_ZaiXianAct.class);
					// startActivity(i);
					if (Frame.HANDLES.get("TczV3_OrderConfirmationAct") != null
							&& Frame.HANDLES.get("TczV3_OrderConfirmationAct").size() > 0) {
					    temp_str = new String[] { "在线支付", "4" };
						Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0)
								.sent(5, temp_str);
					}
				} else if (btn2.isChecked()) {
					// Intent i=new Intent(V3_ZhiFuAct.this,V3_HuoDaoAct.class);
					// startActivity(i);
					if (Frame.HANDLES.get("TczV3_OrderConfirmationAct") != null
							&& Frame.HANDLES.get("TczV3_OrderConfirmationAct").size() > 0) {
						
						Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0)
								.sent(5, temp_str);
					}
				}
				finish();
			}
		});
		text.setText(Html
				.fromHtml("<font color=\"#333333\">提醒：由</font><font color=\"#ff0000\">"
						+ "淘常州配送"
						+ "</font><font color=\"#333333\">订单，才支持货到付款！</font>"));
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				group1.setVisibility(View.GONE);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				group1.setVisibility(View.VISIBLE);
			}
		});
		
		group1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId==R.v3_zhifu.xianjin){
					temp_str = new String[] { "货到付款--现金", "1" };
				}
				if(checkedId==R.v3_zhifu.pos){
					temp_str = new String[] { "货到付款--pos机", "7" };
				}
			}
		});

	}

}
