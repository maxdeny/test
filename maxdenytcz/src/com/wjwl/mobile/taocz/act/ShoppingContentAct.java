package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
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

public class ShoppingContentAct extends MActivity {

	private DragChangeView mDragChangeView;
	String itemid, businessid, businessname,store;
	List<Msg_Cpic> cpiclist;
	private Msg_CitemList2.Builder OrderMain; // 订单
	Msg_Citem citem;
	Msg_AttributeList.Builder mAttribute;
	Msg_Cbusinessinfo cbusinessinfo;
	Msg_Cstars cstars;
	List<Msg_Ccomment> ccommentlist;
	List<Msg_Sstandard> sstandardlist;
	RelativeLayout lay_comment, lay_shopinfo, lay_itemdetails;
	View layoutStandar, layout_oprice;
	StringBuffer standardval = new StringBuffer();
	TextView t_intr, t_price, t_oldprice, t_limit, t_overtime, t_comment,
			t_comment_name, t_commment_time, t_standard, t_productinfo,
			t_commtent, t_commtentcontent, t_shopname, t_matchstar, t_service,
			t_logistics,shopping_num;
	ImageView star_11, star_12, star_13, star_14, star_15, star_21, star_22,
			star_23, star_24, star_25, star_31, star_32, star_33, star_34,
			star_35;
	Button bt_buy, bt_collection, bt_tejia,bt_back,bt_shopcart;
	String specid, price,flag;
	private boolean bolean = false;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.productinfo_content);
		bt_shopcart=(Button) this.findViewById(R.proinfo.bt_shopcart);
		shopping_num=(TextView) this.findViewById(R.proinfo.shopping_num);
		t_intr = (TextView) this.findViewById(R.proinfo.introduction);
		t_price = (TextView) this.findViewById(R.proinfo.newprice);
		t_oldprice = (TextView) this.findViewById(R.proinfo.oldprice);
		t_limit = (TextView) this.findViewById(R.proinfo.limit);
		t_overtime = (TextView) this.findViewById(R.proinfo.overtime);
		t_commtentcontent = (TextView) this
				.findViewById(R.proinfo.commtentcontent);
		t_comment_name = (TextView) this.findViewById(R.proinfo.commentname);
		t_commment_time = (TextView) this.findViewById(R.proinfo.commtenttime);
		this.setId("ShoppingContentAct");
		t_standard = (TextView) this.findViewById(R.proinfo.standard);
		t_productinfo = (TextView) this.findViewById(R.proinfo.productinfo);
		t_commtent = (TextView) this.findViewById(R.proinfo.commtent);
		t_shopname = (TextView) this.findViewById(R.proinfo.shopname);
		t_matchstar = (TextView) this.findViewById(R.proinfo.matchstar_text);
		t_service = (TextView) this.findViewById(R.proinfo.service_text);
		t_logistics = (TextView) this.findViewById(R.proinfo.logistics_text);
		bt_tejia = (Button) findViewById(R.proinfo.bt_tejia);
		bt_buy = (Button) findViewById(R.proinfo.bt_buy);
		bt_back =(Button)findViewById(R.proinfo.back);
		bt_back.setOnClickListener(new layoutListener());
		bt_collection = (Button) findViewById(R.proinfo.bt_collection);
		itemid = getIntent().getStringExtra("itemid");
		layoutStandar = findViewById(R.proinfo.clic_layout1);
		layout_oprice = findViewById(R.proinfo.layout2);
		lay_shopinfo = (RelativeLayout) this
				.findViewById(R.proinfo.clic_layout5);
		lay_comment = (RelativeLayout) this
				.findViewById(R.proinfo.clic_layout3);
		lay_itemdetails = (RelativeLayout) this
				.findViewById(R.proinfo.clic_layout2);
		init_star();
		bt_collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(ShoppingContentAct.this, "ShoppingContentAct",
							"C", 0);
					return;
				}
				collection();
			}
		});

		bt_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(ShoppingContentAct.this, "ShoppingContentAct",
							"B", 0);
					return;
				}
				buy();
			}
		});
		
		bt_shopcart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(ShoppingContentAct.this, "ShoppingContentAct",
							"D", 0);
					return;
				}
				Intent intent = new Intent();
				intent.putExtra("actfrom", "ShoppingCartAct");
				intent.setClass(ShoppingContentAct.this, ShoppingCartAct.class);
				startActivity(intent);
			}
		});

		mDragChangeView = (DragChangeView) findViewById(R.proinfo.DragChangeView);
		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setHideRadio(false);
		mDragChangeView.setAutoMove(false);
		mDragChangeView.setRadius(7);
		layoutStandar.setVisibility(View.GONE);
		dataLoad(new int[] { 0 });
		
		if(!F.USER_ID.equals("")){
			shopping_num.setVisibility(View.VISIBLE);
			dataLoad(new int[] { 3 });
		}
	}

	private void init_star() {
		star_11 = (ImageView) findViewById(R.proinfo.match_star1);
		star_12 = (ImageView) findViewById(R.proinfo.match_star2);
		star_13 = (ImageView) findViewById(R.proinfo.match_star3);
		star_14 = (ImageView) findViewById(R.proinfo.match_star4);
		star_15 = (ImageView) findViewById(R.proinfo.match_star5);
		star_21 = (ImageView) findViewById(R.proinfo.service_star1);
		star_22 = (ImageView) findViewById(R.proinfo.service_star2);
		star_23 = (ImageView) findViewById(R.proinfo.service_star3);
		star_24 = (ImageView) findViewById(R.proinfo.service_star4);
		star_25 = (ImageView) findViewById(R.proinfo.service_star5);
		star_31 = (ImageView) findViewById(R.proinfo.logistics_star1);
		star_32 = (ImageView) findViewById(R.proinfo.logistics_star2);
		star_33 = (ImageView) findViewById(R.proinfo.logistics_star3);
		star_34 = (ImageView) findViewById(R.proinfo.logistics_star4);
		star_35 = (ImageView) findViewById(R.proinfo.logistics_star5);
	}

	private void setStar(int i, int type) {
		switch (i) {
		case 0:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_gray);
			}
			break;
		case 1:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_gray);
			}
			break;
		case 2:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_gray);
			}
			break;
		case 3:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_gray);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_gray);
			}
			break;
		case 4:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_gray);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_gray);
			}
			break;
		case 5:
			if (type == 1) {
				star_11.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_12.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_13.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_14.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_15.setBackgroundResource(R.drawable.sceniclist_star_red);
			} else if (type == 2) {
				star_21.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_22.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_23.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_24.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_25.setBackgroundResource(R.drawable.sceniclist_star_red);
			} else if (type == 3) {
				star_31.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_32.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_33.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_34.setBackgroundResource(R.drawable.sceniclist_star_red);
				star_35.setBackgroundResource(R.drawable.sceniclist_star_red);
			}
			break;

		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("scontent")) {
			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
			cpiclist = builder.getCpiclist().getCpicList();//图片
			ContentImgListAdapter iaad = new ContentImgListAdapter(this,
					cpiclist);
			mDragChangeView.setAdapter(iaad);
			
			ccommentlist = builder.getCommentlist().getCommentList();//评论
			if (ccommentlist.size() != 0) {
				t_commtentcontent.setText(ccommentlist.get(0)
						.getCommentcontent());
				t_comment_name.setText(ccommentlist.get(0).getCommentpeople());
				t_commment_time.setText(ccommentlist.get(0).getCommenttime());
			}else
				t_commtentcontent.setText("暂无评论！");
			
			cbusinessinfo = builder.getCbusinessinfo();
			// t_productinfo.setText(citem.getItemdetails());
			businessname = cbusinessinfo.getBusinessname();
			t_shopname.setText(businessname);
			businessid = cbusinessinfo.getBusinessid();
			
			citem = builder.getCitem();
			specid = citem.getSpecid();
			price = citem.getItemdiscount();
			flag=citem.getItemselltype();//是否是抢购商品
			store=citem.getOther1();
			if(store.equals("0")){
				bt_buy.setClickable(false);
				bt_buy.setText("售完");
			}
			
			cstars = builder.getCstars();
			sstandardlist = builder.getSstandardlist().getSstandardList();
			t_intr.setText(citem.getIteminfo());
			if (citem.getItemtejia().trim().equals("1"))
				bt_tejia.setText("特价");
			else
				bt_tejia.setText("优惠价");
			t_price.setText(Arith.to2zero(citem.getItemdiscount()));
			if (citem.getItemdiscount().equals("0")
					|| citem.getItemdiscount().equals("0.00"))
				t_price.setVisibility(View.GONE);
			else
				t_price.setVisibility(View.VISIBLE);
			setOprice(citem.getItemprice().length() == 0 ? "0.00" : Arith.to2zero(citem
					.getItemprice()));
			
			if(citem.getItemlimited().equals("")||citem.getItemlimited().equals("999")){
				t_limit.setText("库存" + citem.getOther1() + "件");
			}else{
				t_limit.setText("每人限购" + citem.getItemlimited() + "件");
			}
			
			//t_limit.setText("每人限购" + citem.getItemlimited() + "件");
			if(citem.getItemremtime().equals("0")||citem.getItemremtime().equals(""))
				t_overtime.setVisibility(View.GONE);
			else
				t_overtime.setVisibility(View.VISIBLE);
			t_overtime.setText("剩余" + citem.getItemremtime() + "结束");
			
			if (cstars.getServicestar() == null
					|| cstars.getServicestar().trim() == "") {
				t_service.setText("0");
				setStar(0, 1);
			} else {
				t_service.setText(cstars.getServicestar());
				double ss = Double
						.valueOf(cstars.getMatchstar().trim() == "" ? "0"
								: cstars.getMatchstar().trim());
				setStar((int) ss, 1);
			}
			if (cstars.getMatchstar() == null
					|| cstars.getMatchstar().trim() == "") {
				t_matchstar.setText("0");
				setStar(0, 2);
			} else {
				t_matchstar.setText(cstars.getMatchstar());
				double ss = Double.valueOf(cstars.getServicestar().trim());
				setStar((int) ss, 2);
			}
			if (cstars.getLogisticsstar() == null
					|| cstars.getLogisticsstar().trim() == "") {
				t_logistics.setText("0");
				setStar(0, 3);
			} else {
				t_logistics.setText(cstars.getLogisticsstar());
				double ss = Double.valueOf(cstars.getLogisticsstar().trim());
				setStar((int) ss, 3);
			}
			t_standard.setText("选择规格");
			lay_comment.setOnClickListener(new layoutListener());
			lay_shopinfo.setOnClickListener(new layoutListener());
			layoutStandar.setOnClickListener(new layoutListener());
			lay_itemdetails.setOnClickListener(new layoutListener());
			
		}
		if (son.build != null && son.mgetmethod.equals("opcart")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				//F.changecart(0);
				Toast.makeText(ShoppingContentAct.this, "添加成功",
						Toast.LENGTH_LONG).show();
				dataLoad(new int []{3});
			} else {
				Toast.makeText(ShoppingContentAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
			for (MHandler hand : Frame.HANDLES.get("ShoppingCartAct")) {
				hand.reload();
			}
		}
		if (son.build != null && son.mgetmethod.equals("ofavorite")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(ShoppingContentAct.this, "添加收藏成功",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(ShoppingContentAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("attribute")) {
			mAttribute = (Builder) son.build;
			setAttribute(mAttribute);
		}
		if (son.build != null && son.mgetmethod.equals("plist")) {
			OrderMain = (Msg_CitemList2.Builder) son.build;
			int count=0;
			for(int i=0;i<OrderMain.getCitemlistList().size();i++){
				for(int j=0;j<OrderMain.getCitemlist(i).getCitemList().size();j++){
					int num = Integer.parseInt(OrderMain.getCitemlist(i).getCitem(j).getItemcount());
					count += num;
				}
			}
			shopping_num.setVisibility(View.VISIBLE);
			F.GOODSCOUNT = count;
			shopping_num.setText(F.GOODSCOUNT +"");
		}
			
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
					}
					t_price.setText(msg.getPirce());
					setOprice(msg.getOprice().length() == 0 ? "0" : msg
							.getOprice());
				}
			}
		} else {
			bolean = true;
			layoutStandar.setVisibility(View.GONE);
		}
	}

	public void setOprice(String price) {
		if (Double.valueOf(price) > 0) {
			layout_oprice.setVisibility(View.VISIBLE);
			t_oldprice.setText(price);
		} else {
			layout_oprice.setVisibility(View.GONE);
		}
	}

	public class layoutListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.proinfo.clic_layout3:
				if (ccommentlist.size() == 0 || ccommentlist == null)
					Toast.makeText(ShoppingContentAct.this, "该商品暂无评论",
							Toast.LENGTH_SHORT).show();
				else {
					Intent i = new Intent(ShoppingContentAct.this,
							CommentListAct.class);
					i.putExtra("commentFrom", "material");
					i.putExtra("itemid", itemid);
					startActivity(i);
				}
				break;
			case R.proinfo.clic_layout5:
				Intent i1 = new Intent();
				i1.putExtra("businessid", businessid);
				i1.putExtra("businessname", businessname);
				i1.setClass(ShoppingContentAct.this, BusinessShopAct.class);
				startActivity(i1);
				break;
			case R.proinfo.clic_layout1:
				Intent intent = new Intent(ShoppingContentAct.this,
						AttributeAct.class);
				intent.putExtra("itemid", itemid);
				intent.putExtra("specid", specid);
				startActivityForResult(intent, 1);
				break;
			case R.proinfo.clic_layout2:
				Intent intent2 = new Intent(ShoppingContentAct.this,
						ItemwbAct.class);
				intent2.putExtra("itemid", itemid);
				intent2.putExtra("itemtype", "shop");
				startActivity(intent2);
				break;
			case R.proinfo.back:
				ShoppingContentAct.this.finish();
			}
		}
	}

	public void disposeMsg(int type, Object obj) {
		if (type == 86) {
			if (F.USER_ID != null && F.USER_ID.length() > 0) {
				if ("B".equals(obj)) {
					buy();
				} else if ("C".equals(obj)) {
					collection();
				} else if ("D".equals(obj)) {
					Intent intent = new Intent();
					intent.putExtra("actfrom", "ShoppingCartAct");
					intent.setClass(ShoppingContentAct.this, ShoppingCartAct.class);
					startActivity(intent);
				}
			}
		}
	}

	public void buy() {
		if (bolean) {
			dataLoad(new int[] { 1 });
		} else {
			Intent intent = new Intent(ShoppingContentAct.this,
					AttributeAct.class);
			intent.putExtra("itemid", itemid);
			intent.putExtra("specid", specid);
			startActivityForResult(intent, 1);
		}
	}

	public void collection() {
		dataLoad(new int[] { 2 });
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
			this.loadData(new Updateone[] {
					new Updateone("SCONTENT", new String[][] { { "itemid",
							itemid } }),
					new Updateone("ATTRIBUTE", new String[][] { { "goods_id",
							itemid } }), });
		}
		if (types[0] == 1) {
			this.loadData(new Updateone[] { new Updateone("OPCART",
					new String[][] { { "specid", specid },
							{ "userid", F.USER_ID }, { "flg", flag} }), });
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
	}
	@Override
	protected void onResume() {
		super.onResume();
		shopping_num.setText(F.GOODSCOUNT +"");
	}
}
