package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FiltrationAct.FiltrationParam;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter2;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.Item_Search;

public class FruitsAct extends MActivity {
	private PageListView listview;
	private RadioGroup radiogroup;
	private Button btn_category, btn_business, btn_select,back,sousuo;
//	ShoppingListAdapter SLAdapter;
	boolean isEdit = true;
	String categoryid="",rids="";
	String ordertype="1";
	String fenlei,keywords="";
	String shangquan,category4areaid="530";
	String categoryname;
	private PullReloadView prv;
	private Item_Search item_search;
	private View norows;
	private FiltrationParam filt=null;
	private int mPage=1;
	private boolean loaddelay=true;
	ShoppingListAdapter2 SLAdapter;
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.fruits);
		setId("FruitsAct");
		
		listview = (PageListView) findViewById(R.fruits.listview);
		radiogroup = (RadioGroup) findViewById(R.fruits.radiogroup);
		btn_category = (Button) findViewById(R.fruits.btn_category);
		btn_business = (Button) findViewById(R.fruits.btn_business);
		btn_select = (Button) findViewById(R.fruits.btn_select);
		btn_category.setOnClickListener(new OnClick());
		btn_business.setVisibility(View.GONE);
		btn_category.setVisibility(View.GONE);
		btn_business.setOnClickListener(new OnClick());
		btn_select.setOnClickListener(new OnClick());
		if(getIntent().getCharSequenceExtra("category")!=null){
			btn_category.setText(getIntent().getCharSequenceExtra("category"));
		}
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		norows=findViewById(R.id.norows);
//		item_search = (Item_Search)findViewById(R.fruits.item_search);
//		item_search.setSearchDefault(getIntent().getIntExtra("searchPupub", 0));
		back=(Button) findViewById(R.fruits.back);
		back.setText("返回");
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		sousuo=(Button) findViewById(R.fruits.sousuo);
		sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(FruitsAct.this,Search_Act.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
				startActivity(i);
			}
		});
		
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
		
		
		prv=(PullReloadView) findViewById(R.fruits.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
	}
	
	
	private void setSearch(Intent intent){
		categoryid=intent.getStringExtra("categoryid");
		fenlei=intent.getStringExtra("category");
		String keytype=intent.getStringExtra("type");
		keywords=intent.getStringExtra("keywords");
		if(keywords!=null){
			item_search.set(keywords,keytype);
		}
	}	
	
	class OnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (btn_category.equals(v)) {
				Intent intent = new Intent(v.getContext(),
						CategoryFirstAct.class);
				intent.putExtra("isSelect", true);
				intent.putExtra("title", "购物");
				if(Frame.HANDLES.get("CategoryFirstAct").size()>0)
					Frame.HANDLES.closeOne("CategoryFirstAct");
				if(Frame.HANDLES.get("CategorySecondAct").size()>0)
					Frame.HANDLES.closeOne("CategorySecondAct");
				v.getContext().startActivity(intent);
			} else if (btn_business.equals(v)) {
				Intent i=new Intent(v.getContext(),BusinessGroupAllAct.class);
				i.putExtra("navtype", "shop");
				v.getContext().startActivity(i);
			} else if (btn_select.equals(v)) {
				Intent i=new Intent(v.getContext(),FruitCategoryAct.class);
				i.putExtra("navtype", "shop");
				if(filt!=null){
					i.putExtra("filter", filt);
				}
				v.getContext().startActivity(i);
			}
		}

	}
	
	class OnCheckClick implements OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			LoadShow = true;
			switch (checkedId) {
			case R.fruits.rbt_people:
				ordertype = "1";
				break;
			case R.fruits.rbt_price:
				ordertype = "2";
				break;
			case R.fruits.rbt_sale:
				ordertype = "3";
				break;
			}
			listview.reload();
		}
	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		if(type==1){
			String[] str = (String[]) (obj);
			this.rids=str[0];
		}
		loaddelay=true;
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
		if (son.build != null && son.mgetmethod.equals("fslist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem= builder.getCitemList();
			SLAdapter = new ShoppingListAdapter2(FruitsAct.this,list_citem);
			listview.addData(SLAdapter);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==37){
			if(resultCode==RESULT_OK){
				String type=data.getStringExtra("type");
				String text=data.getStringExtra("text");
//				item_search.set(text, type);
			}
		}
	}
	//http://api.taocz.com/mobile/tao.php?app=citemlist&act=slist&ordertype=1&orderby=desc&debug=1&page_per=20&page=946&keywords=
	//&hprice=&lprice=&hcount=0&freight=0&category4selfid
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("FSLIST",
				new String[][] {
								{"keywords",keywords==null?"":keywords},
								{"category4selfid","2878"},
								{"page_per",F.Per_Page+""},
								{"page",this.mPage+""},
								{"orderby",(ordertype!=null&&ordertype.equals("2"))?"asc":"desc"},
								{"rids",filt==null?"":filt.maxPrice},
								{"lprice",filt==null?"":filt.minPrice},
								{"hcount",filt==null?"0":(filt.haveAgio?"1":"0")},
								{"freight",filt==null?"0":(filt.payLate?"1":"0")},
								{"rids",rids},
								{"ordertype",ordertype==null?"1":ordertype}}),
								});
	}
}