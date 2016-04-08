package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.hardware.Sensor;
//import android.hardware.SensorEvent;
//import android.hardware.SensorEventListener;
//import android.hardware.SensorManager;
//import android.os.Bundle;
//import android.os.Vibrator;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Ckeywords.Msg_Ckeywords;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.adapter.SearchHotTextAdapter;
//import com.wjwl.mobile.taocz.widget.Item_Search;
//
//public class NavigationAct extends MActivity {
//	private ArrayList<Map<String, Object>> mData;
//	private GridView gridView;
//	private int[] navi_img = { R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_life, R.drawable.btn_navi_moving,
//			R.drawable.btn_navi_conser, 
//			R.drawable.btn_navi_tuangou };
//	// R.drawable.btn_navi_cityrecruit,R.drawable.btn_navi_takeout
//	// public String[] navi_title = { "购物", "生活", "景点门票", "酒店预订", "便民服务", "外卖",
//	// "积分商城" };
//	public int[] navi_title = { R.string.shop, R.string.life,
//			R.string.moving_show, R.string.conser
////			, R.string.takeout
//			,R.string.group_buying
////			, R.string.cityrecruit
//			};
//
//	// liulu for hotkey begin
//	private boolean loading = false;
//	private int domTable[][] = new int[][] { { 3, 3 }, { 4, 2 }, };
//	private SensorManager sm;
//	private LinearLayout searchHotLayout;
//	private Item_Search item_search;
//	public WmDB wmdb;
//
//	// end
//
//	class ViewHolder {
//		ImageView img;
//		TextView title;
//	}
//
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.navigation);
//		setId("NavigationAct");
//		gridView = (GridView) findViewById(R.navigation.gridview);
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < navi_img.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("img", navi_img[i]);
//			map.put("title", navi_title[i]);
//			mData.add(map);
//		}
//		item_search = (Item_Search) findViewById(R.navigation.item_search);
//		PictureAdapter adapter = new PictureAdapter(NavigationAct.this);
//		gridView.setAdapter(adapter);
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				switch (arg2) {
//				case 0:// 购物
//				case 1:// 生活（服务）
//				case 2:// 电影
//				case 3:// 家政服务
//					Intent i1 = new Intent();
//					i1.putExtra("title",
//							getResources().getString(navi_title[arg2]));
//					i1.putExtra("searchPupub", arg2);
//					i1.setClass(NavigationAct.this, CategoryFirstAct.class);
//					startActivity(i1);
//					break;
////				case 4:
////					wmdb = new WmDB(NavigationAct.this);
////					wmdb.deleteall();
////					Intent i3 = new Intent();
////					i3.putExtra("title",
////							getResources().getString(navi_title[arg2]));
////					i3.putExtra("searchPupub", arg2);
////					i3.setClass(NavigationAct.this, TakeOutGridViewAct.class);
////					startActivity(i3);
////					break;
//				case 4:// 团购
//					Intent i2 = new Intent();
//					i2.putExtra("title",
//							getResources().getString(navi_title[arg2]));
//					i2.putExtra("searchPupub", arg2);
//					i2.setClass(NavigationAct.this, GroupBuyingListAct.class);
//					startActivity(i2);
//					break;
////				case 6:
////					if (F.USER_ID == null || F.USER_ID.length() == 0) {
////						F.toLogin(NavigationAct.this, "NavigationAct",
////								"CiytRecruitAct", 0);
////					} else {
////						Intent i4 = new Intent();
////						i4.setClass(NavigationAct.this, CiytRecruitAct.class);
////						startActivity(i4);
////					}
////					break;
//				}
//			}
//		});
//		// liulu for hotkey begin
//		this.LoadShow = false;
//		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		searchHotLayout = (LinearLayout) findViewById(R.navigation.initlayout);
//		dataLoadByDelay(null, 500);
//		// end
//	}
//
//	// liulu for hotkey begin
//	public void showHotKey() {
//		if (F.HOTKEWORD.size() == 0) {
//			return;
//		}
//		sm.unregisterListener(myAccelerometerListener);
//		Random random = new Random();
//		int ind = Math.abs(random.nextInt()) % domTable.length;
//		int[] ints = domTable[ind];
//		List<Integer> domlist = new ArrayList<Integer>();
//		for (int in : ints) {
//			domlist.add(in);
//		}
//
//		searchHotLayout.removeAllViews();
//		SearchHotTextAdapter searchAda = null;
//		synchronized (F.HOTKEWORD) {
//			searchAda = new SearchHotTextAdapter(this, F.HOTKEWORD, 6, domlist);
//		}
//		if (searchAda != null) {
//			for (int i = 0; i < searchAda.getCount(); i++) {
//				searchHotLayout.addView(searchAda.getView(i, null, null));
//			}
//		}
//		this.handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				sm.registerListener(myAccelerometerListener,
//						sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
//						SensorManager.SENSOR_DELAY_NORMAL);
//			}
//		}, 2000);
//	}
//
//	final SensorEventListener myAccelerometerListener = new SensorEventListener() {
//		public void onSensorChanged(SensorEvent sensorEvent) {
//			if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//
//				float x = sensorEvent.values[0];
//				float y = sensorEvent.values[1];
//				float z = sensorEvent.values[2];
//				if (x * x + y * y + z * z > 400) {
//					dataLoad(null);
//					Vibrator mVibrator = (Vibrator) getApplication()
//							.getSystemService(Service.VIBRATOR_SERVICE);
//					mVibrator.vibrate(new long[] { 100, 10, 100, 1000 }, -1);
//				}
//			}
//		}
//
//		public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//		}
//	};
//
//	public void disposeMsg(int type, Object obj) {
//		if (type == 37) {
//			Intent data = (Intent) obj;
//			String typ = data.getStringExtra("type");
//			String text = data.getStringExtra("text");
//			item_search.set(text, typ);
//		}
//		if (type == 86) {
//			if (F.USER_ID != null && F.USER_ID.length() > 0) {
////				if ("CiytRecruitAct".equals(obj)) {
////					Intent i3 = new Intent();
////					i3.setClass(NavigationAct.this, CiytRecruitAct.class);
////					startActivity(i3);
////				}
//			}
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		List<String[]> listkeywordname = new ArrayList<String[]>();
//		if (son.build != null && son.mgetmethod.equals("ckeywords")) {
//			Msg_Ckeywords.Builder builder = (Msg_Ckeywords.Builder) son.build;
//			for (int i = 0; i < builder.getKeywordnameCount(); i++) {
//				listkeywordname.add(new String[] { builder.getKeywordname(i),
//						builder.getKeywordid(i) });
//			}
//		}
//		synchronized (F.HOTKEWORD) {
//			F.HOTKEWORD.clear();
//			F.HOTKEWORD.addAll(listkeywordname);
//		}
//		loading = false;
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//		showHotKey();
//		if (!loading) {
//			loading = true;
//			this.loadData(new Updateone[] { new Updateone("CKEYWORDS",
//					new String[][] { { "sss", "fff" } }), });
//		}
//	}
//
//	protected void destroy() {
//		sm.unregisterListener(myAccelerometerListener);
//	}
//
//	// end
//
//	class PictureAdapter extends BaseAdapter {
//		private LayoutInflater inflater;
//
//		public PictureAdapter(Context context) {
//			this.inflater = LayoutInflater.from(context);
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder viewHolder;
//			if (convertView == null) {
//				convertView = inflater.inflate(
//						R.layout.item_navigation_grid_photo, null);
//				viewHolder = new ViewHolder();
//				viewHolder.img = (ImageView) convertView
//						.findViewById(R.item_navi_grid.navi_image);
//				viewHolder.title = (TextView) convertView
//						.findViewById(R.item_navi_grid.text);
//				convertView.setTag(viewHolder);
//			} else {
//				viewHolder = (ViewHolder) convertView.getTag();
//			}
//			viewHolder.img.setImageResource((Integer) mData.get(position).get(
//					"img"));
//			// viewHolder.title.setText((String)
//			// mData.get(position).get("title"));
//			viewHolder.title
//					.setText((Integer) mData.get(position).get("title"));
//			return convertView;
//		}
//
//		public int getCount() {
//			return mData.size();
//		}
//
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	}
//}
