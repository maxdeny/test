package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare.FrontiaTheme;
import com.example.goldfoxchina.Adapter.ProductDetailGridViewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.CustomGridView;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品详情
 * 
 * @author kysl
 * 
 */
public class ProductDetailsActivity extends Activity implements OnClickListener {

	// 返回
	private TextView detail_back;
	// 首页
	private TextView back_index;

	// 跳转购物车
	private TextView shop_gwc;

	/* 分享 */
	private TextView detail_share;
	private FrontiaSocialShare mSocialShare;

	private FrontiaSocialShareContent mImageContent = new FrontiaSocialShareContent();

	/**
	 * 原价 删除线
	 */
	private TextView details_price_pre;
	/* 现价 */
	private TextView details_price_now;
	/* 大图 */
	private ImageView details_img;
	/* 商品名称 */
	private TextView detail_content;
	/* 商品描述 */
	private TextView details_title;
	/* 商铺名称 */
//	private TextView details_shopname;
	/* 商铺描述 */
//	private TextView details_shopdescription;
	/* 卖家头像 */
//	private ImageView seller_head;
	/* 销量 */
	private TextView details_sail;
	/* 库存 */
	private TextView details_stock;
	/* 图片id */
	private String icon_url = "";
	/* 图片URL */
	private String img_url = "";
	/* 商品描述 底部 */
	private TextView productdetail_description;
	// 联系卖家
	private TextView contact;
	// 加入购物车
	private TextView cart;
	private Intent intent;
	// 立即购买
	private TextView buy;

	// 设置背景透明度
	private RelativeLayout shop_go;

	// 图片展示gridview
	private CustomGridView productdetail_gridview;
	private ProductDetailGridViewAdapter gridViewAdapter;

	// 评价
	private RelativeLayout layout_assess;
	private TextView assess_count;

	/* 店铺 */
//	private LinearLayout business;

	private Dialog dialog;

	/* 商品详情的ID */
	private String id = "";
	/* 店铺id */
	private String shopid;

	/* 电话 */
	private String telnum = "";

	int width, height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_productdetail);

		WindowManager wm = this.getWindowManager();

		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();

		Intent i = getIntent();
		id = i.getExtras().get("id").toString().trim();
		detail_back = (TextView) findViewById(R.id.detail_back);
		detail_back.setOnClickListener(this);

		/**
		 * 删除线
		 */
		details_price_pre = (TextView) findViewById(R.id.details_price_pre);
		if (details_price_pre.getText() != null
				|| !"".equals(details_price_pre.getText())) {
			details_price_pre.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}

		back_index = (TextView) findViewById(R.id.back_index);
		back_index.setOnClickListener(this);

		shop_gwc = (TextView) findViewById(R.id.shop_gwc);
		shop_gwc.setOnClickListener(this);
		detail_share = (TextView) findViewById(R.id.detail_share);
		detail_share.setOnClickListener(this);
		details_img = (ImageView) findViewById(R.id.details_img);
		/* 设置imageView的大小 */
		details_img.getLayoutParams().width = width;
		details_img.getLayoutParams().height = height / 3;

		detail_content = (TextView) findViewById(R.id.detail_content);
		details_title = (TextView) findViewById(R.id.details_title);
//		details_shopname = (TextView) findViewById(R.id.details_shopname);
//		details_shopdescription = (TextView) findViewById(R.id.details_shopdescription);
//		seller_head = (ImageView) findViewById(R.id.seller_head);
		details_sail = (TextView) findViewById(R.id.details_sail);
		details_stock = (TextView) findViewById(R.id.details_stock);
		details_price_now = (TextView) findViewById(R.id.details_price_now);
		productdetail_description = (TextView) findViewById(R.id.productdetail_description);

		/**
		 * gridview
		 */
		productdetail_gridview = (CustomGridView) findViewById(R.id.productdetail_gridview);

		shop_go = (RelativeLayout) findViewById(R.id.shop_go);
		shop_go.setBackgroundColor(Color.TRANSPARENT); // 设置布局背景为透明

		/**
		 * 联系卖家
		 */
		contact = (TextView) findViewById(R.id.contact);
		contact.setOnClickListener(this);

		/**
		 * 加入购物车
		 */
		cart = (TextView) findViewById(R.id.cart);
		cart.setOnClickListener(this);

		/**
		 * 立即购买
		 */
		buy = (TextView) findViewById(R.id.buy);
		buy.setOnClickListener(this);

		/**
		 * 店铺
		 */
