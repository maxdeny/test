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
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//
//public class TakeOutAct1 extends MActivity {
//	RelativeLayout clic_layout1;
//	Button bt_1, bt_2,btn_search,back;
//	EditText edit;
//	String keywords;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setId("TakeOutAct1");
//		setContentView(R.layout.takeout1);
//		clic_layout1 = (RelativeLayout) findViewById(R.takeout1.clic_layout1);
//		bt_1 = (Button) findViewById(R.takeout1.bt_1);
//		bt_2 = (Button) findViewById(R.takeout1.bt_2);
//		bt_1.setOnClickListener(new onclic());
//		bt_2.setOnClickListener(new onclic());
//		clic_layout1.setOnClickListener(new onclic());
//		edit=(EditText) findViewById(R.takeout1.ed_search);
//		btn_search=(Button) findViewById(R.takeout1.bt_search);
//		btn_search.setOnClickListener(new onclic());
//		back=(Button) findViewById(R.takeout1.back);
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
//			Intent i=new Intent();
//			switch (v.getId()) {
//			case R.takeout1.bt_1:
//				i.setClass(getApplication(), TakeOutListAct1.class);
//				i.putExtra("fujin", "fujin");
//				startActivity(i);
//				break;
//			case R.takeout1.bt_2:
//				i.setClass(getApplication(), AreaSelectAct.class);
//				startActivity(i);
//				break;
//			case R.takeout1.clic_layout1:
//				i.setClass(getApplication(), CookingStyleSelectAct.class);
//				startActivity(i);
//				break;
//			case R.takeout1.bt_search:
//				keywords=String.valueOf(edit.getText());
//				if(keywords.trim().length()>0){
//					Intent in=new Intent();
//					in.putExtra("search", "search");
//					in.putExtra("keywords", keywords);
//					in.setClass(getApplication(), TakeOutListAct1.class);
//					startActivity(in);
//				}
//				break;
//			}
//		}
//	}
//}
