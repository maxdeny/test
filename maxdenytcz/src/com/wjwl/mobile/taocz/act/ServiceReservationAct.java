package com.wjwl.mobile.taocz.act;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.ServiceOtherNeedDialog;
import com.wjwl.mobile.taocz.widget.wheelview.NumericWheelAdapter;
import com.wjwl.mobile.taocz.widget.wheelview.OnWheelChangedListener;
import com.wjwl.mobile.taocz.widget.wheelview.WheelView;

public class ServiceReservationAct extends MActivity {
	private Dialog dialog;
	private static int START_YEAR = 1990, END_YEAR = 2100;
	RelativeLayout service_area, service_otherneed, service_date;
	String[] area, date;
	String t_username, t_phone, t_otherphone, t_price, t_areaaddress = "",
			t_address, t_serviceneed = "", t_servicedate = "", t_sextype,
			t_leveltype;
	TextView t_serarea, t_Type, t_type;
	public static TextView t_need, t_date;
	Button bt_submit, bt_rewrite;
	private RadioButton rbt_man, rbt_woman, rbt_generally, rbt_urgency;
	private EditText username, phone, otherphone, areaaddress, address;
	private String name_category1 = "", name_category2 = "", categoryid1,
			categoryid2;

	@Override
	protected void create(Bundle arg0) {
		setId("ServiceReservationAct");
		setContentView(R.layout.service_reservation);
		Intent i = this.getIntent();
		name_category1 = i.getStringExtra("name_category1");
		name_category2 = i.getStringExtra("name_category2");
		categoryid1 = i.getStringExtra("categoryparentid1");
		categoryid2 = i.getStringExtra("categoryparentid2");
		service_area = (RelativeLayout) this
				.findViewById(R.ser_reser.clic_layout5);
		service_date = (RelativeLayout) this
				.findViewById(R.ser_reser.clic_layout8);
		service_otherneed = (RelativeLayout) this
				.findViewById(R.ser_reser.clic_layout9);
		username = (EditText) this.findViewById(R.ser_reser.edit_ed1);
		phone = (EditText) this.findViewById(R.ser_reser.edit_ed2);
		otherphone = (EditText) this.findViewById(R.ser_reser.edit_ed3);
		areaaddress = (EditText) this.findViewById(R.ser_reser.edit_ed5);
		address = (EditText) this.findViewById(R.ser_reser.edit_ed6);
		rbt_man = (RadioButton) this.findViewById(R.ser_reser.man);
		rbt_woman = (RadioButton) this.findViewById(R.ser_reser.woman);
		rbt_generally = (RadioButton) this.findViewById(R.ser_reser.generally);
		rbt_urgency = (RadioButton) this.findViewById(R.ser_reser.urgency);
		rbt_man.setChecked(true);
		rbt_woman.setChecked(false);
		rbt_generally.setChecked(true);
		rbt_urgency.setChecked(false);
		t_need = (TextView) this.findViewById(R.ser_reser.need);
		t_date = (TextView) this.findViewById(R.ser_reser.date);
		t_serarea = (TextView) this.findViewById(R.ser_reser.ser_area);
		bt_submit = (Button) this.findViewById(R.ser_reser.bt_submit);
		bt_rewrite = (Button) this.findViewById(R.ser_reser.bt_rewrite);
		t_Type = (TextView) findViewById(R.ser_reser.Type);
		t_type = (TextView) findViewById(R.ser_reser.type);
		t_Type.setText(name_category1);
		t_type.setText(name_category2);
		area = new String[] { "戚区", "新北区", "钟楼区", "武进区", "金坛", "溧阳", "天宁区" };
		service_area.setOnClickListener(new Onclick());
		service_otherneed.setOnClickListener(new Onclick());
		service_date.setOnClickListener(new Onclick());
		bt_rewrite.setOnClickListener(new Onclick());
		bt_submit.setOnClickListener(new Onclick());
		service_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDateTimePicker();
			}
		});

	}

	public class Onclick implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.ser_reser.clic_layout5:
				selectDialog("选择地区", area, t_serarea);
				break;
			case R.ser_reser.clic_layout9:
				final ServiceOtherNeedDialog dia = new ServiceOtherNeedDialog(
						ServiceReservationAct.this,"其他服务要求");
				dia.show();
				dia.ed_need.setText(t_need.getText().toString().trim());
				dia.submit.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {					
					t_need.setText(dia.ed_need.getText().toString().trim());
						dia.cancel();
						dia.dismiss();
					}
				});
				break;
			case R.ser_reser.clic_layout8:
				break;
			case R.ser_reser.bt_rewrite:
				rewrite();
				break;
			case R.ser_reser.bt_submit:
				submit();
				break;
			}
		}
	}

	private void submit() {
		if (F.USER_ID == null || F.USER_ID.length() == 0) {
			F.toLogin(ServiceReservationAct.this, "ServiceReservationAct", "B",
					0);
			return;
		}
		t_username = username.getText().toString().trim();
		t_phone = phone.getText().toString().trim();
		t_otherphone = otherphone.getText().toString().trim();
		t_areaaddress = t_serarea.getText().toString().trim();
		t_address = address.getText().toString().trim();
		t_serviceneed = t_need.getText().toString().trim();
		t_servicedate = t_date.getText().toString().trim();
		if (rbt_generally.isChecked())
			t_leveltype = "0";
		else
			t_leveltype = "1";
		if (rbt_man.isChecked())
			t_sextype = "1";
		else
			t_sextype = "0";
		if (t_username.length() <= 0) {
			Toast toast = Toast.makeText(getApplication(), "请输入用户名",
					Toast.LENGTH_SHORT);
			toast.show();
			username.requestFocus();
			return;
		}
		if (t_phone.length() <= 0) {
			Toast toast = Toast.makeText(getApplication(), "请输入手机号码",
					Toast.LENGTH_SHORT);
			toast.show();
			phone.requestFocus();
			return;
		} else if (t_phone.length() != 11) {
			Toast toast = Toast.makeText(getApplication(), "手机号码有错,请重新输入",
					Toast.LENGTH_SHORT);
			toast.show();
			phone.requestFocus();
			return;
		}
		if (t_areaaddress.equals("")) {
			Toast toast = Toast.makeText(getApplication(), "请选择服务区域",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		if (t_address.equals("")) {
			Toast toast = Toast.makeText(getApplication(), "请填写您的详细地址",
					Toast.LENGTH_SHORT);
			toast.show();
			address.requestFocus();
			return;
		}
		if (t_servicedate.equals("")) {
			Toast toast = Toast.makeText(getApplication(), "请选择服务时间",
					Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
		dataLoad(null);
	}

	private void rewrite() {
		rbt_man.setChecked(true);
		rbt_woman.setChecked(false);
		rbt_generally.setChecked(true);
		rbt_urgency.setChecked(false);
		t_serarea.setText(null);
		t_date.setText(null);
		t_need.setText(null);
		username.setText(null);
		phone.setText(null);
		otherphone.setText(null);
		areaaddress.setText(null);
		address.setText(null);
		username.requestFocus();
	}

	void selectDialog(String title, final String[] item, final TextView t) {
		new AlertDialog.Builder(ServiceReservationAct.this).setTitle(title)
				.setItems(item, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						t.setText(item[which]);
					}
				}).create().show();
	}

	private void showDateTimePicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);

		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		dialog = new Dialog(this);
		dialog.setTitle("请选择日期与时间");
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.datepicker_time_layout, null);

		final WheelView wv_year = (WheelView) view.findViewById(R.id.year);
		wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));
		wv_year.setCyclic(true);
		wv_year.setLabel("年");
		wv_year.setCurrentItem(year - START_YEAR);

		final WheelView wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		final WheelView wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		final WheelView wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setLabel("时");
		wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
		wv_hours.setCyclic(true);
		wv_hours.setCurrentItem(hour);

		final WheelView wv_mins = (WheelView) view.findViewById(R.id.mins);
		wv_mins.setLabel("分");
		wv_mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		wv_mins.setCyclic(true);
		wv_mins.setCurrentItem(minute);

		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				if (list_big
						.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month
						.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0)
							|| year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
							.getCurrentItem() + START_YEAR) % 100 != 0)
							|| (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		int textSize = 0;

		textSize = 18;

		wv_day.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;

		Button btn_sure = (Button) view.findViewById(R.id.btn_datetime_sure);
		Button btn_cancel = (Button) view
				.findViewById(R.id.btn_datetime_cancel);
		btn_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String parten = "00";
				DecimalFormat decimal = new DecimalFormat(parten);

				String date = (wv_year.getCurrentItem() + START_YEAR) + "-"
						+ decimal.format((wv_month.getCurrentItem() + 1)) + "-"
						+ decimal.format((wv_day.getCurrentItem() + 1)) + " "
						+ decimal.format(wv_hours.getCurrentItem()) + ":"
						+ decimal.format(wv_mins.getCurrentItem());
				t_date.setText(date);
				dialog.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.setContentView(view);
		dialog.show();
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("border")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 1) {
				Toast.makeText(getApplicationContext(), "预约成功~",
						Toast.LENGTH_LONG).show();
				this.finish();
			} else {
				Toast.makeText(getApplicationContext(), "预约失败~",
						Toast.LENGTH_LONG).show();
				this.finish();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("BORDER", new String[][] {
				{ "userid", F.USER_ID }, { "name", t_username },
				{ "servicetime", t_servicedate }, { "tel", t_phone },
				{ "other", t_serviceneed }, { "area", "常州" + t_areaaddress },
				{ "gender", t_sextype }, { "address", t_address },
				{ "level", t_leveltype }, { "item_parent_id", categoryid1 },
				{ "item_id", categoryid2 } }), });
	}

}
