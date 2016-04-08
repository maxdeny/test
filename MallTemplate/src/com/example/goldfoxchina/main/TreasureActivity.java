package com.example.goldfoxchina.main;

import java.util.ArrayList;

import com.example.goldfoxchina.Bean.TreasureDefaultFragment;
import com.example.goldfoxchina.Bean.TreasureHotFragment;
import com.example.goldfoxchina.Bean.TreasureNewFragment;
import com.example.goldfoxMall.R;

import android.content.res.Resources;
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
import android.widget.TextView;

/**
 * 搜索出的宝贝详细页面
 * @author kysl
 *
 */

public class TreasureActivity extends FragmentActivity {
	
	
	
	//返回
	private TextView search_treasure_back;
	
	// 默认  最热  最新
	private TextView treasure_default, treasure_hot,treasure_new;
	// 宝贝 店铺
	private Fragment fragment_default, fragment_hot,fragment_new;
	
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private Resources resources;
	private int position_one;
	private int position_two;
	private ImageView ivBottomLine;
	private int bottomLineWidth;
	private int currIndex = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_treasure);
		
		search_treasure_back=(TextView) findViewById(R.id.search_treasure_back);
		search_treasure_back.setOnClickListener(new ClickListener());
		
		resources=getResources();
		InitTextView();
		InitViewPager();
		InitWidth();
	}
	
	
	
	/**
	 * 初始化textView
	 */

	private void InitTextView() {
		treasure_default = (TextView) findViewById(R.id.treasure_default);
		treasure_hot = (TextView) findViewById(R.id.treasure_hot);
		treasure_new = (TextView) findViewById(R.id.treasure_new);
		
		
		treasure_default.setOnClickListener(new MyOnClickListener(0));
		treasure_hot.setOnClickListener(new MyOnClickListener(1));
		treasure_new.setOnClickListener(new MyOnClickListener(2));
	}

	/**
	 * 设置每一个title的fragment
	 */
	private void InitViewPager() {
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		fragment_default = new TreasureDefaultFragment();
		fragment_hot = new TreasureHotFragment();
		fragment_new=new TreasureNewFragment();
		fragmentsList.add(fragment_default);
		fragmentsList.add(fragment_hot);
		fragmentsList.add(fragment_new);

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
		position_one = (int) (screenW / 3.0);
		position_two = position_one * 2;
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
					treasure_hot.setTextColor(resources.getColor(R.color.black));
				}else if (currIndex == 2) {
					animation = new TranslateAnimation(position_two, 0, 0, 0);
					treasure_new.setTextColor(resources.getColor(R.color.black));
				}

				treasure_default.setTextColor(resources.getColor(R.color.red));
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_one, 0, 0);
					int color = resources.getColor(R.color.black);
					treasure_default.setTextColor(color);
				}else if (currIndex == 2) {
					animation = new TranslateAnimation(position_two,position_one,  0, 0);
					treasure_new.setTextColor(resources.getColor(R.color.black));
				}

				treasure_hot.setTextColor(resources.getColor(R.color.red));
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(0, position_two, 0, 0);
					int color = resources.getColor(R.color.black);
					treasure_default.setTextColor(color);
				}else if (currIndex == 1) {
					animation = new TranslateAnimation(position_one, position_two, 0, 0);
					treasure_hot.setTextColor(resources.getColor(R.color.black));
				}

				treasure_new.setTextColor(resources.getColor(R.color.red));
				break;
				
			}
			currIndex = arg0;
			animation.setFillAfter(true);
			animation.setDuration(300);
			ivBottomLine.startAnimation(animation);

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			

		}
	}
	
	public class ClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.search_treasure_back:
				finish();
				break;

			default:
				break;
			}
			
		}
		
	}
}
