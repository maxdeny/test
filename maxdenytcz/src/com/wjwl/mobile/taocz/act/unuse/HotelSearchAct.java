package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.Map;
//
//import android.content.Intent;
//import android.location.Location;
//import android.os.Bundle;
//import android.text.format.Time;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.mapapi.LocationListener;
//import com.baidu.mapapi.MKLocationManager;
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.commons.Dateformat;
//
//public class HotelSearchAct extends MActivity {
//
//	Button bt_city, bt_hoteladdress, bt_serch;
//	LinearLayout layout_occupancy, layout_leave, layout_side;
//	TextView t_o_day, t_o_year, t_o_week, t_l_day, t_l_year, t_l_week;
//	private String[] months = { "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月",
//			"9月", "10月", "11月", "12月" };
//	private String[] days = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//	private String startTime = ""; // "yyyy-mm-dd"
//	private String endTime = "";
//	private String defaultTime = "";
//
//	LocationListener mLocationListener = null;
//	int Latitude;
//	int Longitude;
//	boolean reLocation;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.hotel_serch);
//		setId("HotelSearchAct");
//		bt_city = (Button) this.findViewById(R.hotelsearch.bt_citysearch);
//		bt_hoteladdress = (Button) this
//				.findViewById(R.hotelsearch.bt_hoteladdress);
//		bt_serch = (Button) this.findViewById(R.hotelsearch.bt_search);
//		layout_occupancy = (LinearLayout) this
//				.findViewById(R.hotelsearch.layout_occupancy);
//		layout_leave = (LinearLayout) this
//				.findViewById(R.hotelsearch.layout_leave);
//		layout_side = (LinearLayout) this
//				.findViewById(R.hotelsearch.layout_side);
//		t_o_day = (TextView) this.findViewById(R.hotelsearch.occupancy_day);
//		t_o_year = (TextView) this.findViewById(R.hotelsearch.occupancy_year);
//		t_o_week = (TextView) this.findViewById(R.hotelsearch.occupancy_week);
//		t_l_day = (TextView) this.findViewById(R.hotelsearch.leave_day);
//		t_l_year = (TextView) this.findViewById(R.hotelsearch.leave_year);
//		t_l_week = (TextView) this.findViewById(R.hotelsearch.leave_week);
//		// Calendar c = Calendar.getInstance();
//		// int year = c.get(Calendar.YEAR);
//		// int month = c.get(Calendar.MONTH);
//		// int day = c.get(Calendar.DAY_OF_MONTH);
//		Time time = new Time();
//		time.setToNow();
//		defaultTime = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
//		endTime = defaultTime;
//		startTime = defaultTime;
//		t_o_day.setText(months[time.month] + time.monthDay + "日");
//		t_o_year.setText("" + time.year + "年");
//		t_o_week.setText(days[time.weekDay]);
//		t_l_day.setText(months[time.month] + time.monthDay + "日");
//		t_l_year.setText("" + time.year + "年");
//		t_l_week.setText(days[time.weekDay]);
//		bt_hoteladdress.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(getApplication(),
//						HotelNameSearchAct.class);
//				startActivity(i);
//			}
//		});
//		layout_side.setOnClickListener(new OnClick());
//		bt_city.setOnClickListener(new OnClick());
//		layout_occupancy.setOnClickListener(new OnClick());
//		layout_leave.setOnClickListener(new OnClick());
//		bt_serch.setOnClickListener(new OnClick());
//		Frame.MAP.create();
//		locationListener();
//		Frame.MAP.start();
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.requestLocationUpdates(mLocationListener);
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.enableProvider(MKLocationManager.MK_GPS_PROVIDER);
//		Frame.MAP.getmBMapMan().getLocationManager()
//				.enableProvider(MKLocationManager.MK_NETWORK_PROVIDER);
//
//	}
//
//	void locationListener() {
//		mLocationListener = new LocationListener() {
//			@Override
//			public void onLocationChanged(Location location) {
//				if (location != null) {
//					Latitude = (int) (location.getLatitude() * 1e6);
//					Longitude = (int) (location.getLongitude() * 1e6);
//					Frame.MAP.getmBMapMan().getLocationManager()
//							.removeUpdates(mLocationListener);
//					Frame.MAP.stop();
//				}
//			}
//		};
//	}
//
//	class OnClick implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if (bt_city.equals(v)) {
//				Intent intent = new Intent(v.getContext(), CityAct.class);
//				v.getContext().startActivity(intent);
//			} else if (layout_occupancy.equals(v)) {
//				Intent intent = new Intent(v.getContext(), CalendarAct.class);
//				intent.putExtra("type", 1);
//				v.getContext().startActivity(intent);
//			} else if (layout_leave.equals(v)) {
//				Intent intent = new Intent(v.getContext(), CalendarAct.class);
//				intent.putExtra("type", 2);
//				v.getContext().startActivity(intent);
//			} else if (layout_side.equals(v)) {
//				Intent intent = new Intent(getApplication(),
//						BusinessHotelMapAct.class);
//				intent.putExtra("isList", true);
//				//startActivity(intent);
//				v.getContext().startActivity(intent);
//			} else if (bt_serch.equals(v)) {
//				if (bt_hoteladdress.getTag() == null) {
//					Toast.makeText(v.getContext(), "选择酒店名称或地点",
//							Toast.LENGTH_SHORT).show();
//					return;
//				}
//				Intent intent = new Intent(HotelSearchAct.this,
//						HotelSearchListAct.class);
//				intent.putExtra("searchPupub", 3);
//				intent.putExtra("args", new String[] {
//						bt_city.getText().toString(), startTime, endTime,
//						bt_hoteladdress.getText().toString() });
//				v.getContext().startActivity(intent);
//			}
//		}
//	}
//
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		// TODO Auto-generated method stub
//		if (type == 0) {
//			this.bt_city.setText(obj.toString());
//		} else if (type == 1) {
//			if (Dateformat.DateToInt(defaultTime) > Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "入住时间不能小于当前时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			if (Dateformat.DateToInt(endTime) < Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "入住时间不能大于离店时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			startTime = obj.toString();
//			t_o_day.setText(months[Dateformat.getMonth(obj.toString())]
//					+ Dateformat.getDate(obj.toString()) + "日");
//			t_o_year.setText(Dateformat.getYear(obj.toString()) + "年");
//			t_o_week.setText(days[Dateformat.getWeek(obj.toString())]);
//		} else if (type == 2) {
//			if (Dateformat.DateToInt(startTime) > Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "离店时间不能小于入住时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			endTime = obj.toString();
//			t_l_day.setText(months[Dateformat.getMonth(obj.toString())]
//					+ Dateformat.getDate(obj.toString()) + "日");
//			t_l_year.setText(Dateformat.getYear(obj.toString()) + "年");
//			t_l_week.setText(days[Dateformat.getWeek(obj.toString())]);
//		} else if (type == 4) {
//			bt_hoteladdress.setText(((Map) obj).get("hotelname").toString());
//			bt_hoteladdress.setTag(((Map) obj).get("id").toString());
//		}
//	}
//}
