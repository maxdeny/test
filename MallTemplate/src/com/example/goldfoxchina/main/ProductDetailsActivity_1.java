package com.example.goldfoxchina.main;

import java.util.ArrayList;

import com.example.goldfoxchina.Bean.AssessFragment;
import com.example.goldfoxchina.Bean.ProductDetailsFragment;
import com.example.goldfoxMall.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 商品详情      商品详情和评价 fragment布局
 * 未调用   预留
 * @author kysl
 * 
 */
public class ProductDetailsActivity_1 extends FragmentActivity implements
		OnClickListener {

	/**
	 * 删除线
	 */
	private TextView details_price_pre;

	/**
	 * gridview fragment
	 */
	private Resources resources;
	private int offset = 0;
	private int position_one;
	// private int position_two;
	private ImageView ivBottomLine;
	private int bottomLineWidth;
	private int currIndex = 0;

	// 商品详情 评价
	private TextView productdetail_description, productdetail_assess;
	private ArrayList<Fragment> fragmentsList;
	private Fragment fragment_Productdetail, fragment_Assess;
	private ViewPager mPager;

	// 联系卖家
	private TextView contact;
	// 加入购物车
	private TextView cart;
	private Intent intent;
	// 立即购买
	private TextView buy;
	
	//设置背景透明度
	private RelativeLayout shop_go;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_productdetail_1);

		
		//设置布局背景为透明
		
		shop_go=(RelativeLayout) findViewById(R.id.shop_go);
		shop_go.setBackgroundColor(Color.TRANSPARENT);
		/**
		 * 删除线
		 */
		details_price_pre = (TextView) findViewById(R.id.details_price_pre);
		if (details_price_pre.getText() != null
				|| details_price_pre.getText() != "") {
			details_price_pre.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		}
		resources = getResources();

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

		InitWidth();
		InitTextView();
		InitViewPager();

	}

	/**
	 * 初始化textView
	 */

	private void InitTextView() {
		productdetail_description = (TextView) findViewById(R.id.productdetail_description);
		productdetail_assess = (TextView) findViewById(R.id.productdetail_assess);
	}

	/**
	 * 设置每一个title的fragment
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		fragment_Productdetail = new ProductDetailsFragment(mPager);
		fragment_Assess = new AssessFragment(mPager);

		fragmentsList.add(fragment_Productdetail);
		fragmentsList.add(fragment_Assess);

		mPager.setAdapter(new com.example.goldfoxchina.Adapter.FragmentAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void InitWidth() {
		ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
		bottomLineWidth = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (int) ((screenW / 2.0 - bottomLineWidth) / 2);
		position_one = (int) (screenW / 2.0);
		// position_two = position_one * 2;
	}

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, 0, 0, 0);
					productdetail_assess.setTextColor(resources
							.getColor(R.color.black));
				}

				productdetail_description.setTextColor(resources
						.getColor(R.color.red));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_one, 0, 0);
					productdetail_description.setTextColor(resources
							.getColor(R.color.black));
				}

				productdetail_assess.setTextColor(resources
						.getColor(R.color.red));
				break;

			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.contact: // 联系卖家
			// 提示对话框
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("确认拨打")
					.setMessage(
							"联系电话:"
									+ com.example.goldfoxchina.util.Config.telnum)
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 用intent启动拨打电话
									Intent intent = new Intent(
											Intent.ACTION_CALL,
											Uri.parse("tel:"
													+ com.example.goldfoxchina.util.Config.telnum));
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
			intent=new Intent();
			intent.setClass(ProductDetailsActivity_1.this, ProductDetail_Alert_Cart.class);
			startActivity(intent);
			break;

		}

	}

}
