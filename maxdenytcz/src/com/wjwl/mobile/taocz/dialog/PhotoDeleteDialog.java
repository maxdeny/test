package com.wjwl.mobile.taocz.dialog;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class PhotoDeleteDialog extends MDialog {
	public Button submit, cancel;
	public TextView title, text;
	String photo_url;
	Context context;

	public PhotoDeleteDialog(Context context,String photo_url) {
		super(context, R.style.RDialog);
		this.photo_url=photo_url;
		this.context = context;
		Create();
	}

	public void Create() {
		this.setContentView(R.layout.exit);
		submit = (Button) this.findViewById(R.exit.bt_submit);
		cancel = (Button) this.findViewById(R.exit.bt_cancel);
		title = (TextView) findViewById(R.exit.title);
		text = (TextView) findViewById(R.exit.msg);
		title.setText("提示");
		text.setText("您确定要删除吗？");
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(photo_url==null||photo_url.equals(""))
					return;
				File f = new File(photo_url);
				f.delete();
//				Intent i = new Intent();
//				i.setClass(context, WMShowPhoto.class);
//				((Activity) context).setResult(0, i);
				PhotoDeleteDialog.this.cancel();
				PhotoDeleteDialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				PhotoDeleteDialog.this.cancel();
				PhotoDeleteDialog.this.dismiss();
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
