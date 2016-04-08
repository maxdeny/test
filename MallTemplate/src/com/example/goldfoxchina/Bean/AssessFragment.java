package com.example.goldfoxchina.Bean;

import com.example.goldfoxchina.Adapter.ProductDetailsAssessListViewAdapter;
import com.example.goldfoxMall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 评价fragment
 * 
 * @author kysl
 * 
 */
public class AssessFragment extends Fragment {

	private TextView score;
	private ListView assess_listView;
	private ProductDetailsAssessListViewAdapter assessListViewAdapter;

	public AssessFragment(ViewPager mPager) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater
				.inflate(R.layout.fragment_assess, container, false);
		score = (TextView) view.findViewById(R.id.score);
		assess_listView = (ListView) view.findViewById(R.id.assess_listview);
		assessListViewAdapter = new ProductDetailsAssessListViewAdapter(
				view.getContext(), AdvertisementBean.getAdver().getAssessList());
		assess_listView.setAdapter(assessListViewAdapter);

		return view;
	}

}
