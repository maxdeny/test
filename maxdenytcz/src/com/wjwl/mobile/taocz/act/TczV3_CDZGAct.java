package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MDragChangeViewAdapter;
import com.wjwl.mobile.taocz.adapter.TczV3_CDZGAdapter;
import com.wjwl.mobile.taocz.widget.DragChangeView;
import com.wjwl.mobile.taocz.widget.MyGridView;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class TczV3_CDZGAct extends MActivity {
	private ArrayList<Map<String, Object>> mData;
	List<Msg_Citem> list_citem;
	private MyGridView gridView;
	public String id = "", type = "";
	private PullReloadView prv;
	TczV3_HeadLayout headlayout;
	DragChangeView mDragChangeView;
	TczV3_CDZGAdapter adapter;

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tczv3_cdzg);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TczV3_CDZGAct.this.finish();
			}
		});
		headlayout.setTitle("产地直供");
		mDragChangeView = (DragChangeView) findViewById(R.tczv3.DragChangeView);

		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setRadius(7);
		mDragChangeView.setMoveType(1);
		prv = (PullReloadView) findViewById(R.tczv3.pullReloadView);
		gridView = (MyGridView) findViewById(R.tczv3.gridview);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("V3_GOODS_BOOKING")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			list_citem = builder.getCitemList();
			mData = new ArrayList<Map<String, Object>>();
			F.now = System.currentTimeMillis();
			for (int i = 0; i < list_citem.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("img", list_citem.get(i).getItemimageurl());
				map.put("title", list_citem.get(i).getItemtitle());
				map.put("price", list_citem.get(i).getItemdiscount());
				map.put("itemid", list_citem.get(i).getItemid());
				map.put("sellcount", list_citem.get(i).getItemcount());
				map.put("acttime", list_citem.get(i).getV3Remainingtime());
				map.put("endtime", list_citem.get(i).getOther2());
				map.put("selltype", list_citem.get(i).getItemselltype());
				map.put("starttime", list_citem.get(i).getOther1());
				mData.add(map);
			}
			adapter = new TczV3_CDZGAdapter(TczV3_CDZGAct.this, mData);
			gridView.setAdapter(adapter);
			prv.refreshComplete();
		}
		if (son.build != null && son.mgetmethod.equals("V3_AD")) {
			Msg_CitemList2.Builder builder = (Msg_CitemList2.Builder) son.build;
			List<Msg_CitemList> dataSource = builder.getCitemlistList();
			MDragChangeViewAdapter mDragChangeViewAdapter = new MDragChangeViewAdapter(
					dataSource.get(0).getCitemList(), this);
			mDragChangeView.setAdapter(mDragChangeViewAdapter);
		}
	}

	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] {
				new Updateone("V3_GOODS_BOOKING", new String[][] {}),
				new Updateone("V3_AD",
						new String[][] { { "ppage", "booking" } }) });
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("DirectSupplyPage");
		MobclickAgent.onResume(TczV3_CDZGAct.this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("DirectSupplyPage");
		MobclickAgent.onPause(TczV3_CDZGAct.this);
	}

}
