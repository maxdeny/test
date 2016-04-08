package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.tcz.apkfactory.data.CbusinessinfoList.Msg_CbusinessinfoList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.AttentionAdapter;

public class AttentionAct extends MActivity {
	PageListView lv;
	ArrayList<Map<String, Object>> mData = null;
	private PullReloadView prv;
	AttentionAdapter item;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.attention);
		TextView headtext = (TextView) this
				.findViewById(R.attention.head_title);
		headtext.setText(getResources().getString(R.string.sysmenu_attention));
		lv = (PageListView) this.findViewById(R.attention.listview);
		prv = (PullReloadView) findViewById(R.attention.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("MATTENTION")) {
			Msg_CbusinessinfoList.Builder builder = (Msg_CbusinessinfoList.Builder) son.build;
			mData = new ArrayList<Map<String, Object>>();
			List<Msg_Cbusinessinfo> list = new ArrayList<Msg_Cbusinessinfo>();
			list = builder.getCbusinessinfoList();
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("b_img", list.get(i).getBusinessimage());
				map.put("b_name", list.get(i).getBusinessname());
				map.put("b_product", list.get(i).getRemark());
				mData.add(map);
			}
			item = new AttentionAdapter(AttentionAct.this, mData);
			lv.setAdapter(item);
		}
		prv.refreshComplete();
	}
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MATTENTION",
				new String[][] { { "username", "zk" } }), });
	}
}
