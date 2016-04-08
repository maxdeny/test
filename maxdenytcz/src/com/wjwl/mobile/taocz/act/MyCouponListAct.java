package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.McouponsList.Msg_Mcouponslist;
import com.tcz.apkfactory.data.mcoupons.Msg_Mcoupons;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyCouponListAct extends MActivity {
	private ListView lv;
	private PullReloadView prv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	private List<Msg_Mcoupons> list_coupons;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.mycouponlist);
		lv = (ListView) findViewById(R.myouponlist.list);
		prv = (PullReloadView) findViewById(R.myouponlist.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("mcouponslist")) {
			Msg_Mcouponslist.Builder builder = (Msg_Mcouponslist.Builder) son.build;
			list_coupons = builder.getMcouponsList();

			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_coupons.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("timefrom", list_coupons.get(i).getCouponsendtime());
				map.put("timeto", list_coupons.get(i).getCouponsgettime());
				String usetime = list_coupons.get(i).getCouponsusetime();
				if (usetime == "0" || usetime == null || usetime == "")
					map.put("usetime", "未使用");
				else
					map.put("usetime", usetime);
				map.put("title", list_coupons.get(i).getCouponstitle());
				map.put("type", list_coupons.get(i).getCouponstype());
				map.put("zhekou", list_coupons.get(i).getCouponsvalue());
				map.put("code", list_coupons.get(i).getCouponscode());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData, R.layout.item_mycouponlist,
					new String[] { "title", "code", "type", "zhekou", "timeto",
							"timefrom", "usetime" }, new int[] {
							R.item_mycouponlist.name, R.item_mycouponlist.num,
							R.item_mycouponlist.type,
							R.item_mycouponlist.zhekou,
							R.item_mycouponlist.timeto,
							R.item_mycouponlist.timefrom,
							R.item_mycouponlist.usetime });
			lv.setAdapter(sa);
			prv.refreshComplete();
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MCOUPONSLIST",
				new String[][] { { "userid", F.USER_ID } }), });
	}
}