package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;

public class V3_ZiTiAct extends MActivity {
	RelativeLayout head;
	public static RadioGroup group;
	LinearLayout linear;
	RadioButton rbt;
	String areaid = "", itemid = "";
	TextView t_area;
	public static List<Msg_Ccategory> list_ccategory;
	public static ArrayList<Map<String, Object>> AreaData;
	String[] itemids;
	Button bt_back;
	public static EditText ed_name,ed_phone;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_ziti);
		setId("V3_ZiTiAct");
		bt_back = (Button) findViewById(R.v3_ziti.back);
		AreaData = new ArrayList<Map<String, Object>>();
		group = (RadioGroup) findViewById(R.v3_ziti.group);
		t_area = (TextView) findViewById(R.v3_ziti.t_area);
		head=(RelativeLayout)findViewById(R.v3_ziti.headlayout);
		head.setVisibility(View.GONE);
		linear = (LinearLayout) findViewById(R.v3_ziti.linear);
		ed_name=(EditText) findViewById(R.v3_ziti.ed_name);
		ed_phone=(EditText) findViewById(R.v3_ziti.ed_phone);
		
		linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(V3_ZiTiAct.this, V3_AreaListAct.class);
				startActivity(in);
			}
		});
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_ZiTiAct.this.finish();
			}
		});
		dataLoad(new int[] { 1 });
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			int position = (Integer) obj;
			t_area.setText((String) AreaData.get(position).get("areaname"));
			V3_AddressAct.ziti_area = (String) AreaData.get(position).get("areaname");
			areaid = (String) AreaData.get(position).get("areaid");
			dataLoad(new int[] { 2 });
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("v3_ztarealist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();

			for (int i = 0; i < list_ccategory.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("areaname", list_ccategory.get(i).getCategoryname());
				map.put("areaid", list_ccategory.get(i).getCategoryid());
				map.put("remark", list_ccategory.get(i).getRemark());
				AreaData.add(map);
			}
			V3_AddressAct.ziti_area = (String) AreaData.get(0).get("areaname");
			t_area.setText((String) AreaData.get(0).get("areaname"));
			areaid = (String) AreaData.get(0).get("areaid");
			dataLoad(new int[] { 2 });
		}
		if (son.build != null && son.mgetmethod.equals("v3_ztplaceslist")) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			LayoutInflater f = LayoutInflater.from(V3_ZiTiAct.this);
			group.removeAllViews();
			group.removeAllViewsInLayout();
			group.setBackgroundColor(0x00000000);
			itemids = new String[list_ccategory.size()];
			for (int i = 0; i < list_ccategory.size(); i++) {
				rbt = (RadioButton) f.inflate(R.layout.radiobuttonxml, null);
				rbt.setText(list_ccategory.get(i).getCategoryname());
				// rbt.setId();
				itemids[i] = list_ccategory.get(i).getCategoryid();
				group.addView(rbt);
			}
			((RadioButton) group.getChildAt(0)).setChecked(true);// 默认那个给选中必须放到这里
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		if (typs[0] == 1)
			this.loadData(new Updateone[] { new Updateone("V3_ZTAREALIST",
					new String[][] {}), });
		else if (typs[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("V3_ZTPLACESLIST",
					new String[][] { { "areaid", areaid } }), });
			// this.loadData(new Updateone[] { new Updateone("V3_ZTPLACESLIST",
			// new String[][] { { "areaid", areaid } }), });
		}

	}
}
