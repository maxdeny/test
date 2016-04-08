package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.http.message.BasicNameValuePair;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Ckeywords.Msg_Ckeywords;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.DatabaseHepler;
import com.wjwl.mobile.taocz.adapter.MDragChangeViewAdapter;
import com.wjwl.mobile.taocz.adapter.SearchAdapter;
import com.wjwl.mobile.taocz.adapter.SearchHotTextAdapter;
import com.wjwl.mobile.taocz.dialog.LoadingDialog;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.PopubLayout;

public class Search_Act extends MActivity implements OnClickListener,
RecognizerDialogListener{
	RadioGroup group;
	RadioButton radiobtn1,radiobtn2;
	ListView listview1,findlistview;
	LinearLayout listview,searchHotLayout;
	private PopubLayout popublayout;
	private EditText edit_search;
	private Button searchCancel,speakbutton,yuyin,tiaoma,btn_search,btn_del;
	private String editval,keyWord,keyType,fromAct;
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog iatDialog;
	String[] names={"牛奶","咖啡","红酒","巧克力","花生","恰恰瓜子"};
	String[] numbers={"约2333个宝贝","约22111个宝贝","约54444个宝贝","约12432个宝贝","约23412个宝贝","约54333个宝贝",};
	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
	HashMap<String,Object> map;
	private DatabaseHepler dbHelper;
	private SQLiteDatabase db;
	private boolean loading=false;
	RelativeLayout relativeLayout;
	public static List<BasicNameValuePair> childList = new ArrayList<BasicNameValuePair>();
	private List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
	ListAdapter listAdapter;
	private  SensorManager sm;  
	private int domTable[][]=new int[][]{
			{3,3,3,2,4},
			{4,4,3,2,2},
			{3,3,3,3,3}
	};
	DragChangeView mDragChangeView;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.six);
		setId("Search_Act");
		((LoadingDialog)loadingDialog).setText("加载中...");
		loadingDialog.show();
		sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		group=(RadioGroup) findViewById(R.six.group);
		radiobtn1=(RadioButton) findViewById(R.six.radiobtn1);
		radiobtn2=(RadioButton) findViewById(R.six.radiobtn2);
		radiobtn1.setTextColor(Color.parseColor("#808A87"));
		radiobtn2.setTextColor(Color.parseColor("#FFFFFF"));
		listview=(LinearLayout) findViewById(R.six.listview);
		listview1=(ListView) findViewById(R.six.listview1);
		searchHotLayout=(LinearLayout) findViewById(R.six.initlayout);
		btn_search=(Button) findViewById(R.six.btn_search);
		btn_del=(Button) findViewById(R.six.btn_del);
		mDragChangeView = (DragChangeView) findViewById(R.tczv3.DragChangeView);
		
		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setRadius(7);
		mDragChangeView.setMoveType(1);
		
		
		
		btn_search.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if (edit_search.getText().toString().trim().replace("\n","").equals("")) {
//					Toast.makeText(Search_Act.this, "请输入搜索物品名称",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					editval=edit_search.getText().toString().replace("\n","").replace(",", "").replace("。","");
//					keyWord=editval;
//					keyType=popublayout.getPopubArg();
//					F.searchTo(Search_Act.this,keyWord,keyType);
//					
//					
//					dbHelper=new DatabaseHepler(Search_Act.this);
//					db=dbHelper.getWritableDatabase();
//					String sql1="select contents from logcat where contents='"+keyWord+"' and styles='"+keyType+"'";
//					Cursor c=db.rawQuery(sql1, null);
//					if(c!=null&&c.getCount()>0){
//						}else{
//							String sql="insert into logcat(contents,styles) values(?,?)";
//							db.execSQL(sql, new String[]{keyWord,keyType});
//						}
//					c.close();
//					db.close();
//					dbHelper.close();
//				}
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(Search_Act.this, KuSaoAct.class);// KuSaoAct,CameraAct
				startActivity(intent);
				
			}
		});
		
		btn_del.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		searchCancel=(Button) findViewById(R.six.clean_btn);
		edit_search=(EditText) findViewById(R.six.edit_search);
		popublayout = (PopubLayout)findViewById(R.six.popublayout);
			if(F.searchtemp.equals("gouwu")){
				popublayout.setPopubArg("gouwu");
			}
			else{
				popublayout.setPopubArg("shenghuo");
		}
		relativeLayout =(RelativeLayout) findViewById(R.six.relativelayout);
		findlistview=(ListView) findViewById(R.six.listfind);
		
		yuyin=(Button) findViewById(R.six.yuyin);
		tiaoma=(Button) findViewById(R.six.tiaoma);
		tiaoma.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent= new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setClass(Search_Act.this, KuSaoAct.class);
                startActivity(intent);
			}
		});
		
		findlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Toast.makeText(Search_Act.this, childList.get(arg2).getValue(), Toast.LENGTH_SHORT).show();
