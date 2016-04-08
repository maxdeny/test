package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.ConfirmOrderListViewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 订单确认
 * 
 * @author kysl
 * 
 */

public class ConfirmOrderActivity extends Activity {

	/**
	 * 数据接收
	 */
	private String name = "";
	private String telnum = "";
	private String address = "";

	/**
	 * 收件人 电话 地址
	 */
	private TextView confirm_name, confirm_telnum, confirm_address;

	/**
	 * listview
	 */
	private ListView confirm_listview;

	/**
	 * 布局
	 */
	private RelativeLayout confirm_message;

	/**
	 * 提交
	 */
	private TextView confirm_submit;

	/**
	 * 返回
	 */
	private TextView confirm_back;

	private ConfirmOrderListViewAdapter listViewAdapter;

//	private TextView confirm_count;
//	private TextView confirm_shopname;
	private TextView confirm_totalprice;
	private int count = 0;
	private int pre_count = 0;
	private double price = 0.0;
	private double totalprice = 0.0;
	private ArrayList<HashMap<String, Object>> arrayList;
	private ArrayList<String> orderlist=new ArrayList<String>();;
	private Intent intent;
	private EditText confirm_comment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_confirmorder);

		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		telnum = intent.getStringExtra("telnum");
		address = intent.getStringExtra("address");
		arrayList = AdvertisementBean.getAdver().getOrderList();

		for (int i = 0; i < arrayList.size(); i++) {
			orderlist.add(arrayList.get(i).get("id")
					.toString());
			pre_count = Integer.valueOf(arrayList.get(i).get("count")
					.toString());
			count = count + pre_count;
			price = Double.valueOf(arrayList.get(i).get("price").toString());
			totalprice = totalprice + pre_count * price;
		}

		/**
		 * 初始化
		 */
		confirm_back = (TextView) findViewById(R.id.confirm_back);
		confirm_back.setOnClickListener(new ClickListener());

		// 总价
		confirm_totalprice = (TextView) findViewById(R.id.confirm_totalprice);
		// 总件数
//		confirm_count = (TextView) findViewById(R.id.confirm_count);

		// listview
		confirm_listview = (ListView) findViewById(R.id.confirm_listview);
		// adapter
		listViewAdapter = new ConfirmOrderListViewAdapter(this, arrayList);
		/**
		 * listview setadapter
		 */
		confirm_listview.setAdapter(listViewAdapter);
		// submit
		confirm_submit = (TextView) findViewById(R.id.confirm_submit);
		confirm_submit.setOnClickListener(new ClickListener());

		// RelativeLayout
		confirm_message = (RelativeLayout) findViewById(R.id.confirm_message);
		confirm_message.setOnClickListener(new ClickListener());
		// 收件人 电话 地址
		confirm_name = (TextView) findViewById(R.id.confirm_name);
		confirm_telnum = (TextView) findViewById(R.id.confirm_telnum);
		confirm_address = (TextView) findViewById(R.id.confirm_address);

		// 商家
//		confirm_shopname = (TextView) findViewById(R.id.confirm_shopname);
		//留言
		confirm_comment=(EditText) findViewById(R.id.confirm_comment);

//		confirm_shopname.setText(AdvertisementBean.getAdver().getShopName());
		confirm_totalprice.setText(String.valueOf(totalprice));
//		confirm_count.setText(String.valueOf(count) + "件");

		confirm_name.setText(name);
		confirm_telnum.setText(telnum);
		confirm_address.setText(address);

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.confirm_message:
				intent = new Intent();
				intent.setClass(ConfirmOrderActivity.this,
						SettlementAddressActivity.class);
				startActivity(intent);
				ConfirmOrderActivity.this.finish();
				break;
			case R.id.confirm_submit:
				String name=(String) confirm_name.getText();
				String telnum=(String) confirm_telnum.getText();
				String address=(String) confirm_address.getText();
				String comment=confirm_comment.getText().toString();
				
				if(comment.length()<300){
				

				intent = new Intent();
				intent.setClass(ConfirmOrderActivity.this,
						SuccessfulPaymentActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("telnum",telnum);
				intent.putExtra("address", address);
				intent.putExtra("comment", comment);
				intent.putExtra("ids", orderlist);
				
				startActivity(intent);
				ConfirmOrderActivity.this.finish();
				}else{
					Toast.makeText(ConfirmOrderActivity.this, "留言超出字数限制", Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.confirm_back:
				finish();
				break;
			default:
				break;
			}

		}

	}

}
