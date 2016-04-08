package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class V3_SentTimeSelectAct extends MActivity {
	String[] senttimes;
	private TextView head_title;
	private Button bt_back;
	SimpleAdapter sa;
	ArrayList<Map<String, Object>> mData;
	private ListView lv;
	String position;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_com_saixuan);
		setId("V3_SentTimeSelectAct");
		senttimes = getIntent().getStringArrayExtra("senttimes");
		position = getIntent().getStringExtra("position");
		head_title = (TextView) this.findViewById(R.v3_com_saixuan.headtitle);
		head_title.setText("发送时间");
		bt_back = (Button) findViewById(R.v3_com_saixuan.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_SentTimeSelectAct.this.finish();
			}
		});
		lv = (ListView) this.findViewById(R.v3_com_saixuan.merchantlist);
		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < senttimes.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("senttime", senttimes[i]);
			mData.add(map);
		}
		sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
				new String[] { "senttime" }, new int[] { R.item_merchant.text });
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String[] str = new String[] { position,
						(String) mData.get(arg2).get("senttime") };
				Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0)
						.sent(1, str);
				V3_SentTimeSelectAct.this.finish();
			}
		});
	}

}
