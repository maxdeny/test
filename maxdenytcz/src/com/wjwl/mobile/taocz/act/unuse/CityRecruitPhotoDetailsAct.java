package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.io.File;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.dialog.PhotoDeleteDialog;
//
//public class CityRecruitPhotoDetailsAct extends MActivity {
//	Button bt_del, bt_back;
//	ImageView img;
//	String photo_url;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.cityrecruitphotodetails);
//		img = (ImageView) findViewById(R.cityrecruitphotodetails.img);
//		bt_del = (Button) findViewById(R.cityrecruitphotodetails.delete);
//		bt_back = (Button) findViewById(R.cityrecruitphotodetails.back);
//		photo_url = getIntent().getStringExtra("photo_url");
//		Bitmap bm = BitmapFactory.decodeFile(photo_url);
//		img.setImageBitmap(bm);
//		bt_del.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				PhotoDeleteDialog dialog = new PhotoDeleteDialog(
//						CityRecruitPhotoDetailsAct.this, photo_url);
//				dialog.show();
//				dialog.submit.setOnClickListener(new View.OnClickListener() {
//					public void onClick(View v) {
//						if (photo_url == null || photo_url.equals(""))
//							return;
//						File f = new File(photo_url);
//						f.delete();
//						Intent i = new Intent();
//						i.setClass(CityRecruitPhotoDetailsAct.this,
//								WMShowPhoto.class);
//						CityRecruitPhotoDetailsAct.this.setResult(RESULT_OK, i);
//						finish();
//					}
//				});
//			}
//
//		});
//		bt_back.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				CityRecruitPhotoDetailsAct.this.finish();
//			}
//
//		});
//	}
//
//}
