package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.LifeListAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;

public class BusinessLifeAct extends MActivity {
	private TextView headtitle;
	private PageListView listview;
	private List<Msg_Citem> list_citem;
	private PullReloadView prv;
	private String businessid;
    private String ordertype="1";
	private RadioGroup radiogroup;
	private View norows;
	private int mPage=1;
	private boolean loaddelay=true;
	
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_business_product);
		Intent i = getIntent();
		headtitle = (TextView) findViewById(R.com_business.headtitle);
		headtitle.setText(i.getStringExtra("businessname"));
		businessid=i.getStringExtra("businessid");
		listview = (PageListView) findViewById(R.com_business.listview);
		radiogroup = (RadioGroup) findViewById(R.com_business.radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		prv = (PullReloadView) findViewById(R.com_business.pullReloadView);
		norows=(View) findViewById(R.com_business.norows);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
		listview.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if(loaddelay){
					dataLoadByDelay(null);
					loaddelay=false;
				}else{
					dataLoad();
				}
			}
		});
		listview.setLoadView(new FootLoadingShow(this));
		listview.start(1);
	}

	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.com_business.rbt_people:
				ordertype = "1";
				break;
			case R.com_business.rbt_price:
				ordertype = "2";
				break;
			case R.com_business.rbt_sale:
				ordertype = "3";
				break;
			}
			listview.reload();
		}
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		if(son.build==null){
			if(mPage==1){
				norows.setVisibility(View.VISIBLE);
				listview.setAdapter(null);
			}
			listview.endPage();
		}
		if (son.build != null && son.mgetmethod.equals("citemlist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list_citem = builder.getCitemList();
			LifeListAdapter adapter = new LifeListAdapter(BusinessLifeAct.this,
					list_citem);
			listview.addData(adapter);
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("CITEMLIST", new String[][] {
				{ "businessid", businessid },
				{ "pagecount", F.Per_Page+"" },
				{"orderby",(ordertype!=null&&ordertype.equals("2"))?"asc":"desc"},
				{ "page", mPage+"" },
				{"ordertype",ordertype==null?"1":ordertype}}), });
	}
}
