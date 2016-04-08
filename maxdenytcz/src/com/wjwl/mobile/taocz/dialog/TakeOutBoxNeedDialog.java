package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class TakeOutBoxNeedDialog extends MDialog {
	public Button submit, cancel;
	public EditText ed_need;
	String title,position,textinfo;
	Context context;
	public TextView head_title;
	public TakeOutBoxNeedDialog(Context context, String title,String position,String textinfo) {
		super(context, R.style.RDialog);
		this.context = context;
		this.title = title;
		this.position=position;
		this.textinfo=textinfo;
		Create();
	}

	public void Create() {
		this.setContentView(R.layout.service_reservation_otherneed);
		head_title = (TextView) this.findViewById(R.serres_need.head_title);
		if (title != null)
			head_title.setText(title);
		ed_need = (EditText) this.findViewById(R.serres_need.ed_need);
		ed_need.setText(textinfo);
		submit = (Button) this.findViewById(R.serres_need.bt_submit);
		cancel = (Button) this.findViewById(R.serres_need.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String[] str=new String[2];
				str[0]=position;
				str[1]=ed_need.getText().toString().trim();
				Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(3, str);
				closekeyboard();
				cancel();
				dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			    cancel();
				dismiss();
				closekeyboard();
				
			}
		});
	}
	
	private void closekeyboard(){
		InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(ed_need.getWindowToken(), 0); 
	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

}
