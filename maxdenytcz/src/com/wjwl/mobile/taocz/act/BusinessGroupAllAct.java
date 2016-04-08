package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;

public class BusinessGroupAllAct extends MActivity {
	TextView head_title;
	ListView lv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	List<Msg_Ccategory> list_ccategory;
	String navtype,title,categoryparentid;
	private PullReloadView prv;
//	String[] category = { "天宁区", "钟楼区", "新区", "武进区", "戚墅堰区", "溧阳", "金坛" };

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		setId("BusinessGroupAllAct");
		head_title = (TextView) this.findViewById(R.merchant.head_title);
		head_title.setText(getApplication().getResources().getString(
				R.string.head_allbusinessgroup));
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
		navtype=getIntent().getStringExtra("navtype");
		prv=(PullReloadView) findViewById(R.merchant.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("carea")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i <list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("businessGroupAllAct", list_ccategory.get(i).getCategoryname());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
					new String[] { "businessGroupAllAct" },
					new int[] { R.item_merchant.text });
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					title=list_ccategory.get(arg2).getCategoryname();
					categoryparentid=list_ccategory.get(arg2).getCategoryid();
					Intent i=new Intent();
					i.putExtra("title",title);
					i.putExtra("categoryparentid",categoryparentid );
					if(list_ccategory.get(arg2).getCategoryno().equals("")){
						Frame.HANDLES.get("LifeListAct").get(0).sent(4, new String[]{title,categoryparentid});
						Frame.HANDLES.closeOne("BusinessGroupAllAct");
					}
					else{
						i.putExtra("navtype",navtype );
						i.setClass(getApplicationContext(), BusinessGroupAreaAct.class);
						startActivity(i);
						
					}
				}
			});
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("CAREA",
				new String[][] {{"categoryid","530"}}), });
	}

	
}
