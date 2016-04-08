package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.List;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.AdapterView.OnItemClickListener;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.mdx.mobile.widget.PullReloadView;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.MyorderListAdapter;
//
//public class MyOrderUnfinishAct extends MActivity {
//
//	private ListView listview;
//	ArrayAdapter<String> scopeAdapter;
//	ArrayAdapter<String> typeAdapter;
//	ArrayAdapter<String> sortAdapter;
//	private PullReloadView prv;
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.item_myorder);
//		listview = (ListView) findViewById(R.favorite.shop_listview);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//			}
//		});
//		prv = (PullReloadView) findViewById(R.favorite.pullReloadView);
//		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
//			public void onRefresh() {
//				dataLoad(null);
//			}
//		});
//		dataLoad(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("MYORDERLIST")) {
//			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
//			List<Msg_Citem> list = new ArrayList<Msg_Citem>();
//			list = builder.getCitemList();
//			listview.setAdapter(new MyorderListAdapter(MyOrderUnfinishAct.this,
//					list));
//		}
//		prv.refreshComplete();
//	}
//
//	@Override
//	public void dataLoad(int[] typs) {
//		this.loadData(new Updateone[] { new Updateone("MYORDERLIST",
//				new String[][] { { "categoryid", "1" }, { "username", "zk" } },
//				Msg_CitemList.newBuilder()), });
//	}
//
//}
