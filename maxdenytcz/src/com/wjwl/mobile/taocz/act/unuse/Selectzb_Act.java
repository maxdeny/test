package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.BianmingAdapter;
//import com.wjwl.mobile.taocz.adapter.SelectzbAdapter;
//
//public class Selectzb_Act extends MActivity{
//	Button back,dingwei;
//	TextView text,myaddress;
//	GridView gridview;
//	String names[]={"餐饮美食","休闲娱乐","票务剧场","美容保健","酒店旅游","摄影写真","公共设施","更多分类",};
//	private int[] pics = {R.drawable.btn_navi_conser,  R.drawable.btn_navi_shop,R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_life, R.drawable.btn_navi_moving,R.drawable.btn_navi_moving,R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_conser,};
//	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
//	HashMap<String,Object> map;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.select_zb);
//		back=(Button) findViewById(R.select_zb.back);
//		dingwei=(Button) findViewById(R.select_zb.dingwei);
//		text=(TextView) findViewById(R.select_zb.text);
//		myaddress=(TextView) findViewById(R.select_zb.myaddress);
//		gridview=(GridView) findViewById(R.select_zb.gridview);
//		
//		for(int i=0;i<names.length;i++){
//			map=new HashMap<String,Object>();
//			map.put("name", names[i]);
//			map.put("pic", pics[i]);
//			data.add(map);
//		}
//		gridview.setAdapter(new SelectzbAdapter(Selectzb_Act.this,data));
//	}
//
//}