//		business = (LinearLayout) findViewById(R.id.business);
//		business.setOnClickListener(this);

		/* 异步 */
		new ProductDetailData(this).execute();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detail_back:
			finish();
			ProductDetailsActivity.this.onDestroy();
			break;
		case R.id.back_index:   //返回首页
			intent = new Intent();
			intent.setClass(this, TabHostActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			ProductDetailsActivity.this.onDestroy();
			break;
		case R.id.business:     //其它布局  ？？？？？？？？
			intent = new Intent();
			intent.setClass(this, ShopIntroductionActivity.class);
			intent.putExtra("shopid", shopid);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			ProductDetailsActivity.this.finish();
			ProductDetailsActivity.this.onDestroy();
			break;
		case R.id.shop_gwc:
			intent = new Intent();
			intent.setClass(this, CartActivity.class);
			intent.putExtra("flag", "y");
			startActivity(intent);
			finish();
			ProductDetailsActivity.this.onDestroy();
			break;
		case R.id.detail_share:

			Frontia.init(this.getApplicationContext(), Config.APIKEY);
			mSocialShare = com.baidu.frontia.Frontia.getSocialShare();
			mSocialShare.setContext(this);
			mSocialShare.setClientId(MediaType.SINAWEIBO.toString(),
					Config.SINA_APP_KEY);
			mSocialShare.setClientId(MediaType.QZONE.toString(), "100358052");
			mSocialShare
					.setClientId(MediaType.QQFRIEND.toString(), "100358052");
			mSocialShare.setClientName(MediaType.QQFRIEND.toString(), "微猫");
			mSocialShare.setClientId(MediaType.WEIXIN.toString(),
					"wx329c742cb69b41b8");

			mImageContent.setTitle(detail_content.getText().toString()); // 商品名称
			mImageContent.setContent(details_title.getText().toString()); // 商品描述
			mImageContent.setLinkUrl("http://www.goldfoxchina.com"); // 文字链接
			String cookieid = CookieID.getCookieID().getCookieid();
			if (!"".equals(cookieid)) {
				if (!"".equals(icon_url) || null != icon_url) { // 如果图片id不为空

					img_url = (Config.ImageURL(cookieid, icon_url) + ".png")
							.trim();
					mImageContent.setImageUri(Uri.parse(img_url)); // 分享的图片
				}
			} else {
				mImageContent.setImageUri(null);
			}

			mSocialShare.show(ProductDetailsActivity.this.getWindow()
					.getDecorView(), mImageContent, FrontiaTheme.DARK,
					new ShareListener());
			break;
		case R.id.contact: // 联系卖家
			// 提示对话框
			AlertDialog.Builder builder = new Builder(this);
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
		case R.id.cart:
		case R.id.buy:
			intent = new Intent();
			intent.putExtra("id", id);
			intent.putExtra("img", icon_url);
			intent.putExtra("content", detail_content.getText().toString());
			intent.setClass(ProductDetailsActivity.this,
					ProductDetail_Alert_Cart.class);
			startActivity(intent);
			break;
		case R.id.layout_assess:
			intent = new Intent();
			intent.setClass(this, AssessActivity.class);
			startActivity(intent);
			break;
		}

	}

	class ProductDetailData extends
			AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {
		Context context;

		public ProductDetailData(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(ProductDetailsActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
			new AssessData().execute();
		}

		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {

			HashMap<String, Object> hashmap = null;
			try {
				hashmap = GetJsonData.ProductDetailJsonData(
						ProductDetailsActivity.this, id);
				return hashmap;
			} catch (Exception e) {
				return null;
			}

		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {
				details_img.setImageBitmap(((ArrayList<Bitmap>) result
						.get("icons")).get(0));
				icon_url = result.get("icon_url").toString();
				detail_content.setText(result.get("name").toString());
				details_title.setText(result.get("description").toString());
				productdetail_description.setText(result.get("description")
						.toString());
//				details_shopname.setText(result.get("shopName").toString());
//				details_shopdescription.setText(result.get("shopDescription")
//						.toString());
//				seller_head.setImageBitmap((Bitmap) result.get("shopLogo"));
				details_sail.setText(result.get("sales") + "件");
				details_stock.setText(result.get("inventoryCount") + "件");
				details_price_pre.setText(result.get("bidPrice").toString());
				details_price_now
						.setText(result.get("sellingPrice").toString());
				shopid = result.get("shopid").toString();

				telnum = result.get("phone").toString();
				gridViewAdapter = new ProductDetailGridViewAdapter(  //!!
						ProductDetailsActivity.this,
						(ArrayList<Bitmap>) result.get("icons"));
				AdvertisementBean.getAdver().setBitmaplist(
						(ArrayList<Bitmap>) result.get("icons"));
				productdetail_gridview.setAdapter(gridViewAdapter);
				productdetail_gridview
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// 图片点击展示
								Intent intent = new Intent();
								intent.setClass(ProductDetailsActivity.this,
										PhotoGallery_Alert_Activity.class);
								intent.putExtra("position", position);
								startActivity(intent);

							}
						});

			} else {
				// 如果服务器无响应或关闭，弹出提示
				GetNetWorkData.ServerMessage(ProductDetailsActivity.this, "");
				// }
			}

		}
	}

	/**
	 * 评价
	 * 
	 * @author kysl
	 * 
	 */

	class AssessData extends
			AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {

		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			HashMap<String, Object> hashMap = null;
			try {
				hashMap = GetJsonData.getAssessJsonData(
						ProductDetailsActivity.this, id, 0, 30);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return hashMap;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			super.onPostExecute(result);
			assess_count = (TextView) findViewById(R.id.assess_count);
			/**
			 * 评价
			 */
			layout_assess = (RelativeLayout) findViewById(R.id.layout_assess);

			if (result != null) { // 返回结果不为空
				int count = Integer.valueOf(result.get("total").toString()
						.trim());
				assess_count.setText(count + "");
				if (count > 0) { // 评论数量大于0
					layout_assess
							.setOnClickListener(ProductDetailsActivity.this);
					AdvertisementBean.getAdver().setAssessList(
							(ArrayList<HashMap<String, Object>>) result
									.get("content"));
				}
			}
		}

	}

	private class ShareListener implements FrontiaSocialShareListener {

		@Override
		public void onSuccess() {
			Log.d("Test", "share success");
			Toast.makeText(ProductDetailsActivity.this, "分享成功",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onFailure(int errCode, String errMsg) {
			Log.d("Test", "share errCode " + errCode);
			// Toast.makeText(ProductDetailsActivity.this, errMsg,
			// Toast.LENGTH_LONG).show();
		}

		@Override
		public void onCancel() {
			Log.d("Test", "cancel ");
			// Toast.makeText(ProductDetailsActivity.this, "取消",
			// Toast.LENGTH_LONG).show();
		}

	}
}
