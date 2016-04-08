package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PageListView.PageRun;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;
import com.wjwl.mobile.taocz.adapter.MyTxAdapter;
import com.wjwl.mobile.taocz.adapter.MyTxUAdapter;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TaoxinCard_Act extends MActivity {
	TczV3_HeadLayout headlayout;
	RadioGroup group;
	RadioButton mycards, usedcard;
	PageListView listview, listview1;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> map;
	boolean loaddelay = true;
	int mPage = 1;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.taoxincard);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("淘心卡");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TaoxinCard_Act.this.finish();
			}
		});
		group = (RadioGroup) findViewById(R.taoxincard.radiogroup);
		mycards = (RadioButton) findViewById(R.taoxincard.mycards);
		usedcard = (RadioButton) findViewById(R.taoxincard.usedcard);
		listview = (PageListView) findViewById(R.taoxincard.listview);
		listview1 = (PageListView) findViewById(R.taoxincard.listview1);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.taoxincard.mycards:
					// dataLoad(new int[]{1});
					listview.reload();
					listview.setVisibility(View.VISIBLE);
					listview1.setVisibility(View.GONE);
					break;
				case R.taoxincard.usedcard:
					// dataLoad(new int[]{2});
					listview1.reload();
					listview.setVisibility(View.GONE);
					listview1.setVisibility(View.VISIBLE);
					break;

				default:
					break;
				}
			}
		});
		listview.setLoadData(new PageRun() {
			public void run(int page) {
				mPage = page;
				if (loaddelay) {
					dataLoadByDelay(null);
					loaddelay = false;
				} else {
					dataLoad();
				}
			}
		});
		listview.setLoadView(new FootLoadingShow(this));
		listview.start(1);
		// dataLoad(new int[]{1});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mytxklist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			// data.clear();
			// for(int i=0;i<builder.getItemBuilderList().size();i++){
			// map=new HashMap<String,Object>();
			// map.put("card_number", builder.getItem(i).getProductname());
			// map.put("money", builder.getItem(i).getTotal());
			// map.put("time", builder.getItem(i).getPaytime());
			// map.put("ramainde",builder.getItem(i).getItemcount());
			// data.add(map);
			// }
			// listview.setAdapter(new
			// MyAdapter(TaoxinCard_Act.this,data,"TaoxinCard_Act"));
			List<Msg_Morder_Item> list = builder.getItemList();
			listview.addData(new MyTxAdapter(this, list));
			if (list.size() < 10) {
				listview.endPage();
			}
		}
		if (son.build != null && son.mgetmethod.equals("mytxkhilist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			// data.clear();
			// for(int i=0;i<builder.getItemBuilderList().size();i++){
			// map=new HashMap<String,Object>();
			// map.put("card_number", builder.getItem(i).getProductname());
			// map.put("money", builder.getItem(i).getItemcount());
			// map.put("time", builder.getItem(i).getPaytime());
			// map.put("ramainde",builder.getItem(i).getTotal());
			// map.put("orderno",builder.getItem(i).getName());
			// data.add(map);
			// }
			// listview1.setAdapter(new
			// MyAdapter(TaoxinCard_Act.this,data,"TaoxinCard_Act"));
			List<Msg_Morder_Item> list = builder.getItemList();
			listview1.addData(new MyTxUAdapter(this, list));
			if (list.size() < 10) {
				listview1.endPage();
			}
		}
	}

	public void dataLoad(int[] types) {
		// if(types[0]==1){
		this.loadData(new Updateone[] { new Updateone("MYTXKLIST",
				new String[][] { { "userid", F.USER_ID }, { "perpage", "10" },
						{ "page", mPage + "" } }), });
		// }
		// if(types[0]==2){
		this.loadData(new Updateone[] { new Updateone("MYTXKHISLIST",
				new String[][] { { "userid", F.USER_ID }, { "perpage", "10" },
						{ "page", mPage + "" } }), });
		// }

	}
}
