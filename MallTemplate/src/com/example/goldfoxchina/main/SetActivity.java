package com.example.goldfoxchina.main;

import java.util.HashMap;

import org.json.JSONException;

import com.example.goldfoxchina.Bean.CustomDialog;
import com.example.goldfoxchina.net.GetJsonData;
import com.example.goldfoxchina.net.GetNetWorkData;
import com.example.goldfoxchina.util.Config;
import com.example.goldfoxchina.util.DelCache;
import com.example.goldfoxchina.util.FileCache;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 设置
 * 
 * @author kysl
 * 
 */

public class SetActivity extends Activity implements OnClickListener {

	// 返回
	private TextView set_back;

	/* 清除缓存 联系电话 意见反馈 关于我们 */
	private LinearLayout set_delcache, set_accountmanager, set_feedback,
			set_aboutas, set_getcode;
			//, set_upgrade;
	/* 注销 */
	private RelativeLayout set_unlogin;

	private Intent intent;
	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set);

		set_back = (TextView) findViewById(R.id.set_back);
		set_delcache = (LinearLayout) findViewById(R.id.set_delcache);
		set_accountmanager = (LinearLayout) findViewById(R.id.set_accountmanager);
		set_feedback = (LinearLayout) findViewById(R.id.set_feedback);
		set_aboutas = (LinearLayout) findViewById(R.id.set_aboutas);
		set_getcode = (LinearLayout) findViewById(R.id.set_getcode);
		set_unlogin = (RelativeLayout) findViewById(R.id.set_unlogin);
//		set_upgrade = (LinearLayout) findViewById(R.id.set_upgrade);

		set_back.setOnClickListener(this);
		set_delcache.setOnClickListener(this);
		set_accountmanager.setOnClickListener(this);
		set_feedback.setOnClickListener(this);
		set_aboutas.setOnClickListener(this);
		set_getcode.setOnClickListener(this);
		set_unlogin.setOnClickListener(this);
//		set_upgrade.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_back:
			finish();
			break;
		case R.id.set_delcache:

			// 提示对话框
			AlertDialog.Builder builder1 = new Builder(this);
			builder1.setTitle("提示")
					.setMessage("确认清除所有数据？")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									new ClearData().execute();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).show();

			break;
		case R.id.set_accountmanager:

			if (Config.telnum == "" || Config.telnum == null
					|| ("").equals(Config.telnum)) {
				Toast.makeText(this, "商家未设置电话信息", Toast.LENGTH_SHORT).show();

			} else {

				// 提示对话框
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("确认拨打")
						.setMessage("联系电话:" + Config.telnum)
						.setPositiveButton("确认",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 用intent启动拨打电话
										Intent intent = new Intent(
												Intent.ACTION_CALL,
												Uri.parse("tel:"
														+ Config.telnum));
										startActivity(intent);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
									}
								}).show();
			}

			break;
		case R.id.set_feedback:
			intent = new Intent();
			intent.setClass(SetActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.set_aboutas:
			 Intent intent_about=new Intent();
			 intent_about.setClass(SetActivity.this,AboutUSActivity.class);
			 startActivity(intent_about);
			break;
		case R.id.set_getcode:
			intent = new Intent();
			intent.setClass(SetActivity.this, GetCodeActivity.class);
			startActivity(intent);
			break;
		case R.id.set_unlogin:
			new LogoutData().execute();
//		case R.id.set_upgrade:
//			intent = new Intent();
//			intent.setClass(SetActivity.this, UpgradeActivity.class);
//			startActivity(intent);
			break;
		default:
			break;
		}

	}

	class ClearData extends AsyncTask<Void, Boolean, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SetActivity.this, "缓存清理中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			DelCache.cleanInternalCache(SetActivity.this);
			boolean tag = new FileCache(SetActivity.this).clear();
			return tag;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			dialog.dismiss();
			Toast.makeText(SetActivity.this, "缓存已清除！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	class LogoutData extends
			AsyncTask<Void, HashMap<String, String>, HashMap<String, String>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new CustomDialog(SetActivity.this, "获取中……")
					.createLoadingDialog();
			dialog.show();
		}

		@Override
		protected HashMap<String, String> doInBackground(Void... params) {
			HashMap<String, String> hashmap = null;
			try {
				hashmap = GetJsonData.LogoutData(SetActivity.this);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return hashmap;
		}

		@Override
		protected void onPostExecute(HashMap<String, String> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result != null && result.size() > 0) {
				if ("success".equals(result.get("data"))) {
					intent = new Intent();
					intent.setClass(SetActivity.this, LoginActivity.class);
					startActivity(intent);
					finish();
				}

			} else {
				// if (!"".equals(CookieID.getCookieID().getCookieID())) {
				//
				// GetNetWorkData.ReLogin(SetActivity.this,LoginActivity.class);
				// } else {
				// 如果服务器无响应或关闭，弹出提示
				GetNetWorkData.ServerMessage(SetActivity.this, "");
				// }
			}
		}
	}

	/**
	 * 重写返回键事件
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			GetNetWorkData.BackDialog(SetActivity.this);
			return true;
		}
		return false;
	}

}
