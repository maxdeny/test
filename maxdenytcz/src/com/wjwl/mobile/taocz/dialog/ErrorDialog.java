package com.wjwl.mobile.taocz.dialog;

import com.mdx.mobile.InitConfig.ErrMsg;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.dialogs.MsgDialog;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ErrorDialog extends MDialog implements MsgDialog {
	private Button bt_submit;
	private Button bt_cancle;
	private TextView error_title,error_text;

	public ErrorDialog(Context context) {
		super(context, R.style.RDialog);
		mcreate();
	}

	public void setMsg(ErrMsg em) {
		if (em.type == 0) {
			error_title.setText(getContext().getResources().getString(R.string.waring));
			error_text.setText(em.msg);
			bt_submit.setVisibility(View.GONE);
		}
		if (em.type == 1) {
			error_title.setText(getContext().getResources().getString(R.string.error));
			error_text.setText(em.msg);
			bt_submit.setText(getContext().getResources().getString(R.string.sysmenu_exit));
		}
	}

	public void mcreate() {
		this.setContentView(R.layout.error);
		bt_submit = (Button) this.findViewById(R.error.bt_submit);
		bt_cancle = (Button) this.findViewById(R.error.bt_cancel);
		error_title = (TextView) this.findViewById(R.error.error_title);
		error_text=(TextView) this.findViewById(R.error.showText);
		bt_submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				F.close(getContext());
			}
		});
		bt_cancle.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dismiss();
			}
		});
	}
	
	@Override
	public void create() {
	}

	@Override
	public void toLogin() {
		// TODO Auto-generated method stub
		
	}

}
