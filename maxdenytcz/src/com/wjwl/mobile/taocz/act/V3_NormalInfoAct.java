package com.wjwl.mobile.taocz.act;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.MHandler;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Attribute.Msg_AttributeValue;
import com.tcz.apkfactory.data.Attribute.Msg_Store;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList;
import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList.Builder;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.DragChangeView;

public class V3_NormalInfoAct extends MActivity {
	DragChangeView mDragChangeView;
	TextView title, tczprice, oldprice, sellnum, yunfei, wuliu, qianggou_price,
			xiangou_num, shengyu_time, qianggou_guige;
	RelativeLayout clic_layout1, qianggou_linear;
	TextView num;
	private Msg_CitemList2.Builder OrderMain; // 订单
	private List<Msg_Cpic> cpiclist;
	private List<Msg_Ccomment> ccommentlist;
	private Msg_Citem citem;
	private String specid, price, flag, store, businessid, itemid,
			businessnames;
	private Msg_Cbusinessinfo cbusinessinfo;
	private Msg_Cstars cstars;
	private List<Msg_Sstandard> sstandardlist;
	private Builder mAttribute;
	View layoutStandar, layout_oprice;
	private boolean bolean = false;
	private TextView t_standard;
	Button bt_qianggou;
	Timer timer = new Timer();
	int recLen;
	public static ContentImgListAdapter iaad;
	View line5, line4;
	String qgid = "";

	@Override
	protected void create(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_shoppingnormalinfo);
		setId("V3_NormalInfoAct");
		itemid = getIntent().getStringExtra("itemid");
		
		mDragChangeView = (DragChangeView) findViewById(R.v3_shoppingnormaldetails.DragChangeView);
		title = (TextView) findViewById(R.v3_shoppingnormaldetails.title);
		tczprice = (TextView) findViewById(R.v3_shoppingnormaldetails.tczprice);
		oldprice = (TextView) findViewById(R.v3_shoppingnormaldetails.oldprice);
		sellnum = (TextView) findViewById(R.v3_shoppingnormaldetails.sellnum);
		yunfei = (TextView) findViewById(R.v3_shoppingnormaldetails.yunfei);
		wuliu = (TextView) findViewById(R.v3_shoppingnormaldetails.wuliu);
		qianggou_guige = (TextView) findViewById(R.v3_shoppingnormaldetails.qianggou_guige);
		t_standard = (TextView) findViewById(R.v3_shoppingnormaldetails.color);
		t_standard.setText("选择规格");
		bt_qianggou = (Button) findViewById(R.v3_shoppingnormaldetails.bt_qianggou);
		qianggou_price = (TextView) findViewById(R.v3_shoppingnormaldetails.qianggou_price);
		xiangou_num = (TextView) findViewById(R.v3_shoppingnormaldetails.xiangou);
		shengyu_time = (TextView) findViewById(R.v3_shoppingnormaldetails.shengyutime);
		layout_oprice = findViewById(R.v3_shoppingnormaldetails.layout2);
		line5 = findViewById(R.v3_shoppingnormaldetails.line5);
		line4 = findViewById(R.v3_shoppingnormaldetails.line4);

