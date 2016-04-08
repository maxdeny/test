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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;

public class CategoryThirdAct extends MActivity {
	TextView head_title;
	String str_head_title;
	String navigation;
	ListView lv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	List<Msg_Ccategory> list_ccategory;
	String categorysubid;
	String categoryid;
	private PullReloadView prv;
	private Button bt_back;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		setId("CategorySecondAct");
		head_title = (TextView) this.findViewById(R.merchant.head_title);
		Intent intent = this.getIntent();
		navigation = intent.getStringExtra("navigation");
		str_head_title = intent.getStringExtra("title");
		if (str_head_title.equals(getResources().getString(R.string.conser)))
			head_title.setText("选择服务项目");
		else
			head_title.setText(str_head_title);
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
		mData = new ArrayList<Map<String, Object>>();
		categoryid = intent.getStringExtra("categoryparentid");
		bt_back = (Button) findViewById(R.merchant.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CategoryThirdAct.this.finish();
			}
		});
		prv = (PullReloadView) findViewById(R.merchant.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoadByDelay(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("categoryThird", "全部");
			map1.put("categoryparentid3", categoryid);
			mData.add(map1);
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("categoryThird", list_ccategory.get(i)
						.getCategoryname());
				map.put("categoryparentid3", list_ccategory.get(i)
						.getCategoryid());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData,
					R.layout.item_com_merchant_list,
					new String[] { "categoryThird" },
					new int[] { R.item_merchant.text });
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (getIntent().getBooleanExtra("isSelect", false)) {
						if(Frame.HANDLES.get("ShoppingListAct").size()!=0){
							Frame.HANDLES.get("ShoppingListAct").get(0)
							.sent(1, mData.get(arg2));
						}
						
						Frame.HANDLES.close("CategoryFirstAct");
						Frame.HANDLES.close("CategorySecondAct");
					} else {
						Intent intent = new Intent(CategoryThirdAct.this,
								ShoppingListAct.class);
						intent.putExtra("searchPupub",
								getIntent().getIntExtra("searchPupub", -1));
						if (arg2 == 0)
							intent.putExtra("category", str_head_title);
						else
							intent.putExtra("category", (String) mData
									.get(arg2).get("categoryThird"));
						intent.putExtra("categoryid", (String) mData.get(arg2)
								.get("categoryparentid3"));
						startActivity(intent);
					}

				}
			});
			prv.refreshComplete();
		}

	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("SCATEGORY",
				new String[][] { { "categoryid", categoryid } }), });

	}
}
