package com.wjwl.mobile.taocz.act;

import java.util.List;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.tcz.apkfactory.data.CcategoryList.Msg_CcategoryList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.R.newsinfo;
import com.wjwl.mobile.taocz.untils.Arith;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class NewAddressAct extends MActivity {
	private Button bt_area, bt_spinner, bt_submit, bt_area2, bt_spinner2;
	String[] areas, areas2, areasid, areasid2;
	RelativeLayout area_layout;
	TextView headtitle;
	String consignee = "", mobile = "", telphone = "", address = "", areachild,
			area, areaid, areaid2, actfrom = "";
	EditText ed_consignee, ed_mobile, ed_telphone, ed_address;
	int type;
	int temp;
	String isdlt;
	CheckBox chk;
	private List<Msg_Ccategory> list_ccategory, list_ccategory2;
	boolean isHaveTwoCategory;
	private TczV3_HeadLayout headlayout;

	protected void create(Bundle arg0) {
		setContentView(R.layout.edit_address);
		init();
		dataLoad(null);
	}

	private void init() {
		actfrom = getIntent().getStringExtra("actfrom");
		bt_area = (Button) this.findViewById(R.edit_address.bt_area);
		bt_spinner = (Button) this.findViewById(R.edit_address.bt_spinner);
		bt_area2 = (Button) this.findViewById(R.edit_address.bt_area2);
		bt_spinner2 = (Button) this.findViewById(R.edit_address.bt_spinner2);
		area_layout = (RelativeLayout) this
				.findViewById(R.edit_address.area_layout);
		ed_consignee = (EditText) findViewById(R.edit_address.edit_consignee);
		ed_mobile = (EditText) findViewById(R.edit_address.edit_mobile);
		ed_telphone = (EditText) findViewById(R.edit_address.edit_telphone);
		ed_address = (EditText) findViewById(R.edit_address.edit_address);
		bt_submit = (Button) findViewById(R.edit_address.bt_submit);
		chk = (CheckBox) findViewById(R.edit_address.checkbox);
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("新增收货地址");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NewAddressAct.this.finish();
				Arith.closeBoard(NewAddressAct.this, ed_consignee);
				
			}
		});
		bt_submit.setOnClickListener(new Add());
		temp = 0;
	}

	void selectArea() {
		new AlertDialog.Builder(NewAddressAct.this).setTitle("选择地区 ")
				.setItems(areas, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						bt_area.setText(areas[which]);
						areaid = areasid[which];
						if (!list_ccategory.get(which).getCategoryno()
								.equals("")
								&& list_ccategory.get(which).getCategoryno() != null) {
							isHaveTwoCategory = true;
							temp = 1;
							dataLoad(null);
						} else {
							isHaveTwoCategory = false;
							bt_area2.setText("");
							area_layout.setVisibility(View.INVISIBLE);
							areaid2 = "";
							area = "";
						}

					}
				}).create().show();
	}

	void selectArea2() {
		new AlertDialog.Builder(NewAddressAct.this).setTitle("选择地区 ")
				.setItems(areas2, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						bt_area2.setText(areas2[which]);
						areaid2 = areasid2[which];
					}
				}).create().show();
	}

	public class Add implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.edit_address.bt_area:
			case R.edit_address.bt_spinner:
				selectArea();
				break;
			case R.edit_address.bt_area2:
			case R.edit_address.bt_spinner2:
				selectArea2();
				break;
			case R.edit_address.bt_submit:
				consignee = ed_consignee.getText().toString().trim();
				mobile = ed_mobile.getText().toString().trim();
				telphone = ed_telphone.getText().toString().trim();
				address = ed_address.getText().toString().trim();
				String area1 = bt_area.getText().toString();
				String area2 = bt_area2.getText().toString();
				area = bt_area.getText().toString() + " "
						+ bt_area2.getText().toString();
				if (chk.isChecked())
					isdlt = "1";
				else
					isdlt = "0";
				if (consignee.length() <= 0) {
					Toast toast = Toast.makeText(NewAddressAct.this,
							"请输入收货人姓名", Toast.LENGTH_SHORT);
					toast.show();
					ed_consignee.requestFocus();
					return;
				}
				if (mobile.length() <= 0) {
					Toast toast = Toast.makeText(NewAddressAct.this, "请输入手机号码",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_mobile.requestFocus();
					return;
				} else if (!F.isMobileNO(mobile)) {
					Toast toast = Toast.makeText(NewAddressAct.this,
							"号码有误，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_mobile.requestFocus();
					return;
				}

				if (address.length() <= 0) {
					Toast toast = Toast.makeText(NewAddressAct.this, "请输收货地址",
							Toast.LENGTH_SHORT);
					toast.show();
					ed_address.requestFocus();
					return;
				}
				if (area1 == "" || area1 == null) {
					Toast toast = Toast.makeText(NewAddressAct.this, "请选择地区",
							Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				if (isHaveTwoCategory)
					if ((area2 == "" || area2 == null)) {
						Toast toast = Toast.makeText(NewAddressAct.this,
								"请选择区域", Toast.LENGTH_SHORT);
						toast.show();
						return;
					}
				temp = 2;
				dataLoad(null);
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("maddresslistadd")
				&& temp == 2) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "增加成功~",
						Toast.LENGTH_LONG).show();
				if (actfrom.equals("V3_AddressAct")) {
					// String[] str = new String[5];
					// str[0] = consignee;
					// str[1] = address;
					// str[2] = mobile;
					// str[3] = telphone;
					// str[4] = retn.getErrorMsg();
					// Frame.HANDLES.get("TczV3_OrderConfirmationAct").get(0).sent(2,
					// str);
					Frame.HANDLES.get("ConsigneeAddressAct").get(0).sent(1, "");
					// Frame.HANDLES
					// .closeIds("V3_AddressAct,ConsigneeAddressAct,V3_ZiTiAct");
				} else if (actfrom.equals("ConsigneeAddressAct")) {
					Frame.HANDLES.sendAll("ConsigneeAddressAct", 1, null);
					Intent intent = new Intent();
					NewAddressAct.this.setResult(RESULT_OK, intent);
				}
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "增加失败~",
						Toast.LENGTH_LONG).show();
			}
		} else if (son.build != null && son.mgetmethod.equals("carea")
				&& temp == 0) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory = builder.getCcategoryList();
			areas = new String[list_ccategory.size() - 1];
			areasid = new String[list_ccategory.size() - 1];
			for (int i = 0; i < list_ccategory.size(); i++) {
				if (!list_ccategory.get(i).getCategoryname().trim()
						.equals("全部")) {
					areas[i] = list_ccategory.get(i).getCategoryname();
					areasid[i] = list_ccategory.get(i).getCategoryid();
				}
			}
			selectArea();
			bt_area.setOnClickListener(new Add());
			bt_spinner.setOnClickListener(new Add());
			if (isHaveTwoCategory && areaid2 != "") {
				temp = 1;
				dataLoad(null);
			}
		} else if (son.build != null && son.mgetmethod.equals("carea")
				&& temp == 1) {
			Msg_CcategoryList.Builder builder = (Msg_CcategoryList.Builder) son.build;
			list_ccategory2 = builder.getCcategoryList();
			if (builder.getCcategoryList().size() <= 0) {
				area_layout.setVisibility(View.INVISIBLE);
				areaid2 = "";
				isHaveTwoCategory = false;
				return;
			} else
				area_layout.setVisibility(View.VISIBLE);
			isHaveTwoCategory = true;
			areas2 = new String[list_ccategory2.size() - 1];
			areasid2 = new String[list_ccategory2.size() - 1];
			bt_area2.setText(list_ccategory2.get(0).getCategoryname());
			areaid2 = list_ccategory2.get(0).getCategoryid();
			for (int i = 0; i < list_ccategory2.size(); i++) {
				if (!list_ccategory2.get(i).getCategoryname().trim()
						.equals("全部")) {
					areas2[i] = list_ccategory2.get(i).getCategoryname();
					areasid2[i] = list_ccategory2.get(i).getCategoryid();
				}
			}
			bt_area2.setOnClickListener(new Add());
			bt_spinner2.setOnClickListener(new Add());
		} else if (son.build == null && son.mgetmethod.equals("carea")
				&& temp == 1) {
			area_layout.setVisibility(View.INVISIBLE);
			areaid2 = "";
			isHaveTwoCategory = false;
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		switch (temp) {
		case 0:
			this.loadData(new Updateone[] { new Updateone("CAREA",
					new String[][] { { "categoryid", "530" } },
					Msg_CcategoryList.newBuilder()), });
			break;
		case 1:

			this.loadData(new Updateone[] { new Updateone("CAREA",
					new String[][] { { "categoryid", areaid } }), });

			break;
		case 2:
			if (isHaveTwoCategory) {
				areachild = areaid2;
			} else {
				areachild = areaid;
			}
			this.loadData(new Updateone[] { new Updateone("MADDRESSLISTADD",
					new String[][] { { "userid", F.USER_ID },
							{ "receiver", consignee }, { "mobile", mobile },
							{ "telphone", telphone }, { "area", "常州" + area },
							{ "areachild", areachild }, { "address", address },
							{ "ifdefault", isdlt } }), });
			break;
		}

	}
}
