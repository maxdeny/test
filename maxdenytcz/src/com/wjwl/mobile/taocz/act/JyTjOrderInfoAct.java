package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;
import com.wjwl.mobile.taocz.widget.MyListView;

public class JyTjOrderInfoAct extends MActivity {
	Button back;
	TextView text, money, state, ordernumber, date, style,name,evryprice,number;
	MyListView mylistview;
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> map;
	String ordernum;
	MImageView img;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.jyjdorderinfo);
		back = (Button) findViewById(R.tuangouproductinfo.back);
		text = (TextView) findViewById(R.tuangouproductinfo.text);
		money = (TextView) findViewById(R.tuangouproductinfo.money);
		state = (TextView) findViewById(R.tuangouproductinfo.state);
		ordernumber = (TextView) findViewById(R.tuangouproductinfo.ordernumber);
		date = (TextView) findViewById(R.tuangouproductinfo.date);
		style = (TextView) findViewById(R.tuangouproductinfo.style);
		mylistview = (MyListView) findViewById(R.tuangouproductinfo.mylistview);
		img=(MImageView) findViewById(R.jyjdorderinfo.img);
		name=(TextView) findViewById(R.jyjdorderinfo.name);
		number=(TextView) findViewById(R.jyjdorderinfo.number);
		evryprice=(TextView) findViewById(R.jyjdorderinfo.evryprice);
		// TextView赋值的时候要添加额外的文字
		ordernum = getIntent().getStringExtra("ordernumber");
		dataLoad(null);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void setAlldata(String m, String s, String o, String d, String st) {
		money.setText("订单金额：￥" + m);
		state.setText("订单状态：" + s);
		ordernumber.setText("订单号：" + o);
		date.setText("下单时间：" + d);
		style.setText("支付方式：" + st);
	}
	public void setProductInfo(String i,String n,String nb,String p){
//		img.setObj(i);
		name.setText(n);
		number.setText("预定数量："+nb);
		evryprice.setText("单价："+p);
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("jytjorderdetails")) {
			Msg_Morder_Item.Builder builder=(Msg_Morder_Item.Builder) son.build;
			setAlldata(builder.getPaycode(), builder.getBusinessstate(), builder.getBusinessname(),
					builder.getPaytime(), builder.getLevel());
			setProductInfo("url", builder.getProductname(), builder.getItemcount(), builder.getTotal());
		}

	}
//http://life.taocz.com/life.php?app=morderitem&act=jytjorderdetails&debug=1&orderno=2013032517112386
	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("JYTJORDERDETAILS",
				new String[][] { { "orderno", ordernum }
						 }), });
	}

}

