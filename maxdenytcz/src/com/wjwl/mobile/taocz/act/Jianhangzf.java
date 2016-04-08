package com.wjwl.mobile.taocz.act;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import CCB.UTIL.MD5ONCE;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ccb.pay.PayMain;
import com.ccb.pay.PayMain$Callback;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class Jianhangzf extends MActivity{
	private Context mContext;
	private Button btn;

	private TextView merchantid;
	private TextView orderidnum;
	private TextView payment;
//	private TextView branchid;
//	private TextView remark1;
//	private TextView remark2;

	private String tmerchantid = "";
	private String torderidnum = "";
	private String tpayment = "";
	private String tbranchid = "";
	private String tremark1 = "";
	private String tremark2 = "";
	
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.ccb_pay_main);

		mContext = this;

		merchantid = (TextView) findViewById(R.id.merchantid);
		orderidnum = (TextView) findViewById(R.id.orderidnum);
		payment = (TextView) findViewById(R.id.payment);
//		branchid = (TextView) findViewById(R.id.branchid);
//		remark1 = (TextView) findViewById(R.id.remark1);
//		remark2 = (TextView) findViewById(R.id.remark2);

		merchantid.setText("常州买东西网络科技有限公司");
		orderidnum.setText("");
		payment.setText(" ");
		btn = (Button) findViewById(R.id.circularizebrand);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				torderidnum = getIntent().getStringExtra("");
				tpayment = getIntent().getStringExtra("");
				tbranchid = "320000000";
				tmerchantid="105320453110036";
				tremark1 = "android";
				tremark2 = "";

				MD5ONCE mac = new MD5ONCE("SP7010" + tmerchantid + torderidnum + tpayment);
				mac.calc();
				String magic = mac.toString();

				Map<String, String> map = new HashMap<String, String>(); // 商户传入map
				map.put("TXCODE", "SP7010");
				map.put("WAPVER", "1.2");
				map.put("MERCHANTID", tmerchantid);
				map.put("ORDERID", torderidnum);
				map.put("PAYMENT", tpayment);
				map.put("MAGIC", magic);
				map.put("BRANCHID", tbranchid);
				map.put("POSID", "000000000");
				map.put("CURCODE", "01");
				map.put("REMARK1", tremark1);
				map.put("REMARK2", tremark2);
				map.put("ACCOUNTPAY", "Y");
				map.put("MBANKPAY", "N");

				// 商户调用PayMain.pay()方法，实现exit方法
				PayMain.pay(mContext, map, new PayMain$Callback() {
					@Override
					public void exit(Map map) {
						Set<String> set = map.keySet(); 
						  for (String s:set) {
						   System.out.println(s+","+map.get(s));
						  }
						// map为建行返回交易支付结果信息
						// 商户在这里处理...
					}
				});

			}
		});

	}
}
