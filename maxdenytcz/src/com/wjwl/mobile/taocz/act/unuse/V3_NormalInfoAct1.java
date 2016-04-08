package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Html;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.base.Retn.Msg_Retn;
//import com.mdx.mobile.manage.MHandler;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Attribute.Msg_AttributeValue;
//import com.tcz.apkfactory.data.Attribute.Msg_Store;
//import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList;
//import com.tcz.apkfactory.data.AttributeList.Msg_AttributeList.Builder;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.CitemList2.Msg_CitemList2;
//import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
//import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
//import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
//import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
//import com.wjwl.mobile.taocz.commons.Arith;
//import com.wjwl.mobile.taocz.widget.DragChangeView;
//
//public class V3_NormalInfoAct1 extends MActivity {
//	DragChangeView mDragChangeView;
//	TextView title, tczprice, oldprice, sellnum, yunfei, wuliu, qianggou_price,
//			xiangou_num, shengyu_time;
//	RelativeLayout clic_layout1,qianggou_linear;
//	TextView num;
//	private Msg_CitemList2.Builder OrderMain; // 订单
//	private List<Msg_Cpic> cpiclist;
//	private List<Msg_Ccomment> ccommentlist;
//	private Msg_Citem citem;
//	private String specid, price, flag, store, businessid, itemid,
//			businessnames;
//	private Msg_Cbusinessinfo cbusinessinfo;
//	private Msg_Cstars cstars;
//	private List<Msg_Sstandard> sstandardlist;
//	private Builder mAttribute;
//	View layoutStandar, layout_oprice;
//	private boolean bolean = false;
//	private TextView t_standard;
//	Button bt_qianggou;
//	Timer timer=new Timer();
//	int recLen;
//	
//	@Override
//	protected void create(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.v3_shoppingnormalinfo);
//		setId("V3_NormalInfoAct1");
//		itemid=getIntent().getStringExtra("itemid");
//		mDragChangeView = (DragChangeView) findViewById(R.v3_shoppingnormaldetails.DragChangeView);
//		title = (TextView) findViewById(R.v3_shoppingnormaldetails.title);
//		tczprice = (TextView) findViewById(R.v3_shoppingnormaldetails.tczprice);
//		oldprice = (TextView) findViewById(R.v3_shoppingnormaldetails.oldprice);
//		sellnum = (TextView) findViewById(R.v3_shoppingnormaldetails.sellnum);
//		yunfei = (TextView) findViewById(R.v3_shoppingnormaldetails.yunfei);
//		wuliu = (TextView) findViewById(R.v3_shoppingnormaldetails.wuliu);
//		t_standard = (TextView) findViewById(R.v3_shoppingnormaldetails.color);
//		t_standard.setText("选择规格");
//		bt_qianggou = (Button) findViewById(R.v3_shoppingnormaldetails.bt_qianggou);
//		qianggou_price = (TextView) findViewById(R.v3_shoppingnormaldetails.qianggou_price);
//		xiangou_num = (TextView) findViewById(R.v3_shoppingnormaldetails.xiangou);
//		shengyu_time = (TextView) findViewById(R.v3_shoppingnormaldetails.shengyutime);
//		shengyu_time = (TextView) findViewById(R.v3_shoppingnormaldetails.shengyutime);
//		layout_oprice = findViewById(R.v3_shoppingnormaldetails.layout2);
//
//		layoutStandar = (RelativeLayout) findViewById(R.v3_shoppingnormaldetails.layout3);
//		qianggou_linear = (RelativeLayout) findViewById(R.v3_shoppingnormaldetails.layout4);
//		layoutStandar.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(V3_NormalInfoAct1.this,
//						AttributeAct.class);
//				intent.putExtra("itemid", itemid);
//				intent.putExtra("specid", specid);
//				startActivityForResult(intent, 1);
//			}
//		});
//		bt_qianggou.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//		dataLoad(new int[]{0});
//	}
//	
//	
//	public void disposeMsg(int type, Object obj) {
//		if (type == 86) {
//			if (F.USER_ID != null && F.USER_ID.length() > 0) {
//				if ("B".equals(obj)) {
//					buy();
//				} else if ("C".equals(obj)) {
//					collection();
//				}
//			}
//		}
//	}
//	
//	
//	public void buy() {
//		Intent i = new Intent();
//		i.putExtra("specid", specid);
//		i.setClass(getApplication(), ServiceConfirmAct.class);
//		startActivity(i);
//	}
//	
//	
//	public void collection() {
//		dataLoad(new int[] { 2 });
//	}
//	
//
//	public void setOprice(String price) {
//		if (Double.valueOf(price) > 0) {
//			layout_oprice.setVisibility(View.VISIBLE);
//			oldprice.setText(price);
//		} else {
//			layout_oprice.setVisibility(View.GONE);
//		}
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
////	TextView title, tczprice, oldprice, sellnum, yunfei, wuliu, qianggou_price,
////	xiangou_num, shengyu_time;
//		if (son != null && son.mgetmethod.equals("scontent")) {
//			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
//			cpiclist = builder.getCpiclist().getCpicList();
//			ContentImgListAdapter iaad = new ContentImgListAdapter(this,
//					cpiclist);
//			mDragChangeView.setAdapter(iaad);
//			mDragChangeView.setCurrIcon(R.drawable.yes_click);
//			mDragChangeView.setMoveIcon(R.drawable.yes_click);
//			mDragChangeView.setNoCurrIcon(R.drawable.no_click);
//			ccommentlist = builder.getCommentlist().getCommentList();//评论
//			cbusinessinfo = builder.getCbusinessinfo();
//			title.setText(cbusinessinfo.getBusinessname());
//			businessid = cbusinessinfo.getBusinessid();
//
//			citem = builder.getCitem();
//			specid = citem.getSpecid();
//			if(specid!=null&&specid.length()>0){
//				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(2, specid);
//			}
//			price = citem.getItemdiscount();
//			flag = citem.getItemselltype();
//			if(flag!=null&&flag.length()>0){
//				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(3, flag);
//			}
//			store = citem.getOther1();
//			if (store.equals("0")) {
//				Frame.HANDLES.get("V3_ShoppingDetailsAg").get(0).sent(1, store);
//			}
//			tczprice.setText(Arith.to2zero(citem.getItemdiscount()));
//			if (citem.getItemdiscount().equals("0")
//					|| citem.getItemdiscount().equals("0.00"))
//				tczprice.setVisibility(View.GONE);
//			else
//				tczprice.setVisibility(View.VISIBLE);
//			citem.getItemprice();//0.0
//			oldprice.setText(citem.getItemprice());
////			setOprice(citem.getItemprice().length() == 0 ? "0.00" : Arith.to2zero(citem
////					.getItemprice()));
//			if(citem.getV3Remainingtime()!=null&&citem.getV3Remainingtime().length()>0){
//				qianggou_price.setText("￥"+citem.getV3Purchaseprice());
//				xiangou_num.setText("每人限购"+citem.getV3Purchaselimit()+"件");
////				shengyu_time.setText(citem.getV3Remainingtime());
//				recLen=Integer.parseInt(citem.getV3Remainingtime());
//			}else{
//				qianggou_linear.setVisibility(View.GONE);
//			}
//			wuliu.setText(Html.fromHtml(citem.getV3Freightinfo()));
//			sellnum.setText(citem.getItemsold());
////			t_standard.setText(citem.getV3Baseinfo());
//			
//			cstars = builder.getCstars();
//			sstandardlist = builder.getSstandardlist().getSstandardList();
//		}
//		
//		if (son.build != null && son.mgetmethod.equals("opcart")) {
//			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
//			if (retn.getErrorCode() == 0) {
//				//F.changecart(0);
//				Toast.makeText(V3_NormalInfoAct1.this, "添加成功",
//						Toast.LENGTH_LONG).show();
//				dataLoad(new int []{3});
//			} else {
//				Toast.makeText(V3_NormalInfoAct1.this, retn.getErrorMsg(),
//						Toast.LENGTH_LONG).show();
//			}
//			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
//				hand.reload();
//			}
//		}
//		if (son.build != null && son.mgetmethod.equals("ofavorite")) {
//			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
//			if (retn.getErrorCode() == 0) {
//				Toast.makeText(V3_NormalInfoAct1.this, "添加收藏成功",
//						Toast.LENGTH_LONG).show();
//			} else {
//				Toast.makeText(V3_NormalInfoAct1.this, retn.getErrorMsg(),
//						Toast.LENGTH_LONG).show();
//			}
//		}
//
//		
//	}
//
//	
//	@Override
//	public void dataLoad(int[] types) {
//		if (types != null) {
//			if (types[0] == 2) {
//				this.loadData(new Updateone[] { new Updateone("OFAVORITETG",
//						new String[][] { { "itemid", itemid },
//								{ "calss", "service" },
//								{ "userid", F.USER_ID }, { "price", price } }), });
//			}
//		} else {
//			this.loadData(new Updateone[] { new Updateone("LCONTENT",
//					new String[][] { { "itemid", itemid } }, Msg_Scontent
//							.newBuilder()), });
//		}
//
//	}
//
//}
