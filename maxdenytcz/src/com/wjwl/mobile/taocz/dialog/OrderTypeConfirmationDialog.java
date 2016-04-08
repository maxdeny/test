package com.wjwl.mobile.taocz.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.mdx.mobile.dialogs.MDialog;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.act.V3_WriteOrderAct;

public class OrderTypeConfirmationDialog extends MDialog {
	public CheckBox chk1, chk2;
	public Button bt_back, bt_pay;
	private Context context;
	private RadioButton rbt_xianjin, rbt_pos;
	private RadioGroup group;

	public OrderTypeConfirmationDialog(Context context) {
		super(context, R.style.RDialog);
		this.context = context;
		mcreate();
	}

	public void mcreate() {
		// TODO Auto-generated method stub
		this.setContentView(R.layout.ordertypeconfirmationdialog);
		rbt_xianjin = (RadioButton) findViewById(R.ordertypeconfirmationdialog.xianjin);
		rbt_pos = (RadioButton) findViewById(R.ordertypeconfirmationdialog.pos);
		rbt_xianjin.setChecked(true);
		rbt_pos.setChecked(false);
		chk1 = (CheckBox) findViewById(R.ordertypeconfirmationdialog.checkbox1);
		chk2 = (CheckBox) findViewById(R.ordertypeconfirmationdialog.checkbox2);
		bt_back = (Button) findViewById(R.ordertypeconfirmationdialog.bt_back);
		bt_pay = (Button) findViewById(R.ordertypeconfirmationdialog.bt_pay);
		group = (RadioGroup) findViewById(R.ordertypeconfirmationdialog.payTypeGroup);
		bt_back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cancel();
				dismiss();
			}
		});
		bt_pay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String temp_paytype = "4";// 现金还是pos机支付(现金"1"，POS机"2",无选择"4")
				if (chk2.isChecked())
					temp_paytype = "4";
				else if (chk1.isChecked()) {
					if (rbt_xianjin.isChecked())
						temp_paytype = "1";
					else
						temp_paytype = "7";
				}

				Intent i = new Intent();
				i.putExtra("paytype", temp_paytype);
				i.setClass(context, V3_WriteOrderAct.class);
				context.startActivity(i);
				cancel();
				dismiss();

			}
		});
		chk1.setOnCheckedChangeListener(new checkboxListener());
		chk2.setOnCheckedChangeListener(new checkboxListener());
	}

	public class checkboxListener implements
			CompoundButton.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.ordertypeconfirmationdialog.checkbox1:
				if (isChecked) {
					chk2.setChecked(false);
					group.setVisibility(View.VISIBLE);
				} else {
					chk2.setChecked(true);
					group.setVisibility(View.GONE);
				}
				break;
			case R.ordertypeconfirmationdialog.checkbox2:
				if (isChecked) {
					chk1.setChecked(false);
					group.setVisibility(View.GONE);
				} else {
					chk1.setChecked(true);
					group.setVisibility(View.VISIBLE);
				}
				break;
			}
		}
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}
}
