package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.SortItemGridViewAdapter;
import com.example.goldfoxchina.Adapter.SortItemListViewAdapter;
import com.example.goldfoxchina.Bean.CookieID;
import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SortItemActivity extends Activity {

	private Intent intent;

	/**
	 * back
	 */
	private TextView sortitem_back;
	/* 标题 */
	private TextView sort_item_name;

	/**
	 * listview
	 */
	private ListView listView;
	private SortItemListViewAdapter listViewAdapter;

	/**
	 * gridview
	 */
	private GridView gridView;
	private SortItemGridViewAdapter gridViewAdapter;

	// 全局变量，记录选中的item
	public static int select_item = 0; // 默认选中第一个

	private Dialog dialog;

	// 商品分类listview列表
	private ArrayList<HashMap<String, Object>> arraylist = null;
	private String id;
	private String sort_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sort_item);
		Intent intent = getIntent();
		id = intent.getExtras().get("id").toString().trim();
		sort_name = intent.getExtras().get("name").toString().trim();
		sortitem_back = (TextView) findViewById(R.id.sortitem_back);
		sortitem_back.setOnClickListener(new ClickListener());
		sort_item_name = (TextView) findViewById(R.id.sort_item_name);
		sort_item_name.setText(sort_name);

		listView = (ListView) findViewById(R.id.sort_item_listview);

		gridView = (GridView) findViewById(R.id.sort_item_gridview);

		new getSortItemListViewData(this).execute();

	}

	/**
	 * 获取商品分类ListView
	 * 
	 * @author kysl
	 * 
	 */

	class getSortItemListViewData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
		Context context;

		public getSortItemListViewData(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SortItemActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			try {
				arraylist = GetJsonData.getCategoriesItemListViewJsonData(
						SortItemActivity.this, id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arraylist;
		}

		@Override
		protected void onPostExecute(
				final ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null) {
				listViewAdapter = new SortItemListViewAdapter(
						SortItemActivity.this, result);
				listView.setAdapter(listViewAdapter);
				new getSortItemGridViewData(SortItemActivity.this, 0, 30,
						result.get(0).get("id").toString()).execute();
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						select_item = position; // 当前选择的节目item
						listViewAdapter.notifyDataSetChanged(); // 通知adapter刷新数据

						/**
						 * 测试id 3
						 * 
						 * arraylist.get(position).get("id").toString()
						 */

						new getSortItemGridViewData(SortItemActivity.this, 0,
								30, arraylist.get(position).get("id").toString()).execute();
					}
				});
			} else {
				if (!"".equals(CookieID.getCookieID().getCookieID())) {

					GetNetWorkData.ReLogin(SortItemActivity.this,LoginActivity.class);
				} else {
					// 如果服务器无响应或关闭，弹出提示
					GetNetWorkData.ServerMessage(SortItemActivity.this, "");
				}
			}
		}

	}

	/**
	 * 获取商品分类GridView
	 * 
	 * @author kysl
	 * 
	 */
	class getSortItemGridViewData
			extends
			AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
		Context context;
		int page;
		int count;
		String category_id;

		public getSortItemGridViewData(Context context, int page, int count,
				String category_id) {
			this.context = context;
			this.page = page;
			this.count = count;
			this.category_id = category_id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SortItemActivity.this, "数据加载中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(
				Void... params) {
			ArrayList<HashMap<String, Object>> arraygridlist = null;
			try {
				arraygridlist = GetJsonData.getCategoriesItemGridViewJsonData(
						context, page, count, category_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return arraygridlist;
		}

		@Override
		protected void onPostExecute(final ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null) {
				/* gridview */
				gridViewAdapter = new SortItemGridViewAdapter(
						SortItemActivity.this, result);
				gridView.setAdapter(gridViewAdapter);

				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						intent = new Intent();
						intent.setClass(SortItemActivity.this,
								ProductDetailsActivity.class);
						intent.putExtra("id", result.get(position).get("id").toString());
						startActivity(intent);
					}
				});
			} else {
				// 如果服务器无响应或关闭，弹出提示
				GetNetWorkData.ServerMessage(context,"暂无数据");
			}

		}

	}

	public class ClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.sortitem_back:
				finish();
				break;

			default:
				break;
			}

		}

	}
}
