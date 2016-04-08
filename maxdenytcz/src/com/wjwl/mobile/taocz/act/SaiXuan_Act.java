//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//
//public class SaiXuan_Act extends MActivity {
//	Button back, ok_btn;
//	public static TextView text, text1, text2, text3;
//	LinearLayout linear1, linear2, linear3;
//	static String areaid, distance, star;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.saixuan);
//		areaid = "";
//		distance = "";
//		star = "";
//		back = (Button) findViewById(R.saixuan.back);
//		ok_btn = (Button) findViewById(R.saixuan.saixuan);
//		text = (TextView) findViewById(R.saixuan.text);
//		text1 = (TextView) findViewById(R.saixuan.text1);
//		text2 = (TextView) findViewById(R.saixuan.text2);
//		text3 = (TextView) findViewById(R.saixuan.text3);
//		linear1 = (LinearLayout) findViewById(R.saixuan.clic_layout1);
//		linear2 = (LinearLayout) findViewById(R.saixuan.clic_layout2);
//		linear3 = (LinearLayout) findViewById(R.saixuan.clic_layout3);
//		back.setOnClickListener(new click());
//		linear1.setOnClickListener(new click());
//		linear2.setOnClickListener(new click());
//		linear3.setOnClickListener(new click());
//		ok_btn.setOnClickListener(new click());
//	}
//
//	public class click implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.saixuan.clic_layout1:
//				Intent i1 = new Intent();
//				i1.putExtra("act", "SaiXuan_Act");
//				i1.setClass(getApplication(), AreaSelectAct.class);
//				startActivity(i1);
//				break;
//			case R.saixuan.clic_layout2:
//				Intent i2 = new Intent();
//				i2.setClass(getApplication(), DistanceSelectAct.class);
//				startActivity(i2);
//				break;
//			case R.saixuan.clic_layout3:
//				Intent i3 = new Intent();
//				i3.setClass(getApplication(), StarSelectAct.class);
//				startActivity(i3);
//				break;
//			case R.saixuan.back:
//				SaiXuan_Act.this.finish();
//				break;
//			case R.saixuan.saixuan:
//				Intent intent = new Intent();
//				intent.putExtra("areaid", areaid);
//				intent.putExtra("distance", distance);
//				intent.putExtra("stars", star);
//				SaiXuan_Act.this.setResult(RESULT_OK, intent);
//				SaiXuan_Act.this.finish();
//				break;
//			}
//		}
//	}
//}
