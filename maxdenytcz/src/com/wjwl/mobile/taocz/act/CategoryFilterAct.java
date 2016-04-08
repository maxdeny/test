package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.data.CategoryFilterChild;
import com.wjwl.mobile.taocz.data.CategoryFilterGroup;
import com.wjwl.mobile.taocz.widget.CategoryFilterView;

public class CategoryFilterAct extends MActivity {
	private String categoryid = "", keywords = "", businessid = "";
	ArrayList<Map<String, Object>> mData;
	private List<Msg_Ccategory> list_ccategory;
	private String filtertype;
	private List<CategoryFilterGroup> dataSource = new ArrayList<CategoryFilterGroup>();
	private CategoryFilterView container;
	private String actfrom;
	int type = 3;

	@Override
	protected void create(Bundle savedInstanceState) {
		container = new CategoryFilterView(this);
		setContentView(container);
		// filtertype = getIntent().getStringExtra("filtertype");
		if (getIntent().getStringExtra("businessid") != null) {
			businessid = getIntent().getStringExtra("businessid");
			type = 1;
		} else if (getIntent().getStringExtra("categoryid") != null) {
			categoryid = getIntent().getStringExtra("categoryid");
			type = 2;
		} else
			type = 3;
		keywords = getIntent().getStringExtra("keywords");
		actfrom = getIntent().getStringExtra("actfrom");
		if (null == keywords) {
			keywords = "";
		}
		mFinder();
		mBinder();
		mIniter();
	}

	private void mFinder() {
	}

	private void mBinder() {

	}

	private void mIniter() {
		dataLoad(new int[] { 0 });
		// dataLoad(new int[]{ 1 });
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("v3_filterlist")) {
			Toast.makeText(CategoryFilterAct.this, "暂无数据~", Toast.LENGTH_SHORT)
					.show();
		}
		if (son.build != null && son.mgetmethod.equals("v3_filterlist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("itemname", list_ccategory.get(i).getCategoryname());
				map.put("itemid", list_ccategory.get(i).getCategoryid());
				mData.add(map);
			}
			CategoryFilterGroup group = new CategoryFilterGroup();
			List<CategoryFilterChild> childList = new ArrayList<CategoryFilterChild>();
			CategoryFilterChild child;

			if (filtertype.equals("brand")) {
				// 获得品牌
				group.setName("品牌");

				for (int i = 0; i < list_ccategory.size(); i++) {
					child = new CategoryFilterChild();
					child.setItemName(list_ccategory.get(i).getCategoryname());
					child.setItemId(list_ccategory.get(i).getCategoryid());
					childList.add(child);
				}
				group.setChildList(childList);
				dataSource.add(group);
				dataLoad(new int[] { 1 });

			} else if (filtertype.equals("price")) {
				// 获得价格
				group.setName("价格");

				for (int i = 0; i < list_ccategory.size(); i++) {
					child = new CategoryFilterChild();
					child.setItemName(list_ccategory.get(i).getCategoryname());
					child.setItemId(list_ccategory.get(i).getCategoryid());
					childList.add(child);
				}
				group.setChildList(childList);
				dataSource.add(group);

				container.setData(dataSource, actfrom, type);
			} else {
			}
			/*
			 * sa = new SimpleAdapter(this, mData,
			 * R.layout.item_com_merchant_list, new String[] { "itemname" }, new
			 * int[] { R.item_merchant.text }); lv.setAdapter(sa);
			 * lv.setOnItemClickListener(new OnItemClickListener() { public void
			 * onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			 * { String[] str = new String[] { (String)
			 * mData.get(arg2).get("itemname"), (String)
			 * mData.get(arg2).get("itemid") }; if (filtertype.equals("brand"))
			 * { Frame.HANDLES.get(actfrom).get(0).sent(1, str); } else if
			 * (filtertype.equals("price")) {
			 * Frame.HANDLES.get(actfrom).get(0).sent(2, str); }
			 * CategoryFilterAct.this.finish(); } });
			 */
		}
	}

	@Override
	public void dataLoad(int[] types) {
		switch (types[0]) {
		case 0:
			filtertype = "brand";
			break;
		case 1:
			filtertype = "price";
			break;
		default:
			break;
		}
		if (type == 1) {
			this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
					new String[][] { { "businessid", businessid },
							{ "keywords", keywords },
							{ "filtertype", filtertype } }), });
		} else if (type == 2) {
			this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
					new String[][] { { "itemid", categoryid },
							{ "keywords", keywords },
							{ "filtertype", filtertype } }), });

		} else if (type == 3)
			this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
					new String[][] { { "keywords", keywords },
							{ "filtertype", filtertype } }), });
	}
}
