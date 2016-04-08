package com.wjwl.mobile.taocz.act;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.activity.MActivityGroup;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.mtcz.Msg_Mtcz;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.MyTczAdapter;
import com.wjwl.mobile.taocz.dialog.ExitLogindialog;

public class MyTaoczAct extends MActivity {
	TextView name, money, jifen;
	Button zhuxiao, shezhi;
	GridView gridview;
	// "优惠劵", R.drawable.mytaocz21,
	String ts[] = { "我的订单", "淘心卡", "我的优惠券", "我的收藏", "收货地址", "我的积分" };
	int[] pics = { R.drawable.mytaocz19, R.drawable.mytaocz20,
			R.drawable.mytaocz21, R.drawable.mytaocz22, R.drawable.mytaocz23,
			R.drawable.mytaocz24 };
	List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> map;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setId("MyTaoczAct");
		setContentView(R.layout.mytaocz);
		name = (TextView) findViewById(R.mytaocz.username);
		money = (TextView) findViewById(R.mytaocz.money);
		jifen = (TextView) findViewById(R.mytaocz.jifen);
		shezhi = (Button) findViewById(R.mytaocz.shezhi);
		zhuxiao = (Button) findViewById(R.mytaocz.zhuxiao);
		gridview = (GridView) findViewById(R.mytaocz.gridview);
		for (int i = 0; i < ts.length; i++) {
			map = new HashMap<String, Object>();
			map.put("pic", pics[i]);
			map.put("ts", ts[i]);
			data.add(map);
		}
		gridview.setAdapter(new MyTczAdapter(MyTaoczAct.this, data));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:// 我的订单
						// Intent in = new Intent(MyTaoczAct.this,
						// MyOrder_Act.class);
						// startActivity(in);
//					Intent i1 = new Intent();
//					i1.setClass(getApplicationContext(), MyOrderListAg.class);
//					startActivity(i1);
					
					Intent i1 = new Intent();
					i1.setClass(getApplicationContext(), MyOrderDetailsAct.class);
					i1.putExtra("type", "1");
					startActivity(i1);
			
					break;
				case 1:// 淘心卡
					Intent in1 = new Intent(MyTaoczAct.this,
							TaoxinCard_Act.class);
					startActivity(in1);
					break;
				case 2:// 优惠劵
					Intent in2 = new Intent(MyTaoczAct.this, MyCouponAg.class);
					startActivity(in2);
					break;
				case 3:// 我的收藏
//					Intent intent3 = new Intent();
//					intent3.setClass(MyTaoczAct.this, FavoriteAg.class)
//							.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//					startActivity(intent3);
					
					Intent intent3 = new Intent();
					intent3.setClass(MyTaoczAct.this, FavoriteShopAct.class)
							.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent3);
			
					break;
				case 4:// 收货地址
					Intent i = new Intent();
					i.putExtra("act", "canchange");
					i.setClass(MyTaoczAct.this, ConsigneeAddressAct.class);
					startActivity(i);
					break;
				case 5:// 我的积分
					Intent i5 = new Intent();
					i5.putExtra("act", "canchange");
					i5.setClass(MyTaoczAct.this, MyjifenAct.class);
					startActivity(i5);
					break;
				default:
					break;
				}

			}
		});
		zhuxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExitLogindialog ex = new ExitLogindialog(MyTaoczAct.this);
				ex.show();
			}
		});
		shezhi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent4 = new Intent();
				intent4.setClass(MyTaoczAct.this, SystemSetupAct.class)
						.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(intent4);
			}
		});
		dataLoad(null);

	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			// ((MActivityGroup)getParent()).setCurrent(R.frame.index);
			Frame.HANDLES.get("FrameAg").get(0).sent(1, R.frame.homeindex);
		}
	}

	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("mtcz")) {
			Msg_Mtcz.Builder builder = (Msg_Mtcz.Builder) son.build;
			name.setText(F.USERNAME);
			jifen.setText("淘心卡："
					+ (builder.getCard().equals("") ? "0" : builder.getCard()));
			money.setText("余额:" + builder.getMoney() + "元");
			// if (builder.getFaceurl() != null)
			// imghead.setObj(builder.getFaceurl());
			// values[0] = builder.getMyorder();
			// values[1] = builder.getMycoin();
			// // values[2] = builder.getMydiscount();
			// values[2] = builder.getMymessage();
			// values[3] = builder.getMyaddress();
			// mData = new ArrayList<Map<String, Object>>();
			// for (int i = 0; i < titles.length; i++) {
			// Map<String, Object> map = new HashMap<String, Object>();
			// map.put("icon", icons[i]);
			// map.put("title", titles[i]);
			// if (titles[i].equals(getResources().getString(
			// R.string.myinfo_cityrecruit))) {
			// map.put("value","(在线报名,点击进入)");
			// map.put("arr", R.drawable.more_arrow);
			// } else {
			// if (i == 1)
			// map.put("value", values[i] + "币");
			// else
			// map.put("value", values[i]);
			// if (titles[i].equals(getResources().getString(
			// R.string.myinfo_gold)))
			// map.put("arr", R.drawable.more_arrow_none);
			// else {
			// map.put("arr", R.drawable.more_arrow);
			// }
			// }
			// mData.add(map);
			// }
			// sadapter = new SimpleAdapter(this, mData,
			// R.layout.item_myinfo_list, new String[] { "icon", "title",
			// "value", "arr" }, new int[] { R.item_info.icon,
			// R.item_info.title, R.item_info.value,
			// R.item_info.arr });
			//
			// listview.setAdapter(sadapter);
			// listview.setOnItemClickListener(new OnItemClickListener() {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id) {
			// switch (position) {
			// case 0:
			// Intent i1 = new Intent();
			// i1.setClass(getApplicationContext(),
			// MyOrderListAct.class);
			// startActivity(i1);
			// break;
			// case 1:
			// break;
			// // case 2:
			// // Intent i3 = new Intent();
			// // i3.setClass(getApplicationContext(),
			// // MyCouponListAct.class);
			// // startActivity(i3);
			// // break;
			// case 2:
			// Intent i2 = new Intent();
			// i2.setClass(getApplicationContext(),
			// MyMessageListAct.class);
			// startActivity(i2);
			// break;
			// case 3:
			// Intent i = new Intent();
			// i.putExtra("act", "canchange");
			// i.setClass(getApplicationContext(),
			// ConsigneeAddressAct.class);
			// startActivity(i);
			// break;
			// case 4:
			// Intent i3 = new Intent();
			// i3.putExtra("act", "canchange");
			// i3.setClass(getApplicationContext(),
			// CiytRecruitAct.class);
			// startActivity(i3);
			// break;
			// }
			// }
			// });
			//
		}
		//
		// prv.refreshComplete();
	}

	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("MTCZ",
				new String[][] { { "userid", F.USER_ID } }), });
	}
}
