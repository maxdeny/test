package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.RadioGroup;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PageListView;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.FiltrationAct.FiltrationParam;
//import com.wjwl.mobile.taocz.adapter.ShoppingListAdapter;
//
//public class TakeOutAct extends MActivity {
//	private PageListView listview;
//	private RadioGroup radiogroup;
//	private Button btn_category, btn_business, btn_select;
//	ShoppingListAdapter SLAdapter;
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
//		setContentView(R.layout.shoppinglist);
//		setId("TakeOutAct");
//		listview = (PageListView) findViewById(R.shoppinglist.listview);
//		radiogroup = (RadioGroup) findViewById(R.shoppinglist.radiogroup);
//		btn_category = (Button) findViewById(R.shoppinglist.btn_category);
//		btn_business = (Button) findViewById(R.shoppinglist.btn_business);
//		btn_select = (Button) findViewById(R.shoppinglist.btn_select);
//		btn_category.setOnClickListener(new OnClick());
//		btn_business.setOnClickListener(new OnClick());
//		btn_select.setOnClickListener(new OnClick());
//		
//		btn_category.setText(getIntent().getCharSequenceExtra("title"));
//		radiogroup.setOnCheckedChangeListener(new OnCheckClick());
//		categoryid=getIntent().getStringExtra("categoryid");
//		fenlei=getIntent().getStringExtra("title");
//		prv=(PullReloadView) findViewById(R.shoppinglist.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		dataLoad(null);
//	}
//	
//	
//
//	class OnClick implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			if (btn_category.equals(v)) {
//				Intent intent = new Intent(v.getContext(),
//						CategoryFirstAct.class);
//				intent.putExtra("isSelect", true);
//				intent.putExtra("callback",true);
//				intent.putExtra("title", "外卖");
//				if(Frame.HANDLES.get("CategoryFirstAct").size()>0)
//					Frame.HANDLES.closeOne("CategoryFirstAct");
//				v.getContext().startActivity(intent);
//			} else if (btn_business.equals(v)) {
//				Intent i=new Intent(v.getContext(),BusinessGroupAllAct.class);
//				i.putExtra("navtype", "takeout");
//				v.getContext().startActivity(i);
//			} else if (btn_select.equals(v)) {
//				Intent i=new Intent(v.getContext(),FiltrationAct.class);
//				i.putExtra("navtype", "takeout");
//				v.getContext().startActivity(i);
//			}
//		}
//
//	}
//	
//	class OnCheckClick implements OnCheckedChangeListener{
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			// TODO Auto-generated method stub
//			switch (checkedId) {
//			case R.shoppinglist.rbt_people:
//				ordertype="1";
//				dataLoad(null);
//				break;
//			case R.shoppinglist.rbt_price:
//				ordertype="2";
//				dataLoad(null);
//				break;
//			case R.shoppinglist.rbt_sale:
//				ordertype="3";
//				dataLoad(null);
//				break;
//			}
//		}
//	}
//	
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		if(type==1){
//			this.btn_category.setText(obj.toString());
//			fenlei=obj.toString();
//			this.btn_business.setText(shangquan==null?"商圈":shangquan);
//		}
//		if(type==2){
//			this.btn_category.setText(fenlei==null?"分类":fenlei);
//			shangquan=obj.toString();
//			this.btn_business.setText(obj.toString());
//		}
//		if(type==3){
//			FiltrationParam filt=(FiltrationParam)obj;
//		}
//		dataLoadByDelay(null);
//	}
//
//	
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("WLIST")) {
//			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			list_citem = builder.getCitemList();
//			SLAdapter = new ShoppingListAdapter(TakeOutAct.this,list_citem);
//			listview.setAdapter(SLAdapter);
//			listview.setOnItemClickListener(new OnItemClickListener(){
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					// TODO Auto-generated method stub
//					Intent i=new Intent();
//					i.putExtra("itemid",list_citem.get(arg2).getItemid() );
//					i.setClass(getApplicationContext(), TakeOutContentAct.class);
//					startActivity(i);
//				}});
//		}
//		prv.refreshComplete();
//	}
//
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WLIST",
//				new String[][] {{"categoryid",categoryid},{"ordertype",ordertype}}), });
//	}
//	
//	
//}
