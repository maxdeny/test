package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.FragmentAdapter;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.Bean.SearchShopFragment;
import com.example.goldfoxchina.Bean.SearchTreasureFragment;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.AsyncTask;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchItemActivity extends FragmentActivity {

	/**
	 * 返回 搜索
	 */
	private TextView searchitem_back, search_buttn;
	private EditText search_content;

	private ArrayList<HashMap<String, Object>> arraylist = null;

	// 宝贝 店铺
	private TextView search_treasure;
//	private TextView search_shop;
	private ArrayList<Fragment> fragmentsList;

	// 宝贝 店铺
	private Fragment fragment_search_treasure, fragment_search_shop;
	private ViewPager mPager;
	/**
	 * gridview fragment
	 */
	private Resources resources;
	private int position_one;
	private ImageView ivBottomLine;
	private int bottomLineWidth;
	private int currIndex = 0;

	private Dialog dialog;

	/* 宝贝数据 */
	private ArrayList<HashMap<String, Object>> treasure_list = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search_item);
		resources = getResources();
		search_content = (EditText) findViewById(R.id.search_content);
		searchitem_back = (TextView) findViewById(R.id.searchitem_back);
		searchitem_back.setOnClickListener(new ClickListener());
		search_buttn = (TextView) findViewById(R.id.search_buttn);
		search_buttn.setOnClickListener(new ClickListener());
		
		String flag=getIntent().getExtras().getString("flag");
		if("y".equals(flag)){
			search_content.setText(getIntent().getExtras().getString("content").trim());
			search_buttn.performClick();   //模拟点击
		}
		
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
//					search_shop.setTextColor(resources.getColor(R.color.black));
				}

				search_treasure.setTextColor(resources.getColor(R.color.red));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_one, 0, 0);
					int color = resources.getColor(R.color.black);
					search_treasure.setTextColor(color);
				}

//				search_shop.setTextColor(resources.getColor(R.color.red));
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

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.searchitem_back:
				finish();
				break;
			case R.id.search_buttn:
				String content = search_content.getText().toString().trim();
				if (!"".equals(content)) {
					new SearchData(content).execute();
				}
				break;
			default:
				break;
			}

		}

	}

	class SearchData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {

		String content;

		public SearchData(String content) {
			this.content = content;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SearchItemActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();

		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			try {
				arraylist = GetJsonData.SearchCategoriesJsonData(
						SearchItemActivity.this, 0, 30, content);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arraylist;
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null) {
				treasure_list = result;
				InitWidth();
				InitTextView();
				InitViewPager();
			} else {
					// 如果服务器无响应或关闭，弹出提示
					GetNetWorkData.ServerMessage(SearchItemActivity.this, "");
			}
		}

		/**
		 * 初始化textView
		 */

		private void InitTextView() {
			search_treasure = (TextView) findViewById(R.id.search_treasure);
//			search_shop = (TextView) findViewById(R.id.search_shop);

			search_treasure.setOnClickListener(new MyOnClickListener(0));
//			search_shop.setOnClickListener(new MyOnClickListener(1));
		}

		/**
		 * 设置每一个title的fragment
		 */
		private void InitViewPager() {
			mPager = (ViewPager) findViewById(R.id.vPager);
			fragmentsList = new ArrayList<Fragment>();
			fragment_search_treasure = new SearchTreasureFragment(mPager,
					treasure_list);
			fragment_search_shop = new SearchShopFragment(mPager);
			
			fragmentsList.add(fragment_search_treasure);
//			fragmentsList.add(fragment_search_shop);

			mPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
					fragmentsList));
			mPager.setCurrentItem(0);
			mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		}

		private void InitWidth() {
			ivBottomLine = (ImageView) findViewById(R.id.iv_bottom_line);
			bottomLineWidth = ivBottomLine.getLayoutParams().width;
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenW = dm.widthPixels;
			position_one = (int) (screenW / 2.0);
		}
	}

}
