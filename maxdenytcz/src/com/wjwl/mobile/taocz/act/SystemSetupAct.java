package com.wjwl.mobile.taocz.act;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.ehour.protobuf.MApp.Msg_AppList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.Data;
import com.wjwl.mobile.taocz.services.AutoNotService;
import com.wjwl.mobile.taocz.widget.Item_more;

public class SystemSetupAct extends MActivity {
	TextView title1, title2, title3, title4, title5, title6;
	TextView small_title;
	CheckBox chk1, chk2, chk3, chk4, chk5, chk6;
	RelativeLayout layout2, layout4, layout5, layout6, layout7, layout8,
			layout9;
	private Data mData;
	Button bt_back;
	private LinearLayout f_layout;
	HorizontalScrollView hscrollview;
	Context context;
	PackageInfo info;
	String packageNames = "";

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.systemsetup);
		// init();
		context = SystemSetupAct.this;
		f_layout = (LinearLayout) findViewById(R.sysset.addlayout);
		hscrollview = (HorizontalScrollView) findViewById(R.sysset.hscrollview);
		f_layout.removeAllViews();
		layout2 = (RelativeLayout) findViewById(R.sysset.clic_layout2);
		layout2.setOnClickListener(new layoutListener());
		mData = new Data(this);
		chk3 = (CheckBox) findViewById(R.item_sysset.checkbox3);
		chk3.setChecked(mData.getAutoUpdate(false));
		layout6 = (RelativeLayout) findViewById(R.sysset.clic_layout6);
		layout6.setOnClickListener(new layoutListener());
		bt_back = (Button) findViewById(R.sysset.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SystemSetupAct.this.finish();
			}
		});
		chk3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mData.setAutoUpdate(isChecked);
				if (isChecked) {
					AutoNotService.start(SystemSetupAct.this);
				} else {
					AutoNotService.stop();
				}
			}
		});

		try {
			info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			packageNames = info.packageName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		dataLoad(null);
	}

	private void init() {
		title1 = (TextView) findViewById(R.item_sysset.text1);
		title2 = (TextView) findViewById(R.item_sysset.text2);
		title3 = (TextView) findViewById(R.item_sysset.text3);
		title4 = (TextView) findViewById(R.item_sysset.text4);
		title5 = (TextView) findViewById(R.item_sysset.text5);
		title6 = (TextView) findViewById(R.item_sysset.text6);
		layout2 = (RelativeLayout) findViewById(R.sysset.clic_layout2);
		layout2.setOnClickListener(new layoutListener());
		layout4 = (RelativeLayout) findViewById(R.sysset.clic_layout4);
		layout4.setOnClickListener(new layoutListener());
		layout5 = (RelativeLayout) findViewById(R.sysset.clic_layout5);
		layout5.setOnClickListener(new layoutListener());
		layout6 = (RelativeLayout) findViewById(R.sysset.clic_layout6);
		layout6.setOnClickListener(new layoutListener());
		layout7 = (RelativeLayout) findViewById(R.sysset.clic_layout7);
		layout7.setOnClickListener(new layoutListener());
		layout8 = (RelativeLayout) findViewById(R.sysset.clic_layout8);
		layout8.setOnClickListener(new layoutListener());
		layout9 = (RelativeLayout) findViewById(R.sysset.clic_layout9);
		layout9.setOnClickListener(new layoutListener());

		chk1 = (CheckBox) findViewById(R.item_sysset.checkbox1);
		chk1.setChecked(true);
		chk2 = (CheckBox) findViewById(R.item_sysset.checkbox2);
		chk2.setChecked(true);
		chk3 = (CheckBox) findViewById(R.item_sysset.checkbox3);
		chk3.setChecked(true);
		chk4 = (CheckBox) findViewById(R.item_sysset.checkbox4);
		chk4.setChecked(true);
		chk5 = (CheckBox) findViewById(R.item_sysset.checkbox5);
		chk5.setChecked(true);
		chk6 = (CheckBox) findViewById(R.item_sysset.checkbox6);
		chk6.setChecked(true);

		chk1.setOnCheckedChangeListener(new checkboxListener());
		chk2.setOnCheckedChangeListener(new checkboxListener());
		chk3.setOnCheckedChangeListener(new checkboxListener());
		chk4.setOnCheckedChangeListener(new checkboxListener());
		chk5.setOnCheckedChangeListener(new checkboxListener());
		chk6.setOnCheckedChangeListener(new checkboxListener());

	}

	public class checkboxListener implements
			CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.item_sysset.checkbox1:
				if (isChecked) {
					title1.setTextColor(0xff333333);
					title2.setTextColor(0xff333333);
					title3.setTextColor(0xff333333);
					title4.setTextColor(0xff333333);
					title5.setTextColor(0xff333333);
					title6.setTextColor(0xff333333);
					chk2.setChecked(true);
					chk3.setChecked(true);
					chk4.setChecked(true);
					chk5.setChecked(true);
					chk6.setChecked(true);
				} else {

					title1.setTextColor(0xff808080);
					title2.setTextColor(0xff808080);
					title3.setTextColor(0xff808080);
					title4.setTextColor(0xff808080);
					title5.setTextColor(0xff808080);
					title6.setTextColor(0xff808080);
					chk2.setChecked(false);
					chk3.setChecked(false);
					chk4.setChecked(false);
					chk5.setChecked(false);
					chk6.setChecked(false);
				}

				break;
			case R.item_sysset.checkbox2:
				if (isChecked && chk1.isChecked() == true) {
					title2.setTextColor(0xff333333);
				} else if (isChecked == false && chk1.isChecked() == true) {
					title2.setTextColor(0xff808080);
				} else if (isChecked && chk1.isChecked() == false) {
					chk2.setChecked(false);
					title2.setTextColor(0xff808080);
				}
				break;
			case R.item_sysset.checkbox3:
				if (isChecked == true && chk1.isChecked() == true) {
					title3.setTextColor(0xff333333);
				} else if (isChecked == false && chk1.isChecked() == true) {
					title3.setTextColor(0xff808080);
				}

				else if (chk1.isChecked() == false) {
					chk3.setChecked(false);
					title3.setTextColor(0xff808080);
				}
				break;
			case R.item_sysset.checkbox4:
				if (isChecked && chk1.isChecked() == true) {
					title4.setTextColor(0xff333333);
				} else if (isChecked == false && chk1.isChecked() == true) {
					title4.setTextColor(0xff808080);
				} else if (isChecked && chk1.isChecked() == false) {
					chk4.setChecked(false);
					title4.setTextColor(0xff808080);
				}
				break;
			case R.item_sysset.checkbox5:
				if (isChecked && chk1.isChecked() == true) {
					title5.setTextColor(0xff333333);
				} else if (isChecked == false && chk1.isChecked() == true) {
					title5.setTextColor(0xff808080);
				} else if (isChecked && chk1.isChecked() == false) {
					chk5.setChecked(false);
					title5.setTextColor(0xff808080);
				}
				break;
			case R.item_sysset.checkbox6:
				if (isChecked && chk1.isChecked() == true) {
					title6.setTextColor(0xff333333);
				} else if (isChecked == false && chk1.isChecked() == true) {
					title6.setTextColor(0xff808080);
				} else if (isChecked && chk1.isChecked() == false) {
					chk6.setChecked(false);
					title6.setTextColor(0xff808080);
				}
				break;

			default:

				return;

			}
		}

	}

	public class layoutListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.sysset.clic_layout2:
				Toast.makeText(getApplication(), "缓存已清除", Toast.LENGTH_SHORT)
						.show();
				break;
			case R.sysset.clic_layout4:
				chk1.setChecked(!chk1.isChecked());
				break;
			case R.sysset.clic_layout5:
				chk2.setChecked(!chk2.isChecked());
				break;
			case R.sysset.clic_layout6:
				chk3.setChecked(!chk3.isChecked());
				break;
			case R.sysset.clic_layout7:
				chk4.setChecked(!chk4.isChecked());
				break;
			case R.sysset.clic_layout8:
				chk5.setChecked(!chk5.isChecked());
				break;
			case R.sysset.clic_layout9:
				chk6.setChecked(!chk6.isChecked());
				break;
			default:
				break;
			}
		}

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("MAppList")) {
			Msg_AppList.Builder builder = (Msg_AppList.Builder) son.build;

			for (int i = 0; i < builder.getListList().size(); i++) {
				if (!builder.getList(i).getPg().equals(packageNames)) {
					LayoutInflater flater = LayoutInflater
							.from(SystemSetupAct.this);
					Item_more item1 = (Item_more) flater.inflate(
							R.layout.item_more, null);
					item1.initview();

					item1.setTitle(builder.getList(i).getName());
					item1.setImg("http://longre.cms.iappk.com/ehour/downloadFile.do?id="
							+ builder.getList(i).getIcon());
					item1.setDownloadUrl(builder.getList(i).getAndroidUrl());
					f_layout.addView(item1);
				}

			}
		}
		if (son.build == null && son.mgetmethod.equals("MAppList")) {
			hscrollview.setVisibility(View.GONE);
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MAppList",
				new String[][] { { "deviceid", Frame.getDeviceid(this) } }) });

	}
}
