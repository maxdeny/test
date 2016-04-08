package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Intent;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.text.format.Time;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.wheelview.ArrayWheelAdapter;
//import com.wjwl.mobile.taocz.widget.wheelview.WheelMain;
//import com.wjwl.mobile.taocz.widget.wheelview.WheelView;
//	
//public class OneKeyOK_Act extends MActivity implements OnClickListener{
//	LinearLayout linear_day,linear_time,linear_select;
//	EditText phone,number,name,beizhu;
//	TextView day,time,select_text;
//	private PopupWindow pp;
//	private WheelView country,country1,country2;
//	int position1, position2, position3;
//	private Button btnok,back;
//	String years[] = new String[] { "2013年", "2014年", };
//	String mothes[] = new String[] { "1月", "2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月", };
//	String days1[] = new String[] { "01", "02", };
//	String houses[] ;//= new String[] { "只订大厅", "只订包房","优先订大厅","包房也可以","优先订包房","大厅也可以", }
//	String businessid;
//	Button login;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.onekeyok);
//		businessid=getIntent().getStringExtra("businessid");
//		linear_day=(LinearLayout) findViewById(R.onekeyok.linear_day);
//		linear_time=(LinearLayout) findViewById(R.onekeyok.linear_time);
//		linear_select=(LinearLayout) findViewById(R.onekeyok.linear_select);
//		phone=(EditText) findViewById(R.onekeyok.phone);
//		number=(EditText) findViewById(R.onekeyok.number);
//		name=(EditText) findViewById(R.onekeyok.name);
//		beizhu=(EditText) findViewById(R.onekeyok.beizhu);
//		day=(TextView) findViewById(R.onekeyok.day);
//		time=(TextView) findViewById(R.onekeyok.time);
//		select_text=(TextView) findViewById(R.onekeyok.select_text);
//		btnok=(Button) findViewById(R.onekeyok.btnok);
//		back=(Button) findViewById(R.onekeyok.booking);
//		login=(Button) findViewById(R.onekeyok.login);
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//		if(getIntent().getStringExtra("date")!=null&&getIntent().getStringExtra("week")!=null){
//			day.setText(getIntent().getStringExtra("date")+"  "+getIntent().getStringExtra("week"));
//		}else{
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");     
//			Date curDate = new Date(System.currentTimeMillis());//获取当前时间      
//			String str = formatter.format(curDate);
//			String str1="";
//			Calendar cal = Calendar.getInstance();
//			int day_week=cal.get(Calendar.DAY_OF_WEEK);
//			if(day_week==1){
//				str1="星期天";
//			}else if(day_week==2){
//				str1="星期一";
//			}else if(day_week==3){
//				str1="星期二";
//			}else if(day_week==4){
//				str1="星期三";
//			}else if(day_week==5){
//				str1="星期四";
//			}else if(day_week==6){
//				str1="星期五";
//			}else if(day_week==7){
//				str1="星期六";
//			}
//			day.setText(str+"  "+str1);
//		}
//		if(getIntent().getStringExtra("time")!=null){
//			time.setText(getIntent().getStringExtra("time"));
//		}else{
//			long times=System.currentTimeMillis();
//			 final Calendar mCalendar=Calendar.getInstance();
//			 mCalendar.setTimeInMillis(times);
//			int  mHour=mCalendar.get(Calendar.HOUR);
//			 int mMinuts=mCalendar.get(Calendar.MINUTE);
//			time.setText(mHour+":"+mMinuts);
//		}
//		if(F.USER_ID.length()>1){
//			login.setVisibility(View.INVISIBLE);
//		}else{
//			login.setVisibility(View.VISIBLE);
//		}
//		login.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent in=new Intent(OneKeyOK_Act.this,LoginAct.class);
//				startActivity(in);
//			}
//		});
//		linear_select.setOnClickListener(this);
//		linear_time.setOnClickListener(this);
//		linear_day.setOnClickListener(this);
//		btnok.setOnClickListener(this);
//		dataLoad(new int[]{2});
//	}
//	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.onekeyok.linear_day:
//			select();
//			break;
//		case R.onekeyok.linear_time:
//			LayoutInflater f=LayoutInflater.from(getApplication());
//			View view=f.inflate(R.layout.timechoose, null);
//			Button btn_ok=(Button) view.findViewById(R.id.btn_ok);
//			Button btn_cc=(Button) view.findViewById(R.id.btn_cc);
//			final TimePicker tp=(TimePicker) view.findViewById(R.id.timepicker);
//			tp.setIs24HourView(true);
//			final Dialog d=new AlertDialog.Builder(OneKeyOK_Act.this).setTitle("选择时间")
//			.setView(view).show();
//			btn_ok.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					time.setText(""+tp.getCurrentHour()+":"+tp.getCurrentMinute());
//					d.dismiss();
//				}
//			});
//			btn_cc.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					d.dismiss();
//				}
//			});
//			break;
//		case R.onekeyok.linear_select:
//			Select(houses,select_text);
//			break;
//		case R.onekeyok.btnok:
//			dataLoad(new int[]{1});
//			break;
//		default:
//			break;
//		}
//	}
//	public void select(){
//		View view = getLayoutInflater().inflate(R.layout.place2, null);
//		LinearLayout timePicker1 = (LinearLayout) view.findViewById(R.place2.timePicker1);
//		final WheelMain wheelMain = new WheelMain(timePicker1);
//		wheelMain.initDateTimePicker();
//		Button btn_ok1 = (Button) view.findViewById(R.place2.ok);
//		btn_ok1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				day.setText(wheelMain.getTime());
//				pp.dismiss();
//			}
//		});
//		pp = new PopupWindow(view, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT, true);
//		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
//		if (getParent() != null) {
//			pp.showAtLocation(getParent().findViewById(R.frame.linear),
//					Gravity.BOTTOM, 0, 0);
//		} else {
//			pp.showAtLocation(findViewById(R.onekeyok.linear),
//					Gravity.BOTTOM, 0, 0);
//		}
//	}
//	
//	public void Select(String[] str ,final TextView tv) {
//		View view = getLayoutInflater().inflate(R.layout.place1, null);
//		country = (WheelView) view.findViewById(R.place1.wheel);
//		Button btn_ok = (Button) view.findViewById(R.place1.ok);
//		btn_ok.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//					tv.setText(country.getAdapter().getItem(
//							country.getCurrentItem()));
//				pp.dismiss();
//			}
//		});
//		country.setVisibleItems(3);
//		country.setAdapter(new ArrayWheelAdapter<String>(str));
//		pp = new PopupWindow(view, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT, true);
//		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
//		if (getParent() != null) {
//			pp.showAtLocation(getParent().findViewById(R.frame.linear),
//					Gravity.BOTTOM, 0, 0);
//		} else {
//			pp.showAtLocation(findViewById(R.onekeyok.linear),
//					Gravity.BOTTOM, 0, 0);
//		}
//	}
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("yyctydfx")) {
//			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//			if(builder.getCcategoryList().size()>0)
//				houses=new String[builder.getCcategoryList().size()];
//			for(int i=0;i<builder.getCcategoryList().size();i++){
//				String s=builder.getCcategoryList().get(i).getCategoryname();
//				houses[i]=s;
//			}
//		}
//		if (son.build != null && son.mgetmethod.equals("yyctyd")) {
//			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
//			builder.getErrorMsg();
//			builder.getErrorCode();
//			Toast.makeText(getApplication(), builder.getErrorMsg(), 1).show();
//		}
//	}
//	
//	
//	@Override
//	public void dataLoad(int[] types) {
//		if(types[0]==1){//如果数据不完整可以尝试配置接口app-->msg_retn
//			//http://life.taocz.com/life.php?app=oretn&act=YYCTYD&debug=1&phonenumber=15618521812
//				//&ordertime=2013031810:00:00&peoplecount=15
//				//&ordername=wuhongbing&roomtypeid=1&userid=11&location_id=1279&gender=1
//			this.loadData(new Updateone[] { new Updateone("YYCTYD",
//					new String[][] { { "phonenumber", "15618521812" }, { "userid", "11" }, //F.USER_ID
//					{ "ordertime", "2013031810:00:00" }, { "peoplecount","15" }, { "ordername", "wuhongbing" },
//					{ "roomtypeid", "1" }, { "mark", "留言信息" }, { "gender", "1" },{ "location_id", "1279" }
//					}), });
//		}
//		if(types[0]==2){
//			this.loadData(new Updateone[] { new Updateone("YYCTYDFX",
//					new String[][] { { "businessid", businessid }}), });
//		}
//	}
//
//}
