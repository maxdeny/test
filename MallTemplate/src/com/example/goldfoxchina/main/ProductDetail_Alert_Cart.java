package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;

import com.example.goldfoxchina.Adapter.ProductDetailAlertCartColorAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.CustomGridView;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.util.ClassicMessageDAO;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxchina.util.FileCache;
import com.example.goldfoxchina.util.SPfSaveData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品详情 加入购物车的弹出框
 * 
 * @author kysl
 * 
 */

public class ProductDetail_Alert_Cart extends Activity {

	private ProductDetailAlertCartColorAdapter alertCartColorAdapter;
	private CustomGridView alert_gridview_color;
	// 全局变量，记录选中的item
	public static int select_item_color = -1; // 默认选中第一个

	// 关闭
	private TextView alert_close;

	// 图片
	private ImageView alert_details_img;

	// 描述
	private TextView alert_details_content;

	// 价格
	private TextView detail_alert_price;

	// 数量加减
	private TextView bt_cut, bt_add;
	private EditText quantity;
	// 库存
	private TextView inventory;
	// 假设库存为10
	int inventory_count = 0;

	private int count = 0;

	// 加入购物车
	private TextView add_cart;

	// 立即购买
	private TextView now_buy;

	/* 商品ID */
	private String id = "";
	private String img = "";
	private String content = "";
	private Bitmap bitmap = null;
	private String color = "";
	private String size = "";
	private String price = "";
	private Intent intent;

