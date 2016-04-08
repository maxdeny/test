package com.wjwl.mobile.taocz.widget;

import com.mdx.mobile.manage.ImageLoad;
import com.mdx.mobile.mcommons.MContact;
import com.mdx.mobile.widget.MImageView;
import com.wjwl.mobile.taocz.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddressListViewItem extends LinearLayout {
	
//	private Context context;
	
	@SuppressWarnings("unused")
	private Object tag;
	private MImageView imageView;
	private TextView textViewName;
	private TextView textViewTelphone;
	private CheckBox checkBox;
	private Context context;
	private MContact contact=null;
	
	public AddressListViewItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}
	
	public AddressListViewItem(Context context) {
		super(context);
		this.context = context;
		init();
	}
	
	private void init(){
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.address_listview_item, this);
		imageView = (MImageView)findViewById(R.id.imageView);
		textViewName = (TextView)findViewById(R.id.textViewName);
		textViewTelphone = (TextView)findViewById(R.id.textViewTelphone);
		checkBox = (CheckBox)findViewById(R.id.checkBox);
		LinearLayout addressLayout = (LinearLayout)findViewById(R.id.addressLayout);
		addressLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				checkBox.setChecked(!checkBox.isChecked());
			}
		});
	}
	
	public void setImage(Drawable drawable){
		imageView.setBackgroundDrawable(drawable);
	}
	
	public void setLoad(ImageLoad imageload){
		imageView.setImageload(imageload);
	}
	
	public void setImage(String imageurl){
		imageView.setObj(imageurl);
	}
	
	public void setName(String value){
		textViewName.setText(value);
	}

	public void setTelphone(String value){
		textViewTelphone.setText(value);
	}
	
	public void setTag(Object tag){
		this.tag = tag;
	}
	
	
	public void setContact(MContact contact) {
		this.contact = contact;
		imageView.setObj(contact);
		this.setName(contact.getName());
		this.setTelphone(contact.getPhone());
	}
	
	public MContact getContact(){
		return this.contact;
	}

	public void setChecked(boolean flag){
		checkBox.setChecked(flag);
	}
	
	public boolean isChecked(){
		return checkBox.isChecked();
	}
	
	public void setOnChecked(final OnFocusChangeListener checked){
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(checked!=null){
					checked.onFocusChange(AddressListViewItem.this, isChecked);
				}
			}
		});
		
	}
}

