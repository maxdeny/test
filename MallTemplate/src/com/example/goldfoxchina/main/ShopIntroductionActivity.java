package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.InterchangeableAdapter;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 搜索出的店铺详细界面
 * 
 * @author kysl
 * 
 */
public class ShopIntroductionActivity extends Activity {

	// 返回
	private TextView search_shop_back;
	// 联系卖家
	private TextView text_contact_seller;

	// 店铺名称
	private TextView text_shop_name;
	// 店铺描述
	private TextView text_shop_content;

	// 宝贝数量
	private TextView text_count;

	// 电话号码
	String telnum = "";

	private Dialog dialog;

	private GridView search_shop_gridview;
	private InterchangeableAdapter changeableAdapter;

	private String shopid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_shop_introduction);
		shopid = getIntent().getExtras().getString("shopid");
		search_shop_back = (TextView) findViewById(R.id.search_shop_back);
		search_shop_back.setOnClickListener(new ClickListener());
		text_contact_seller = (TextView) findViewById(R.id.text_contact_seller);
		text_contact_seller.setOnClickListener(new ClickListener());

		new getShopMessageData().execute();
	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_shop_back:
				finish();
				break;
			case R.id.text_contact_seller:
				// 提示对话框
				AlertDialog.Builder builder = new Builder(
						ShopIntroductionActivity.this);
				builder.setTitle("确认拨打")
						.setMessage("联系电话:" + telnum)
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 用intent启动拨打电话
										Intent intent = new Intent(
												Intent.ACTION_CALL, Uri
														.parse("tel:" + telnum));
										startActivity(intent);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();

				break;
			default:
				break;
			}

		}

	}

	class getShopMessageData extends
			AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {
		Context context;
		ArrayList<HashMap<String, Object>> arraylist;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(ShopIntroductionActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();

		}

		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();

			try {
				HashMap<String, String> ShophashMap = GetJsonData
						.ShopMessageJsonData(ShopIntroductionActivity.this,
								shopid);
				HashMap<String, Object> ContenthashMap = GetJsonData
						.ShopProductJsonData(ShopIntroductionActivity.this,
								shopid);
				if (ShophashMap.size() > 0 && ContenthashMap.size() > 0) {
					hashMap.put("shopName", ShophashMap);
					hashMap.put("contentValue", ContenthashMap);
				} else {
					hashMap = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return hashMap;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {

				HashMap<String, String> Shop = (HashMap<String, String>) result
						.get("shopName");

				HashMap<String, Object> Product = (HashMap<String, Object>) result
						.get("contentValue");
				String total = (String) Product.get("total");

				arraylist = (ArrayList<HashMap<String, Object>>) Product
						.get("value");

				text_shop_name = (TextView) findViewById(R.id.text_shop_name);

				text_shop_name.setText(Shop.get("name"));

				telnum = Shop.get("phone").trim();

				text_shop_content = (TextView) findViewById(R.id.text_shop_content);

				text_shop_content.setText(Shop.get("content"));

				text_count = (TextView) findViewById(R.id.text_count);

				text_count.setText(total);

				search_shop_gridview = (GridView) findViewById(R.id.search_shop_gridview);
				changeableAdapter = new InterchangeableAdapter(
						ShopIntroductionActivity.this, arraylist);
				search_shop_gridview.setAdapter(changeableAdapter);
				search_shop_gridview
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								Intent intent = new Intent();
								intent.setClass(ShopIntroductionActivity.this,
										ProductDetailsActivity.class);
								intent.putExtra("id", arraylist.get(position)
										.get("id").toString());
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								ShopIntroductionActivity.this.finish();
								ShopIntroductionActivity.this.onDestroy();
							}
						});
			} else {
				// 如果服务器无响应或关闭，弹出提示
				GetNetWorkData.ServerMessage(ShopIntroductionActivity.this, "");

			}
		}
	}

}
