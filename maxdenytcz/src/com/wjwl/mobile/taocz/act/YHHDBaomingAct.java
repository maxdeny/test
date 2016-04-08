package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;

import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class YHHDBaomingAct extends MActivity {
	class YYHD {
		TextView title;
		Button bt_baoming;
	}

	String businessid, event_id;
	YYHD yyhd;
	private ListView lv;
	private MyAdapter madapter = null;
	private ArrayList<Map<String, Object>> mData;
	private Button bt_back;
	int type = 0;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.yhhdbaoming);
		setId("YHHDBaomingAct");
		businessid = getIntent().getStringExtra("businessid");
		lv = (ListView) findViewById(R.yhhdbaoming.list);
		bt_back = (Button) findViewById(R.yhhdbaoming.back);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				YHHDBaomingAct.this.finish();
			}
		});
		dataLoad(null);
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return mData.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			yyhd = null;

			try {
				if (convertView == null) {
					yyhd = new YYHD();
					convertView = mInflater.inflate(R.layout.item_yhhdbaoming,
							null);
					yyhd.title = (TextView) convertView
							.findViewById(R.item_yhhdbaoming.title);
					yyhd.bt_baoming = (Button) convertView
							.findViewById(R.item_yhhdbaoming.baoming);
					convertView.setTag(yyhd);
				} else {
					yyhd = (YYHD) convertView.getTag();
				}

				yyhd.title.setText((String) mData.get(position).get("title"));

				yyhd.bt_baoming.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (F.USER_ID == null || F.USER_ID.length() == 0) {
							F.toLogin(YHHDBaomingAct.this, "YHHDBaomingAct",
									"B", 0);
							return;
						}
						event_id = (String) mData.get(position).get("id");
						type = 1;
						dataLoad(null);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("yyyhydlist")) {
			Msg_CcategoryList.Builder build = (Msg_CcategoryList.Builder) son.build;
			List<Msg_Ccategory> list_item = build.getCcategoryList();
			if (mData == null)
				mData = new ArrayList<Map<String, Object>>();
			else
				mData.clear();
			for (int i = 0; i < list_item.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("title", list_item.get(i).getCategoryname());
				map.put("id", list_item.get(i).getCategoryid());
				mData.add(map);
			}
			madapter = new MyAdapter(this);
			lv.setAdapter(madapter);
		} else if (son.build != null && son.mgetmethod.equals("yyyhhd")) {
			Msg_Retn.Builder build = (Msg_Retn.Builder) son.build;
			if (build.getErrorCode() == 1)
				Toast.makeText(getApplication(), "报名成功", Toast.LENGTH_SHORT)
						.show();
			else if (build.getErrorCode() == 0)
				if (build.getErrorMsg().equals(""))
					Toast.makeText(getApplication(), "报名失败", Toast.LENGTH_SHORT)
							.show();
				else
					Toast.makeText(getApplication(), build.getErrorMsg(),
							Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void dataLoad(int[] types) {
		switch (type) {
		case 0:
			this.loadData(new Updateone[] { new Updateone("YYYHYDLIST",
					new String[][] { { "businessid", businessid } }), });
			break;
		case 1:
			this.loadData(new Updateone[] { new Updateone("YYYHHD",
					new String[][] { { "event_id", event_id },
							{ "userid", F.USER_ID } }), });
			break;
		}
	}
}
