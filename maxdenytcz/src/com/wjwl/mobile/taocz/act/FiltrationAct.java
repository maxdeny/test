package com.wjwl.mobile.taocz.act;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.wjwl.mobile.taocz.R;

public class FiltrationAct extends MActivity {
	private RelativeLayout layout1, layout2,layout3;
	private CheckBox chk1, chk2;
	private EditText ed_minprice, ed_maxprice;
	private Button bt_submit;
	private String navtype;
	private FiltrationParam filtraparams;
	
	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.filtration);
		
		Intent intent = this.getIntent();
		navtype = intent.getStringExtra("navtype");
		
		layout1 = (RelativeLayout) this.findViewById(R.filtration.clic_layout1);
		layout2 = (RelativeLayout) this.findViewById(R.filtration.clic_layout2);
		chk1 = (CheckBox) this.findViewById(R.filtration.checkbox1);
		chk2 = (CheckBox) this.findViewById(R.filtration.checkbox2);
		ed_minprice = (EditText) this.findViewById(R.filtration.ed_minprice);
		ed_maxprice = (EditText) this.findViewById(R.filtration.ed_maxprice);
		bt_submit = (Button) this.findViewById(R.filtration.bt_submit);
		layout1.setOnClickListener(new Click());
		layout2.setOnClickListener(new Click());
		bt_submit.setOnClickListener(new Click());
		layout3 = (RelativeLayout) this.findViewById(R.filtration.layout2);
		if(navtype.equals("life")){
			layout3.setVisibility(View.GONE);
		}
		
		filtraparams =(FiltrationParam) intent.getSerializableExtra("filter");
		if(filtraparams!=null){
			ed_minprice.setText(filtraparams.minPrice);
			ed_maxprice.setText(filtraparams.maxPrice);
			chk1.setChecked(filtraparams.payLate);
			chk2.setChecked(filtraparams.haveAgio);
		}
	}


	public class Click implements OnClickListener {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.filtration.clic_layout1:
				chk1.setChecked(!chk1.isChecked());
				break;
			case R.filtration.clic_layout2:
				chk2.setChecked(!chk2.isChecked());
				break;
			case R.filtration.bt_submit:
				FiltrationParam filtp=new FiltrationParam();
				filtp.payLate=chk1.isChecked();
				filtp.haveAgio=chk2.isChecked();
				filtp.minPrice=ed_minprice.getText().toString().equals("")?"0":ed_minprice.getText().toString();
				filtp.maxPrice=ed_maxprice.getText().toString().equals("")?"0":ed_maxprice.getText().toString();
				if(Float.parseFloat(filtp.minPrice)>Float.parseFloat(filtp.maxPrice)){
					Toast.makeText(FiltrationAct.this,"价格输入错误！", Toast.LENGTH_SHORT).show();
					break;
				}
				
				if(navtype.equals("shop")){
					Frame.HANDLES.get("ShoppingListAct").get(0).sent(3,filtp);
				}
				if(navtype.equals("life")){
					Frame.HANDLES.get("LifeListAct").get(0).sent(3,filtp);
				}
				if(navtype.equals("takeout")){
					Frame.HANDLES.get("TakeOutAct").get(0).sent(3,filtp);
				}
				finish();
				break;
			}
		}
	}
	
	public static class FiltrationParam implements Serializable{
		private static final long serialVersionUID = 1L;
		public String minPrice,maxPrice;
		public boolean payLate,haveAgio;
	}
}
