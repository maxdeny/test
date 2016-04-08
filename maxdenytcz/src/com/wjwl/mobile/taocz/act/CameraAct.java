package com.wjwl.mobile.taocz.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.mdx.mobile.Frame;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.cam.CameraManager;
import com.wjwl.mobile.taocz.cam.ZxingFdecode;



public class CameraAct extends Activity implements SurfaceHolder.Callback {
	private SurfaceView mSurfaceView = null;
	private int waitingforcheck = 0;
	private SurfaceHolder mSurfaceHolder = null;
	private TextView textv;
	private View scanview;
	private Animation shake;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			scanview.startAnimation(shake);
			switch (msg.what) {
			case 1:
				if (waitingforcheck < 2) {
					CameraManager.get().requestPreviewFrame(handler, 2);
				}
				break;
			case 2:
				waitingforcheck++;
				(new decodethread((byte[]) msg.obj, msg.arg1, msg.arg2))
						.start();
				break;
			case 3:
				waitingforcheck--;
				if (msg.arg2 == 0) {
					Result result = (Result) msg.obj;
					textv.setText("条码读取成功");
					CameraManager.get().closeDriver();
					finish();
					if(result.getText().startsWith("http://") || result.getText().startsWith("https://") ){
						Intent intent = new Intent(Intent.ACTION_VIEW);
						Uri content_url = Uri.parse(result.getText());
						intent.setData(content_url);
						CameraAct.this.startActivity(intent);
					}else{
						Frame.HANDLES.sentAll("KuSaoAct", 0, result);
//						Intent in=new Intent(CameraAct.this,V3_SaoMiaoAct.class);
//						Bundle b=new Bundle();
//						b.p
//						in.putExtra("result", result);
//						startActivity(in);
					}
				} else {
					textv.setText("请确保要读取的条码完整清晰");
					CameraManager.get().requestAutoFocus(handler, 1);
				}
				break;
			}
		}
	};

	private class decodethread extends Thread {
		public byte[] data;
		public int width, height;

		public decodethread(byte[] data, int width, int height) {
			this.data = data;
			this.width = width;
			this.height = height;
		}

		public void run() {
			try {
				Result resul = ZxingFdecode.decode(data, width, height);
				handler.sendMessage(handler.obtainMessage(3, 0, 0, resul));
			} catch (NotFoundException e) {
				handler.sendMessage(handler.obtainMessage(3, 0, 1));
			}
		}
	}

	@Override
	public void finish(){
		CameraManager.get().closeDriver();
		super.finish();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.camerashow);
		mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		textv = (TextView) findViewById(R.id.textView1);
		scanview=findViewById(R.id.scanview);
		shake = AnimationUtils.loadAnimation(this, R.anim.scan);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		CameraManager.get().startPreview();
		CameraManager.get().requestAutoFocus(handler, 1);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		initCamera(holder);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		CameraManager.get().requestAutoFocus(handler, 1);
		return super.onTouchEvent(event);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("ScanningPage");
		CameraManager.init(getApplication());
		MobclickAgent.onResume(CameraAct.this);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("ScanningPage"); 
		MobclickAgent.onPause(CameraAct.this);
	}

	@Override
	protected void onStop() {
		CameraManager.get().closeDriver();
		super.onStop();
	}
}
