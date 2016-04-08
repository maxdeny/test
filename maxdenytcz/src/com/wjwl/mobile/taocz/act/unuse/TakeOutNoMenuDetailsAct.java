package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CBill.Msg_CBill;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_BillCategory;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ImageListAdapter;
//import com.wjwl.mobile.taocz.widget.DragChangeView;
//
//public class TakeOutNoMenuDetailsAct extends MActivity {
//	Button bt_phone;
//	private DragChangeView mDragChangeView;
//	String phone = "";
//	TextView  headtitle,address,stime,sarea,spay,songcanshuoming;
//	private String businessname;
//	String businessid;
//    Button bt_back;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutnomenudetails);
//		businessid = getIntent().getStringExtra("businessid");
//		headtitle = (TextView) findViewById(R.takeoutnomenudetails.headtitle);
//		bt_phone = (Button) findViewById(R.takeoutnomenudetails.bt_phone);
//		bt_back=(Button)findViewById(R.takeoutnomenudetails.back);
//		address = (TextView) findViewById(R.takeoutnomenudetails.address);
//		stime = (TextView) findViewById(R.takeoutnomenudetails.stime);
//		sarea = (TextView) findViewById(R.takeoutnomenudetails.sarea);
//		spay = (TextView) findViewById(R.takeoutnomenudetails.sdabaopay);
//		songcanshuoming = (TextView) findViewById(R.takeoutnomenudetails.songcanshuoming);
//		mDragChangeView = (DragChangeView) findViewById(R.takeoutnomenudetails.DragChangeView);
//		mDragChangeView.setAutoMove(true);
//		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
//		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setHideRadio(false);
//		mDragChangeView.setRadius(7);
//		bt_phone.setText("联系电话：" + phone);
//		bt_back.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				TakeOutNoMenuDetailsAct.this.finish();
//			}
//		});
//		bt_phone.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Uri uri = Uri.parse("tel:" + phone);
//				Intent intent = new Intent(Intent.ACTION_DIAL, uri);
//				startActivity(intent);
//			}
//		});
//		dataLoad(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("wmbill")) {
//			Msg_CBill.Builder cbill = (Msg_CBill.Builder) son.build;
//			if (!cbill.getCbusinessinfo().getBusinessname().equals(""))
//				headtitle.setText(cbill.getCbusinessinfo().getBusinessname());
//			phone = cbill.getCbusinessinfo().getWmOrdertel();
//			address.setText (cbill.getCbusinessinfo().getBusinessaddress());
//			stime.setText(cbill.getCbusinessinfo().getWmOpentime());
//			sarea.setText("周围"+cbill.getCbusinessinfo().getWmSendrange()+"公里");
//			spay.setText("￥"+cbill.getCbusinessinfo().getWmLogisticsmoney());
//			songcanshuoming.setText(cbill.getCbusinessinfo().getRemark());
//			bt_phone.setText("联系电话：" + phone);
//			List<Msg_BillCategory> item = cbill.getBillCategoryList();
//			ImageListAdapter iaad = new ImageListAdapter(this, item.get(0)
//					.getBillitemList());
//			mDragChangeView.setAdapter(iaad);
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMBILL",
//				new String[][] { { "businessid", businessid } }), });
//
//	}
//
//}
