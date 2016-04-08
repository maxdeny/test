package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.WaimaiAdapter;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//
//public class Waimai_listAct extends MActivity{
//	ListView listview;
//	Button back,saixuan;
//	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
//	HashMap<String,Object> map;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.waimai_list);
//		listview=(ListView) findViewById(R.waimai.listview);
//		back=(Button) findViewById(R.waimai.back);
//		saixuan=(Button) findViewById(R.waimai.saixuan);
//		for(int i=0;i<10;i++){
//			map=new  HashMap<String,Object>();
//			map.put("pic", R.drawable.ic_launcher);
//			map.put("title", "正宗重庆凉拌菜");
//			map.put("style", "凉菜");
//			map.put("juli", "20km");
//			map.put("price", "人均：70元");
//			map.put("star", "4");
//			data.add(map);
//		}
//		listview.setAdapter(new WaimaiAdapter(Waimai_listAct.this,data));
////		LayoutInflater flater=LayoutInflater.from(getApplication());
////		View view=flater.inflate(R.layout.headlistview, null);
////		ListView listview1=(ListView) view.findViewById(R.headlistview.listview);
////		listview.addHeaderView(listview1);
//	}
//
//}
