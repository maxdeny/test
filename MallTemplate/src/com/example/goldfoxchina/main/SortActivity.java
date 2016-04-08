package com.example.goldfoxchina.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.goldfoxchina.Adapter.SortGridviewAdapter;
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
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 商品分类
 * 
 * @author kysl
 * 
 */
public class SortActivity extends Activity {

	/**
	 * sort gridview
	 */

	private GridView gridView;
	private SortGridviewAdapter gridViewAdapter;

	private Intent intent;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_sort);

		/**
		 * gridview
		 */
		gridView = (GridView) findViewById(R.id.sort_style_gridview);

		// gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		new getSortData(this).execute();

	}

	class getSortData extends AsyncTask<Void, ArrayList<HashMap<String, Object>>, ArrayList<HashMap<String, Object>>> {
		Context context;

		public getSortData(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SortActivity.this, "数据加载中……").createLoadingDialog();
			dialog.show();
		}

		@Override
		protected ArrayList<HashMap<String, Object>> doInBackground(Void... params) {
			ArrayList<HashMap<String, Object>> arraylist = null;
			try {
				arraylist = GetJsonData.getCategoriesJsonData(SortActivity.this, "");
			} catch (Exception e) {
				return null;
			}
			return arraylist;
		}

		@Override
		protected void onPostExecute(
				final ArrayList<HashMap<String, Object>> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null) {
				gridViewAdapter = new SortGridviewAdapter(SortActivity.this,
						result);
				gridView.setAdapter(gridViewAdapter);

				gridView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						intent = new Intent();
						intent.setClass(SortActivity.this,
								SortItemActivity.class);
						intent.putExtra("id", result.get(position).get("id")
								.toString());
						intent.putExtra("name", result.get(position)
								.get("name").toString());

						startActivity(intent);

					}
				});
			}else{
//				if (!"".equals(CookieID.getCookieID().getCookieID())) {
//
//					GetNetWorkData.ReLogin(SortActivity.this,LoginActivity.class);
//				} else {
					// 如果服务器无响应或关闭，弹出提示
					GetNetWorkData.ServerMessage(SortActivity.this, "");
//				}    
			}
			
		}

	}
	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(SortActivity.this);
			return true;
		}
		return false;
	}
}
