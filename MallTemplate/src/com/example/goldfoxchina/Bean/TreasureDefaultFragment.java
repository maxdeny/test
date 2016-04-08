package com.example.goldfoxchina.Bean;

import com.example.goldfoxMall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TreasureDefaultFragment extends Fragment {

	public TreasureDefaultFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_searchdetail_treasure,
				container, false);
		view
				.findViewById(R.id.search_detail_treasure_gridview);
//		changeableAdapter = new InterchangeableAdapter(view.getContext(), data);
//		gridView.setAdapter(changeableAdapter);
		return view;
	}
}
