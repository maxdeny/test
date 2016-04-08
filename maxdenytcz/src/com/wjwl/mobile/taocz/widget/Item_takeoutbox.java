//package com.wjwl.mobile.taocz.widget;
//
//import java.util.List;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.Frame;
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.ConsigneeAddressAct;
//import com.wjwl.mobile.taocz.dialog.TakeOutBoxDeleteDialog;
//import com.wjwl.mobile.taocz.dialog.TakeOutBoxNeedDialog;
//import com.wjwl.mobile.taocz.dialog.TakeOutBoxPayTypeDialog;
//
//public class Item_takeoutbox extends LinearLayout {
//	private TextView businessname, peisongfei, heji, address, takeoutneed,paytype;
//	private Button bt_del, bt_submit;
//	private RelativeLayout clic_layout1, clic_layout2, clic_layout3;
//	LinearLayout addlayout;
//	Context context;
//	private EditText phoneno;
//	private String bussinessId, pay_type,position,addressid;
//	private Msg_Billitem billitem;
//	// String store_id, address_info, intBuyID, strBuyerName, pay_type,
//	// goodsAmount, shippingfee_total, postscript;
//	List<Msg_Billitem> billitemlist;
//	TakeOutBoxPayTypeDialog dialog;
//
//	public Item_takeoutbox(Context context) {
//		super(context);
//		this.context = context;
//		// TODO Auto-generated constructor stub
//	}
//
//	public Item_takeoutbox(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//	}
//
//	public void initView() {
//		businessname = (TextView) findViewById(R.item_takeoutbox.bussinessname);
//		peisongfei = (TextView) findViewById(R.item_takeoutbox.peisongfei);
//		address = (TextView) findViewById(R.item_takeoutbox.address);
//		takeoutneed = (TextView) findViewById(R.item_takeoutbox.canyingyaoqiu);
//		paytype = (TextView) findViewById(R.item_takeoutbox.paytype);
//		heji = (TextView) findViewById(R.item_takeoutbox.heji);
//		clic_layout1 = (RelativeLayout) findViewById(R.item_takeoutbox.clic_layout1);
//		clic_layout2 = (RelativeLayout) findViewById(R.item_takeoutbox.clic_layout2);
//		clic_layout3 = (RelativeLayout) findViewById(R.item_takeoutbox.clic_layout3);
//		addlayout = (LinearLayout) findViewById(R.item_takeoutbox.addlayout);
//		bt_del = (Button) findViewById(R.item_takeoutbox.bt_delete);
//		bt_submit = (Button) findViewById(R.item_takeoutbox.bt_submit);
//		phoneno=(EditText) findViewById(R.item_takeoutbox.phoneno);
//		phoneno.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				phoneno.setFocusable(true);
//			}
//		});
//		clic_layout1.setOnClickListener(new onclic());
//		clic_layout2.setOnClickListener(new onclic());
//		//clic_layout3.setOnClickListener(new onclic());
//		bt_del.setOnClickListener(new onclic());
//		bt_submit.setOnClickListener(new onclic());
//	}
//
//	public void setBussinessName(CharSequence text) {
//		this.businessname.setText(text);
//	}
//
//	public void setListMsg_Billitem(List<Msg_Billitem> list) {
//		this.billitemlist = list;
//	}
//
//	public void setBussinessId(String text) {
//		this.bussinessId = text;
//	}
//
//	public void setPosition(String text) {
//		this.position=text;
//	}
//
//	public void setPeiSongFei(CharSequence text) {
//		this.peisongfei.setText(text + "元");
//	}
//
//	public void setHeJi(CharSequence text) {
//		this.heji.setText(text + "元");
//	}
//
//	public void setAddress(CharSequence text) {
//		this.address.setText(text);
//	}
//	public void setAddressid(String text) {
//		this.addressid=text;
//	}
//	public void setPayType(CharSequence text) {
//		this.paytype.setText(text);
//	}
//	public void setNeed(CharSequence text) {
//		this.takeoutneed.setText(text);
//	}
//
//	public class onclic implements OnClickListener {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.item_takeoutbox.clic_layout1:
//				if (F.USER_ID == null || F.USER_ID.length() == 0) {
//					F.toLogin(getContext(), "TakeOutBoxAct", "", 0);
//				}else{
//					Intent i = new Intent();
//					i.putExtra("act", "TakeOutBoxAct");
//					i.putExtra("position", position);
//					i.setClass(context, ConsigneeAddressAct.class);
//					context.startActivity(i);
//				}
//				break;
//			case R.item_takeoutbox.clic_layout2:
//				TakeOutBoxNeedDialog dia = new TakeOutBoxNeedDialog(
//						context, "餐饮要求",position,takeoutneed.getText().toString().trim());
//				dia.show();
//				break;
//			case R.item_takeoutbox.clic_layout3:
//				dialog = new TakeOutBoxPayTypeDialog(context,position);
//				dialog.show();
//				break;
//			case R.item_takeoutbox.bt_delete:
//				TakeOutBoxDeleteDialog dia2=new TakeOutBoxDeleteDialog(context,position);
//				dia2.show();
//				break;
//			case R.item_takeoutbox.bt_submit:
//				String str_address = address.getText().toString().trim();
//				String str_takeoutneed = takeoutneed.getText().toString()
//						.trim();
//				String str_paytype = paytype.getText().toString().trim();
//				String str_hejie = heji.getText().toString().trim();
//				String str_peisongfei = peisongfei.getText().toString().trim();
//				String str_phoneno = phoneno.getText().toString().trim();
//				if (str_paytype.equals("货到付款"))
//					pay_type = "1";
//				else if (str_paytype.equals("在线支付"))
//					pay_type = "2";
//				if (str_address.equals("")) {
//					Toast.makeText(context, "请选择收货地址", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				if (str_paytype.equals("")) {
//					Toast.makeText(context, "请选择支付方式", Toast.LENGTH_SHORT)
//							.show();
//					return;
//				}
//				if(str_phoneno.equals("")||str_phoneno.length()==0||str_phoneno.length()!=11){
//					Toast.makeText(context, "输入的手机号码有误", Toast.LENGTH_SHORT)
//					.show();
//		        	return;
//				}
//				// pay_type,goodsAmount,shippingfee_total,postscript,address_info
//				StringBuffer itemid = new StringBuffer();
//				StringBuffer itemcount = new StringBuffer();
//				for (int k = 0; k < billitemlist.size(); k++) {
//					billitem = billitemlist.get(k);
//					itemid.append(billitem.getBillitemid());
//					itemcount.append(billitem.getBillitemcount());
//					if (!(k == billitemlist.size() - 1)) {
//						itemid.append(",");
//						itemcount.append(",");
//					}
//				}
//				Frame.HANDLES
//						.get("TakeOutBoxAct")
//						.get(0)
//						.sent(4,
//								new String[] { pay_type, str_hejie,
//										str_takeoutneed, str_peisongfei,
//										addressid, bussinessId,
//										itemid.toString().trim(),
//										itemcount.toString().trim(),
//										str_phoneno});
//				break;
//			}
//		}
//	}
//
//	public void setTakeoutBoxLayout() {
//		// Msg_Citem product;
//		for (int j = 0; j < addlayout.getChildCount(); j++) {
//			addlayout.getChildAt(j).setVisibility(View.GONE);
//		}
//
//		for (int i = 0; i < billitemlist.size(); i++) {
//			billitem = billitemlist.get(i);
//			Item_item_takeoutbox item_takeout;
//
//			if (i < addlayout.getChildCount()) {
//				item_takeout = (Item_item_takeoutbox) addlayout.getChildAt(i);
//				item_takeout.setVisibility(View.VISIBLE);
//			} else {
//				LayoutInflater flater = LayoutInflater.from(this.getContext());
//				item_takeout = (Item_item_takeoutbox) flater.inflate(
//						R.layout.item_item_takeoutbox, null);
//				item_takeout.initView();
//				this.addlayout.addView(item_takeout);
//			}
//			item_takeout.setPorductId(billitem.getBillitemid());
//			item_takeout.setBussinessId(bussinessId);
//			item_takeout.setProductPrice(billitem.getBillitemprice());
//			item_takeout.setProductName(billitem.getBillitemname());
//			item_takeout.setNum();
//
//		}
//	}
//}
