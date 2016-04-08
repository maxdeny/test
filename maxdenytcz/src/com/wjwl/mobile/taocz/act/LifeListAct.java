package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import com.wjwl.mobile.taocz.act.Lihua_Act.OnClick;
import com.wjwl.mobile.taocz.adapter.LifeListAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.Item_Search;

public class LifeListAct extends MActivity {

	private PageListView listview;
	private RadioGroup radiogroup;
	private Button btn_category, btn_business, btn_select;
	LifeListAdapter LLAdapter;
	boolean isEdit = true;
	String categoryid,keywords;
	List<Msg_Citem> list_citem;
	String ordertype="1";
	String fenlei;
	String shangquan,category4areaid="";
	String categoryname;
	private PullReloadView prv;
	private Item_Search item_search;
	private View norows;
	private FiltrationParam filt=null;
	private int mPage=1;
	private boolean loaddelay=true;
    Button bt_price;
	private String orderby = "asc";
    RadioButton rbt_1,rbt_2;
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.shoppinglist);
		setId("LifeListAct");
		rbt_1=(RadioButton)findViewById(R.shoppinglist.rbt_people);
		rbt_2=(RadioButton)findViewById(R.shoppinglist.rbt_sale);
		listview = (PageListView) findViewById(R.shoppinglist.listview);
		radiogroup = (RadioGroup) findViewById(R.shoppinglist.radiogroup);
		btn_category = (Button) findViewById(R.shoppinglist.btn_category);
		btn_business = (Button) findViewById(R.shoppinglist.btn_business);
		btn_select = (Button) findViewById(R.shoppinglist.btn_select);
		bt_price=(Button)findViewById(R.shoppinglist.price);
		bt_price.setOnClickListener(new OnClick());
		btn_category.setOnClickListener(new OnClick());
		btn_business.setOnClickListener(new OnClick());
		btn_select.setOnClickListener(new OnClick());
		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
//		item_search = (Item_Search)findViewById(R.shoppinglist.item_search);
//		item_search.setSearchDefault(getIntent().getIntExtra("searchPupub", 1));
		norows=findViewById(R.id.norows);

		if(getIntent().getCharSequenceExtra("category")!=null){
			btn_category.setText(getIntent().getCharSequenceExtra("category"));
		}
		
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
				intent.putExtra("title", "生活");
				if(Frame.HANDLES.get("CategoryFirstAct").size()>0)
					Frame.HANDLES.closeOne("CategoryFirstAct");
				if(Frame.HANDLES.get("CategorySecondAct").size()>0)
					Frame.HANDLES.closeOne("CategorySecondAct");
				v.getContext().startActivity(intent);
			} else if (btn_business.equals(v)) {
				Intent i=new Intent(v.getContext(),BusinessGroupAllAct.class);
				i.putExtra("navtype", "life");
				v.getContext().startActivity(i);
			} else if (btn_select.equals(v)) {
				Intent i=new Intent(v.getContext(),FiltrationAct.class);
				i.putExtra("navtype", "life");
				if(filt!=null){
					i.putExtra("filter", filt);
				}
				v.getContext().startActivity(i);
			}else if (bt_price.equals(v)) {
				rbt_1.setChecked(false);
				rbt_2.setChecked(false);
				LoadShow = true;
				if (orderby.equals("asc"))
					orderby = "desc";
				else
					orderby = "asc";
				ordertype = "2";
				listview.reload();
			}
		}

	}
	
	class OnCheckClick implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			LoadShow = true;
			switch (checkedId) {
			case R.shoppinglist.rbt_people:
				ordertype="1";
				break;
//			case R.shoppinglist.rbt_price:
//				ordertype="2";
//				break;
			case R.shoppinglist.rbt_sale:
				ordertype="3";
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
		if(type==4){
			this.btn_category.setText(fenlei==null?"分类":fenlei);
			shangquan=((String[])obj)[0];
			category4areaid=((String[])obj)[1];
			this.btn_business.setText(shangquan);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==37){
			if(resultCode==RESULT_OK){
				String type=data.getStringExtra("type");
				String text=data.getStringExtra("text");
				item_search.set(text, type);
			}
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
		if (son.build != null && son.mgetmethod.equals("llist")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list_citem = builder.getCitemList();
			LLAdapter = new LifeListAdapter(LifeListAct.this,list_citem);
			listview.addData(LLAdapter);
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

	//http://api.taocz.com/mobile/tao.php?app=citemlist&act=llist&ordertype=1&orderby=desc&debug=1&page_per=20&page=20&keywords=&hprice=&lprice=&hcount=0&freight=0&category4selfid=&category4areaid=
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("LLIST",
				new String[][] {{"keywords",keywords==null?"":keywords},
								{"category4selfid",categoryid==null?"":categoryid},
								{"category4areaid",category4areaid},
								{"page_per",F.Per_Page+""},
								{"page",this.mPage+""},
								{"orderby",orderby},
								{"hprice",filt==null?"":filt.maxPrice},
								{"lprice",filt==null?"":filt.minPrice},
								{"hcount","0"},
								{"freight","0"},
								{"ordertype",ordertype==null?"1":ordertype}}), });
	}
	
}