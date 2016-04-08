package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.CategoryFilterAdapter;
import com.wjwl.mobile.taocz.data.CategoryFilterChild;
import com.wjwl.mobile.taocz.data.CategoryFilterGroup;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_ShaiXuanAct extends MActivity {
	RadioGroup radiogroup;
	LinearLayout layout1, layout2;
	private TczV3_HeadLayout headlayout;
	protected int type;// 1、只有businessid,2、只有categoryid,3、businessid和categoryid都无
	CategoryFilterAdapter cfa;
	private String businessid = "", categoryid = "", brandId = "",
			priceId = "", keywords = "", actfrom = "", filtertype = "";
	private List<CategoryFilterGroup> dataSource = new ArrayList<CategoryFilterGroup>();
	ExpandableListView el_filter;
	private List<Msg_Ccategory> list_ccategory, list_ccategory2;
	private ArrayList<Map<String, Object>> mData;
	private CategoryFilterGroup group;
	List<CategoryFilterChild> childList;
	CategoryFilterChild child;
	PageListView lv;
	private SimpleAdapter sa;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_shaixuan);
		if (getIntent().getStringExtra("actfrom") != null) {
			actfrom = getIntent().getStringExtra("actfrom");
		}

		if (getIntent().getStringExtra("businessid") != null) {
			businessid = getIntent().getStringExtra("businessid");
			type = 1;
		}
		if (getIntent().getStringExtra("categoryid") != null) {
			categoryid = getIntent().getStringExtra("categoryid");
			type = 2;
		}
		if (businessid.equals("") && categoryid.equals(""))
			type = 3;
		keywords = getIntent().getStringExtra("keywords");
		actfrom = getIntent().getStringExtra("actfrom");
		if (null == keywords) {
			keywords = "";
		}
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_ShaiXuanAct.this.finish();
			}
		});
		headlayout.setRightButton3Text("确定");
		headlayout.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] str = new String[] { brandId, priceId };
				// if(_actfrom.equals("Lihua_Act")){
				// Frame.HANDLES.get("Lihua_Act").get(0).sent(7, str);
				// }else
				if (actfrom.equals("TczV3_GoodsListAct")) {
					if (type == 1)
						Frame.HANDLES.get("TczV3_GoodsListAct").get(0)
								.sent(7, str);
					else if (type == 2)
						Frame.HANDLES.get("TczV3_GoodsListAct").get(0)
								.sent(7, str);
				}
				TczV3_ShaiXuanAct.this.finish();
			}
		});
		lv = (PageListView) findViewById(R.id.listview);
		el_filter = (ExpandableListView) findViewById(R.id.el_filter);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout1.setVisibility(View.VISIBLE);
		layout2.setVisibility(View.GONE);
		radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_fl:
					layout1.setVisibility(View.VISIBLE);
					layout2.setVisibility(View.GONE);
					headlayout.setRightButton3Gone();
					break;
				case R.id.radio_sx:
					layout1.setVisibility(View.GONE);
					layout2.setVisibility(View.VISIBLE);
					headlayout.setRightButton3VISIBLE();
					if (list_ccategory == null) {
						dataLoad(new int[] { 0 });
					}
					break;
				}
			}
		});
		if (businessid.equals("")) {
			headlayout.setTitle("筛选");
			radiogroup.setVisibility(View.GONE);
			layout1.setVisibility(View.GONE);
			layout2.setVisibility(View.VISIBLE);
			dataLoad(new int[] { 0 });// 筛选
		} else {
			headlayout.setTitle("分类筛选");
			radiogroup.setVisibility(View.VISIBLE);
			dataLoad(new int[] { 2 });// 店铺分类
		}

	}

	public void setData(List<CategoryFilterGroup> _dataSource, String actfrom,
			int type) {
		cfa = new CategoryFilterAdapter(TczV3_ShaiXuanAct.this, dataSource);
		el_filter.setAdapter(cfa);
		el_filter.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				switch (arg2) {
				case 0:
					brandId = dataSource.get(arg2).getChildList().get(arg3)
							.getItemId();
					break;
				case 1:
					priceId = dataSource.get(arg2).getChildList().get(arg3)
							.getItemId();
					break;
				default:
					break;
				}
				for (CategoryFilterChild child : dataSource.get(arg2)
						.getChildList()) {
					child.setChecked(false);
				}
				dataSource.get(arg2).getChildList().get(arg3).changeChecked();
				cfa.notifyDataSetChanged();
				return false;
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("v3_filterlist")) {
			if (filtertype.equals("brand")) {
				Toast.makeText(TczV3_ShaiXuanAct.this, "品牌暂无数据~",
						Toast.LENGTH_SHORT).show();
				dataLoad(new int[] { 1 });
			} else if (filtertype.equals("price")) {
				Toast.makeText(TczV3_ShaiXuanAct.this, "价格暂无数据~",
						Toast.LENGTH_SHORT).show();
				if (dataSource.size() > 0)
					setData(dataSource, actfrom, type);
			}
		}
		if (son.build != null && son.mgetmethod.equals("v3_filterlist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			// mData = new ArrayList<Map<String, Object>>();
			// for (int i = 0; i < list_ccategory.size(); i++) {
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("itemname", list_ccategory.get(i).getCategoryname());
			// map.put("itemid", list_ccategory.get(i).getCategoryid());
			// mData.add(map);
			// }
			group = new CategoryFilterGroup();
			childList = new ArrayList<CategoryFilterChild>();
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
				setData(dataSource, actfrom, type);
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
		if (son.build == null && son.mgetmethod.equals("v3_scategory")) {
			Toast.makeText(TczV3_ShaiXuanAct.this, "暂无分类~", Toast.LENGTH_SHORT)
					.show();
		}
		if (son.build != null && son.mgetmethod.equals("v3_scategory")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory2 = builder.getCcategoryList();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_ccategory2.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("itemname", list_ccategory2.get(i).getCategoryname());
				map.put("itemid", list_ccategory2.get(i).getCategoryid());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData,
					R.layout.item_com_merchant_list,
					new String[] { "itemname" },
					new int[] { R.item_merchant.text });
			lv.setAdapter(sa);
			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					String[] str = new String[] { businessid,
							(String) mData.get(arg2).get("itemid") };
					if (Frame.HANDLES.get("TczV3_GoodsListAct").size() >= 1)
						Frame.HANDLES
								.get("TczV3_GoodsListAct")
								.get(Frame.HANDLES.get("TczV3_GoodsListAct")
										.size() - 1).sent(2, str);
					TczV3_ShaiXuanAct.this.finish();
				}
			});
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 0 || types[0] == 1) {
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
			else if (type == 4)
				this.loadData(new Updateone[] { new Updateone("V3_FILTERLIST",
						new String[][] { { "businessid", businessid },
								{ "itemid", categoryid },
								{ "keywords", keywords },
								{ "filtertype", filtertype } }), });
		} else if (types[0] == 2)
			this.loadData(new Updateone[] { new Updateone("V3_SCATEGORY",
					new String[][] { { "sid", businessid } }), });
	}
}
