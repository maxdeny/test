package com.wjwl.mobile.taocz.dialog;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.mcommons.MContact;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.adapter.ContactAdapter;
import com.wjwl.mobile.taocz.widget.AddressListViewItem;

public class ContactSelect extends MDialog{
	public Button confim,cancel;
	public EditText edt;
	public ListView content;
	private CheckBox checkbox;
	public String search="";
	public boolean runc=true;
	private onchecklistener onchecklistener;
	private checklistener checkedlistener;
	private List<MContact> selected=new ArrayList<MContact>();
	private OnContactSelectListener oncontactselect;
	private ContactAdapter adapter;
	private View loading_fill = null;
	
	public ContactSelect(Context context) {
		super(context,R.style.RDialog);
	}

	public void Create(Bundle savedInstanceState) {
		this.setContentView(R.layout.contact_select);
		selected.clear();
		confim=(Button) findViewById(R.contactSelect.submit);
		content=(ListView) findViewById(R.contactSelect.content);
		cancel=(Button) findViewById(R.contactSelect.cancel);
		edt=(EditText) findViewById(R.contactSelect.contact_search);
		this.DataLoadByDelay(null);
		loading_fill = findViewById(R.id.Loading_fill);
		checkbox=(CheckBox) findViewById(R.contactSelect.checkbox);
		edt.addTextChangedListener(new TextWatcher() {
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			public void afterTextChanged(Editable s) {
				search=s.toString();
				if(adapter!=null){
					adapter.setSearch(search);
					content.scrollTo(0, 0);
					setcheck(true);
				}
			}
		});
		onchecklistener=new onchecklistener();
		checkedlistener=new checklistener();
		checkbox.setOnCheckedChangeListener(onchecklistener);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ContactSelect.this.cancel();
			}
		});
		
		confim.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(oncontactselect!=null){
					oncontactselect.onSelect(selected);
				}
				ContactSelect.this.cancel();
			}
		});
		checkbox.setEnabled(false);
	}
	
	
	
	public class onchecklistener implements OnCheckedChangeListener{
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
			adapter.checkeAll(isChecked);
			setContent(isChecked);
		}
	}

	public class checklistener implements OnFocusChangeListener{
		public void onFocusChange(View v, boolean hasFocus) {
			AddressListViewItem addr=(AddressListViewItem) v;
			if(hasFocus){
				if(!selected.contains(addr.getContact())){
					selected.add(addr.getContact());
				}
				setcheck(true);
			}else{
				selected.remove(addr.getContact());
				setcheck(false);
			}
		}
	}
	
	private void setContent(boolean bolean){
		for(int i=0;i<content.getChildCount();i++){
			AddressListViewItem view=(AddressListViewItem) content.getChildAt(i);
			view.setOnChecked(null);
			view.setChecked(bolean);
			view.setOnChecked(checkedlistener);
		}
	}
	

	@Override
	public void dataLoad(int[] typs) {
		this.LoadData(0,null);
	}

	
	private void setcheck(boolean checked){
		checked=adapter.allchecked();
		checkbox.setOnCheckedChangeListener(null);
		checkbox.setChecked(checked);
		checkbox.setOnCheckedChangeListener(onchecklistener);
	}
	@Override
	public Object runLoad(int type,Object obj){
		return Frame.getContacts(ContactSelect.this.getContext());
	}
	
	public interface OnContactSelectListener{
		public void onSelect(List<MContact> list);
	}

	public void setOnContactselect(OnContactSelectListener oncontactselect) {
		this.oncontactselect = oncontactselect;
	}
	
	@Override
	public void showLoad() {
		loading_fill.setVisibility(View.VISIBLE);
	}

	@Override
	public void closeLoad() {
		loading_fill.setVisibility(View.GONE);
	}
	
	@SuppressWarnings("unchecked")
	public void disposeMsg(int type,Object obj){
		List<MContact> list=(List<MContact>) obj;
		adapter=new ContactAdapter(this.getContext(), list, selected, checkedlistener);
		content.setAdapter(adapter);
		checkbox.setEnabled(true);
		adapter.setSearch(search);
		content.scrollTo(0, 0);
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		Create(null);
	}

}
