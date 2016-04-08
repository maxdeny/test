package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.SettlementAddressListViewAdapter;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 收获地址
 * 
 * @author kysl
 * 
 */

public class SettlementAddressActivity extends Activity {

	private SettlementAddressListViewAdapter listViewAdapter;
	
	/**
	 * 用于存放数据
	 */
	private ArrayList<HashMap<String, String>> arrayList = null;
//	// 数量
	int count=0;
	private ListView address_listview;

	/**
	 * 新增
	 */
	private TextView settlement_add;
	/**
	 * 返回
	 */
	private TextView address_back;


	/**
	 * 姓名 手机号 地址
	 */
//	private TextView settlement_name, settlement_telnum, settlement_address;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_settlement);

		/**
		 * 获取到数据
		 */
//		getData();
		
		
		
		
		
		address_listview = (ListView) findViewById(R.id.address_listview);
		listViewAdapter = new SettlementAddressListViewAdapter(this,this,address_listview);
		address_listview.setAdapter(listViewAdapter);

		address_listview.setOnItemClickListener(new ItemClickListener());

		/**
		 * 返回
		 */

		address_back = (TextView) findViewById(R.id.address_back);
		address_back.setOnClickListener(new ClickListener());

		/**
		 * 增加按钮初始化
		 */
		settlement_add = (TextView) findViewById(R.id.settlement_add);
		settlement_add.setOnClickListener(new ClickListener());
	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.settlement_add:
				Intent intent = new Intent();
				intent.setClass(SettlementAddressActivity.this,
						SettlementAddressFillInActivity.class);
				startActivity(intent);
				SettlementAddressActivity.this.finish();
				break;
			case R.id.address_back:
				finish();
				break;
			default:
				break;
			}

		}
	}

	public class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		}

	}
	
	
}
