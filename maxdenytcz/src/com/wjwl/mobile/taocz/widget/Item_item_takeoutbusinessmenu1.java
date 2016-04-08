//package com.wjwl.mobile.taocz.widget;
//
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.mdx.mobile.widget.MImageView;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.act.TakeOutBusinessMenuAct1;
//
//public class Item_item_takeoutbusinessmenu1 extends LinearLayout {
//	TextView productname, productprice, renqi;
//	Button bt_plus, bt_reduce;
//	EditText ed_num;
//	Context context;
//	public String price;
//	int id;
//	public String imgurl, itemname, iteminfo;
//	public String productid;
//	public String categoryid;
//	public String categoryName;
//	public WmDB wmdb;
//	MImageView productimg;
//
//	public Item_item_takeoutbusinessmenu1(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//
//	}
//
//	public Item_item_takeoutbusinessmenu1(Context context) {
//		super(context);
//		this.context = context;
//	}
//
//	public void initView() {
//		productname = (TextView) findViewById(R.item_item_takeoutbusinessmenu1.title);
//		renqi = (TextView) findViewById(R.item_item_takeoutbusinessmenu1.renqi);
//		productprice = (TextView) findViewById(R.item_item_takeoutbusinessmenu1.price);
//		bt_reduce = (Button) findViewById(R.item_item_takeoutbusinessmenu1.btn_reduce);
//		bt_plus = (Button) findViewById(R.item_item_takeoutbusinessmenu1.btn_plus);
//		productimg = (MImageView) findViewById(R.item_item_takeoutbusinessmenu1.businessimg);
//		ed_num = (EditText) findViewById(R.item_item_takeoutbusinessmenu1.edit_num);
//		ed_num.setText("0");
//		bt_plus.setOnClickListener(new onClic());
//		bt_reduce.setOnClickListener(new onClic());
//	}
//
//	public void setProductCount(int type) {// 0初始化 1点击修改
//		for (int i = 0; i < TakeOutBusinessMenuAct1.productNumTemp.size(); i++)
//			if (this.categoryid.equals(TakeOutBusinessMenuAct1.productNumTemp
//					.get(i).get("categoryId"))
//					&& this.productid
//							.equals(TakeOutBusinessMenuAct1.productNumTemp.get(
//									i).get("productId"))) {
//				this.id = i;
//				String numtemp = TakeOutBusinessMenuAct1.productNumTemp.get(id)
//						.get("productNum").toString();
//
//				String productid = TakeOutBusinessMenuAct1.productNumTemp
//						.get(id).get("productId").toString();
//				String businessid = TakeOutBusinessMenuAct1.businessid;
//
//				if (type == 1) {
//					wmdb = new WmDB(getContext());
//					List<Map<String, String>> maplist = wmdb.find("ITEMID = '"
//							+ productid + "'");
//					if (maplist.size() != 0) {
//						wmdb.Update(productid, numtemp);
//					} else {
//						wmdb.Insert(businessid, productid, numtemp);
//					}
//				}
//				this.ed_num.setText(numtemp);
//
//				return;
//			}
//	}
//
//	public void setProductName(CharSequence text) {
//		this.productname.setText(text);
//	}
//
//	public void setProductId(String text) {
//		this.productid = text;
//	}
//
//	public void setCategoryId(String text) {
//		this.categoryid = text;
//	}
//
//	public void setProductPrice(String text) {
//		for (int i = 0; i < TakeOutBusinessMenuAct1.productNumTemp.size(); i++)
//			if (this.categoryid.equals(TakeOutBusinessMenuAct1.productNumTemp
//					.get(i).get("categoryId"))
//					&& this.productid
//							.equals(TakeOutBusinessMenuAct1.productNumTemp.get(
//									i).get("productId"))) {
//				this.id = i;
//				float temp = Float.valueOf(text.equals("")?"0":text);
//				int tempcount = Integer
//						.valueOf(TakeOutBusinessMenuAct1.productNumTemp.get(id)
//								.get("productNum").toString());
//				if (tempcount <= 0)
//					this.productprice.setText(temp + "元");
//				else
//					this.productprice.setText(temp * tempcount + "元");
//				return;
//			}
//	}
//
//	public void setProductimg(CharSequence text) {
//		this.productimg.setObj(text);
//	}
//
//	public void setitemname(String itemname) {
//		this.itemname = itemname;
//	}
//
//	public void setiteminfo(String iteminfo) {
//		this.iteminfo = iteminfo;
//	}
//
//	public class onClic implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			switch (v.getId()) {
//			case R.item_item_takeoutbusinessmenu1.btn_plus:
//				int count = Integer
//						.parseInt(TakeOutBusinessMenuAct1.productNumTemp
//								.get(id).get("productNum").toString());
//				TakeOutBusinessMenuAct1.allCount++;
//				setAllCount("" + TakeOutBusinessMenuAct1.allCount);
//				count = count + 1;
//				TakeOutBusinessMenuAct1.productNumTemp.get(id).put(
//						"productNum", count);
//				setProductCount(1);
//				break;
//			case R.item_item_takeoutbusinessmenu1.btn_reduce:
//				int count1 = Integer
//						.parseInt(TakeOutBusinessMenuAct1.productNumTemp
//								.get(id).get("productNum").toString());
//				if (count1 > 0) {
//					TakeOutBusinessMenuAct1.allCount--;
//					setAllCount("" + TakeOutBusinessMenuAct1.allCount);
//					count1 = count1 - 1;
//				} else
//					count1 = 0;
//				TakeOutBusinessMenuAct1.productNumTemp.get(id).put(
//						"productNum", count1);
//				setProductCount(1);
//				break;
//			}
//		}
//
//	}
//
//	public void setAllCount(CharSequence text) {
//		if (TakeOutBusinessMenuAct1.allCount > 0) {
//			TakeOutBusinessMenuAct1.t_allCount.setVisibility(View.VISIBLE);
//			TakeOutBusinessMenuAct1.t_allCount.setText(text);
//		} else {
//			TakeOutBusinessMenuAct1.t_allCount.setVisibility(View.GONE);
//		}
//	}
//}