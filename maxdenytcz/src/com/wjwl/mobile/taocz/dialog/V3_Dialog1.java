package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingCartAct;
import com.wjwl.mobile.taocz.widget.Item_ShoppingCart_Item;

public class V3_Dialog1 extends MDialog {
	Button bt_minus, bt_plus, bt_cancel, bt_submit;
	EditText ed_num;
	TextView textview,price_tv;
	Context context;
	String productid,price,number,flag,businessId,specid;
	int count;
	int id;
	String data[];
	private int productMaxNum = 100;

	public V3_Dialog1(Context context) {
		super(context,R.style.RDialog);
		this.context = context;
		Create();
		// TODO Auto-generated constructor stub
	}
	public void getData(int id,String itemid,String price,String number,String flag,String businessId,String specid){
		this.productid=itemid;
		if(number!=null&&number.length()>0){
			count=Integer.parseInt(number);
		}else{
			count=1;
		}
		ed_num.setText(count+"");
		this.flag=flag;
		this.price=price;
		price_tv.setText("￥"+price);
		this.businessId=businessId;
		this.specid=specid;
		this.id=id;
	}

	public void Create() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.v3_dialog1);
		bt_minus = (Button) findViewById(R.v3_dialog1.minus);
		bt_plus = (Button) findViewById(R.v3_dialog1.plus);
		bt_cancel = (Button) findViewById(R.v3_dialog1.bt_cancel);
		bt_submit = (Button) findViewById(R.v3_dialog1.bt_submit);
		price_tv=(TextView) findViewById(R.v3_dialog1.price);
		ed_num=(EditText) findViewById(R.v3_dialog1.ed_num);
		
		bt_minus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				count++;
				ed_num.setText(count+"");
			}

		});
		bt_plus.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(count>1){
					count--;
					ed_num.setText(count+"");
				
				}
			}

		});
		bt_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancel();
				dismiss();
			}

		});
		bt_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				int temp = Integer.parseInt(ShoppingCartAct.productNumTemp.get(id).get("productNum").toString());
//				setproductNumTemp(temp);
				ShoppingCartAct.productNumTemp.get(id).put("productNum", count);
				
				
//				data=new String[]{specid,ed_num.getText().toString(),businessId,productid,flag};
//				if(Frame.HANDLES.get("ShoppingCartAct")!=null&&Frame.HANDLES.get("ShoppingCartAct").size()>0){
//					Frame.HANDLES.sentAll("ShoppingCartAct", 100, data);
//				}
				
				Intent intent =new Intent("Receiver_ShoppingCartAct");
				intent.putExtra("specid", specid);
				intent.putExtra("num", ed_num.getText().toString());
				intent.putExtra("businessId", businessId);
				intent.putExtra("productId", productid);
				intent.putExtra("flag", flag);
				getContext().sendBroadcast(intent);
				
//				int temp = Integer.parseInt(ShoppingCartAct.productNumTemp.get(id).get("productNum").toString());
//				setproductNumTemp(temp);
//				ShoppingCartAct.productNumTemp.get(id).put("productNum", temp);
				dismiss();
			}

		});
	}
	
	public void setproductNumTemp(int num) {
//		this.edit_num.setText(num + "");
		String specid = ShoppingCartAct.productNumTemp.get(id).get("specid").toString();
		if (num <= 1) {
			this.bt_plus.setBackgroundResource(R.drawable.cart_plus_unenable);
		} else {
			this.bt_plus.setBackgroundResource(R.drawable.cart_plus);
		}
		if (num >= productMaxNum) {
			this.bt_minus
					.setBackgroundResource(R.drawable.cart_minus_unenable);
		} else {
			this.bt_minus.setBackgroundResource(R.drawable.cart_minus);
		}
		if((num >= 1)&&(num <= productMaxNum)){
			Frame.HANDLES.sentAll("ShoppingCartAct",2,new String[]{specid,num+"",businessId,productid,flag});
		}
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}
}
