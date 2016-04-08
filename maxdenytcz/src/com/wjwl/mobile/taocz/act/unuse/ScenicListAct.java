package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ScenicLsitAdapter;
//
//public class ScenicListAct extends MActivity {
//	ListView lv;
//	ArrayList<Map<String, Object>> mData = null;
//	private ScenicLsitAdapter item_sceniclist;
//	private EditText ed_name;
//	private Button bt_1, bt_2, bt_search;
//	private PullReloadView prv;
//	List<Msg_Citem> list_citem;
//	TextView headtitle;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.sceniclist);
//		Intent i=getIntent();
//		headtitle=(TextView)findViewById(R.sceniclist.headtitle);
//		headtitle.setText(i.getStringExtra("title"));
//		ed_name = (EditText) findViewById(R.sceniclist.ed_name);
//		bt_search = (Button) findViewById(R.sceniclist.bt_search);
//		bt_1 = (Button) findViewById(R.sceniclist.bt_1);
//		bt_2 = (Button) findViewById(R.sceniclist.bt_2);
//		lv = (ListView) this.findViewById(R.sceniclist.listview);
//		prv=(PullReloadView) findViewById(R.sceniclist.pullReloadView);
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
//			switch (v.getId()) {
//			case R.sceniclist.bt_1:
//				break;
//			case R.sceniclist.bt_2:
//				break;
//			case R.sceniclist.bt_search:
//				break;
//			}
//		}
//	}
//	@Override
//	public void disposeMsg(int type, Object obj) {
//		if(type==1){
//
//		}
//		if(type==2){
//
//		}
//		prv.refreshComplete();
//	}
//
//	
//	
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("TLIST")) {
//			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			list_citem = builder.getCitemList();
//			item_sceniclist = new ScenicLsitAdapter(getApplication(),list_citem);
//			lv.setAdapter(item_sceniclist);
//			lv.setOnItemClickListener(new OnItemClickListener(){
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//						long arg3) {
//					// TODO Auto-generated method stub
////					Intent i=new Intent();
////					i.putExtra("itemid",list_citem.get(arg2).getItemid() );
////					i.setClass(getApplicationContext(), ShoppingContentAct.class);
////					startActivity(i);
//				}});
//		}
//		prv.refreshComplete();
//	}
//
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("TLIST",
//				new String[][] {{"distance","2000"},{"ordertype","1"},{"city",headtitle.getText().toString()}}), });
//	}
//	
//	
//}
