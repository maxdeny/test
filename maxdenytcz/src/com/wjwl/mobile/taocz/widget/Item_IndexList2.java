//package com.wjwl.mobile.taocz.widget;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.F;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.act.HotRecommendAct;
//import com.wjwl.mobile.taocz.act.SystemSetupAct;
//
//public class Item_IndexList2 extends LinearLayout {
//
//	private MImageView icon;
//	String itemid = "", type = "";
//	TextView title;
//	LinearLayout addlayout;
//
//	public Item_IndexList2(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public Item_IndexList2(Context context) {
//		super(context);
//		initview();
//		// TODO Auto-generated constructor stub
//	}
//
//	public void initview() {
//		// TODO Auto-generated method stub
//		LayoutInflater flater = LayoutInflater.from(this.getContext());
//		flater.inflate(R.layout.item_indexlist2, this);
//		icon = (MImageView) findViewById(R.item_indexlist.icon);
//		title = (TextView) findViewById(R.item_indexlist.title);
//		addlayout = (LinearLayout) findViewById(R.item_indexlist.addlayout);
//		// layout.setOnClickListener(new OnClickListener() {
//		//
//		// @Override
//		// public void onClick(View v) {
//		// // TODO Auto-generated method stub
//		// Intent i = new Intent();
//		// i.putExtra("title", title);
//		// i.putExtra("id", itemid);
//		// i.putExtra("type", "");
//		// i.setClass(getContext(), HotRecommendAct.class);
//		// getContext().startActivity(i);
//		// }
//		// });
//
//	}
//
//	public void setIcon(Object obj) {
//		icon.setType(9);
//		icon.setObj(obj);
//	}
//
//	public void setItemid(String text) {
//		this.itemid = text;
//	}
//
//	public void setTitle(CharSequence text) {
//		this.title.setText(text);
//	}
//
//	public void setType(String text) {
//		this.type = text;
//	}
//
//	public void setStyle(Object obj) {
//		String style = (String) obj;
//		if (style.equals("zt_three_rightbig")) {
//			LayoutInflater flater = LayoutInflater.from(getContext());
//			Item_Index_Layout5 item1 = (Item_Index_Layout5) flater.inflate(
//					R.layout.item_index_layout5, null);
//			item1.initView();
//			// item1.setImg(obj);
//			// item1.setItemid(itemid);
//			addlayout.addView(item1);
//		} else if (style.equals("zt_three_leftbig")) {
//			LayoutInflater flater = LayoutInflater.from(getContext());
//			Item_Index_Layout4 item1 = (Item_Index_Layout4) flater.inflate(
//					R.layout.item_index_layout4, null);
//			item1.initView();
//			// item1.setImg(obj);
//			// item1.setItemid(itemid);
//			addlayout.addView(item1);
//		}else if (style.equals("zt_three_samebig")) {
//			LayoutInflater flater = LayoutInflater.from(getContext());
//			Item_Index_Layout3 item1 = (Item_Index_Layout3) flater.inflate(
//					R.layout.item_index_layout3, null);
//			item1.initView();
//			// item1.setImg(obj);
//			// item1.setItemid(itemid);
//			addlayout.addView(item1);
//		}
//		else if (style.equals("zt_two_samebig")) {
//			LayoutInflater flater = LayoutInflater.from(getContext());
//			Item_Index_Layout2 item1 = (Item_Index_Layout2) flater.inflate(
//					R.layout.item_index_layout2, null);
//			item1.initView();
//			// item1.setImg(obj);
//			// item1.setItemid(itemid);
//			addlayout.addView(item1);
//		}
//		else if (style.equals("zt_one_samebig")) {
//			LayoutInflater flater = LayoutInflater.from(getContext());
//			Item_Index_Layout1 item1 = (Item_Index_Layout1) flater.inflate(
//					R.layout.item_index_layout1, null);
//			item1.initView();
//			// item1.setImg(obj);
//			// item1.setItemid(itemid);
//			addlayout.addView(item1);
//		}
//	}
//}
