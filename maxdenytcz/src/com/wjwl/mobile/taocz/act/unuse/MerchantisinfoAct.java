package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
//import com.wjwl.mobile.taocz.R;
//
//public class MerchantisinfoAct extends MActivity {
//	RelativeLayout Raddress,Rphone,Rperipheral;
//	ImageView merchants_img;
//	TextView merchants_name, merchants_consume, merchants_intr,
//			merchants_address, merchants_phone, merchants_traffic,
//			merchants_landmark;
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.merchantsinfo);
//        dataLoad(null);
//	}
//
//	private void init() {
//		
//		merchants_img=(ImageView)this.findViewById(R.merchantsinfo.merchantsinfo_img);
//		merchants_name=(TextView)this.findViewById(R.merchantsinfo.merchantsinfo_name);
//		merchants_consume=(TextView)this.findViewById(R.merchantsinfo.merchantsinfo_consume);
//		merchants_intr=(TextView)this.findViewById(R.merchantsinfo.merchantsinfo_introduce);
//		merchants_address=(TextView)this.findViewById(R.merchantsinfo.merchantsinfo_address);
//		merchants_phone=(TextView)this.findViewById(R.merchantsinfo.merchantsinfo_phone);
//		merchants_traffic=(TextView)this.findViewById(R.merchantsinfo.traffic);
//		merchants_landmark=(TextView)this.findViewById(R.merchantsinfo.landmark);
//		Raddress=(RelativeLayout)this.findViewById(R.merchantsinfo.clic_layout2);
//		Rphone=(RelativeLayout)this.findViewById(R.merchantsinfo.clic_layout3);
//		Rperipheral=(RelativeLayout)this.findViewById(R.merchantsinfo.clic_layout6);
//		merchants_img.setImageResource(R.drawable.myinfo_person);
//		merchants_name.setText(list.getCbusinessinfoList().get(0).getBusinessname());
//		merchants_consume.setText(list.getCbusinessinfoList().get(0).getEverycost());
//		merchants_intr.setText(list.getCbusinessinfoList().get(0).getEnvironment());
//		merchants_address.setText(list.getCbusinessinfoList().get(0).getBusinessaddress());
//		merchants_phone.setText(list.getCbusinessinfoList().get(0).getBusinessphone());
//		merchants_traffic.setText(list.getCbusinessinfoList().get(0).getBusinessbusway());
//		merchants_landmark.setText(list.getCbusinessinfoList().get(0).getRemark());
//		Raddress.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(MerchantisinfoAct.this,
//						BusinessGuidance.class);
//				intent.putExtra("id", getIntent().getStringExtra("id"));
//				startActivity(intent);
//			}
//		});
//		Rphone.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
//						+ merchants_phone.getText()));
//				startActivity(intent);
//			}
//		});
//		Rperipheral.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				MerchantisinfoAct.this.finish();
//			}
//		});
//	}
//	
//	static Msg_CbusinessinfoList.Builder list;
//	@Override
//	public void dataLoad(int[] types) {
//		// TODO Auto-generated method stub
//		this.loadData(new Updateone[] { new Updateone("RBUSINESSLIST",
//				new String[][] { { "businessidlist", getIntent().getStringExtra("id")} },
//				Msg_CbusinessinfoList.newBuilder()), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		// TODO Auto-generated method stub
//		if (son != null && son.metod == "RBUSINESSLIST") {
//			list = (Msg_CbusinessinfoList.Builder) son.build;
//			init();
//		}
//	}
//}
