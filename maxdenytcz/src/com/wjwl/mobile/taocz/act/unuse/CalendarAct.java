package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.Calendar;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//import android.widget.TableLayout;
//import android.widget.TableRow;
//import android.widget.TextView;
//
//public class CalendarAct extends MActivity {
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.calendarview);
//		findViews();
//		getDate();
//		initCalendar(mYear, mMonth, mDay);
//	}
//
//	// Click the cell for return the result
//	public String returnSelectDay(String day) {
//		String selectDay = mYear + "-" + mMonth + "-" + day;
//		if (getIntent().getIntExtra("type", 0) == 1)
//			Frame.HANDLES.get("BookingAppointmentAct").get(0)
//					.sent(1, selectDay);
//		if (getIntent().getIntExtra("type", 0) == 2)
//			Frame.HANDLES.get("BookingAppointmentAct").get(0)
//					.sent(2, selectDay);
//		if (getIntent().getIntExtra("type", 0) == 3)
//			Frame.HANDLES.get("RestaurantDetailsAct").get(0).sent(1, selectDay);
//		if (getIntent().getIntExtra("type", 0) == 4)
//			Frame.HANDLES.get("BookingHotelAct").get(0).sent(1, selectDay);
//		if (getIntent().getIntExtra("type", 0) == 5)
//			Frame.HANDLES.get("BookingHotelAct").get(0).sent(2, selectDay);
//		return selectDay;
//	}
//
//	float int1X, int2X, int1Y, int2Y;
//	int maxYear = 2020;
//	int minYear = 2000;
//
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		int measure = 100;
//		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//			int1X = ev.getX();
//			int1Y = ev.getY();
//		}
//		if (ev.getAction() == MotionEvent.ACTION_UP) {
//			if (int1Y < table_layout.getTop()
//					|| int1Y > table_layout.getBottom()) {
//				return super.dispatchTouchEvent(ev);
//			}
//			int2X = ev.getX();
//			int2Y = ev.getY();
//
//			if (int2X - int1X > measure && Math.abs(int2Y - int1Y) < measure) {
//				preMonth();
//			}
//			if (int2X - int1X < -measure && Math.abs(int2Y - int1Y) < measure) {
//				nextMonth();
//			}
//			if (int2Y - int1Y > measure && Math.abs(int2X - int1X) < measure) {
//				preYear();
//			}
//			if (int2Y - int1Y < -measure && Math.abs(int2X - int1X) < measure) {
//				nextYear();
//			}
//		}
//		return super.dispatchTouchEvent(ev);
//	}
//
//	void preMonth() {
//		if (minYear >= mYear && mMonth == 1)
//			return;
//		if (mMonth == 1) {
//			mYear -= 1;
//			mMonth = 12;
//			new CalendarShow(mYear, mMonth);
//			showOnScreen();
//		} else {
//			mMonth -= 1;
//			new CalendarShow(mYear, mMonth);
//			showOnScreen();
//		}
//		table_layout.setAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.zoom_right_in));
//	}
//
//	void nextMonth() {
//		if (maxYear <= mYear && mMonth == 12)
//			return;
//		if (mMonth == 12) {
//			mYear += 1;
//			mMonth = 1;
//			new CalendarShow(mYear, mMonth);
//			showOnScreen();
//		} else {
//			mMonth += 1;
//			new CalendarShow(mYear, mMonth);
//			showOnScreen();
//		}
//		table_layout.setAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.zoom_left_in));
//	}
//
//	void nextYear() {
//		if (maxYear <= mYear)
//			return;
//		mYear += 1;
//		new CalendarShow(mYear, mMonth);
//		showOnScreen();
//		table_layout.setAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.slide_down_in));
//	}
//
//	void preYear() {
//		if (minYear >= mYear)
//			return;
//		mYear -= 1;
//		new CalendarShow(mYear, mMonth);
//		showOnScreen();
//		table_layout.setAnimation(AnimationUtils.loadAnimation(this,
//				R.anim.slide_up_in));
//	}
//
//	public void initCalendar(int year, int month, int day) {
//		new CalendarShow(mYear, mMonth);
//		showOnScreen();
//		arrLeft.setOnClickListener(new OnClick());
//		arrRight.setOnClickListener(new OnClick());
//	}
//
//	public class OnClick implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if (v.getId() == R.id.arrRight) {
//				nextMonth();
//			} else if (v.getId() == R.id.arrLeft) {
//				preMonth();
//			} else if (gotoday.equals(v)) {
//				getDate();
//				new CalendarShow(mYear, mMonth);
//				showOnScreen();
//			}
//		}
//
//	}
//
//	// 传输过来的参数
//	boolean parameterArg = false;;
//	String parameterDay;
//	String parameterYear;
//	String parameterMonth;
//
//	// 获取手机本机时间
//	int currentDay;
//	int currentYear;
//	int currentMonth;
//
//	// 定义当前要显示的时间
//	private static int mYear;
//	private static int mMonth;
//	private static int mDay;
//
//	public void getDate() {
//		mYear = Calendar.getInstance().get(Calendar.YEAR);
//		mMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
//		mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//
//		maxYear = mYear + 5;
//		minYear = mYear - 5;
//
//		currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		currentYear = Calendar.getInstance().get(Calendar.YEAR);
//		currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
//
//		// 预定时间参数
//		parameterDay = getIntent().getStringExtra("day");
//		parameterYear = getIntent().getStringExtra("month");
//		parameterMonth = getIntent().getStringExtra("year");
//		if (parameterDay != null && parameterDay != "" && parameterYear != null
//				&& parameterYear != "" && parameterMonth != null
//				&& parameterMonth != "") {
//			mYear = Integer.parseInt(parameterYear);
//			mMonth = Integer.parseInt(parameterMonth);
//			parameterArg = true;
//		}
//	}
//
//	public static String[][] dateArr = new String[6][7];
//	TableLayout table_layout;
//	ImageView arrLeft;
//	ImageView arrRight;
//	TextView gotoday;
//	private TextView date_today;
//	private TextView e00;
//	private TextView e01;
//	private TextView e02;
//	private TextView e03;
//	private TextView e04;
//	private TextView e05;
//	private TextView e06;
//	private TextView e10;
//	private TextView e11;
//	private TextView e12;
//	private TextView e13;
//	private TextView e14;
//	private TextView e15;
//	private TextView e16;
//	private TextView e20;
//	private TextView e21;
//	private TextView e22;
//	private TextView e23;
//	private TextView e24;
//	private TextView e25;
//	private TextView e26;
//	private TextView e30;
//	private TextView e31;
//	private TextView e32;
//	private TextView e33;
//	private TextView e34;
//	private TextView e35;
//	private TextView e36;
//	private TextView e40;
//	private TextView e41;
//	private TextView e42;
//	private TextView e43;
//	private TextView e44;
//	private TextView e45;
//	private TextView e46;
//	private TextView e50;
//	private TextView e51;
//	private TextView e52;
//	private TextView e53;
//	private TextView e54;
//	private TextView e55;
//	private TextView e56;
//
//	private void findViews() {
//		table_layout = (TableLayout) findViewById(R.id.table_layout);
//		arrLeft = (ImageView) findViewById(R.id.arrLeft);
//		arrRight = (ImageView) findViewById(R.id.arrRight);
//		date_today = (TextView) findViewById(R.id.date_today);
//		gotoday = (TextView) findViewById(R.id.gotoday);
//		gotoday.setOnClickListener(new OnClick());
//
//		e00 = (TextView) findViewById(R.id.e00);
//		e01 = (TextView) findViewById(R.id.e01);
//		e02 = (TextView) findViewById(R.id.e02);
//		e03 = (TextView) findViewById(R.id.e03);
//		e04 = (TextView) findViewById(R.id.e04);
//		e05 = (TextView) findViewById(R.id.e05);
//		e06 = (TextView) findViewById(R.id.e06);
//		e10 = (TextView) findViewById(R.id.e10);
//		e11 = (TextView) findViewById(R.id.e11);
//		e12 = (TextView) findViewById(R.id.e12);
//		e13 = (TextView) findViewById(R.id.e13);
//		e14 = (TextView) findViewById(R.id.e14);
//		e15 = (TextView) findViewById(R.id.e15);
//		e16 = (TextView) findViewById(R.id.e16);
//		e20 = (TextView) findViewById(R.id.e20);
//		e21 = (TextView) findViewById(R.id.e21);
//		e22 = (TextView) findViewById(R.id.e22);
//		e23 = (TextView) findViewById(R.id.e23);
//		e24 = (TextView) findViewById(R.id.e24);
//		e25 = (TextView) findViewById(R.id.e25);
//		e26 = (TextView) findViewById(R.id.e26);
//		e30 = (TextView) findViewById(R.id.e30);
//		e31 = (TextView) findViewById(R.id.e31);
//		e32 = (TextView) findViewById(R.id.e32);
//		e33 = (TextView) findViewById(R.id.e33);
//		e34 = (TextView) findViewById(R.id.e34);
//		e35 = (TextView) findViewById(R.id.e35);
//		e36 = (TextView) findViewById(R.id.e36);
//		e40 = (TextView) findViewById(R.id.e40);
//		e41 = (TextView) findViewById(R.id.e41);
//		e42 = (TextView) findViewById(R.id.e42);
//		e43 = (TextView) findViewById(R.id.e43);
//		e44 = (TextView) findViewById(R.id.e44);
//		e45 = (TextView) findViewById(R.id.e45);
//		e46 = (TextView) findViewById(R.id.e46);
//		e50 = (TextView) findViewById(R.id.e50);
//		e51 = (TextView) findViewById(R.id.e51);
//		e52 = (TextView) findViewById(R.id.e52);
//		e53 = (TextView) findViewById(R.id.e53);
//		e54 = (TextView) findViewById(R.id.e54);
//		e55 = (TextView) findViewById(R.id.e55);
//		e56 = (TextView) findViewById(R.id.e56);
//	}
//
//	public void showOnScreen() {
//		date_today.setText(mYear + "年" + mMonth + "月");
//		e00.setText(dateArr[0][0]);
//		e01.setText("" + dateArr[0][1]);
//		e02.setText("" + dateArr[0][2]);
//		e03.setText("" + dateArr[0][3]);
//		e04.setText("" + dateArr[0][4]);
//		e05.setText("" + dateArr[0][5]);
//		e06.setText("" + dateArr[0][6]);
//		if (dateArr[0][6] == "") {
//			table_layout.getChildAt(1).setVisibility(View.GONE);
//		} else {
//			table_layout.getChildAt(1).setVisibility(View.VISIBLE);
//		}
//		e10.setText("" + dateArr[1][0]);
//		e11.setText("" + dateArr[1][1]);
//		e12.setText("" + dateArr[1][2]);
//		e13.setText("" + dateArr[1][3]);
//		e14.setText("" + dateArr[1][4]);
//		e15.setText("" + dateArr[1][5]);
//		e16.setText("" + dateArr[1][6]);
//		e20.setText("" + dateArr[2][0]);
//		e21.setText("" + dateArr[2][1]);
//		e22.setText("" + dateArr[2][2]);
//		e23.setText("" + dateArr[2][3]);
//		e24.setText("" + dateArr[2][4]);
//		e25.setText("" + dateArr[2][5]);
//		e26.setText("" + dateArr[2][6]);
//		e30.setText("" + dateArr[3][0]);
//		e31.setText("" + dateArr[3][1]);
//		e32.setText("" + dateArr[3][2]);
//		e33.setText("" + dateArr[3][3]);
//		e34.setText("" + dateArr[3][4]);
//		e35.setText("" + dateArr[3][5]);
//		e36.setText("" + dateArr[3][6]);
//		e40.setText("" + dateArr[4][0]);
//		e41.setText("" + dateArr[4][1]);
//		e42.setText("" + dateArr[4][2]);
//		e43.setText("" + dateArr[4][3]);
//		e44.setText("" + dateArr[4][4]);
//		e45.setText("" + dateArr[4][5]);
//		e46.setText("" + dateArr[4][6]);
//		e50.setText("" + dateArr[5][0]);
//		e51.setText("" + dateArr[5][1]);
//		e52.setText("" + dateArr[5][2]);
//		e53.setText("" + dateArr[5][3]);
//		e54.setText("" + dateArr[5][4]);
//		e55.setText("" + dateArr[5][5]);
//		e56.setText("" + dateArr[5][6]);
//
//		if (dateArr[5][0] == "") {
//			table_layout.getChildAt(6).setVisibility(View.GONE);
//		} else {
//			table_layout.getChildAt(6).setVisibility(View.VISIBLE);
//		}
//
//		for (int i = 0; i < table_layout.getChildCount(); i++) {
//			if (table_layout.getChildAt(i) instanceof TableRow) {
//				TableRow t = (TableRow) table_layout.getChildAt(i);
//				for (int j = 0; j < t.getChildCount(); j++) {
//					if (t.getChildAt(j) instanceof TextView) {
//						TextView txt = (TextView) t.getChildAt(j);
//						final String txtValue = txt.getText().toString().trim();
//						if (i != 0)
//							txt.setBackgroundDrawable(getResources()
//									.getDrawable(R.drawable.bg_hotel_date_day));
//						if (txtValue.equals(""))
//							txt.setBackgroundDrawable(getResources()
//									.getDrawable(
//											R.drawable.bg_hotel_date_day_nor));
//						if (!parameterArg && mYear == currentYear
//								&& mMonth == currentMonth
//								&& txtValue.equals(String.valueOf(currentDay))) {
//							txt.setBackgroundDrawable(getResources()
//									.getDrawable(R.drawable.bg_hotel_date_day1));
//							txt.setTextColor(0xffffffff);
//						} else {
//							txt.setTextColor(0xff000000);
//						}
//
//						if (parameterArg
//								&& mYear == Integer.parseInt(parameterYear)
//								&& mMonth == Integer.parseInt(parameterMonth)
//								&& txtValue
//										.equals(String.valueOf(parameterDay))) {
//							txt.setBackgroundDrawable(getResources()
//									.getDrawable(R.drawable.bg_hotel_date_day1));
//						}
//
//						if (!txtValue.equals("") && i != 0) {
//							txt.setOnClickListener(new OnClickListener() {
//								@Override
//								public void onClick(View v) {
//									// TODO Auto-generated method stub
//									returnSelectDay(txtValue);
//									finish();
//								}
//							});
//						}
//					}
//				}
//			}
//		}
//	}
//}