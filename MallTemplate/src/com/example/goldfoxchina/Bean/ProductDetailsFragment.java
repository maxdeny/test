package com.example.goldfoxchina.Bean;



import com.example.goldfoxchina.Adapter.ProductDetailGridViewAdapter;
import com.example.goldfoxMall.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 商品详情页面图片展示的fragment
 * @author kysl
 *
 */
public class ProductDetailsFragment extends Fragment {
	
	
	
	private GridView gridView;
	private TextView textView;
	private ProductDetailGridViewAdapter gridViewAdapter;
	
	public ProductDetailsFragment(ViewPager mPager) {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.fragment_productdetail, container, false);
		gridView=(GridView) view.findViewById(R.id.productdetail_gridview);
		textView=(TextView) view.findViewById(R.id.productdetail_description);
		
//		gridViewAdapter=new ProductDetailGridViewAdapter(view.getContext(), data);
		
		gridView.setAdapter(gridViewAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
			}
		});
		
		return view;
	}
	
}
