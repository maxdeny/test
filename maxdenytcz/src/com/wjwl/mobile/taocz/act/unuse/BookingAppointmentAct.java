package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.format.Time;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.untils.Dateformat;
//
//public class BookingAppointmentAct extends MActivity {
//	RelativeLayout clic_layout1, clic_layout2;
//	Button bt_search, bt_1, bt_2, bt_3, back;
//	TextView text2, text3, text4, text5;
//	private String[] days = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//	private String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
//			"10", "11", "12" };
//	private String defaultTime1 = "", str_date;
//	public static String categoryareaid = "", categoryareaname = "";
//	public static TextView text1;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.bookingappointment);
//		setId("BookingAppointmentAct");
//		clic_layout1 = (RelativeLayout) findViewById(R.bookingappointment.clic_layout1);
//		clic_layout2 = (RelativeLayout) findViewById(R.bookingappointment.clic_layout2);
//		bt_search = (Button) findViewById(R.bookingappointment.bt_search);
//		bt_1 = (Button) findViewById(R.bookingappointment.bt_1);
//		bt_2 = (Button) findViewById(R.bookingappointment.bt_2);
//		bt_3 = (Button) findViewById(R.bookingappointment.bt_3);
//		text1 = (TextView) findViewById(R.bookingappointment.text1);
//		text2 = (TextView) findViewById(R.bookingappointment.text2);
//		text3 = (TextView) findViewById(R.bookingappointment.text3);
//		text4 = (TextView) findViewById(R.bookingappointment.text4);
//		text5 = (TextView) findViewById(R.bookingappointment.text5);
//		back = (Button) findViewById(R.bookingappointment.back);
//		clic_layout1.setOnClickListener(new onclic());
//		clic_layout2.setOnClickListener(new onclic());
//		bt_search.setOnClickListener(new onclic());
//		bt_1.setOnClickListener(new onclic());
//		bt_2.setOnClickListener(new onclic());
//		bt_3.setOnClickListener(new onclic());
//		back.setOnClickListener(new onclic());
//		categoryareaname="";
//		Time time = new Time();
//		time.setToNow();
//		text3.setText("今天");
//		defaultTime1 = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
//		if (time.month + 1 < 10)
//			str_date = "" + time.year + "0" + (time.month + 1) + time.monthDay;
//		else
//			str_date = "" + time.year + (time.month + 1) + time.monthDay;
//		text4.setText(time.year + "年" + months[time.month] + "月"
//				+ time.monthDay + "日");
//		text5.setText(days[time.weekDay]);
//	}
//
//	public String getWeek(String date) throws ParseException {
//		String s = "";
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar c = Calendar.getInstance();
//		c.setTime(format.parse(date));
//		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//			s = "星期天";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 2) {
//			s = "星期一";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 3) {
//			s = "星期二";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 4) {
//			s = "星期三";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 5) {
//			s = "星期四";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 6) {
//			s = "星期五";
//		} else if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//			s = "星期六";
//		}
//		return s;
//	}
//
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		// TODO Auto-generated method stub
//		String xingqi = "";
//		if (obj != null) {
//			try {
//				xingqi = getWeek(obj.toString());
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		if (type == 1) {
//			if (Dateformat.DateToInt(defaultTime1) > Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "选择时间不能小于当前时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			int tempdays = Dateformat
//					.DateThanDate(defaultTime1, obj.toString());
//			if (tempdays == 1)
//				text3.setText("明天");
//			else if (tempdays == 2)
//				text3.setText("后天");
//			else if (tempdays < 7)
//				text3.setText("一星期内");
//			else if (tempdays <= 30)
//				text3.setText("一个月内");
//			else if (tempdays > 30)
//				text3.setText("一个月后");
//			text4.setText(Dateformat.getYear(obj.toString()) + "年"
//					+ months[Dateformat.getMonth(obj.toString())] + "月"
//					+ Dateformat.getDate(obj.toString()) + "日");
//			text5.setText(xingqi);
//			if (Integer.parseInt(months[Dateformat.getMonth(obj.toString())]) < 10)
//				str_date = Dateformat.getYear(obj.toString()) + "0"
//						+ months[Dateformat.getMonth(obj.toString())]
//						+ Dateformat.getDate(obj.toString());
//			else
//				str_date = Dateformat.getYear(obj.toString())
//						+ months[Dateformat.getMonth(obj.toString())]
//						+ Dateformat.getDate(obj.toString());
//
//		}
//	}
//
//	public class onclic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.bookingappointment.bt_1:
//				Intent i1 = new Intent();
//				i1.setClass(BookingAppointmentAct.this, BookingRestaurantAct.class);
//				startActivity(i1);
//				break;
//			case R.bookingappointment.bt_2:
//				Intent i2 = new Intent();
////				i2.setClass(getApplication(), PreferentialSetMealAct.class);
//				i2.putExtra("act", "BookingAppointmentAct");;
//				i2.putExtra("itemname", "餐饮美食");
//				i2.putExtra("itemid", "35");
//				i2.setClass(BookingAppointmentAct.this, GroupBuyingListAct.class);
//				startActivity(i2);
//				break;
//			case R.bookingappointment.bt_3:
//				Intent i3 = new Intent();
//				i3.setClass(BookingAppointmentAct.this, CouponAct.class);
//				startActivity(i3);
//				break;
////			case R.bookingappointment.bt_search:
////				Intent i = new Intent();
////				i.putExtra("date", str_date);
////				i.putExtra("act", "BookingAppointmentAct");
////				i.putExtra("categoryareaname", categoryareaname);
////				i.putExtra("categoryareaid", categoryareaid);
////				i.setClass(BookingAppointmentAct.this, RestaurantDetailsListAct.class);
////				startActivity(i);
////				break;
//			case R.bookingappointment.clic_layout1:
//				Intent intent2 = new Intent(BookingAppointmentAct.this,
//						AreaSelectAct.class);
//				intent2.putExtra("act","BookingAppointmentAct");
//				startActivity(intent2);
//				break;
//			case R.bookingappointment.clic_layout2:
//				Intent intent1 = new Intent(v.getContext(), CalendarAct.class);
//				intent1.putExtra("type", 1);
//				v.getContext().startActivity(intent1);
//				break;
//			case R.bookingappointment.back:
//				finish();
//				break;
//			}
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) {
//		case RESULT_OK:
//			if (data.getStringExtra("act").equals("AreaSelectAct")) {
//				String title = data.getStringExtra("title");
//				text1.setText(title);
//			}
//
//		}
//	}
//}
