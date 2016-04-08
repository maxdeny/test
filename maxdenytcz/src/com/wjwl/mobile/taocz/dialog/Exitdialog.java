package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;

public class Exitdialog extends MDialog {
	public Button submit,cancel;
	public Exitdialog(Context context) {
		super(context,R.style.RDialog);
	}

	@Override
	public void create() {
		this.setContentView(R.layout.exit);
		submit=(Button)this.findViewById(R.exit.bt_submit);
		cancel=(Button)this.findViewById(R.exit.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				F.close(getContext());
				Exitdialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {				
				Exitdialog.this.dismiss();
			}
		});
	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	 public void disposeMessage(Son son) throws Exception{
	}

}
