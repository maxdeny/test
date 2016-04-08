package com.wjwl.mobile.taocz.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.widget.MImageView;
import com.tcz.apkfactory.data.Ccategory.Msg_Ccategory;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.NearGroupBuyingListAct;

public class Item_Periperal_List extends LinearLayout {

	public String itemid;
	private MImageView micon;
	private TextView mTv;
	private View mClick;
	private List<Msg_Ccategory> mlist;

	public Item_Periperal_List(Context context) {
		super(context);
		initview();
	}

	public Item_Periperal_List(Context context, AttributeSet attrs) {
		super(context, attrs);
		initview();
	}

	void initview() {
		LayoutInflater flater = LayoutInflater.from(this.getContext());
		flater.inflate(R.layout.item_periperal_list, this);
		micon=(MImageView) this.findViewById(R.item_periperal.icon);
		mTv=(TextView) findViewById(R.item_periperal.title);
		mClick=findViewById(R.item_periperal.click);
		mClick.setClickable(true);
	}
	
	public void set(Msg_Ccategory msgcc,final String latitude,final String longitude){
		micon.setObj(msgcc.getCategoryimage());
		mTv.setText(msgcc.getCategoryname());
		itemid=msgcc.getCategoryid();
		mClick.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(getContext(), BusinessListAct.class);
				//Intent intent = new Intent(PeripheralAct.this, ShoppingListAct.class);
				Intent intent = new Intent(getContext(), NearGroupBuyingListAct.class);
				intent.putExtra("act","PeripheralAct");
				intent.putExtra("id",itemid);
				intent.putExtra("tuds", new String[] { latitude, longitude });
				intent.putExtra("title",mTv.getText().toString());
//				intent.putExtra("from", "PeripheralAct");
//				intent.putExtra("typs",(ArrayList<Msg_Ccategory>)mlist);
				getContext().startActivity(intent);
			}
		});
	}
	
	public void set(Msg_Ccategory msgcc,final String latitude,final String longitude, List<Msg_Ccategory> list){
		this.mlist=list;
		set(msgcc,latitude,longitude);
	}
	
	
	
//	sadapter = new SimpleAdapter(this, mData, R.layout.item_periperal_list,
//	new String[] { "icon", "title", }, new int[] {
//			R.item_periperal.icon, R.item_periperal.title });
	
//	//2012-8-17 修改成商品列表
//	Intent intent = new Intent(PeripheralAct.this, BusinessListAct.class);
//	//Intent intent = new Intent(PeripheralAct.this, ShoppingListAct.class);
//	intent.putExtra("id",list_ccategory.get(position).getCategoryid());
//	intent.putExtra("tuds", new String[] { Latitude, Longitude });
//	intent.putExtra("title", list_ccategory.get(position).getCategoryname());
//	intent.putExtra("from", "PeripheralAct");
//	startActivity(intent);

}