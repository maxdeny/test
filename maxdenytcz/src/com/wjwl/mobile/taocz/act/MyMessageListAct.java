package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Mmessage.Msg_Mmessage;
import com.tcz.apkfactory.data.MmessageList.Msg_Mmessagelist;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class MyMessageListAct extends MActivity {
	private ListView lv;
	private TextView headtitle;
	private PullReloadView prv;
	ArrayList<Map<String, Object>> mData = null;
	private SimpleAdapter sa;
	private List<Msg_Mmessage> list_message;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.com_merchant_list);
		lv = (ListView) findViewById(R.merchant.merchantlist);
		headtitle = (TextView) findViewById(R.merchant.head_title);
		prv = (PullReloadView) findViewById(R.merchant.pullReloadView);
		headtitle.setText("我的短消息");
		prv.setOnRefreshListener(new PullReloadView.OnRefreshListener() {
			public void onRefresh() {
				dataLoad(null);
			}
		});
		dataLoad(null);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mmessagelist")) {
			Msg_Mmessagelist.Builder builder = (Msg_Mmessagelist.Builder) son.build;
			list_message = builder.getMmessageList();

			mData = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list_message.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", list_message.get(i).getTitle());
				map.put("type", list_message.get(i).getMessagetype());
				map.put("id", list_message.get(i).getMessageid());
				map.put("details", list_message.get(i).getDetails());
				map.put("time", list_message.get(i).getSendtime());
				map.put("from", list_message.get(i).getSender());
				mData.add(map);
			}
			sa = new SimpleAdapter(this, mData, R.layout.item_mymessage,
					new String[] { "type", "title", "time" }, new int[] {
							R.item_mymessage.type, R.item_mymessage.text,
							R.item_mymessage.time });
			lv.setAdapter(sa);
            lv.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i=new Intent();
					i.putExtra("title",list_message.get(arg2).getTitle());
					i.putExtra("from",list_message.get(arg2).getSender());
					i.putExtra("time",list_message.get(arg2).getSendtime());
					i.putExtra("details",list_message.get(arg2).getDetails());
					i.setClass(MyMessageListAct.this, MyMessageContentAct.class);
					startActivity(i);
				}});
			prv.refreshComplete();
		}
	}

	@Override
	public void dataLoad(int[] types) {

		this.loadData(new Updateone[] { new Updateone("MMESSAGELIST",
				new String[][] { { "userid", F.USER_ID } }), });
	}
}
