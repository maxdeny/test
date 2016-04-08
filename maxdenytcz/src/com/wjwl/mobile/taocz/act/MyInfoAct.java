package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.mtcz.Msg_Mtcz;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyInfoAct extends MActivity {

	private TextView username;
	private TextView taocardmoney, taomoney;
	private ListView listview;
	private MImageView imghead;
	String[] titles;
	int[] icons;
	String[] values;
	ArrayList<Map<String, Object>> mData;
	SimpleAdapter sadapter;
	private PullReloadView prv;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.myinfo);
		setId("MyInfoAct");
		init();
	}

	private void init() {
		// titles = new String[] {
		// getResources().getString(R.string.myinfo_order),
		// getResources().getString(R.string.myinfo_gold),
		// getResources().getString(R.string.myinfo_coupon),
		// getResources().getString(R.string.myinfo_message),
		// getResources().getString(R.string.myinfo_address) };
		// icons = new int[] { R.drawable.myinfo_order, R.drawable.myinfo_txb,
		// R.drawable.myinfo_preferential, R.drawable.myinfo_message,
		// R.drawable.myinfo_address };
		// values = new String[] { "0", "0个", "0", "0", "0" };
		titles = new String[] {
				getResources().getString(R.string.myinfo_order),
				getResources().getString(R.string.myinfo_gold),
				getResources().getString(R.string.myinfo_message),
				getResources().getString(R.string.myinfo_address)
		// ,getResources().getString(R.string.myinfo_cityrecruit)
		};
		icons = new int[] { R.drawable.myinfo_order, R.drawable.myinfo_txb,
				R.drawable.myinfo_message, R.drawable.myinfo_address
		// ,R.drawable.myinfo_cityrecruit
		};
		values = new String[] { "0", "0个", "0", "0" };
		username = (TextView) findViewById(R.id.username);
		taocardmoney = (TextView) findViewById(R.id.taocardmoney);
		taomoney = (TextView) findViewById(R.id.money);
		imghead = (MImageView) this.findViewById(R.id.userhead);
		listview = (ListView) findViewById(R.id.listview);
		username.setText("淘常州android客户端");
		taocardmoney.setText("0");
		prv = (PullReloadView) findViewById(R.id.pullReloadView);
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoadByDelay(null);
	}

	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mtcz")) {
			Msg_Mtcz.Builder builder = (Msg_Mtcz.Builder) son.build;
			username.setText("淘常州android客户端");
			taocardmoney.setText(builder.getCard().equals("") ? "0" : builder
					.getCard());
			taomoney.setText(builder.getMoney() + "元");
			if (builder.getFaceurl() != null)
				imghead.setObj(builder.getFaceurl());
			values[0] = builder.getMyorder();
			values[1] = builder.getMycoin();
			// values[2] = builder.getMydiscount();
			values[2] = builder.getMymessage();
			values[3] = builder.getMyaddress();
			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < titles.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("icon", icons[i]);
				map.put("title", titles[i]);
				if (titles[i].equals(getResources().getString(
						R.string.myinfo_cityrecruit))) {
					map.put("value", "(在线报名,点击进入)");
					map.put("arr", R.drawable.more_arrow);
				} else {
					if (i == 1)
						map.put("value", values[i] + "币");
					else
						map.put("value", values[i]);
					if (titles[i].equals(getResources().getString(
							R.string.myinfo_gold)))
						map.put("arr", R.drawable.more_arrow_none);
					else {
						map.put("arr", R.drawable.more_arrow);
					}
				}
				mData.add(map);
			}
			sadapter = new SimpleAdapter(this, mData,
					R.layout.item_myinfo_list, new String[] { "icon", "title",
							"value", "arr" }, new int[] { R.item_info.icon,
							R.item_info.title, R.item_info.value,
							R.item_info.arr });

			listview.setAdapter(sadapter);
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (position) {
					case 0:
						Intent i1 = new Intent();
						i1.setClass(getApplicationContext(),
								MyOrderListAct.class);
						startActivity(i1);
						break;
					case 1:
						break;
					// case 2:
					// Intent i3 = new Intent();
					// i3.setClass(getApplicationContext(),
					// MyCouponListAct.class);
					// startActivity(i3);
					// break;
					case 2:
						Intent i2 = new Intent();
						i2.setClass(getApplicationContext(),
								MyMessageListAct.class);
						startActivity(i2);
						break;
					case 3:
						Intent i = new Intent();
						i.putExtra("act", "canchange");
						i.setClass(getApplicationContext(),
								ConsigneeAddressAct.class);
						startActivity(i);
						break;
					case 4:
//						Intent i3 = new Intent();
//						i3.putExtra("act", "canchange");
//						i3.setClass(getApplicationContext(),
//								CiytRecruitAct.class);
//						startActivity(i3);
						break;
					}
				}
			});

		}

		prv.refreshComplete();
	}

	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MTCZ",
				new String[][] { { "userid", F.USER_ID } }), });
	}
}