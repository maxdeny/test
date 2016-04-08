package com.wjwl.mobile.taocz.act;

import java.util.List;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
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
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
import com.wjwl.mobile.taocz.commons.Arith;
import com.wjwl.mobile.taocz.widget.DragChangeView;

public class LifeContentAct extends MActivity {
	private DragChangeView mDragChangeView;
	public static ContentImgListAdapter iaad;
	String itemid, businessid, businessname, price, specid;
	List<Msg_Cpic> cpiclist;
	Msg_Citem citem;
	Msg_Cbusinessinfo cbusinessinfo;
	Msg_Cstars cstars;
	List<Msg_Ccomment> ccommentlist;
	RelativeLayout lay_comment, lay_shopinfo, lay_listdetails, lay_needknow;
	Button bt_collection, bt_buy, bt_back;
	private View tv_oldprice;
	TextView t_intr, t_price, t_oldprice, t_ordernum, t_limit, t_overtime,
			t_comment, t_comment_name, t_needknow, t_commment_time, t_shopname,
			t_service, t_environment, t_pricecost, itemname, iteminfo;
	ImageView star_11, star_12, star_13, star_14, star_15, star_21, star_22,
			star_23, star_24, star_25, star_31, star_32, star_33, star_34,
			star_35;

	@Override
	protected void create(Bundle arg0) {
		setContentView(R.layout.life_content);
		this.setId("LifeContentAct");
		t_intr = (TextView) this.findViewById(R.life.introduction);
		t_price = (TextView) this.findViewById(R.life.price);
		t_oldprice = (TextView) this.findViewById(R.life.oldprice);
		t_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		t_oldprice.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		t_ordernum = (TextView) this.findViewById(R.life.ordernum);
		t_limit = (TextView) this.findViewById(R.life.limit);
		t_overtime = (TextView) this.findViewById(R.life.overtime);
		t_comment = (TextView) this.findViewById(R.life.commtent);
		t_comment_name = (TextView) this.findViewById(R.life.commentname);
		t_commment_time = (TextView) this.findViewById(R.life.commtenttime);
		t_needknow = (TextView) this.findViewById(R.life.needknow);
		t_shopname = (TextView) this.findViewById(R.life.shopname);
		t_service = (TextView) this.findViewById(R.life.service_text);
		t_environment = (TextView) this.findViewById(R.life.environment_text);
		tv_oldprice = this.findViewById(R.life.tv_oldprice);
		t_pricecost = (TextView) this.findViewById(R.life.pricecost_text);
		itemid = getIntent().getStringExtra("itemid");
		lay_shopinfo = (RelativeLayout) this.findViewById(R.life.clic_layout2);
		lay_listdetails = (RelativeLayout) this
				.findViewById(R.life.clic_layout1);
		lay_comment = (RelativeLayout) this
				.findViewById(R.life.layout_commtent);
		bt_collection = (Button) findViewById(R.life.bt_collection);
		bt_buy = (Button) findViewById(R.life.bt_buy);
		bt_back = (Button) findViewById(R.life.back);
		bt_back.setOnClickListener(new layoutListener());
		lay_needknow = (RelativeLayout) this
				.findViewById(R.life.clic_needknowlayout);
		init_star();
		mDragChangeView = (DragChangeView) findViewById(R.life_content.DragChangeView);
		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setHideRadio(false);
		mDragChangeView.setRadius(7);
		iteminfo = (TextView) this.findViewById(R.life.iteminfo);
		itemname = (TextView) this.findViewById(R.life.itemname);

		dataLoad(null);
	}

