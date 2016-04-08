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
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.untils.Dateformat;
//
//public class BookingHotelAct extends MActivity {
//	EditText ed_name, ed_roomnum, ed_phone;
//	TextView headtitle, title, roomtype, price, ruzhu_date, li_date;
//	Button bt_back, bt_sumbit;
//	String itemid, hotelid, istejia, username = "", phone = "", roomnum = "1",totalprice="";
//	private String[] days = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//	private String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
//			"10", "11", "12" };
//	private String defaultTime1 = "", defaultTime2 = "", hotelname;
//	RelativeLayout layout_ruzhu, layout_lidian;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.bookinghotel);
//		setId("BookingHotelAct");
//		istejia = getIntent().getStringExtra("istejia");
//		itemid = getIntent().getStringExtra("itemid");
//		hotelid = getIntent().getStringExtra("hotelid");
//		hotelname = getIntent().getStringExtra("title");
//		totalprice=getIntent().getStringExtra("price");
//		headtitle = (TextView) findViewById(R.bookinghotel.head_title);
//		title = (TextView) findViewById(R.bookinghotel.title);
//		roomtype = (TextView) findViewById(R.bookinghotel.roomtype);
//		price = (TextView) findViewById(R.bookinghotel.price);
//		ruzhu_date = (TextView) findViewById(R.bookinghotel.zhu_date);
//		li_date = (TextView) findViewById(R.bookinghotel.li_date);
//		ed_name = (EditText) findViewById(R.bookinghotel.ed_personname);
//		ed_roomnum = (EditText) findViewById(R.bookinghotel.ed_roomnum);
//		ed_roomnum.setText(roomnum);
//		price.setText(totalprice);
//		title.setText(hotelname);
//		ed_phone = (EditText) findViewById(R.bookinghotel.ed_personphone);
//		bt_back = (Button) findViewById(R.bookinghotel.back);
//		bt_sumbit = (Button) findViewById(R.bookinghotel.bt_sumbit);
//		layout_ruzhu = (RelativeLayout) findViewById(R.bookinghotel.layout_ruzhu);
//		layout_lidian = (RelativeLayout) findViewById(R.bookinghotel.layout_lidian);
//		Time nowtime = new Time();
//		nowtime.setToNow();
//		defaultTime1 = nowtime.year + "-" + (nowtime.month + 1) + "-"
//				+ nowtime.monthDay;
//		defaultTime2 = defaultTime1;
//		ruzhu_date
//				.setText(months[nowtime.month] + "月" + nowtime.monthDay + "日");
//		li_date.setText(months[nowtime.month] + "月" + nowtime.monthDay + "日");
//		bt_back.setOnClickListener(new clic());
//		bt_sumbit.setOnClickListener(new clic());
////		layout_ruzhu.setOnClickListener(new clic());
////		layout_lidian.setOnClickListener(new clic());
//
//	}
//
//	public class clic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.bookinghotel.layout_ruzhu:
//				Intent intent1 = new Intent(v.getContext(), CalendarAct.class);
//				intent1.putExtra("type", 4);
//				v.getContext().startActivity(intent1);
//				break;
//			case R.bookinghotel.layout_lidian:
//				Intent intent2 = new Intent(v.getContext(), CalendarAct.class);
//				intent2.putExtra("type", 5);
//				v.getContext().startActivity(intent2);
//				break;
//			case R.bookinghotel.back:
//				BookingHotelAct.this.finish();
//				break;
//			case R.bookinghotel.bt_sumbit:
//				if (F.USER_ID.equals("")) {
//					F.toLogin(BookingHotelAct.this, "BookingHotelAct", "", 0);
//					return;
//				}
//				if (Dateformat.DateToInt(defaultTime1) > Dateformat
//						.DateToInt(defaultTime2)) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"离店时间不能小于住店时间", Toast.LENGTH_SHORT);
//					toast.show();
//					return;
//				}
//				username = ed_name.getText().toString().trim();
//				roomnum = ed_roomnum.getText().toString();
//				phone = ed_phone.getText().toString();
//				if (roomnum.length() <= 0) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"请输入预订房间数", Toast.LENGTH_SHORT);
//					toast.show();
//					ed_name.requestFocus();
//					return;
//				}
//				if (username.length() <= 0) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"请输入用户名", Toast.LENGTH_SHORT);
//					toast.show();
//					ed_name.requestFocus();
//					return;
//				} else if (username.length() > 20) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"输入用户名有误，请重新输入", Toast.LENGTH_SHORT);
//					toast.show();
//					ed_name.requestFocus();
//					return;
//				}
//				if (phone.length() <= 0) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"请输入手机号码", Toast.LENGTH_SHORT);
//					toast.show();
//					ed_phone.requestFocus();
//					return;
//				} else if (username.length() > 11) {
//					Toast toast = Toast.makeText(BookingHotelAct.this,
//							"您输入的手机号码有误，请重新输入", Toast.LENGTH_SHORT);
//					toast.show();
//					ed_phone.requestFocus();
//					return;
//				}
//				dataLoad(null);
//				break;
//			}
//
//		}
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
//		if (obj != null) {
//		}
//		if (type == 1) {
//			if (Dateformat.DateToInt(defaultTime1) > Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "选择时间不能小于当前时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			defaultTime1 = obj.toString();
//			ruzhu_date.setText(months[Dateformat.getMonth(obj.toString())]
//					+ "月" + Dateformat.getDate(obj.toString()) + "日");
//		} else if (type == 2) {
//			if (Dateformat.DateToInt(defaultTime1) > Dateformat.DateToInt(obj
//					.toString())) {
//				Toast.makeText(this, "选择时间不能小于入住时间", Toast.LENGTH_SHORT).show();
//				return;
//			}
//			defaultTime2 = obj.toString();
//			li_date.setText(months[Dateformat.getMonth(obj.toString())]
//					+ "月" + Dateformat.getDate(obj.toString()) + "日");
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("jyjdyd")) {
//			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
//			{
//				if (builder.getErrorCode() == 0) {
//					Toast.makeText(this, "预订成功", Toast.LENGTH_SHORT).show();
//				} else {
//						Toast.makeText(this, builder.getErrorMsg(),
//								Toast.LENGTH_SHORT).show();
//				}
//
//			}
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("JYJDYD", new String[][] {
//				{ "hotelid", hotelid }, { "roomid", itemid },
//				{ "indate", defaultTime1 }, { "outdate", defaultTime2 },
//				{ "roomcount", roomnum }, { "paytype", "1" },//1到店付款2在线支付
//				{ "ordername", username }, { "orderphone", phone },{ "is_tejia", istejia },
//				{ "userid", F.USER_ID } }), });
//	}
//
//}
