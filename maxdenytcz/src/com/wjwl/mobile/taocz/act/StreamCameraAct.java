package com.wjwl.mobile.taocz.act;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class StreamCameraAct extends MActivity {

	private ImageView cameradata;;
	private String picpathsave;
	private String picpathcrop;
	private ImageView submit, cancel;
	BitmapFactory.Options opts;
	private int size = 480;
	private TextView title;
	private Button button, back;
	private String str_id, tempcontents;

	public void savePhoto(Bitmap bitmap) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(picpathcrop);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delphoto(String path) {
		File f = new File(path);
		f.delete();
	}

	public int getSize() {
		if (getWindowManager().getDefaultDisplay().getWidth() > getWindowManager()
				.getDefaultDisplay().getHeight()) {
			size = getWindowManager().getDefaultDisplay().getHeight();
		} else {
			size = getWindowManager().getDefaultDisplay().getWidth();
		}
		return size;
	}

	public class OnClick implements OnClickListener {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.stream_camera.submit:
				Frame.HANDLES.get("CiytRecruitAct").get(0).sent(2,picpathcrop);
				finish();
				break;
			case R.stream_camera.cancel:
				File f = new File(picpathcrop);
				f.delete();
				finish();
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			this.finish();
			return;
		}
		switch (requestCode) {
		case 1:
			// Bitmap bit = data.getParcelableExtra("data");
			startPhotoZoom(null, data.getData());
			break;
		case 2:
			//Bitmap photoCrop = BitmapFactory.decodeFile(picpathsave, opts);
			//startPhotoZoom(photoCrop, null);
			startPhotoZoom(null, Uri.fromFile(new File(picpathsave)));
			break;
		case 3:
			if (data != null) {
				setPicToView(data);
				Bitmap bitmap = data.getParcelableExtra("data");
				savePhoto(bitmap); // save the croped image
				delphoto(picpathsave); // delete the source image
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void ShowPick(String type) {
		if (type.equals("1")) {
			Intent pick = new Intent(Intent.ACTION_PICK, null);
			pick.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(pick, 1);//选择相框
		}
		if (type.equals("2")) {
			Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			capture.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(picpathsave)));
			startActivityForResult(capture, 2);//拍照
		}
		if (type.equals("3")) {
			startPhotoZoomError();
		}
	}

	public void startPhotoZoom(Bitmap data, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		if (data == null && uri != null) {
			intent.setDataAndType(uri, "image/*");
		} else if (data != null && uri == null) {
			intent.setType("image/*");
			intent.putExtra("data", data);
		}
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 350);
		intent.putExtra("outputY", 350);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		try {
			startActivityForResult(intent, 3);
		} catch (Exception e) {
			Intent i = new Intent(StreamCameraAct.this, StreamCameraAct.class);
			i.putExtra("type", "3");
			i.putExtra("select_type", "1");
			i.putExtra("lastimgurl", "1");
			i.putExtra("CiytRecruitid", F.CiytRecruitid);
			
			startActivity(i);
			finish();
		}
	}

	public void startPhotoZoomError() {
		Intent intent = new Intent("com.android.camera.action.CROP");
		Uri url = Uri.fromFile(new File(picpathsave));
		intent.setDataAndType(url, "image/*");
		intent.putExtra("crop", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 350);
		intent.putExtra("outputY", 350);
		intent.putExtra("return-data", true);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, 3);
	}

	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			cameradata.setBackgroundDrawable(drawable);
		}
	}

	@Override
	protected void create(Bundle savedInstanceState) {
		setContentView(R.layout.streamcamera);
		cameradata = (ImageView) findViewById(R.stream_camera.cameradata);
		back = (Button) findViewById(R.header.back);
		button = (Button) findViewById(R.header.button);
		title = (TextView) findViewById(R.header.title);
		title.setText("选择确认");
		button.setVisibility(8);
		back.setVisibility(8);
		submit = (ImageView) findViewById(R.stream_camera.submit);
		cancel = (ImageView) findViewById(R.stream_camera.cancel);
		str_id = getIntent().getStringExtra("CiytRecruitid");
		if (getSDPath().equals("") || getSDPath() == null) {
			Toast.makeText(getApplication(), "你的SD卡不存在", 1).show();
			finish();
			return;
		}
		tempcontents = getSDPath() + "/images/" + str_id + "/";
		String temptime = "" + System.currentTimeMillis();
		
		if (getIntent().getStringExtra("select_type").equals("1")) {
			if(null==getIntent().getStringExtra("lastimgurl")){
				picpathsave = tempcontents + "tempsave.jpg";
				picpathcrop = tempcontents + temptime + "_tempcrop.jpg";
				F.lastimgurl=temptime;
			}else{
				picpathsave = tempcontents + "tempsave.jpg";
				picpathcrop = tempcontents + F.lastimgurl + "_tempcrop.jpg";
			}
			
		} 
//		else if (getIntent().getStringExtra("select_type").equals("2")) {
//			picpathsave = tempcontents + "tempsave.jpg";
//			picpathcrop = tempcontents + temptime + "_bussinesslicence.jpg";
//		} else if (getIntent().getStringExtra("select_type").equals("3")) {
//			picpathsave = tempcontents + "tempsave.jpg";
//			picpathcrop = tempcontents + temptime + "_healthicence.jpg";
//		}

		submit.setOnClickListener(new OnClick());
		cancel.setOnClickListener(new OnClick());
		opts = new BitmapFactory.Options();
		opts.inSampleSize = 10;
		getSize();
		File file = new File(tempcontents);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (getIntent().getStringExtra("type").equals("1")) {
			ShowPick("1");
		} 
		else if (getIntent().getStringExtra("type").equals("2")) {
			ShowPick("2");
		} else if (getIntent().getStringExtra("type").equals("3")) {
			ShowPick("3");
		}
	}

	public String getSDPath() {
		File sdDir = null;
		String sdpath="";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			sdpath=sdDir.toString();
		}
		else{
			sdpath="";
		}
		return sdpath;

	}

}