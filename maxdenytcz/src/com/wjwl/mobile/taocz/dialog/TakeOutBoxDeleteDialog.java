package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class TakeOutBoxDeleteDialog extends MDialog{
	public Button submit, cancel;
	private TextView title, info;
	private Context context;
    private String position;

	public TakeOutBoxDeleteDialog (Context context,String position) {
		super(context, R.style.RDialog);
		this.context = context;
		this.position=position;
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
		title.setText("确认删除");
		info.setText("是否删除该餐盒？");
		submit = (Button) this.findViewById(R.cartdialg.bt_submit);
		cancel = (Button) this.findViewById(R.cartdialg.bt_cancel);
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String [] str=new String[1];
				str[0]=position;
				Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(5, str);
				cancel();
				dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
			cancel();
			dismiss();
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
