package com.wjwl.mobile.taocz.act;

import java.util.List;
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

public class V3_AreaListAct extends MActivity {
	private TextView head_title;
	private Button bt_back;
	private ListView lv;
	SimpleAdapter sa;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_com_saixuan);
		setId("V3_SaiXuan_PinPaiAct");
		head_title = (TextView) this.findViewById(R.v3_com_saixuan.headtitle);
		head_title.setText("区域");
		bt_back = (Button) findViewById(R.v3_com_saixuan.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_AreaListAct.this.finish();
			}
		});
		lv = (ListView) this.findViewById(R.v3_com_saixuan.merchantlist);
		sa = new SimpleAdapter(this, V3_ZiTiAct.AreaData,
				R.layout.item_com_merchant_list, new String[] { "areaname" },
				new int[] { R.item_merchant.text });
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Frame.HANDLES.get("V3_ZiTiAct").get(0).sent(1, arg2);
				V3_AreaListAct.this.finish();
			}
		});
	}

}
