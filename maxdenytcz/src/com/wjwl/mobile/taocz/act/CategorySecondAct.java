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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;

public class CategorySecondAct extends MActivity {
	TextView head_title;
	private String str_head_title;
	private String navigation;
	private ListView lv;
	private ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	private List<Msg_Ccategory> list_ccategory;
	// private String categorysubid;
	private String categoryid;
	private PullReloadView prv;
	private String name_category;
	private Button bt_back;

	// String[] category = { "茅台老窖","真果粒橙","雪碧", "可乐", "百事可乐", "青岛啤酒", "哈尔滨啤酒"
	// };
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		setId("CategorySecondAct");
		head_title = (TextView) this.findViewById(R.merchant.head_title);
		Intent intent = this.getIntent();
		navigation = intent.getStringExtra("navigation");
		str_head_title = intent.getStringExtra("title");
		bt_back = (Button) findViewById(R.merchant.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CategorySecondAct.this.finish();
			}
		});
		if (str_head_title.equals(getResources().getString(R.string.conser))) {
			head_title.setText("选择服务项目");
			name_category = intent.getStringExtra("name_category");
		} else
			head_title.setText(str_head_title);
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
		mData = new ArrayList<Map<String, Object>>();
		categoryid = intent.getStringExtra("categoryparentid");
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
			if (navigation.equals(getResources().getString(R.string.shop))) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("categorySecond", "全部");
				map.put("categoryparentid2", categoryid);
				mData.add(map);
			}
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("categorySecond", list_ccategory.get(i)
						.getCategoryname());
				map.put("categoryparentid2", list_ccategory.get(i)
						.getCategoryid());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData,
					R.layout.item_com_merchant_list,
					new String[] { "categorySecond" },
					new int[] { R.item_merchant.text });
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					if (navigation != null
							&& navigation.equals(getResources().getString(
									R.string.conser))) {
						Intent intent = new Intent();
						intent.putExtra("categoryparentid1", categoryid);
						intent.putExtra("name_category1", name_category);
						intent.putExtra("categoryparentid2", list_ccategory
								.get(arg2).getCategoryid());
						intent.putExtra("name_category2",
								list_ccategory.get(arg2).getCategoryname());
						intent.setClass(CategorySecondAct.this,
								ServiceReservationAct.class);
						startActivity(intent);
					} else if (navigation != null
							&& navigation.equals(getResources().getString(
									R.string.shop))) {
						// if (getIntent().getBooleanExtra("isSelect", false)) {
						// Frame.HANDLES
						// .get("ShoppingListAct")
						// .get(0)
						// .sent(1,
						// list_ccategory.get(arg2));
						// Frame.HANDLES.closeOne("CategoryFirstAct");
						// Frame.HANDLES.closeOne("CategorySecondAct");
						// } else {
						// Intent intent = new Intent(CategorySecondAct.this,
						// ShoppingListAct.class);
						// intent.putExtra("searchPupub",
						// getIntent().getIntExtra("searchPupub", -1));
						// intent.putExtra("category", list_ccategory.get(arg2)
						// .getCategoryname());
						// intent.putExtra("categoryid",
						// list_ccategory.get(arg2)
						// .getCategoryid());
						// startActivity(intent);
						// }
						Intent i = new Intent();
						if (arg2 == 0) {// 全部
							i.putExtra("searchPupub",
									getIntent().getIntExtra("searchPupub", -1));
							i.putExtra("category", str_head_title);
							i.putExtra("categoryid", (String) mData.get(arg2)
									.get("categoryparentid2"));
							i.setClass(getApplication(), ShoppingListAct.class);
						} else {
							i.putExtra("title", (String) mData.get(arg2)
									.get("categorySecond"));
							i.putExtra("navigation", str_head_title);
							i.putExtra("searchPupub",
									getIntent().getIntExtra("searchPupub", -1));
							i.putExtra(
									"isSelect",
									getIntent().getBooleanExtra("isSelect",
											false));
							i.putExtra(
									"categoryparentid",
									(String) mData.get(arg2).get(
											"categoryparentid2"));
							i.setClass(getApplicationContext(),
									CategoryThirdAct.class);
						}
						startActivity(i);
					} else if (navigation != null
							&& navigation.equals(getResources().getString(
									R.string.life))) {
						if (getIntent().getBooleanExtra("isSelect", false)) {
							Frame.HANDLES.get("LifeListAct").get(0)
									.sent(1, list_ccategory.get(arg2));
							Frame.HANDLES.closeOne("CategoryFirstAct");
							Frame.HANDLES.closeOne("CategorySecondAct");
						} else {
							Intent intent = new Intent(CategorySecondAct.this,
									LifeListAct.class);
							intent.putExtra("searchPupub", getIntent()
									.getIntExtra("searchPupub", -1));
							intent.putExtra("category", list_ccategory
									.get(arg2).getCategoryname());
							intent.putExtra("categoryid",
									list_ccategory.get(arg2).getCategoryid());
							startActivity(intent);
						}
					} else {

					}
				}
			});
			prv.refreshComplete();
		}

	}

	@Override
	public void dataLoad(int[] types) {
		if (navigation.equals(getResources().getString(R.string.shop))) {
			this.loadData(new Updateone[] { new Updateone("SCATEGORY",
					new String[][] { { "categoryid", categoryid } }), });
		} else if (navigation.equals(getResources().getString(R.string.life))) {
			this.loadData(new Updateone[] { new Updateone("LCATEGORY",
					new String[][] { { "categoryid", categoryid } }), });
		} else if (navigation.equals(getResources().getString(R.string.conser))) {
			this.loadData(new Updateone[] { new Updateone("BCATEGORY",
					new String[][] { { "categoryid", categoryid } }), });
		} else if (navigation.equals(getResources().getString(
				R.string.group_buying))) {
			this.loadData(new Updateone[] { new Updateone("WCATEGORY",
					new String[][] { { "categoryid", categoryid } }), });
		} else {
			this.loadData(new Updateone[] { new Updateone("JCATEGORY",
					new String[][] { { "categoryid", categoryid } }), });
		}

	}

}
