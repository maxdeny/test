//package com.wjwl.mobile.taocz.widget;
//import com.mdx.mobile.Frame;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.DB.WmDB;
//import com.wjwl.mobile.taocz.act.TakeOutBoxAct;
//import com.wjwl.mobile.taocz.act.TakeOutBussinessMenuAct;
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class Item_item_takeoutbox extends LinearLayout {
//	TextView productname, price;
//	EditText ed_num;
//	Button bt_add, b_reduce;
//	String productId;
//	String businessId;
//	int id;
//    private WmDB wmdb;
//	public Item_item_takeoutbox(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
//
//	public Item_item_takeoutbox(Context context) {
//		super(context);
//		// TODO Auto-generated constructor stub
//	}
//
//	public void initView() {
//		productname = (TextView) findViewById(R.item_item_takeoutbox.productname);
//		price = (TextView) findViewById(R.item_item_takeoutbox.productprice);
//		ed_num = (EditText) findViewById(R.item_item_takeoutbox.edit_num);
//		bt_add = (Button) findViewById(R.item_item_takeoutbox.btn_plus);
//		b_reduce = (Button) findViewById(R.item_item_takeoutbox.btn_minus);
//		bt_add.setOnClickListener(new onClic());
//		b_reduce.setOnClickListener(new onClic());
//	}
//
//	public void setPorductId(String text) {
//		this.productId = text;
//	}
//
//	public void setBussinessId(String text) {
//		this.businessId = text;
//	}
//
//	public void setProductName(CharSequence text) {
//		this.productname.setText(text);
//	}
//
//	public void setNum() {
//		for (int i = 0; i < TakeOutBoxAct.productNumTemp.size(); i++)
//			if (this.businessId.equals(TakeOutBoxAct.productNumTemp
//					.get(i).get("businessId"))
//					&& this.productId
//							.equals(TakeOutBoxAct.productNumTemp.get(
//									i).get("productId"))) {
//				this.id = i;
//				String numtemp = TakeOutBoxAct.productNumTemp.get(id)
//						.get("productNum").toString();
//				
//				this.ed_num.setText(numtemp);
//			}
//	}
//
//	public void setProductPrice(CharSequence text) {
//		this.price.setText(text + "元/份");
//	}
//
//	public void setProductNum(String text) {
//		this.ed_num.setText(text);
//	}
//
//	public class onClic implements OnClickListener {
//
//		@Override
//		public void onClick(View v) {
//
//			wmdb= new WmDB(getContext());
//			switch (v.getId()) {
//			case R.item_item_takeoutbox.btn_plus:
//				int count = Integer
//				.parseInt(TakeOutBoxAct.productNumTemp
//						.get(id).get("productNum").toString());
//				count = count + 1;
//				TakeOutBoxAct.productNumTemp.get(id).put(
//						"productNum", count);
//				// setProductPrice(Arith.to2zero(""+(count*f_price)));++
//				wmdb.Update(TakeOutBoxAct.productNumTemp
//						.get(id).get("productId").toString(),count+"");
//				setNum();
//				Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(6, new String[]{"1"});
//				break;
//			case R.item_item_takeoutbox.btn_minus:
//				int count1 = Integer
//				.parseInt(TakeOutBoxAct.productNumTemp
//						.get(id).get("productNum").toString());
//				if (count1 > 0)
//					count1--;
//				else
//					count1 = 0;
//				TakeOutBoxAct.productNumTemp.get(id).put(
//						"productNum", count1);			
//				wmdb.Update(TakeOutBoxAct.productNumTemp
//						.get(id).get("productId").toString(),count1+"");
//				setNum();
//				Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(6, new String[]{"1"});
//				break;
//			}
//		}
//	}
//}
