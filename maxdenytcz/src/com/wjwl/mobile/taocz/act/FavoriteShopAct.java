package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.FavoriteListAdapter;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class FavoriteShopAct extends MActivity {

	private ListView listview;
	ArrayAdapter<String> scopeAdapter;
	ArrayAdapter<String> typeAdapter;
	ArrayAdapter<String> sortAdapter;
	private PullReloadView prv;
	TczV3_HeadLayout headlayout;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.favorite_shop);
		setId("FavoriteShopAct");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("宝贝收藏");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FavoriteShopAct.this.finish();
			}
		});
		if (getIntent().getStringExtra("actfrom") != null
				&& getIntent().getStringExtra("actfrom").equals("FavoriteAg")) {
			headlayout.setVisibility(View.GONE);
		}
		listview = (ListView) findViewById(R.favorite.shop_listview);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		prv = (PullReloadView) findViewById(R.favorite.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build == null && son.mgetmethod.equals("mfavorite")) {
			listview.setAdapter(null);
		}
		if (son.build != null && son.mgetmethod.equals("mfavorite")) {
			Msg_CitemList.Builder builder = (Msg_CitemList.Builder) son.build;
			List<Msg_Citem> list = new ArrayList<Msg_Citem>();
			list = builder.getCitemList();
			listview.setAdapter(new FavoriteListAdapter(FavoriteShopAct.this,
					list, null));
		}
		prv.refreshComplete();
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			dataLoad(null);
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		this.loadData(new Updateone[] { new Updateone("MFAVORITE",
				new String[][] { { "class", "material" },
						{ "userid", F.USER_ID } }), });
	}

}
