package com.wjwl.mobile.taocz.act.unuse;
//package com.wjwl.mobile.taocz.act;
//
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.activity.MActivity;
//import com.mdx.mobile.manage.Updateone;
//import com.mdx.mobile.server.Son;
//import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
//import com.tcz.apkfactory.data.Ccomment.Msg_Ccomment;
//import com.tcz.apkfactory.data.Citem.Msg_Citem;
//import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
//import com.tcz.apkfactory.data.Cstars.Msg_Cstars;
//import com.tcz.apkfactory.data.Scontent.Msg_Scontent;
//import com.tcz.apkfactory.data.Sstandard.Msg_Sstandard;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.adapter.ContentImgListAdapter;
//import com.wjwl.mobile.taocz.commons.Arith;
//import com.wjwl.mobile.taocz.widget.DragChangeView;
//
//public class TakeOutContentAct extends MActivity {
//
//	private DragChangeView mDragChangeView;
//	String itemid, businessid, businessname;
//	List<Msg_Cpic> cpiclist;
//	Msg_Citem citem;
//	Msg_Cbusinessinfo cbusinessinfo;
//	Msg_Cstars cstars;
//	List<Msg_Ccomment> ccommentlist;
//	List<Msg_Sstandard> sstandardlist;
//	RelativeLayout lay_comment, lay_shopinfo;
//	View view;
//	StringBuffer standardval = new StringBuffer();
//	// int[] img = { R.drawable.life_dragview, R.drawable.life_dragview1,
//	// R.drawable.life_dragview2 };
//	TextView t_intr, t_price, t_oldprice, t_peisongprice, t_qisong, t_overtime,
//			t_comment, t_comment_name, t_commment_time, t_standard,
//			t_productinfo, t_commtent, t_commtentcontent, t_commentname,
//			t_commtenttime, t_youhui, t_shopname, t_matchstar, t_service,
//			t_logistics;
//
//	@Override
//	protected void create(Bundle arg0) {
//		setContentView(R.layout.takeout_content);
//		t_intr = (TextView) this.findViewById(R.takeout.introduction);
//		t_price = (TextView) this.findViewById(R.takeout.newprice);
//		t_oldprice = (TextView) this.findViewById(R.takeout.oldprice);
//		t_peisongprice = (TextView) this.findViewById(R.takeout.peisongprice);
//		t_qisong = (TextView) this.findViewById(R.takeout.qisong);
//		t_commtentcontent = (TextView) this
//				.findViewById(R.takeout.commtent_content);
//		t_comment_name = (TextView) this.findViewById(R.takeout.commentname);
//		t_commment_time = (TextView) this.findViewById(R.takeout.commtenttime);
//
//		t_standard = (TextView) this.findViewById(R.takeout.standard);
//		t_youhui = (TextView) this.findViewById(R.takeout.youhui);
//		t_shopname = (TextView) this.findViewById(R.takeout.shopname);
//		t_matchstar = (TextView) this.findViewById(R.takeout.miaoshu_text);
//		t_service = (TextView) this.findViewById(R.takeout.fuwu_text);
//		t_logistics = (TextView) this.findViewById(R.takeout.fahuo_text);
//		itemid = getIntent().getStringExtra("itemid");
//		lay_shopinfo = (RelativeLayout) this
//				.findViewById(R.takeout.clic_layout5);
//		lay_comment = (RelativeLayout) this
//				.findViewById(R.takeout.clic_layout3);
//
//		mDragChangeView = (DragChangeView) findViewById(R.takeout.DragChangeView);
//		mDragChangeView.setAutoMove(true);
//		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
//		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
//		mDragChangeView.setHideRadio(false);
//		mDragChangeView.setRadius(7);
//
//		dataLoad(null);
//	}
//
//	@Override
//	public void disposeMessage(Son son) throws Exception {
//		if (son != null && son.mgetmethod.equals("WCONTENT")) {
//			Msg_Scontent.Builder builder = (Msg_Scontent.Builder) son.build;
//			cpiclist = builder.getCpiclist().getCpicList();
//			ccommentlist = builder.getCommentlist().getCommentList();
//			citem = builder.getCitem();
//			cbusinessinfo = builder.getCbusinessinfo();
//			cstars = builder.getCstars();
//			sstandardlist = builder.getSstandardlist().getSstandardList();
//
//			t_intr.setText(citem.getIteminfo());
//			t_price.setText(Arith.to2zero(citem.getItemdiscount()));
//			t_oldprice.setText(Arith.to2zero(citem.getItemprice()));
//			t_qisong.setText(citem.getItemfreight());
//			//t_peisongprice.setText(citem.getItemlimited());// 配送费
//			t_commtentcontent.setText(ccommentlist.get(0).getCommentcontent());
//			t_comment_name.setText(ccommentlist.get(0).getCommentpeople());
//			t_commment_time.setText(ccommentlist.get(0).getCommenttime());
//
//			// t_productinfo.setText(citem.getItemdetails());
//			t_youhui.setText(cbusinessinfo.getRemark());
//			businessname = cbusinessinfo.getBusinessname();
//			t_shopname.setText(businessname);
//			businessid=cbusinessinfo.getBusinessid();
//			t_service.setText(cstars.getServicestar());
//			t_matchstar.setText(cstars.getMatchstar());
//			t_logistics.setText(cstars.getLogisticsstar());
//
//			for (int i = 0; i < sstandardlist.size(); i++) {
//				standardval.append(sstandardlist.get(i).getFirstname() + "、"
//						+ sstandardlist.get(i).getSecondname() + "；");
//			}
//			t_standard.setText(standardval.toString());
//			lay_comment.setOnClickListener(new layoutListener());
//			lay_shopinfo.setOnClickListener(new layoutListener());
//			t_standard.setOnClickListener(new layoutListener());
//			ContentImgListAdapter iaad = new ContentImgListAdapter(this,
//					cpiclist);
//			mDragChangeView.setAdapter(iaad);
//		}
//	}
//
//	public class layoutListener implements OnClickListener {
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.takeout.clic_layout3:
//				Intent i = new Intent(TakeOutContentAct.this,
//						CommentListAct.class);
//				i.putExtra("citemid", itemid);
//				startActivity(i);
//				break;
//			case R.takeout.clic_layout5:
//				Intent i1 = new Intent();
//				i1.putExtra("businessname", businessname);
//				i1.putExtra("businessid", businessid);
//				i1.setClass(TakeOutContentAct.this, BusinessTakeoutAct.class);
//				startActivity(i1);
//				break;
//			case R.takeout.standard:
//				Intent intent = new Intent(TakeOutContentAct.this,AttributeAct.class);
//				startActivity(intent);
//				break;
//			}
//		}
//	}
//
//	@Override
//	public void dataLoad(int[] types) {
//		this.loadData(new Updateone[] { new Updateone("WCONTENT",
//				new String[][] { { "itemid", itemid == null ? "1" : itemid } }), });
//	}
//
//}
