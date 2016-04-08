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
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;

public class CategoryFirstAct extends MActivity {
	TextView head_title;
	String str_head_title;
	ListView lv;
	private PullReloadView prv;
	private boolean callBack = false;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	// String[] category = { "休闲食品", "服装装饰", "酒水饮料","美妆护理","母婴玩具","家电数码" };
	List<Msg_Ccategory> list_ccategory;
	Button bt_back;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		setId("CategoryFirstAct");
		head_title = (TextView) this.findViewById(R.merchant.head_title);
		str_head_title = getIntent().getStringExtra("title");//购物
		callBack = getIntent().getBooleanExtra("callback", false);
		bt_back = (Button) findViewById(R.merchant.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CategoryFirstAct.this.finish();
			}
		});
		if (str_head_title.equals(getResources().getString(R.string.conser)))
			head_title.setText("选择服务类型");
		else
			head_title.setText(str_head_title);
		lv = (ListView) this.findViewById(R.merchant.merchantlist);
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
		// if (son != null && son.mgetmethod.equals("SCATEGORY")) {
		Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
		list_ccategory = builder.getCcategoryList();

		mData = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list_ccategory.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("categoryFirst", list_ccategory.get(i).getCategoryname());
			mData.add(map);
		}
		sa = new SimpleAdapter(this, mData, R.layout.item_com_merchant_list,
				new String[] { "categoryFirst" },
				new int[] { R.item_merchant.text });
		lv.setAdapter(sa);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (str_head_title.equals(getResources().getString(
						R.string.group_buying))) {
					if (callBack) {
						Frame.HANDLES
								.get("TakeOutAct")
								.get(0)
								.sent(1,
										list_ccategory.get(arg2)
												.getCategoryname());
						Frame.HANDLES.closeOne("CategoryFirstAct");
					} else {
//						Intent i = new Intent(getApplicationContext(),
//								TakeOutAct.class);
//						i.putExtra("categoryid", list_ccategory.get(arg2)
//								.getCategoryid());
//						i.putExtra("title", list_ccategory.get(arg2)
//								.getCategoryname());
//						startActivity(i);
					}
				} else if (str_head_title.equals(getResources().getString(
						R.string.life))) {
					Intent i = new Intent();
					i.putExtra("title", list_ccategory.get(arg2)
							.getCategoryname());
					i.putExtra("navigation", str_head_title);
					i.putExtra("isSelect",
							getIntent().getBooleanExtra("isSelect", false));
					i.putExtra("categoryparentid", list_ccategory.get(arg2)
							.getCategoryid());
					i.putExtra("searchPupub",
							getIntent().getIntExtra("searchPupub", -1));
					i.setClass(getApplicationContext(), CategorySecondAct.class);
					startActivity(i);
				} else if (str_head_title.equals(getResources().getString(
						R.string.conser))) {
					Intent i = new Intent();
					// i.putExtra("title", list_ccategory.get(arg2)
					// .getCategoryname());
					i.putExtra("title", str_head_title);
					i.putExtra("navigation", str_head_title);
					i.putExtra("categoryparentid", list_ccategory.get(arg2)
							.getCategoryid());
					i.putExtra("name_category", list_ccategory.get(arg2)
							.getCategoryname());
					i.setClass(getApplicationContext(), CategorySecondAct.class);
					startActivity(i);

				}else if (str_head_title.equals(getResources().getString(
							R.string.moving_show))) {
						Intent i = new Intent();
						// i.putExtra("title", list_ccategory.get(arg2)
						// .getCategoryname());
						i.putExtra("title", str_head_title);
						i.putExtra("navigation", str_head_title);
						i.putExtra("businessid", list_ccategory.get(arg2)
								.getCategoryid());
						i.putExtra("businessname", list_ccategory.get(arg2)
								.getCategoryname());
						i.setClass(getApplicationContext(), MovieShowListAct.class);
						startActivity(i);

					} else {
					Intent i = new Intent();
					i.putExtra("title", list_ccategory.get(arg2)
							.getCategoryname());
					i.putExtra("navigation", str_head_title);
					i.putExtra("searchPupub",
							getIntent().getIntExtra("searchPupub", -1));
					i.putExtra("isSelect",
							getIntent().getBooleanExtra("isSelect", false));
					i.putExtra("categoryparentid", list_ccategory.get(arg2)
							.getCategoryid());
					i.setClass(getApplicationContext(), CategorySecondAct.class);
					startActivity(i);
				}
			}
		});
		prv.refreshComplete();
	}

	// }

	@Override
	public void dataLoad(int[] types) {
		if (str_head_title.equals(getResources().getString(R.string.shop))) {
			this.loadData(new Updateone[] { new Updateone("SCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		} else if (str_head_title.equals(getResources()
				.getString(R.string.life))) {
			this.loadData(new Updateone[] { new Updateone("LCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		} else if (str_head_title.equals(getResources().getString(
				R.string.conser))) {
			this.loadData(new Updateone[] { new Updateone("BCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		} else if (str_head_title.equals(getResources().getString(
				R.string.group_buying))) {
			this.loadData(new Updateone[] { new Updateone("WCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		}else if (str_head_title.equals(getResources().getString(
				R.string.moving_show))) {
			this.loadData(new Updateone[] { new Updateone("DCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		}else {
			this.loadData(new Updateone[] { new Updateone("JCATEGORY",
					new String[][] { { "categoryid", "0" } }), });
		}

	}

}
