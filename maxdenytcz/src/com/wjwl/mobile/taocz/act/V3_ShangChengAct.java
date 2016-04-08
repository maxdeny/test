package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;

public class V3_ShangChengAct extends MActivity {
	HeadLayout head;
	EditText phone, mima;
	TextView shuoming;
	String str_phone;
	String str_password;
	public String ydcoin;
	public String order_sn_main, actfrom,ydcount;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_changcheng);
		order_sn_main = getIntent().getStringExtra("order_sn_main");
		actfrom = getIntent().getStringExtra("actfrom");
		ydcount= getIntent().getStringExtra("ydcount");
		head = (HeadLayout) findViewById(R.v3_changcheng.head);
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setTitle("移动商城币支付");
		head.setRightText("提交");
		phone = (EditText) findViewById(R.v3_changcheng.phone);
		mima = (EditText) findViewById(R.v3_changcheng.mima);
		shuoming = (TextView) findViewById(R.v3_changcheng.shuoming);
		shuoming.setText(Html
				.fromHtml("<font color=\"#333333\">忘记密码？请编辑短信</font>"
						+ "<font color=\"#ff0000\">CX</font>"
						+ "<font color=\"#333333\">到</font>"
						+ "<font color=\"#ff0000\">106583410167</font>"
						+ "<font color=\"#333333\">找回</font>"));
		head.setRightClick(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				str_phone = phone.getText().toString().trim();
				str_password = mima.getText().toString().trim();
				if (str_phone.length() == 0) {
					Toast.makeText(V3_ShangChengAct.this, "请输入11位有效手机号码",
							Toast.LENGTH_SHORT).show();
					phone.requestFocus();
					return;
				} else if (str_phone.length() != 11) {
					Toast.makeText(V3_ShangChengAct.this, "请输入11位有效手机号码",
							Toast.LENGTH_SHORT).show();
					phone.requestFocus();
					return;
				}
				if (str_password.length() == 0) {
					Toast.makeText(V3_ShangChengAct.this, "请输入11位有效手机号码",
							Toast.LENGTH_SHORT).show();
					mima.requestFocus();
					return;
				}
				dataLoad(null);
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("v3_oydcoin")) {
			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
			if (builder.getErrorCode() == 0) {
				Toast.makeText(V3_ShangChengAct.this, "支付成功",
						Toast.LENGTH_SHORT).show();
				if (actfrom.equals("V3_ZaiXianAct")) {
					Frame.HANDLES.get("V3_ZaiXianAct").get(0)
							.sent(1, builder.getErrorMsg());
					V3_ShangChengAct.this.finish();
				}
				else{
					Frame.HANDLES.get("V3_HuoDaoAct").get(0)
					.sent(1, builder.getErrorMsg());
			       V3_ShangChengAct.this.finish();
				}
			} else {
				Toast.makeText(V3_ShangChengAct.this, builder.getErrorMsg(),
						Toast.LENGTH_SHORT).show();
				V3_ShangChengAct.this.finish();
			}

		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("V3_OYDCOIN",
				new String[][] { { "userid", F.USER_ID },
						{ "telphone", str_phone },
						{ "telphonepassword", str_password },
						{ "ydcoin", ydcount },
						{ "order_sn_main", order_sn_main } }), });
	}
}
