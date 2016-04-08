package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.HotelCity.Msg_HotelCity;
//import com.tcz.apkfactory.data.HotelCityList.Msg_HotelCityList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.CityAdapter;
//import com.wjwl.mobile.taocz.adapter.CitySearchAdapter;
//import com.wjwl.mobile.taocz.widget.CityRightVCharacterView;
//import com.wjwl.mobile.taocz.widget.CityRightVCharacterView.OnTouchingLetterChangedListener;
//
//import android.content.Context;
//import android.graphics.PixelFormat;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.view.WindowManager;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//public class CityAct extends MActivity implements ListView.OnScrollListener,
//		OnItemClickListener {
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("HOTELCITY",
//				new String[][] {}), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		// TODO Auto-generated method stub
//		if (son == null || !son.mgetmethod.equals("HOTELCITY"))
//			return;
//		Msg_HotelCityList.Builder builder = (Msg_HotelCityList.Builder) son.build;
//
//		CityList = builder.getHotelCityList();
//		hotelCityList = CityList;
//		listAdapter = new CityAdapter(this, hotelCityList);
//		listMain.setAdapter(listAdapter);
//	}
//
//	private CityRightVCharacterView letterListView;
//	private Handler handler;
//	private DisapearThread disapearThread;
//	private int scrollState;
//	private CityAdapter listAdapter;
//	private ListView listMain;//listSearch;
//	private LinearLayout layouySearch;
//	private TextView txtOverlay;
//	private WindowManager windowManager;
//	private Button btn_search;
//	private PopupWindow popupWindow;
//	private EditText edit_name;
//	private List<Msg_HotelCity> hotelCityList;
//	private List<Msg_HotelCity> CityList;
//	private int firstNum = 0, firstNumTemp = 0;
//	String[] strCharactor = new String[0]; // 存储提示文字{A B C D E}
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.city);
//		hotelCityList = new ArrayList<Msg_HotelCity>();
//		CityList = new ArrayList<Msg_HotelCity>();
//		handler = new Handler();
//		btn_search = (Button) findViewById(R.city.bt_search);
//		edit_name = (EditText) findViewById(R.city.edit_name);
//		letterListView = (CityRightVCharacterView) findViewById(R.city.rightCharacterListView);
//		listMain = (ListView) findViewById(R.city.listInfo);
//		LayoutInflater inflater = LayoutInflater.from(this);
//		layouySearch = (LinearLayout) inflater.inflate(R.layout.list, null);
//		txtOverlay = (TextView) LayoutInflater.from(this).inflate(
//				R.layout.city_popup, null);
//		txtOverlay.setVisibility(View.INVISIBLE);
//		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_APPLICATION,
//				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//				PixelFormat.TRANSLUCENT);
//		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//		windowManager.addView(txtOverlay, lp);
//
//		popupWindow = new PopupWindow(layouySearch, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources()));
//
//		dataLoad(null);
//
//		disapearThread = new DisapearThread();
//		listMain.setOnItemClickListener(this);
//		listMain.setOnScrollListener(this);
//
//		String[] az = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
//				"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
//				"X", "Y", "Z" };
//		letterListView.setB(az);
//		letterListView
//				.setOnTouchingLetterChangedListener(new LetterListViewListener());
//		getFirstNum();
//		edit_name.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// TODO Auto-generated method stub
//				searchPopview(s.toString());
//				// searchKey(s.toString());
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//	}
//
//	void getData() {
//		hotelCityList = CityList;
//	}
//
//	ListView listview;
//
//	public void searchPopview(String key) {
//		if (key.trim().length() == 0)
//			return;
//		if (listview == null)
//			listview = (ListView) layouySearch.findViewById(R.id.list);
//		final ArrayList<Msg_HotelCity> SearchList = new ArrayList<Msg_HotelCity>();
//		for (Msg_HotelCity city : hotelCityList) {
//			if (city.getHotelCityName().length() >= key.length()
//					&& city.getHotelCityName().startsWith(key)
//					&& city.getHotelCityCategory().length() == 1
//					&& !city.getHotelCityType().equals("1")) {
//				SearchList.add(city);
//			}
//		}
//		if (SearchList.size() > 0)
//			listview.setVisibility(View.VISIBLE);
//		else {
//			listview.setVisibility(View.GONE);
//		}
//		CitySearchAdapter listsearchAdapter = new CitySearchAdapter(this,
//				SearchList);
//		listview.setAdapter(listsearchAdapter);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				if (SearchList.get(position).getHotelCityType().equals("0")) {
//					String personalName = SearchList.get(position)
//							.getHotelCityName();
//					Frame.HANDLES.get("HotelSearchAct").get(0)
//							.sent(0, personalName);
//					popupWindow.dismiss();
//					finish();
//				}
//			}
//		});
//		if (!popupWindow.isShowing()) {
//			popupWindow.showAsDropDown(findViewById(R.city.city_search), 0, -4);
//			popupWindow.setFocusable(true);
//			popupWindow.update();
//		}
//	}
//
//	// listview 带A B C D搜索结果
//	public void searchKey(String key) {
//		getData();
//		if (key.length() > 0) {
//			ArrayList<Msg_HotelCity> SearchList = new ArrayList<Msg_HotelCity>();
//			boolean first = true;
//			for (Msg_HotelCity city : hotelCityList) {
//				if (city.getHotelCityName().contains(key)) {
//					if (first) {
//						if (city.getHotelCityType().equals("1")
//								|| city.getHotelCityCategory().length() > 1) // >1
//																				// //
//																				// 判断是不是A-Z
//							continue;
//						Msg_HotelCity.Builder cityfirst = Msg_HotelCity
//								.newBuilder();// newBuilder();
//						cityfirst.setHotelCityName(city.getHotelCityCategory());
//						cityfirst.setHotelCityCategory(city
//								.getHotelCityCategory());
//						cityfirst.setHotelCityType("1");
//						SearchList.add((Msg_HotelCity) cityfirst.build());
//						first = false;
//					}
//					if (!city.getHotelCityCategory().equals(
//							SearchList.get(SearchList.size() - 1)
//									.getHotelCityCategory()))
//						first = true;
//					if (first) {
//						if (city.getHotelCityType().equals("1")
//								|| city.getHotelCityCategory().length() > 1) // >1
//																				// //
//																				// 判断是不是A-Z
//							continue;
//						Msg_HotelCity.Builder cityfirst = Msg_HotelCity
//								.newBuilder();
//						cityfirst.setHotelCityName(city.getHotelCityCategory());
//						cityfirst.setHotelCityCategory(city
//								.getHotelCityCategory());
//						cityfirst.setHotelCityType("1");
//						SearchList.add((Msg_HotelCity) cityfirst.build());
//						first = false;
//					}
//					SearchList.add(city);
//				}
//			}
//			hotelCityList = SearchList;
//			CityAdapter ca = new CityAdapter(this, SearchList);
//			listMain.setAdapter(ca);
//		} else {
//			listMain.setAdapter(listAdapter);
//		}
//
//	}
//
//	public void getFirstNum() {
//		for (int i = 0; i < hotelCityList.size(); i++) {
//			if (hotelCityList.get(i).getHotelCityCategory().equals("A")) {
//				firstNum = i;
//				firstNumTemp = firstNum;
//				return;
//			}
//		}
//	}
//
//	public class LetterListViewListener implements
//			OnTouchingLetterChangedListener {
//
//		@Override
//		public void onTouchingLetterChanged(final String s) {
//			for (int i = firstNum; i < hotelCityList.size(); i++) {
//				if (s.equals("")) {
//					listMain.setSelectionFromTop(0, 0);
//					return;
//				} else if ("A".equals(s)) {
//					break;
//				} else if (character2ASCII(hotelCityList.get(i)
//						.getHotelCityCategory().toUpperCase()) < (character2ASCII(s))) {
//					firstNumTemp += 1;
//				}
//			}
//			listMain.setSelectionFromTop(firstNumTemp, 0);
//			firstNumTemp = firstNum;
//		}
//	}
//
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount) {
//		if (hotelCityList != null && hotelCityList.size() > 0)
//			txtOverlay.setText(String.valueOf(hotelCityList.get(
//					firstVisibleItem).getHotelCityCategory()));
//	}
//
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		this.scrollState = scrollState;
//		if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
//			handler.removeCallbacks(disapearThread);
//			handler.postDelayed(disapearThread, 1500);
//		} else {
//			txtOverlay.setVisibility(View.VISIBLE);
//		}
//	}
//
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		if (hotelCityList.get(position).getHotelCityType().equals("0")) {
//			String personalName = hotelCityList.get(position)
//					.getHotelCityName();
//			Frame.HANDLES.get("HotelSearchAct").get(0).sent(0, personalName);
//			finish();
//		}
//	}
//
//	private class DisapearThread implements Runnable {
//		public void run() {
//			if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
//				txtOverlay.setVisibility(View.INVISIBLE);
//			}
//		}
//	}
//
//	public void onDestroy() {
//		super.onDestroy();
//		txtOverlay.setVisibility(View.INVISIBLE);
//		windowManager.removeView(txtOverlay);
//	}
//
//	public static int character2ASCII(String input) {
//		if (input.length() > 1)
//			return 0;
//		char[] temp = input.toCharArray();
//		StringBuilder builder = new StringBuilder();
//		for (char each : temp) {
//			builder.append((int) each);
//		}
//		String result = builder.toString();
//		return Integer.parseInt(result);
//	}
//}