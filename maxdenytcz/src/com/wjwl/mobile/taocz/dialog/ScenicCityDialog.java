//package com.wjwl.mobile.taocz.dialog;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import android.content.Context;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import com.mdx.mobile.dialogs.MDialog;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.ScenicAct;
//
//public class ScenicCityDialog extends MDialog {
//
//	private TextView headtext;
//	private ListView lv;
//	private SimpleAdapter sa;
//	public ArrayList<Map<String, Object>> mData = null;
//	Context context;
//
//	public ScenicCityDialog(Context context) {
//		super(context,R.style.FullWindowDialog);
//		this.context = context;
//		Create();
//	}
//
//	public void Create() {
//		// TODO Auto-generated method stub
//		this.setContentView(R.layout.com_merchant_list);
//		headtext = (TextView) this.findViewById(R.merchant.head_title);
//		headtext.setText(R.string.head_sceniclist_city);
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
//		dataLoad(null);
//	}
//
//	public void create() {
//
//	}
//	
//	@Override
//	public void dataLoad(int[] types) {
//		// TODO Auto-generated method stub
//		this.loadData(new Updateone[] { new Updateone("CCITY",
//				new String[][] {{"categoryid","0"}}, Msg_CcategoryList.newBuilder()), });
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		// TODO Auto-generated method stub
//		Msg_CcategoryList.Builder list = (Msg_CcategoryList.Builder)son.build;
//		mData = new ArrayList<Map<String, Object>>();
//		for (int i = 0; i < list.getCcategoryList().size(); i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("city", list.getCcategory(i).getCategoryname());
//			map.put("id", list.getCcategory(i).getCategoryid());
//			mData.add(map);
//		}
//		sa = new SimpleAdapter(context, mData, R.layout.item_com_merchant_list,
//				new String[] { "city" }, new int[] { R.item_merchant.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				ScenicAct.bt_city_search.setText(mData.get(arg2).get("city").toString());
//				ScenicAct.bt_city_search.setTag(mData.get(arg2).get("id").toString());
//				ScenicCityDialog.this.cancel();
//				ScenicCityDialog.this.dismiss();
//			}});
//	}
//	
//}
