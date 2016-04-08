package com.wjwl.mobile.taocz.widget;

import java.util.List;
import java.util.Timer;
import com.wjwl.mobile.taocz.act.V3_NewsInfoAct;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.IsubjectList.Msg_IsubjectList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.V3_ShoppingDetailsAg;

public class head_index extends LinearLayout {

	Button bt_1, bt_2, bt_3, bt_4, bt_5, bt_6, bt_7, bt_8, delete;
	TextView text_gg, text1, text2, text3, textj;
	LinearLayout freelayout;
	RelativeLayout guanggao;
	LinearLayout dongtai_linear;
	ImageView icon1;
	public TextView tv11, tv12, tv13, tv21, tv22, tv23, tv31, tv32, tv33, tv34,
			tv41, tv42, tv43, tv44, zhekou1, zhekou2, zhekou3, zhekou4,
			qg_text11, qg_text12, qg_text13, qg_text21, qg_text22, qg_text23,qg_text11_2, qg_text12_2, qg_text13_2, qg_text21_2, qg_text22_2, qg_text23_2,
			qg_text11_3, qg_text12_3, qg_text13_3, qg_text21_3, qg_text22_3, qg_text23_3,
			qg_zhekou1, qg_zhekou2, qg_shi1, qg_feng1, qg_miao1, qg_shi2,	qg_zhekou1_2, qg_zhekou2_2, qg_shi1_2, qg_feng1_2, qg_miao1_2, qg_shi2_2,
			qg_zhekou1_3, qg_zhekou2_3, qg_shi1_3, qg_feng1_3, qg_miao1_3, qg_shi2_3,
			qg_feng2, qg_miao2,qg_feng2_2, qg_miao2_2,qg_feng2_3, qg_miao2_3;
	MImageView img1, img2, img3, img4, qg_img1, qg_img2, qg_img1_2, qg_img2_2, qg_img1_3, qg_img2_3;
	LinearLayout clicLayout1, clicLayout2, clicLayout3, clicLayout4,
			qg_clicLayout1, qg_clicLayout2,qg_clicLayout1_2, qg_clicLayout2_2,qg_clicLayout1_3, qg_clicLayout2_3;
	float ondownx = 0, onupx = 0;
	int touchpostion = 0;
	int width;
	int i = 0;
	ScollerItem item1, item2;
	List<Msg_Cpic> data;
	private String gg_id;
	String itemid1 = "", itemid2 = "", itemid3 = "", itemid4 = "",
			qg_itemid1 = "", qg_itemid2 = "",qg_itemid1_2 = "", qg_itemid2_2 = "",qg_itemid1_3 = "", qg_itemid2_3 = "", qg_time1, qg_time2
			,qg_time1_2, qg_time2_2,qg_time1_3, qg_time2_3;
	ScollerItem2 aItem2;
	Timer timer = new Timer();
	private int recLen1, recLen2,recLen3,recLen4,recLen5,recLen6;

	public head_index(Context context) {
		super(context);
		initview();
	}

	public head_index(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.v3_index_head, this);

