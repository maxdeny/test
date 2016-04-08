package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.TextView;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class HotelListAct extends MActivity {
//	TextView head_title;
//	String str_head_title;
//	String navigation;
//	ListView lv;
//	ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	List<Msg_Ccategory> list_ccategory;
//	String categorysubid;
//	String categoryid;
//	//String[] category = { "茅台老窖","真果粒橙","雪碧", "可乐", "百事可乐", "青岛啤酒", "哈尔滨啤酒" };
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.com_merchant_list);
//		setId("HotelListAct");
//		head_title = (TextView) this.findViewById(R.merchant.head_title);
//		Intent intent = this.getIntent();
//		navigation = intent.getStringExtra("navigation");
//		str_head_title = intent.getStringExtra("title");
//		head_title.setText(str_head_title);
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
//		mData = new ArrayList<Map<String, Object>>();
//		categoryid= intent.getStringExtra("categoryparentid");
//	    dataLoad(null);
//	}
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("HCATEGORYAREA")) {
//			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
//			list_ccategory = builder.getCcategoryList();
//			for (int i = 0; i < list_ccategory.size(); i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("hotelname", list_ccategory.get(i).getCategoryname());
//				map.put("id", list_ccategory.get(i).getCategoryid());
//				mData.add(map);
//			}
//			sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
//					new String[] { "hotelname" },
//					new int[] { R.item_merchant.text });
//			lv.setAdapter(sa);
//			lv.setOnItemClickListener(new OnItemClickListener() {
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					Frame.HANDLES.close("HotelNameSearchAct");
//					Frame.HANDLES.get("HotelSearchAct").get(0).sent(4, mData.get(arg2));
//					finish();
//				}
//			});
//		}
//	}
//	
//	@Override
//	public void dataLoad(int[] types) {
//		if(navigation.equals("交通枢纽")){
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid",categoryid}}), });
//		}
//		else if(navigation.equals("地铁站点")){
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid",categoryid}}), });
//		}
//		else if(navigation.equals("热门区域")){
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid",categoryid}}), });
//		}
//		else if(navigation.equals("热门景点")){
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid",categoryid}}), });
//		}
//		else if(navigation.equals("热门品牌")){
//			this.loadData(new Updateone[] { new Updateone("HCATEGORYAREA",
//					new String[][] {{"categoryid",categoryid}}), });
//		}		
//	}
//}