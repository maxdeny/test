package com.example.musicdialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TestMainActivity extends Activity implements OnClickListener{
	
	private TextView showDialog1;
	private TextView showDialog2;
	private TextView showDialog3;
	private Context mContext = this;
	private static final String TAG = "TestMainActivity";
	//音乐文件地址
	private String musicPath1 = "";
	private String musicPath2 = "";
	private String musicPath3 = "";
	//private NewOneMusic music;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicPath1 = getSDPath() + "/" + "mother.m4a";//mother.m4a  test.png
        musicPath2 = getSDPath() + "/" + "test.png";
        musicPath3 = getSDPath() + "/" + "play.mp3";
        initView();  
    }
    
    
	private void initView() {
		// TODO Auto-generated method stub
		showDialog1 = (TextView) findViewById(R.id.show1); //正常
		showDialog2 = (TextView) findViewById(R.id.show2); //非音频格式
		showDialog3 = (TextView) findViewById(R.id.show3); //文件不存在
		showDialog1.setOnClickListener(this);
		showDialog2.setOnClickListener(this);
		showDialog3.setOnClickListener(this);
	}
	
    
    private void showMusicDialog(String musicPath) {
		// TODO Auto-generated method stub
    	NewTwoMusic music = new NewTwoMusic(mContext, musicPath);
		int IsShow = music.showMyDialog();
		if(IsShow == 0){
			Toast.makeText(mContext, "播放文件不存在", Toast.LENGTH_SHORT).show();
		}
		else if(IsShow == 1){
			Toast.makeText(mContext, "其他错误(包括非音频格式)", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.show1:
			showMusicDialog(musicPath1);
			break;
		case R.id.show2:
			showMusicDialog(musicPath2);
			break;
		case R.id.show3:
			showMusicDialog(musicPath3);
			break;
		}
	}

	public String getSDPath(){ 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState()   
                            .equals(android.os.Environment.MEDIA_MOUNTED);
        if(sdCardExist){                               
          sdDir = Environment.getExternalStorageDirectory();
        }   
        return sdDir.toString();      
    }
}
