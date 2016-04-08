package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.server.Son;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.R.bookingcoupon;
//import com.wjwl.mobile.taocz.widget.DragChangeView;
//
//public class CouponContentAct extends MActivity {
//	TextView title, info,servicedetails;
//	String itemid;
//	Button bt_yuding;
//	private DragChangeView mDragChangeView;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.conpon_content);
//		itemid = getIntent().getStringExtra("itemid");
//		title = (TextView) findViewById(R.conpon_content.title);
//		info = (TextView) findViewById(R.conpon_content.info);
//		bt_yuding = (Button) findViewById(R.conpon_content.bt_yuding);
//		servicedetails =(TextView)findViewById(R.conpon_content.servicedetails);
//		title.setText("[150店通用]澳门豆捞");
//		info.setText("仅售78元，市场价100元澳门豆捞100元现金抵用券抵用1次。。。。。。");
//		servicedetails.setText("凭拉手票享受豆捞坊100元现金抵用一次");
//		mDragChangeView = (DragChangeView) findViewById(R.conpon_content.DragChangeView);
//		mDragChangeView.setAutoMove(true);
//		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
//		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setHideRadio(false);
//		mDragChangeView.setAutoMove(false);
//		mDragChangeView.setRadius(7);
//		bt_yuding.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent();
//				i.putExtra("itemid", itemid);
//				i.setClass(getApplication(),BookingCouponAct.class);
//				startActivity(i);
//			}
//		});
//
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//
//	}
//}
