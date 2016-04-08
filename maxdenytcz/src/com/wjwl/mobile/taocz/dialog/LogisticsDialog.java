package com.wjwl.mobile.taocz.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class LogisticsDialog extends MDialog {
	TextView t1, t2, t3, t4, t5, t6;
	ListView lv;
	Context context;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	String[] str = { "2012-07-28 23:09 湖北武汉中转站快件进入分拨中心进行中转",
			"2012-07-28 23:10 湖北武汉中转站快件进行重新扫描",
			"2012-07-30 13:10 上海中转站快件进入分拨中心发出，本次转运目的地：上海中转站",
			"2012-07-31 06:10 上海中转站亏阿健从分拨中心出发，本次转运目的地：江苏江阴公司" };

	public LogisticsDialog(Context context) {
		super(context, R.style.RDialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		Create();
	}

	public void Create() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.logistics);
		t1 = (TextView) findViewById(R.logistics.text1);
		t2 = (TextView) findViewById(R.logistics.text2);
		t3 = (TextView) findViewById(R.logistics.text3);
		t4 = (TextView) findViewById(R.logistics.text4);
		t5 = (TextView) findViewById(R.logistics.text5);
		t6 = (TextView) findViewById(R.logistics.text6);
		lv = (ListView) findViewById(R.logistics.listview);
		t1.setText("在线下单");
		t2.setText("韵达快运");
		t3.setText("LP00010407407498");
		t4.setText("1200550852916");
		t5.setText("等待物流公司确认");
		t6.setText("该信息由物流公司提供，如有疑问请咨询" + "韵达快运官网");
		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < str.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", str[i]);

			mData.add(map);
		}
		sa = new SimpleAdapter(context, mData,
				R.layout.item_logistics, new String[] { "text" },
				new int[] { R.item_logistics.logisticsinfo });
		lv.setAdapter(sa);
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
	}

}
