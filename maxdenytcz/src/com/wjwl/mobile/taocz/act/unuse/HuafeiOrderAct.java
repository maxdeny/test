package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.MyAdapter;
//
//public class HuafeiOrderAct extends MActivity{
//	Button back;
//	TextView text;
//	ListView listview;
//	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
//	HashMap<String,Object> map;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.huafeiorder);
//		back=(Button) findViewById(R.huafeiorder.back);
//		text=(TextView) findViewById(R.huafeiorder.text);
//		listview=(ListView) findViewById(R.huafeiorder.listview);
//		
//		for(int i=0;i<10;i++){
//			map=new HashMap<String,Object>();
//			map.put("name", "湖北移动20元话费充值");
//			map.put("date", "2012-11-14");
//			map.put("ordernumber", "131239871");
//			map.put("money", "170");
//			map.put("state", "交易完成");
//			data.add(map);
//		}
//		listview.setAdapter(new MyAdapter(HuafeiOrderAct.this,data,"HuafeiOrderAct"));
//		
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//	}
//
//}