//				String jumptype=childList.get(arg2).getValue();
				String jumptype=popublayout.getPopubArg().equals("gouwu")?"5":"2";
				//商家1，团购2，外卖3，预定4，购物5
				Intent intent =new Intent();
				intent.putExtra("keywords", keyWord);
				intent.putExtra("act", "SearchIteam");
				if(jumptype.equals("1")){
//				intent.setClass(Search_Act.this, TnTejia_Act.class);
				}else if(jumptype.equals("2")){
				intent.setClass(Search_Act.this, GroupBuyingListAct.class);
				}else if(jumptype.equals("3")){
				intent.putExtra("search", "search");
//				intent.setClass(Search_Act.this, TakeOutListAct1.class);		
				}else if(jumptype.equals("4")){
//				intent.setClass(Search_Act.this, RestaurantDetailsListAct.class);
				}else if(jumptype.equals("5")){
				intent.setClass(Search_Act.this, ShoppingListAct.class);
				}
				startActivity(intent);
			}
		});
		
		this.LoadShow=false;
		
		iatDialog = new RecognizerDialog(Search_Act.this, "appid=" +F.APPID);
		iatDialog.setListener(this);
		mSharedPreferences = Search_Act.this.getSharedPreferences("com.wjwl.mobile.taocz", 0);
		
//		keyWord=getIntent().getStringExtra("keyWord");
//		keyWord=keyWord==null?"":keyWord.replace(",", "").replace("。","");
//		
//		keyType=getIntent().getStringExtra("keyType");
//		keyType=keyType==null?"":keyType;
//		
//		fromAct=getIntent().getStringExtra("fromAct");
		
//		popublayout.setPopubArg(keyType);
//		edit_search.setText(keyWord);
//		lishi_record();
//		dataLoad(new int[]{1});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.six.radiobtn1:      //搜索历史记录
					radiobtn1.setTextColor(Color.parseColor("#FFFFFF"));
					radiobtn2.setTextColor(Color.parseColor("#808A87"));
					lishi_record();
					break;
				case R.six.radiobtn2:			//热门搜索
					radiobtn1.setTextColor(Color.parseColor("#808A87"));
					radiobtn2.setTextColor(Color.parseColor("#FFFFFF"));
					listview.setVisibility(View.VISIBLE);
					listview1.setVisibility(View.GONE);
					searchCancel.setVisibility(View.INVISIBLE);
					dataLoad(new int[]{1});
					break;
				default:
					break;
				}
			}
		});
		
//		speakbutton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				showIatDialog();
//			}
//		});
		yuyin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showIatDialog();
			}
		});
		
		searchCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbHelper=new DatabaseHepler(Search_Act.this);
				db=dbHelper.getReadableDatabase();
				String sql="delete from logcat"; 
				db.execSQL(sql);
				listview1.setVisibility(View.INVISIBLE);
				searchCancel.setVisibility(View.INVISIBLE);
				db.close();
				dbHelper.close();
			}
		});
		
		edit_search.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == 66) {
//					edit_search.setFocusable(false);
//					childList.clear();
					if (edit_search.getText().toString().trim().replace("\n","").equals("")) {
						Toast.makeText(Search_Act.this, "请输入搜索物品名称",
								Toast.LENGTH_SHORT).show();
//						edit_search.requestFocus();
					} else {
						editval=edit_search.getText().toString().replace("\n","").replace(",", "").replace("。","");
						keyWord=editval;
						keyType=popublayout.getPopubArg();
//						dataLoad(new int[]{2});
						F.searchTo(Search_Act.this,keyWord,keyType);
//						Search_Act.this.finish();
						
//						dbHelper=new DatabaseHepler(Search_Act.this);
//						db=dbHelper.getWritableDatabase();
//						String sql1="select contents from logcat where contents='"+keyWord+"' and styles='"+keyType+"'";
//						Cursor c=db.rawQuery(sql1, null);
//						if(c!=null&&c.getCount()>0){
//							}else{
//								String sql="insert into logcat(contents,styles) values(?,?)";
//								db.execSQL(sql, new String[]{keyWord,keyType});
//							}
//						c.close();
//						db.close();
//						dbHelper.close();
					}
				}
			return false;
		}
		});
