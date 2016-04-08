package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;
import com.wjwl.mobile.taocz.widget.MyListView;

public class TuangouProductInfoAct extends MActivity {
	Button back;
	TextView text, money, state, ordernumber, date, style;
	MyListView mylistview;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> map;
	String ordernum;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.tuangouproductinfo);
		back = (Button) findViewById(R.tuangouproductinfo.back);
		text = (TextView) findViewById(R.tuangouproductinfo.text);
		money = (TextView) findViewById(R.tuangouproductinfo.money);
		state = (TextView) findViewById(R.tuangouproductinfo.state);
		ordernumber = (TextView) findViewById(R.tuangouproductinfo.ordernumber);
		date = (TextView) findViewById(R.tuangouproductinfo.date);
		style = (TextView) findViewById(R.tuangouproductinfo.style);
		mylistview = (MyListView) findViewById(R.tuangouproductinfo.mylistview);
		// TextView赋值的时候要添加额外的文字
		ordernum = getIntent().getStringExtra("ordernumber");
		dataLoad(null);
	}

	public void setAlldata(String m, String s, String o, String d, String st) {
		money.setText("订单金额：￥" + m);
		state.setText("订单状态：" + s);
		ordernumber.setText("订单号：" + o);
		date.setText("下单时间：" + d);
		style.setText("支付方式：" + st);

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("tgorderdetail")) {//tgorderdetail
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;//Msg_MorderBusiness
			for (int i = 0; i < builder.getItemList().size(); i++) {
				Msg_Morder_Item item = builder.getItem(i);
				map = new HashMap<String, Object>();
				map.put("name", item.getProductname());
				map.put("number", item.getItemcount());
				map.put("price", item.getTotal());
				data.add(map);
			}
			mylistview.setAdapter(new MyAdapter(TuangouProductInfoAct.this,
					data, "TuangouProductInfoAct"));
			setAlldata(builder.getTprice(), builder.getState(), ordernum,
					builder.getBusinessid(), builder.getBusinessname());

		}

	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("TGORDERDETAIL",//
				new String[][] { { "userid", F.USER_ID },
						 { "ordemo", ordernum }//{ "perpage", F.Per_Page + "" },{ "page", "1" } 
						}), });
	}

}
