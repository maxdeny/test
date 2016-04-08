package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
//import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
//import com.wjwl.mobile.taocz.R;
//
//public class StarSelectAct extends MActivity {
//	private TextView head_title;
//	private ListView lv;
//	ArrayList<Map<String, Object>> mData = null;
//	private SimpleAdapter sa;
//	String stars[] = { "经济型", "商务型", "主题型", "三星级", "四星级","五星级" };
//	String stars1[] = { "1", "2", "3", "4", "5","6" };
//
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.com_merchant_list);
//		setId("SaiXuanCategoryFirst");
//		head_title = (TextView) this.findViewById(R.merchant.head_title);
//		mData = new ArrayList<Map<String, Object>>();
//		lv = (ListView) this.findViewById(R.merchant.merchantlist);
//		head_title.setText("星级");
//		for (int i = 0; i < stars.length; i++) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("item", stars[i]);
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
//				SaiXuan_Act.text3.setText((String) mData.get(arg2).get("item"));
//                SaiXuan_Act.star=stars1[arg2];
//				StarSelectAct.this.finish();
//			}
//
//		});
//	}
//
//}
