package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.PreferentialActiviesAct.OnCheckClick;
//import com.wjwl.mobile.taocz.adapter.PreferentialActiviesAdapter;
//import com.wjwl.mobile.taocz.adapter.TopListAdapter;
//import com.wjwl.mobile.taocz.adapter.TopSecondAdapter;
//import com.wjwl.mobile.taocz.data.ButtonView;
//
//public class PreferentialSetMealAct extends MActivity {
//	private RadioGroup radiogroup;
//	private PageListView listview;
////	String ordertype = "1";
//	private int mPage = 1;
//	private View norows;
//	private PullReloadView prv;
//	private boolean loaddelay = true;
//	private ArrayList<Map<String, Object>> mData = null;
//	PreferentialActiviesAdapter adp;
//	TextView headtitle;
//	String ordertype="desc",order="current_price";
//	RadioButton rbtn1;
//	
//	ArrayList<ButtonView> buttonListView3 = new ArrayList<ButtonView>(); 
//	private TopListAdapter toplistAdapter = null;
//	private TopSecondAdapter topsecondAdapter = null;
//	ListView pp_listview1,pp_listview2,pp_listview3;
//	PopupWindow pp;
//	View viewpp;
//	Handler hd;
//	Runnable rb;
//	LinearLayout linear,linear2;
//	ArrayList<HashMap<String,String>> data4=new ArrayList<HashMap<String,String>>();
//	HashMap<String,String> map4;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.preferentialactivies);
//		setId("PreferentialSetMealAct");
//		listview = (PageListView) findViewById(R.preferentialactivies.listview);
//		radiogroup = (RadioGroup) findViewById(R.preferentialactivies.radiogroup);
//		headtitle = (TextView) findViewById(R.preferentialactivies.headtitle);
//		headtitle.setText("优惠套餐");
//		norows = findViewById(R.id.norows);
//		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
//		rbtn1=(RadioButton) findViewById(R.preferentialactivies.rbt_people);
//		mData = new ArrayList<Map<String, Object>>();
//		viewpp=findViewById(R.preferentialactivies.view);
//		
//		LayoutInflater flater=LayoutInflater.from(getApplication());
//		View view=flater.inflate(R.layout.pp_content, null);
//		pp_listview1=(ListView) view.findViewById(R.pp.listview1);
//		pp_listview2=(ListView) view.findViewById(R.pp.listview2);
//		pp_listview3=(ListView) view.findViewById(R.pp.listview3);
//		linear=(LinearLayout) view.findViewById(R.pp.linear);
//		linear2=(LinearLayout) view.findViewById(R.pp.linear2);
//		pp_listview1.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
//				toplistAdapter.setSelectedPosition(arg2);
//				toplistAdapter.notifyDataSetInvalidated(); 
//				
//				linear.setVisibility(View.VISIBLE);
//					if(buttonListView3.get(arg2).textViewId==0){
//						pp.dismiss();
//						rbtn1.setText("全城");
//					}
//					else{
//						data4.clear();
//						for(int o=0;o<F.AREACATEGORY.getComment(arg2-1).getCommentCount();o++){
//							map4=new HashMap<String,String>();
//							map4.put("secondmenuname",F.AREACATEGORY.getComment(arg2-1).getComment(o).getCommentcontent());
//							map4.put("secondmenuid",F.AREACATEGORY.getComment(arg2-1).getComment(o).getCommentid());
//							data4.add(map4);
//						}
//						topsecondAdapter=new TopSecondAdapter(PreferentialSetMealAct.this,data4,"");
//						pp_listview2.setAdapter(topsecondAdapter);
//					}
//			}
//		});
//		pp_listview2.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				pp.dismiss();
//					rbtn1.setText(data4.get(arg2).get("secondmenuname"));
////					diqu=data4.get(arg2).get("secondmenuid");
//					ordertype = "desc";
//					order="current_price";
//					dataLoad(null);
//			}
//		});
//		pp = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
//				520, true);
//		pp.setBackgroundDrawable(new BitmapDrawable(getResources()));
//		
//		hd=new Handler();
//		rb=new Runnable() {
//			@Override
//			public void run() {
//				if(pp.isShowing()){
//					
//				}else{
//					viewpp.setVisibility(View.GONE);
//				}
//				hd.postDelayed(rb, 10);
//			}
//		};
//		hd.postDelayed(rb, 10);
//		// listview.setLoadData(new PageRun() {
//		// public void run(int page) {
//		// mPage = page;
//		// if (loaddelay) {
//		// dataLoadByDelay(null);
//		// loaddelay = false;
//		// } else {
//		// dataLoad();
//		// }
//		// }
//		// });
//		//
//		// listview.setLoadView(new FootLoadingShow(this));
//		// listview.start(1);
//		// prv = (PullReloadView)
//		// findViewById(R.preferentialactivies.pullReloadView);
//		// prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//		// public void onRefresh() {
//		// listview.reload();
//		// }
//		// });
//		dataLoad(null);
//		rbtn1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				buttonListView3.clear();
//				ButtonView a1 = new ButtonView(0,"全城","");
//				buttonListView3.add(a1);
//				for(int i=0;i<F.AREACATEGORY.getCommentList().size();i++){
//					ButtonView a = new ButtonView(Integer.parseInt(F.AREACATEGORY.getComment(i).getCommentid()),
//							F.AREACATEGORY.getComment(i).getCommentcontent(),"");
//					buttonListView3.add(a);
//				}
//				toplistAdapter = new TopListAdapter(PreferentialSetMealAct.this,buttonListView3,"");
//				pp_listview1.setAdapter(toplistAdapter);
//				
//				pp.showAsDropDown(rbtn1,0,0);
//				pp_listview1.setVisibility(View.VISIBLE);
//				linear.setVisibility(View.GONE);
//				linear2.setVisibility(View.GONE);
//				viewpp.setVisibility(View.VISIBLE);
//			}
//		});
//	}
//
//	class OnCheckClick implements OnCheckedChangeListener {
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			LoadShow = true;
//			switch (checkedId) {
//			case R.preferentialactivies.rbt_people:
//				
//				break;
//			case R.preferentialactivies.rbt_price:
//				ordertype = "asc";
//				order="current_price";
//				dataLoad(null);
//				break;
//			case R.preferentialactivies.rbt_sale:
//				ordertype = "desc";
//				order="buy_count";
//				dataLoad(null);
//				break;
//			}
//			listview.reload();
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		 if(son.build==null){
//			 if(mPage==1){
//				 norows.setVisibility(View.VISIBLE);
//				 listview.setAdapter(null);
//			 }
//			 listview.endPage();
//		 }
//		 if (son.build != null && son.mgetmethod.equals("yyyhtclist")) {
//			 Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			 List<Msg_Citem> list_citem= builder.getCitemList();
//			 if(mData.size()>0){
//				 mData.clear();
//			 }
//			 for(int i=0;i<list_citem.size();i++){
//				 	Map<String, Object> map = new HashMap<String, Object>();
//					map.put("oldprice",list_citem.get(i).getItemprice());
//					map.put("newprice", list_citem.get(i).getItemdiscount());
//					map.put("title",list_citem.get(i).getItemtitle());
//					map.put("buynums",list_citem.get(i).getItemsold());
//					map.put("area", list_citem.get(i).getOther2());
//					map.put("itemid", list_citem.get(i).getItemid());
//					map.put("pic", list_citem.get(i).getItemimageurl());
//					mData.add(map);
//			 }
//			 adp = new PreferentialActiviesAdapter(PreferentialSetMealAct.this,
//						mData);
//			 listview.setAdapter(adp);
//		 
//			 if(list_citem.size()<F.Per_Page){
//				 listview.endPage();
//			 }
//		 	norows.setVisibility(View.INVISIBLE);
//		 }
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		//http://life.taocz.com/life.php?app=citemlist&ac
//			//t=yyyhtclist&debug=1&ordertype=desc&order=current_price&page=1&perpage=10&categoryareaid=10
//		 this.loadData(new Updateone[] { new Updateone("YYYHTCLIST",
//		 new String[][] {
//		 {"perpage",10+""},//F.Per_Page
//		 {"page",this.mPage+""},
//		 {"ordertype",ordertype},//ordertype!=null?"asc":"desc"
//		 {"categoryareaid","10"},
//		 {"order",order}}), });//（价格current_price、销量buy_count）//ordertype==null?"current_price":"buy_count"
//	}
//}