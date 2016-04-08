package com.wjwl.mobile.taocz.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mdx.mobile.activity.MActivity;
import com.mdx.mobile.base.Retn.Msg_Retn;
import com.mdx.mobile.manage.Updateone;
import com.mdx.mobile.server.Son;
import com.wjwl.mobile.taocz.R;

public class V3_RegesterStep2Act extends MActivity {
	TextView t_title, title;
	EditText ed_vertifycode;
	Button bt_sumbit, bt_back,bt_regetvertifycode;
	private String tel, vertifycode;

	@Override
	protected void create(Bundle arg0) {
		// TODO Auto-generated method stub
		setContentView(R.layout.v3_registerstep2);
		title = (TextView) findViewById(R.v3_registerstpe2.title);
		t_title = (TextView) findViewById(R.v3_registerstpe2.t_title);
		ed_vertifycode=(EditText)findViewById(R.v3_registerstpe2.ed_vertifycode);
		tel = getIntent().getStringExtra("title");
		title.setText(tel);
		bt_regetvertifycode = (Button) findViewById(R.v3_registerstpe2.bt_getvertifycode);
		bt_sumbit = (Button) findViewById(R.v3_registerstpe2.bt_sumbit);
		bt_back = (Button) findViewById(R.v3_registerstpe2.back);
		bt_regetvertifycode.setOnClickListener(new onclic());
		bt_sumbit.setOnClickListener(new onclic());
		bt_back.setOnClickListener(new onclic());
		// dataLoad(null);
	}

	public class onclic implements OnClickListener {

		public void onClick(View v) {
			switch (v.getId()) {
			case R.v3_registerstpe2.back:
				V3_RegesterStep2Act.this.finish();
				break;
			case R.v3_registerstpe2.bt_getvertifycode:
				// bt_regetvertifycode.setBackgroundColor(R.drawable.bt_com_gray);
				dataLoad(null);
				break;
			case R.v3_registerstpe2.bt_sumbit:
				vertifycode = ed_vertifycode.getText().toString().trim();
				if (vertifycode.length() <= 0) {
					Toast.makeText(V3_RegesterStep2Act.this, "请输入验证码",
							Toast.LENGTH_SHORT).show();
					ed_vertifycode.requestFocus();
					return;
				}else if (vertifycode.length() != 4) {
					Toast.makeText(V3_RegesterStep2Act.this, "验证码输入有误",
							Toast.LENGTH_SHORT).show();
					ed_vertifycode.requestFocus();
					return;
				}
				dataLoad(new int[] { 1 });
				break;
			}
		}
	}

	@Override
	public void disposeMessage(Son son) throws Exception {
		if (son.build != null && son.mgetmethod.equals("osendcode")) {
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "验证码已发送",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
		if (son.build != null && son.mgetmethod.equals("v3_verification")) {// 验证验证码
			Msg_Retn.Builder retn = (Msg_Retn.Builder) son.build;
			if (retn.getErrorCode() == 0) {
				Toast.makeText(getApplicationContext(), "认证通过",
						Toast.LENGTH_LONG).show();
				Intent i = new Intent();
				i.putExtra("tel", tel);
				i.setClass(V3_RegesterStep2Act.this, V3_RegesterStep3Act.class);
				startActivity(i);
				V3_RegesterStep2Act.this.finish();
			} else {
				Toast.makeText(getApplicationContext(), retn.getErrorMsg(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public void dataLoad(int[] typs) {
		if (typs == null)
			this.loadData(new Updateone[] { new Updateone("OSENDCODE",
					new String[][] { { "tel", tel }, { "sendtype", "1" } }), });
		else if (typs[0] == 1)
			this.loadData(new Updateone[] { new Updateone("V3_VERIFICATION",
					new String[][] { { "phoneno", tel },
							{ "registcode", vertifycode },
							{ "registtyep", "1" } }), });

	}
}
