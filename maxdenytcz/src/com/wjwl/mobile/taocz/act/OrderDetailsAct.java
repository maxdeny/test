package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.LogisticsDialog;

public class OrderDetailsAct extends MActivity {
	TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, tnum,
			tyunfei;
	MImageView img;
	Button bt_wl;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.orderdetails);
		t1 = (TextView) findViewById(R.orderdetails.text1);
		t2 = (TextView) findViewById(R.orderdetails.text2);
		t3 = (TextView) findViewById(R.orderdetails.text3);
		t4 = (TextView) findViewById(R.orderdetails.text4);
		t5 = (TextView) findViewById(R.orderdetails.text5);
		t6 = (TextView) findViewById(R.orderdetails.text6);
		t7 = (TextView) findViewById(R.orderdetails.text7);
		t8 = (TextView) findViewById(R.orderdetails.text8);
		t9 = (TextView) findViewById(R.orderdetails.text9);
		t10 = (TextView) findViewById(R.orderdetails.text10);
		t11 = (TextView) findViewById(R.orderdetails.text11);
		t12 = (TextView) findViewById(R.orderdetails.text12);
		t13 = (TextView) findViewById(R.orderdetails.text13);
		tnum = (TextView) findViewById(R.orderdetails.text_num);
		tyunfei = (TextView) findViewById(R.orderdetails.text_yunfen);
		img = (MImageView) findViewById(R.orderdetails.productimg);
		bt_wl = (Button) findViewById(R.orderdetails.bt_wl);
		t1.setText("14835301483216");
		t2.setText("201272730430907");
		t3.setText("交易成功");
		t4.setText("1");
		t5.setText("188");
		tyunfei.setText("（含运费" + "0.00" + "元）");
		t6.setText("卡夫奥利奥夹心饼干家庭装（300g）");
		t7.setText("188.00");
		tnum.setText("1");
		t8.setText("卡其鱼09");
		t9.setText("2012-07-12 20.08。10");
		t10.setText("2012-07-12 20.08。10");
		t11.setText("2012-07-12 20.08。10");
		t12.setText("常州市新北区漓江路9号");
		t13.setText("213000");
		bt_wl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LogisticsDialog logistics = new LogisticsDialog(
						OrderDetailsAct.this);
				logistics.show();
			}
		});
	}

}
