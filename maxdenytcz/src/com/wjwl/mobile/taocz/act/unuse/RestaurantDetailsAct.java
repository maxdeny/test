package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.format.Time;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.MImageView;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.dialog.ComDialog;
//import com.wjwl.mobile.taocz.untils.Dateformat;
//
//public class RestaurantDetailsAct extends MActivity {
//	TextView title, yinxiang1, yinxiang2, renjun, day, date, week, time, spay,
//			stime, fanwei, quan_content, quan_price, taocan_content,
//			di_content, di_price, hui_content, biaoqian1, biaoqian2, biaoqian3,
//			t_biaoqian1, t_biaoqian2, t_biaoqian3, address, phone, tesenum,
//			tuijian_title1, tuijian_renqi1, tuijian_title2, tuijian_renqi2,
//			tuijian_title3, tuijian_renqi3;
//	Button bt_yuding, bt_dingcan, bt_baoming,back;
//	MImageView img;
//	String itemid, str_phone = "";
//	int _minute, _hour;
//	RelativeLayout clic_layoutdate, clic_layouttime, clic_layoutdistance,
//			clic_layoutquan, clic_layouttaocan, clic_layouthui,
//			clic_layoutaddress, clic_layoutphone, clic_layouttuijian,
//			clic_layoutjiaotong, clic_layoutdaohang, clic_layoutbucuo,
//			clic_layoutdi, layout_tuijian1, layout_tuijian2, layout_tuijian3;
//	ImageView line1, line2, line3;
//	private String[] days = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
//	private String[] months = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
//			"10", "11", "12" };
//	private String defaultTime1 = "", businessbusway = "";
//	String b_longitude, b_latitude;
//	LinearLayout layout_wmfw;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.restaurantdetails);
//		setId("RestaurantDetailsAct");
//		initview();
//
//		dataLoad(null);
//
//	}
//
//	private void initview() {
//		// TODO Auto-generated method stub
//		Intent i = getIntent();
//		itemid = i.getStringExtra("itemid");
//		// itemid = "1260";
//		//itemid = "1298";
//		back=(Button) findViewById(R.restaurantdetails.back);
//		title = (TextView) findViewById(R.restaurantdetails.title);
//		yinxiang1 = (TextView) findViewById(R.restaurantdetails.yinxiang1);
//		yinxiang1.setVisibility(View.GONE);
//		yinxiang2 = (TextView) findViewById(R.restaurantdetails.yinxiang2);
//		yinxiang2.setVisibility(View.GONE);
//		renjun = (TextView) findViewById(R.restaurantdetails.renjun);
//		day = (TextView) findViewById(R.restaurantdetails.day);
//		date = (TextView) findViewById(R.restaurantdetails.date);
//		week = (TextView) findViewById(R.restaurantdetails.week);
//		time = (TextView) findViewById(R.restaurantdetails.time);
//		spay = (TextView) findViewById(R.restaurantdetails.spay);
//		stime = (TextView) findViewById(R.restaurantdetails.stime);
//		fanwei = (TextView) findViewById(R.restaurantdetails.fanwei);
//		quan_content = (TextView) findViewById(R.restaurantdetails.quan_content);
//		quan_price = (TextView) findViewById(R.restaurantdetails.quan_price);
//		taocan_content = (TextView) findViewById(R.restaurantdetails.taocan_content);
//		di_content = (TextView) findViewById(R.restaurantdetails.di_content);
//		di_price = (TextView) findViewById(R.restaurantdetails.di_price);
//		hui_content = (TextView) findViewById(R.restaurantdetails.hui_content);
//		biaoqian1 = (TextView) findViewById(R.restaurantdetails.biaoqian1);
//		biaoqian2 = (TextView) findViewById(R.restaurantdetails.biaoqian2);
//		biaoqian3 = (TextView) findViewById(R.restaurantdetails.biaoqian3);
//		t_biaoqian1 = (TextView) findViewById(R.restaurantdetails.t_biaoqian1);
//		t_biaoqian2 = (TextView) findViewById(R.restaurantdetails.t_biaoqian2);
//		t_biaoqian3 = (TextView) findViewById(R.restaurantdetails.t_biaoqian3);
//		address = (TextView) findViewById(R.restaurantdetails.address);
//		phone = (TextView) findViewById(R.restaurantdetails.phone);
//		tesenum = (TextView) findViewById(R.restaurantdetails.tese_num);
//		tuijian_title1 = (TextView) findViewById(R.restaurantdetails.tuijian_title1);
//		tuijian_renqi1 = (TextView) findViewById(R.restaurantdetails.tuijian_renqi1);
//		tuijian_title2 = (TextView) findViewById(R.restaurantdetails.tuijian_title2);
//		tuijian_renqi2 = (TextView) findViewById(R.restaurantdetails.tuijian_renqi2);
//		tuijian_title3 = (TextView) findViewById(R.restaurantdetails.tuijian_title3);
//		tuijian_renqi3 = (TextView) findViewById(R.restaurantdetails.tuijian_renqi3);
//		img = (MImageView) findViewById(R.restaurantdetails.img);
//		clic_layoutdate = (RelativeLayout) findViewById(R.restaurantdetails.clic_layout1);
//		clic_layouttime = (RelativeLayout) findViewById(R.restaurantdetails.clic_layout2);
//		clic_layoutdistance = (RelativeLayout) findViewById(R.restaurantdetails.clic_layout4);
//		clic_layoutquan = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutquan);
//		clic_layouttaocan = (RelativeLayout) findViewById(R.restaurantdetails.clic_layouttaocan);
//		clic_layouthui = (RelativeLayout) findViewById(R.restaurantdetails.clic_layouthui);
//		clic_layoutaddress = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutaddress);
//		clic_layoutphone = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutphone);
//		clic_layouttuijian = (RelativeLayout) findViewById(R.restaurantdetails.clic_layouttuijian);
//		clic_layoutjiaotong = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutjiaotong);
//		clic_layoutdaohang = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutdaohang);
//		clic_layoutdi = (RelativeLayout) findViewById(R.restaurantdetails.clic_layoutdi);
//		layout_wmfw=(LinearLayout)findViewById(R.restaurantdetails.layout_wmfw);
//		line1 = (ImageView) findViewById(R.restaurantdetails.line1);
//		line2 = (ImageView) findViewById(R.restaurantdetails.line2);
//		line3 = (ImageView) findViewById(R.restaurantdetails.line3);
//		layout_tuijian1 = (RelativeLayout) findViewById(R.restaurantdetails.tuijian_layout1);
//		layout_tuijian2 = (RelativeLayout) findViewById(R.restaurantdetails.tuijian_layout2);
//		layout_tuijian3 = (RelativeLayout) findViewById(R.restaurantdetails.tuijian_layout3);
//		bt_yuding = (Button) findViewById(R.restaurantdetails.bt_yuding);
//		bt_dingcan = (Button) findViewById(R.restaurantdetails.bt_diancan);
//		bt_baoming = (Button) findViewById(R.restaurantdetails.bt_baoming);
//		clic_layoutdate.setOnClickListener(new onclic());
//		clic_layouttime.setOnClickListener(new onclic());
//		clic_layoutdistance.setOnClickListener(new onclic());
//		clic_layoutquan.setOnClickListener(new onclic());
//		clic_layoutdi.setOnClickListener(new onclic());
//		clic_layouttaocan.setOnClickListener(new onclic());
//		clic_layouthui.setOnClickListener(new onclic());
//		clic_layoutaddress.setOnClickListener(new onclic());
//		clic_layoutphone.setOnClickListener(new onclic());
//		clic_layouttuijian.setOnClickListener(new onclic());
//		clic_layoutjiaotong.setOnClickListener(new onclic());
//		clic_layoutdaohang.setOnClickListener(new onclic());
//		bt_yuding.setOnClickListener(new onclic());
//		bt_dingcan.setOnClickListener(new onclic());
//		bt_baoming.setOnClickListener(new onclic());
//		Time nowtime = new Time();
//		nowtime.setToNow();
//		day.setText("今天");
//		defaultTime1 = nowtime.year + "-" + (nowtime.month + 1) + "-"
//				+ nowtime.monthDay;
//		date.setText(months[nowtime.month] + "月" + nowtime.monthDay + "日");
//		week.setText(days[nowtime.weekDay]);
//		time.setText("" + nowtime.hour + ":" + nowtime.minute);
//		_hour = nowtime.hour;
//		_minute = nowtime.minute;
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//	}
//
//	public class onclic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.restaurantdetails.clic_layout1:
//				Intent intent1 = new Intent(v.getContext(), CalendarAct.class);
//				intent1.putExtra("type", 3);
//				v.getContext().startActivity(intent1);
//				break;
//			case R.restaurantdetails.clic_layout2:
//				TimePickerDialog tp = new TimePickerDialog(
//						RestaurantDetailsAct.this, new timeListener(), _hour,
//						_minute, true);
//				tp.show();
//				break;
//			case R.restaurantdetails.clic_layoutquan:
//				break;
//			case R.restaurantdetails.clic_layouttaocan:
//				Intent i = new Intent();
//				i.putExtra("act", "RestaurantDetailsAct");
//				i.putExtra("businessid", itemid);
//				i.setClass(RestaurantDetailsAct.this, GroupBuyingListAct.class);
//				startActivity(i);
//				break;
//			case R.restaurantdetails.clic_layouthui:
//				Intent i2 = new Intent();
//				i2.putExtra("act", "RestaurantDetailsAct");
//				i2.putExtra("businessid", itemid);
//				i2.setClass(RestaurantDetailsAct.this, YHHDBaomingAct.class);
//				startActivity(i2);
//				break;
//			case R.restaurantdetails.clic_layoutdi:
//				Intent i1 = new Intent();
//				i1.putExtra("act", "RestaurantDetailsAct");
//				i1.putExtra("businessid", itemid);
//				i1.setClass(RestaurantDetailsAct.this, GroupBuyingListAct.class);
//				startActivity(i1);
//				break;
//			case R.restaurantdetails.clic_layoutaddress:
//				break;
//			case R.restaurantdetails.clic_layoutphone:
//				Uri uri = Uri.parse("tel:" + str_phone);
//				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//				startActivity(intent);
//				break;
//			case R.restaurantdetails.clic_layouttuijian:
//				break;
//			case R.restaurantdetails.clic_layoutjiaotong:
//				ComDialog dia = new ComDialog(RestaurantDetailsAct.this);
//				dia.setText(businessbusway);
//				dia.show();
//				break;
//			case R.restaurantdetails.clic_layout4:
//			case R.restaurantdetails.clic_layoutdaohang:
//				Intent i4 = new Intent();
//				i4.putExtra("b_latitude", b_latitude);
//				i4.putExtra("b_longitude", b_longitude);
//				i4.setClass(getApplication(), Business_MapAct.class);
//				startActivity(i4);
//				break;
//			case R.restaurantdetails.bt_yuding:
//				Intent ydintent = new Intent();
//				ydintent.putExtra("businessid", itemid);
//				ydintent.putExtra("date", date.getText());
//				ydintent.putExtra("week", week.getText());
//				ydintent.putExtra("time", time.getText());
//				ydintent.setClass(RestaurantDetailsAct.this, OneKeyOK_Act.class);
//				startActivity(ydintent);
//				break;
//			case R.restaurantdetails.bt_diancan:
//				break;
//			case R.restaurantdetails.bt_baoming:
//				break;
//			}
//		}
//	}
//
//	private class timeListener implements TimePickerDialog.OnTimeSetListener {
//
//		@Override
//		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//			// TODO Auto-generated method stub
//			time.setText(hourOfDay + ":" + minute);
//		}
//
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
//				day.setText("明天");
//			else if (tempdays == 2)
//				day.setText("后天");
//			else if (tempdays < 7)
//				day.setText("一星期内");
//			else if (tempdays <= 30)
//				day.setText("一个月内");
//			else if (tempdays > 30)
//				day.setText("一个月后");
//			date.setText(months[Dateformat.getMonth(obj.toString())] + "月"
//					+ Dateformat.getDate(obj.toString()) + "日");
//			week.setText(xingqi);
//
//		}
//	}
//
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("yyctxq")) {
//			Msg_CbusinessinfoList.Builder builder = (Msg_CbusinessinfoList.Builder) son.build;
//			Msg_Cbusinessinfo item = builder.getCbusinessinfo(0);
//			title.setText(item.getBusinessname());
//			if (!item.getRecommend().equals("")) {
//				String arrays[] = item.getRecommend().split(",");
//				if (arrays.length >= 1) {
//					yinxiang1.setVisibility(View.GONE);
//					yinxiang1.setText(arrays[0]);
//				}
//				if (arrays.length >= 2) {
//					yinxiang2.setText(arrays[1]);
//					yinxiang2.setVisibility(View.GONE);
//				}
//
//			}
//			if(item.getWmOpentime().equals(""))
//				layout_wmfw.setVisibility(View.GONE);
//			else
//				layout_wmfw.setVisibility(View.VISIBLE);
//			b_latitude = item.getLatitude();
//			b_longitude = item.getLongitude();
//			renjun.setText("￥" + item.getEverycost());
//			spay.setText("￥" + item.getWmLogisticsmoney());
//			stime.setText(item.getWmOpentime());
//			fanwei.setText("外送范围：周围" + item.getWmSendrange() + "公里");
//			if (!item.getRemark().equals("")) {// 优惠券
//				String arrays[] = item.getRemark().split(",");
//				if (arrays.length > 1)
//					quan_price.setText(arrays[1]);
//				quan_content.setText(arrays[0]);
//			} else {
//				clic_layoutquan.setVisibility(View.GONE);
//				line1.setVisibility(View.GONE);
//			}
//
//			if (!item.getWmWarntype().equals(""))// 优惠套餐
//				taocan_content.setText(item.getWmWarntype());
//			else {
//				clic_layouttaocan.setVisibility(View.GONE);
//				line2.setVisibility(View.GONE);
//			}
//			if (!item.getWmMajor().equals(""))// 优惠活动
//				hui_content.setText(item.getWmMajor());
//			else {
//				clic_layouthui.setVisibility(View.GONE);
//			}
//			if (!item.getWmOrdertel().equals("")) {// 抵用券
//				String arrays[] = item.getWmOrdertel().split(",");
//				if (arrays.length > 1)
//					di_price.setText(arrays[1]);
//				di_content.setText(arrays[0]);
//			} else {
//				clic_layoutdi.setVisibility(View.GONE);
//				line3.setVisibility(View.GONE);
//			}
//
//			if (!item.getEnvironment().equals("")) {
//				String arrays[] = item.getEnvironment().split(",");
//				if (arrays.length == 6) {
//					t_biaoqian1.setText(arrays[0]);
//					biaoqian1.setText(arrays[1]);
//					t_biaoqian2.setText(arrays[2]);
//					biaoqian2.setText(arrays[3]);
//					t_biaoqian3.setText(arrays[4]);
//					biaoqian3.setText(arrays[5]);
//				} else if (arrays.length == 4) {
//					t_biaoqian1.setText(arrays[0]);
//					biaoqian1.setText(arrays[1]);
//					t_biaoqian2.setText(arrays[2]);
//					biaoqian2.setText(arrays[3]);
//				}
//
//			}
//			address.setText(item.getBusinessaddress());
//			phone.setText(item.getBusinessphone());
//			str_phone=item.getBusinessphone();
//			tesenum.setText("特色菜推荐（0）");
//			if (!item.getGoodin().equals("")) {
//				String arrays[] = item.getGoodin().split(",");
//				if (arrays.length >= 6) {
//					tuijian_title3.setText(arrays[4]);
//					tuijian_renqi3.setText("人气：" + arrays[5]);
//					layout_tuijian3.setVisibility(View.VISIBLE);
//				}
//				if (arrays.length >= 4) {
//					tuijian_title2.setText(arrays[2]);
//					tuijian_renqi2.setText("人气：" + arrays[3]);
//					layout_tuijian2.setVisibility(View.VISIBLE);
//				}
//				if (arrays.length >= 1) {
//					tuijian_title1.setText(arrays[0]);
//					tuijian_renqi1.setText("人气：" + arrays[1]);
//					layout_tuijian1.setVisibility(View.VISIBLE);
//				}
//				tesenum.setText("特色推荐(" + arrays.length / 2 + ")");
//			}
//
//			businessbusway = item.getBusinessbusway();
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("YYCTXQ",
//				new String[][] { { "businessid", itemid } }), });
//	}
//}
