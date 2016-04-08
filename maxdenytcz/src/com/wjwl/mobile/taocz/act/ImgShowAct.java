//package com.wjwl.mobile.taocz.act;
//
//
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.images.touch.BitmapView;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//
//
//public class ImgShowAct extends Activity {
//	private BitmapView bitmapview;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.wm_imageshow);
//		String imgurl=getIntent().getStringExtra("imgpath");
//		bitmapview=new BitmapView(this,imgurl);
//		this.setContentView(bitmapview);
//	}
//
//
//	
//	@Override
//	public void onDestroy() {
//		bitmapview.destroy();
//		super.onDestroy();
//	}
//}
