package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.wjwl.mobile.taocz.R;

public class V3_Dialog2 extends MDialog {
	Button bt_submit;
	CheckBox chk1, chk2;
	Context context;

	String myjumptype ;
	public V3_Dialog2(Context context,String jumptype) {
		super(context,R.style.RDialog);
		this.context = context;
		this.myjumptype=jumptype;
		Create();
		// TODO Auto-generated constructor stub
	}

	public void Create() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.v3_dialog2);
		chk1 = (CheckBox) findViewById(R.v3_dialog2.checkbox1);
		chk2 = (CheckBox) findViewById(R.v3_dialog2.checkbox2);
		bt_submit = (Button) findViewById(R.v3_dialog2.submit);
		bt_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean[] temp = new Boolean[] { chk1.isChecked(),
						chk2.isChecked() };
				if(myjumptype.equals("list")){
					Frame.HANDLES.get("GroupBuyingListAct").get(0).sent(1, temp);
				}
				else{
					Frame.HANDLES.get("NearGroupBuyingListAct").get(0).sent(1, temp);
				}
				cancel();
				dismiss();
			}

		});
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}
}