		layoutStandar = (RelativeLayout) findViewById(R.v3_shoppingnormaldetails.layout3);
		qianggou_linear = (RelativeLayout) findViewById(R.v3_shoppingnormaldetails.layout4);
		layoutStandar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(V3_NormalInfoAct.this,
						AttributeAct.class);
				intent.putExtra("itemid", itemid);
				intent.putExtra("flag", flag);
				intent.putExtra("specid", specid);
				startActivityForResult(intent, 1);
			}
		});
		bt_qianggou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(V3_NormalInfoAct.this, "V3_ShoppingDetailsAg",
							"B", 0);
					return;
				}
				buy2();
			}
		});
		dataLoad(new int[] { 0 });
	}

	@Override
	public void disposeMsg(int type, Object obj) {
		if (type == 1) {
			buy();
		}
		if (type == 2) {
			collection();
		}

		if (type == 86) {
			if (F.USER_ID != null && F.USER_ID.length() > 0) {
				if ("B".equals(obj)) {
					buy();
				} else if ("C".equals(obj)) {
					collection();
				} else if ("D".equals(obj)) {
					Intent intent = new Intent();
					intent.putExtra("actfrom", "V3_NormalInfoAct");
					intent.setClass(V3_NormalInfoAct.this,
							ShoppingCartAct.class);
					startActivity(intent);
				}
			}
		}
	}

	public void collection() {
		dataLoad(new int[] { 2 });
	}

	private void setAttribute(Msg_AttributeList.Builder mbuild) {
		layoutStandar.setVisibility(View.VISIBLE);
		List<Msg_Store> storelist = mbuild.getStoreList();
		List<Msg_AttributeValue> attributv = mbuild.getAttributeValueList();
		if (storelist.size() > 1) {
			StringBuffer stb = new StringBuffer();
			for (Msg_Store msg : storelist) {
				if (specid == null) {
					specid = msg.getSpecid();
				}
				if (msg.getSpecid().equals(specid)) {
					if (attributv.size() == 0) {
						layoutStandar.setVisibility(View.GONE);
						line4.setVisibility(View.GONE);
					} else if (attributv.size() >= 1) {
						stb.append(attributv.get(0).getName());
						stb.append(":");
						stb.append(msg.getFirst());

						if (attributv.size() > 1) {
							stb.append("; ");
							stb.append(attributv.get(1).getName());
							stb.append(":");
							stb.append(msg.getSecond());
						}
						t_standard.setText(stb.toString());
						line4.setVisibility(View.VISIBLE);
					}
					// tczprice.setText(msg.getPirce());
					// setOprice(msg.getOprice().length() == 0 ? "0" : msg
					// .getOprice());
				}
			}
		} else {
			bolean = true;
			layoutStandar.setVisibility(View.GONE);
			line4.setVisibility(View.GONE);
		}
	}

	public void setOprice(String price) {
		if (Double.valueOf(price) > 0) {
			layout_oprice.setVisibility(View.VISIBLE);
			oldprice.setText(price);
		} else {
			layout_oprice.setVisibility(View.GONE);
		}
	}

	public void buy2() {
		// if (bolean) {
		dataLoad(new int[] { 4 });
		// } else {
		// Intent intent = new Intent(V3_NormalInfoAct.this,
		// AttributeAct.class);
		// intent.putExtra("itemid", itemid);
		// intent.putExtra("specid", specid);
		// intent.putExtra("flag", "2");//flag
		// startActivityForResult(intent, 1);
		// }
	}

	public void buy() {
		if (bolean) {
			dataLoad(new int[] { 1 });
		} else {
			Intent intent = new Intent(V3_NormalInfoAct.this,
					AttributeAct.class);
			intent.putExtra("itemid", itemid);
			intent.putExtra("specid", specid);
			intent.putExtra("flag", flag);// flag
			startActivityForResult(intent, 1);
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		// TextView title, tczprice, oldprice, sellnum, yunfei, wuliu,
		// qianggou_price,
		// xiangou_num, shengyu_time;
		if (son != null && (son.mgetmethod.equals("scontent")|| son.mgetmethod.equals("V3_LIANGFAN_SCONTENT"))) {
			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
			cpiclist = builder.getCpiclist().getCpicList();
			iaad = new ContentImgListAdapter(this, cpiclist);
			mDragChangeView.setAdapter(iaad);
			mDragChangeView.setCurrIcon(R.drawable.yes_click);
			mDragChangeView.setMoveIcon(R.drawable.yes_click);
			mDragChangeView.setNoCurrIcon(R.drawable.no_click);
			ccommentlist = builder.getCommentlist().getCommentList();// 评论
			cbusinessinfo = builder.getCbusinessinfo();
			businessid = cbusinessinfo.getBusinessid();
			if (builder.getCitem().getPoints().equals("1"))
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0)
						.sent(5, "true");
			else if (builder.getCitem().getPoints().equals("0"))
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0)
						.sent(5, "false");
			citem = builder.getCitem();
			specid = citem.getSpecid();
			title.setText(citem.getIteminfo());// 没值
			if (specid != null && specid.length() > 0) {
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0)
						.sent(2, specid);
			}
			price = citem.getItemdiscount();
			flag = citem.getItemselltype();
			if (flag != null && flag.length() > 0) {
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(3, flag);
			}
			store = citem.getOther1();
			if (store.equals("0")) {
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(1, store);
			}
			tczprice.setText(Arith.to2zero(citem.getItemdiscount()));
			if (citem.getItemdiscount().equals("0")
					|| citem.getItemdiscount().equals("0.00"))
				tczprice.setVisibility(View.GONE);
			else
				tczprice.setVisibility(View.VISIBLE);
			citem.getItemprice();// 0.0
			oldprice.setText("￥" + citem.getItemprice());
			oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			oldprice.getPaint().setFlags(
					Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			if (citem.getItemprice().equals("0.0")
					|| citem.getItemprice().equals("0.00")) {
				oldprice.setVisibility(View.GONE);
			}
//			if ((getIntent().getStringExtra("from") != null
//					&& getIntent().getStringExtra("from").equals("index"))||(flag!=null&&flag.equals("4"))) {
              if(citem.getV3Purchaselimit().length()>0){
               qianggou_price.setText("￥" + citem.getV3Purchaseprice());
				if (citem.getV3Purchaselimit().equals("0"))
					xiangou_num.setVisibility(View.GONE);
				else
					xiangou_num.setText("每人限购" + citem.getV3Purchaselimit()
							+ "件");
				qianggou_guige.setText("规格：" + citem.getItemaddr());
				qgid = citem.getOther2();
				flag = "1";
				if (citem.getV3Remainingtime() != null
						&& citem.getV3Remainingtime().length() > 1) {
					recLen = Integer.parseInt(citem.getV3Remainingtime());
					timer.schedule(task, 1000, 1000);// 递减
					bt_qianggou
							.setBackgroundResource(R.drawable.bt_com_undisable_ped);
					LayoutParams lp = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					bt_qianggou.setLayoutParams(lp);
					bt_qianggou.setClickable(true);
					bt_qianggou.setTextColor(Color.WHITE);
					bt_qianggou.setTextSize(18);

				} else {
					shengyu_time.setText("尚未开始");
					bt_qianggou
							.setBackgroundResource(R.drawable.bt_com_disable_ped);
					LayoutParams lp = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					bt_qianggou.setText("即将开始");
					bt_qianggou.setLayoutParams(lp);
					bt_qianggou.setClickable(false);
					bt_qianggou.setTextColor(Color.WHITE);
					bt_qianggou.setTextSize(18);

				}
				qianggou_linear.setVisibility(View.VISIBLE);
			} else {
				qianggou_linear.setVisibility(View.GONE);
				line5.setVisibility(View.GONE);
			}

			wuliu.setText(Html.fromHtml(citem.getV3Freightinfo()).toString()
					.replace("&&", "\n"));
			sellnum.setText("已售:" + citem.getItemsold());
			sellnum.setVisibility(View.GONE);
			// t_standard.setText(citem.getV3Baseinfo());

			cstars = builder.getCstars();
			sstandardlist = builder.getSstandardlist().getSstandardList();
			String[] str = new String[] {
					citem.getIteminfo() + ",淘常州价:" + citem.getItemdiscount()
							+ "元", citem.getIteminfo() };
			Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(101, str);
		}

		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				// F.changecart(0);
				Toast.makeText(V3_NormalInfoAct.this, "添加成功", Toast.LENGTH_LONG)
						.show();
				dataLoad(new int[] { 3 });
			} else {
				Toast.makeText(V3_NormalInfoAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
		if (son.build != null && son.mgetmethod.equals("ofavorite")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(V3_NormalInfoAct.this, "添加收藏成功",
						Toast.LENGTH_LONG).show();
				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0)
						.sent(5, "true");
			} else {
				Toast.makeText(V3_NormalInfoAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("attribute")) {
			mAttribute = (Builder) son.build;
			setAttribute(mAttribute);
		}
		if (son.build != null && son.mgetmethod.equals("plist")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			int count = 0;
			for (int i = 0; i < OrderMain.getCitemlistList().size(); i++) {
				for (int j = 0; j < OrderMain.getCitemlist(i).getCitemList()
						.size(); j++) {
					int num = Integer.parseInt(OrderMain.getCitemlist(i)
							.getCitem(j).getItemcount());
					count += num;
				}
			}
			V3_ShoppingDetailsAg.shopping_num.setVisibility(View.VISIBLE);
			F.GOODSCOUNT = count;
			V3_ShoppingDetailsAg.shopping_num.setText(F.GOODSCOUNT + "");
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			specid = data.getStringExtra("specid");
			bolean = true;
			setAttribute(mAttribute);
		}
	}

	@Override
	public void dataLoad(int[] types) {
		if (types[0] == 0) {
			if (getIntent().getStringExtra("tejia") != null)
				this.loadData(new Updateone[] {
						new Updateone("SCONTENT", new String[][] {
								{ "itemid", itemid }, { "userid", F.USER_ID },
								{ "isindex", "1" } }),
						new Updateone("ATTRIBUTE", new String[][] { {
								"goods_id", itemid } }), });
			else if (getIntent().getStringExtra("liangfantuan") !=null)
				this.loadData(new Updateone[] {
						new Updateone("V3_LIANGFAN_SCONTENT", new String[][] {
								{ "itemid", itemid }, { "userid", F.USER_ID }}),//1989
						new Updateone("ATTRIBUTE", new String[][] { {
								"goods_id", itemid } }), });
			else
				this.loadData(new Updateone[] {
						new Updateone("SCONTENT", new String[][] {
								{ "itemid", itemid }, { "userid", F.USER_ID } }),
						new Updateone("ATTRIBUTE", new String[][] { {
								"goods_id", itemid } }), });
		}
		if (types[0] == 1) {
			if (getIntent().getStringExtra("liangfantuan") !=null){
				this.loadData(new Updateone[] { new Updateone("OPCART",
						new String[][] { { "specid", itemid },
								{ "userid", F.USER_ID },
								{ "username", F.USERNAME }, { "flg", "4" } }), });
			}else{
				this.loadData(new Updateone[] { new Updateone("OPCART",
						new String[][] { { "specid", specid },
								{ "userid", F.USER_ID },
								{ "username", F.USERNAME }, { "flg", flag } }), });
			}
			
		}
		if (types[0] == 2) {
			this.loadData(new Updateone[] { new Updateone("OFAVORITE",
					new String[][] { { "itemid", itemid },
							{ "calss", "material" }, { "userid", F.USER_ID },
							{ "price", price } }), });
		}

		if (types[0] == 3) {
			this.loadData(new Updateone[] { new Updateone("PLIST",
					new String[][] { { "userid", F.USER_ID } }), });
		}
		if (types[0] == 4) {
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", qgid },
							{ "userid", F.USER_ID },
							{ "username", F.USERNAME }, { "flg", "2" } }), });
		}
	}

	TimerTask task = new TimerTask() {
		@Override
		public void run() {

			runOnUiThread(new Runnable() { // UI thread
				@Override
				public void run() {
					recLen--;
					shengyu_time.setText(cal(recLen));
					if (recLen < 0) {
						timer.cancel();
						// 抢购时间结束，让它隐藏
						// shengyu_time.setVisibility(View.GONE);
						// qianggou_linear.setVisibility(View.GONE);
						// 抢购时间结束，不可点击
						bt_qianggou.setClickable(false);
						bt_qianggou
								.setBackgroundResource(R.drawable.un_clickable);
					}
				}
			});
		}
	};

	public static String cal(int second) {// 将秒转换为时分秒
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		return h + "时" + d + "分" + s + "秒";
	}
}