//		edit_search.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				if (s == null || s.toString().equals("")) {
//					findlistview.setVisibility(View.GONE);
//					relativeLayout.setVisibility(View.VISIBLE);
//				} 
//			}
//		});
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		dataLoadByDelay(new int[]{1},500);
	}
	public void lishi_record(){
		listview.setVisibility(View.GONE);
		listview1.setVisibility(View.VISIBLE);
		dbHelper=new DatabaseHepler(Search_Act.this);
		db=dbHelper.getReadableDatabase();
		String sql="select * from logcat";
		Cursor c=db.rawQuery(sql, null);
		if(c!=null&&c.getCount()>0){
			data=new ArrayList<HashMap<String,Object>>();
			while (c.moveToNext()) {
				map=new HashMap<String,Object>();
				map.put("name", c.getString(c.getColumnIndex("contents")));//1
				map.put("style", c.getString(c.getColumnIndex("styles")));
				data.add(map);
			}
			listview1.setAdapter(new SearchAdapter(Search_Act.this, data));
			searchCancel.setVisibility(View.VISIBLE);
		}else{
			listview1.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplication(), "暂无搜索记录", Toast.LENGTH_SHORT).show();
		}
		db.close();
		dbHelper.close();
	}
	@Override
	public void disposeMsg(int type, Object obj) {
//		childList.clear();
//		((LoadingDialog)loadingDialog).setText("加载中...");
//		loadingDialog.show();
//		if(type==1){
//			String[] str = (String[]) obj;
//			keyWord=str[0];
//			dataLoad(new int[]{2});
//		}		
//		if(type==2){
//			keyWord="";
//			loadingDialog.dismiss();
//			}
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}
	
	@Override
	public void disposeMessage(Son son) throws Exception {
		List<String[]> listkeywordname=new ArrayList<String[]>();
		if (son.build != null && son.mgetmethod.equals("ckeywords")) {
			Msg_Ckeywords.Builder builder = (Msg_Ckeywords.Builder) son.build;
			for(int i=0;i<builder.getKeywordnameCount();i++){
				listkeywordname.add(new String[]{builder.getKeywordname(i),builder.getKeywordid(i)});//id即style
			}
			if(listkeywordname!=null&&listkeywordname.size()>0){
				data=new ArrayList<HashMap<String,Object>>();
				for(int i=0;i<listkeywordname.size();i++){
					map=new HashMap<String,Object>();
					map.put("name", listkeywordname.get(i)[0]);
					map.put("style", listkeywordname.get(i)[1]);
					data.add(map);
				}
//				listview.setAdapter(new SearchAdapter(Search_Act.this, data));
				synchronized (F.HOTKEWORD) {
					F.HOTKEWORD.clear();
					F.HOTKEWORD.addAll(listkeywordname);
				}
			}
			loadingDialog.dismiss();
		}
		if (son.mgetmethod.equals("searchall")) {
			if(son.build != null ){
				childList.clear();
				Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
				for(int i=0;i<builder.getCcategoryList().size();i++){
					childList.add(new BasicNameValuePair(builder.getCcategoryBuilder(i).getCategoryname(), builder.getCcategoryBuilder(i).getCategoryid()));
				}
			}
			dataLoad(new int[]{3} );
		}
		if (son.build != null && son.mgetmethod.equals("searchallgw")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			for(int i=0;i<builder.getCcategoryList().size();i++){
				childList.add(new BasicNameValuePair(builder.getCcategoryBuilder(i).getCategoryname(), builder.getCcategoryBuilder(i).getCategoryid()));
			}
			if (keyWord != null && !keyWord.toString().equals("")) {
				listAdapter =new ListAdapter(Search_Act.this, keyWord
				.toString());
				findlistview.setAdapter(listAdapter);
				findlistview.setVisibility(View.VISIBLE);
				relativeLayout.setVisibility(View.GONE);
			}
			
			edit_search.setText(keyWord);
			edit_search.setFocusable(true);   
			edit_search.setFocusableInTouchMode(true);   
			edit_search.requestFocus();  
			loadingDialog.dismiss();
		}
		if (son.build != null && son.mgetmethod.equals("V3_AD")) {
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder)son.build;
			List<Msg_CitemList> dataSource = builder.getCitemlistList();
			MDragChangeViewAdapter mDragChangeViewAdapter = new MDragChangeViewAdapter(dataSource.get(0).getCitemList(), this);
			mDragChangeView.setAdapter(mDragChangeViewAdapter);
		}
		