		guanggao = (RelativeLayout) findViewById(R.v3_index_head.bt_guanggao);
		text_gg = (TextView) findViewById(R.v3_index_head.guanggao_text);
		text1 = (TextView) findViewById(R.v3_index_head.text1);
		textj = (TextView) findViewById(R.v3_index_head.textj);
		// textj.getPaint().setFakeBoldText(true);
		text2 = (TextView) findViewById(R.v3_index_head.text2);
		text3 = (TextView) findViewById(R.v3_index_head.text3);
		dongtai_linear = (LinearLayout) findViewById(R.v3_index_head.dongtai_linear);
		icon1 = (ImageView) findViewById(R.v3_index_head.icon1);
		tv11 = (TextView) findViewById(R.v3_index_head.text11);
		tv12 = (TextView) findViewById(R.v3_index_head.text12);
		tv13 = (TextView) findViewById(R.v3_index_head.text13);
		tv21 = (TextView) findViewById(R.v3_index_head.text21);
		tv22 = (TextView) findViewById(R.v3_index_head.text22);
		tv23 = (TextView) findViewById(R.v3_index_head.text23);
		tv31 = (TextView) findViewById(R.v3_index_head.text31);
		tv32 = (TextView) findViewById(R.v3_index_head.text32);
		tv33 = (TextView) findViewById(R.v3_index_head.text33);
		tv41 = (TextView) findViewById(R.v3_index_head.text41);
		tv42 = (TextView) findViewById(R.v3_index_head.text42);
		tv43 = (TextView) findViewById(R.v3_index_head.text43);
		// tv12.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
		tv13.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tv23.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tv33.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tv43.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		img1 = (MImageView) findViewById(R.v3_index_head.Photo1);
		img2 = (MImageView) findViewById(R.v3_index_head.Photo2);
		img3 = (MImageView) findViewById(R.v3_index_head.Photo3);
		img4 = (MImageView) findViewById(R.v3_index_head.Photo4);
		zhekou1 = (TextView) findViewById(R.v3_index_head.zhekou1);
		zhekou2 = (TextView) findViewById(R.v3_index_head.zhekou2);
		zhekou3 = (TextView) findViewById(R.v3_index_head.zhekou3);
		zhekou4 = (TextView) findViewById(R.v3_index_head.zhekou4);
		clicLayout1 = (LinearLayout) findViewById(R.v3_index_head.cliclayout1);
		clicLayout2 = (LinearLayout) findViewById(R.v3_index_head.cliclayout2);
		clicLayout3 = (LinearLayout) findViewById(R.v3_index_head.cliclayout3);
		clicLayout4 = (LinearLayout) findViewById(R.v3_index_head.cliclayout4);
		qg_text11 = (TextView) findViewById(R.v3_index_head.qg_text11);
		qg_text12 = (TextView) findViewById(R.v3_index_head.qg_text12);
		qg_text13 = (TextView) findViewById(R.v3_index_head.qg_text13);
		qg_text21 = (TextView) findViewById(R.v3_index_head.qg_text21);
		qg_text22 = (TextView) findViewById(R.v3_index_head.qg_text22);
		qg_text23 = (TextView) findViewById(R.v3_index_head.qg_text23);
		qg_zhekou1 = (TextView) findViewById(R.v3_index_head.qg_zhekou1);
		qg_zhekou2 = (TextView) findViewById(R.v3_index_head.qg_zhekou2);
		qg_text13.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_text23.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_shi1 = (TextView) findViewById(R.v3_index_head.qg_shi1);
		qg_feng1 = (TextView) findViewById(R.v3_index_head.qg_feng1);
		qg_miao1 = (TextView) findViewById(R.v3_index_head.qg_miao1);
		qg_shi2 = (TextView) findViewById(R.v3_index_head.qg_shi2);
		qg_feng2 = (TextView) findViewById(R.v3_index_head.qg_feng2);
		qg_miao2 = (TextView) findViewById(R.v3_index_head.qg_miao2);
		qg_img1 = (MImageView) findViewById(R.v3_index_head.qg_Photo1);
		qg_img2 = (MImageView) findViewById(R.v3_index_head.qg_Photo2);
		qg_clicLayout1 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout1);
		qg_clicLayout2 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout2);
		
		
		qg_text11_2 = (TextView) findViewById(R.v3_index_head.qg_text11_2);
		qg_text12_2 = (TextView) findViewById(R.v3_index_head.qg_text12_2);
		qg_text13_2 = (TextView) findViewById(R.v3_index_head.qg_text13_2);
		qg_text21_2 = (TextView) findViewById(R.v3_index_head.qg_text21_2);
		qg_text22_2 = (TextView) findViewById(R.v3_index_head.qg_text22_2);
		qg_text23_2 = (TextView) findViewById(R.v3_index_head.qg_text23_2);
		qg_zhekou1_2 = (TextView) findViewById(R.v3_index_head.qg_zhekou1_2);
		qg_zhekou2_2 = (TextView) findViewById(R.v3_index_head.qg_zhekou2_2);
		qg_text13_2.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_text23_2.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_shi1_2 = (TextView) findViewById(R.v3_index_head.qg_shi1_2);
		qg_feng1_2 = (TextView) findViewById(R.v3_index_head.qg_feng1_2);
		qg_miao1_2 = (TextView) findViewById(R.v3_index_head.qg_miao1_2);
		qg_shi2_2 = (TextView) findViewById(R.v3_index_head.qg_shi2_2);
		qg_feng2_2 = (TextView) findViewById(R.v3_index_head.qg_feng2_2);
		qg_miao2_2 = (TextView) findViewById(R.v3_index_head.qg_miao2_2);
		qg_img1_2 = (MImageView) findViewById(R.v3_index_head.qg_Photo1_2);
		qg_img2_2 = (MImageView) findViewById(R.v3_index_head.qg_Photo2_2);
		qg_clicLayout1_2 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout1_2);
		qg_clicLayout2_2 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout2_2);
		
		
		qg_text11_3 = (TextView) findViewById(R.v3_index_head.qg_text11_3);
		qg_text12_3 = (TextView) findViewById(R.v3_index_head.qg_text12_3);
		qg_text13_3 = (TextView) findViewById(R.v3_index_head.qg_text13_3);
		qg_text21_3 = (TextView) findViewById(R.v3_index_head.qg_text21_3);
		qg_text22_3 = (TextView) findViewById(R.v3_index_head.qg_text22_3);
		qg_text23_3 = (TextView) findViewById(R.v3_index_head.qg_text23_3);
		qg_zhekou1_3 = (TextView) findViewById(R.v3_index_head.qg_zhekou1_3);
		qg_zhekou2_3 = (TextView) findViewById(R.v3_index_head.qg_zhekou2_3);
		qg_text13_3.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_text23_3.getPaint().setFlags(
				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		qg_shi1_3 = (TextView) findViewById(R.v3_index_head.qg_shi1_3);
		qg_feng1_3 = (TextView) findViewById(R.v3_index_head.qg_feng1_3);
		qg_miao1_3 = (TextView) findViewById(R.v3_index_head.qg_miao1_3);
		qg_shi2_3 = (TextView) findViewById(R.v3_index_head.qg_shi2_3);
		qg_feng2_3 = (TextView) findViewById(R.v3_index_head.qg_feng2_3);
		qg_miao2_3 = (TextView) findViewById(R.v3_index_head.qg_miao2_3);
		qg_img1_3 = (MImageView) findViewById(R.v3_index_head.qg_Photo1_3);
		qg_img2_3 = (MImageView) findViewById(R.v3_index_head.qg_Photo2_3);
		qg_clicLayout1_3 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout1_3);
		qg_clicLayout2_3 = (LinearLayout) findViewById(R.v3_index_head.qg_cliclayout2_3);
		
		
		delete = (Button) findViewById(R.v3_index_head.guanggao_btn);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				guanggao.setVisibility(View.GONE);
			}
		});
		guanggao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getContext(), V3_NewsInfoAct.class);
				i.putExtra("gg_id", gg_id);
				getContext().startActivity(i);
			}
		});
		clicLayout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid1.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", itemid1);
					i.putExtra("from", "index");
					getContext().startActivity(i);
				}
			}
		});
		clicLayout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid2.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", itemid2);
					i.putExtra("from", "index");
					getContext().startActivity(i);
				}
			}
		});
		clicLayout3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid3.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", itemid3);
					i.putExtra("from", "index");
					getContext().startActivity(i);
				}
			}
		});
		clicLayout4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", itemid4);
					i.putExtra("from", "index");
					getContext().startActivity(i);
				}
			}
		});
		qg_clicLayout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid1);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
		qg_clicLayout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid2);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
		
		
		qg_clicLayout1_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid1_2);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
		qg_clicLayout2_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid2_2);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
		
		qg_clicLayout1_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid1_3);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
		qg_clicLayout2_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!itemid4.equals("")) {
					Intent i = new Intent(getContext(),
							V3_ShoppingDetailsAg.class);
					i.putExtra("itemid", qg_itemid2_3);
					i.putExtra("from", "index");
					i.putExtra("tejia", "1");
					getContext().startActivity(i);
				}
			}
		});
	}
	
	

	
	public void setGuangGao(String str) {
		text_gg.setText(str);
	}
	
	
	
	public void setXianShiQiangGou(List<Msg_Citem> datas) {
		if (datas.size() > 0) {
			qg_img1.setObj(datas.get(0).getItemimageurl());
			qg_text11.setText(datas.get(0).getItembusinessname());// 名字
			qg_text12.setText("￥" + datas.get(0).getItemprice());// 新价
			qg_text13.setText("￥" + datas.get(0).getItemdiscount());// 原价
			qg_zhekou1.setText(datas.get(0).getOther1() + "折");
			qg_itemid1 = datas.get(0).getItemid();
			if (!datas.get(0).getItemremtime().equals("")) {
				recLen1 = Integer.parseInt(datas.get(0).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(1, recLen1);
			}
		}
		if (datas.size() > 1) {
			qg_img2.setObj(datas.get(1).getItemimageurl());
			qg_text21.setText(datas.get(1).getItembusinessname());// 名字
			qg_text22.setText("￥" + datas.get(1).getItemprice());// 新价
			qg_text23.setText("￥" + datas.get(1).getItemdiscount());// 原价
			qg_zhekou2.setText(datas.get(1).getOther1() + "折");
			qg_itemid2 = datas.get(1).getItemid();
			if (!datas.get(1).getItemremtime().equals("")) {
				recLen2 = Integer.parseInt(datas.get(1).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(2, recLen2);
			}
		}
		if (datas.size() > 2) {
			qg_img1_2.setObj(datas.get(2).getItemimageurl());
			qg_text11_2.setText(datas.get(2).getItembusinessname());// 名字
			qg_text12_2.setText("￥" + datas.get(2).getItemprice());// 新价
			qg_text13_2.setText("￥" + datas.get(2).getItemdiscount());// 原价
			qg_zhekou1_2.setText(datas.get(2).getOther1() + "折");
			qg_itemid1_2 = datas.get(2).getItemid();
			if (!datas.get(2).getItemremtime().equals("")) {
				recLen3 = Integer.parseInt(datas.get(2).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(3, recLen3);
			}
		}
		if (datas.size() > 3) {
			qg_img2_2.setObj(datas.get(3).getItemimageurl());
			qg_text21_2.setText(datas.get(3).getItembusinessname());// 名字
			qg_text22_2.setText("￥" + datas.get(3).getItemprice());// 新价
			qg_text23_2.setText("￥" + datas.get(3).getItemdiscount());// 原价
			qg_zhekou2_2.setText(datas.get(3).getOther1() + "折");
			qg_itemid2_2 = datas.get(3).getItemid();
			if (!datas.get(3).getItemremtime().equals("")) {
				recLen4 = Integer.parseInt(datas.get(3).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(4, recLen4);
			}
		}
		if (datas.size() > 4) {
			qg_img1_3.setObj(datas.get(4).getItemimageurl());
			qg_text11_3.setText(datas.get(4).getItembusinessname());// 名字
			qg_text12_3.setText("￥" + datas.get(4).getItemprice());// 新价
			qg_text13_3.setText("￥" + datas.get(4).getItemdiscount());// 原价
			qg_zhekou1_3.setText(datas.get(4).getOther1() + "折");
			qg_itemid1_3 = datas.get(4).getItemid();
			if (!datas.get(4).getItemremtime().equals("")) {
				recLen5 = Integer.parseInt(datas.get(4).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(5, recLen5);
			}
		}
		if (datas.size() > 5) {
			qg_img2_3.setObj(datas.get(5).getItemimageurl());
			qg_text21_3.setText(datas.get(5).getItembusinessname());// 名字
			qg_text22_3.setText("￥" + datas.get(5).getItemprice());// 新价
			qg_text23_3.setText("￥" + datas.get(5).getItemdiscount());// 原价
			qg_zhekou2_3.setText(datas.get(5).getOther1() + "折");
			qg_itemid2_3 = datas.get(5).getItemid();
			if (!datas.get(5).getItemremtime().equals("")) {
				recLen6 = Integer.parseInt(datas.get(5).getItemremtime());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(6, recLen6);
			}
		}
	}

	public void setQiangGouTime1(String[] str) {
		qg_shi1.setText(str[0]);
		qg_feng1.setText(str[1]);
		qg_miao1.setText(str[2]);
	}

	public void setQiangGouTime2(String[] str) {
		qg_shi2.setText(str[0]);
		qg_feng2.setText(str[1]);
		qg_miao2.setText(str[2]);
	}
	
	public void setQiangGouTime3(String[] str) {
		qg_shi1_2.setText(str[0]);
		qg_feng1_2.setText(str[1]);
		qg_miao1_2.setText(str[2]);
	}

	public void setQiangGouTime4(String[] str) {
		qg_shi2_2.setText(str[0]);
		qg_feng2_2.setText(str[1]);
		qg_miao2_2.setText(str[2]);
	}
	
	public void setQiangGouTime5(String[] str) {
		qg_shi1_3.setText(str[0]);
		qg_feng1_3.setText(str[1]);
		qg_miao1_3.setText(str[2]);
	}

	public void setQiangGouTime6(String[] str) {
		qg_shi2_3.setText(str[0]);
		qg_feng2_3.setText(str[1]);
		qg_miao2_3.setText(str[2]);
	}
	

	public void setQiangGou(List<Msg_Cpic> datas) {
		if (datas.size() > 0) {
			img1.setObj(datas.get(0).getImageurl());
			tv11.setText(datas.get(0).getV3Iteminfo());// 名字
			tv12.setText("￥" + datas.get(0).getV3Itemdiscount());// 原价
			tv13.setText("￥" + datas.get(0).getV3Itemprice());// 新价
			zhekou1.setText(datas.get(0).getV3Discount() + "折");
			itemid1 = datas.get(0).getProid();
		}
		if (datas.size() > 1) {
			img2.setObj(datas.get(1).getImageurl());
			tv21.setText(datas.get(1).getV3Iteminfo());// 名字
			tv22.setText("￥" + datas.get(1).getV3Itemdiscount());// 原价
			tv23.setText("￥" + datas.get(1).getV3Itemprice());// 新价
			zhekou2.setText(datas.get(1).getV3Discount() + "折");
			itemid2 = datas.get(1).getProid();
		}
		if (datas.size() > 2) {
			img3.setObj(datas.get(2).getImageurl());
			tv31.setText(datas.get(2).getV3Iteminfo());// 名字
			tv32.setText("￥" + datas.get(2).getV3Itemdiscount());// 原价
			tv33.setText("￥" + datas.get(2).getV3Itemprice());// 新价
			zhekou3.setText(datas.get(2).getV3Discount() + "折");
			itemid3 = datas.get(2).getProid();
		}
		if (datas.size() > 3) {
			img4.setObj(datas.get(3).getImageurl());
			tv41.setText(datas.get(3).getV3Iteminfo());// 名字
			tv42.setText("￥" + datas.get(3).getV3Itemdiscount());// 原价
			tv43.setText("￥" + datas.get(3).getV3Itemprice());// 新价
			zhekou4.setText(datas.get(3).getV3Discount() + "折");
			itemid4 = datas.get(3).getProid();
		}

	}

	public void setText1(CharSequence text) {
		this.text1.setText(text);
	}

	public void setText2(CharSequence text) {
		this.text2.setText(text);
	}

	public void setText2Visable(boolean value) {
		if (value) {
			this.text2.setVisibility(View.VISIBLE);
		} else {
			this.text2.setVisibility(View.GONE);
		}
	}

	public void setIcon1(String text) {
		if (text.equals("早市")) {
			icon1.setBackgroundResource(R.drawable.v3_index_zaoshi);
		} else if (text.equals("夜市")) {
			icon1.setBackgroundResource(R.drawable.v3_index_yeshi);
		} else if (text.equals("早市未开始")) {
			icon1.setBackgroundResource(R.drawable.zhongri);
		} else if (text.equals("夜市未开始")) {
			icon1.setBackgroundResource(R.drawable.zhongyue);
		}
	}

	public void sethead(Msg_IsubjectList data0) {
		int hang = 0;
		if (data0.getIsubjectList().size() % 4 != 0) {
			hang = data0.getIsubjectList().size() / 4 + 1;
		} else {
			hang = data0.getIsubjectList().size() / 4;
		}
		for (int i = 0; i < hang; i++) {
			LinearLayout linear = new LinearLayout(getContext());
			linear.setGravity(Gravity.CENTER_HORIZONTAL);
			LinearLayout.LayoutParams ps1 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
			if (i != 0) {
				ps1.setMargins(0, 15, 0, 0);// left,top,right,bottom
			}
			linear.setOrientation(LinearLayout.HORIZONTAL);// LinearLayout默认的就是水平方向，本不用设置的
			linear.setLayoutParams(ps1);
			for (int k = 0; k < 4; k++) {
				FenLeiButton btn = new FenLeiButton(getContext());
				LinearLayout.LayoutParams ps = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);

				if (k > 0)
					if (F.Fwidth > 480) {
						ps.setMargins(30, 0, 0, 0);
					} else {
						ps.setMargins(15, 0, 0, 0);
					}

				btn.setLayoutParams(ps);
				if ((4 * i + k) < data0.getIsubjectList().size()) {// 防止边界溢出，因为这样出现的是3的整数个
					btn.setData(data0.getIsubjectList().get(4 * i + k)
							.getSubjectimgurl(),
							data0.getIsubjectList().get(4 * i + k)
									.getV3Categoryname(), data0
									.getIsubjectList().get(4 * i + k)
									.getV3Categoryid(), data0.getIsubjectList()
									.get(4 * i + k).getV3Categoryjumptyep());
				} else {
					btn.setVisibility(View.INVISIBLE);// 将 溢出的，隐藏掉
				}
				linear.addView(btn);

			}
			dongtai_linear.addView(linear);
		}
	}

}
