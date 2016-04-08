package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.mdx.mobile.Frame;
import com.mdx.mobile.dialogs.MDialog;
import com.wjwl.mobile.taocz.R;

public class ShareDialog extends MDialog {

	private Context context;
	private Button bt_wb;
	private Button bt_wx;
	private String actfrom;

	public ShareDialog(Context context, String actfrom) {
		super(context,R.style.RDialog);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.actfrom = actfrom;
		mcreate();
	}

	private void mcreate() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.cameradialog);
		bt_wb = (Button) findViewById(R.cameradialog.capture);
		bt_wx = (Button) findViewById(R.cameradialog.pick);
		bt_wb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (actfrom.equals("TczV3_GoodsDetailsAg"))
					Frame.HANDLES.get("TczV3_GoodsDetailsAg").get(0)
							.sent(99, "");
//				else if (actfrom.equals("GroupBuyContentsAct"))
//					Frame.HANDLES.get("GroupBuyContentsAct").get(0)
//							.sent(99, "");
				cancel();
				dismiss();
			}
		});
		bt_wx.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (actfrom.equals("TczV3_GoodsDetailsAg"))
					Frame.HANDLES.get("TczV3_GoodsDetailsAg").get(0)
							.sent(100, "");
//				else if (actfrom.equals("GroupBuyContentsAct"))
//					Frame.HANDLES.get("GroupBuyContentsAct").get(0)
//							.sent(100, "");
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
