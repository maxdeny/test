package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;

public class MyOrder_Act extends MActivity{
	TextView text;
	ListView listview;
	Button back;
//	String ts[]={"购物订单",,,"移动营业厅订单","今夜特价订单","话费订单","便民订单"};"团购订单""外卖外送订单"
	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
	HashMap<String,Object> map;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.myorder_act);
		text=(TextView) findViewById(R.myorder_act.text);
		listview=(ListView) findViewById(R.myorder_act.listview);
		back=(Button) findViewById(R.myorder_act.back);
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
//				Intent i=new Intent(MyOrder_Act.this,JyTjOrderAct.class);
//				startActivity(i);
			}
		});
		if(data.size()>0)
			data.clear();
		dataLoad(null);
		
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mcategory2")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			for(int i=0;i<builder.getCcategoryList().size();i++){
				map=new HashMap<String,Object>();
				map.put("name", builder.getCcategoryList().get(i).getCategoryname());
				map.put("number", builder.getCcategoryList().get(i).getCategoryid()+"");
				if(!builder.getCcategoryList().get(i).getCategoryname().equals("生活订单"))
					data.add(map);
			}
		}
		if (son.build != null && son.mgetmethod.equals("mtgcategory")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			for(int i=0;i<builder.getCcategoryList().size();i++){
				map=new HashMap<String,Object>();
				map.put("name", builder.getCcategoryList().get(i).getCategoryname());
				map.put("number", builder.getCcategoryList().get(i).getCategoryid()+"");
				data.add(map);
			}
		}
		listview.setAdapter(new MyAdapter(MyOrder_Act.this,data,"MyOrder_Act"));
	}
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MCATEGORY2",
				new String[][] { { "user_id", F.USER_ID} }),new Updateone("MTGCATEGORY",
						new String[][] { { "userid", F.USER_ID } }) });//F.USER_ID 
	}

}
