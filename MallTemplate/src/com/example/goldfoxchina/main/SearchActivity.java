package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.IndexGalleryAdapter;
import com.example.goldfoxchina.Adapter.SearchGridviewAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.DetialGallery;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 快寻
 * 
 * @author kysl
 * 
 */
public class SearchActivity extends Activity {

	// 放小圆点的容器
	private LinearLayout circle_layout;

	// 小圆点滑动初始化第一个
	int move = 0;
	// 装小圆点的集合
	private ImageView[] imageViews;
	/**
	 * index style gridview
	 */
	private GridView gridView;
	private SearchGridviewAdapter gridViewAdapter;

	private ArrayList<HashMap<String, Object>> arraylist1, arraylist5;

	/**
	 * 搜索按钮
	 */
	private TextView text_search_item;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_search);

		text_search_item = (TextView) findViewById(R.id.text_search_item);
		text_search_item.setOnClickListener(new ClickListener());

		DetialGallery gallery = (DetialGallery) findViewById(R.id.search_gallery);

		arraylist1 = AdvertisementBean.getAdver().getArraylist1();
		arraylist5 = AdvertisementBean.getAdver().getArraylist5();

		/**
		 * 与gallery的图片数相等，可以方法动态实现
		 */
		if (arraylist5 != null && arraylist1 != null) {
			// 初始化数组
			imageViews = new ImageView[arraylist1.size()];
			addCircle(arraylist1.size());
			gallery.setAdapter(new IndexGalleryAdapter(SearchActivity.this,
					arraylist1));
			gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					listener(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
			gallery.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent();
					intent.setClass(SearchActivity.this,
							ProductDetailsActivity.class);
					intent.putExtra("id", ((String) arraylist1.get(position)
							.get("id")));
					startActivity(intent);

				}
			});

			/**
			 * gridview
			 */
			gridView = (GridView) findViewById(R.id.search_style_gridview);

			gridViewAdapter = new SearchGridviewAdapter(this, arraylist5);

			gridView.setAdapter(gridViewAdapter);
			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent = new Intent();
					intent.setClass(SearchActivity.this,
							SearchItemActivity.class);
					intent.putExtra("flag", "y");
					intent.putExtra("content",
							arraylist5.get(position).get("id").toString());
					startActivity(intent);

				}
			});
		}else{
			GetNetWorkData.DialogMessage(this, "");
		}
	}

	/**
	 * 添加圆点的方法
	 */
	private void addCircle(int count) {
		circle_layout = (LinearLayout) findViewById(R.id.circle_holder);
		for (int i = 0; i < count; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(15, 15));
			imageView.setPadding(5, 0, 5, 0);
			imageViews[i] = imageView;
			if (i == move) {
				imageViews[i].setBackgroundResource(R.drawable.select_yes);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.select_no);
			}
			circle_layout.addView(imageView);
		}
	}

	private void listener(int id) {
		for (int i = 0; i < imageViews.length; i++) {
			if (i == id) {
				imageViews[i].setBackgroundResource(R.drawable.select_yes);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.select_no);
			}
		}
	}

	public class ClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.text_search_item:
				Intent intent = new Intent();
				intent.setClass(SearchActivity.this, SearchItemActivity.class);
				intent.putExtra("flag", "n");
				startActivity(intent);
				break;
			}

		}

	}

	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(SearchActivity.this);
			return true;
		}
		return false;
	}
}
