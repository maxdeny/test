package com.wjwl.mobile.taocz.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.GroupBuyingListAct;
import com.wjwl.mobile.taocz.act.Search_Act;
import com.wjwl.mobile.taocz.act.ShoppingListAct;
import com.wjwl.mobile.taocz.act.TczV3_GoodsListAct;

public class SearchIteam extends LinearLayout{
	TextView name,number;
	String style="";
	LinearLayout linear;
	public SearchIteam(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	public void init(){
		LayoutInflater flater=LayoutInflater.from(getContext());
		View view=flater.inflate(R.layout.searchitem, this);
		name=(TextView) findViewById(R.searchitem.name);
		number=(TextView) findViewById(R.searchitem.number);
		linear=(LinearLayout) findViewById(R.searchitem.linear);
		linear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String keyWord=name.getText().toString().replace("-团购", "").replace("-购物","");
				if(style==null&&Frame.HANDLES.get("Search_Act").size()!=0){
					Frame.HANDLES.get("Search_Act").get(0).sent(1, new String[] {keyWord});
				}else{
//					Intent intent =new Intent();
//					intent.putExtra("keywords", keyWord);
//					intent.putExtra("title", keyWord);
//					intent.putExtra("shaixuan","true" );
//					if(style.equals("yuding")){
////						intent.setClass(getContext(), RestaurantDetailsListAct.class);
//					}
//					//else if(style.equals("waimai")){
//					//	intent.setClass(getContext(), TakeOutListAct1.class);
//					//}
//				else if(style.equals("shangjia")){
////						intent.setClass(getContext(), TnTejia_Act.class);
//					}else if(style.equals("shenghuo")||style.equals("tuangou")){
//						intent.putExtra("act", "SearchIteam");
//						intent.putExtra("isshow", "no");
//						intent.setClass(getContext(), GroupBuyingListAct.class);
//					}else{
//						intent.putExtra("isshow", "no");
//						intent.setClass(getContext(), TczV3_GoodsListAct.class);
//					}
//					getContext().startActivity(intent);
					
					F.searchTo(getContext(),keyWord,"");
				}
				
				
			}
		});
	}
	public void setData(String na,String nu){
		name.setText(na);
		number.setText(nu);
	}
	public void setName(String na){
		name.setText(na);
	}
	public void setStyle(String style){
		this.style=style;
	}
	public void setNumber(String nu){
		number.setText(nu);
	}
}
