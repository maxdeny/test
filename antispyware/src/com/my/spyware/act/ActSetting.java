package com.my.spyware.act;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import cn.jpush.android.api.JPushInterface;

import com.example.antispyware.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.http.Son;
import com.mdx.mobile.http.Updateone;
import com.mdx.mobile.http.json.Updateone2json;
import com.my.spyware.F;
import com.my.spyware.dialog.MyProgressDialog;
import com.my.spyware.widget.ItemHeadLayout;
import com.xcecs.data.dw.DW_User.MsgUserList;
import com.xcecs.data.dw.DW_User.MsgUserList.Builder;

/**
 * 设置
 * 
 * @Title: ActSetting
 * @ToDo: TODO
 * @author Administrator
 * @version v 1.0
 * @date [2016-3-14下午4:41:35]
 */
public class ActSetting extends MActivity implements OnClickListener {

	// 修改用户密码
	@ViewInject(R.id.setting_modify_password)
	private RelativeLayout setting_modify_password;

	// 注销登录
	@ViewInject(R.id.setting_logout)
	private RelativeLayout setting_logout;

	// 报警范围
	@ViewInject(R.id.setting_modify_scope)
	private RelativeLayout setting_modify_scope;

	// 推送
	@ViewInject(R.id.setting_checkbox_push)
	private CheckBox setting_checkbox_push;

	// 声音
	@ViewInject(R.id.setting_checkbox_sound)
	private CheckBox setting_checkbox_sound;

	// 振动
	@ViewInject(R.id.setting_checkbox_vibrate)
	private CheckBox setting_checkbox_vibrate;

	@ViewInject(R.id.head)
	private ItemHeadLayout header;

	private String scope = "0";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		initdialog();
		setContentView(R.layout.act_setting);
		ViewUtils.inject(this);
		initView();
	}

	@Override
	protected void initdialog() {
		// TODO Auto-generated method stub
		loadingDialog = new MyProgressDialog(this, "请稍后···");
	}

	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 0:
			loadData(new Updateone[] { new Updateone("MBUserScope",
					new String[][] { { "account_id", F.userId },
							{ "scope", scope }, { "deviceid", F.DEVICEID } }) });
			break;
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.getMetod().equals("MBUserScope")) {
			if (son.getError() == 0) {
				F.showToast(this, "设置成功");

			} else {
				F.showToast(this, son.getMsg());
			}
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		setting_modify_password.setOnClickListener(this);
		setting_logout.setOnClickListener(this);
		setting_modify_scope.setOnClickListener(this);
		setting_checkbox_push
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							JPushInterface.resumePush(getApplicationContext());
						} else {
							JPushInterface.stopPush(getApplicationContext());
						}
					}
				});
		setting_checkbox_sound
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub

						F.isPlaySound = isChecked;
					}
				});
		setting_checkbox_vibrate
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						F.isVibrate = isChecked;
					}
				});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setting_modify_password:

			Intent intent = new Intent(this, ActModifyPass.class);
			startActivity(intent);
			break;
		case R.id.setting_logout:
			F.setLogin(getApplicationContext(), "", "");
			Intent intent1 = new Intent(this, ActLogin.class);
			startActivity(intent1);
			this.finish();
			break;
		case R.id.setting_modify_scope:
			showEditDialog();
			break;
		}
	}

	private void showEditDialog() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		final View textEntryView = inflater.inflate(R.layout.dialog_edit, null);
		final EditText edtInput = (EditText) textEntryView
				.findViewById(R.id.et_scope);
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("报警范围");
		builder.setView(textEntryView);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				if (!F.isEmpty(edtInput.getText().toString())) {

					scope = edtInput.getText().toString();
					dataLoad(new int[] { 0 });

				} else {
					F.showToast(ActSetting.this, "请先输入范围");
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				setTitle("");
			}
		});
		builder.show();
	}

}