	private void init_star() {
		// TODO Auto-generated method stub
		star_11 = (ImageView) findViewById(R.life.service_star1);
		star_12 = (ImageView) findViewById(R.life.service_star2);
		star_13 = (ImageView) findViewById(R.life.service_star3);
		star_14 = (ImageView) findViewById(R.life.service_star4);
		star_15 = (ImageView) findViewById(R.life.service_star5);
		star_21 = (ImageView) findViewById(R.life.environment_star1);
		star_22 = (ImageView) findViewById(R.life.environment_star2);
		star_23 = (ImageView) findViewById(R.life.environment_star3);
		star_24 = (ImageView) findViewById(R.life.environment_star4);
		star_25 = (ImageView) findViewById(R.life.environment_star5);
		star_31 = (ImageView) findViewById(R.life.pricecost_star1);
		star_32 = (ImageView) findViewById(R.life.pricecost_star2);
		star_33 = (ImageView) findViewById(R.life.pricecost_star3);
		star_34 = (ImageView) findViewById(R.life.pricecost_star4);
		star_35 = (ImageView) findViewById(R.life.pricecost_star5);
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
		if (son.build != null && son.mgetmethod.equals("lcontent")) {
			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
			cpiclist = builder.getCpiclist().getCpicList();
			ccommentlist = builder.getCommentlist().getCommentList();
			citem = builder.getCitem();
			cbusinessinfo = builder.getCbusinessinfo();
			cstars = builder.getCstars();
			price = citem.getItemdiscount();
			t_intr.setText(citem.getIteminfo());
			t_price.setText(Arith.to2zero(citem.getItemdiscount().replace(
					".00", "0.0")));
			t_oldprice
					.setText("￥"
							+ Arith.to2zero(citem.getItemprice().replace(".00",
									"0.0")));
			// t_ordernum.setText("共" + citem.getItemsold() + "人订购");
			t_ordernum.setText("已售：" + citem.getItemsold());
			if (builder.getCitem().getPoints().equals("1"))
				Frame.HANDLES.get("GroupBuyContentsAct").get(0).sent(5, "true");
			else if (builder.getCitem().getPoints().equals("0"))
				Frame.HANDLES.get("GroupBuyContentsAct").get(0)
						.sent(5, "false");
			if (citem.getItemlimited().equals("")) {
				if (!citem.getOther1().equals("")) {
					t_limit.setText("库存" + citem.getOther1() + "件");
				}
			} else {
				t_limit.setText("每人限购" + citem.getItemlimited() + "件");
			}
			if (citem.getItemremtime().equals("0")
					|| citem.getItemremtime().equals(""))
				t_overtime.setVisibility(View.GONE);
			else
				t_overtime.setVisibility(View.VISIBLE);
			t_overtime.setText("剩余" + citem.getItemremtime() + "结束");
			specid = citem.getSpecid();
			if (ccommentlist.size() != 0) {
				t_comment.setText(ccommentlist.get(0).getCommentcontent());
				t_comment_name.setText(ccommentlist.get(0).getCommentpeople());
				t_commment_time.setText(ccommentlist.get(0).getCommenttime());
			} else
				t_comment.setText("暂无评论!");
			businessname = cbusinessinfo.getBusinessname();
			t_shopname.setText(businessname);
			businessid = cbusinessinfo.getBusinessid();
			if (cstars.getServicestar() == null
					|| cstars.getServicestar().trim() == "") {
				t_service.setText("0");
				setStar(0, 1);
			} else {
				t_service.setText(cstars.getServicestar());
				setStar(Integer.valueOf(cstars.getServicestar().trim()), 1);
			}
			if (cstars.getEnvironmentstar() == null
					|| cstars.getEnvironmentstar().trim() == "") {
				t_environment.setText("0");
				setStar(0, 2);
			} else {
				t_environment.setText(cstars.getEnvironmentstar());
				setStar(Integer.valueOf(cstars.getEnvironmentstar().trim()), 2);
			}
			if (cstars.getPricestar() == null
					|| cstars.getPricestar().trim() == "") {
				t_pricecost.setText("0");
				setStar(0, 3);
			} else {
				t_pricecost.setText(cstars.getPricestar());
				setStar(Integer.valueOf(cstars.getPricestar().trim()), 3);
			}

			iteminfo.setText(Html.fromHtml(citem.getV3Baseinfo()));
			itemname.setText(citem.getIteminfo());

			lay_comment.setOnClickListener(new layoutListener());
			lay_shopinfo.setOnClickListener(new layoutListener());
			lay_listdetails.setOnClickListener(new layoutListener());
			lay_needknow.setOnClickListener(new layoutListener());
			bt_collection.setOnClickListener(new layoutListener());
			bt_buy.setOnClickListener(new layoutListener());
			iaad = new ContentImgListAdapter(this, cpiclist);
			mDragChangeView.setAdapter(iaad);
			String[] str = new String[] {
					citem.getIteminfo() + ",淘常州价:" + citem.getItemdiscount()
							+ "元", citem.getIteminfo() };
			Frame.HANDLES.get("GroupBuyContentsAct").get(0).sent(101, str);
		}
		if (son.build != null && son.mgetmethod.equals("ofavoritetg")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(LifeContentAct.this, "添加收藏成功", Toast.LENGTH_LONG)
						.show();
				Frame.HANDLES.get("GroupBuyContentsAct").get(0).sent(5, "true");

			} else {
				Toast.makeText(LifeContentAct.this, retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public class layoutListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.life.layout_commtent:
				if (ccommentlist.size() == 0 || ccommentlist == null)
					Toast.makeText(LifeContentAct.this, "该商品暂无评论",
							Toast.LENGTH_SHORT).show();
				else {
					Intent i = new Intent(LifeContentAct.this,
							CommentListAct.class);
					// i.putExtra("businessid", businessid);
					i.putExtra("commentFrom", "service");
					i.putExtra("itemid", itemid);
					startActivity(i);
				}
				break;
			case R.life.clic_layout2:
				Intent i1 = new Intent();
				i1.putExtra("businessname", businessname);
				i1.putExtra("businessid", businessid);
				i1.putExtra("act", "LifeContentAct");
				i1.setClass(LifeContentAct.this, GroupBuyingListAct.class);
				startActivity(i1);
				break;
			case R.life.clic_layout1:
				Intent intent2 = new Intent(LifeContentAct.this,
						ItemwbAct.class);
				intent2.putExtra("itemid", itemid);
				intent2.putExtra("itemtype", "life");
				startActivity(intent2);
				break;
			case R.life.clic_needknowlayout:
				Intent intent3 = new Intent(LifeContentAct.this,
						ItemwbAct.class);
				intent3.putExtra("itemid", itemid);
				intent3.putExtra("itemtype", "need");
				startActivity(intent3);
				break;
			case R.life.bt_collection:
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(LifeContentAct.this, "LifeContentAct", "C", 0);
					return;
				}
				collection();
				break;
			case R.life.bt_buy:
				if (F.USER_ID == null || F.USER_ID.length() == 0) {
					F.toLogin(LifeContentAct.this, "LifeContentAct", "B", 0);
				} else {
					buy();
				}
				break;
			case R.life.back:
				LifeContentAct.this.finish();
				break;
			}
		}
	}

	public void setOprice(String price) {
		if (Double.valueOf(price) > 0) {
			t_oldprice.setVisibility(View.VISIBLE);
			tv_oldprice.setVisibility(View.VISIBLE);
			t_oldprice.setText(price);
		} else {
			t_oldprice.setVisibility(View.GONE);
			tv_oldprice.setVisibility(View.GONE);
		}
	}

	public void buy() {
		Intent i = new Intent();
		i.putExtra("specid", specid);
		i.setClass(getApplication(), ServiceConfirmAct.class);
		startActivity(i);
	}

	public void collection() {
		dataLoad(new int[] { 2 });
	}

	public void disposeMsg(int type, Object obj) {
		if (type == 86) {
			if (F.USER_ID != null && F.USER_ID.length() > 0) {
				if ("B".equals(obj)) {
					buy();
				} else if ("C".equals(obj)) {
					collection();
				}
			}
		}

		if (type == 1) {
			buy();
		}
		if (type == 2) {
			collection();
		}

	}

	@Override
	public void dataLoad(int[] types) {
		if (types != null) {
			if (types[0] == 2) {
				this.loadData(new Updateone[] { new Updateone("OFAVORITETG",
						new String[][] { { "itemid", itemid },
								{ "calss", "service" },
								{ "userid", F.USER_ID }, { "price", price } }), });
			}
		} else {
			this.loadData(new Updateone[] { new Updateone("LCONTENT",
					new String[][] { { "itemid", itemid },
							{ "userid", F.USER_ID } }, Msg_Scontent
							.newBuilder()), });
		}

	}

}
