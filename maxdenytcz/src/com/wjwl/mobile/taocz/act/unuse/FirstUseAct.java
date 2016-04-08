package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ViewPagerAdapter;
//
//public class FirstUseAct extends Activity {
//	private ViewPager viewPager;
//	private ArrayList<View> pageViews;
//	private ViewGroup main, group;
//	private SharedPreferences sp;
//	private ImageView[] imageViews;
//	private ImageView imageView;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		sp = getSharedPreferences("onelogin", 0);
//		String one = sp.getString("key", "");
//		if ("1".equals(one)) {
//			setContentView(R.layout.loading);
//			Intent intent = new Intent(FirstUseAct.this, LoadingAct.class);
//			startActivity(intent);
//			FirstUseAct.this.finish();
//		} else {
//			sp.edit().putString("key", "1").commit();
//			LayoutInflater inflater = getLayoutInflater();
//			View v = inflater.inflate(R.layout.bglayout4, null);
//			Button b = (Button) v.findViewById(R.id.bt_next);
//			b.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					Intent intent = new Intent(FirstUseAct.this,
//							LoadingAct.class);
//					startActivity(intent);
//					FirstUseAct.this.finish();
//				}
//			});
//			pageViews = new ArrayList<View>();
//			pageViews.add(inflater.inflate(R.layout.bglayout1, null));
//			pageViews.add(inflater.inflate(R.layout.bglayout2, null));
//			pageViews.add(inflater.inflate(R.layout.bglayout3, null));
//			pageViews.add(v);
//			imageViews = new ImageView[pageViews.size()];
//			main = (ViewGroup) inflater.inflate(R.layout.mains, null);
//			group = (ViewGroup) main.findViewById(R.id.viewGroup);
//			viewPager = (ViewPager) main.findViewById(R.id.guidePages);
//			setContentView(main);
//			for (int i = 0; i < pageViews.size(); i++) {
//				imageView = new ImageView(FirstUseAct.this);
//				LayoutParams abc = new LayoutParams(20, 20);
//				LinearLayout.LayoutParams abcd = new LinearLayout.LayoutParams(
//						10, 10);
//				abcd.setMargins(10, 0, 10, 0);
//				imageView.setLayoutParams(abcd);
//				imageView.setPadding(20, 0, 20, 0);
//				imageViews[i] = imageView;
//				if (i == 0) {
//					imageViews[i]
//							.setBackgroundResource(R.drawable.index_cur_ped);
//				} else {
//					imageViews[i]
//							.setBackgroundResource(R.drawable.index_cur_nor);
//				}
//				group.addView(imageViews[i]);
//			}
//			viewPager.setAdapter(new ViewPagerAdapter(FirstUseAct.this,
//					pageViews));
//			viewPager.setOnPageChangeListener(new GuidePageChangeListener());
//		}
//	}
//
//	class GuidePageChangeListener implements OnPageChangeListener {
//
//		@Override
//		public void onPageScrollStateChanged(int arg0) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onPageSelected(int arg0) {
//			for (int i = 0; i < imageViews.length; i++) {
//				imageViews[arg0]
//						.setBackgroundResource(R.drawable.index_cur_ped);
//				if (arg0 != i) {
//					imageViews[i]
//							.setBackgroundResource(R.drawable.index_cur_nor);
//				}
//			}
//		}
//
//	}
//
//}
