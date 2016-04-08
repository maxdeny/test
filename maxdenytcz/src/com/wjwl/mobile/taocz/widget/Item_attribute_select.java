package com.wjwl.mobile.taocz.widget;

import java.util.HashMap;
import java.util.List;

import com.tcz.apkfactory.data.Attribute.Msg_Attribute;
import com.tcz.apkfactory.data.Attribute.Msg_AttributeValue;
import com.tcz.apkfactory.data.Attribute.Msg_Connection;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Item_attribute_select extends LinearLayout {
	private TextView mName;
	private LinearLayout mLayout;
	private Msg_AttributeValue mAttribute;
	private LayoutParams linearL,linearR,linearF;
	private HashMap<String,AttributeRadio> listRadio=new HashMap<String,AttributeRadio>();
	private Msg_Attribute selectAttribute=null;
	private onChangeListener changeListener=null;
	
	
	public Item_attribute_select(Context context) {
		super(context);
	}

	public Item_attribute_select(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void init(){
		mName=(TextView) findViewById(R.item_attribute.name);
		mLayout=(LinearLayout) findViewById(R.item_attribute.layout);
		
		linearL=new LayoutParams(0,LayoutParams.WRAP_CONTENT);
		linearL.weight=1;
		linearL.setMargins(0, 0, 5, 0);
		
		linearR=new LayoutParams(0,LayoutParams.WRAP_CONTENT);
		linearR.weight=1;
		linearR.setMargins(5, 0, 0, 0);
		
		linearF=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT); 
		linearF.setMargins(0, 10, 0, 0);
	}
	
	public Msg_Attribute setChecked(String id,Boolean bol){
		AttributeRadio ao=listRadio.get(id);
		ao.setChecked(bol);
		return (Msg_Attribute) ao.getTag();
	}
	
	public Msg_Attribute setChecked(String id){
		return setChecked(id,true);
	}
	
	public void set(Msg_AttributeValue attribute){
		this.mAttribute=attribute;
		mName.setText(attribute.getName());
		mLayout.removeAllViews();
		for(int i=0;i<attribute.getAttributeCount();i++){
			LinearLayout linear=new LinearLayout(getContext());
			{
				Msg_Attribute att=attribute.getAttribute(i);
				AttributeRadio ao=new AttributeRadio(getContext());
				ao.setText(att.getName());
				ao.setTag(att);
				ao.setOnClickListener(checkl);
				listRadio.put(att.getId(),ao);
				linear.addView(ao,linearL);
			}
			if((i+1)<attribute.getAttributeCount()){
				Msg_Attribute att1=attribute.getAttribute(i+1);
				AttributeRadio ao=new AttributeRadio(getContext());
				ao.setText(att1.getName());
				ao.setTag(att1);
				listRadio.put(att1.getId(),ao);
				ao.setOnClickListener(checkl);
				linear.addView(ao,linearR);
				i+=1;
			}else{
				View view=new View(getContext());
				linear.addView(view,linearR);
			}
			mLayout.addView(linear,linearF);
		}
	}
	
	private OnClickListener checkl=new OnClickListener() {
		@Override
		public void onClick(View v) {
			for(String key:listRadio.keySet()){
				AttributeRadio aro=listRadio.get(key);
				if(aro!=v){
					aro.setChecked(false);
				}
			}
			AttributeRadio arv=(AttributeRadio) v;
			if((Msg_Attribute) v.getTag()!=selectAttribute){
				if(changeListener!=null){
					changeListener.onChange(mAttribute,(Msg_Attribute) v.getTag(),selectAttribute);
				}
				selectAttribute=(Msg_Attribute) v.getTag();
			}else{
				selectAttribute=null;
				if(changeListener!=null){
					changeListener.onChange(mAttribute,null,selectAttribute);
				}
				arv.setChecked(false);
			}
		}
	};
	
	public void disable(List<String> votos){
		for(String key:votos){
			AttributeRadio aro=listRadio.get(key);
			aro.setChecked(false);
			aro.setEnabled(false);
			if(aro.getTag()==selectAttribute){
				selectAttribute=null;
				changeListener.onChange(mAttribute,null,selectAttribute);
			}
		}
	}
	
	public void disable(String type,String id){
		for(Msg_Attribute ma:this.mAttribute.getAttributeList()){
			for(Msg_Connection con:ma.getConnectionList()){
				if(con.getId().equals(type)){
					for(String voto:con.getVetosList()){
						if(voto.equals(id)){
							AttributeRadio aro=listRadio.get(ma.getId());
							aro.setChecked(false);
							aro.setEnabled(false);
							if(aro.getTag()==selectAttribute){
								selectAttribute=null;
								changeListener.onChange(mAttribute,null,selectAttribute);
							}
						}
					}
				}
			}
		}
	}
	
	public void enable(){
		for(String key:listRadio.keySet()){
			AttributeRadio aro=listRadio.get(key);
			aro.setEnabled(true);
		}
	}
	
	public interface onChangeListener{
		public void onChange(Msg_AttributeValue mAttribute,Msg_Attribute msgA,Msg_Attribute last);
	}

	public Msg_AttributeValue getmAttribute() {
		return mAttribute;
	}

	public void setmAttribute(Msg_AttributeValue mAttribute) {
		this.mAttribute = mAttribute;
	}

	public Msg_Attribute getSelectAttribute() {
		return selectAttribute;
	}

	public void setSelectAttribute(Msg_Attribute selectAttribute) {
		this.selectAttribute = selectAttribute;
	}

	public onChangeListener getChangeListener() {
		return changeListener;
	}

	public void setOnChangeListener(onChangeListener onChangeListener) {
		this.changeListener = onChangeListener;
	}
}