package com.wjwl.mobile.taocz.act;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.widget.AMLayout;
import com.mdx.mobile.widget.FillLine;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class V3_AddressAct extends MActivityGroup {
	TczV3_HeadLayout head;
	RadioGroup group;
	RadioButton btn1, btn2;
	AMLayout layout;
	public static String ziti_area = "", ziti_name = "",ziti_phone = "", ziti_id = "",ztrname="",ztrphone="";

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_address);
		setId("V3_AddressAct");
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		head.setRightButton3Text("新增");
	
		group = (RadioGroup) findViewById(R.v3_address.group);
		btn1 = (RadioButton) findViewById(R.v3_address.btn1);
		btn2 = (RadioButton) findViewById(R.v3_address.btn2);
		
		if(TczV3_OrderConfirmationAct.isziti.equals("1")){
			btn2.setVisibility(View.GONE);
			Toast.makeText(this, "您有不支持自提的商品", Toast.LENGTH_SHORT).show();
		}
		layout = (AMLayout) findViewById(R.v3_address.content);
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (btn1.isChecked()) {
					Intent intent = new Intent();
					intent.putExtra("actfrom","V3_AddressAct");
					intent.setClass(V3_AddressAct.this, NewAddressAct.class);
					startActivity(intent);
				} else if (btn2.isChecked()) {
					if (V3_ZiTiAct.AreaData != null) {
						if (V3_ZiTiAct.list_ccategory != null) {
							ztrname=V3_ZiTiAct.ed_name.getText().toString().trim();
							ztrphone=V3_ZiTiAct.ed_phone.getText().toString().trim();
							if(ztrname.length()>0&&ztrphone.length()>0){
							for (int i = 0; i < V3_ZiTiAct.group.getChildCount(); i++) {
								if (((RadioButton) V3_ZiTiAct.group.getChildAt(i)).isChecked()) {
									ziti_name = V3_ZiTiAct.list_ccategory.get(i).getCategoryname();
									ziti_id = V3_ZiTiAct.list_ccategory.get(i).getCategoryid();
									ziti_phone= V3_ZiTiAct.list_ccategory.get(i).getRemark();
									String[] str = new String[] { ziti_area,ziti_name, ziti_id ,ziti_phone,ztrname,ztrphone};
									Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0).sent(7, str);
									V3_AddressAct.this.finish();
								}
							}
							}
							else{
								Toast.makeText(V3_AddressAct.this,
										"请填写完整取货人姓名和手机号~", Toast.LENGTH_SHORT)
										.show();
							}
						} else {
							Toast.makeText(V3_AddressAct.this,
									"该区域内没有自提点，请选择收货地址收货", Toast.LENGTH_SHORT)
									.show();
						}
					}
				}
			}
		});
		head.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setTitle("收货地址");
		this.setContentLayout(layout);
		group.setOnCheckedChangeListener(new FillLine.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				setCurrent(checkedId);
				if (checkedId == R.v3_address.btn1)
					head.setRightButton3Text("新增");
				else if (checkedId == R.v3_address.btn2)
					head.setRightButton3Text("保存");
			}
		});
		{
			Intent intent = new Intent(this, ConsigneeAddressAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("act", "V3_AddressAct");
			this.addChild(R.v3_address.btn1, "btn1", intent);
		}
		{
			Intent intent = new Intent(this, V3_ZiTiAct.class)
					.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			this.addChild(R.v3_address.btn2, "btn2", intent);
		}

	}
}
