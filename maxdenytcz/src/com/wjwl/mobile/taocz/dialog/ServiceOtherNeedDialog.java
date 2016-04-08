package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.ServiceReservationAct;

public class ServiceOtherNeedDialog extends MDialog {
	public Button submit, cancel;
	public EditText ed_need;
	String title;
	Context context;
	public TextView head_title;

	public ServiceOtherNeedDialog(Context context, String title) {
		super(context, R.style.RDialog);
		this.context = context;
		this.title = title;
		Create();
	}

	public void Create() {
		this.setContentView(R.layout.service_reservation_otherneed);
		head_title = (TextView) this.findViewById(R.serres_need.head_title);
		if (title != null)
			head_title.setText(title);
		ed_need = (EditText) this.findViewById(R.serres_need.ed_need);
		submit = (Button) this.findViewById(R.serres_need.bt_submit);
		cancel = (Button) this.findViewById(R.serres_need.bt_cancel);

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ServiceOtherNeedDialog.this.cancel();
				ServiceOtherNeedDialog.this.dismiss();
			}
		});
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
