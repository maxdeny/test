package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.FiltrationAct.FiltrationParam;
import com.wjwl.mobile.taocz.adapter.RanqiListAdapter;
import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter2_YiDong;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.Item_Search;

public class Yidong_Act extends MActivity {
	private PageListView listview;
	private RadioGroup radiogroup;
	private Button btn_category, btn_business, btn_select,back,sousuo;
//	RanqiListAdapter SLAdapter;
	boolean isEdit = true;
	String categoryid="";
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
	TextView text;
	LinearLayout linear;
	ShoppingListAdapter2_YiDong SLAdapter;
	
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.shoppinglist);
		setId("Yidong_Act");
		
		listview = (PageListView) findViewById(R.shoppinglist.listview);
		radiogroup = (RadioGroup) findViewById(R.shoppinglist.radiogroup);
		btn_category = (Button) findViewById(R.shoppinglist.btn_category);
		btn_business = (Button) findViewById(R.shoppinglist.btn_business);
		btn_select = (Button) findViewById(R.shoppinglist.btn_select);
		btn_category.setOnClickListener(new OnClick());
		btn_business.setVisibility(View.GONE);
		btn_business.setOnClickListener(new OnClick());
		btn_select.setOnClickListener(new OnClick());
		if(getIntent().getCharSequenceExtra("category")!=null){
			btn_category.setText(getIntent().getCharSequenceExtra("category"));
		}
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
		norows=findViewById(R.id.norows);
		btn_business.setVisibility(View.GONE);
//		item_search = (Item_Search)findViewById(R.shoppinglist.item_search);
//		item_search.setSearchDefault(getIntent().getIntExtra("searchPupub", 0));
		text=(TextView) findViewById(R.shoppinglist.text);
		text.setText("常州移动");
		back=(Button) findViewById(R.shoppinglist.back);
		back.setText("返回");
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		linear=(LinearLayout) findViewById(R.shoppinglist.layout);
		linear.setVisibility(View.GONE);
		sousuo=(Button) findViewById(R.shopinglist.sousuo);
		sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(Yidong_Act.this,Search_Act.class);
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
		
		
		prv=(PullReloadView) findViewById(R.shoppinglist.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				listview.reload();
			}
		});
		btn_category.setVisibility(View.GONE);
		 btn_business.setVisibility(View.GONE);
		 btn_select.setVisibility(View.GONE);
	}
	
	
	private void setSearch(Intent intent){
		categoryid=intent.getStringExtra("categoryid");
		fenlei=intent.getStringExtra("category");
		String keytype=intent.getStringExtra("type");
		keywords=intent.getStringExtra("keywords");
		if(keywords!=null){
//			item_search.set(keywords,keytype);
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
				Intent i=new Intent(v.getContext(),FiltrationAct.class);
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
			case R.shoppinglist.rbt_people:
				ordertype = "1";
				break;
//			case R.shoppinglist.rbt_price:
//				ordertype = "2";
//				break;
			case R.shoppinglist.rbt_sale:
				ordertype = "3";
				break;
			}
			listview.reload();
		}
	}
	
	@Override
	public void disposeMsg(int type, Object obj) {
		if(type==1){
			this.btn_business.setText(shangquan==null?"商圈":shangquan);
			fenlei=((Msg_Ccategory)obj).getCategoryname();
			categoryid=((Msg_Ccategory)obj).getCategoryid();
			this.btn_category.setText(fenlei);
		}
		if(type==2){
			this.btn_category.setText(fenlei==null?"分类":fenlei);
			shangquan=((Msg_Ccategory)obj).getCategoryname();
			category4areaid=((Msg_Ccategory)obj).getCategoryid();
			this.btn_business.setText(shangquan);
		}
		if(type==3){
			filt=(FiltrationParam)obj;
		}
		if(type==37){
			Intent intent=(Intent) obj;
			setSearch(intent);
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
		if (son.build != null && son.mgetmethod.equals("slist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem= builder.getCitemList();
			SLAdapter = new ShoppingListAdapter2_YiDong(Yidong_Act.this,list_citem);
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
		this.loadData(new Updateone[] { new Updateone("SLIST",
				new String[][] {
								{"keywords",keywords==null?"":keywords},
								{"category4selfid","3407"},
								{"page_per",F.Per_Page+""},
								{"page",this.mPage+""},
								{"businessid","11"},
								{"orderby",(ordertype!=null&&ordertype.equals("2"))?"asc":"desc"},
								{"hprice",filt==null?"":filt.maxPrice},
								{"lprice",filt==null?"":filt.minPrice},
								{"hcount",filt==null?"0":(filt.haveAgio?"1":"0")},
								{"freight",filt==null?"0":(filt.payLate?"1":"0")},
								{"ordertype",ordertype==null?"1":ordertype}}), });
	}
	
}
