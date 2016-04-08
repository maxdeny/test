package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.IndexGalleryAdapter;
import com.example.goldfoxchina.Adapter.IndexGridViewLayoutAdapter1;
import com.example.goldfoxchina.Adapter.IndexGridViewLayoutAdapter2;
import com.example.goldfoxchina.Adapter.IndexGridviewAdapter;
import com.example.goldfoxchina.Bean.*;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

/**
 * 首页
 * 
 * @author kysl
 *         <p/>
 *         通过containsKey() 判断hashmap中是否包含此键值 如包含“key”，则返回true，否则false。
 */
public class IndexActivity extends Activity {

	// 放小圆点的容器
	private LinearLayout circle_layout;

	// 小圆点滑动初始化第一个
	int move = 0;
	// 装小圆点的集合
	private ImageView[] imageViews;
	/**
	 * index style gridview
	 */
	private CustomGridView gridView, gridView1;
	//, gridView2;
	private IndexGridviewAdapter gridViewAdapter;
	/* scrollview */
	// private ScrollView index_scrollview;

	// set
//	private TextView sy_setup;
	private Dialog dialog;
	private ArrayList<HashMap<String, Object>> adlist1, adlist2, adlist3,
			adlist4, adlist5;
	ArrayList<HashMap<String, Object>> Listdata;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_index);
//		sy_setup = (TextView) findViewById(R.id.sy_setup);
//		sy_setup.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(IndexActivity.this, SetActivity.class);
//				startActivity(intent);
//
//			}
//		});

		new AdverDetailData().execute();

	}

	class AdverDetailData extends
			AsyncTask<Void, HashMap<String, Object>, HashMap<String, Object>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(IndexActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected HashMap<String, Object> doInBackground(Void... params) {
			HashMap<String, Object> arraylist = null;
			try {
				arraylist = GetJsonData.getIndexJsonData(IndexActivity.this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arraylist;
		}

		@Override
		protected void onPostExecute(HashMap<String, Object> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {

				if (result.containsKey("message") == true) {
					GetNetWorkData.ReLogin(IndexActivity.this,
							LoginActivity.class); // 超时重登

				} else {

					DetialGallery gallery = (DetialGallery) findViewById(R.id.index_gallery);
					// index_scrollview = (ScrollView)
					// findViewById(R.id.index_scrollview);

					adlist1 = (ArrayList<HashMap<String, Object>>) result
							.get("adlist1");
					adlist2 = (ArrayList<HashMap<String, Object>>) result
							.get("adlist2");
					adlist3 = (ArrayList<HashMap<String, Object>>) result
							.get("adlist3");
					adlist4 = (ArrayList<HashMap<String, Object>>) result
							.get("adlist4");

					adlist5 = (ArrayList<HashMap<String, Object>>) result
							.get("adlist5");

					if (adlist5.size() > 0 && adlist5 != null) {
						AdvertisementBean.getAdver().setArraylist5(adlist5);
					} else {

					}
					if (adlist1.size() > 0 && adlist1 != null) {
						AdvertisementBean.getAdver().setArraylist1(adlist1);
						/**
						 * 与gallery的图片数相等，可以方法动态实现
						 */
						// 初始化数组

						imageViews = new ImageView[adlist1.size()];
						addCircle(adlist1.size());

						gallery.setAdapter(new IndexGalleryAdapter(
								IndexActivity.this, adlist1));

						gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								listener(position);  //这个方法其实可以用move=position;代替

							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {

							}
						});

						gallery.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								intent = new Intent();
								intent.setClass(IndexActivity.this,
										ProductDetailsActivity.class);
								intent.putExtra(
										"id",
										((String) adlist1.get(position).get(
												"id")));
								startActivity(intent);

							}
						});
					} else {

					}

					if (adlist2.size() > 0 && adlist2 != null) {
						gridView1 = (CustomGridView) findViewById(R.id.index_top_show_layout);

						gridView1.setAdapter(new IndexGridViewLayoutAdapter1(
								IndexActivity.this, adlist2));
						gridView1
								.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> parent, View view,
											int position, long id) {
										intent = new Intent();
										intent.setClass(IndexActivity.this,
												ProductDetailsActivity.class);
										intent.putExtra("id", ((String) adlist2
												.get(position).get("id")));
										startActivity(intent);

									}
								});
					} else {

					}

//					if (adlist3.size() > 0 && adlist3 != null) {
//
//						/**
//						 * 风格馆
//						 */
//						gridView2 = (CustomGridView) findViewById(R.id.index_style_layout);
//
//						gridView2.setAdapter(new IndexGridViewLayoutAdapter2(
//								IndexActivity.this, adlist3));
//
//						gridView2
//								.setOnItemClickListener(new OnItemClickListener() {
//
//									@Override
//									public void onItemClick(
//											AdapterView<?> parent, View view,
//											int position, long id) {
//										intent = new Intent();
//										intent.setClass(IndexActivity.this,
//												ShopIntroductionActivity.class);
//										intent.putExtra("shopid",
//												((String) adlist3.get(position)
//														.get("id")));
//										startActivity(intent);
//
//									}
//								});
//					} else {
//
//					}
					if (adlist4.size() > 0 && adlist4 != null) {
						if (adlist4.size() > 8) {
							/* 这里要new否则报错，具体打开谷歌去百度吧 */
							Listdata = new ArrayList<HashMap<String, Object>>(
									adlist4.subList(0, 8));
						}else{
							Listdata = new ArrayList<HashMap<String, Object>>(
									adlist4);
						}

						/**
						 * gridview
						 */
						gridView = (CustomGridView) findViewById(R.id.index_style_gridview);

						gridViewAdapter = new IndexGridviewAdapter(
								IndexActivity.this, Listdata, 1);

						gridView.setAdapter(gridViewAdapter);

						gridView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								String ids = adlist4.get(position).get("id")
										.toString();
								if (position <= 6) {
									Intent intent = new Intent();
									intent.setClass(IndexActivity.this,
											SortItemActivity.class);
									intent.putExtra("id", ids);
									intent.putExtra("name",
											adlist4.get(position).get("name")
													.toString());
									startActivity(intent);
								} else {
									Intent intent = new Intent();
									intent.setClass(IndexActivity.this,
											IndexGoodsListActivity.class);
									Adlist4.getAdlist4().setArrayList(adlist4);
									startActivity(intent);
								}
							}
						});

					} else {

					}
				}
			} else {
				GetNetWorkData.DialogMessage(IndexActivity.this, "");
			}
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

	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(IndexActivity.this);
			return true;
		}
		return false;
	}

}
