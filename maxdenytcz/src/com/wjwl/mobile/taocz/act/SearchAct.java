package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ckeywords.Msg_Ckeywords;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.SearchHotTextAdapter;
import com.wjwl.mobile.taocz.widget.PopubLayout;

public class SearchAct extends MActivity implements OnClickListener,
RecognizerDialogListener {
	private PopubLayout popublayout;
	private boolean loading=false;
	private int domTable[][]=new int[][]{
			{3,3,3,2,4},
			{4,4,3,2,2},
			{3,3,3,3,3}
	};
	private  SensorManager sm;  
	private LinearLayout searchHotLayout;
	private EditText edit_search;
	private Button searchbutton;
	private String editval,keyWord,keyType,fromAct;
	private Button searchCancel,speakbutton;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog iatDialog;
	
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.search);
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		searchbutton=(Button) findViewById(R.search.searchbutton);
		speakbutton=(Button) findViewById(R.search.speakbutton);
		this.LoadShow=false;
		
		searchHotLayout=(LinearLayout) findViewById(R.search.initlayout);
		edit_search=(EditText) findViewById(R.search.edit_search);
		popublayout = (PopubLayout)findViewById(R.search.popublayout);
		
		searchCancel=(Button) findViewById(R.search.cancel);
		
		keyWord=getIntent().getStringExtra("keyWord");
		keyWord=keyWord==null?"":keyWord.replace(",", "").replace("。","");
		
		keyType=getIntent().getStringExtra("keyType");
		keyType=keyType==null?"":keyType;
		
		fromAct=getIntent().getStringExtra("fromAct");
		
		popublayout.setPopubArg(keyType);
		edit_search.setText(keyWord);
		mSharedPreferences = getSharedPreferences(getPackageName(),
				MODE_PRIVATE);
		
		speakbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showIatDialog();
			}
		});
		
	    searchbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				editval=edit_search.getText().toString().replace(",", "").replace("。","");
				keyWord=editval;
				keyType=popublayout.getPopubArg();
				F.searchTo(SearchAct.this,keyWord,keyType);
				SearchAct.this.finish();
			}
		});
	    edit_search.addTextChangedListener(tw);
	    searchCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edit_search.setText("");
				v.setVisibility(View.GONE);
			}
		});
		if(edit_search.getText().length()>0){
			searchCancel.setVisibility(View.VISIBLE);
		}else{
			searchCancel.setVisibility(View.GONE);
		}
		iatDialog = new RecognizerDialog(this, "appid=" +F.APPID);
		iatDialog.setListener(this);
		dataLoadByDelay(null,500);
	}
	
	private TextWatcher tw=new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			if(s.length()>0){
				searchCancel.setVisibility(View.VISIBLE);
			}else{
				searchCancel.setVisibility(View.GONE);
			}
		}
	};
	
	final SensorEventListener myAccelerometerListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				float x = sensorEvent.values[0];
				float y = sensorEvent.values[1];
				float z = sensorEvent.values[2];
				if(x*x+y*y+z*z>400){
					dataLoad(null);
					Vibrator mVibrator = ( Vibrator )getApplication().getSystemService( Service .VIBRATOR_SERVICE );
					mVibrator.vibrate( new long[]{100,10,100,1000},-1); 
				}
			}
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
	
	@Override
	public void finish(){
		Intent data = new Intent();
		data.putExtra("type", popublayout.getPopubArg());
		data.putExtra("text", edit_search.getText().toString());
		setResult(RESULT_OK, data);
		if(this.fromAct!=null && this.fromAct.length()>0 && ",IndexAct,NavigationAct,".indexOf(","+this.fromAct+",")>=0){
			Frame.HANDLES.sentAll(this.fromAct, 37, data);
		}
		super.finish();
	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		List<String[]> listkeywordname=new ArrayList<String[]>();
		if (son != null && son.mgetmethod.equals("ckeywords")) {
			Msg_Ckeywords.Builder builder = (Msg_Ckeywords.Builder) son.build;
			for(int i=0;i<builder.getKeywordnameCount();i++){
				listkeywordname.add(new String[]{builder.getKeywordname(i),builder.getKeywordid(i)});
			}
		}
		synchronized (F.HOTKEWORD) {
			F.HOTKEWORD.clear();
			F.HOTKEWORD.addAll(listkeywordname);
		}
		loading=false;
	}
	
	
	public void showHotKey(){
		if(F.HOTKEWORD.size()==0){
			return;
		}
		sm.unregisterListener(myAccelerometerListener);
		Random random = new Random();
		int ind=Math.abs(random.nextInt())%domTable.length;
		int[] ints=domTable[ind];
		List<Integer> domlist=new ArrayList<Integer>();
		for(int in:ints){
			domlist.add(in);
		}
		
		searchHotLayout.removeAllViews();
		SearchHotTextAdapter searchAda=null;
		synchronized ( F.HOTKEWORD) {
			searchAda=new SearchHotTextAdapter(this, F.HOTKEWORD,15, domlist);
		}
		if(searchAda!=null){
			for(int i=0;i<searchAda.getCount();i++){
				searchHotLayout.addView(searchAda.getView(i, null, null));
			}
		}
		this.handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sm.registerListener(myAccelerometerListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL); 				
			}
		}, 2000);
	}
	
	@Override
	public void dataLoad(int[] typs) {
		showHotKey();
		if (!loading) {
			loading = true;
			this.loadData(new Updateone[] { new Updateone("CKEYWORDS",
					new String[][] { { "sss", "fff" } }), });
		}
	}
	
	protected void resume() {
		sm.registerListener(myAccelerometerListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);  
	}

	protected void pause() {
		sm.unregisterListener(myAccelerometerListener);  
	}

	@Override
	public void onEnd(SpeechError arg0) {
		editval=(edit_search.getText().toString()).replace(",", "").replace("。","");
		keyWord=editval;
		keyType=popublayout.getPopubArg();
		F.searchTo(SearchAct.this,keyWord,keyType);
		SearchAct.this.finish();
	}

	@Override
	public void onResults(ArrayList<RecognizerResult> results,boolean isLast) {
		StringBuilder builder = new StringBuilder();
		for (RecognizerResult recognizerResult : results) {
			builder.append(recognizerResult.text);
		}
		edit_search.append(builder.toString().replace("。","").replace(",", ""));
		edit_search.setSelection(edit_search.length());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void showIatDialog()
	{
		//获取引擎参数
		String engine = "vsearch";

		//获取area参数，POI搜索时需要传入.
		String area = null;
		if ("poi".equals(engine)) {
			final String defaultProvince = getString(R.string.preference_default_poi_province);
			String province = mSharedPreferences.getString(
					getString(R.string.preference_key_poi_province),
					defaultProvince);
			final String defaultCity = getString(R.string.preference_default_poi_city);
			String city = mSharedPreferences.getString(
					getString(R.string.preference_key_poi_city),
					defaultCity);

			if (!defaultProvince.equals(province)) {
				area = "search_area=" + province;
				if (!defaultCity.equals(city)) {
					area += city;
				}
			}
		}

		
		if(TextUtils.isEmpty(area))
			 area = "";
		else 
			area += ",";
		//设置转写Dialog的引擎和poi参数.
		iatDialog.setEngine(engine, area, null);

		//设置采样率参数，由于绝大部分手机只支持8K和16K，所以设置11K和22K采样率将无法启动录音. 
		String rate = mSharedPreferences.getString(
				getString(R.string.preference_key_iat_rate),
				getString(R.string.preference_default_iat_rate));
		if(rate.equals("rate8k"))
			iatDialog.setSampleRate(RATE.rate8k);
		else if(rate.equals("rate11k"))
			iatDialog.setSampleRate(RATE.rate11k);
		else if(rate.equals("rate16k"))
			iatDialog.setSampleRate(RATE.rate16k);
		else if(rate.equals("rate22k"))
			iatDialog.setSampleRate(RATE.rate22k);
		edit_search.setText(null);
		//弹出转写Dialog.
		iatDialog.show();
	}

}
