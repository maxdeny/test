package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//
//public class BookingRestaurantAct extends MActivity {
//	RelativeLayout clic_layout1, clic_layout2, clic_layout3;
//	Button bt_search, bt_1, bt_2,bt_back;
//	TextView text12, text22, text32;
//	EditText edit;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.bookingrestaurant);
//		setId("BookingRestaurantAct");
//		clic_layout1 = (RelativeLayout) findViewById(R.bookingrestaurant.clic_layout1);
//		clic_layout2 = (RelativeLayout) findViewById(R.bookingrestaurant.clic_layout2);
//		clic_layout3 = (RelativeLayout) findViewById(R.bookingrestaurant.clic_layout3);
//		bt_1 = (Button) findViewById(R.bookingrestaurant.bt_1);
//		bt_2 = (Button) findViewById(R.bookingrestaurant.bt_2);
//		bt_back=(Button)findViewById(R.bookingrestaurant.back);
//		text12 = (TextView) findViewById(R.bookingrestaurant.text12);
//		text22 = (TextView) findViewById(R.bookingrestaurant.text22);
//		text32 = (TextView) findViewById(R.bookingrestaurant.text32);
//		bt_search=(Button) findViewById(R.bookingrestaurant.bt_search);
//		edit=(EditText) findViewById(R.bookingrestaurant.ed_search);
//		clic_layout1.setOnClickListener(new onclic());
//		clic_layout2.setOnClickListener(new onclic());
//		clic_layout3.setOnClickListener(new onclic());
//		bt_1.setOnClickListener(new onclic());
//		bt_2.setOnClickListener(new onclic());
//		bt_back.setOnClickListener(new onclic());
//		bt_search.setOnClickListener(new onclic());
//	}
//
//	public class onclic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
////			case R.bookingrestaurant.bt_1:
////				Intent i = new Intent();
////				i.setClass(BookingRestaurantAct.this, CookingStyleSelectAct.class);
////				startActivity(i);
////				break;
//			case R.bookingrestaurant.bt_2:
//				Intent i1 = new Intent();
//				i1.putExtra("act", "BookingRestaurantAct");
//				i1.setClass(BookingRestaurantAct.this, AreaSelectAct.class);
//				startActivity(i1);
//				break;
//			case R.bookingrestaurant.clic_layout1:
//				Intent i4 = new Intent();
//				i4.setClass(BookingRestaurantAct.this, GroupBuyingListAct.class);//CouponAct
//				startActivity(i4);
//				break;
//			case R.bookingrestaurant.clic_layout2:
//				Intent i2 = new Intent();
//				i2.setClass(BookingRestaurantAct.this,
//						PreferentialSetMealAct.class);
//				startActivity(i2);
//				break;
//			case R.bookingrestaurant.clic_layout3:
//				Intent i3 = new Intent();
//				i3.setClass(BookingRestaurantAct.this, EatPurposeAct.class);
//				startActivity(i3);
//				break;
//			case R.bookingrestaurant.back:
//				BookingRestaurantAct.this.finish();
//				break;
//			case R.bookingrestaurant.bt_search:
//				String keywords=edit.getText().toString();
//				if(!keywords.equals("")){
//					Intent it = new Intent();
//					it.putExtra("keywords", keywords);
//					it.setClass(BookingRestaurantAct.this, RestaurantDetailsListAct.class);
//					startActivity(it);
//				}else{
//					Toast.makeText(getApplication(), "请输入搜索词", 1).show();
//				}
//				break;
//			}
//		}
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (resultCode) {
//		case RESULT_OK:
//			if (data.getStringExtra("act").equals("EatPurposeAct")) {
//				String title = data.getStringExtra("title");
//				text32.setText(title);
//			}
//
//		}
//	}
//}
