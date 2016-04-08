package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.wjwl.mobile.taocz.adapter.MyAdapter1;
import com.wjwl.mobile.taocz.widget.FootLoadingShow;

public class TuangouOrderAct extends MActivity{
	Button back;
	TextView text;
	PageListView listview;
	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
	HashMap<String,Object> map;
	private int mPage=1;
	private boolean loaddelay=true;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tuangouorder);
		back=(Button) findViewById(R.tuangouorder.back);
		text=(TextView) findViewById(R.tuangouorder.text);
		listview=(PageListView) findViewById(R.tuangouorder.listview);
		
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
	}

	
	@Override
	public void disposeMessage(Son son) throws Exception {
		if(son.build==null){
			if(mPage==1){
//				norows.setVisibility(View.VISIBLE);
				listview.setAdapter(null);
			}
			listview.endPage();
		}
		if (son.build != null && son.mgetmethod.equals("tgorderlist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			List<Msg_Morder_Item> datas=builder.getItemList();
//			{
//				for(int i=0;i<datas.size();i++){
//					Msg_Morder_Item item=datas.get(i);
//					map=new HashMap<String,Object>();
//					map.put("name", item.getProductname());
//					map.put("date", item.getPaytime());
//					map.put("ordernumber",item.getLevel());
//					map.put("numbers", item.getItemcount());
//					map.put("money",item.getTotal());
//					map.put("state", item.getBusinessstate());
//					data.add(map);
//				}
//				listview.setAdapter(new MyAdapter(TuangouOrderAct.this,data,"TuangouOrderAct"));
			listview.addData(new MyAdapter1(TuangouOrderAct.this, datas));
				if(datas.size()<10){
					listview.endPage();
				}
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("TGORDERLIST",//测试id44
				new String[][] { { "userid",F.USER_ID},{ "perpage", 10+"" },
				{ "page", mPage+"" }}), });
	}
}
