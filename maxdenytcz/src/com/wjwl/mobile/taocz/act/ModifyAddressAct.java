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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.wjwl.mobile.taocz.dialog.AffirmDialog;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class ModifyAddressAct extends MActivity {
	private Button bt_area, bt_spinner, bt_submit, bt_area2, bt_spinner2,
			bt_edit;
	String[] areas, areas2, areasid, areasid2;
	RelativeLayout area_layout;
	TextView tv_area;
	String consignee, mobile, telphone, address, areachild, area, areaid,
			areaid2, addressid, area1, area2, formlist;
	EditText ed_consignee, ed_mobile, ed_telphone, ed_address;
	private AffirmDialog dialog;
	int type;
	int temp;
	String isdlt;
	CheckBox chk;
	private List<Msg_Ccategory> list_ccategory, list_ccategory2;
	boolean isHaveTwoCategory,clickedit=false;
	LinearLayout layout1, layout2;
	TczV3_HeadLayout headlayout;

	protected void create(Bundle arg0) {
		setId("ModifyAddressAct");
		setContentView(R.layout.edit_address);
		init();
	}

	private void init() {
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
		tv_area = (TextView) findViewById(R.edit_address.area);
		bt_submit = (Button) findViewById(R.edit_address.bt_submit);
		bt_edit = (Button) findViewById(R.edit_address.bt_edit);
		layout1 = (LinearLayout) findViewById(R.edit_address.arealayout1);
		layout2 = (LinearLayout) findViewById(R.edit_address.arealayout2);
		layout2.setVisibility(View.VISIBLE);
		layout1.setVisibility(View.GONE);
		chk = (CheckBox) findViewById(R.edit_address.checkbox);
		Intent intent = getIntent();
		area = intent.getStringExtra("area");
		tv_area.setText(area);
		ed_consignee.setText(intent.getStringExtra("consignee"));
		ed_mobile.setText(intent.getStringExtra("mobile"));
		ed_telphone.setText(intent.getStringExtra("telphone"));
		ed_address.setText(intent.getStringExtra("address"));
		isdlt = intent.getStringExtra("isdlt");
		formlist = intent.getStringExtra("formlist");

		addressid = intent.getStringExtra("addressid");
		areachild = intent.getStringExtra("childareaid");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("修改收货地址");
		headlayout.setRightButton3Text("删除");
		headlayout.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = new AffirmDialog(ModifyAddressAct.this);
				dialog.show();
				dialog.bt_submit.setOnClickListener(new OnClickListener() {
	
					@Override
					public void onClick(View v) {
						Frame.HANDLES.get("ConsigneeAddressAct").get(0)
						.sent(2, addressid);
					}
				});
			}
		});
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ModifyAddressAct.this.finish();
			}
		});
		if (isdlt.equals("1"))
			chk.setChecked(true);
		else
			chk.setChecked(false);
		chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isdlt.equals("1")) {
					chk.setChecked(true);
					Toast.makeText(getApplication(), "当前地址为默认地址，无法修改默认状态",
							Toast.LENGTH_SHORT).show();
				}
			}

		});
		bt_submit.setOnClickListener(new modify());
		bt_edit.setOnClickListener(new modify());
	}

	void selectArea() {
		new AlertDialog.Builder(ModifyAddressAct.this).setTitle("选择地区 ")
				.setItems(areas, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						bt_area.setText(areas[which]);
						areaid = areasid[which];
						if (!list_ccategory.get(which).getCategoryno()
								.equals("")
								&& list_ccategory.get(which).getCategoryno() != null) {
							temp = 1;
							area1=list_ccategory.get(which).getCategoryid();
							isHaveTwoCategory = true;
							dataLoad(null);
						} else {
							isHaveTwoCategory = false;
							bt_area2.setText("");
							area1=list_ccategory.get(which).getCategoryid();
							area_layout.setVisibility(View.INVISIBLE);
							areaid2 = "";
							area = "";
						}

					}
				}).create().show();
	}

	void selectArea2() {
		new AlertDialog.Builder(ModifyAddressAct.this).setTitle("选择地区 ")
				.setItems(areas2, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						bt_area2.setText(areas2[which]);
						areaid2 = areasid2[which];
					}
				}).create().show();
	}

	public class modify implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.edit_address.bt_edit:
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.GONE);
				temp = 0;
				area = "";
				formlist = "";
				clickedit=true;
				dataLoad(null);
				break;
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
				if (area.equals("")) {
					area1 = bt_area.getText().toString().trim();
					area2 = bt_area2.getText().toString().trim();
					area = "常州" + bt_area.getText().toString() + " "
							+ bt_area2.getText().toString();
				}
				if (chk.isChecked())
					isdlt = "1";
				else
					isdlt = "0";
				if (consignee.length() <= 0) {
					Toast toast = Toast.makeText(ModifyAddressAct.this,
							"请输入收货人姓名", Toast.LENGTH_SHORT);
					toast.show();
					ed_consignee.requestFocus();
					return;
				}
				if (mobile.length() <= 0) {
					Toast toast = Toast.makeText(ModifyAddressAct.this,
							"请输入手机号码", Toast.LENGTH_SHORT);
					toast.show();
					ed_mobile.requestFocus();
					return;
				} else if (!F.isMobileNO(mobile)) {
					Toast toast = Toast.makeText(ModifyAddressAct.this,
							"手机号码输入不正确，请重新输入", Toast.LENGTH_SHORT);
					toast.show();
					ed_mobile.requestFocus();
					return;
				}
				if (address.length() <= 0) {
					Toast toast = Toast.makeText(ModifyAddressAct.this,
							"请输收货地址", Toast.LENGTH_SHORT);
					toast.show();
					ed_address.requestFocus();
					return;
				}
				if (!area.equals("")) {
					if (!formlist.equals("formlist")) {
						if (area1 == "" || area1 == null) {
							Toast toast = Toast.makeText(ModifyAddressAct.this,
									"请选择地区", Toast.LENGTH_SHORT);
							toast.show();
							return;
						}
					}
					if (isHaveTwoCategory) {
						area1 = bt_area.getText().toString().trim();
						area2 = bt_area2.getText().toString().trim();
						area = "常州" + bt_area.getText().toString() + " "
								+ bt_area2.getText().toString();
						if ((area2 == "" || area2 == null)) {
							Toast toast = Toast.makeText(ModifyAddressAct.this,
									"请选择区域", Toast.LENGTH_SHORT);
							toast.show();
							return;
						}
					}

				}
				temp = 2;
				dataLoad(null);
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("maddresslistedit")
				&& temp == 2) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "修改成功~",
						Toast.LENGTH_LONG).show();

				Frame.HANDLES.sendAll("ConsigneeAddressAct", type, null);

				Intent intent = new Intent();
				ModifyAddressAct.this.setResult(RESULT_OK, intent);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "修改失败~",
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
			bt_area.setOnClickListener(new modify());
			bt_spinner.setOnClickListener(new modify());
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
			for (int i = 0; i < list_ccategory2.size(); i++) {
				if (!list_ccategory2.get(i).getCategoryname().trim()
						.equals("全部")) {
					areas2[i] = list_ccategory2.get(i).getCategoryname();
					areasid2[i] = list_ccategory2.get(i).getCategoryid();
				}
			}
			bt_area2.setOnClickListener(new modify());
			bt_spinner2.setOnClickListener(new modify());
			selectArea2();
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
					new String[][] { { "categoryid", "530" } }), });
			break;
		case 1:

			this.loadData(new Updateone[] { new Updateone("CAREA",
					new String[][] { { "categoryid", areaid } }), });

			break;
		case 2:
			if (areachild!=null&&areachild.equals("")&&clickedit)
				if (isHaveTwoCategory) {
					areachild = areaid2;
				} else {
					areachild = areaid;
				}
			this.loadData(new Updateone[] { new Updateone("MADDRESSLISTEDIT",
					new String[][] { { "addressid", addressid },
							{ "userid", F.USER_ID }, { "receiver", consignee },
							{ "mobile", mobile }, { "telphone", telphone },
							{ "area", area }, { "areachild", areachild },
							{ "address", address }, { "ifdefault", isdlt } }), });
			break;
		}
	}
}
