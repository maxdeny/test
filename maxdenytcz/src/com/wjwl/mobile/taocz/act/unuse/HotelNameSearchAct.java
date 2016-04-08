package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.SimpleAdapter;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class HotelNameSearchAct extends MActivity {
//	
//	@Override
//	public void dataLoad(int[] types) {
//		// TODO Auto-generated method stub
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid","-1"}}, Msg_CcategoryList.newBuilder()), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		// TODO Auto-generated method stub
//		search = (Msg_CcategoryList.Builder)son.build;
//		list_ccategory = search.getCcategoryList();
//	}
//
//	ListView lv;
//	EditText editText;
//	String[] setname = { "交通枢纽", "地铁站点", "热门区域", "热门景点", "热门品牌" };
//	ArrayList<Map<String, Object>> mData = null;
//	Msg_CcategoryList.Builder search = null;
//	private SimpleAdapter sa;
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.hotelname_address);
//		setId("HotelNameSearchAct");
//		lv = (ListView) this.findViewById(R.hotelname.hotelnamelist);
//		editText = (EditText)findViewById(R.hotelname.bt_name);
//		editText.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				searchPopview(s.toString());
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < setname.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("setname", setname[i]);
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(this, mData, R.layout.item_hotelname_address,
//				new String[] { "setname" }, new int[] { R.item_hotelname.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				switch (arg2) {
//				case 0:
//					Intent intent0 = new Intent(HotelNameSearchAct.this,HotelListAct.class);
//					intent0.putExtra("navigation", "交通枢纽");
//					intent0.putExtra("title", "交通枢纽");
//					intent0.putExtra("categoryparentid", "1");
//					startActivity(intent0);
//					break;
//				case 1:
//					Intent intent1 = new Intent(HotelNameSearchAct.this,HotelListAct.class);
//					intent1.putExtra("navigation", "地铁站点");
//					intent1.putExtra("title", "地铁站点");
//					intent1.putExtra("categoryparentid", "2");
//					startActivity(intent1);
//					break;
//				case 2:
//					Intent intent2 = new Intent(HotelNameSearchAct.this,HotelListAct.class);
//					intent2.putExtra("navigation", "热门区域");
//					intent2.putExtra("title", "热门区域");
//					intent2.putExtra("categoryparentid", "3");
//					startActivity(intent2);
//					break;
//				case 3:
//					Intent intent3 = new Intent(HotelNameSearchAct.this,HotelListAct.class);
//					intent3.putExtra("navigation", "热门景点");
//					intent3.putExtra("title", "热门景点");
//					intent3.putExtra("categoryparentid", "4");
//					startActivity(intent3);
//					break;
//				case 4:
//					Intent intent4 = new Intent(HotelNameSearchAct.this,HotelListAct.class);
//					intent4.putExtra("navigation", "热门品牌");
//					intent4.putExtra("title", "热门品牌");
//					intent4.putExtra("categoryparentid", "5");
//					startActivity(intent4);
//					break;
//				}
//			}
//		});
//		
//		LayoutInflater inflater = LayoutInflater.from(this);
//		layouySearch = (LinearLayout) inflater.inflate(R.layout.listcitysearch, null);
//		popupWindow = new PopupWindow(layouySearch, LayoutParams.FILL_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources()));
//		dataLoad(null);
//	}
//
//	
//	
//	ArrayList<Map<String, Object>> searData = null;
//	private SimpleAdapter searchAdapter;
//	private PopupWindow popupWindow;
//	List<Msg_Ccategory> list_ccategory;
//	private LinearLayout layouySearch;
//	ListView listview;
//	public void searchPopview(String key) {
//		if (key.trim().length() <= 1)
//			return;
//		if (listview == null)
//			listview = (ListView) layouySearch.findViewById(R.id.list);
//		searData = new ArrayList<Map<String,Object>>();
//		final ArrayList<Msg_Ccategory> SearchList = new ArrayList<Msg_Ccategory>();
//		for (Msg_Ccategory category : list_ccategory) {
//			if (category.getCategoryname().contains(key)) {
//				SearchList.add(category);
//			}
//		}
//		if (SearchList.size() > 0)
//			listview.setVisibility(View.VISIBLE);
//		else {
//			listview.setVisibility(View.GONE);
//		}
//		for (int i = 0; i < SearchList.size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("hotelname", SearchList.get(i).getCategoryname());
//			map.put("id", SearchList.get(i).getCategoryid());
//			searData.add(map);
//		}
//		if(searData.size()==0)
//			return;
//		searchAdapter = new SimpleAdapter(this, searData, R.layout.item_list,
//				new String[] { "hotelname" },
//				new int[] { R.id.text_website_name });
//		listview.setAdapter(searchAdapter);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub
//				Frame.HANDLES.get("HotelSearchAct").get(0).sent(4, searData.get(position));
//				finish();
//			}
//		});
//		if (!popupWindow.isShowing()) {
//			popupWindow.showAsDropDown(findViewById(R.hotelname.bt_name), 0, -4);
//			popupWindow.setFocusable(true);
//			popupWindow.update();
//		}
//	}
//	
//}
