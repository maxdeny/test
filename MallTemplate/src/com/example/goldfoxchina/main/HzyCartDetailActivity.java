package com.example.goldfoxchina.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.goldfoxchina.Adapter.HzyCartDetailListViewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.util.ClassicMessageDAO;
import com.example.goldfoxchina.util.SPfSaveData;
import com.example.goldfoxMall.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单列表界面
 */
public class HzyCartDetailActivity extends Activity {
	private String shopId;
	private TextView back, home, figureOut, payForMoney;
	private ToggleButton choice;
	private ImageButton delete;
	private ListView listView;
	private Dialog dialog;

	private HzyCartDetailListViewAdapter adapter;

	private List<String> goodsIds = new ArrayList<String>();// 储存选择商品的ID
	private List<String> ids; // 选中的ids
	private ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();// 初始化进来的时候
																									// 列表中包含的内容
	private List<Integer> allChoice = new ArrayList<Integer>();// 所有选中的item
	private List<Integer> backList = new ArrayList<Integer>();// 返回设置point

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		shopId = getIntent().getStringExtra("shopid");
		setContentView(R.layout.hzy3_cart_detail);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new ProductListData(shopId).execute();
		init();// 初始化控件
		click();// 点击事件
	}

	// 初始化控件
	private void init() {
		back = (TextView) findViewById(R.id.hzy_cartdetail_back);// 返回按钮
		home = (TextView) findViewById(R.id.hzy_cartdetail_back_index);// 返回按钮
		figureOut = (TextView) findViewById(R.id.hzy_cart_shop_all_price);// 合计价格
		payForMoney = (TextView) findViewById(R.id.hzy_cart_shop_now_buy);// 去结算按钮
		choice = (ToggleButton) findViewById(R.id.hzy_cart_shop_select);// 全选按钮
		delete = (ImageButton) findViewById(R.id.hzy_cart_shop_del);// 删除按钮

	}

	// 点击事件
	private void click() {
		/* 返回按钮点击事件 */
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				onDestroy();
			}
		});

		/* Home按钮点击事件 */
		home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HzyCartDetailActivity.this,
						TabHostActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});
		/* 取消全选按钮 */
		choice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 当存在商品的时候才可以触发点击效果
				if (arraylist.size() > 0) {
					if (choice.isChecked()) {// 取消所以选择
						choice.setBackgroundResource(R.drawable.qx_n);
						backList = new ArrayList<Integer>();
						adapter.setPoint(backList);
					} else { // 全选
						choice.setBackgroundResource(R.drawable.wddd_qxbut_n);
						for (int k = 0; k < arraylist.size(); k++) {
							backList.add(k);
						}
						adapter.setPoint(backList);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});

		/* 删除按钮 */
		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				allChoice = adapter.getPoint(); // 获得所有被商品选中的position
				if (allChoice.size() > 0) {
					// String ids = "";
					// for (int i = 0; i < allChoice.size() - 1; i++) {
					// ids = ids + goodsIds.get(allChoice.get(i)) + ",";
					// }
					// ids = ids + goodsIds.get(allChoice.get(allChoice.size() -
					// 1));//拼接的position
					// Log.d("ids",ids+"");
					ids = new ArrayList<String>();
					for (int i = 0; i < allChoice.size(); i++) {
						ids.add(goodsIds.get(allChoice.get(i)));
					}
				}
				if (ids.size() > 0) {
					// 提示对话框
					AlertDialog.Builder builder = new Builder(
							HzyCartDetailActivity.this);
					builder.setTitle("提示")
							.setMessage("确定删除?")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											new ProductDetailData().execute();
											dialog.dismiss();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
										}
									}).show();

				} else {
					Toast.makeText(HzyCartDetailActivity.this,
							"您当前没有选择任何商品...", Toast.LENGTH_LONG).show();
				}

			}
		});
		/* 结算按钮 */
		payForMoney.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				allChoice = adapter.getPoint(); // 获得所有被商品选中的position
				if (allChoice != null && allChoice.size() > 0) {
					ArrayList<Integer> list_count = adapter.getRecordQuantity(); // 所有商品的count
					ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
					HashMap<String, Object> hashMap;
					for (int i = 0; i < allChoice.size(); i++) {
						hashMap = new HashMap<String, Object>();
						int position = allChoice.get(i);

						int inventoryCount = Integer.valueOf(arraylist
								.get(position).get("inventoryCount").toString()
								.trim()); // 库存
						int count = Integer.valueOf(list_count.get(position)
								.toString().trim()); // 数量
						if (!(count > inventoryCount)) {
							hashMap.put("id", (String) arraylist.get(position)
									.get("id"));
							hashMap.put("inventoryCount", inventoryCount); // 库存
							hashMap.put("name", (String) arraylist
									.get(position).get("commodityName"));
							hashMap.put(
									"color",
									(String) arraylist.get(position).get(
											"color"));
							hashMap.put("size", (String) arraylist
									.get(position).get("size"));
							hashMap.put(
									"price",
									(String) arraylist.get(position).get(
											"price"));
							hashMap.put("path", (Bitmap) arraylist
									.get(position).get("path"));
							hashMap.put("count", count);
							list.add(hashMap);
						} else {
							Toast.makeText(
									HzyCartDetailActivity.this,
									position + 1 + "号商品购买数量超出库存，库存量"
											+ inventoryCount,
									Toast.LENGTH_SHORT).show();
						}

					}

					AdvertisementBean.getAdver().setOrderList(list);

					intent = new Intent();

					// 数量
					int cont = ClassicMessageDAO.getClassicMessageDAO(
							HzyCartDetailActivity.this).SelCount();

					if (cont >= 1) { // 说明数据库中有数据 存在收货地址
						String name = SPfSaveData
								.getspf(HzyCartDetailActivity.this)
								.ReadData("name").toString().trim();

						if (!"".equals(name)) { // 说明设置了默认收货地址

							String telnum = SPfSaveData
									.getspf(HzyCartDetailActivity.this)
									.ReadData("telnum").toString().trim();
							String address = SPfSaveData
									.getspf(HzyCartDetailActivity.this)
									.ReadData("address").toString().trim();

							intent.setClass(HzyCartDetailActivity.this,
									ConfirmOrderActivity.class);
							intent.putExtra("name", name);
							intent.putExtra("telnum", telnum);
							intent.putExtra("address", address);

							// CartDetailActivity.this

							// ConfirmOrderActivity 订单确认
						} else {
							intent.setClass(HzyCartDetailActivity.this,
									SettlementAddressActivity.class);
						}

					} else { // 没数据。 不存在收货地址
						intent.setClass(HzyCartDetailActivity.this,
								SettlementAddressFillInActivity.class);

					}
					startActivity(intent);

				} else {
					Toast.makeText(HzyCartDetailActivity.this,
							"您当前没有选择任何商品...", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * 加载订单列表数据
	 */

	class ProductListData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
		String shopid;

		public ProductListData(String shopid) {
			this.shopid = shopid;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(HzyCartDetailActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			try {
				arraylist = GetJsonData.ProductListJsonData(
						HzyCartDetailActivity.this, shopid);
				goodsIds = new ArrayList<String>();// goodsId初始化
				for (int i = 0; i < arraylist.size(); i++) {
					String id = arraylist.get(i).get("id").toString();
					goodsIds.add(id);
				}
				return arraylist;
			} catch (Exception e) {
				return null;
			}

		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {
				/* listView适配 */
				listView = (ListView) findViewById(R.id.hzy_cartdetail_listview);// listView
				adapter = new HzyCartDetailListViewAdapter(
						HzyCartDetailActivity.this, result,
						HzyCartDetailActivity.this, choice, figureOut);
				listView.setAdapter(adapter);

			} else {
				// 提示对话框
				AlertDialog.Builder builder = new AlertDialog.Builder(
						HzyCartDetailActivity.this);
				builder.setTitle("提示")
						.setMessage("该商家中已经没有任何商品！")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent();
										intent.setClass(
												HzyCartDetailActivity.this,
												CartActivity.class);
										setResult(RESULT_OK, intent);
										finish();
									}
								}).show();
			}
		}

	}

	/**
	 * 删除数据请求
	 */
	class ProductDetailData extends
			AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(HzyCartDetailActivity.this, "删除中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {
			HashMap<String, String> hashmap = GetJsonData.ProductDelJsonData(
					HzyCartDetailActivity.this, ids);
			return hashmap;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {
				if ("success".equals(result.get("message"))) {
					Toast.makeText(HzyCartDetailActivity.this, "删除成功",
							Toast.LENGTH_SHORT).show();
					new ProductListData(shopId).execute();
				}
			}
		}

	}

}
