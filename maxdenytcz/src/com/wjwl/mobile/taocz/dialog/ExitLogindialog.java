package com.wjwl.mobile.taocz.dialog;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.DB.SQLDB;
import com.wjwl.mobile.taocz.act.LoginAct;

public class ExitLogindialog extends MDialog {
	public Button submit, cancel;
	public TextView title, text;
	private SQLDB userdb;

	public ExitLogindialog(Context context) {
		super(context, R.style.RDialog);
	}

	@Override
	public void create() {
		this.setContentView(R.layout.exit);
		userdb = new SQLDB(getContext());
		submit = (Button) this.findViewById(R.exit.bt_submit);
		cancel = (Button) this.findViewById(R.exit.bt_cancel);
		title = (TextView) findViewById(R.exit.title);
		text = (TextView) findViewById(R.exit.msg);
		title.setText("提示");
		text.setText("您确定要注销吗？");
		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				F.USER_ID = null;
				F.GOODSCOUNT = 0;
				CleanDB();
				Toast.makeText(getContext(), "注销成功", Toast.LENGTH_LONG).show();
				Frame.HANDLES.closeIds("MyAct,ShoppingCartAct,MySettingAct");
				Frame.HANDLES.sentAll("FrameAg", 1, R.frame.homeindex);
				// Frame.HANDLES.get("FrameAg").get(0).sent(86, R.frame.index);
				Frame.HANDLES.sentAll("ShoppingCartAct", 3, "");
				F.USER_ID = "";
				F.USERNAME = "";
				ExitLogindialog.this.cancel();
				ExitLogindialog.this.dismiss();
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ExitLogindialog.this.cancel();
				ExitLogindialog.this.dismiss();
			}
		});
	}

	protected void CleanDB() {
		// TODO Auto-generated method stub
		if (userdb.tabIsExist(userdb.tbname1)) {
			Cursor cs = userdb.query(userdb.tbname1);
			if (cs.getCount() > 0) {
				userdb.delete(userdb.tbname1, "c_id=?", new String[] { "1" });
			}
		}

	}

	@Override
	public void dataLoad(int[] typs) {

	}

	@Override
	public void disposeMessage(Son son) throws Exception {
	}

}
