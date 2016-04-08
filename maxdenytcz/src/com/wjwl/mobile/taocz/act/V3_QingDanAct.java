package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.base.Retn.Msg_Retn.Builder;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.PageListView;
import com.mdx.mobile.widget.PullReloadView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList.Msg_CitemList;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Maddress.Msg_Maddress;
import com.tcz.apkfactory.data.Maddresslist.Msg_Maddresslist;
import com.tcz.apkfactory.data.Order.Msg_Order;
import com.tcz.apkfactory.data.OrderList.Msg_OrderList;
import com.umeng.analytics.MobclickAgent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.OrderConfirmationAdapter;
import com.wjwl.mobile.taocz.adapter.ShoppingCartAdapter;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.dialog.OrderTypeConfirmationDialog;
import com.wjwl.mobile.taocz.widget.foot_orderconfirmation;
import com.wjwl.mobile.taocz.widget.head_orderconfirmaiton;

/**
 * 查看、修改和删除购物车
 * 
 * @author Administrator
 * 
 */
public class V3_QingDanAct extends MActivity {

	TextView tv_username, tv_address, tv_tel, tv_allpay;
	EditText ed_fptt;
	ListView lv;
	RelativeLayout layout1;
	OrderConfirmationAdapter OCAdp;
	String region_type;
	// private Msg_Order.Builder OrderMain; // 订单
	// public ArrayList<Msg_OrderList> orderList; // 订单列表
	private List<Msg_Maddress> addresslist;
	private Msg_CitemList2.Builder OrderMain; // 订单
	public ArrayList<Msg_CitemList> orderList; // 订单列表
	private Button bt_pay;
	private String paytype, addressid, remark, total, username, useraddress,
			usertel, paytypetemp;
	private Context mContext;
	Button bt_back;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setId("V3_QingDanAct");
		setContentView(R.layout.order_confirmation);
		mContext = V3_QingDanAct.this;
		Intent i = getIntent();
		paytypetemp = i.getStringExtra("paytypetemp");
		region_type = i.getStringExtra("region_type");
		addressid = i.getStringExtra("addressid");
		tv_allpay = (TextView) findViewById(R.order_confirmation.allpay);
		bt_back = (Button) findViewById(R.order_confirmation.back);
		lv = (ListView) findViewById(R.order_confirmation.listview);
		bt_pay = (Button) findViewById(R.order_confirmation.bt_pay);
		bt_pay.setVisibility(View.GONE);
		bt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_QingDanAct.this.finish();
			}
		});
		dataLoad(null);
	}

	@Override
	public void dataLoad(int[] types) {

		if (types == null) {
			this.loadData(new Updateone[] { new Updateone("PORDER",
					new String[][] { { "userid", F.USER_ID },
							{ "order_pay_type", paytypetemp },
							{ "region_type", region_type },// V3新参数
							{ "region_id", addressid } }), });
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("porder")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			orderList = new ArrayList<Msg_CitemList>(
					OrderMain.getCitemlistList()); // 定义临时列表
			total = Arith.to2zero(OrderMain.getCartTotal());
			tv_allpay
					.setText(total + "(邮费：" + OrderMain.getShippingFee() + ")");
			remark = OrderMain.getRemark();
			OCAdp = new OrderConfirmationAdapter(this, orderList);
			lv.setAdapter(OCAdp);
		}
	}

}
