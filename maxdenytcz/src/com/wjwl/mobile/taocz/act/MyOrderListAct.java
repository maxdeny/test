package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyOrderListAct extends MActivity {
	private ListView lv;
	private List<Msg_Ccategory> list_ccategory;
	private ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	private PullReloadView prv;
	private Button bt_back;
	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.myorderlist);
		setId("MyOrderListAct");
		bt_back = (Button) findViewById(R.myorderlist.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyOrderListAct.this.finish();
			}
		});
		prv=(PullReloadView)findViewById(R.myorderlist.pullReloadView);
		lv = (ListView) this.findViewById(R.myorderlist.list);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i=new Intent();
				switch(position){
				case 0:
					i.putExtra("type", "1");
					i.setClass(MyOrderListAct.this, MyOrderDetailsAct.class);
					break;
				case 1:
					i.putExtra("type", "2");
					i.setClass(MyOrderListAct.this, MyOrderLifeDetailsAct.class);
					break;
				case 2:
					i.putExtra("type", "3");
					i.setClass(MyOrderListAct.this, MyOrderReservationDetailsAct.class);
					break;
				}
				startActivity(i);
			}
		});
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mcategory")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ordertype",list_ccategory.get(i).getCategoryname());
				map.put("orderinfo", list_ccategory.get(i).getRemark());
				mData.add(map);
			}
			sa = new SimpleAdapter(
					this,
					mData,
					R.layout.item_myorderlist,
					new String[] { "ordertype",  "orderinfo" },
					new int[] { R.item_myorderlist.ordertype, R.item_myorderlist.orders });
			lv.setAdapter(sa);
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MCATEGORY",
				new String[][] { { "user_id", F.USER_ID } })
//		,new Updateone("MGTCATEGORY",
//						new String[][] { { "user_id", F.USER_ID } })
		});
	}
	
}
