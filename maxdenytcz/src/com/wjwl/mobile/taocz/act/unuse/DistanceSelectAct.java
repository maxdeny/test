package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class DistanceSelectAct extends MActivity {
//
//	private TextView head_title;
//	private ListView lv;
//	ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	String distances[] = { "1000米", "2000米", "5000米" ,"全市"};
//	String distance1[] = { "1000", "2000", "5000" ,"100000"};
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.com_merchant_list);
//		setId("SaiXuanCategoryFirst");
//		head_title = (TextView) this.findViewById(R.merchant.head_title);
//		mData = new ArrayList<Map<String, Object>>();
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
//		head_title.setText("距离");
//		for (int i = 0; i < distances.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("item", distances[i]);
//			mData.add(map);
//		}
//
//		sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
//				new String[] { "item" }, new int[] { R.item_merchant.text });
//		lv.setAdapter(sa);
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//
//				SaiXuan_Act.text2.setText((String) mData.get(arg2).get("item"));
//                SaiXuan_Act.distance=distance1[arg2];
//				DistanceSelectAct.this.finish();
//			}
//
//		});
//	}
//
//}