	ArrayList<HashMap<String, String>> data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.product_detail_alert_cart);
		Window window = getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		// 设置窗口的大小及透明度
		layoutParams.width = LayoutParams.FILL_PARENT;
		layoutParams.height = layoutParams.WRAP_CONTENT;
		layoutParams.alpha = 1.0f;
		window.setAttributes(layoutParams);

		id = getIntent().getStringExtra("id");
		img = getIntent().getStringExtra("img");
		content = getIntent().getStringExtra("content");
		/**
		 * 关闭
		 */
		alert_close = (TextView) findViewById(R.id.alert_close);
		alert_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ProductDetail_Alert_Cart.this.onDestroy();
				ProductDetail_Alert_Cart.this.finish();
			}
		});

		/**
		 * 数量加减
		 */
		bt_cut = (TextView) findViewById(R.id.bt_cut);
		bt_add = (TextView) findViewById(R.id.bt_add);
		quantity = (EditText) findViewById(R.id.quantity);

		bt_cut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				isNull();

				// 获取EditText中数量
				count = Integer.valueOf(quantity.getText().toString().trim());
				if (count > 0 && count <= inventory_count) {
					count = count - 1;
					quantity.setText(count + "");
				} else if (count < 0) {
					quantity.setText(0 + "");
				} else if (count > inventory_count) {
					quantity.setText(inventory_count + "");

				}

			}
		});

		bt_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				isNull();
				// 获取EditText中数量
				count = Integer.valueOf(quantity.getText().toString().trim());
				if (count < inventory_count) {
					count = count + 1;
					quantity.setText(count + "");
				} else if (count > inventory_count) {
					quantity.setText(inventory_count + "");

				}

			}

		});

		new ProductColAndSizeData(this).execute();

	}

	/**
	 * 判断获取到的EditText中的值是否是空值
	 */
	private void isNull() {
		String str = quantity.getText().toString().trim();
		if (str == null || "".equals(str)) {
			quantity.setText(0 + "");
		}
	}

	class ProductColAndSizeData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, String>>, ArrayList<HashMap<String, String>>> {
		private Dialog dialog;
		Context context;

		public ProductColAndSizeData(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(ProductDetail_Alert_Cart.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				Void... params) {
			ArrayList<HashMap<String, String>> arrayList = null;

			try {
				arrayList = GetJsonData.ProductDetailColAndSizeJsonData(
						context, id);
				String cookieid = CookieID.getCookieID().getCookieid();
				if (!"".equals(cookieid)) {
					bitmap = new FileCache(context).getBitmap(Config.ImageURL(
							cookieid, img));    //   img是传递过来的icon_url
				} else {
					return null;
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}

			return arrayList;
		}

		@Override
		protected void onPostExecute(
				final ArrayList<HashMap<String, String>> result) {
			super.onPostExecute(result);

			// 价格
			detail_alert_price = (TextView) findViewById(R.id.detail_alert_price);

			// 库存
			inventory = (TextView) findViewById(R.id.inventory);
			/* 加入购物车 */
			add_cart = (TextView) findViewById(R.id.add_cart);
			/* 立即购买 */
			now_buy = (TextView) findViewById(R.id.now_buy);

			if (result != null && result.size() > 0) {

				data = result;

				/**
				 * gridview
				 */

				alertCartColorAdapter = new ProductDetailAlertCartColorAdapter(
						ProductDetail_Alert_Cart.this, result,
						detail_alert_price, inventory, quantity);

				alert_gridview_color = (CustomGridView) findViewById(R.id.alert_gridview_color);

				alert_gridview_color.setAdapter(alertCartColorAdapter);

				alert_gridview_color
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								select_item_color = position; // 当前选择的节目item
								alertCartColorAdapter.notifyDataSetChanged(); // 通知adapter刷新数据
								color = data.get(position).get("color");
								size = data.get(position).get("size");
								inventory_count = Integer.valueOf(data.get(
										position).get("inventoryCount"));
							}
						});

				alert_details_img = (ImageView) findViewById(R.id.alert_details_img);
				alert_details_img.setImageBitmap(bitmap);

				alert_details_content = (TextView) findViewById(R.id.alert_details_content);
				alert_details_content.setText(content);
				/* 加入购物车 */
				add_cart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						try {
							if (select_item_color >= 0) {
								count = Integer.valueOf(quantity.getText()
										.toString().trim());
								if (!(count > inventory_count)) {
									String status = GetJsonData
											.ProductToAddCartJsonData(
													ProductDetail_Alert_Cart.this,
													result.get(
															select_item_color)
															.get("id"),
													quantity.getText()
															.toString()).get(
													"data");
									if ("success".equals(status)) {
										Toast.makeText(
												ProductDetail_Alert_Cart.this,
												"加入购物车成功", Toast.LENGTH_SHORT)
												.show();
									} else {
										Toast.makeText(
												ProductDetail_Alert_Cart.this,
												"加入购物车失败", Toast.LENGTH_SHORT)
												.show();
									}
									ProductDetail_Alert_Cart.this.finish();
								} else {
									Toast.makeText(context, "购买数量超出库存",
											Toast.LENGTH_SHORT).show();
								}
							} else {
								Toast.makeText(context, "请选择商品规格",
										Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

				/* 立即购买 */
				now_buy.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						intent=new Intent();
						ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
						count = Integer.valueOf(quantity.getText().toString()
								.trim());
						price = detail_alert_price.getText().toString();
						if (select_item_color >= 0) {
							if (count <= inventory_count) {
								HashMap<String, Object> hashMap = new HashMap<String, Object>();
								hashMap.put("id", id);
								hashMap.put("inventoryCount", inventory_count); // 库存
								hashMap.put("name", content);
								hashMap.put("color", color);
								hashMap.put("size", size);
								hashMap.put("price", price);
								hashMap.put("path", bitmap);
								hashMap.put("count", count);
								arraylist.add(hashMap);
								AdvertisementBean.getAdver().setOrderList(
										arraylist);
								// 数量
								int count = ClassicMessageDAO
										.getClassicMessageDAO(
												ProductDetail_Alert_Cart.this)
										.SelCount();

								if (count >= 1) { // 说明数据库中有数据 存在收货地址
									String name = SPfSaveData
											.getspf(ProductDetail_Alert_Cart.this)
											.ReadData("name").toString().trim();

									if (!"".equals(name)) { // 说明设置了默认收货地址

										String telnum = SPfSaveData
												.getspf(ProductDetail_Alert_Cart.this)
												.ReadData("telnum").toString()
												.trim();
										String address = SPfSaveData
												.getspf(ProductDetail_Alert_Cart.this)
												.ReadData("address").toString()
												.trim();

										intent.setClass(
												ProductDetail_Alert_Cart.this,
												ConfirmOrderActivity.class);
										intent.putExtra("name", name);
										intent.putExtra("telnum", telnum);
										intent.putExtra("address", address);

										// CartDetailActivity.this

										// ConfirmOrderActivity 订单确认
									} else {
										intent.setClass(
												ProductDetail_Alert_Cart.this,
												SettlementAddressActivity.class);
									}

								} else { // 没数据。 不存在收货地址
									intent.setClass(
											ProductDetail_Alert_Cart.this,
											SettlementAddressFillInActivity.class);

								}
								startActivity(intent);
							} else {
								Toast.makeText(ProductDetail_Alert_Cart.this,
										"购买数量大于库存", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(ProductDetail_Alert_Cart.this,
									"请选择商品规格", Toast.LENGTH_SHORT).show();
						}
					}
				});

			} else {
				// 提示对话框
				AlertDialog.Builder builder = new Builder(context);
				builder.setTitle("提示")
						.setMessage("数据不全，无法购买该物品！")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();

			}
			// else{
			// // 如果服务器无响应或关闭，弹出提示
			// GetNetWorkData.ServerMessage(context,"");
			// }
			dialog.dismiss();
		}
	}

}
