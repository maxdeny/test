package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import com.example.goldfoxchina.Adapter.CartListViewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.pullTorefresh.XListView;
import com.example.goldfoxchina.pullTorefresh.XListView.IXListViewListener;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 购物车
 * 
 * @author kysl
 * 
 */
public class CartActivity extends Activity {
	/**
	 * 购物车为空
	 */
	private TextView cart_null_go, cart_null_back;

	/**
	 * 购物车不为空
	 */
	// 返回
	private TextView cart_back;

	// 我的订单
	private TextView cart_myorder;

	private CartListViewAdapter cartListViewAdapter;
	private ListView listView;

	private Intent intent;

	private Dialog dialog;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		new SellerListData().execute();

	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cart_back:
				finish();
				break;
			case R.id.cart_myorder:
				intent = new Intent();
				intent.setClass(CartActivity.this, MyOrderActivity.class);
				intent.putExtra("activityId", 2);
				startActivity(intent);
				// finish();
				break;
			default:
				break;
			}

		}

	}

	class SellerListData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(CartActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			ArrayList<HashMap<String, Object>> arraylist = null;
			try {
				arraylist = GetJsonData.SellerListJsonData(CartActivity.this);
			} catch (JSONException e) {
				return null;
			}
			return arraylist;
		}

		@Override
		protected void onPostExecute(
				final ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			if (result != null && result.size() > 0) { // 购物车不为空
				setContentView(R.layout.layout_cart);

				String flag = getIntent().getStringExtra("flag");

				cart_back = (TextView) findViewById(R.id.cart_back);
				cart_myorder = (TextView) findViewById(R.id.cart_myorder);

				cart_back.setOnClickListener(new ClickListener());
				cart_myorder.setOnClickListener(new ClickListener());

				if (!"y".equals(flag)) {
					cart_back.setVisibility(View.GONE);
					// cart_myorder.setVisibility(View.GONE);
				}

				listView = (ListView) findViewById(R.id.cart_list_business);
				cartListViewAdapter = new CartListViewAdapter(
						CartActivity.this, result);
				listView.setAdapter(cartListViewAdapter);
				/*实现加载必须*/
//				listView.setPullLoadEnable(true);
//				listView.setXListViewListener(CartActivity.this);
				/************/
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String shopid = (String) result.get(position).get(
								"id");
						AdvertisementBean.getAdver().setShopName(
								(String) result.get(position).get("name"));
						AdvertisementBean.getAdver().setShopId(shopid);

						intent = new Intent();
						// intent.setClass(CartActivity.this,CartDetailActivity.class);
						intent.setClass(CartActivity.this,
								HzyCartDetailActivity.class);
						intent.putExtra("shopid", shopid);
						startActivityForResult(intent, 1); // 回调刷新
					}
				});
			} else { // 购物车为空
				setContentView(R.layout.cart_null);
				// 返回
				cart_null_back = (TextView) findViewById(R.id.cart_null_back);
				cart_null_back.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();

					}
				});

				// 逛逛
				cart_null_go = (TextView) findViewById(R.id.cart_null_go);
				cart_null_go.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						intent = new Intent();
						intent.setClass(CartActivity.this,
								TabHostActivity.class);
						startActivity(intent);
						CartActivity.this.finish();
					}
				});

			}
			dialog.dismiss();
		}

	}

	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(CartActivity.this);
			return true;
		}
		return false;
	}

	/**
	 * 重写ActivityResult事件
	 */

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			new SellerListData().execute();
		}
	}

//	@Override
//	public void onRefresh() {
//		// TODO Auto-generated method stub
//		onLoad();
//	}
//
//	@Override
//	public void onLoadMore() {
//		mHandler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				
//				onLoad();
//
//			}
//
//		}, 2000);
//
//	}

//	private void onLoad() {
//		listView.stopRefresh();
//		listView.stopLoadMore();
//		listView.setRefreshTime("刚刚");
//	}
}
