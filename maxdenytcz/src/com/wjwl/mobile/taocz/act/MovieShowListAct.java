package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
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
import com.wjwl.mobile.taocz.widget.Item_Search;

public class MovieShowListAct extends MActivity {
	private PageListView listview;
	private RadioGroup radiogroup;
	// ShoppingListAdapter SLAdapter;
	boolean isEdit = true;
	String categoryid;
	String ordertype = "1";
	private String businessid, keywords="";
	private PullReloadView prv;
	private LifeListAdapter MovieShowAdapter;
	private Item_Search item_search;
	private View norows;
	private int mPage=1;
	private boolean loaddelay=true;
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.movielist);
		this.setId("MovieShowListAct");
		radiogroup = (RadioGroup) findViewById(R.movielist.radiogroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		listview = (PageListView) findViewById(R.movielist.listview);
		categoryid = "1";
		businessid=getIntent().getStringExtra("businessid");
		norows=findViewById(R.id.norows);
		item_search = (Item_Search)findViewById(R.movielist.item_search);
		
		setSearch(getIntent());
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
		
		prv=(PullReloadView) findViewById(R.movielist.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
	}
	
	
	private void setSearch(Intent intent){
		businessid=intent.getStringExtra("businessid");
		String keytype=intent.getStringExtra("type");
		keywords=intent.getStringExtra("keywords");
		if(keywords!=null){
			item_search.set(keywords,keytype);
		}else{
			item_search.set("","dianyingyanchu");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==37){
			if(resultCode==RESULT_OK){
				String type=data.getStringExtra("type");
				String text=data.getStringExtra("text");
				item_search.set(text, type);
			}
		}
	}

	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			LoadShow = true;
			switch (checkedId) {
			case R.movielist.rbt_people:
				ordertype = "1";
				break;
			case R.movielist.rbt_price:
				ordertype = "2";
				break;
			case R.movielist.rbt_sale:
				ordertype = "3";
				break;
			}
			listview.reload();
		}
	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		if(type==37){
			Intent intent=(Intent) obj;
			setSearch(intent);
		}
		this.LoadShow = true;
		listview.reload();
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
		if (son.build != null && son.mgetmethod.equals("dlist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem = builder.getCitemList();
			MovieShowAdapter = new LifeListAdapter(MovieShowListAct.this,list_citem);
			listview.addData(MovieShowAdapter);
			if(list_citem.size()<F.Per_Page){
				listview.endPage();
			}
			norows.setVisibility(View.INVISIBLE);
		}
		prv.refreshComplete();
	}

	@Override
	public void closeLoad() {
		super.closeLoad();
		this.LoadShow = false;
	}
	
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("DLIST", new String[][] {
				{ "ordertype", ordertype == null ? "1" : ordertype } ,
				{"keywords",keywords==null?"":keywords},
				{"page_per",F.Per_Page+""},
				{"page",this.mPage+""},
				{"orderby",(ordertype!=null&&ordertype.equals("2"))?"asc":"desc"},
				{"category4selfid",businessid==null?"":businessid}}), });
	}
}
