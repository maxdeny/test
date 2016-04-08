package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;

public class V3_SaiXuanListAct extends MActivity {

	private TextView head_title;
	private Button bt_back;
	private ListView lv;
	SimpleAdapter sa;
	ArrayList<Map<String, Object>> mData;
	private List<Msg_Ccategory> list_ccategory;
	private String filtertype = "", categoryid = "", keywords = "";
	private String actfrom = "";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_com_saixuan);
		setId("V3_SaiXuan_PinPaiAct");
		head_title = (TextView) this.findViewById(R.v3_com_saixuan.headtitle);
		head_title.setText(getIntent().getStringExtra("title"));
		filtertype = getIntent().getStringExtra("filtertype");
		categoryid = getIntent().getStringExtra("categoryid");
		keywords = getIntent().getStringExtra("keywords");
		actfrom = getIntent().getStringExtra("actfrom");
		bt_back = (Button) findViewById(R.v3_com_saixuan.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_SaiXuanListAct.this.finish();
			}
		});
		lv = (ListView) this.findViewById(R.v3_com_saixuan.merchantlist);
		dataLoadByDelay(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("v3_filterlist")) {
			Toast.makeText(V3_SaiXuanListAct.this, "暂无数据~", Toast.LENGTH_SHORT)
					.show();
		}
		if (son.build != null && son.mgetmethod.equals("v3_filterlist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("itemname", list_ccategory.get(i).getCategoryname());
				map.put("itemid", list_ccategory.get(i).getCategoryid());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData,
					R.layout.item_com_merchant_list,
					new String[] { "itemname" },
					new int[] { R.item_merchant.text });
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String[] str = new String[] {
							(String) mData.get(arg2).get("itemname"),
							(String) mData.get(arg2).get("itemid") };
					if (filtertype.equals("brand")) {
						Frame.HANDLES.get(actfrom).get(0).sent(1, str);
					} else if (filtertype.equals("price")) {
						Frame.HANDLES.get(actfrom).get(0).sent(2, str);
					}
					V3_SaiXuanListAct.this.finish();
				}
			});
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (actfrom.equals("V3_ShaiXuanAct"))
			this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
					new String[][] { { "itemid", categoryid },
							{ "keywords", keywords },
							{ "filtertype", filtertype } }), });
		else if (actfrom.equals("LiHua_SaiXuanAct"))
			this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
					new String[][] { { "businessid", categoryid },
							{ "keywords", keywords },
							{ "filtertype", filtertype } }), });
	}
}
