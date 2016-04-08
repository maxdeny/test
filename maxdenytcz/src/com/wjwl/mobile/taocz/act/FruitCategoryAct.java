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
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.R.fruitcategory;
import com.wjwl.mobile.taocz.adapter.ChooseAdapter;

public class FruitCategoryAct extends MActivity {
	private PageListView list;
	private ArrayList<Map<String, Object>> allmData;
	private int mPage = 1;
	private boolean loaddelay = true;
	ArrayList<Map<String, Object>> mData = null;
	List<Msg_Ccategory> list_ccategory;
	Button bt_back;
	String navtype = "", categoryid = "";
	TextView headtext;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.fruitcategory);
		setId("FruitCategoryAct");
		bt_back = (Button) findViewById(R.fruitcategory.back);
		headtext = (TextView) findViewById(R.fruitcategory.headtext);

		navtype = getIntent().getStringExtra("navtype");
		if (navtype.equals("vmarket"))
			categoryid = getIntent().getStringExtra("categoryid");
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FruitCategoryAct.this.finish();
			}
		});
		if (navtype.equals("fruit")) {
			headtext.setText("水果分类");
		} else if (navtype.equals("mini_shop")) {
			headtext.setText("微店铺分类");
		} else {
			headtext.setText("果蔬生鲜分类");
		}
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
		if (navtype.equals("mini_shop")) {
			this.loadData(new Updateone[] { new Updateone("V3_CATEGORY_WEI",
					new String[][] {}), });
		} else {
			this.loadData(new Updateone[] { new Updateone("FRUITCATEGORY",
					new String[][] { { "navtype", navtype },
							{ "category4selfid", categoryid } }), });
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null
				&& (son.mgetmethod.equals("fruitcategory") || son.mgetmethod
						.equals("v3_category_wei"))) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", list_ccategory.get(i).getCategoryid());
				map.put("name", list_ccategory.get(i).getCategoryname());
				mData.add(map);
			}
			ChooseAdapter adapter = new ChooseAdapter(this, mData, navtype);
			list.addData(adapter);
			if (list_ccategory.size() < F.Per_Page) {
				list.endPage();
			}
		}
	}

}
