package com.wjwl.mobile.taocz.dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;

import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class TakeOutDeleteDialog extends MDialog {
	public Button submit, cancel;
	private TextView title, info;
	private Context context;
	String downloadyrl="";


	public TakeOutDeleteDialog(Context context,String url) {
		super(context, R.style.RDialog);
		this.context = context;
		this.downloadyrl=url;
		MCreate();
	}

	public void setTitle(CharSequence text) {
		this.title.setText(text);
	}

	public void setInfo(CharSequence text) {
		this.info.setText(text);
	}

	@Override
	public void create() {

	}

	public void MCreate() {
		this.setContentView(R.layout.deletedialg);
		this.title = (TextView) findViewById(R.id.title);
		this.info = (TextView) findViewById(R.id.info);
		title.setText("提示");
		info.setText("是否下载楼口美食？");
		submit = (Button) this.findViewById(R.cartdialg.bt_submit);
		cancel = (Button) this.findViewById(R.cartdialg.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Frame.HANDLES.get("HomePageAct").get(0).sent(1, downloadyrl);
				TakeOutDeleteDialog.this.cancel();
				TakeOutDeleteDialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TakeOutDeleteDialog.this.cancel();
				TakeOutDeleteDialog.this.dismiss();
			}
		});
	}

	@Override
	public void dataLoad(int[] typs) {
		
	}

	@Override
	public void disposeMessage(Son son) throws Exception {

		
	}
}
