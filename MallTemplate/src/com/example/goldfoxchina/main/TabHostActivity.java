package com.example.goldfoxchina.main;

import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxMall.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * tabhost页面
 * 
 * @author kysl
 * 
 */
public class TabHostActivity extends TabActivity implements
		OnCheckedChangeListener {

	public TabHost tabHost;
	private RadioButton main_radiobtn_index, main_radiobtn_sort,
			main_radiobtn_shop, main_radiobtn_search, main_radiobtn_cart;
	private Intent indexIntent, sortIntent, shopIntent, searchIntent,
			cartIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tabhost);

		tabHost = this.getTabHost();
		initRadios();
		addTabSpec();
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.main_index:
				tabHost.setCurrentTabByTag("index");
				break;
			case R.id.main_sort:
				tabHost.setCurrentTabByTag("categories");
				break;
			case R.id.main_shop:
				tabHost.setCurrentTabByTag("shop");
				break;
			case R.id.main_search:
				tabHost.setCurrentTabByTag("search");
				break;
			case R.id.main_cart:
				tabHost.setCurrentTabByTag("cart");
				break;
			}
		}
	}

	private void initRadios() {

		main_radiobtn_index = (RadioButton) findViewById(R.id.main_index);
		main_radiobtn_sort = (RadioButton) findViewById(R.id.main_sort);
		main_radiobtn_shop = (RadioButton) findViewById(R.id.main_shop);
		main_radiobtn_search = (RadioButton) findViewById(R.id.main_search);
		main_radiobtn_cart = (RadioButton) findViewById(R.id.main_cart);

		main_radiobtn_index.setOnCheckedChangeListener(this);
		main_radiobtn_sort.setOnCheckedChangeListener(this);
		main_radiobtn_shop.setOnCheckedChangeListener(this);
		main_radiobtn_search.setOnCheckedChangeListener(this);
		main_radiobtn_cart.setOnCheckedChangeListener(this);

	}

	/**
	 * 添加tab事件
	 */
	private void addTabSpec() {

		tabHost = this.getTabHost();

		TabHost.TabSpec tabSpec_index = tabHost.newTabSpec("index");
		tabSpec_index.setIndicator("index", null);
		indexIntent = new Intent();
		indexIntent.setClass(this, IndexActivity.class);
		tabSpec_index.setContent(indexIntent);
		tabHost.addTab(tabSpec_index);

		TabHost.TabSpec tabSpec_categories = tabHost.newTabSpec("categories");
		tabSpec_categories.setIndicator("categories", null);
		sortIntent = new Intent();
		sortIntent.setClass(this, SortActivity.class);
		tabSpec_categories.setContent(sortIntent);
		tabHost.addTab(tabSpec_categories);

		TabHost.TabSpec tabSpec_shop = tabHost.newTabSpec("shop");
		tabSpec_shop.setIndicator("shop", null);
		shopIntent = new Intent();
		shopIntent.setClass(this, SearchActivity.class);
		tabSpec_shop.setContent(shopIntent);
		tabHost.addTab(tabSpec_shop);

		TabHost.TabSpec tabSpec_search = tabHost.newTabSpec("search");
		tabSpec_search.setIndicator("search", null);
		searchIntent = new Intent();
		searchIntent.setClass(this, CartActivity.class);
		tabSpec_search.setContent(searchIntent);
		tabHost.addTab(tabSpec_search);

		TabHost.TabSpec tabSpec_cart = tabHost.newTabSpec("cart");
		tabSpec_cart.setIndicator("cart", null);
		cartIntent = new Intent();
		cartIntent = new Intent(this, SetActivity.class);
		cartIntent.putExtra("flag", "n");
		tabSpec_cart.setContent(cartIntent);
		tabHost.addTab(tabSpec_cart);

	}

}
