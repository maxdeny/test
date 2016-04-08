//package com.wjwl.mobile.taocz.widget;
//
//import java.util.List;
//import java.util.Map;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.act.TakeOutBussinessMenuAct;
//import com.wjwl.mobile.taocz.dialog.TakeOutShowProductDialog;
//
//public class Item_item_takeoutbusinessmenu extends LinearLayout {
//	TextView productcount, productname, productprice;
//	Button reduce;
//	RelativeLayout clic_layout1, clic_layout2, clic_layout3;
//	Context context;
//	ImageView line, img_count, arrow;
//	public String price;
//	int id;
//	public String imgurl, itemname, iteminfo;
//	public String productid;
//	public String categoryid;
//	public String categoryName;
//	public WmDB wmdb;
//
//	public Item_item_takeoutbusinessmenu(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//
//	}
//
//	public Item_item_takeoutbusinessmenu(Context context) {
//		super(context);
//		this.context = context;
//	}
//
//	public void initView() {
//		productcount = (TextView) findViewById(R.item_item_takeoutbusinessmenu.count);
//		productname = (TextView) findViewById(R.item_item_takeoutbusinessmenu.productname);
//		productprice = (TextView) findViewById(R.item_item_takeoutbusinessmenu.productprice);
//		img_count = (ImageView) findViewById(R.item_item_takeoutbusinessmenu.img_count);
//		reduce = (Button) findViewById(R.item_item_takeoutbusinessmenu.reduce);
//		clic_layout1 = (RelativeLayout) findViewById(R.item_item_takeoutbusinessmenu.clic_layout1);
//		clic_layout2 = (RelativeLayout) findViewById(R.item_item_takeoutbusinessmenu.clic_layout2);
//		clic_layout3 = (RelativeLayout) findViewById(R.item_item_takeoutbusinessmenu.reducelayout);
//		line = (ImageView) findViewById(R.item_item_takeoutbusinessmenu.line);
//		//arrow = (ImageView) findViewById(R.item_item_takeoutbusinessmenu.arrow);
//		reduce.setVisibility(View.INVISIBLE);
//		reduce.setOnClickListener(new onClic());
//		clic_layout1.setOnClickListener(new onClic());
//		clic_layout2.setOnClickListener(new onClic());
//		clic_layout3.setOnClickListener(new onClic());
//		productprice.setOnClickListener(new onClic());
//	}
//
//	public void setProductCount(int type) {// 0初始化 1点击修改
//		for (int i = 0; i < TakeOutBussinessMenuAct.productNumTemp.size(); i++)
//			if (this.categoryid.equals(TakeOutBussinessMenuAct.productNumTemp
//					.get(i).get("categoryId"))
//					&& this.productid
//							.equals(TakeOutBussinessMenuAct.productNumTemp.get(
//									i).get("productId"))) {
//				this.id = i;
//				String numtemp = TakeOutBussinessMenuAct.productNumTemp.get(id)
//						.get("productNum").toString();
//
//				String productid = TakeOutBussinessMenuAct.productNumTemp
//						.get(id).get("productId").toString();
//				String businessid = TakeOutBussinessMenuAct.businessid;
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
//
//				if (Integer.valueOf(numtemp) > 0) {
//					this.productcount.setText(numtemp);
//					this.productcount.setVisibility(View.VISIBLE);
//					this.reduce.setVisibility(View.VISIBLE);
//					this.img_count.setVisibility(View.INVISIBLE);
//				} else {
//					this.reduce.setVisibility(View.INVISIBLE);
//					this.productcount.setVisibility(View.INVISIBLE);
//					img_count.setVisibility(View.VISIBLE);
//				}
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
//		for (int i = 0; i < TakeOutBussinessMenuAct.productNumTemp.size(); i++)
//			if (this.categoryid.equals(TakeOutBussinessMenuAct.productNumTemp
//					.get(i).get("categoryId"))
//					&& this.productid
//							.equals(TakeOutBussinessMenuAct.productNumTemp.get(
//									i).get("productId"))) {
//				this.id = i;
//				float temp = Float.valueOf(text);
//				int tempcount = Integer
//						.valueOf(TakeOutBussinessMenuAct.productNumTemp.get(id)
//								.get("productNum").toString());
//				if (tempcount <= 0)
//					this.productprice.setText(temp + "元");
//				else
//					this.productprice.setText(temp * tempcount + "元");
//				return;
//			}
//	}
//
//	public void setLineGone() {
//		this.line.setVisibility(View.GONE);
//	}
//
//	public void setid() {
//
//	}
//
//	public void setimgurl(String imgurl) {
//		if (imgurl.equals(""))
//			clic_layout2.setVisibility(View.GONE);
//		else
//			clic_layout2.setVisibility(View.VISIBLE);
//		this.imgurl = imgurl;
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
//			case R.item_item_takeoutbusinessmenu.clic_layout1:
//			case R.item_item_takeoutbusinessmenu.productprice:
//				int count = Integer
//						.parseInt(TakeOutBussinessMenuAct.productNumTemp
//								.get(id).get("productNum").toString());
//				TakeOutBussinessMenuAct.allCount++;
//				setAllCount("" + TakeOutBussinessMenuAct.allCount);
//				count = count + 1;
//
//				TakeOutBussinessMenuAct.productNumTemp.get(id).put(
//						"productNum", count);
//				setProductPrice(price);
//				// setProductPrice(Arith.to2zero(""+(count*f_price)));
//				setProductCount(1);
//				break;
//			case R.item_item_takeoutbusinessmenu.clic_layout2:
////				if(imgurl.equals(""))
////					return;
//				TakeOutShowProductDialog dia = new TakeOutShowProductDialog(
//						context, itemname, imgurl, iteminfo);
//				dia.show();
//				break;
//			// case R.item_item_takeoutbusinessmenu.reducelayout:
//			case R.item_item_takeoutbusinessmenu.reduce:
//				int count1 = Integer
//						.parseInt(TakeOutBussinessMenuAct.productNumTemp
//								.get(id).get("productNum").toString());
//				if (count1 > 0) {
//					TakeOutBussinessMenuAct.allCount--;
//					setAllCount("" + TakeOutBussinessMenuAct.allCount);
//					count1 = count1 - 1;
//				} else
//					count1 = 0;
//				TakeOutBussinessMenuAct.productNumTemp.get(id).put(
//						"productNum", count1);
//				setProductPrice(price);
//				setProductCount(1);
//				break;
//			}
//		}
//
//	}
//
//	public void setAllCount(CharSequence text) {
//		if (TakeOutBussinessMenuAct.allCount > 0) {
//			TakeOutBussinessMenuAct.t_allCount.setVisibility(View.VISIBLE);
//			TakeOutBussinessMenuAct.t_allCount.setText(text);
//		} else {
//			TakeOutBussinessMenuAct.t_allCount.setVisibility(View.GONE);
//		}
//	}
//}
