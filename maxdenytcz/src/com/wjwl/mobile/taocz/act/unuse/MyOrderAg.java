package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.RadioGroup;
//
//import com.mdx.mobile.activity.MActivityGroup;
//import com.mdx.mobile.widget.AMLayout;
//import com.mdx.mobile.widget.FillLine;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.CurrView4Fav;
//
//public class MyOrderAg extends MActivityGroup{
//	private AMLayout layout;
//	private RadioGroup mFavoriteGroup;
//	private CurrView4Fav cv;
//	
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.myorder);
//		setId("MyOrderAg");
//		mFavoriteGroup = (RadioGroup) findViewById(R.id.radioGroup);
//		cv=(CurrView4Fav) findViewById(R.id.favorite_cv);
//		layout = (AMLayout) findViewById(R.id.favorite_content);
//		layout.setCurrentView(cv);
//		this.setContentLayout(layout);
//		
////		{
////			Intent intent = new Intent(this, MyOrderUnfinishAct.class)
////					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////			this.addChild(R.id.radio_shop, "shop", intent);
////		}
////		{
////			Intent intent = new Intent(this, MyOrderFinishAct.class)
////					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
////			this.addChild(R.id.radio_life, "life", intent);
////		}
//
//		{
//			Intent intent = new Intent(this, MyOrderCancelAct.class)
//					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			this.addChild(R.id.radio_takeout, "takeout", intent);
//		}
//		
//		
//		int nowCheckedRadioId = mFavoriteGroup.getCheckedRadioButtonId();
//		if (nowCheckedRadioId != -1) {
//			this.setCurrent(nowCheckedRadioId);
//		}
//		mFavoriteGroup.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
//			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				setCurrent(checkedId);
//			}
//		});
//		
//	}
//	
//	public void disposeMsg(int type, Object obj) {
//		if(type==1){
//			mFavoriteGroup.check((Integer)obj);
//		}
//	}
//
//}
