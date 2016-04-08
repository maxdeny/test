package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.dialog.AffirmDialog;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class ConsigneeAddressAct extends MActivity {
	private ListView lv;
	private MyAdapter madapter = null;
	private ArrayList<Map<String, Object>> mData;
	private List<Msg_Maddress> addresslist;
	Consignee consignee;
	private int temp = -1;
	private String act = "";
	private AffirmDialog dialog;
	public String addressid, childareaid;
	LinearLayout addlayout;
	Button bt_add;
	TczV3_HeadLayout headlayout;

	class Consignee {
		String str_addressid;
		TextView consignee, mobile, telphone, area, address, isdlt;
		Button bt_del, bt_mod, bt_use;
		RelativeLayout clic_layout;
	}

	protected void create(Bundle arg0) {
		setContentView(R.layout.consignee_address);
		init();
		dataLoad(null);
	}

	private void init() {
		setId("ConsigneeAddressAct");
		Intent intent = getIntent();
		act = intent.getStringExtra("act");
		headlayout = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		headlayout.setTitle("我的收货地址");
		headlayout.setLeftClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConsigneeAddressAct.this.finish();
				if(getIntent().getStringExtra("hasadd")!=null){
					Frame.HANDLES.close("TczV3_OrderConfirmationAct");
				}
			}
		});
		headlayout.setRightButton3Text("新增");
		headlayout.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent();
				i.putExtra("actfrom", "ConsigneeAddressAct");
				i.setClass(getApplicationContext(), NewAddressAct.class);
				startActivityForResult(i, 0);
			}
		});
		if (act.equals("canchange") || act.equals("canuse")
				|| act.equals("ActPay_ConfirmationAct")) {
			headlayout.setVisibility(View.VISIBLE);
		} else
			headlayout.setVisibility(View.GONE);
		lv = (ListView) this.findViewById(R.conignee.conigneelist);
		addlayout = (LinearLayout) findViewById(R.conigneelist.addlayout);
		bt_add = (Button) findViewById(R.conigneelist.bt_add);
		bt_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("actfrom", "ConsigneeAddressAct");
				i.setClass(getApplicationContext(), NewAddressAct.class);
				startActivityForResult(i, 0);
			}

		});
		temp = 0;
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
			consignee = null;

			try {
				if (convertView == null) {
					consignee = new Consignee();
					convertView = mInflater.inflate(
							R.layout.item_consignee_address, null);
					consignee.clic_layout = (RelativeLayout) convertView
							.findViewById(R.item_consignee.cliclayout);
					consignee.consignee = (TextView) convertView
							.findViewById(R.item_consignee.tv_consignee);
					consignee.mobile = (TextView) convertView
							.findViewById(R.item_consignee.tv_mobilephone);
					consignee.telphone = (TextView) convertView
							.findViewById(R.item_consignee.tv_telphone);
					consignee.area = (TextView) convertView
							.findViewById(R.item_consignee.tv_area);
					consignee.address = (TextView) convertView
							.findViewById(R.item_consignee.tv_address);
					consignee.bt_del = (Button) convertView
							.findViewById(R.item_consignee.bt_del);
					consignee.bt_mod = (Button) convertView
							.findViewById(R.item_consignee.bt_mod);
					consignee.bt_use = (Button) convertView
							.findViewById(R.item_consignee.bt_use);
					consignee.isdlt = (TextView) convertView
							.findViewById(R.item_consignee.tv_isdlt);
					convertView.setTag(consignee);
				} else {
					consignee = (Consignee) convertView.getTag();
				}

				// 序号
				consignee.consignee.setText((String) mData.get(position).get(
						"consignee"));
				consignee.mobile.setText((String) mData.get(position).get(
						"mobile"));
				consignee.telphone.setText((String) mData.get(position).get(
						"telphone"));
				consignee.area
						.setText((String) mData.get(position).get("area"));
				consignee.address.setText((String) mData.get(position).get(
						"address"));
				consignee.str_addressid = (String) mData.get(position).get(
						"addressid");
				if (((String) mData.get(position).get(// 判断默认显示
						"ifdefault")).equals("1")) {
					consignee.isdlt.setVisibility(View.VISIBLE);
					consignee.bt_del.setVisibility(View.GONE);
				} else {
					consignee.isdlt.setVisibility(View.INVISIBLE);
					consignee.bt_del.setVisibility(View.VISIBLE);
				}

				if (act.equals("canchange"))
					consignee.bt_use.setVisibility(View.GONE);
				else
					consignee.bt_use.setVisibility(View.VISIBLE);

				consignee.clic_layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (act != null && act.equals("V3_AddressAct")) {

							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("TczV3_OrderConfirmationAct")
									.get(0).sent(2, str);
							ConsigneeAddressAct.this.finish();
						}
							else if (act != null && act.equals("ActPay_ConfirmationAct")) {
							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position)
											.get("telphone"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("ActPay_ConfirmationAct").get(0)
									.sent(1, str);
							ConsigneeAddressAct.this.finish();
							
						} else {
							Intent i = new Intent();
							i.setClass(getApplication(), ModifyAddressAct.class);
							i.putExtra("consignee", (String) mData
									.get(position).get("consignee"));
							i.putExtra("mobile", (String) mData.get(position)
									.get("mobile"));
							i.putExtra("telphone", (String) mData.get(position)
									.get("telphone"));
							i.putExtra("address", (String) mData.get(position)
									.get("address"));
							i.putExtra("addressid", (String) mData
									.get(position).get("addressid"));
							i.putExtra("area", (String) mData.get(position)
									.get("area"));
							i.putExtra(
									"childareaid",
									(String) mData.get(position).get(
											"childareaid"));
							i.putExtra("isdlt", (String) mData.get(position)
									.get(// 判断默认显示
									"ifdefault"));
							i.putExtra("formlist", "formlist");
							startActivityForResult(i, 0);
						}
					}

				});
				consignee.bt_use.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (act.equals("canuse")) {
							// Intent intent = new Intent();
							// intent.putExtra("username",(String)
							// mData.get(position).get("consignee"));
							// intent.putExtra("useraddress",(String)
							// mData.get(position).get("address"));
							// intent.putExtra("usertel",(String)
							// mData.get(position).get("telphone"));
							// intent.putExtra("addressid",(String)
							// mData.get(position).get("addressid"));
							// ConsigneeAddressAct.this.setResult(RESULT_OK,intent);
							//

							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position)
											.get("telphone"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("V3_WriteOrderAct").get(0)
									.sent(2, str);
							ConsigneeAddressAct.this.finish();
							Frame.HANDLES.close("V3_AddressAct");

							finish();
						} else if (act.equals("V3_AddressAct")) {
							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position)
											.get("telphone"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("V3_WriteOrderAct").get(0)
									.sent(2, str);
							ConsigneeAddressAct.this.finish();
							Frame.HANDLES.close("V3_AddressAct");
						} else if (act.equals("JH_ZFAct")) {
							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position)
											.get("telphone"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("JH_ZFAct").get(0).sent(1, str);
							ConsigneeAddressAct.this.finish();
							Frame.HANDLES.close("V3_AddressAct");
						} else if (act.equals("ActPay_ConfirmationAct")) {
							String[] str = new String[] {
									(String) mData.get(position).get(
											"consignee"),
									(String) mData.get(position).get("address"),
									(String) mData.get(position).get("mobile"),
									(String) mData.get(position)
											.get("telphone"),
									(String) mData.get(position).get(
											"addressid") };
							Frame.HANDLES.get("ActPay_ConfirmationAct").get(0)
									.sent(1, str);
							ConsigneeAddressAct.this.finish();
							Frame.HANDLES.close("V3_AddressAct");
						}

					}
				});
				consignee.bt_del.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog = new AffirmDialog(ConsigneeAddressAct.this);
						dialog.show();
						dialog.bt_submit
								.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										temp = 1;
										addressid = (String) mData
												.get(position).get("addressid");
										dataLoad(null);
										dialog.cancel();
										dialog.dismiss();
									}
								});
					}

				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;

		}
	}

	@Override
	public void disposeMsg(int type, final Object obj) {
		if (type == 1) {
			temp = 0;
			dataLoad(null);
		}
		if (type == 2) {
//			dialog = new AffirmDialog(ConsigneeAddressAct.this);
//			dialog.show();
//			dialog.bt_submit.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					temp = 1;
//					addressid = (obj.toString());
//					dataLoad(null);
//					dialog.cancel();
//					dialog.dismiss();
//				}
//			});
			temp = 1;
			addressid = (obj.toString());
			dataLoad(null);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_OK:
			temp = 0;
			dataLoad(null);
			Frame.HANDLES.reloadAll("MyInfoAct");
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("maddresslistdel")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "删除成功~",
						Toast.LENGTH_LONG).show();
				temp = 0;
				dataLoad(null);
				Frame.HANDLES.reloadAll("MyInfoAct");
			} else {
				Toast.makeText(getApplicationContext(), "删除失败~",
						Toast.LENGTH_LONG).show();
			}
			Frame.HANDLES.close("ModifyAddressAct");
		} else if (son.build != null && son.mgetmethod.equals("maddresslist")) {
			addlayout.setVisibility(View.GONE);
			Msg_Maddresslist.Builder builder = (Msg_Maddresslist.Builder) son.build;
			addresslist = builder.getMaddressList();
			if (mData == null)
				mData = new ArrayList<Map<String, Object>>();
			else
				mData.clear();
			for (int i = 0; i < addresslist.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("consignee", addresslist.get(i).getReceiver());
				map.put("mobile", addresslist.get(i).getMobile());
				map.put("telphone", addresslist.get(i).getTelphone());
				map.put("area", addresslist.get(i).getArea());
				map.put("address", addresslist.get(i).getDetailsaddress());
				map.put("addressid", addresslist.get(i).getAddressid());
				map.put("ifdefault", addresslist.get(i).getIfdefault());
				map.put("childareaid", addresslist.get(i).getChildareaid().equals("")?addresslist.get(i).getParentareaid():addresslist.get(i).getChildareaid());
				mData.add(map);
			}
			madapter = new MyAdapter(this);
			lv.setAdapter(madapter);
		} else if (son.build == null && son.mgetmethod.equals("maddresslist")) {
			addlayout.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		switch (temp) {
		case 0:
			this.loadData(new Updateone[] { new Updateone("MADDRESSLIST",
					new String[][] { { "userid", F.USER_ID } }), });
			break;
		case 1:
			this.loadData(new Updateone[] { new Updateone("MADDRESSLISTDEL",
					new String[][] { { "addressid", addressid } }), });
			break;
		}
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(getIntent().getStringExtra("hasadd")!=null){
				Frame.HANDLES.close("TczV3_OrderConfirmationAct");
			}
			this.finish();
			return true;
		}
		return false;
	}

}
