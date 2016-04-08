package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter;

public class BusinessTakeoutAct extends MActivity {
	TextView headtitle;
	PageListView lv;
	List<Msg_Citem> list_citem;
	private PullReloadView prv;
	String businessid;
	private String ordertype = "1";
	private RadioGroup radiogroup;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_business_product);
		Intent i = getIntent();
		headtitle = (TextView) findViewById(R.com_business.headtitle);
		headtitle.setText(i.getStringExtra("businessname"));
		businessid = i.getStringExtra("businessid");
		lv = (PageListView) findViewById(R.com_business.listview);
		radiogroup = (RadioGroup) findViewById(R.com_business.radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		prv = (PullReloadView) findViewById(R.com_business.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.com_business.rbt_people:
				ordertype = "1";
				dataLoad(null);
				break;
			case R.com_business.rbt_price:
				ordertype = "2";
				dataLoad(null);
				break;
			case R.com_business.rbt_sale:
				ordertype = "3";
				dataLoad(null);
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("SLIST")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list_citem = builder.getCitemList();
			ShoppingListAdapter adapter = new ShoppingListAdapter(
					BusinessTakeoutAct.this, list_citem);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i = new Intent();
					i.putExtra("itemid", list_citem.get(arg2).getItemid());
					i.setClass(getApplicationContext(),
							ShoppingContentAct.class);
					startActivity(i);
				}
			});
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("SLIST", new String[][] {
				{ "businessid", businessid }, { "ordertype", ordertype } }), });
	}

}
