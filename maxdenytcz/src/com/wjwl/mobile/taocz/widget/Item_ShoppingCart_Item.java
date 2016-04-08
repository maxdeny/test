package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ShoppingCartAct;
import com.wjwl.mobile.taocz.dialog.CartDialog;
import com.wjwl.mobile.taocz.dialog.V3_Dialog1;

public class Item_ShoppingCart_Item extends LinearLayout {
	private MImageView productimg;
	private TextView productname, productprice, productnum,itemchecktext;
	private Button btn_plus, btn_minus, productdelete;
	public EditText edit_num;
	private int productMaxNum = 100;
	private RelativeLayout editlayout;
	Button itemcheck;
	private int id;
	private String businessId,productId,specid,flag,price,number;

	public Item_ShoppingCart_Item(Context context) {
		super(context);
	}

	public Item_ShoppingCart_Item(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	void initview() {
		itemcheck = (Button) findViewById(R.item_shoppingcart_item.itemcheck);
		itemchecktext = (TextView) findViewById(R.item_shoppingcart_item.itemchecktext);
		productimg = (MImageView) findViewById(R.item_shoppingcart_item.productimg);
		productname = (TextView) findViewById(R.item_shoppingcart_item.productname);
		productprice = (TextView) findViewById(R.item_shoppingcart_item.productprice);
		productnum = (TextView) findViewById(R.item_shoppingcart_item.productnumvalue);
		btn_plus = (Button) findViewById(R.item_shoppingcart_item.btn_plus);
		btn_minus = (Button) findViewById(R.item_shoppingcart_item.btn_minus);
		productdelete = (Button) findViewById(R.item_shoppingcart_item.productdelete);
//		edit_num = (EditText) findViewById(R.item_shoppingcart_item.edit_num);
		editlayout = (RelativeLayout) findViewById(R.item_shoppingcart_item.editlayout);
		btn_plus.setOnClickListener(new OnClick());
		btn_minus.setOnClickListener(new OnClick());
		productdelete.setOnClickListener(new OnClick());
		itemcheck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(itemchecktext.getText().equals("false")){
//					Frame.HANDLES.get("ShoppingCartAct").get(0).sent(102, new String[]{specid,"true",businessId});
					Frame.HANDLES.sentAll("ShoppingCartAct", 102,  new String[]{specid,"true",businessId});
				}
				else{
//					Frame.HANDLES.get("ShoppingCartAct").get(0).sent(102, new String[]{specid,"false",businessId});
					Frame.HANDLES.sentAll("ShoppingCartAct", 102,  new String[]{specid,"false",businessId});
				}
			}
		});
		productnum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				V3_Dialog1 dialog=new V3_Dialog1(getContext());
				dialog.getData(id,productId, price,number,flag,businessId,specid);
				dialog.show();
			}
		});
	}

	
	
	public void setItemChecked(String checked){
		itemchecktext.setText(checked);
		if(checked.equals("true")){
			this.itemcheck.setBackgroundResource(R.drawable.shopcart_icon_ped);
		}
		else{
			this.itemcheck.setBackgroundResource(R.drawable.shopcart_icon_nor);
		}
	}
	
	public void setBusinessId(String id){
		this.businessId = id;
	}
	public void setProductId(String id){
		this.productId = id;
	}
	public void setspecId(String id){
		this.specid = id;
	}
	public void setflag(String flag){
		this.flag = flag;
	}
	public void setproductName(CharSequence text) {
		this.productname.setText(text);
	}

	public void setproductPrice(String text) {
		this.productprice.setText("￥" + text);
		price=text;
	}

//	public void setId(int postion) {
//		this.id=postion;
//	}

	public void setproductNum(String num) {
		for(int i = 0; i < ShoppingCartAct.productNumTemp.size();i++)
		if(this.businessId.equals(ShoppingCartAct.productNumTemp.get(i).get("businessId"))
			&& this.productId.equals(ShoppingCartAct.productNumTemp.get(i).get("productId"))
			&&this.specid.equals(ShoppingCartAct.productNumTemp.get(i).get("specid"))
				){
			this.id = i;
		}
		//利用主Activity中的num赋值
		String numtemp = ShoppingCartAct.productNumTemp.get(id).get("productNum").toString();

//		this.edit_num.setText(numtemp);
		this.productnum.setText(num);
		number=num;
		if (Integer.parseInt(numtemp) <= 1) {
			this.btn_plus.setBackgroundResource(R.drawable.cart_plus_unenable);
		} else {
			this.btn_plus.setBackgroundResource(R.drawable.cart_plus);

		}
		if (Integer.parseInt(numtemp) >= productMaxNum) {
			this.btn_minus
					.setBackgroundResource(R.drawable.cart_minus_unenable);
		} else {
			this.btn_minus.setBackgroundResource(R.drawable.cart_minus);
		}
		
	}

	public void setproductNumTemp(int num) {
//		this.edit_num.setText(num + "");
		String specid = ShoppingCartAct.productNumTemp.get(id).get("specid").toString();
		if (num <= 1) {
			this.btn_plus.setBackgroundResource(R.drawable.cart_plus_unenable);
		} else {
			this.btn_plus.setBackgroundResource(R.drawable.cart_plus);
		}
		if (num >= productMaxNum) {
			this.btn_minus
					.setBackgroundResource(R.drawable.cart_minus_unenable);
		} else {
			this.btn_minus.setBackgroundResource(R.drawable.cart_minus);
		}
		if((num >= 1)&&(num <= productMaxNum)){
//			Frame.HANDLES.get("ShoppingCartAct").get(0).sent(2,new String[]{specid,num+"",businessId,productId,flag});
//			Frame.HANDLES.sentAll("ShoppingCartAct", 2,  new String[]{specid,num+"",businessId,productId,flag});
			
			Intent intent =new Intent("Receiver_ShoppingCartAct");
			intent.putExtra("specid", specid);
			intent.putExtra("num", num+"");
			intent.putExtra("businessId", businessId);
			intent.putExtra("productId", productId);
			intent.putExtra("flag", flag);
			getContext().sendBroadcast(intent);
			
		}
	}

	public void setproduvtImg(Object obj) {
		this.productimg.setObj(obj);
	}

	public void setEditLayoutVisible(int visibility) {
		this.editlayout.setVisibility(visibility);
	}
	public void setButtonVisible(int visibility){
		btn_plus.setVisibility(visibility);
		btn_minus.setVisibility(visibility);
	}

	public void setproductMaxNum(String maxNum) {
		if(!"".equals(maxNum))
			this.productMaxNum = Integer.parseInt(maxNum);
		else 
			this.productMaxNum = 100;
	}

	class OnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			int temp = Integer.parseInt(ShoppingCartAct.productNumTemp.get(id).get("productNum").toString());
			if (btn_plus.equals(v)) {
				if (temp-- > 1) {
					setproductNumTemp(temp);
					ShoppingCartAct.productNumTemp.get(id).put("productNum", temp);
				}
			}else if (btn_minus.equals(v)) {
				
				if (temp++ < productMaxNum) {
					setproductNumTemp(temp);
					ShoppingCartAct.productNumTemp.get(id).put("productNum", temp);
				}
			}else if(productdelete.equals(v)){
				CartDialog dialog = new CartDialog(v.getContext(),id,businessId,productId,specid,flag);
				dialog.show();
			}
		}
	}
}