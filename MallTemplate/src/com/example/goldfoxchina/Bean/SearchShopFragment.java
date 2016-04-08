package com.example.goldfoxchina.Bean;

import com.example.goldfoxchina.Adapter.SearchItemShopListViewAdapter;
import com.example.goldfoxchina.main.ShopIntroductionActivity;
import com.example.goldfoxMall.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


/**
 * 搜索店铺
 * @author kysl
 *
 */
public class SearchShopFragment extends Fragment {

	private String[] data = { "123", "测试", "这是测试", "你个二货" };
	
	private SearchItemShopListViewAdapter listViewAdapter;
	
	public SearchShopFragment(ViewPager mPager) {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//定义item布局
		View view = inflater.inflate(R.layout.fragment_search_shop, container, false);
		ListView listView=(ListView) view.findViewById(R.id.fragment_shop_listview);
		listViewAdapter=new SearchItemShopListViewAdapter(view.getContext(), data);
		listView.setAdapter(listViewAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
				intent.setClass(view.getContext(), ShopIntroductionActivity.class);
				startActivity(intent);
				
			}
		});
		
		return view;
	}
}
