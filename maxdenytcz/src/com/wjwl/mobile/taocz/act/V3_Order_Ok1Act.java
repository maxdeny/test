package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.CBill.Msg_CBill;
import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder;
import com.tcz.apkfactory.data.Payorder.Msg_Payorder.Builder;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MListAdapter;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.MyListView;

public class V3_Order_Ok1Act extends MActivity{
	HeadLayout head;
	MyListView listview;
	LinearLayout linear1,linear2;
	Button taoczjuan,shouye;
	TextView order,orderjine,style,address;
	TextView name,ordernumber;
	List<Msg_Billitem> data=new ArrayList<Msg_Billitem>();
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_order_ok1);
		head=(HeadLayout) findViewById(R.id.v3_head);
		listview=(MyListView) findViewById(R.id.v3_listview1);
		linear1=(LinearLayout) findViewById(R.id.v3_linear1);
		linear2=(LinearLayout) findViewById(R.id.v3_linear2);
		taoczjuan=(Button) findViewById(R.id.v3_taoczjuan);
		shouye=(Button) findViewById(R.id.v3_shouye);
		order=(TextView) findViewById(R.id.v3_order);
		orderjine=(TextView) findViewById(R.id.v3_jine);
		style=(TextView) findViewById(R.id.v3_style);
		address=(TextView) findViewById(R.id.v3_address);
		name=(TextView) findViewById(R.id.v3_name);
		ordernumber=(TextView) findViewById(R.id.orderidnum);
		linear2.setVisibility(View.GONE);
		taoczjuan.setVisibility(View.GONE);
		
		head.setRightGone();
		head.setLeftGone();
		head.setTitle("下单成功");
		
		listview.setAdapter(new MListAdapter(V3_Order_Ok1Act.this,data));
	}
	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("V3_ORDERINFO",
				new String[][] { { "orderno", ""},{"ordertype",""},{"userid",""}}), });
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("v3_orderinfo")) {
			Msg_CBill.Builder build=(Msg_CBill.Builder) son.build;
		}
	}

}
