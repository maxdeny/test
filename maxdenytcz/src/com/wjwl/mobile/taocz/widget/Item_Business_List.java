package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Cbusinessinfo.Msg_Cbusinessinfo;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.BusinessLifeAct;
import com.wjwl.mobile.taocz.act.BusinessShopAct;
import com.wjwl.mobile.taocz.commons.Arith;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_Business_List extends LinearLayout {

	private TextView title, position, bus, telephone, address;
	private MImageView businessImg;
	private String businesstype;
	public Item_Business_List(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview();

	}

	public Item_Business_List(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	public void set(final Msg_Cbusinessinfo cbu) {
		setAddress(cbu.getBusinessaddress());
		setTitle(cbu.getBusinessname());
		setTeleohone(cbu.getBusinessphone());
		setBus(cbu.getBusinessbusway());
		setPosition(Arith.to2zero((Float.parseFloat(cbu.getRemark()==null?"0":cbu.getRemark())/1000)+"") + "公里");
		businesstype=cbu.getProvidetype();
		setBusinessImg(cbu.getBusinessimage());
		findViewById(R.id.click).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("businessid", cbu.getBusinessid());
				i.putExtra("businessname", cbu.getBusinessname());
				if (businesstype.equals("material")){
					i.setClass(getContext(), BusinessShopAct.class);
				}else if(businesstype.equals("service")){
					i.setClass(getContext(), BusinessLifeAct.class);
				}else{
					i.setClass(getContext(), BusinessShopAct.class);
				}
				getContext().startActivity(i);
			}
		});
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_business_list, this);
		businessImg = (MImageView) findViewById(R.item_business_list.businessimg);
		title = (TextView) findViewById(R.item_business_list.title);
		position = (TextView) findViewById(R.item_business_list.position);
		bus = (TextView) findViewById(R.item_business_list.bus);
		telephone = (TextView) findViewById(R.item_business_list.telephone);
		address = (TextView) findViewById(R.item_business_list.address);
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setPosition(CharSequence text) {
		this.position.setText(text);
	}

	public void setBus(CharSequence text) {
		this.bus.setText(text);
	}

	public void setTeleohone(final CharSequence text) {
		this.telephone.setText(text);
	}

	public void setAddress(CharSequence text) {
		this.address.setText(text);
	}

	public void setBusinessImg(Object obj) {
		this.businessImg.clean();
		this.businessImg.setObj(obj);
	}
}