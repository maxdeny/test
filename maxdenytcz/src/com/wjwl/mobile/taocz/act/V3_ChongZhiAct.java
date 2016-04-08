package com.wjwl.mobile.taocz.act;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.mdx.mobile.Frame;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.F;
import com.wjwl.mobile.taocz.R;
import com.wjwl.mobile.taocz.widget.HeadLayout;
import com.wjwl.mobile.taocz.widget.TczV3_HeadLayout;

public class V3_ChongZhiAct extends MActivity {
	TczV3_HeadLayout head;
	EditText kahao, mima;
	String actfrom;
	String tcardcode, tcardpassword;
	EditText ed_tcardcode, ed_tcardpassword;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_chongzhi);
		ed_tcardcode = (EditText) findViewById(R.v3_chongzhi.kahao);
		ed_tcardpassword = (EditText) findViewById(R.v3_chongzhi.mima);
		ed_tcardcode.setText("");
		ed_tcardpassword.setText("");
		head = (TczV3_HeadLayout) findViewById(R.tczv3.header);
		head.setLeftClick(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head.setTitle("淘心卡充值");
		head.setRightButton3Text("提交");
		kahao = (EditText) findViewById(R.v3_chongzhi.kahao);
		mima = (EditText) findViewById(R.v3_chongzhi.mima);
		actfrom = getIntent().getStringExtra("actfrom");
		head.setRightButton3Click(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tcardcode = ed_tcardcode.getText().toString().trim();
				tcardpassword = ed_tcardpassword.getText().toString().trim();
				if (tcardcode.length() == 0) {
					Toast.makeText(V3_ChongZhiAct.this, "请输入淘心卡账号",
							Toast.LENGTH_SHORT).show();
					ed_tcardcode.requestFocus();
					return;
				}
				// else if (tcardcode.length() != 15) {
				// Toast.makeText(V3_ChongZhiAct.this, "输入有误，请重新输入",
				// Toast.LENGTH_SHORT).show();
				// ed_tcardcode.requestFocus();
				// return;
				// }
				if (tcardpassword.length() == 0) {
					Toast.makeText(V3_ChongZhiAct.this, "请输入密码",
							Toast.LENGTH_SHORT).show();
					ed_tcardpassword.requestFocus();
					return;
				}
				dataLoad(null);
			}
		});
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son != null && son.mgetmethod.equals("v3_otcard")) {
			Msg_Retn.Builder builder = (Msg_Retn.Builder) son.build;
			if (builder.getErrorCode() == 0) {
				Toast.makeText(V3_ChongZhiAct.this, "充值成功", Toast.LENGTH_SHORT)
						.show();
				if (Frame.HANDLES.get("V3_ZaiXianAct").size() > 0) {
					// Frame.HANDLES.get("V3_ZaiXianAct").get(0).reload();
					Frame.HANDLES.get("V3_ZaiXianAct").get(0)
							.sent(2, "refresh");
				}
				if (Frame.HANDLES.get("V3_HuoDaoAct").size() > 0) {
					// Frame.HANDLES.get("V3_HuoDaoAct").get(0).reload();
					Frame.HANDLES.get("V3_HuoDaoAct").get(0).sent(2, "refresh");
				}
				finish();
			} else {
				Toast.makeText(V3_ChongZhiAct.this, builder.getErrorMsg(),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	@Override
	public void dataLoad(int[] types) {
		this.loadData(new Updateone[] { new Updateone("V3_OTCARD",
				new String[][] {
						{ "userid", F.USER_ID },// F.USER_ID
						{ "tcardcode", tcardcode },
						{ "tcardpassword", tcardpassword } }), });
	}
}
