package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.CanweiAdapter;

public class CanweiInfo_Act extends MActivity{
		Button back,other;
		RadioButton left,mid,right;
		TextView text;
		RadioGroup group;
		ListView listview1,listview2,listview3,pp_listview1,pp_listview2;
		List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map;
		List<HashMap<String,String>> data1=new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map1;
		PopupWindow pp;
		String[] stylemenu={"川菜","湘菜","日本料理","韩式烤肉","西餐","鄂菜"};
		View viewpp;
		Handler hd;
		Runnable rb;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.canweiinfo);
		back=(Button) findViewById(R.canwei.back);
		other=(Button) findViewById(R.canwei.other);
		group=(RadioGroup) findViewById(R.canwei.group);
		left=(RadioButton) findViewById(R.canwei.left);
		mid=(RadioButton) findViewById(R.canwei.mid);
		right=(RadioButton) findViewById(R.canwei.right);
		text=(TextView) findViewById(R.canwei.text);
		listview1=(ListView) findViewById(R.canwei.listview1);
		listview2=(ListView) findViewById(R.canwei.listview2);
		listview3=(ListView) findViewById(R.canwei.listview3);
		viewpp=findViewById(R.canwei.view1);
		for(int i=0;i<10;i++){
			map=new HashMap<String,Object>();
			map.put("pic", R.drawable.ic_launcher);
			map.put("title", "金陵尚府");
			map.put("star", "4");
			map.put("juli", "460米");
			map.put("style", "川菜");
			map.put("price", "人均：170-320元");
			data.add(map);
		}
		listview2.setAdapter(new CanweiAdapter(CanweiInfo_Act.this,data));
		left.setOnClickListener(new Click());
		mid.setOnClickListener(new Click());
		right.setOnClickListener(new Click());
		
		LayoutInflater flater=LayoutInflater.from(getApplication());
		View view=flater.inflate(R.layout.pp_content, null);
		pp_listview1=(ListView) view.findViewById(R.pp.listview1);
		pp_listview2=(ListView) view.findViewById(R.pp.listview2);
		for(int i=0;i<stylemenu.length;i++){
			map1=new HashMap<String,String>();
			map1.put("menu", stylemenu[i]);
			data1.add(map1);
		}
		pp_listview1.setAdapter(new SimpleAdapter(CanweiInfo_Act.this, data1, R.layout.pp_listviewitem,
				new String[]{"menu"}, new int[]{R.pp_listviewitem.text}));
		pp_listview1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pp_listview2.setVisibility(View.VISIBLE);
				pp_listview2.setAdapter(new SimpleAdapter(CanweiInfo_Act.this, data1, R.layout.pp_listviewitem,
						new String[]{"menu"}, new int[]{R.pp_listviewitem.text}));
			}
		});
		pp_listview2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				pp.dismiss();
			}
		});
		pp = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				200, true);
		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
		
		hd=new Handler();
		rb=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if(pp.isShowing()){
					
				}else{
					viewpp.setVisibility(View.GONE);
				}
				hd.postDelayed(rb, 10);
			}
		};
		hd.postDelayed(rb, 10);
	}

	public class Click implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.canwei.left:
				listview1.setVisibility(View.VISIBLE);
				listview2.setVisibility(View.GONE);
				listview3.setVisibility(View.GONE);
				break;
			case R.canwei.mid:
				listview1.setVisibility(View.GONE);
				listview2.setVisibility(View.VISIBLE);
				listview3.setVisibility(View.GONE);
				pp.showAsDropDown(left,15,0);
				pp_listview2.setVisibility(View.GONE);
				viewpp.setVisibility(View.VISIBLE);
				break;
			case R.canwei.right:
				listview1.setVisibility(View.GONE);
				listview2.setVisibility(View.GONE);
				listview3.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}
	

}
