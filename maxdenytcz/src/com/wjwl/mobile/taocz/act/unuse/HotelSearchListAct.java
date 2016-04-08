package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.HotelSearchListAdapter;
//import com.wjwl.mobile.taocz.widget.Item_Search;
//
//public class HotelSearchListAct extends MActivity {
//
//	private PageListView listview;
//	private Button btn_map, btn_sort, btn_select;
//	HotelSearchListAdapter SLAdapter;
//	boolean isEdit = true;
//	String categoryid;
//	List<Msg_Citem> list_citem;
//	String ordertype="1";
//	String fenlei;
//	String shangquan;
//	String categoryname;
//	private PullReloadView prv;
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.hotelsearchlist);
//		setId("HotelSearchListAct");
//		listview = (PageListView) findViewById(R.hotelsearchlist.listview);
//		btn_map = (Button) findViewById(R.hotelsearchlist.btn_map);
//		btn_sort = (Button) findViewById(R.hotelsearchlist.btn_sort);
//		btn_select = (Button) findViewById(R.hotelsearchlist.btn_select);
//		btn_map.setOnClickListener(new OnClick());
//		btn_sort.setOnClickListener(new OnClick());
//		btn_select.setOnClickListener(new OnClick());
//		Item_Search item_search = (Item_Search)findViewById(R.hotelsearchlist.item_search);
//		item_search.setSearchDefault(getIntent().getIntExtra("searchPupub", -1));
//		prv=(PullReloadView) findViewById(R.hotelsearchlist.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		dataLoad(null);
//	}
//
//	class OnClick implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if (btn_map.equals(v)) {
//				Intent intent = new Intent(getApplication(),
//						BusinessHotelMapAct.class);
//				intent.putExtra("isList", true);
//				//startActivity(intent);
//				v.getContext().startActivity(intent);
//			} else if (btn_sort.equals(v)) {
//				dataLoadByDelay(null, 500);
//			} else if (btn_select.equals(v)) {
//				dataLoadByDelay(null, 500);
//			}
//		}
//	}
//	
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		
//	}
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("HLIST")) {
//			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			list_citem = builder.getCitemList();
//			SLAdapter = new HotelSearchListAdapter(HotelSearchListAct.this,list_citem);
//			listview.setAdapter(SLAdapter);
//			listview.setOnItemClickListener(new OnItemClickListener(){
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					// TODO Auto-generated method stub
//					Intent i=new Intent();
//					i.putExtra("itemid",list_citem.get(arg2).getItemid() );
//					i.setClass(getApplicationContext(), HotelContentAct.class);
//					startActivity(i);
//				}});
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("HLIST",
//				new String[][] {}, Msg_CitemList.newBuilder()), });
//	}
//	
//}