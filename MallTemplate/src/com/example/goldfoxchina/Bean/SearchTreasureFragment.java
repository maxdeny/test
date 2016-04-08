package com.example.goldfoxchina.Bean;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.SearchItemTreasureListViewAdapter;
import com.example.goldfoxchina.main.ProductDetailsActivity;
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
 * 搜索 宝贝
 * 
 * @author kysl
 * 
 */

public class SearchTreasureFragment extends Fragment {

	private SearchItemTreasureListViewAdapter listViewAdapter;
	
	private ArrayList<HashMap<String, Object>> arraylist;
	public SearchTreasureFragment(ViewPager mPager,ArrayList<HashMap<String, Object>> arraylist) {
		this.arraylist=arraylist;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search_treasure,
				container, false);
		ListView listView=(ListView) view.findViewById(R.id.fragment_listview);
		listViewAdapter=new SearchItemTreasureListViewAdapter(view.getContext(), arraylist);
		listView.setAdapter(listViewAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
//				intent.setClass(view.getContext(), TreasureActivity.class);
				intent.setClass(view.getContext(), ProductDetailsActivity.class);
				intent.putExtra("id",arraylist.get(position).get("id").toString());
				startActivity(intent);
				
			}
		});
		return view;

	}

}
