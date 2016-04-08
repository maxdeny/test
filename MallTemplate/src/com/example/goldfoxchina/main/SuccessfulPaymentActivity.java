package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 支付成功
 * 
 * @author kysl
 * 
 */

public class SuccessfulPaymentActivity extends Activity {

	/* 返回 */
	private TextView payment_back;
	/* 返回首页 */
	private TextView payment_back_index;
	/* 我的订单 */
	private TextView payment_myorder;
	private Intent intent;
	private Dialog dialog;

	/* 订单号 */
	private TextView payment_text_num;
	/* 金额 */
	private TextView payment_text_price;
	/* 收货人 */
	private TextView payment_text_name;
	/* 收货地址 */
	private TextView payment_text_address;
	/* 联系电话 */
	private TextView payment_text_tel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_successfulpayment);

		// 初始化
		payment_back = (TextView) findViewById(R.id.payment_back);
		payment_back_index = (TextView) findViewById(R.id.payment_back_index);
		payment_myorder = (TextView) findViewById(R.id.payment_myorder);
		payment_text_num = (TextView) findViewById(R.id.payment_text_num);
		payment_text_price = (TextView) findViewById(R.id.payment_text_price);
		payment_text_name = (TextView) findViewById(R.id.payment_text_name);
		payment_text_address = (TextView) findViewById(R.id.payment_text_address);
		payment_text_tel = (TextView) findViewById(R.id.payment_text_tel);

		payment_back.setOnClickListener(new ClickListener());
		payment_back_index.setOnClickListener(new ClickListener());
		payment_myorder.setOnClickListener(new ClickListener());

		Intent intent = getIntent();

		String comment = intent.getExtras().getString("comment");
		String name = intent.getExtras().getString("name");
		String phone = intent.getExtras().getString("telnum");
		String postcode = "";
		String area = intent.getExtras().getString("address");
		String road = "";
		String items = "";
		ArrayList<String> list = intent.getStringArrayListExtra("ids");
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				items = list.get(0);
			} else {
				items = items + "," + list.get(i);
			}

		}

		String shopId = AdvertisementBean.getAdver().getShopId();

		new OrderData(comment, name, phone, postcode, area, road, items, shopId)
				.execute();

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.payment_back: // 返回
				finish();
				break;
			case R.id.payment_back_index: // 返回首页
				intent=new Intent();
				intent.setClass(SuccessfulPaymentActivity.this, TabHostActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				SuccessfulPaymentActivity.this.finish();
				break;
			case R.id.payment_myorder: // 我的订单
				intent = new Intent();
				intent.setClass(SuccessfulPaymentActivity.this,
						MyOrderActivity.class);
				intent.putExtra("activityId",2);
				startActivity(intent);
				SuccessfulPaymentActivity.this.finish();
				break;

			default:
				break;
			}

		}

	}

	class OrderData extends
			AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {

		String comment;
		String name;
		String phone;
		String postcode;
		String area;
		String road;
		String items;
		String shopId;

		public OrderData(String comment, String name, String phone,
				String postcode, String area, String road, String items,
				String shopId) {
			this.comment = comment;
			this.name = name;
			this.phone = phone;
			this.postcode = postcode;
			this.area = area;
			this.road = road;
			this.items = items;
			this.shopId = shopId;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SuccessfulPaymentActivity.this, "订单提交中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {
			HashMap<String, String> hashMap = null;
			try {
				hashMap = GetJsonData.CreateOderJsonData(comment, name, phone,
						postcode, area, road, items, shopId);
			} catch (Exception e) {
				return null;
			}
			return hashMap;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {

				payment_text_num.setText(result.get("id"));
				payment_text_price.setText(result.get("amount"));
				payment_text_name.setText(result.get("name"));
				payment_text_address.setText(result.get("address"));
				payment_text_tel.setText(result.get("phone"));

			}

		}
	}

}
