//package com.wjwl.mobile.taocz.widget;
//
//import java.util.List;
//
//import com.tcz.apkfactory.data.CBill.Msg_CBill.Msg_Billitem;
//import com.wjwl.mobile.taocz.R;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class Item_takeoutbusinessmenu1 extends LinearLayout {
//	public TextView title;
//	LinearLayout addlayout;
//	Context context;
//	Msg_Billitem item;
//	String categoryId;
//	public Item_takeoutbusinessmenu1(Context context) {
//		super(context);
//		this.context = context;
//		// TODO Auto-generated constructor stub
//	}
//
//	public Item_takeoutbusinessmenu1(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//
//	}
//
//	public void initView() {
//		title = (TextView) findViewById(R.item_takeoutbusinessmenu.menu);
//		addlayout = (LinearLayout) findViewById(R.item_takeoutbusinessmenu.addlayout);
//	}
//
//	public void setTile(CharSequence text) {
//		this.title.setText(text);
//	}
//
//	public void setcategoryId(String text) {
//		this.categoryId = text;
//	}
//
//	public void setTakeoutBusinessMenuLayout(List<Msg_Billitem> billitemlist) {
//		for (int j = 0; j < addlayout.getChildCount(); j++) {
//			addlayout.getChildAt(j).setVisibility(View.GONE);
//		}
//
//		for (int i = 0; i < billitemlist.size(); i++) {
//			item = billitemlist.get(i);
//			Item_item_takeoutbusinessmenu1 item_takeout;
//
//			if (i < addlayout.getChildCount()) {
//				item_takeout = (Item_item_takeoutbusinessmenu1) addlayout
//						.getChildAt(i);
//				item_takeout.setVisibility(View.VISIBLE);
//
//			} else {
//				LayoutInflater flater = LayoutInflater.from(this.getContext());
//				item_takeout = (Item_item_takeoutbusinessmenu1) flater.inflate(
//						R.layout.item_item_takeoutbusinessmenu1, null);
//				item_takeout.initView();
//
//				this.addlayout.addView(item_takeout);
//			}
//
//
//			item_takeout.price = item.getBillitemprice();
//			item_takeout.setProductName(item.getBillitemname());
//			item_takeout.setProductimg(item.getBillitemimage());
//			item_takeout.setitemname(item.getBillitemname());
//			item_takeout.setiteminfo(item.getBilliteminfo());
//			item_takeout.setProductId(item.getBillitemid());
//			item_takeout.setCategoryId(this.categoryId);
//			item_takeout.setProductCount(0);
//			item_takeout.setProductPrice(item.getBillitemprice());
//		}
//	}
//}
