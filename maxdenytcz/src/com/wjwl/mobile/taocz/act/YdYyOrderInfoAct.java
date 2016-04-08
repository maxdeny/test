package com.wjwl.mobile.taocz.act;

import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;

public class YdYyOrderInfoAct extends MActivity{
	LinearLayout product_linear;
	TextView product_name,price,state,who,phone,eattime,number,house,text;
	String id;
	Button back;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.ydyyorderinfo);
		product_linear=(LinearLayout) findViewById(R.ydyyorderinfo.product_linear);
		product_name=(TextView) findViewById(R.ydyyorderinfo.name);
		price=(TextView) findViewById(R.ydyyorderinfo.price);
		state=(TextView) findViewById(R.ydyyorderinfo.state);
		who=(TextView) findViewById(R.ydyyorderinfo.who);
		phone=(TextView) findViewById(R.ydyyorderinfo.phone);
		eattime=(TextView) findViewById(R.ydyyorderinfo.eattime);
		number=(TextView) findViewById(R.ydyyorderinfo.number);
		house=(TextView) findViewById(R.ydyyorderinfo.house);
		text=(TextView) findViewById(R.ydyyorderinfo.text);
		back=(Button) findViewById(R.ydyyorderinfo.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		dataLoad(null);
	}
	
	@Override
	public void dataLoad(int[] types) {
		// TODO Auto-generated method stub
		this.loadData(new Updateone[] { new Updateone("YYORDERDETAIL",
				new String[][] { { "userid",F.USER_ID},
				{ "orderno", getIntent().getStringExtra("orderno")==null?"11":getIntent().getStringExtra("orderno")}
				}), });
	}
	@Override
	public void disposeMessage(Son son) throws Exception {
		// TODO Auto-generated method stub
		if (son.build != null && son.mgetmethod.equals("yyorderdetail")) {
			Msg_Morder_Item.Builder builder = (Msg_Morder_Item.Builder) son.build;
			product_name.setText(builder.getProductname());
			price.setText("人均:￥"+builder.getLevel());
			state.setText("订单状态："+builder.getBusinessstate());
			who.setText("预订人："+builder.getName());
			phone.setText("联系电话："+builder.getPhone());
			eattime.setText("就餐时间："+builder.getPaytime());
			number.setText("订餐人数："+builder.getItemcount());
			String xuqiu=builder.getAddress().equals("")?"无":builder.getAddress();
			house.setText("包房需求："+xuqiu);
		}
	}
	
	public void setData(String pn,String p,String st,String w,String ph,String e,String n,String h){
		product_name.setText(pn);
		price.setText(p);
		state.setText(st);
		who.setText(w);
		phone.setText(ph);
		eattime.setText(e);
		number.setText(n);
		house.setText(h);
	}
}
