package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.BianmingAdapter;
//
//public class Bianming_GridAct extends MActivity{
//	GridView gridview;
//	Button back;
//	TextView text;
//	String[] titles={"家政预约","水电煤查询","违章查询","医院挂号","手机话费充值","点卡充值","天气预报","便民电话",
//			"蔬菜价格","药品价格","公共事业价格",};
//	private int[] navi_img = {R.drawable.btn_navi_conser,  R.drawable.btn_navi_shop,R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_life, R.drawable.btn_navi_moving,R.drawable.btn_navi_moving,R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_conser, R.drawable.btn_navi_conser,R.drawable.btn_navi_shop,
//			R.drawable.btn_navi_tuangou };
//	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
//	HashMap<String,Object> map;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.bianming_grid);
//		gridview=(GridView) findViewById(R.bianming.gridview);
//		back=(Button) findViewById(R.bianming.back);
//		text=(TextView) findViewById(R.bianming.text);
//		for(int i=0;i<titles.length;i++){
//			map=new HashMap<String,Object>();
//			map.put("pic", navi_img[i]);
//			map.put("title", titles[i]);
//			data.add(map);
//		}
//		gridview.setAdapter(new BianmingAdapter(Bianming_GridAct.this,data));
//		gridview.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				switch (arg2) {
//				case 0:
//					Intent i1 = new Intent();
//					i1.putExtra("title",
//							"家政预约");
//					i1.putExtra("searchPupub", 2);
//					i1.setClass(Bianming_GridAct.this, CategoryFirstAct.class);
//					startActivity(i1);
//					
//					break;
//
//				default:
//					break;
//				}
//			}
//		});
//		
//	}
//
//}
