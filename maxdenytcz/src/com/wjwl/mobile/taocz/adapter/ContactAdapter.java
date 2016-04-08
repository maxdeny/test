package com.wjwl.mobile.taocz.adapter;
import java.util.ArrayList;
import java.util.List;

import com.mdx.mobile.adapter.MAdapter;
import com.mdx.mobile.mcommons.MContact;
import com.wjwl.mobile.taocz.widget.AddressListViewItem;
import com.wjwl.mobile.taocz.widget.ImageContactLoad;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
public class ContactAdapter extends MAdapter<MContact>{	
	private ImageContactLoad imageload=new ImageContactLoad();
	private List<MContact> selected=null;
	private OnFocusChangeListener checklistener;
	private List<MContact> showing=new ArrayList<MContact>();
	
	public ContactAdapter(Context context, List<MContact> list,List<MContact> selected,OnFocusChangeListener checklistener) {
		super(context, list);
		this.selected=selected;
		this.checklistener=checklistener;
		setSearch("");
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MContact contact=showing.get(position);
		if(convertView==null){
			AddressListViewItem addr=new AddressListViewItem(this.getContext());
			addr.setLoad(imageload);
			convertView=addr;
		}
		AddressListViewItem addr=(AddressListViewItem) convertView;
		addr.setContact(contact);
		addr.setOnChecked(null);
		if(selected.contains(contact)){
			addr.setChecked(true);
		}else{
			addr.setChecked(false);
		}
		addr.setOnChecked(checklistener);
		return convertView;
	}
	
	public void setSearch(String text){
		showing.clear();
		for(MContact contact:this.getList()){
			if(contact.search(text)){
				showing.add(contact);
			}
		}
		notifyDataSetChanged();
	}
	
	public int getCount() {
		return showing.size();
	}
	
	public boolean allchecked(){
		for(MContact contact:showing){
			if(!selected.contains(contact)){
				return false;
			}
		}
		return true;
	}
	
	public void checkeAll(boolean checked){
		if(checked){
			for(MContact contact:showing){
				if(!selected.contains(contact)){
					selected.add(contact);
				}
			}
		}else{
			for(MContact contact:showing){
				selected.remove(contact);
			}
		}
	}
}
