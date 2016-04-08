package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;
	
public class Youhui_Act extends MActivity{
	Button back;
	TextView text;
	RadioGroup group;
	RadioButton notused,used;
	ListView listview,listview1;
	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
	HashMap<String,Object> map;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.youhui_act);
		back=(Button) findViewById(R.youhui_act.back);
		text=(TextView) findViewById(R.youhui_act.text);
		group=(RadioGroup) findViewById(R.youhui_act.radiogroup);
		notused=(RadioButton) findViewById(R.youhui_act.notused);
		used=(RadioButton) findViewById(R.youhui_act.used);
		listview=(ListView) findViewById(R.youhui_act.listview);
		listview1=(ListView) findViewById(R.youhui_act.listview1);
		
		for(int i=0;i<5;i++){
			map=new  HashMap<String,Object>();
			map.put("pic", R.drawable.ic_launcher);
			map.put("name", "汉庭快捷酒店优惠劵");
			map.put("money", "5");
			map.put("time", "2013-6-5");
			data.add(map);
		}
		listview.setAdapter(new MyAdapter(Youhui_Act.this,data,"Youhui_Act"));
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.youhui_act.notused:
					listview.setVisibility(View.VISIBLE);
					listview1.setVisibility(View.GONE);
					break;
				case R.youhui_act.used:
					listview.setVisibility(View.GONE);
					listview1.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		});
	}

}
