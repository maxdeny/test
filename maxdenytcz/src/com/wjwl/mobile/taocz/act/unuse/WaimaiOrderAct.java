package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.MorderItem.Msg_Morder_Item;
//import com.tcz.apkfactory.data.Msg_Morder_Business.Msg_MorderBusiness;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.MyAdapter;
//
//public class WaimaiOrderAct extends MActivity{
//	Button back;
//	TextView text;
//	ListView listview;
//	List<HashMap<String,Object>> data=new ArrayList<HashMap<String,Object>>();
//	HashMap<String,Object> map;
//	@Override
//	protected void create(Bundle arg0) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.waimaiorder);
//		back=(Button) findViewById(R.waimaiorder.back);
//		text=(TextView) findViewById(R.waimaiorder.text);
//		listview=(ListView) findViewById(R.waimaiorder.listview);
//		
//		back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				finish();
//			}
//		});
//		dataLoad(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son.build != null && son.mgetmethod.equals("wmwsorderlist")) {
//			Msg_MorderBusiness.Builder builder = (Msg_MorderBusiness.Builder) son.build;
//			{
//				List<Msg_Morder_Item> datas=builder.getItemList();
//				for(int i=0;i<datas.size();i++){
//					Msg_Morder_Item item=datas.get(i);
//					map=new HashMap<String,Object>();
//					map.put("name", item.getProductname());
//					map.put("date", item.getPaytime());
//					map.put("ordernumber", item.getPaycode());
//					map.put("who", item.getName());
//					map.put("phone", item.getPhone());
//					map.put("money", item.getTotal());
//					map.put("state", item.getBusinessstate());
//					data.add(map);
//				}
//				listview.setAdapter(new MyAdapter(WaimaiOrderAct.this,data,"WaimaiOrderAct"));
//			}
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WMWSORDERLIST",//测试id=111288
//				new String[][] { { "userid", "111288" },{ "perpage", F.Per_Page+"" },
//				{ "page", "1" }}), });
//	}
//}
