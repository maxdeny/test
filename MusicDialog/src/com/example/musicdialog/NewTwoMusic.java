package com.example.musicdialog;

import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;
import java.text.SimpleDateFormat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class NewTwoMusic implements OnClickListener{

	private TextView tVCurrentTime;
	private TextView tVDurationTime;
	private TextView tVPlay;
	private TextView tVStop;
	private TextView tVBack;
	private TextView tVSpeed;
	private SeekBar seekBar;
	private MyDialog alertDialog;
	private Context mContext;
	private static final String TAG = "NewOneMusic";
	private LayoutInflater layoutInflater;
	// 更新UI线程
	private MusicThread musicThread;

	// 音乐文件地址
	private String musicPath = "";

	// 当前歌曲进度
	private int currentPosition = 0;

	// 歌曲总时间
	private int allTime = 0;

	// seekbar最大值
	private int maxValue = 0;

	// 更新UI间隔
	private final int milliSeconds = 500;

	// 快进、快退时间
	private static final int MOVETIME = 30000;

	// 播放器
	private MediaPlayer mPlayer;
	
	//正在播放
	private boolean IsPlaying = true;

	public NewTwoMusic(Context context, String path) {
		mContext = context;
		musicPath = path;
		layoutInflater = LayoutInflater.from(mContext);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if (musicThread.isRun == 0) {
					currentPosition = mPlayer.getCurrentPosition();
					seekBar.setProgress(currentPosition * maxValue / allTime);
					tVCurrentTime.setText(getFormatTime(currentPosition));
					tVDurationTime.setText(getFormatTime(allTime));
				}
				break;
			default:
				break;
			}
		}
	};
	
	/**
	 * 显示对话框及初始化播放器
	 * @return 文件存在IsShow  0-文件不存在   1-其它错误   2-音频格式   
	 */
	public int showMyDialog() {
		// TODO Auto-generated method stub
		int IsShow = 2;
		File musicFile = new File(musicPath);
		if(!musicFile.exists()){
			IsShow = 0;
			return IsShow;
		}
		View dialogView = layoutInflater.inflate(R.layout.mydialog, null);
		findView(dialogView);
		boolean initSucess = initMedia();
		if(!initSucess){
			IsShow = 1;
			return IsShow;
		}
		musicThread = new MusicThread();
		musicThread.start();
		alertDialog = new MyDialog(mContext, 280, 180, dialogView,
				R.style.mydialog);
		alertDialog.show();
		alertDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				musicThread.isRun = 2;
				if (mPlayer != null) {
					if (mPlayer.isPlaying()) {
						mPlayer.stop();
						Log.v(TAG, "dismiss正在播放--停止");
					}
					Log.v(TAG, "dismiss播放器不为null未播放--释放");
					mPlayer.release();
					mPlayer = null;
				}
			}
		});
		return IsShow;
	}
	
	/**
	 * 初始化播放器
	 */
	private boolean initMedia() {
		boolean initSucess = true;
		// TODO Auto-generated method stub
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(musicPath);
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.prepare();
			mPlayer.setOnCompletionListener(new MediaCompleteListhener());
			mPlayer.setOnErrorListener(new MediaErrorListhener());
			mPlayer.setOnPreparedListener(new MediaPreparedListhener());

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			initSucess = false;
			e.printStackTrace();
			Log.v(TAG, "IllegalArgumentException:" + e.getMessage());
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			initSucess = false;
			e.printStackTrace();
			Log.v(TAG, "SecurityException:" + e.getMessage());
		} catch (IllegalStateException e) {
			initSucess = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v(TAG, "IllegalStateException:" + e.getMessage());
		} catch (IOException e) {
			initSucess = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v(TAG, "IOException:" + e.getMessage());
			
		}
		return initSucess;
	}
	
	private void findView(View dialogView) {
		// TODO Auto-generated method stub
		tVCurrentTime = (TextView) dialogView.findViewById(R.id.tv_currenttime);
		tVDurationTime = (TextView) dialogView.findViewById(R.id.tv_duration);
		tVPlay = (TextView) dialogView.findViewById(R.id.tv_play);
		tVStop = (TextView) dialogView.findViewById(R.id.tv_stop);
		tVBack = (TextView) dialogView.findViewById(R.id.tv_back);
		tVSpeed = (TextView) dialogView.findViewById(R.id.tv_speed);
		seekBar = (SeekBar) dialogView.findViewById(R.id.seekbar);
		tVPlay.setOnClickListener(this);
		tVStop.setOnClickListener(this);
		tVBack.setOnClickListener(this);
		tVSpeed.setOnClickListener(this);
		tVStop.setClickable(false);
		tVBack.setClickable(false);
		tVSpeed.setClickable(false);
		maxValue = seekBar.getMax();
		seekBar.setOnSeekBarChangeListener(new MySeekBarListener());
	}

	/**
	 * 播放
	 */
	private void play() {
		if (mPlayer != null && !mPlayer.isPlaying()) {
			musicThread.isRun = 0;
			Log.v(TAG, "thread status" + musicThread.getState());
			//第二种方法 打断线程睡眠
//			if(musicThread.getState().equals(State.TIMED_WAITING)){
//				musicThread.interrupt();
//			}
			mPlayer.start();
			tVPlay.setBackgroundResource(R.drawable.play_no);
			tVStop.setBackgroundResource(R.drawable.stop_yes);
			tVBack.setBackgroundResource(R.drawable.back_yes);
			tVSpeed.setBackgroundResource(R.drawable.speed_yes);
			tVStop.setClickable(true);
			tVBack.setClickable(true);
			tVSpeed.setClickable(true);
		}
	}
	
	/**
	 * 暂停
	 */
	private void pause() {
		if (mPlayer != null && mPlayer.isPlaying()) {
			musicThread.isRun = 1;
			mPlayer.pause();
			tVPlay.setBackgroundResource(R.drawable.play_yes);
			tVBack.setBackgroundResource(R.drawable.back_no);
			tVSpeed.setBackgroundResource(R.drawable.speed_no);
			tVBack.setClickable(false);
			tVSpeed.setClickable(false);

		}
	}
	
	/**
	 * 停止
	 */
	private void stop(){
		alertDialog.dismiss();
	}
	
	/**
	 * 后退30000ms
	 */
	private void back() {
		if (MOVETIME < currentPosition) {
			int backNumTime = currentPosition - MOVETIME;
			seekBar.setProgress(backNumTime * maxValue / allTime);
			mPlayer.seekTo(allTime * seekBar.getProgress() / maxValue);
		} else {
			seekBar.setProgress(0);
			mPlayer.seekTo(0);
		}
	}
	
	/**
	 * 前进30000ms
	 */
	private void speed() {
		int speedNumTime = currentPosition + MOVETIME;
		if (speedNumTime < allTime) {
			seekBar.setProgress(speedNumTime * maxValue / allTime);
			mPlayer.seekTo(allTime * seekBar.getProgress() / maxValue);
		} else {
			seekBar.setProgress(maxValue);
			mPlayer.seekTo(allTime);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tv_play:
			Log.v(TAG, "playclick");
			if(IsPlaying){
				pause();
				IsPlaying = false;
			}else{
				play();
				IsPlaying = true;
			}
			break;
		case R.id.tv_stop:
			stop();
			break;
		case R.id.tv_back:
			back();
			break;
		case R.id.tv_speed:
			speed();
			break;
		}
	}

	/**
	 * 进度条监听类
	 * @author Administrator
	 *
	 */
	private final class MySeekBarListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
			int dest = seekBar.getProgress();
			int touchPosition = allTime*dest/maxValue;
			mPlayer.seekTo(touchPosition);
			tVCurrentTime.setText(getFormatTime(touchPosition));
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			pause();
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			play();
		}
		
	}
	
	/**
	 * 更新UI线程类
	 * @author Administrator
	 *
	 */
	public class MusicThread extends Thread {

		public volatile int isRun = 0;
		// 0--发送消息   1--等待     2--结束线程
		@Override
		public void run() {
			// TODO Auto-generated method stub
			sendMessage();
		}

		private void sendMessage() {
			// TODO Auto-generated method stub
			while (isRun == 0) {
				try {
					sleep(milliSeconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mHandler.sendEmptyMessage(0);
			}
			//第一种方法 
			if(isRun == 2){
				end();
			}
			else if(isRun == 1){
				Log.v(TAG, " wait for notifyMe");
				notifyMe();
			}
			//第二种方法：通过睡眠很长时间等待打断  需要配合isRun参数使用  当暂停和播放结束时设置Boolean isRun = false;
//			try {
//				Log.v(TAG, "thread sleep long time");
//				sleep(1000000000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				sendMessage();
//			}
		}

		private void end() {
			// TODO Auto-generated method stub
			Log.v(TAG, " Thrend end");
		}

		private void notifyMe() {
			// TODO Auto-generated method stub
			while(isRun == 1){
				try {
					sleep(milliSeconds);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isRun == 0){
				Log.v(TAG, "I Notify you");
				sendMessage();
			}
			else if(isRun == 2){
				end();
			}
			
			
		}
		
		
	}

	/**
	 * 播放结束监听类
	 * @author Administrator
	 *
	 */
	public class MediaCompleteListhener implements
			MediaPlayer.OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer arg0) {
			// TODO Auto-generated method stub
			try {
				musicThread.isRun = 1;
				IsPlaying = false;
				Toast.makeText(mContext, "播放结束", Toast.LENGTH_SHORT).show();
				tVPlay.setBackgroundResource(R.drawable.play_yes);
				tVStop.setBackgroundResource(R.drawable.stop_no);
				tVBack.setBackgroundResource(R.drawable.back_no);
				tVSpeed.setBackgroundResource(R.drawable.speed_no);
				tVStop.setClickable(false);
				tVPlay.setClickable(true);
				tVBack.setClickable(false);
				tVSpeed.setClickable(false);
			} catch (Exception e) {
				e.printStackTrace();
				Log.v(TAG, e.getMessage());
			}
		}

	}
	
	/**
	 * 当准备结束播放音乐
	 * @author Administrator
	 *
	 */
	public class MediaPreparedListhener implements MediaPlayer.OnPreparedListener{

		@Override
		public void onPrepared(MediaPlayer mp) {
			// TODO Auto-generated method stub
			play();
			allTime = mp.getDuration();
			Log.v(TAG, "prepare:alltime" + String.valueOf(allTime));
		}
		
	}
	
	/**
	 * 播放出错监听类
	 * @author Administrator
	 *
	 */
	public class MediaErrorListhener implements MediaPlayer.OnErrorListener {

		@Override
		public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			try {
				if(arg2 == MediaPlayer.MEDIA_ERROR_UNSUPPORTED ){
					Log.v(TAG, "MEDIA_ERROR_UNSUPPORTED");
					Toast.makeText(mContext, "MEDIA_ERROR_UNSUPPORTED!", Toast.LENGTH_SHORT).show();
				}
				Toast.makeText(mContext, "播放发生异常!", Toast.LENGTH_SHORT).show();
				Log.v(TAG, "arg0:"+String.valueOf(arg0));
				Log.v(TAG, "arg1:"+String.valueOf(arg1));
				Log.v(TAG, "arg2:"+String.valueOf(arg2));
				alertDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
				Log.v(TAG, e.getMessage());
			}
			return false;
		}
	}

	/**
	 * 将Long时间转换为00:00时间格式
	 * @param milliSeconds
	 * @return
	 */
	private String getFormatTime(long milliSeconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");// 初始化Formatter的转换格式。
		String formatTime = formatter.format(milliSeconds);
		return formatTime;
	}

}