//		synchronized (F.HOTKEWORD) {
//			F.HOTKEWORD.clear();
//			F.HOTKEWORD.addAll(listkeywordname);
//		}
//		loading=false;
	}
	@Override
	public void dataLoad(int[] typs) {
//		if (!loading) {
//			loading = true;
		if(typs[0]==1){
			showHotKey();
			this.loadData(new Updateone[] { new Updateone("CKEYWORDS",
					new String[][] { { "sss", "fff" } }),new Updateone("V3_AD",
							new String[][] {{ "ppage","search" }}) });
		}
		if(typs[0]==2){
			this.loadData(new Updateone[] { new Updateone("SEARCHALL",
					new String[][] { { "keywords", keyWord } }), });
		}
		if(typs[0]==3){
			this.loadData(new Updateone[] { new Updateone("SEARCHALLGW",
					new String[][] { { "keywords", keyWord } }), });
		}
//		}
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

	@Override
	public void onEnd(SpeechError arg0) {
//		keyType=popublayout.getPopubArg();
//		F.searchTo(Search_Act.this,keyWord,keyType==null?"":keyType);
		
//		if (keyWord != null && !keyWord.toString().equals("")) {
//			findlistview.setAdapter(new ListAdapter(Search_Act.this, keyWord
//					.toString()));
//			findlistview.setVisibility(View.VISIBLE);
//			relativeLayout.setVisibility(View.GONE);
//		} else {
//			findlistview.setVisibility(View.GONE);
//			relativeLayout.setVisibility(View.VISIBLE);
//		}
		dataLoad(new int []{2});
	}

	@Override
	public void onResults(ArrayList<RecognizerResult> results, boolean arg1) {
		StringBuilder builder = new StringBuilder();
		for (RecognizerResult recognizerResult : results) {
			builder.append(recognizerResult.text);
		}
		keyWord=builder.toString().replace("。","").replace(",", "");
//		edit_search.append(keyWord);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	protected class ListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		//查询结果列表
		public ListAdapter(Context context, String strin) {
			mInflater = LayoutInflater.from(context);
//			list.clear();
			//查询匹配
//			for (int i = 0; i < childList.size(); i++) {
//					String tmp = childList.get(i).getName();
//					if (tmp.indexOf(strin) >= 0) {
//						list.add(new BasicNameValuePair(tmp,childList.get(i).getValue()));
//					}
//			}
		}

		public int getCount() {
			return childList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.search_child, null);
			TextView title = (TextView) convertView.findViewById(R.search.child);
			title.setText(childList.get(position).getName());
			return convertView;
		}
	}
	
	final SensorEventListener myAccelerometerListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				float x = sensorEvent.values[0];
				float y = sensorEvent.values[1];
				float z = sensorEvent.values[2];
				if(x*x+y*y+z*z>400){
					dataLoad(new int[]{1});
//					Vibrator mVibrator = ( Vibrator )getApplication().getSystemService( Service .VIBRATOR_SERVICE );
//					mVibrator.vibrate( new long[]{100,10,100,1000},-1); 
				}
			}
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}
	};
	
	
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
	protected void resume() {
		sm.registerListener(myAccelerometerListener,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);  
		MobclickAgent.onPageStart("SearchPage");
		MobclickAgent.onResume(Search_Act.this);
		if(radiobtn1.isChecked()){
			lishi_record();
		}
		if(radiobtn2.isChecked()){
			dataLoad(new int[]{1});
		}
		
	}
	@Override
	protected void pause() {
		sm.unregisterListener(myAccelerometerListener);  
		childList.clear();
		MobclickAgent.onPageEnd("SearchPage");
		MobclickAgent.onPause(Search_Act.this);
	}
	
	
	

}

