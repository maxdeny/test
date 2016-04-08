//package com.wjwl.mobile.taocz.dialog;
//
//import android.content.Context;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.RadioButton;
//
//import com.mdx.mobile.Frame;
//import com.mdx.mobile.dialogs.MDialog;
//import com.wjwl.mobile.taocz.R;
//import com.wjwl.mobile.taocz.widget.Item_takeoutbox;
//
//public class TakeOutBoxPayTypeDialog extends MDialog {
//	public RadioButton rbt_zxzf, rbt_hdfk;
//	public Button bt_sumbit, bt_cancel;
//	public String position;
//
//	public TakeOutBoxPayTypeDialog(Context context, String position) {
//		super(context, R.style.RDialog);
//		this.position = position;
//		Create();
//		// TODO Auto-generated constructor stub
//	}
//
//	public void Create() {
//		// TODO Auto-generated method stub
//		setContentView(R.layout.takeoutboxpaytypedialog);
//		rbt_zxzf = (RadioButton) findViewById(R.takeoutboxpaytypedialog.zxzf);
//		rbt_hdfk = (RadioButton) findViewById(R.takeoutboxpaytypedialog.hdfk);
//		rbt_hdfk.setChecked(true);
//		bt_sumbit = (Button) findViewById(R.takeoutboxpaytypedialog.bt_sumbit);
//		bt_cancel = (Button) findViewById(R.takeoutboxpaytypedialog.bt_back);
//		bt_sumbit.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				String str[] = new String[2];
//				if (rbt_hdfk.isChecked()) {
//					// Item_takeoutbox.paytype.setText("货到付款");
//					str[0] = position;
//					str[1] = "1";
//					Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(2, str);
//				} else if (rbt_zxzf.isChecked()) {
//					// Item_takeoutbox.paytype.setText("在线支付");
//					str[0] = position;
//					str[1] = "2";
//					Frame.HANDLES.get("TakeOutBoxAct").get(0).sent(2, str);
//				}
//
//				dismiss();
//			}
//		});
//		bt_cancel.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				cancel();
//				dismiss();
//			}
//		});
//	}
//
//	@Override
//	public void create() {
//		// TODO Auto-generated method stub
//
//	}
//
//}
