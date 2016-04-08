package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.BusinessList2Adapter;

public class BusinessCategoryAct extends MActivity {
	private PageListView list;
	private ArrayList<Map<String, Object>> allmData;
	private int mPage = 1;
	private boolean loaddelay = true;
	ArrayList<Map<String, Object>> mData = null;
	Button bt_back;
	String navtype = "business";
	TextView headtext;
	private String categoryid = "2878";
	private List<Msg_Citem> list_citem;
	private String actfrom = "", categoryname = "";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fruitcategory);
		setId("BusinessCategoryAct");
		actfrom = getIntent().getStringExtra("actfrom");
		categoryid = getIntent().getStringExtra("categoryid");
		bt_back = (Button) findViewById(R.fruitcategory.back);
		headtext = (TextView) findViewById(R.fruitcategory.headtext);
		categoryname = getIntent().getStringExtra("categoryname");
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				BusinessCategoryAct.this.finish();
			}
		});
		headtext.setText("商家");
		list = (PageListView) findViewById(R.fruitcategory.list);
		list.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if (loaddelay) {
					dataLoadByDelay(null);
					loaddelay = false;
				} else {
					dataLoad();
				}
			}
		});
		list.start(1);
	}

	public void dataLoad(int[] types) {
		if (categoryname!=null&&categoryname.equals("微店铺")) {
			this.loadData(new Updateone[] { new Updateone("V3_STORELIST_WEI",
					new String[][] { { "category4selfid", categoryid } }), });
		} else {
			this.loadData(new Updateone[] { new Updateone("V3_STORELIST",
					new String[][] { { "category4selfid", categoryid } }), });
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null
				&& (son.mgetmethod.equals("v3_storelist") || son.mgetmethod
						.equals("v3_storelist_wei"))) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list_citem = builder.getCitemList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_citem.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", list_citem.get(i).getItemid());
				map.put("name", list_citem.get(i).getItembusinessname());
				mData.add(map);
			}
			BusinessList2Adapter adapter = new BusinessList2Adapter(this,
					mData, actfrom);
			list.addData(adapter);
			if (list_citem.size() < F.Per_Page) {
				list.endPage();
			}
		}
	}

}
