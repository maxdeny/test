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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class MyjifenAct extends MActivity {
	TczV3_HeadLayout headlayout;
	TextView  myjifen;
	RadioGroup group;
	RadioButton mycards, usedcard;
	ListView listview, listview1;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> map;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.myjifen);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("我的积分");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyjifenAct.this.finish();
			}
		});
		myjifen = (TextView) findViewById(R.myjifen.jifen);
		listview = (ListView) findViewById(R.myjifen.listview);
		dataLoad(new int[] { 1 });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("myjflist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			data.clear();
			myjifen.setText(builder.getTprice());
			for (int i = 0; i < builder.getItemBuilderList().size(); i++) {
				map = new HashMap<String, Object>();
				map.put("jifencount", builder.getItem(i).getItemcount());
				map.put("starttime", builder.getItem(i).getPaytime());
				map.put("fromorder", builder.getItem(i).getProductname());
				data.add(map);
			}
			listview.setAdapter(new MyAdapter(MyjifenAct.this, data,
					"MyjifenAct"));

		}
	}

	public void dataLoad(int[] types) {
		if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("MYJFLIST",
					new String[][] { { "userid", F.USER_ID },
							{ "perpage", "1000" }, { "page", "1" } }), });
		}

	}
}
