package com.example.goldfoxchina.main;

import java.util.ArrayList;

import com.example.goldfoxchina.Adapter.PhotoGalleryAdapter;
import com.example.goldfoxchina.Bean.AdvertisementBean;
import com.example.goldfoxchina.Bean.DetialGallery;
import com.example.goldfoxMall.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;

/**
 * 图片展示
 * 
 * @author kysl
 * 
 */

public class PhotoGallery_Alert_Activity extends Activity {

	// 关闭
	private TextView photogallery_close;

	DetialGallery gallery;
	//
	float x1, y1, x2, y2;
	// 选中图片的id
	int pos = -1;
	int img_id = 0;
	private Dialog dialog;
	
	private ArrayList<Bitmap> arraylist;
	private PhotoGalleryAdapter galleryAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.layout_photogallery);

		Intent intent = getIntent();
		pos = Integer.valueOf(intent.getExtras().get("position").toString()
				.trim());
		img_id=pos+1;
		arraylist=AdvertisementBean.getAdver().getBitmaplist();
		
		gallery = (DetialGallery) findViewById(R.id.photogallery_gallery);
		galleryAdapter=new PhotoGalleryAdapter(PhotoGallery_Alert_Activity.this, arraylist);
		galleryAdapter.setId(img_id);
		gallery.setAdapter(galleryAdapter);

		gallery.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x1 = event.getX();
					y1 = event.getY();

				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					x2 = event.getX();
					y2 = event.getY();

					float x = x2 - x1;

					if (x > 0) { // 左滑

						img_id = img_id - 1;
						if (!(img_id < 0)) {

//							new GalleryAsync().execute();
//							Toast.makeText(PhotoGallery_Alert_Activity.this,
//									"x的值为:" + x + "^^^" + img_id,
//									Toast.LENGTH_SHORT).show();
						} else {
							img_id = 0;
						}

					} else if (x < 0) { // 右滑

						img_id = img_id + 1;
						if (!(img_id >= arraylist.size())) {

//							new GalleryAsync().execute();
//							Toast.makeText(
//									PhotoGallery_Alert_Activity.this,
//									"x的值为:" + x + "^^^" + img_id + "%%%"
//											+ arraylist.size() + "pos:" + pos,
//									Toast.LENGTH_SHORT).show();
						} else {
							img_id = arraylist.size() - 1;
						}

					}
					galleryAdapter.setId(img_id);
					galleryAdapter.notifyDataSetChanged();

				}

				return false;
			}
		});

		/**
		 * 关闭
		 */
		photogallery_close = (TextView) findViewById(R.id.photogallery_close);
		photogallery_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PhotoGallery_Alert_Activity.this.onDestroy();
				PhotoGallery_Alert_Activity.this.finish();

			}
		});

	}
}
