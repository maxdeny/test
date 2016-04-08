package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.mcommons.MContact;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.commons.RegularString;
import com.wjwl.mobile.taocz.dialog.ContactSelect;
import com.wjwl.mobile.taocz.dialog.ContactSelect.OnContactSelectListener;

public class RecommendSoftAct extends MActivity {
	private View loading_fill = null;
	private Button bt_tuijiangei, bt_quxiao, bt_tuijian;
	private EditText ed_tel;
	private StringBuffer strArray = new StringBuffer();
	private String phone;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.recommenddialog);
		bt_tuijiangei = (Button) findViewById(R.receommenddialog.bt_tuijiangei);
		bt_quxiao = (Button) findViewById(R.receommenddialog.bt_quxiao);
		bt_tuijian = (Button) findViewById(R.receommenddialog.bt_tuijian);
		loading_fill = findViewById(R.receommenddialog.Loading_fill);
		ed_tel = (EditText) findViewById(R.receommenddialog.ed_tel);
		bt_tuijiangei.setOnClickListener(new onclic());
		bt_quxiao.setOnClickListener(new onclic());
		bt_tuijian.setOnClickListener(new onclic());
	}

	public class onclic implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.receommenddialog.bt_quxiao:
				RecommendSoftAct.this.finish();
				break;
			case R.receommenddialog.bt_tuijian:

				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(RecommendSoftAct.this, "RecommendSoftAct", "", 0);
				} else {
					DataLoad(null);
				}

				break;
			case R.receommenddialog.bt_tuijiangei:
				ContactSelect contact = new ContactSelect(RecommendSoftAct.this);
				contact.show();
				contact.setOnContactselect(new OnContactSelectListener() {
					public void onSelect(List<MContact> list) {
						strArray.delete(0, strArray.length());
						for (int i = 0; i < list.size(); i++) {
							// String name = ((MContact)list.get(i)).getName();
							String tel = ((MContact) list.get(i)).getPhone();
							if (RegularString.checkTel(tel)) {
								if (tel.startsWith("86")
										|| tel.startsWith("+86")) {
									tel = tel.substring(tel.length() - 11,
											tel.length());
								}
								strArray.append(tel);
								strArray.append(",");
							}
						}
						if (strArray.length() != 0) {
							String phonenumber = strArray.toString();
							phone = phonenumber.substring(0,
									phonenumber.length() - 1);
						}

						ed_tel.setText(phone);
					}

				});
				break;
			}

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("osendmsg")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "推送成功",
						Toast.LENGTH_LONG).show();
				RecommendSoftAct.this.finish();
			}

		} else {
			Toast.makeText(getApplicationContext(), "推送失败", Toast.LENGTH_LONG)
					.show();
			RecommendSoftAct.this.finish();
		}

	}

	public void DataLoad(int[] typs) {
			this.loadData(new Updateone[] { new Updateone("OSENDMSG",
					new String[][] { { "userid", F.USER_ID }, { "tel", ed_tel.getText().toString() } }), });

	}

	@Override
	public void showLoad() {
		loading_fill.setVisibility(View.VISIBLE);
	}

	@Override
	public void closeLoad() {
		loading_fill.setVisibility(View.GONE);
	}
}
