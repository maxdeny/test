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

public class BusinessGroupAreaAct extends MActivity {
	TextView head_title;
	ListView lv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	List<Msg_Ccategory> list_ccategory;
//	String[] category = { "商家名称一", "商家名称二", "商家名称三", "商家名称四", "商家名称五", "商家名称六",
//			"商家名称七" };
	private String str_head_title;
	String categoryid,navtype,title;
	private PullReloadView prv;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		setId("BusinessGroupAreaAct");
		head_title = (TextView) this.findViewById(R.merchant.head_title);
		Intent intent = this.getIntent();
		str_head_title = intent.getStringExtra("title");
		navtype = intent.getStringExtra("navtype");
		head_title.setText(str_head_title);
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
		categoryid= intent.getStringExtra("categoryparentid");
		title=intent.getStringExtra("title");
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
		if(son.build==null){
			Frame.HANDLES.get("LifeListAct").get(0).sent(4, new String[]{title,categoryid});
			Frame.HANDLES.closeOne("BusinessGroupAllAct");
			Frame.HANDLES.closeOne("BusinessGroupAreaAct");
		}
		if (son.build != null && son.mgetmethod.equals("carea")) {
		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
		list_ccategory = builder.getCcategoryList();
		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list_ccategory.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessGroupAreaAct", list_ccategory.get(i).getCategoryname());
			mData.add(map);
		}
		sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
				new String[] { "businessGroupAreaAct" },
				new int[] { R.item_merchant.text });
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(navtype.equals("shop")){
					Frame.HANDLES.get("ShoppingListAct").get(0).sent(2, list_ccategory.get(arg2));
					Frame.HANDLES.closeOne("BusinessGroupAllAct");
					Frame.HANDLES.closeOne("BusinessGroupAreaAct");
				}
				if(navtype.equals("life")){
					Frame.HANDLES.get("LifeListAct").get(0).sent(2, list_ccategory.get(arg2));
					Frame.HANDLES.closeOne("BusinessGroupAllAct");
					Frame.HANDLES.closeOne("BusinessGroupAreaAct");
				}
				if(navtype.equals("takeout")){
					Frame.HANDLES.get("TakeOutAct").get(0).sent(2, list_ccategory.get(arg2));
					Frame.HANDLES.closeOne("BusinessGroupAllAct");
					Frame.HANDLES.closeOne("BusinessGroupAreaAct");
				}
					
			}
		});
		}
		prv.refreshComplete();
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("CAREA",
				new String[][] {{"categoryid",categoryid}}), });
	}
	
	
}
