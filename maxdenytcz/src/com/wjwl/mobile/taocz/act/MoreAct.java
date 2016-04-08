package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.ExitLogindialog;

public class MoreAct extends MActivity {
	String[] setname;
	int[] icon;
	ListView lv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;

	protected void create(Bundle arg0) {
		setContentView(R.layout.more);
		setId("MoreAct");
		init();

	}

	private void init() {
		// setname = new String[] {
		// getResources().getString(R.string.more_nullify),
		// getResources().getString(R.string.more_about),
		// getResources().getString(R.string.more_accset),
		// getResources().getString(R.string.more_help) };
		// icon = new int[] { R.drawable.more_nullify, R.drawable.more_about,
		// R.drawable.more_accset, R.drawable.more_help };
		setname = new String[] {
				getResources().getString(R.string.more_nullify),
				getResources().getString(R.string.more_about),
				getResources().getString(R.string.more_accset), };
		icon = new int[] { R.drawable.more_nullify, R.drawable.more_about,
				R.drawable.more_accset };
		lv = (ListView) this.findViewById(R.more.morelist);
		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < setname.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("icon", icon[i]);
			map.put("setname", setname[i]);
			mData.add(map);
		}
		sa = new SimpleAdapter(this, mData, R.layout.item_more_list,
				new String[] { "icon", "setname" }, new int[] {
						R.item_more.icon, R.item_more.text });
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					if(F.USER_ID!=null && F.USER_ID.length()>0){
						ExitLogindialog ex=new ExitLogindialog(MoreAct.this);
						ex.show();
					}else{
						Toast.makeText(MoreAct.this, "您还没有登录，无法注销", Toast.LENGTH_SHORT).show();
					}
					break;
				case 1:
					Intent intent1 = new Intent();
					intent1.setClass(getApplicationContext(), AboutAct.class);
					startActivity(intent1);
					break;
				case 2:
					Intent i = new Intent();
					i.setClass(getApplicationContext(), AccSetupAct.class);
					startActivity(i);
					break;
				case 3:
					Intent intent2 = new Intent();
					intent2.setClass(getApplicationContext(), HelpAct.class);
					startActivity(intent2);
					break;
				}
			}
		});
	}
}
