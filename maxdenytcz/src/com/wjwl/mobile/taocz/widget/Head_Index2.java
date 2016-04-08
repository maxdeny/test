package com.wjwl.mobile.taocz.widget;

import java.util.List;
import java.util.Map;
import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Citem.Msg_Citem;
import com.tcz.apkfactory.data.Cpic.Msg_Cpic;
import com.tcz.apkfactory.data.IsubjectList.Msg_IsubjectList;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.V3_NewsInfoAct;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Head_Index2 extends LinearLayout {
	HorizontalScrollView horizontalScrollView;
	LinearLayout addlayout,qianggoubar,qianggoutitle;
	MImageView qg_img1, qg_img2;
	TextView qg_price1, qg_price2, qg_shi1, qg_feng1, qg_miao1, qg_shi2,
			qg_feng2, qg_miao2;
	private int recLen1, recLen2;
	String qg_itemid1, qg_itemid2, gg_id;
	private Button delete;
	private RelativeLayout guanggao;
	LinearLayout dongtai_linear;
	DragChangeView mDragChangeView;

	public Head_Index2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	public Head_Index2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.head_index, this);
		addlayout = (LinearLayout) findViewById(R.v3_index_head.addlayout);
		dongtai_linear = (LinearLayout) findViewById(R.v3_index_head.dongtai_linear);
		horizontalScrollView = (HorizontalScrollView) findViewById(R.v3_index_head.hscrollview);
		delete = (Button) findViewById(R.v3_index_head.guanggao_btn);
		guanggao = (RelativeLayout) findViewById(R.v3_index_head.bt_guanggao);
		mDragChangeView = (DragChangeView) findViewById(R.v3_index_head.DragChangeView);
		mDragChangeView.setAutoMove(true);
		mDragChangeView.setNoCurrIcon(R.drawable.index_cur_nor);
		mDragChangeView.setCurrIcon(R.drawable.index_cur_ped);
		mDragChangeView.setMoveIcon(R.drawable.index_cur_ped);
		mDragChangeView.setRadius(7);
		mDragChangeView.setMoveType(1);
//		delete.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				guanggao.setVisibility(View.GONE);
//			}
//		});
//		guanggao.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(getContext(), V3_NewsInfoAct.class);
//				i.putExtra("gg_id", gg_id);
//				getContext().startActivity(i);
//			}
//		});
	}
	
	public void setHorizontalScrollViewsShow(boolean val){
		if(val){
			horizontalScrollView.setVisibility(View.VISIBLE);
		}else{
			horizontalScrollView.setVisibility(View.GONE);
		}
	
	}
	
	public void setDongtai_linearShow(boolean val){
		if(val){
			dongtai_linear.setVisibility(View.VISIBLE);
		}else{
			dongtai_linear.setVisibility(View.GONE);
		}
	
	}
	
	public void setQGShow(boolean val){
		if(val){
			qianggoubar.setVisibility(View.VISIBLE);
			qianggoutitle.setVisibility(View.VISIBLE);
		}else{
			qianggoubar.setVisibility(View.GONE);
			qianggoutitle.setVisibility(View.GONE);
			
		}
	
	}
	
	
	public void setXianShiQiangGou(List<Msg_Cpic>  datas) {
		if (datas.size() > 0) {
			qg_img1.setObj(datas.get(0).getImageurl());
			qg_price1.setText("￥" + datas.get(0).getV3Itemdiscount());// 新价
			qg_itemid1 = datas.get(0).getProid();
			if (!datas.get(0).getV3Itemdiscount().equals("")) {
				recLen1 = Integer.parseInt(datas.get(0).getV3Itemdiscount());
				Frame.HANDLES.get("Index2Act").get(0).sent(1, recLen1);
			}
		}
		if (datas.size() > 1) {
			qg_img2.setObj(datas.get(1).getImageurl());
			qg_price2.setText("￥" + datas.get(1).getV3Itemdiscount());// 新价
			qg_itemid2 = datas.get(1).getProid();
			if (!datas.get(1).getV3Itemdiscount().equals("")) {
				recLen2 = Integer.parseInt(datas.get(1).getV3Itemdiscount());
				Frame.HANDLES.get("V3_IndexAct").get(0).sent(2, recLen2);
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

	public void setAddLayout(List<Map<String, Object>> list) {
		for (int i = 0; i < list.size(); i++) {
			LayoutInflater flater = LayoutInflater.from(getContext());
			Item_Index_More item1 = (Item_Index_More) flater.inflate(
					R.layout.item_index_more, null);
			item1.initview();
			item1.setItemId((String) list.get(i).get("itemid"));
			item1.setImg((String)list.get(i).get("img"));
			item1.setPrice((String) list.get(i).get("price"));
			addlayout.addView(item1);
		}
	}

	@SuppressWarnings("deprecation")
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
