package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyAdapter;

public class YdYyOrderAct extends MActivity{
	TextView text;
	Button back;
	ListView listview;
	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
	HashMap<String,Object> map;
	private int mPage=1;
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.ydyyorder);
		text=(TextView) findViewById(R.ydyyorder.text);
		back=(Button) findViewById(R.ydyyorder.back);
		listview=(ListView) findViewById(R.ydyyorder.listview);
		
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
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("yyorderlist")) {
			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
			{
				List<Msg_Morder_Item> datas=builder.getItemList();
				for(int i=0;i<datas.size();i++){
					Msg_Morder_Item item=datas.get(i);
					map=new HashMap<String,Object>();
					map.put("name", item.getProductname());
					map.put("paytime", item.getPaytime());
					map.put("eatetime", item.getProductimg());
					map.put("state", item.getBusinessstate());
					map.put("orderno", item.getLevel());
					data.add(map);
				}
				listview.setAdapter(new MyAdapter(YdYyOrderAct.this,data,"YdYyOrderAct"));
			}
		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("YYORDERLIST",
				new String[][] { { "userid", F.USER_ID },{ "perpage", F.Per_Page+"" },//内网测试userid->"11"
				{ "page", this.mPage+"" }}), });
	}


}
